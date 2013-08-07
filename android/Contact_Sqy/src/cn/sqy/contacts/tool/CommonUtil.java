package cn.sqy.contacts.tool;

import java.util.Calendar;
import java.util.List;

import cn.sqy.contacts.model.Email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class CommonUtil {
	/**
	 * ��־��Ϣ
	 * 
	 * @param tag
	 *            ��־
	 * @param funcName
	 *            ����־�ĺ�����
	 * @param msg
	 *            ��־��Ϣ����
	 * @param type
	 *            ��־����
	 */
	public static void Log(String tag, String funcName, String msg, char type) {
		switch (type) {
		case 'e':
			Log.e(tag, funcName + "===>" + msg);
			break;
		case 'v':
			Log.v(tag, funcName + "===>" + msg);
			break;
		case 'i':
			Log.i(tag, funcName + "===>" + msg);
			break;
		case 'd':
			Log.d(tag, funcName + "===>" + msg);
			break;
		default:
			Log.d(tag, funcName + "===>" + msg);
			break;
		}
	}

	/**
	 * ��ʾ��ʾ��Ϣ
	 * 
	 * @param context
	 *            Ҫ��ʾ��Ϣ�ĵ�ǰʵ��
	 * @param msg
	 *            ��Ϣ����
	 */
	public static void Toast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * ���ص�ǰϵͳ����
	 * 
	 * @return
	 */
	public static String getNowDate() {
		String date = null;
		Calendar calendar = Calendar.getInstance();
		int mYear = calendar.get(Calendar.YEAR);
		int mMonth = calendar.get(Calendar.MONTH);
		int mDay = calendar.get(Calendar.DAY_OF_MONTH);
		mMonth++;
		if (mMonth < 10)
			date = mYear + "-0" + mMonth;
		else
			date = mYear + "-" + mMonth;
		if (mDay < 10)
			date = date + "-0" + mDay;
		else
			date = date + "-" + mDay;
		return date;
	}

	/**
	 * ���ص�ǰϵͳʱ��
	 * 
	 * @return
	 */
	public static String getNowTime() {
		String time = null;
		Calendar calendar = Calendar.getInstance();
		int mHour = calendar.get(Calendar.HOUR_OF_DAY);
		int mMinute = calendar.get(Calendar.MINUTE);
		int mSecond = calendar.get(Calendar.SECOND);

		if (mHour < 10)
			time = "0" + mHour;
		else
			time = mHour + "";
		if (mMinute < 10)
			time = time + ":0" + mMinute;
		else
			time = time + ":" + mMinute;
		if (mSecond < 10)
			time = time + ":0" + mSecond;
		else
			time = time + ":" + mSecond;
		return time;
	}

	/**
	 * ��绰
	 * 
	 * @param num
	 *            �绰����
	 */
	public static void dial(Context context, String num) {
		if (num.equals("��")) {
			CommonUtil.Toast(context, "����ϵ���޺���!");
			return;
		}
		Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ num));
		context.startActivity(phoneIntent);
		CommonUtil.Toast(context, "���ڲ���" + num);
	}

	/**
	 * ���ʼ�
	 * 
	 * @param emails
	 *            �����ַ
	 */
	public static void sendEmail(Context context, List<Email> emails) {
		if (emails == null || emails.size() < 1) {
			CommonUtil.Toast(context, "����ϵ��������!");
			return;
		}
		Intent email = new Intent(android.content.Intent.ACTION_SEND);
		email.setType("plain/text");
		String[] emailReciver = new String[] { emails.get(0).getEmailAcount() };
		String emailSubject = "����һ������";
		String emailBody = "....";

		// �����ʼ�Ĭ�ϵ�ַ
		email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
		// �����ʼ�Ĭ�ϱ���
		email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
		// ����ҪĬ�Ϸ��͵�����
		email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
		// ����ϵͳ���ʼ�ϵͳ
		context.startActivity(Intent.createChooser(email, "��ѡ���ʼ��������"));
	}

}
