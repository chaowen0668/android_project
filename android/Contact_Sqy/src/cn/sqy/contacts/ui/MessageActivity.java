package cn.sqy.contacts.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.sqy.contacts.R;
import cn.sqy.contacts.biz.ContactManager;
import cn.sqy.contacts.biz.EmailManager;
import cn.sqy.contacts.biz.MsgManager;
import cn.sqy.contacts.model.Contact;
import cn.sqy.contacts.model.Msg;
import cn.sqy.contacts.tool.CommonUtil;
import cn.sqy.contacts.tool.ContantsUtil;
import cn.sqy.contacts.tool.ImageTools;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class MessageActivity extends Activity {
	private ImageButton imbNewMsg;
	private Context context;
	private List<Msg> msgs;// ����ÿ����ϵ�����µ�һ������
	private LayoutInflater inflater;
	private ListView lsvMsg;
	// ��Manager
	private ContactManager contactMgr;
	private MsgManager msgMgr;
	private EmailManager emailMgr;
	// ��Ϣ����
	private Menu myMenu;// �˵�
	private boolean isMessageMgr = false;// true:��Ϣ�������,false:��Ϣ�б����
	private Map<Integer, Boolean> isChecked;// �����Ϣ��ÿ���CheckBox�Ƿ�ѡ��
	private int nowPosMenu;// ��ǵ�ǰʹ�������Ĳ˵������Position
	private int ckbCount = 0;// ��ǰѡ����Ϣ����
	private boolean flag = false;// ��ǵ�ǰ������Ի��б�ǰ�Ƿ���δ������
	private AlertDialog dialog;

	private ComposeObserver composeObserver = null;// ϵͳ���ݿ����

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if ((Integer) msg.obj == 1) {
				CommonUtil.Toast(context, "����ɾ���Ի��ɹ�!");
				toNotMessageMgrMenu();// ��ת��������
			}
			if ((Integer) msg.obj == 2) {
				refreshMsg();
				CommonUtil.Toast(context, "ɾ���Ի��ɹ�!");
			}
			dialog.dismiss();
		}

	};

	public static final int REQUEST_MSG_EDIT = 1;
	public static final int REQUEST_MSG_NEW_CONTACT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		init();// ��ʼ��
		registerForContextMenu(lsvMsg);
		registerSMSObserver();// ע��ϵͳ���ݿ����,���Ƿ�������Ϣ

		// ��ת����Ϣ�༭����
		lsvMsg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!isMessageMgr) {
					if (msgs.get(position).getUnreadCount() > 0)
						flag = true;
					Intent intent = new Intent(context, MsgEditActivity.class);
					intent.putExtra("threadId", msgs.get(position)
							.getThreadId());
					intent.putExtra("flag", flag);
					startActivityForResult(intent, REQUEST_MSG_EDIT);
					flag = false;
				} else {
					CheckBox ckbMsg = (CheckBox) view
							.findViewById(R.id.thread_list_item_message_checkbox);
					if (ckbMsg.isChecked()) {
						ckbMsg.setChecked(false);
						isChecked.put(msgs.get(position).getThreadId(), false);
						ckbCount--;
					} else {
						ckbMsg.setChecked(true);
						isChecked.put(msgs.get(position).getThreadId(), true);
						ckbCount++;
					}
				}
			}
		});

		imbNewMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NewMsgActivity.class);
				startActivity(intent);

				isMessageMgr = false;
				if (myMenu != null) {
					myMenu.getItem(0).setTitle("��������");
					myMenu.getItem(1).setEnabled(false);
					myMenu.getItem(2).setEnabled(false);
					myMenu.getItem(3).setEnabled(false);
					myMenu.getItem(4).setEnabled(false);
				}
			}
		});
	}

	/**
	 * Activity�ĳ�ʼ��
	 */
	@SuppressLint("UseSparseArrays")
	private void init() {
		context = this;
		inflater = LayoutInflater.from(context);
		imbNewMsg = (ImageButton) findViewById(R.id.imbNewMessage);
		lsvMsg = (ListView) findViewById(R.id.lsvMessage);
		contactMgr = new ContactManager(context);
		msgMgr = new MsgManager(context);
		emailMgr = new EmailManager(context);
		refreshMsg();
		isChecked = new HashMap<Integer, Boolean>();
		for (Msg msg : msgs)
			isChecked.put(msg.getThreadId(), false);
		composeObserver = new ComposeObserver(new Handler());
	}

	/**
	 * ˢ�½���
	 */
	private void refreshMsg() {
		msgs = new ArrayList<Msg>();
		msgs = msgMgr.getEveryContactNo1Msg();
		MsgUiAdapter adapter = new MsgUiAdapter();
		lsvMsg.setAdapter(adapter);
	}

	/**
	 * �����˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		CommonUtil.Log("menu", "MessageActivity", "onCreateOptionsMenu", 'i');
		menu.add(1, 1, 1, "��������").setIcon(R.drawable.menu_batch_mgr);
		menu.add(1, 2, 2, "ȫѡ").setIcon(R.drawable.menu_batch_all)
				.setEnabled(false);
		menu.add(1, 3, 3, "ȫ��").setIcon(R.drawable.menu_batch_cancel)
				.setEnabled(false);
		menu.add(1, 4, 4, "�Ѷ�").setIcon(R.drawable.menu_all_read)
				.setEnabled(false);
		menu.add(1, 5, 5, "����ɾ��").setIcon(R.drawable.menu_batch_delete)
				.setEnabled(false);
		myMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * ���������Ĳ˵�
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		CommonUtil.Log("menu", "MessageActivity", "onCreateContextMenu", 'i');
		menu.add(0, 10, 0, "����绰");
		menu.add(0, 11, 1, "ɾ��");
	}

	/*
	 * ��Ӧ�����Ĳ˵��Ͳ˵�
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();// ��ŵ�ǰ��������Ϣ
		CommonUtil.Log("menu", "ContactActivity", "onMenuItemSelected", 'i');
		if (featureId == 6)// �����������Ĳ˵�
			nowPosMenu = info.position;
		switch (item.getItemId()) {
		case 1:// ��������
			if (isMessageMgr) {
				toNotMessageMgrMenu();
			} else {
				toIsMessageMgrMenu();
			}
			break;
		case 2:// ȫѡ
			for (Msg msg : msgs)
				isChecked.put(msg.getThreadId(), true);
			refreshMsg();
			ckbCount = isChecked.size();
			break;
		case 3:// ȫ��
			for (Msg msg : msgs)
				isChecked.put(msg.getThreadId(), false);
			refreshMsg();
			ckbCount = 0;
			break;
		case 4:// �Ѷ�
			if (ckbCount > 0) {
				for (Msg msg : msgs)
					if (isChecked.get(msg.getThreadId()))
						msgMgr.modifyMsgRead(1, msg.getThreadId());
				refreshMsg();
				CommonUtil.Toast(context, "����Ѷ��ɹ�!");
			} else {
				CommonUtil.Toast(context, "��ǰ��ѡ����!");
			}
			toNotMessageMgrMenu();
			break;
		case 5:// ����ɾ��
			if (ckbCount > 0) {
				View view = inflater.inflate(R.layout.progress_dialog, null);
				((TextView) view.findViewById(R.id.progress_msg))
						.setText(" ɾ �� �� . . .");
				dialog = new AlertDialog.Builder(context).setView(view)
						.create();
				new AlertDialog.Builder(context)
						.setTitle("����")
						.setMessage("��ȷ��Ҫɾ��ѡ�еĶԻ���?")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										MessageActivity.this.dialog.show();
										new Thread(new Runnable() {

											@Override
											public void run() {
												try {
													Thread.sleep(700);
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
												for (Msg msg : msgs)
													if (isChecked.get(msg
															.getThreadId())) {
														msgMgr.delMsgByThreadId(msg
																.getThreadId());
													}
												Message message = new Message();
												message.obj = 1;
												handler.sendMessage(message);
											}
										}).start();
									}
								}).setNegativeButton("ȡ��", null).create()
						.show();
			} else
				CommonUtil.Toast(context, "��ǰ��ѡ����!");
			break;
		case 10:// ��绰
			String num = msgs.get(nowPosMenu).getAddress();
			CommonUtil.dial(context, num);
			break;
		case 11:// ɾ���Ի�
			View view1 = inflater.inflate(R.layout.progress_dialog, null);
			((TextView) view1.findViewById(R.id.progress_msg))
					.setText(" ɾ �� �� . . .");
			dialog = new AlertDialog.Builder(context).setView(view1).create();
			new AlertDialog.Builder(context)
					.setTitle("����")
					.setMessage("ȷ��ɾ���˶Ի�??")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									MessageActivity.this.dialog.show();
									new Thread(new Runnable() {

										@Override
										public void run() {
											try {
												Thread.sleep(500);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
											msgMgr.delMsgByThreadId(msgs.get(
													nowPosMenu).getThreadId());
											Message message = new Message();
											message.obj = 2;
											handler.sendMessage(message);
										}
									}).start();
								}
							}).setNegativeButton("ȡ��", null).create().show();

			break;
		}
		return true;
	}

	private void toIsMessageMgrMenu() {
		isMessageMgr = true;
		myMenu.getItem(0).setTitle("ȡ������");
		refreshMsg();
		myMenu.getItem(1).setEnabled(true);
		myMenu.getItem(2).setEnabled(true);
		myMenu.getItem(3).setEnabled(true);
		myMenu.getItem(4).setEnabled(true);
		for (Msg msg : msgs)
			isChecked.put(msg.getThreadId(), false);
	}

	private void toNotMessageMgrMenu() {
		isMessageMgr = false;
		myMenu.getItem(0).setTitle("��������");
		refreshMsg();
		myMenu.getItem(1).setEnabled(false);
		myMenu.getItem(2).setEnabled(false);
		myMenu.getItem(3).setEnabled(false);
		myMenu.getItem(4).setEnabled(false);
	}

	/**
	 * �ص�ˢ�½���
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_MSG_NEW_CONTACT
				&& resultCode == NewContactActivity.RESULT_BTN_SAVE)
			refreshMsg();
		if (requestCode == REQUEST_MSG_EDIT
				&& resultCode == MsgEditActivity.RESULT_MSG_CHANGED)
			refreshMsg();
		if (resultCode == NewMsgActivity.RESULT_SEND_MESG_SUCCEED)
			refreshMsg();
	}

	/**
	 * ����ϵͳ����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		CommonUtil.Log("sqy", "onKeyDown+++", "onKeyDown", 'i');
		if (keyCode == KeyEvent.KEYCODE_BACK && isMessageMgr) {
			toNotMessageMgrMenu();
			CommonUtil.Log("menu", "keyCode", 222 + "", 'i');
			return true;
		}
		CommonUtil.Log("menu", "keyCode", "111", 'i');
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �Զ���Adapter,��ʾ��Ϣ�б�
	 * 
	 * @author dell
	 * 
	 */
	class MsgUiAdapter extends BaseAdapter {
		private PopupWindow mPopupWindow;
		private View popView;

		@Override
		public int getCount() {
			return msgs.size();
		}

		@Override
		public Object getItem(int position) {
			return msgs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = inflater.inflate(R.layout.vlist_message, null);
			QuickContactBadge qcbImage = (QuickContactBadge) convertView
					.findViewById(R.id.thread_list_item_photoview);
			TextView txtUnReadMsgCount = (TextView) convertView
					.findViewById(R.id.thread_list_item_message_count);
			TextView txtMsgName = (TextView) convertView
					.findViewById(R.id.thread_list_item_message_name);
			TextView txtMsgContent = (TextView) convertView
					.findViewById(R.id.thread_list_item_message_summary);
			TextView txtMsgTime = (TextView) convertView
					.findViewById(R.id.thread_list_item_time);
			CheckBox ckbMsg = (CheckBox) convertView
					.findViewById(R.id.thread_list_item_message_checkbox);
			final Msg msg = msgs.get(position);
			// ��ʾ�����Ͷ�������
			if (msg.getPerson().equals(""))
				txtMsgName.setText(msg.getAddress() + " (" + msg.getMsgCount()
						+ ")");
			else
				txtMsgName.setText(msg.getPerson() + " (" + msg.getMsgCount()
						+ ")");
			// ��ʾ��������
			if (msg.getContent().length() > 12)
				txtMsgContent
						.setText(msg.getContent().substring(0, 12) + "...");
			else
				txtMsgContent.setText(msg.getContent());
			// ��ʾ����
			String date = CommonUtil.getNowDate();
			if (msg.getMsgDate().substring(0, 10).equals(date))
				txtMsgTime.setText(msg.getMsgDate().substring(11));
			else
				txtMsgTime.setText(msg.getMsgDate().substring(0, 10));
			// ������ʾδ����������
			if (msg.getUnreadCount() > 0) {
				txtUnReadMsgCount.setVisibility(View.VISIBLE);
				txtUnReadMsgCount.setText(msg.getUnreadCount() + "");
			} else {
				txtUnReadMsgCount.setVisibility(View.GONE);
			}
			// ����ͷ��
			if (!msg.getPerson().equals("")) {// ��Ӧͷ�����¼�
				final List<Contact> list = contactMgr.getContactsByName(msg
						.getPerson());
				byte[] image = list.get(0).getImage();
				Bitmap bitmap = ImageTools.getBitmapFromByte(image);
				qcbImage.setImageBitmap(bitmap);
				qcbImage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// CommonUtil.Toast(context, "������ݲ˵�");
						if (mPopupWindow == null) {
							popView = inflater.inflate(
									R.layout.popup_send_msgs, null);
							mPopupWindow = new PopupWindow(popView,
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT, true);

							mPopupWindow.setFocusable(true);
							mPopupWindow.setTouchable(true);
							mPopupWindow
									.setBackgroundDrawable(new BitmapDrawable());

						}
						if (mPopupWindow.isShowing())
							mPopupWindow.dismiss();

						mPopupWindow.showAsDropDown(v, 50, -25);

						ImageButton imbCall = (ImageButton) popView
								.findViewById(R.id.imbCall);
						ImageButton imbMsg = (ImageButton) popView
								.findViewById(R.id.imbMsg);
						ImageButton imbEmail = (ImageButton) popView
								.findViewById(R.id.imbEmail);
						ImageButton imbEditContact = (ImageButton) popView
								.findViewById(R.id.imbEditContact);
						// ��绰
						imbCall.setOnClickListener(new View.OnClickListener() {

							public void onClick(View v) {
								CommonUtil.dial(context, msg.getAddress());// ��绰
								mPopupWindow.dismiss();
							}
						});
						// ������
						imbMsg.setOnClickListener(new View.OnClickListener() {

							public void onClick(View v) {
								if (msg.getUnreadCount() > 0)
									flag = true;
								Intent intent = new Intent(context,
										MsgEditActivity.class);
								intent.putExtra("threadId", msg.getThreadId());
								intent.putExtra("flag", flag);
								startActivityForResult(intent, REQUEST_MSG_EDIT);
								flag = false;
								mPopupWindow.dismiss();
							}
						});
						// ���ʼ�
						imbEmail.setOnClickListener(new View.OnClickListener() {

							public void onClick(View v) {
								CommonUtil.sendEmail(
										context,
										emailMgr.getEmailsByContactId(list.get(
												0).getId()));// ���ʼ�
								mPopupWindow.dismiss();
							}
						});
						// �鿴��ϵ������
						imbEditContact.setVisibility(View.VISIBLE);
						imbEditContact
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent intent = new Intent(context,
												NewContactActivity.class);
										intent.putExtra("contactId", list
												.get(0).getId());
										startActivityForResult(intent,
												REQUEST_MSG_NEW_CONTACT);
										mPopupWindow.dismiss();
									}
								});
					}
				});
			} else {// �½���ϵ��
				qcbImage.setImageResource(R.drawable.no_contact_photo);
				qcbImage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						new AlertDialog.Builder(context)
								.setTitle("�����ϵ��")
								.setMessage(
										"��'" + msg.getAddress() + "'��ӵ���ϵ��?")
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												Intent intent = new Intent(
														MessageActivity.this,
														NewContactActivity.class);
												intent.putExtra("tel",
														msg.getAddress());
												startActivityForResult(intent,
														REQUEST_MSG_NEW_CONTACT);
											}
										}).setNegativeButton("ȡ��", null)
								.create().show();
					}
				});
			}
			if (isMessageMgr) {
				ckbMsg.setVisibility(View.VISIBLE);
				if (isChecked.get(msg.getThreadId()))
					ckbMsg.setChecked(true);
				else
					ckbMsg.setChecked(false);
			} else {
				ckbMsg.setVisibility(View.GONE);
			}
			return convertView;
		}
	}

	/**
	 * ע��observer
	 */
	private void registerSMSObserver() {
		this.getContentResolver().registerContentObserver(
				Uri.parse(ContantsUtil.SMS_URI_ALL), true, composeObserver);

	}

	/**
	 * ���ݿ����
	 * 
	 * @author Administrator
	 * 
	 */

	class ComposeObserver extends ContentObserver {
		public ComposeObserver(Handler handler) {
			super(handler);
		}

		public void onChange(boolean selfChange) {
			refreshMsg();// ˢ�½���
			//CommonUtil.Log("sqy", "ComposeObserver", "====>refreshMsg()", 'i');
		}
	}

}
