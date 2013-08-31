package cn.sqy.contacts.daompl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import cn.sqy.contacts.dao.IContact;
import cn.sqy.contacts.db.DB;
import cn.sqy.contacts.db.DBHelper;
import cn.sqy.contacts.model.Contact;

public class ContactService implements IContact {
	private DBHelper dbHelper;

	public ContactService(Context context) {
		dbHelper = new DBHelper(context);
	}

	/**
	 * ������ϵ��ʵ��
	 */
	@Override
	public void insert(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(DB.TABLES.CONTACT.FIELDS.NAME, contact.getName());
		values.put(DB.TABLES.CONTACT.FIELDS.NAMEPINYIN, contact.getNamePinyin());
		values.put(DB.TABLES.CONTACT.FIELDS.NICKNAME, contact.getNickName());
		values.put(DB.TABLES.CONTACT.FIELDS.ADDRESS, contact.getAddress());
		values.put(DB.TABLES.CONTACT.FIELDS.COMPANY, contact.getCompany());
		values.put(DB.TABLES.CONTACT.FIELDS.BIRTHDAY, contact.getBirthday());
		values.put(DB.TABLES.CONTACT.FIELDS.NOTE, contact.getNote());
		values.put(DB.TABLES.CONTACT.FIELDS.IMAGE, contact.getImage());
		values.put(DB.TABLES.CONTACT.FIELDS.GROUPID, contact.getGroupId());

		dbHelper.insert(DB.TABLES.CONTACT.TABLENAME, values);
	}

	/**
	 * ��������IDɾ����ϵ��
	 */
	@Override
	public void delete(int id) {
		String sql = String.format(DB.TABLES.CONTACT.SQL.DELETE, id);
		dbHelper.execSQL(sql);
	}

	/**
	 * ɾ��������ϵ��
	 */
	@Override
	public void deleteAll() {
		String sql = DB.TABLES.CONTACT.SQL.DROPTABLE;
		dbHelper.execSQL(sql);
		dbHelper.execSQL(DB.TABLES.CONTACT.SQL.CREATE);
	}

	/**
	 * �޸���ϵ��ʵ��
	 */
	@Override
	public void update(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(DB.TABLES.CONTACT.FIELDS.NAME, contact.getName());
		values.put(DB.TABLES.CONTACT.FIELDS.NAMEPINYIN, contact.getNamePinyin());
		values.put(DB.TABLES.CONTACT.FIELDS.NICKNAME, contact.getNickName());
		values.put(DB.TABLES.CONTACT.FIELDS.ADDRESS, contact.getAddress());
		values.put(DB.TABLES.CONTACT.FIELDS.COMPANY, contact.getCompany());
		values.put(DB.TABLES.CONTACT.FIELDS.BIRTHDAY, contact.getBirthday());
		values.put(DB.TABLES.CONTACT.FIELDS.NOTE, contact.getNote());
		values.put(DB.TABLES.CONTACT.FIELDS.IMAGE, contact.getImage());
		values.put(DB.TABLES.CONTACT.FIELDS.GROUPID, contact.getGroupId());

		dbHelper.update(DB.TABLES.CONTACT.TABLENAME, values,
				DB.TABLES.CONTACT.FIELDS.ID + "= ? ",
				new String[] { contact.getId() + "" });
	}

	/**
	 * ��������������ϵ��
	 */
	@Override
	public List<Contact> getContactsByCondition(String condition) {
		String sql = String.format(DB.TABLES.CONTACT.SQL.SELECT, condition);
		Cursor cursor = dbHelper.rawQuery(sql);

		List<Contact> contacts = new ArrayList<Contact>();
		while (cursor.moveToNext()) {
			Contact contact = new Contact();
			contact.setId(cursor.getInt(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.ID)));
			contact.setName(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.NAME)));
			contact.setNamePinyin(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.NAMEPINYIN)));
			contact.setNickName(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.NICKNAME)));
			contact.setAddress(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.ADDRESS)));
			contact.setCompany(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.COMPANY)));
			contact.setBirthday(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.BIRTHDAY)));
			contact.setNote(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.NOTE)));
			contact.setImage(cursor.getBlob(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.IMAGE)));
			contact.setGroupId(cursor.getInt(cursor
					.getColumnIndex(DB.TABLES.CONTACT.FIELDS.GROUPID)));
			contacts.add(contact);
		}
		dbHelper.close();
		return contacts;
	}

	/**
	 * �ı���ϵ�˷���
	 */
	@Override
	public void changeGroup(String sql) {
		dbHelper.execSingle(sql);
	}

}
