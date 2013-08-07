package cn.sqy.contacts.dao;

import java.util.List;

import cn.sqy.contacts.model.Email;

public interface IEmail {
	public void insert(Email email);

	public void delete(int emailId);

	public void update(Email email);

	public List<Email> getEmailsByCondition(String condition);
}
