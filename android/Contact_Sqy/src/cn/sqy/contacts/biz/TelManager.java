package cn.sqy.contacts.biz;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.sqy.contacts.dao.ITel;
import cn.sqy.contacts.daompl.TelService;
import cn.sqy.contacts.db.DB.TABLES.TEL.FIELDS;
import cn.sqy.contacts.model.Tel;

public class TelManager {
	private ITel dao;

	public TelManager(Context context) {
		dao = new TelService(context);
	}

	/**
	 * ���һ���绰����
	 * 
	 * @param tel
	 *            �绰ʵ��
	 */
	public void addTel(Tel tel) {
		dao.insert(tel);
	}

	/**
	 * ���һ���绰����(+1)
	 * 
	 * @param id
	 *            ��ϵ��ID��
	 * @param telName
	 *            �绰��������
	 * @param telNumber
	 *            �绰����
	 */
	public void addTel(int id, String telName, String telNumber) {
		Tel tel = new Tel(id, telName, telNumber);
		this.addTel(tel);
	}

	/**
	 * �޸ĵ绰����
	 * 
	 * @param tel
	 *            �绰ʵ��
	 */
	public void modifyTel(Tel tel) {
		dao.update(tel);
	}

	/**
	 * �޸ĵ绰����(+1)
	 * 
	 * @param telId
	 *            Ҫ�޸ĵ绰��������
	 * @param id
	 *            �µ���ϵ��ID��
	 * @param telName
	 *            �µĵ绰��������
	 * @param telNumber
	 *            �µĵ绰����
	 */
	public void modifyTel(int telId, int id, String telName, String telNumber) {
		Tel tel = this.getTelById(telId);
		tel.setId(id);
		tel.setTelName(telName);
		tel.setTelNumber(telNumber);

		this.modifyTel(tel);
	}

	/**
	 * ��������ɾ���绰����
	 * 
	 * @param telId
	 *            �绰������
	 */
	public void delTelById(int telId) {
		dao.delete(telId);
	}

	/**
	 * ������ϵID��ɾ���绰����
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 */
	public void delTelByContactId(int contactId) {
		List<Tel> list = this.getTelsByContactId(contactId);
		for (Tel tel : list)
			this.delTelById(tel.getTelId());
	}

	/**
	 * ���������Ų��ҵ绰����
	 * 
	 * @param telId
	 *            �绰������
	 * @return �绰ʵ��
	 */
	public Tel getTelById(int telId) {
		String condition = FIELDS.TELID + "=" + telId;
		List<Tel> list = dao.getTelsByCondition(condition);
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * ������ϵ��ID�Ų��ҵ绰����
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 * @return �绰���뼯
	 */
	public List<String> getTelNumbersByContactId(int contactId) {
		List<Tel> list = this.getTelsByContactId(contactId);
		List<String> telNumbers = new ArrayList<String>();
		for (Tel tel : list)
			telNumbers.add(tel.getTelNumber());
		return telNumbers;
	}

	/**
	 * ������ϵ��ID�Ų��ҵ绰
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 * @return �绰ʵ�弯
	 */
	public List<Tel> getTelsByContactId(int contactId) {
		String condition = FIELDS.ID + "=" + contactId;
		List<Tel> list = dao.getTelsByCondition(condition);
		return list;
	}

	/**
	 * ���ݵ绰���������ϵ��ID
	 * 
	 * @param telNumber
	 *            �绰����
	 * @return ��ϵ��ID����
	 */
	public List<Integer> getContactIdByTelNumber(String telNumber) {
		List<Integer> contactIds = new ArrayList<Integer>();
		String condition = FIELDS.TELNUMBER + "='" + telNumber + "'";
		List<Tel> list = dao.getTelsByCondition(condition);
		for (Tel tel : list)
			contactIds.add(tel.getId());
		return contactIds;
	}
}
