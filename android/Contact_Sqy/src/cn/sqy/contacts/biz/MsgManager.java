package cn.sqy.contacts.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import cn.sqy.contacts.daompl.MsgService;
import cn.sqy.contacts.model.Msg;
import cn.sqy.contacts.tool.CommonUtil;

public class MsgManager {
	private ContentResolver contentResolver;
	private TelManager telMgr;
	private ContactManager contactMgr;
	private SmsManager smsMag = null;
	private MsgService msgService;

	public static final String SMS_URI_ALL = "content://sms/";// ��ȡϵͳ���ж���
	public static final String SMS_URI_INBOX = "content://sms/inbox";// ��ȡϵͳ�ռ������
	public static final String SMS_URI_SEND = "content://sms/sent";// ��ȡϵͳ���������
	public static final String SMS_URI_DRAFT = "content://sms/draft";// ��ȡϵͳ�ݸ������

	public MsgManager(Context context) {
		contentResolver = context.getContentResolver();
		telMgr = new TelManager(context);
		contactMgr = new ContactManager(context);
		smsMag = SmsManager.getDefault();
		msgService = new MsgService(context);
	}

	/**
	 * ���һ����Ϣ(+1)
	 * 
	 * @param msg
	 *            Ҫ��ӵ���Ϣʵ��
	 */
	public void addMsg(String address, String body, int type) {
		ContentValues cv = new ContentValues();
		cv.put("address", address);
		cv.put("body", body);
		cv.put("type", type);
		Uri uri = Uri.parse(SMS_URI_ALL);
		contentResolver.insert(uri, cv);
	}

