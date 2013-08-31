package cn.sqy.contacts.dao;

import java.util.List;

import cn.sqy.contacts.model.Contact;

public interface IContact {
	public void insert(Contact contact);

	public void delete(int id);

	public void deleteAll();

	public void update(Contact contact);

	public List<Contact> getContactsByCondition(String condition);

	public void changeGroup(String sql);
}
