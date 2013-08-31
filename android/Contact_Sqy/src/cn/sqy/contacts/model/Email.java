package cn.sqy.contacts.model;

public class Email {
	private int emailId;// ����ID�ţ�������
	private int id;// ��ϵ��ID��
	private String emailName;// ������������
	private String EmailAcount;// �����˺�

	/**
	 * 
	 */
	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param emailName
	 * @param emailAcount
	 */
	public Email(int id, String emailName, String emailAcount) {
		super();
		this.id = id;
		this.emailName = emailName;
		EmailAcount = emailAcount;
	}

	/**
	 * @param emailId
	 * @param id
	 * @param emailName
	 * @param emailAcount
	 */
	public Email(int emailId, int id, String emailName, String emailAcount) {
		super();
		this.emailId = emailId;
		this.id = id;
		this.emailName = emailName;
		EmailAcount = emailAcount;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getEmailAcount() {
		return EmailAcount;
	}

	public void setEmailAcount(String emailAcount) {
		EmailAcount = emailAcount;
	}

	@Override
	public String toString() {
		return "Email [emailId=" + emailId + ", id=" + id + ", emailName="
				+ emailName + ", EmailAcount=" + EmailAcount + "]";
	}

}
