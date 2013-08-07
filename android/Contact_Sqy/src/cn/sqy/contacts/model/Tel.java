package cn.sqy.contacts.model;

public class Tel {
	private int telId;// �绰ID�ţ�������
	private int id;// ��ϵ��ID��
	private String telName;// �绰��������
	private String telNumber;// �绰����

	/**
	 * ���캯��
	 */
	public Tel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���캯��(+1)
	 * 
	 * @param id
	 *            ��ϵ��ID��
	 * @param telName
	 *            �绰��������
	 * @param telNumber
	 *            �绰����
	 */
	public Tel(int id, String telName, String telNumber) {
		super();
		this.id = id;
		this.telName = telName;
		this.telNumber = telNumber;
	}

	/**
	 * ���캯��(+2)
	 * 
	 * @param telId
	 *            �绰ID�ţ�������
	 * @param id
	 *            ��ϵ��ID��
	 * @param telName
	 *            �绰��������
	 * @param telNumber
	 *            �绰����
	 */
	public Tel(int telId, int id, String telName, String telNumber) {
		super();
		this.telId = telId;
		this.id = id;
		this.telName = telName;
		this.telNumber = telNumber;
	}

	public int getTelId() {
		return telId;
	}

	public void setTelId(int telId) {
		this.telId = telId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelName() {
		return telName;
	}

	public void setTelName(String telName) {
		this.telName = telName;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	@Override
	public String toString() {
		return "Tel [telId=" + telId + ", id=" + id + ", telName=" + telName
				+ ", telNumber=" + telNumber + "]";
	}

}
