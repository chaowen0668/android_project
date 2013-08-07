package cn.sqy.contacts.ui;

import java.util.ArrayList;
import java.util.List;

import cn.sqy.contacts.R;
import cn.sqy.contacts.biz.ContactManager;
import cn.sqy.contacts.biz.EmailManager;
import cn.sqy.contacts.biz.MsgManager;
import cn.sqy.contacts.biz.TelManager;
import cn.sqy.contacts.model.Contact;
import cn.sqy.contacts.model.Msg;
import cn.sqy.contacts.myview.SendMsgService;
import cn.sqy.contacts.tool.CommonUtil;
import cn.sqy.contacts.tool.ImageTools;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MsgEditActivity extends Activity implements OnClickListener,
		OnTouchListener {
	private Context context;
	private ListView lsvItemsMsg;
	private LayoutInflater inflater;
	private ImageView imgMsgPhoto;// ��ϵ����Ϣͷ��
	private TextView txtMsgName;// ��ϵ������,���Ϊİ��������ʾ�绰����
	private ImageButton imbMsgCall;// ��绰��ť
	private Button btnSendMsg;// ������Ϣ
	private EditText edtInputMsg;// ��Ϣ�༭��
	private MsgManager msgMgr;
	private ContactManager contactMgr;
	private EmailManager emailMgr;
	private TelManager telMgr;
	private List<Msg> msgs;
	private int nowThreadId;// ���ڽ��������洫�����Ķ������к�
	private boolean changedFlag = false;// ��ǵ�ǰҳ���Ƿ������ݸı�
	private PopupWindow mPopupWindow;
	private View popView;
	private boolean flag;// ���������洫������flag;
	private ViewFlipper vFlipper;
	private float touchDownX;// ���һ���ʱ��ָ���µ�X����
	private float touchUpX;// ���һ���ʱ��ָ�ɿ���X����
	private ImageView imgPhoneChoiceLeft, imgPhoneChoiceRight;
	private TextView txtCurrentTel, txtTempTel;
	private String currentNumber;// ��¼��ǰ�ĵ绰����
	private String tempNumber;// ��¼�ڶ�������
	private String msgContent;// ���ڴ洢��Ϣ����

	public static final int RESULT_MSG_CHANGED = 1;
	public static final int REQUEST_MSG_EDIT_NEW_CONTACT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_edit);

		init();// ��ʼ��

		initUI();// ��ʼ������

		imbMsgCall.setOnClickListener(this);
		btnSendMsg.setOnClickListener(this);
		registerForContextMenu(lsvItemsMsg);// ע�������Ĳ˵�
	}

	/**
	 * Activity�ĳ�ʼ��
	 */
	private void init() {
		this.context = this;
		lsvItemsMsg = (ListView) findViewById(R.id.lsvItemsMsg);
		imgMsgPhoto = (ImageView) findViewById(R.id.imgMsgPhoto);
		txtMsgName = (TextView) findViewById(R.id.txtMsgName);
		imbMsgCall = (ImageButton) findViewById(R.id.imbMsgCall);
		btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
		edtInputMsg = (EditText) findViewById(R.id.edtInputMsg);
		vFlipper = (ViewFlipper) findViewById(R.id.compose_title_phonenumber);
		imgPhoneChoiceLeft = (ImageView) findViewById(R.id.phone_choice_left);
		imgPhoneChoiceRight = (ImageView) findViewById(R.id.phone_choice_right);
		txtCurrentTel = (TextView) findViewById(R.id.compose_title_current_phonenumber);
		txtTempTel = (TextView) findViewById(R.id.compose_title_temp_phonenumber);
		msgMgr = new MsgManager(context);
		contactMgr = new ContactManager(context);
		emailMgr = new EmailManager(context);
		telMgr = new TelManager(context);
		inflater = LayoutInflater.from(context);
		nowThreadId = this.getIntent().getIntExtra("threadId", -1);
		flag = this.getIntent().getBooleanExtra("flag", false);
		if (flag)
			msgMgr.modifyMsgRead(1, nowThreadId);
	}

	/*
	 * ���������Ĳ˵�
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		CommonUtil.Log("menu", "MsgEditActivity", "onCreateContextMenu", 'i');
		menu.add(0, 1, 0, "ת����Ϣ");
		menu.add(0, 2, 1, "ɾ����Ϣ");
	}

	/*
	 * ��Ӧ�����Ĳ˵��Ͳ˵�
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();// ��ŵ�ǰ��������Ϣ
		CommonUtil.Log("menu", "ContactActivity", "onMenuItemSelected", 'i');
		final int nowPosition = info.position;
		switch (item.getItemId()) {
		case 1:// ת����Ϣ
			Intent intent = new Intent(context, NewMsgActivity.class);
			intent.putExtra("content", msgs.get(nowPosition).getContent());
			startActivity(intent);
			finish();
			break;
		case 2:// ɾ����Ϣ
			new AlertDialog.Builder(context)
					.setTitle("����")
					.setMessage("ȷ��ɾ��Щ��Ϣ��?")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Msg msg = msgs.get(nowPosition);
									msgMgr.delMsgByMsgId(msg.getId());
									refresh();
									changedFlag = true;
									CommonUtil.Toast(context, "ɾ����Ϣ�ɹ�!");
								}
							}).setNegativeButton("ȡ��", null).create().show();
			break;
		}
		return true;
	}

	/**
	 * ˢ�½���
	 */
	public void refresh() {
		msgs = new ArrayList<Msg>();
		if (nowThreadId == -1) {
			Contact contact = contactMgr.getContactById(this.getIntent()
					.getIntExtra("contactId", -1));
			List<String> list = telMgr
					.getTelNumbersByContactId(contact.getId());
			String address;
			if (list.size() > 0) {
				address = list.get(0);
			} else {
				address = "��";
			}
			Msg msg = new Msg(nowThreadId, 0, 0, address, contact.getName(), 0,
					0, "", "");
			msgs.add(msg);
		} else {
			msgs = msgMgr.getMsgByThreadId(nowThreadId);
			MsgItemsAdapter adapter = new MsgItemsAdapter();
			lsvItemsMsg.setAdapter(adapter);
		}
	}

	/**
	 * ��ʼ������
	 */
	private void initUI() {
		refresh();// ��ʾ��Ϣ�б�
		// ��ʾͷ�������
		if (!msgs.get(0).getPerson().equals("")) {
			txtMsgName.setText(msgs.get(0).getPerson());
			final List<Contact> list = contactMgr.getContactsByName(msgs.get(0)
					.getPerson());
			final Contact contact = list.get(0);// ��õ�ǰ����ϵ��
			byte[] image = contact.getImage();
			Bitmap bitmap = ImageTools.getBitmapFromByte(image);
			imgMsgPhoto.setImageBitmap(bitmap);
			imgMsgPhoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mPopupWindow == null) {
						popView = inflater.inflate(R.layout.popup_send_msgs,
								null);
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

					mPopupWindow.showAsDropDown(v, 35, -15);

					ImageButton imbCall = (ImageButton) popView
							.findViewById(R.id.imbCall);
					ImageButton imbMsg = (ImageButton) popView
							.findViewById(R.id.imbMsg);
					ImageButton imbEmail = (ImageButton) popView
							.findViewById(R.id.imbEmail);
					ImageButton imbEditContact = (ImageButton) popView
							.findViewById(R.id.imbEditContact);
					imbMsg.setVisibility(View.GONE);
					imbEditContact.setVisibility(View.VISIBLE);
					// ��绰
					imbCall.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {
							CommonUtil.dial(context, currentNumber);// ��绰
							mPopupWindow.dismiss();
						}
					});
					// �鿴��ϵ������
					imbEditContact
							.setOnClickListener(new View.OnClickListener() {

								public void onClick(View v) {
									Intent intent = new Intent(context,
											NewContactActivity.class);
									intent.putExtra("contactId",
											contact.getId());
									startActivityForResult(intent,
											REQUEST_MSG_EDIT_NEW_CONTACT);
									mPopupWindow.dismiss();
								}
							});
					// ���ʼ�
					imbEmail.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {

							CommonUtil.sendEmail(context, emailMgr
									.getEmailsByContactId(contact.getId()));// ���ʼ�
							mPopupWindow.dismiss();
						}
					});
				}
			});
			// ��ʾ�绰����
			List<String> telNumbers = telMgr.getTelNumbersByContactId(contact
					.getId());
			if (telNumbers.size() > 1) {
				imgPhoneChoiceLeft.setVisibility(View.VISIBLE);
				imgPhoneChoiceRight.setVisibility(View.VISIBLE);
				txtCurrentTel.setText(telNumbers.get(0));
				for (String str : telNumbers)
					if (!str.equals(msgs.get(0).getAddress())) {
						txtTempTel.setText(str);
						break;
					}
				currentNumber = ((TextView) vFlipper.getCurrentView())
						.getText().toString();// ��¼��ǰ��ʾ����
				for (String str : telNumbers)
					if (!str.equals(currentNumber)) {
						tempNumber = str;// ��¼�ڶ�������
						break;
					}
				vFlipper.setOnTouchListener(this);
			} else {
				imgPhoneChoiceLeft.setVisibility(View.GONE);
				imgPhoneChoiceRight.setVisibility(View.GONE);
				txtCurrentTel.setText(msgs.get(0).getAddress());
				currentNumber = msgs.get(0).getAddress();// ��¼��ǰ��ʾ����
			}
		} else {
			txtMsgName.setText(msgs.get(0).getAddress());// İ������ʾ�绰����
			// ��ʾ�绰����
			imgPhoneChoiceLeft.setVisibility(View.GONE);
			imgPhoneChoiceRight.setVisibility(View.GONE);
			txtCurrentTel.setText(msgs.get(0).getAddress());
			currentNumber = msgs.get(0).getAddress();// ��¼��ǰ��ʾ����
			// ��ʾͷ��
			imgMsgPhoto.setImageResource(R.drawable.no_contact_photo);
			imgMsgPhoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MsgEditActivity.this,
							NewContactActivity.class);
					intent.putExtra("tel", msgs.get(0).getAddress());
					startActivityForResult(intent, REQUEST_MSG_EDIT_NEW_CONTACT);
				}
			});
		}
	}

	/**
	 * �ص�ˢ�½���
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_MSG_EDIT_NEW_CONTACT
				&& resultCode == NewContactActivity.RESULT_BTN_SAVE) {
			initUI();
			changedFlag = true;
		}
	}

	/**
	 * onClickListener�¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbMsgCall:// ��绰
			CommonUtil.dial(context, currentNumber);
			break;
		case R.id.btnSendMsg:// ���Ͷ���
			msgContent = edtInputMsg.getText().toString().trim();
			if (msgContent.equals("")) {
				CommonUtil.Toast(context, "��Ϣ���ݲ���Ϊ��!");
				return;
			}
			if (currentNumber.equals("") || currentNumber.equals("��")) {
				CommonUtil.Toast(context, "�绰���벻��Ϊ��!");
				return;
			}
			Intent serviceIntent = new Intent(MsgEditActivity.this,
					SendMsgService.class);
			serviceIntent.putExtra("number", currentNumber);
			serviceIntent.putExtra("content", msgContent);
			startService(serviceIntent);// ���������ŵķ���

			CommonUtil.Toast(context, "�����С���");
			msgMgr.addMsg(currentNumber, msgContent, 2);// ����Ϣ���浽ϵͳ���ݿ�
			refresh();// ˢ���б�
			edtInputMsg.setText("");
			changedFlag = true;
			break;
		}
	}

	/**
	 * onTouchListener�¼�
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// ȡ�����һ���ʱ��ָ���µ�X����
			touchDownX = event.getX();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			// ȡ�����һ���ʱ��ָ�ɿ���X����
			touchUpX = event.getX();
			// �������ң���ǰһ��View
			if (touchUpX - touchDownX > 100) {
				// ����View�л��Ķ���
				vFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_in_left));
				vFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_out_right));
				// ��ʾ��һ��View
				vFlipper.showPrevious();
				// �ı䵱ǰ��ʾ����
				String str = null;
				str = currentNumber;
				currentNumber = tempNumber;
				tempNumber = str;
				// �������󣬿���һ��View
			} else if (touchDownX - touchUpX > 100) {
				// ����View�л��Ķ���
				// ����Androidû���ṩslide_out_left��slide_in_right�����Է���slide_in_left��slide_out_right��д��slide_out_left��slide_in_right
				vFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_in_right));
				vFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_out_left));
				// ��ʾǰһ��View
				vFlipper.showNext();
				// �ı䵱ǰ��ʾ����
				String str = null;
				str = currentNumber;
				currentNumber = tempNumber;
				tempNumber = str;
			}
			return true;
		}
		return false;
	}

	/**
	 * �Զ���Adapter,��ʾ��Ϣ�б�
	 * 
	 * @author dell
	 * 
	 */
	class MsgItemsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (nowThreadId == -1)
				return 0;
			else
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
			Msg msg = msgs.get(position);
			if (msg.getMsgMark() == 1)// ���յ�����Ϣ
				convertView = inflater.inflate(R.layout.vlist_receive_msgs,
						null);
			else
				convertView = inflater.inflate(R.layout.vlist_send_msgs, null);
			TextView txtDate = (TextView) convertView
					.findViewById(R.id.txtDate);
			TextView txtMsgContent = (TextView) convertView
					.findViewById(R.id.txtMsgContent);
			// ������Ϣ��ʱ��
			String date = CommonUtil.getNowDate();// �õ���ǰϵͳ����
			if (msg.getMsgDate().substring(0, 10).equals(date))
				txtDate.setText(msg.getMsgDate().substring(11));
			else
				txtDate.setText(msg.getMsgDate().substring(0, 10));
			// ������Ϣ����
			txtMsgContent.setText(msg.getContent());
			return convertView;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && (changedFlag || flag))
			setResult(RESULT_MSG_CHANGED);
		return super.onKeyDown(keyCode, event);
	}

}
