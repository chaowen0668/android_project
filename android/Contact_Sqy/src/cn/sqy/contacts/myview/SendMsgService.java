package cn.sqy.contacts.myview;

import java.util.List;

import cn.sqy.contacts.tool.CommonUtil;
import cn.sqy.contacts.tool.ContantsUtil;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsManager;

public class SendMsgService extends Service {
	private Context context;
	// private MsgManager msgMgr;
	private SmsManager smsManager;
	private String number;
	private String content;
	private SMSSendReceiver sendReceiver = null;
	private SMSSendReceiver deliveryReceiver = null;

	public SendMsgService() {
		this.context = SendMsgService.this;
		smsManager = SmsManager.getDefault();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		registerSMSReceiver();// ������Ϣ��״̬�ļ���
		CommonUtil.Log("service111", "onCreate", "", 'i');
	}

	@Override
	public void onStart(Intent intent, int startId) {
		number = intent.getStringExtra("number");
		content = intent.getStringExtra("content");
		CommonUtil.Log("service111", "onStart", "", 'i');
		CommonUtil.Log("service111", "number==>", number, 'i');
		CommonUtil.Log("service111", "content==>", content, 'i');
		new Thread(new Runnable() {

			@Override
			public void run() {
				sendMsg(number, content);// ���÷��Ͷ���
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (sendReceiver != null && deliveryReceiver != null) {
			unregisterReceiver(sendReceiver);
			unregisterReceiver(deliveryReceiver);
		}
		CommonUtil.Log("service111", "onDestroy()", "succeed", 'i');
	}

	/**
	 * ���Ͷ���
	 * 
	 * @param number
	 *            �绰����
	 * @param msgContent
	 *            ��������
	 */
	public void sendMsg(String number, String content) {
		/* �����Զ���Action������Intent(��PendingIntent����֮��) */
		Intent itSend = new Intent(ContantsUtil.ACTION_SMS_SEND);
		/* sentIntent����Ϊ���ͺ���ܵĹ㲥��ϢPendingIntent */
		PendingIntent mSendPI = PendingIntent.getBroadcast(context,
				(int) System.currentTimeMillis(), itSend,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Intent itDeliver = new Intent(ContantsUtil.ACTION_SMS_DELIVERY);
		/* deliveryIntent����Ϊ�ʹ����ܵĹ㲥��ϢPendingIntent */
		PendingIntent mDeliverPI = PendingIntent.getBroadcast(context,
				(int) System.currentTimeMillis(), itDeliver,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// ���Ͷ���
		if (content.length() > 70) {// �������ݴ���70ʱ,���ֶ���
			List<String> list = smsManager.divideMessage(content);
			for (String str : list)
				smsManager.sendTextMessage(number, null, str, mSendPI,
						mDeliverPI);
		} else {
			smsManager.sendTextMessage(number, null, content, mSendPI,
					mDeliverPI);
		}
	}

	/**
	 * ע��Receiver
	 */
	private void registerSMSReceiver() {
		sendReceiver = new SMSSendReceiver();
		IntentFilter sendFilter = new IntentFilter(ContantsUtil.ACTION_SMS_SEND);
		registerReceiver(sendReceiver, sendFilter);
		deliveryReceiver = new SMSSendReceiver();
		IntentFilter deliveryFilter = new IntentFilter(
				ContantsUtil.ACTION_SMS_DELIVERY);
		registerReceiver(deliveryReceiver, deliveryFilter);
	}

	/**
	 * �㲥������
	 * 
	 * @author Administrator
	 * 
	 */
	public class SMSSendReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String actionName = intent.getAction();
			CommonUtil.Log("sqy", "SMSSendReceiver", actionName, 'i');
			int resultCode = getResultCode();
			System.out.println("onReceive");
			if (actionName.equals(ContantsUtil.ACTION_SMS_SEND)) {
				switch (resultCode) {
				case Activity.RESULT_OK:
					CommonUtil.Toast(context, "���ͳɹ�!");
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					CommonUtil.Toast(context,
							"SMS Send:RESULT_ERROR_GENERIC_FAILURE!");
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					CommonUtil.Toast(context,
							"SMS Send:RESULT_ERROR_NO_SERVICE!");
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					CommonUtil
							.Toast(context, "SMS Send:RESULT_ERROR_NULL_PDU!");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					CommonUtil.Toast(context,
							"SMS Send:RESULT_ERROR_RADIO_OFF!");
					break;
				}
			} else if (actionName.equals(ContantsUtil.ACTION_SMS_DELIVERY)) {
				switch (resultCode) {
				case Activity.RESULT_OK:
					CommonUtil.Toast(context, "�Է����յ�!");
					onDestroy();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					CommonUtil.Toast(context,
							"SMS Delivery:RESULT_ERROR_GENERIC_FAILURE!");
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					CommonUtil.Toast(context,
							"SMS Delivery:RESULT_ERROR_NO_SERVICE!");
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					CommonUtil.Toast(context,
							"SMS Delivery:RESULT_ERROR_NULL_PDU!");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					CommonUtil.Toast(context,
							"SMS Delivery:RESULT_ERROR_RADIO_OFF!");
					break;
				}
			}
		}
	}
}
