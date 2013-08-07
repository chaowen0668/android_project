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
	 * 插入联系人实体
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
	 * 根据主键ID删除联系人
	 */
	@Override
	public void delete(int id) {
		String sql = String.format(DB.TABLES.CONTACT.SQL.DELETE, id);
		dbHelper.execSQL(sql);
	}

	/**
	 * 删除所有联系人
	 */
	@Override
	public void deleteAll() {
		String sql = DB.TABLES.CONTACT.SQL.DROPTABLE;
		dbHelper.execSQL(sql);
		dbHelper.execSQL(DB.TABLES.CONTACT.SQL.CREATE);
	}

	/**
	 * 修改联系人实体
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
	 * 根据条件查找联系人
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
	 * 改变联系人分组
	 */
	@Override
	public void changeGroup(String sql) {
		dbHelper.execSingle(sql);
	}

}
