package cn.sqy.contacts.db;

import cn.sqy.contacts.tool.CommonUtil;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Msg_DBHelper {

	private Msg_SQLiteHelper helper;
	private SQLiteDatabase db;

	public Msg_DBHelper(Context context) {
		helper = new Msg_SQLiteHelper(context);
	}

	/**
	 * ����ִ�����ݵ���ɾ�Ĳ���
	 * 
	 * @param sql
	 *            ��insert | update |delete��ͷ��SQL���
	 */
	public void execSQL(String sql) {
		db = helper.getWritableDatabase();
		try {
			db.execSQL(sql);
			//CommonUtil.Log("msg", "DBHelper", "execSQL:sql=" + sql, 'i');
		} catch (SQLException sqlEx) {
			System.out.println(sqlEx.getMessage());
		} finally {
			db.close();
		}
	}

	/**
	 * ����ִ�����ݵĲ�ѯ����
	 * 
	 * @param sql��select��ͷ��SQL���
	 * @return
	 */
	public Cursor rawQuery(String sql) {
		db = helper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			//CommonUtil.Log("msg", "DBHelper", "rawQuery��sql=" + sql, 'i');
			return cursor;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * ���ڹرձ�
	 */
	public void close() {
		if (db != null)
			db.close();
	}
}
