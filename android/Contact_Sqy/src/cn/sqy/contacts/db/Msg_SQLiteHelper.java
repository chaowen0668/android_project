package cn.sqy.contacts.db;

import cn.sqy.contacts.tool.CommonUtil;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Msg_SQLiteHelper extends SQLiteOpenHelper {

	/**
	 * ���캯�����������ݿ�
	 * 
	 * @param context
	 */
	public Msg_SQLiteHelper(Context context) {
		super(context, MSG_DB.DATABASENAME, null, MSG_DB.DATABASE_VERSION);
	}

	/**
	 * ������
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(MSG_DB.TABLES.Msg.SQL.CREATE);
			//CommonUtil.Log("msg", "SQLiteHelper", "onCreate", 'i');
		} catch (SQLException sqlEx) {
			throw sqlEx;
		}
	}

	/**
	 * ���ݿ�����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(MSG_DB.TABLES.Msg.SQL.DROPTABLE);
		onCreate(db);
		//CommonUtil.Log("msg", "SQLiteHelper", "onUpgrade", 'i');
	}

}
