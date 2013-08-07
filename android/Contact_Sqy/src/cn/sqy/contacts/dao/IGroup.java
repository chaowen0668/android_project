package cn.sqy.contacts.dao;

import java.util.List;

import cn.sqy.contacts.model.Group;

public interface IGroup {
	public void insert(Group group);

	public void delete(int groupId);

	public void update(Group group);

	public List<Group> getGroupsByCondition(String condition);

}
