package cn.sqy.contacts.daompl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import cn.sqy.contacts.dao.IGroup;
import cn.sqy.contacts.db.DB.TABLES.GROUP.FIELDS;
import cn.sqy.contacts.db.DB.TABLES.GROUP.SQL;
import cn.sqy.contacts.db.DBHelper;
import cn.sqy.contacts.model.Group;

public class GroupService implements IGroup {

	private DBHelper dbHelper;

	public GroupService(Context context) {
		dbHelper = new DBHelper(context);
	}

	@Override
	public void insert(Group group) {
		String sql = String.format(SQL.INSERT, group.getGroupName());
		dbHelper.execSQL(sql);
	}

	@Override
	public void delete(int groupId) {
		String sql = String.format(SQL.DELETE, groupId);
		dbHelper.execSQL(sql);
	}

	@Override
	public void update(Group group) {
		String sql = String.format(SQL.UPDATE, group.getGroupName(),
				group.getGroupId());
		dbHelper.execSQL(sql);
	}

	@Override
	public List<Group> getGroupsByCondition(String condition) {
		String sql = String.format(SQL.SELECT, condition);
		Cursor cursor = dbHelper.rawQuery(sql);

		List<Group> list = new ArrayList<Group>();
		while (cursor.moveToNext()) {
			Group group = new Group();
			group.setGroupId(cursor.getInt(cursor
					.getColumnIndex(FIELDS.GROUPID)));
			group.setGroupName(cursor.getString(cursor
					.getColumnIndex(FIELDS.GROUPNAME)));

			list.add(group);
		}
		dbHelper.close();
		return list;
	}

}
