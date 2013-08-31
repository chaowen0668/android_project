package cn.sqy.contacts.model;

import java.util.Arrays;

public class Contact implements Comparable<Contact> {
	private int id; // ��ϵ��ID��������
	private String name;// ��ϵ������
	private String namePinyin;// ��ϵ������ĸ
	private String nickName;// ��ϵ���ǳ�
	private String address;// ��ϵ�˵�ַ
	private String company;// ��ϵ�˹�˾
	private String birthday;// ��ϵ������
	private String note;// ��ϵ�˱�ע��Ϣ
	private byte[] image;// ��ϵ��ͷ��
	private int groupId;// ��ϵ�˷����

	/**
	 * ��ϵ���๹�캯��
	 */
	public Contact() {

	}

	/**
	 * ��ϵ���๹�캯��(+1)
	 * 
	 * @param name
	 *            ��ϵ������
	 * @param nickName
	 *            ��ϵ���ǳ�
	 * @param address
	 *            ��ϵ�˵�ַ
	 * @param company
	 *            ��ϵ�˹�˾
	 * @param birthday
	 *            ��ϵ������
	 * @param note
	 *            ��ϵ�˱�ע
	 * @param image
	 *            ��ϵ��ͷ��
	 * @param groupId
	 *            ��ϵ�˷����
	 */
	public Contact(String name, String namePinyin, String nickName,
			String address, String company, String birthday, String note,
			byte[] image, int groupId) {
		super();
		this.name = name;
		this.namePinyin = namePinyin;
		this.nickName = nickName;
		this.address = address;
		this.company = company;
		this.birthday = birthday;
		this.note = note;
		this.image = image;
		this.groupId = groupId;
	}

	/**
	 * ��ϵ���๹�캯��(+2)
	 * 
	 * @param id
	 *            ��ϵ��ID��
	 * @param name
	 *            ��ϵ������
	 * @param nickName
	 *            ��ϵ���ǳ�
	 * @param address
	 *            ��ϵ�˵�ַ
	 * @param company
	 *            ��ϵ�˹�˾
	 * @param birthday
	 *            ��ϵ������
	 * @param note
	 *            ��ϵ�˱�ע
	 * @param image
	 *            ��ϵ��ͷ��
	 * @param groupId
	 *            ��ϵ�˷����
	 */
	public Contact(int id, String name, String namePinyin, String nickName,

	String address, String company, String birthday, String note, byte[] image,
			int groupId) {
		super();
		this.id = id;
		this.name = name;
		this.namePinyin = namePinyin;
		this.nickName = nickName;
		this.address = address;
		this.company = company;
		this.birthday = birthday;
		this.note = note;
		this.image = image;
		this.groupId = groupId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", namePinyin="
				+ namePinyin + ", nickName=" + nickName + ", address="
				+ address + ", company=" + company + ", birthday=" + birthday
				+ ", note=" + note + ", image=" + Arrays.toString(image)
				+ ", groupId=" + groupId + "]";
	}

	@Override
	public int compareTo(Contact another) {
		return this.namePinyin.compareToIgnoreCase(another.getNamePinyin());
	}

}
