package cn.sqy.contacts.daompl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import cn.sqy.contacts.dao.IIM;
import cn.sqy.contacts.db.DB.TABLES.IM.FIELDS;
import cn.sqy.contacts.db.DB.TABLES.IM.SQL;
import cn.sqy.contacts.db.DBHelper;
import cn.sqy.contacts.model.IM;

public class IMService implements IIM {

	private DBHelper dbHelper;

	public IMService(Context context) {
		dbHelper = new DBHelper(context);
	}

	@Override
	public void insert(IM im) {
		String sql = String.format(SQL.INSERT, im.getId(), im.getImName(),
				im.getImAcount());
		dbHelper.execSQL(sql);
	}

	@Override
	public void delete(int imId) {
		String sql = String.format(SQL.DELETE, imId);
		dbHelper.execSQL(sql);
	}

	@Override
	public void update(IM im) {
		String sql = String.format(SQL.UPDATE, im.getId(), im.getImName(),
				im.getImAcount(), im.getImId());
		dbHelper.execSQL(sql);
	}

	@Override
	public List<IM> getIMsByCondition(String condition) {
		String sql = String.format(SQL.SELECT, condition);
		Cursor cursor = dbHelper.rawQuery(sql);

		List<IM> list = new ArrayList<IM>();
		while (cursor.moveToNext()) {
			IM im = new IM();
			im.setImId(cursor.getInt(cursor.getColumnIndex(FIELDS.IMID)));
			im.setId(cursor.getInt(cursor.getColumnIndex(FIELDS.ID)));
			im.setImName(cursor.getString(cursor.getColumnIndex(FIELDS.IMNAME)));
			im.setImAcount(cursor.getString(cursor
					.getColumnIndex(FIELDS.IMACOUNT)));

			list.add(im);
		}
		dbHelper.close();
		return list;
	}
}
