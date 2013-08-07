package cn.sqy.contacts.db;

import cn.sqy.contacts.tool.CommonUtil;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	/**
	 * ���캯�����������ݿ�
	 * 
	 * @param context
	 */
	public SQLiteHelper(Context context) {
		super(context, DB.DATABASENAME, null, DB.DATABASE_VERSION);
	}

	/**
	 * ������
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DB.TABLES.CONTACT.SQL.CREATE);
			db.execSQL(DB.TABLES.EMAIL.SQL.CREATE);
			db.execSQL(DB.TABLES.GROUP.SQL.CREATE);
			db.execSQL(DB.TABLES.IM.SQL.CREATE);
			db.execSQL(DB.TABLES.TEL.SQL.CREATE);
			//CommonUtil.Log("sqy", "SQLiteHelper", "onCreate", 'i');
		} catch (SQLException sqlEx) {
			throw sqlEx;
		}
	}

	/**
	 * ���ݿ�����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DB.TABLES.CONTACT.SQL.DROPTABLE);
		db.execSQL(DB.TABLES.EMAIL.SQL.DROPTABLE);
		db.execSQL(DB.TABLES.GROUP.SQL.DROPTABLE);
		db.execSQL(DB.TABLES.IM.SQL.DROPTABLE);
		db.execSQL(DB.TABLES.TEL.SQL.DROPTABLE);
		onCreate(db);
		//CommonUtil.Log("sqy", "SQLiteHelper", "onUpgrade", 'i');
	}

}