	/**
	 * �޸���ϢΪ�Ѷ���δ��
	 * 
	 * @param read
	 *            (0:δ��;1:�Ѷ�)
	 * @param threadId
	 */
	public void modifyMsgRead(int read, int threadId) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		ContentValues values = new ContentValues();
		values.put("read", read);
		contentResolver.update(uri, values, "thread_id = ?",
				new String[] { threadId + "" });
	}

	/**
	 * ���ֶ���
	 * 
	 * @param content
	 * @return
	 */
	public ArrayList<String> divideMessage(String content) {
		return smsMag.divideMessage(content);
	}

	/**
	 * ���Ͷ���
	 * 
	 * @param content
	 * @param sendPI
	 * @param deliveryPI
	 */
	public void sendMessage(String address, String content,
			PendingIntent sendPI, PendingIntent deliveryPI) {
		smsMag.sendTextMessage(address, null, content, sendPI, deliveryPI);
	}

	/**
	 * ͨ����ϵ��threadIdɾ����Ϣ
	 * 
	 * @param threadId
	 *            �Ի����к�
	 */
	public void delMsgByThreadId(int threadId) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		contentResolver.delete(uri, "thread_id = ?", new String[] { threadId
				+ "" });
	}

	/**
	 * ͨ����ϢID��ɾ��ָ����Ϣ
	 * 
	 * @param id
	 *            ��Ϣ��ID��
	 */
	public void delMsgByMsgId(int id) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		contentResolver.delete(uri, "_id = ?", new String[] { id + "" });
	}

	/**
	 * ɾ��������Ϣ
	 */
	public void delAllMsg() {
		Uri uri = Uri.parse(SMS_URI_ALL);
		contentResolver.delete(uri, "1 = 1", null);
	}

	/**
	 * ���ݶԻ���Ų�ѯ���ж���
	 * 
	 * @param threadId
	 *            �Ի����
	 * @return
	 */
	public List<Msg> getMsgByThreadId(int threadId) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		String[] projection = new String[] { "_id", "address", "body", "date",
				"type", "read" };
		String selection = "thread_id = " + threadId;
		String sortOrder = " date asc";
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				sortOrder);

		List<Msg> msgs = new ArrayList<Msg>();
		while (cursor.moveToNext()) {
			Msg msg = new Msg();
			msg.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			msg.setThreadId(threadId);
			msg.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			msg.setContent(cursor.getString(cursor.getColumnIndex("body")));
			msg.setMsgMark(cursor.getInt(cursor.getColumnIndex("type")));
			msg.setRead(cursor.getInt(cursor.getColumnIndex("read")));
			CommonUtil.Log("sqy", "getMsgByThreadId", msg.getRead() + "", 'i');
			// ��������
			String address = cursor.getString(cursor.getColumnIndex("address"));
			List<Integer> list = new ArrayList<Integer>();
			list = telMgr.getContactIdByTelNumber(address);
			if (list.size() > 0)
				msg.setPerson(contactMgr.getContactById(list.get(0)).getName());
			else
				msg.setPerson("");
			// ��������
			String date = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			Date d = new Date(Long.parseLong(cursor.getString(cursor
					.getColumnIndex("date"))));
			date = dateFormat.format(d);
			msg.setMsgDate(date);

			msgs.add(msg);
		}
		return msgs;
	}

	/**
	 * ���threadId�Ķ�����
	 * 
	 * @param threadId
	 * @return
	 */
	public int getMsgCountByThreadId(int threadId) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		int smsCount = 0;
		String[] projection = new String[] { "count(*)" };
		String selection = "thread_id=" + threadId;
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				null);
		if (cursor.moveToFirst()) {
			smsCount = Integer.valueOf(cursor.getString(0));
		}
		cursor.close();
		return smsCount;
	}

	/**
	 * ���ݵ绰���뷵�ضԻ����к�
	 * 
	 * @param telNumber
	 * @return
	 */
	public int getThreadIdByTelNumber(String telNumber) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		int threadId = -1;
		String[] projection = new String[] { "thread_id" };
		String selection = "address='" + telNumber + "'";
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				null);
		if (cursor.moveToFirst()) {
			threadId = cursor.getInt(cursor.getColumnIndex("thread_id"));
		}
		cursor.close();
		return threadId;
	}

	/**
	 * ���threadId��δ��������
	 * 
	 * @param threadId
	 * @return
	 */
	public int getUnreadMsgCountByThreadId(int threadId) {
		Uri uri = Uri.parse(SMS_URI_ALL);
		int newSmsCount = 0;
		String[] projection = new String[] { "count(*)" };
		String selection = "thread_id=" + threadId + " and read=0";
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				null);
		if (cursor.moveToFirst()) {
			newSmsCount = Integer.valueOf(cursor.getString(cursor
					.getColumnIndex("count(*)")));
		}
		cursor.close();
		return newSmsCount;
	}

	/**
	 * ��ȡϵͳ���ݿ�ÿ����ϵ�˵����µ�һ������
	 * 
	 * @return ����ʵ�弯
	 */
	public List<Msg> getEveryContactNo1Msg() {
		Uri uri = Uri.parse(SMS_URI_ALL);
		String[] projection = new String[] { "distinct " + "thread_id", "_id",
				"address", "body", "date", "type", "read" };
		String selection = "1=1) group by " + "thread_id"
				+ " order by date desc -- (";
		String sortOrder = " date desc";
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				sortOrder);

		List<Msg> msgs = new ArrayList<Msg>();
		while (cursor.moveToNext()) {
			Msg msg = new Msg();
			msg.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			msg.setThreadId(cursor.getInt(cursor.getColumnIndex("thread_id")));
			msg.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			msg.setContent(cursor.getString(cursor.getColumnIndex("body")));
			msg.setMsgMark(cursor.getInt(cursor.getColumnIndex("type")));
			msg.setRead(cursor.getInt(cursor.getColumnIndex("read")));
			msg.setMsgCount(this.getMsgCountByThreadId(msg.getThreadId()));
			msg.setUnreadCount(this.getUnreadMsgCountByThreadId(msg
					.getThreadId()));
			// ��������
			String address = cursor.getString(cursor.getColumnIndex("address"));
			List<Integer> list = new ArrayList<Integer>();
			list = telMgr.getContactIdByTelNumber(address);
			if (list.size() > 0)
				msg.setPerson(contactMgr.getContactById(list.get(0)).getName());
			else
				msg.setPerson("");
			// ��������
			String date = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			Date d = new Date(Long.parseLong(cursor.getString(cursor
					.getColumnIndex("date"))));
			date = dateFormat.format(d);
			msg.setMsgDate(date);

			msgs.add(msg);
		}
		return msgs;
	}

	/**
	 * ��ȡ�ҵ���ϵ���б���ϵͳ���ݿ�ÿ����ϵ�˵����µ�һ������
	 * 
	 * @return ����ʵ�弯
	 */
	public List<Msg> getMyEveryContactNo1MsgNoCount() {
		Uri uri = Uri.parse(SMS_URI_ALL);
		String[] projection = new String[] { "distinct " + "thread_id", "_id",
				"address", "body", "date", "type", "read" };
		String selection = "1=1) group by " + "thread_id"
				+ " order by date desc -- (";
		String sortOrder = " date desc";
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				sortOrder);

		List<Msg> msgs = new ArrayList<Msg>();

		while (cursor.moveToNext()) {
			Msg msg = new Msg();
			// ��������
			String address = cursor.getString(cursor.getColumnIndex("address"));
			List<Integer> list = new ArrayList<Integer>();
			list = telMgr.getContactIdByTelNumber(address);
			if (list.size() > 0) {
				msg.setPerson(contactMgr.getContactById(list.get(0)).getName());
				msg.setAddress(address);
				msgs.add(msg);
			}
		}
		return msgs;
	}

	/**
	 * ��ϵͳ���еĶ��Ų����Զ�������ݿ�
	 */
	public void insertMsgsFromSysAllMsgs() {
		msgService.deleteAll();// ��ɾ�����������ٵ���
		Uri uri = Uri.parse(SMS_URI_ALL);
		String[] projection = new String[] { "_id", "thread_id", "address",
				"person", "body", "date", "type" };
		String selection = "1=1) -- (";
		String sortOrder = " date desc";
		Cursor cursor = contentResolver.query(uri, projection, selection, null,
				sortOrder);
		while (cursor.moveToNext()) {
			Msg msg = new Msg();
			// ��������
			msg.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			msg.setThreadId(cursor.getInt(cursor.getColumnIndex("thread_id")));
			msg.setAddress(cursor.getString(cursor.getColumnIndex("address")));
			msg.setContent(cursor.getString(cursor.getColumnIndex("body")));
			msg.setMsgMark(cursor.getInt(cursor.getColumnIndex("type")));
			msg.setMsgDate(cursor.getString(cursor.getColumnIndex("date")));
			msgService.insert(msg);// ������뵽�ҵ����ݿ�
		}
	}

	/**
	 * ���ұ��ݵ���Ϣ��ԭ��ϵͳ���ݿ�
	 */
	public void insertIntoSysMsgFromMyMsg() {
		this.delAllMsg();// ��ɾ��������Ϣ
		List<Msg> list = msgService.getMsgsByCondition(" 1=1 ");
		for (Msg msg : list) {
			ContentValues cv = new ContentValues();
			cv.put("address", msg.getAddress());
			cv.put("body", msg.getContent());
			cv.put("type", msg.getMsgMark());
			cv.put("date", msg.getMsgDate());
			cv.put("read", 1);
			Uri uri = Uri.parse(SMS_URI_ALL);
			contentResolver.insert(uri, cv);
		}
	}
}
