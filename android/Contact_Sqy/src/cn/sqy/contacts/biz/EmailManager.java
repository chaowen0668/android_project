package cn.sqy.contacts.biz;

import java.util.List;

import android.content.Context;
import cn.sqy.contacts.dao.IEmail;
import cn.sqy.contacts.daompl.EmailService;
import cn.sqy.contacts.db.DB.TABLES.EMAIL.FIELDS;
import cn.sqy.contacts.model.Email;

public class EmailManager {
	private IEmail dao;

	public EmailManager(Context context) {
		dao = new EmailService(context);
	}

	/**
	 * �������
	 * 
	 * @param email
	 *            ����ʵ��
	 */
	public void addEmail(Email email) {
		dao.insert(email);
	}

	/**
	 * �������(+1)
	 * 
	 * @param id
	 *            ��ϵ��ID
	 * @param emailName
	 *            ������������
	 * @param emailAcount
	 *            �����˺�
	 */
	public void addEmail(int id, String emailName, String emailAcount) {
		Email email = new Email(id, emailName, emailAcount);
		this.addEmail(email);
	}

	/**
	 * �޸�����
	 * 
	 * @param email
	 *            ����ʵ��
	 */
	public void modifyEmail(Email email) {
		dao.update(email);
	}

	/**
	 * �޸�����(+1)
	 * 
	 * @param emailId
	 *            Ҫ�޸ĵ�����ID��
	 * @param id
	 *            �µ���ϵ��ID
	 * @param emailName
	 *            �µ�������������
	 * @param emailAcount
	 *            �µ������˺�
	 */
	public void modifyEmail(int emailId, int id, String emailName,
			String emailAcount) {
		Email email = this.getEmailById(emailId);
		email.setId(id);
		email.setEmailName(emailName);
		email.setEmailAcount(emailAcount);

		this.modifyEmail(email);
	}

	/**
	 * ������������IDɾ������
	 * 
	 * @param emailId
	 *            ����������
	 */
	public void delEmailById(int emailId) {
		dao.delete(emailId);
	}

	/**
	 * ������ϵ��ID��ɾ������
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 */
	public void delEmailByContactId(int contactId) {
		List<Email> emails = this.getEmailsByContactId(contactId);
		for (Email email : emails)
			this.delEmailById(email.getEmailId());
	}

	/**
	 * ������������ID�Ų�������
	 * 
	 * @param emailId
	 *            ����������
	 * @return ����ʵ��
	 */
	public Email getEmailById(int emailId) {
		String condition = FIELDS.EMAILID + "=" + emailId;
		List<Email> emails = dao.getEmailsByCondition(condition);
		if (emails.size() > 0)
			return emails.get(0);
		else
			return null;
	}

	/**
	 * ������ϵ��ID�Ų�������
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 * @return ����ʵ�弯
	 */
	public List<Email> getEmailsByContactId(int contactId) {
		String condition = FIELDS.ID + "=" + contactId;
		List<Email> emails = dao.getEmailsByCondition(condition);
		return emails;
	}
}
