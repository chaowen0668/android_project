package cn.sqy.contacts.daompl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import cn.sqy.contacts.dao.IEmail;
import cn.sqy.contacts.db.DB;
import cn.sqy.contacts.db.DBHelper;
import cn.sqy.contacts.model.Email;

public class EmailService implements IEmail {
	private DBHelper dbHelper;

	public EmailService(Context context) {
		dbHelper = new DBHelper(context);
	}

	@Override
	public void insert(Email email) {
		String sql = String.format(DB.TABLES.EMAIL.SQL.INSERT, email.getId(),
				email.getEmailName(), email.getEmailAcount());
		dbHelper.execSQL(sql);
	}

	@Override
	public void delete(int emailId) {
		String sql = String.format(DB.TABLES.EMAIL.SQL.DELETE, emailId);
		dbHelper.execSQL(sql);
	}

	@Override
	public void update(Email email) {
		String sql = String.format(DB.TABLES.EMAIL.SQL.UPDATE, email.getId(),
				email.getEmailName(), email.getEmailAcount(),
				email.getEmailId());
		dbHelper.execSQL(sql);
	}

	@Override
	public List<Email> getEmailsByCondition(String condition) {
		String sql = String.format(DB.TABLES.EMAIL.SQL.SELECT, condition);
		Cursor cursor = dbHelper.rawQuery(sql);

		List<Email> list = new ArrayList<Email>();
		while (cursor.moveToNext()) {
			Email email = new Email();
			email.setEmailId(cursor.getInt(cursor
					.getColumnIndex(DB.TABLES.EMAIL.FIELDS.EMAILID)));
			email.setId(cursor.getInt(cursor
					.getColumnIndex(DB.TABLES.EMAIL.FIELDS.ID)));
			email.setEmailName(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.EMAIL.FIELDS.EMAILNAME)));
			email.setEmailAcount(cursor.getString(cursor
					.getColumnIndex(DB.TABLES.EMAIL.FIELDS.EMAILACOUNT)));
			list.add(email);
		}
		dbHelper.close();
		return list;
	}

}
