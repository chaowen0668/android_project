package cn.sqy.contacts.db;

import java.util.List;
import cn.sqy.contacts.tool.CommonUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {

	private SQLiteHelper helper;
	private SQLiteDatabase db;

	public DBHelper(Context context) {
		helper = new SQLiteHelper(context);
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
			//CommonUtil.Log("sqy", "DBHelper", "execSQL:sql=" + sql, 'i');
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
			//CommonUtil.Log("sqy", "DBHelper", "rawQuery��sql=" + sql, 'i');
			return cursor;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * ����ͳ�Ʒ����������еĵ�һֵ
	 * 
	 * @param sql
	 *            select min(),select max(),select count(),select sum()
	 * @return
	 * @throws SQLException
	 */
	public int execSingle(String sql) throws SQLException {
		db = helper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			//CommonUtil.Log("sqy", "DBHelper", "execSingle��sql=" + sql, 'i');
			if (cursor.moveToNext()) {
				return cursor.getInt(0);
			}
			return -1;
		} finally {
			cursor.close();
			db.close();
		}
	}

	/**
	 * ���һ����¼
	 * 
	 * @param table
	 *            ����
	 * @param values
	 *            ���ֶμ�ֵ��HashMap
	 */
	public void insert(String table, ContentValues values) throws SQLException {
		SQLiteDatabase db = null;
		try {
			db = this.helper.getWritableDatabase();
			db.insert(table, null, values);
			//CommonUtil.Log("sqy", "DBHelper", "insert:tableName=" + table, 'i');
		} finally {
			db.close();
		}
	}

	/**
	 * ���������޸ļ�¼
	 * 
	 * @param table
	 *            ����
	 * @param values
	 *            ���ֶμ�ֵ��HashMap
	 * @param whereClause
	 *            Where��䲿��(eg: id=?)
	 * @param whereArgs
	 *            where����Ӧ����ֵ(eg:new String[]{1});
	 */
	public void update(String table, ContentValues values, String whereClause,
			String[] whereArgs) throws SQLException {
		SQLiteDatabase db = null;
		try {
			db = this.helper.getWritableDatabase();
			db.update(table, values, whereClause, whereArgs);
			//CommonUtil.Log("sqy", "DBHelper", "update:tableName=" + table, 'i');
		} finally {
			db.close();
		}
	}

	/**
	 * ɾ��
	 * 
	 * @param table
	 *            ����
	 * @param whereClause
	 *            where ��䲿�� eg: id=?
	 * @param whereArgs
	 *            where��䲿����������ֵ��eg : new String[]{"xxx"}
	 */
	public void delete(String table, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = null;
		try {
			db = this.helper.getWritableDatabase();
			db.delete(table, whereClause, whereArgs);
			//CommonUtil.Log("sqy", "DBHelper", "delete:tableName=" + table, 'i');
		} finally {
			db.close();
		}
	}

	/**
	 * ��װ��������汾�Ĳ���
	 * 
	 * @param sqls
	 *            eg: List<String> sqls =new ArrayList<String>();
	 *            sqls.add("insert into table (field1,field2) values(value1,value2); "
	 *            );
	 *            sqls.add("insert into table (field1,field2) values(value1,value2); "
	 *            );
	 */
	public void execSQLWithTrans(List<String> sqls) {
		SQLiteDatabase db = null;
		try {
			db = this.helper.getWritableDatabase();
			db.beginTransaction();// ��ʼ����
			for (String sql : sqls) {
				db.execSQL(sql);
				//CommonUtil.Log("sqy", "DBHelper",
				//		"execSQLWithTrans:sql=" + sql, 'i');
			}
			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();// ������ı�־�������ύ���񣬻��ǻع�����
			db.close();
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
