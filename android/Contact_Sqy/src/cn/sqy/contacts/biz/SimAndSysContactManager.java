package cn.sqy.contacts.biz;

import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import cn.sqy.contacts.R;
import cn.sqy.contacts.model.Contact;
import cn.sqy.contacts.tool.CommonUtil;
import cn.sqy.contacts.tool.ImageTools;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

public class SimAndSysContactManager {
	private static final int NAME_COLUMN = 0;
	private static final int NUMBER_COLUMN = 1;
	private static final String SIM_URI = "content://icc/adn";
	private Context context = null;
	private ContentResolver resolver = null;
	private TelephonyManager telMgr = null;
	private TelManager myTelMgr;
	private ContactManager myContactMgr;

	public SimAndSysContactManager(Context context) {
		this.context = context;
		resolver = context.getContentResolver();
		myTelMgr = new TelManager(context);
		myContactMgr = new ContactManager(context);
	}

	public TelephonyManager getTelephonyManager() {
		if (telMgr == null) {
			telMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telMgr;
	}

	public int getSimCardState() {
		return getTelephonyManager().getSimState();
	}

	/**
	 * ��SIM���ϵ���ϵ�˵��뵽�ҵ����ݿ�
	 */
	public void insertContactToMyContactFromSIM() {
		Uri uri = Uri.parse(SIM_URI);
		Cursor cursor = resolver.query(uri, null, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String name = cursor.getString(NAME_COLUMN);// ��ȡ��ϵ������
			List<Contact> list = myContactMgr.getContactsByName(name);
			if (list.size() < 1) {// ��ϵ���б����ڴ���������ϵ��,������ݲ���
				CommonUtil.Log("sqy", "insertContactToMyContactFromSIM", name,
						'i');
				// ������תΪ����ĸ��ƴ��
				String namePinyin = "";
				HanyuPinyinCaseType caseType = HanyuPinyinCaseType.LOWERCASE;
				HanyuPinyinVCharType vcharType = HanyuPinyinVCharType.WITH_U_AND_COLON;
				HanyuPinyinToneType toneType = HanyuPinyinToneType.WITHOUT_TONE;
				HanyuPinyinOutputFormat output = new HanyuPinyinOutputFormat();
				output.setCaseType(caseType);
				output.setToneType(toneType);
				output.setVCharType(vcharType);
				String string = null;
				string = PinyinHelper.toHanyuPinyinString(name, output, "-");
				String[] strs = string.split("-");
				for (String str : strs)
					namePinyin = namePinyin + str.substring(0, 1);
				// ��ȡͷ��
				byte[] image = null;
				ImageView img = new ImageView(context);
				img.setImageResource(R.drawable.bg_photo_default);
				Bitmap bitmap = ImageTools.getBitmapFromDrawable(img
						.getDrawable());
				image = ImageTools.getByteFromBitmap(bitmap);
				// ������ϵ��
				Contact contact = new Contact(name, namePinyin, "", "", "", "",
						"", image, 0);
				myContactMgr.addContact(contact);// ����ϵ�˲������ݿ�

				List<Contact> list1 = myContactMgr.getContactsByName(name);
				int contactId = list1.get(list1.size() - 1).getId();
				String phoneNumber = cursor.getString(NUMBER_COLUMN);// ��ȡ��ϵ�˵绰
				CommonUtil.Log("sqy", "insertContactToMyContactFromSIM",
						phoneNumber, 'i');
				myTelMgr.addTel(contactId, "�ֻ�", phoneNumber);// ���绰�������ݿ�
			}
		}
		cursor.close();

	}

	/**
	 * ��ϵͳ���ݿ����ϵ�˵��뵽�ҵ����ݿ�
	 */
	public void insertContactToMyContactFromSysContacts() {
		Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// ��ȡ����
			List<Contact> list = myContactMgr.getContactsByName(name);
			if (list.size() < 1) {// ����ҵ���ϵ���б���û�д���������ϵ��,�����
				// ������תΪ����ĸ��ƴ��
				String namePinyin = "";
				HanyuPinyinCaseType caseType = HanyuPinyinCaseType.LOWERCASE;
				HanyuPinyinVCharType vcharType = HanyuPinyinVCharType.WITH_U_AND_COLON;
				HanyuPinyinToneType toneType = HanyuPinyinToneType.WITHOUT_TONE;
				HanyuPinyinOutputFormat output = new HanyuPinyinOutputFormat();
				output.setCaseType(caseType);
				output.setToneType(toneType);
				output.setVCharType(vcharType);
				String string = null;
				string = PinyinHelper.toHanyuPinyinString(name, output, "-");
				String[] strs = string.split("-");
				for (String str : strs)
					namePinyin = namePinyin + str.substring(0, 1);
				// ��ȡͷ��
				byte[] image = null;
				ImageView img = new ImageView(context);
				img.setImageResource(R.drawable.bg_photo_default);
				Bitmap bitmap = ImageTools.getBitmapFromDrawable(img
						.getDrawable());
				image = ImageTools.getByteFromBitmap(bitmap);
				// ������ϵ��
				Contact contact = new Contact(name, namePinyin, "", "", "", "",
						"", image, 0);
				myContactMgr.addContact(contact);// ����ϵ�˲������ݿ�
				// ��ȡ����ϵ�˵ĵ绰����,���������ݿ�
				String id = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));// ��ȡID
				CommonUtil.Log("sqy",
						"insertContactToMyContactFromSysContacts", "Name is : "
								+ name, 'i');
				int isHas = Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
				if (isHas > 0) {// ��������ϵ���е绰����
					List<Contact> list1 = myContactMgr.getContactsByName(name);
					int contactId = list1.get(list1.size() - 1).getId();// ��ϵ�˵�ID��
					Cursor c = resolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + id, null, null);
					while (c.moveToNext()) {
						String number = c
								.getString(c
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						CommonUtil.Log("sqy",
								"insertContactToMyContactFromSysContacts",
								"Number is : " + number, 'i');
						myTelMgr.addTel(contactId, "�ֻ�", number);// ���绰�������ݿ�
					}
					c.close();
				}
			}
		}
		cursor.close();
	}

}
