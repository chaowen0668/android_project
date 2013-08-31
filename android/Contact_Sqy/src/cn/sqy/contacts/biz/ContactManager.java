package cn.sqy.contacts.biz;

import java.util.List;

import android.content.Context;
import cn.sqy.contacts.dao.IContact;
import cn.sqy.contacts.daompl.ContactService;
import cn.sqy.contacts.db.DB;
import cn.sqy.contacts.db.DB.TABLES.CONTACT.FIELDS;
import cn.sqy.contacts.model.Contact;

public class ContactManager {

	private IContact dao;

	public ContactManager(Context context) {
		dao = new ContactService(context);
	}

	/**
	 * ���һ����ϵ��
	 * 
	 * @param contact
	 *            ��ϵ��ʵ��
	 */
	public void addContact(Contact contact) {
		dao.insert(contact);
	}

	/**
	 * ���һ����ϵ��(+1)
	 * 
	 * @param name
	 *            ��ϵ������
	 * @param nickName
	 *            ��ϵ���ǳ�
	 * @param address
	 *            ��ϵ��סַ
	 * @param company
	 *            ��ϵ�����ڹ�˾
	 * @param birthday
	 *            ��ϵ������
	 * @param note
	 *            ��ϵ�˱�ע
	 * @param image
	 *            ��ϵ��ͷ��
	 * @param groupId
	 *            ��ϵ���������
	 */
	public void addContact(String name, String namePinyin, String nickName,
			String address, String company, String birthday, String note,
			byte[] image, int groupId) {
		Contact contact = new Contact(name, namePinyin, nickName, address,
				company, birthday, note, image, groupId);
		this.addContact(contact);
	}

	/**
	 * �޸���ϵ��
	 * 
	 * @param contact
	 *            ��ϵ��ʵ��
	 */
	public void modifyContact(Contact contact) {
		dao.update(contact);
	}

	/**
	 * �޸���ϵ��(+1)
	 * 
	 * @param id
	 *            ��ϵ������ID��
	 * @param name
	 *            ��ϵ������
	 * @param nickName
	 *            ��ϵ���ǳ�
	 * @param address
	 *            ��ϵ��סַ
	 * @param company
	 *            ��ϵ�����ڹ�˾
	 * @param birthday
	 *            ��ϵ������
	 * @param note
	 *            ��ϵ�˱�ע
	 * @param image
	 *            ��ϵ��ͷ��
	 * @param groupId
	 *            ��ϵ���������
	 */
	public void modifyContact(int id, String name, String namePinyin,
			String nickName, String address, String company, String birthday,
			String note, byte[] image, int groupId) {
		Contact contact = this.getContactById(id);
		contact.setName(name);
		contact.setNamePinyin(namePinyin);
		contact.setNickName(nickName);
		contact.setAddress(address);
		contact.setCompany(company);
		contact.setBirthday(birthday);
		contact.setNote(note);
		contact.setImage(image);
		contact.setGroupId(groupId);

		this.modifyContact(contact);
	}

	/**
	 * ͨ������IDɾ����ϵ��
	 * 
	 * @param id
	 *            ����ID
	 */
	public void delContact(int id) {
		dao.delete(id);
	}

	/**
	 * ͨ�������groupIdɾ����ϵ��
	 * 
	 * @param groupId
	 *            �����
	 */
	public void delContactsByGroupId(int groupId) {
		List<Contact> contacts = this.getContactsByGroupId(groupId);
		for (Contact contact : contacts)
			this.delContact(contact.getId());
	}

	/**
	 * ɾ��ȫ����ϵ��
	 */
	public void delAllContacts() {
		dao.deleteAll();
	}

	/**
	 * ͨ������ID������ϵ��
	 * 
	 * @param id
	 *            ����ID
	 * @return ��ϵ��ʵ��
	 */
	public Contact getContactById(int id) {
		String condition = DB.TABLES.CONTACT.FIELDS.ID + " = " + id;
		List<Contact> contacts = dao.getContactsByCondition(condition);
		if (contacts.size() > 0)
			return contacts.get(0);
		else
			return null;
	}

	/**
	 * ͨ����ϵ������name������ϵ��
	 * 
	 * @param contactName
	 *            ��ϵ������
	 * @return ��ϵ��ʵ�弯
	 */
	public List<Contact> getContactsByName(String contactName) {
		String condition = FIELDS.NAME + " like '%" + contactName + "%' ";
		return dao.getContactsByCondition(condition);
	}

	/**
	 * ͨ����ϵ������namePinyin������ϵ��
	 * 
	 * @param contactNamePinyin
	 *            ��ϵ������ƴ��
	 * @return ��ϵ��ʵ�弯
	 */
	public List<Contact> getContactsByNamePinyin(String contactNamePinyin) {
		String condition = FIELDS.NAMEPINYIN + " like '" + contactNamePinyin + "%' ";
		return dao.getContactsByCondition(condition);
	}

	/**
	 * ͨ����ϵ������name�����ض��������ϵ��
	 * 
	 * @param contactName
	 *            ��ϵ������
	 * @param groupId
	 *            ����ID��
	 * @return ��ϵ��ʵ�弯
	 */
	public List<Contact> getContactsByNameAddGroupId(String contactName,
			int groupId) {
		String condition = FIELDS.NAME + " like '%" + contactName + "%' and "
				+ FIELDS.GROUPID + " = " + groupId;
		return dao.getContactsByCondition(condition);
	}

	/**
	 * ͨ����ϵ������namePinyin�����ض��������ϵ��
	 * 
	 * @param contactNamePinyin
	 *            ��ϵ������ƴ��
	 * @param groupId
	 *            ����ID��
	 * @return ��ϵ��ʵ�弯
	 */
	public List<Contact> getContactsByNamePinyinAddGroupId(
			String contactNamePinyin, int groupId) {
		String condition = FIELDS.NAMEPINYIN + " like '" + contactNamePinyin
				+ "%' and " + FIELDS.GROUPID + " = " + groupId;
		return dao.getContactsByCondition(condition);
	}

	/**
	 * ͨ�������groupId������ϵ��
	 * 
	 * @param groupId
	 *            �����
	 * @return ��ϵ��ʵ�弯
	 */
	public List<Contact> getContactsByGroupId(int groupId) {
		String condition = FIELDS.GROUPID + " = " + groupId;
		return dao.getContactsByCondition(condition);
	}

	/**
	 * ����������ϵ��
	 * 
	 * @return ������ϵ��ʵ�弯
	 */
	public List<Contact> getAllContacts() {
		return dao.getContactsByCondition("1=1");
	}

	/**
	 * �ѹ���ϵ��ID���µķ����toGroupId�ı����
	 * 
	 * @param contactId
	 *            Ҫ�ı�������ϵ��ID��
	 * @param toGroupId
	 *            Ҫ�ƶ����·���ķ���ID��
	 */
	public void changeGroupByContact(int contactId, int toGroupId) {
		String sql = String.format(DB.TABLES.CONTACT.SQL.CHANGEGROUP,
				toGroupId, DB.TABLES.CONTACT.FIELDS.ID + "=" + contactId);
		dao.changeGroup(sql);
	}
}
