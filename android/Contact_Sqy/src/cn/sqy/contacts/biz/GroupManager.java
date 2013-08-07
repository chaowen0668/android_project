package cn.sqy.contacts.biz;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.sqy.contacts.dao.IGroup;
import cn.sqy.contacts.daompl.GroupService;
import cn.sqy.contacts.db.DB.TABLES.GROUP.FIELDS;
import cn.sqy.contacts.model.Group;

public class GroupManager {
	private IGroup dao;

	public GroupManager(Context context) {
		dao = new GroupService(context);
	}

	/**
	 * ���һ������
	 * 
	 * @param group
	 *            ����ʵ��
	 */
	public void addGroup(Group group) {
		dao.insert(group);
	}

	/**
	 * ���һ������(+1)
	 * 
	 * @param groupName
	 *            ��������
	 */
	public void addGroup(String groupName) {
		Group group = new Group(groupName);
		this.addGroup(group);
	}

	/**
	 * ����������
	 * 
	 * @param group
	 *            ����ʵ��
	 */
	public void modifyGroup(Group group) {
		dao.update(group);
	}

	/**
	 * ����������(+1)
	 * 
	 * @param groupId
	 *            Ҫ�޸ĵķ�������ID��
	 * @param groupName
	 *            ����������
	 */
	public void modifyGroup(int groupId, String groupName) {
		Group group = this.getGroupById(groupId);
		group.setGroupName(groupName);
		this.modifyGroup(group);
	}

	/**
	 * ���ݷ��������ID��ɾ������
	 * 
	 * @param groupId
	 *            ����������
	 */
	public void delGroupById(int groupId) {
		dao.delete(groupId);
	}

	/**
	 * ���ݷ�������ID�Ų��ҷ���
	 * 
	 * @param groupId
	 *            ����������
	 * @return ����ʵ��
	 */
	public Group getGroupById(int groupId) {
		String condition = FIELDS.GROUPID + " = " + groupId;
		List<Group> groups = dao.getGroupsByCondition(condition);
		if (groups.size() > 0)
			return groups.get(0);
		else
			return null;
	}

	/**
	 * ���ݷ������Ʋ��ҷ���
	 * 
	 * @param groupName
	 *            ��������
	 * @return ����ʵ�弯
	 */
	public Group getGroupsByName(String groupName) {
		String condition = FIELDS.GROUPNAME + " = " + "'" + groupName + "'";
		Group group = dao.getGroupsByCondition(condition).get(0);
		return group;
	}

	/**
	 * �������з���
	 * 
	 * @return ���з�������Ƽ���
	 */
	public List<String> getAllGroupName() {
		String condition = "1=1";
		List<Group> groups = dao.getGroupsByCondition(condition);
		List<String> groupNames = new ArrayList<String>();
		for (Group group : groups)
			groupNames.add(group.getGroupName());
		return groupNames;
	}

	/**
	 * �������з���
	 * 
	 * @return ���з���ʵ�弯
	 */
	public List<Group> getAllGroups() {
		String condition = "1=1";
		List<Group> groups = dao.getGroupsByCondition(condition);
		return groups;
	}
}
