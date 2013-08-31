package cn.sqy.contacts.biz;

import java.util.List;

import android.content.Context;
import cn.sqy.contacts.dao.IIM;
import cn.sqy.contacts.daompl.IMService;
import cn.sqy.contacts.db.DB.TABLES.IM.FIELDS;
import cn.sqy.contacts.model.IM;

public class IMManager {
	private IIM dao;

	public IMManager(Context context) {
		dao = new IMService(context);
	}

	/**
	 * ���һ����ʱ��Ϣ
	 * 
	 * @param im
	 *            ��Ϣʵ��
	 */
	public void addIM(IM im) {
		dao.insert(im);
	}

	/**
	 * ���һ����ʱ��Ϣ(+1)
	 * 
	 * @param id
	 *            ��ϵ��ID
	 * @param imName
	 *            ��Ϣ����
	 * @param imAcount
	 *            ��Ϣ�˺�
	 */
	public void addIM(int id, String imName, String imAcount) {
		IM im = new IM(id, imName, imAcount);
		this.addIM(im);
	}

	/**
	 * �޸�һ����ʱ��Ϣ
	 * 
	 * @param im
	 *            ��Ϣʵ��
	 */
	public void modifyIM(IM im) {
		dao.update(im);
	}

	/**
	 * �޸�һ����ʱ��Ϣ(+1)
	 * 
	 * @param imId
	 *            Ҫ�޸���Ϣ������ID��
	 * @param id
	 *            �µ���ϵ��ID
	 * @param imName
	 *            �µ���Ϣ����
	 * @param imAcount
	 *            �µ���Ϣ�˺�
	 */
	public void modifyIM(int imId, int id, String imName, String imAcount) {
		IM im = this.getIMById(imId);
		im.setId(id);
		im.setImName(imName);
		im.setImAcount(imAcount);

		this.modifyIM(im);
	}

	/**
	 * ͨ����Ϣ������ɾ��һ����Ϣ
	 * 
	 * @param imId
	 *            ��Ϣ������
	 */
	public void delIMById(int imId) {
		dao.delete(imId);
	}

	/**
	 * �ѹ���ϵ��ID��ɾ����Ϣ
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 */
	public void delIMByContactId(int contactId) {
		List<IM> list = this.getIMsByContactId(contactId);
		for (IM im : list)
			this.delIMById(im.getImId());
	}

	/**
	 * ͨ����Ϣ�����Ų�����Ϣ
	 * 
	 * @param imId
	 *            ��Ϣ������
	 * @return ��Ϣʵ��
	 */
	public IM getIMById(int imId) {
		String condition = FIELDS.IMID + " = " + imId;
		List<IM> list = dao.getIMsByCondition(condition);
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * ͨ����ϵ��ID�Ų�����Ϣ
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 * @return ��Ϣʵ�弯
	 */
	public List<IM> getIMsByContactId(int contactId) {
		String condition = FIELDS.ID + " = " + contactId;
		List<IM> list = dao.getIMsByCondition(condition);
		return list;
	}
}
