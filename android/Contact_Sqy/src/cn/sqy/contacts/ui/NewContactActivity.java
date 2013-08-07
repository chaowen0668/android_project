package cn.sqy.contacts.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import cn.sqy.contacts.R;
import cn.sqy.contacts.biz.ContactManager;
import cn.sqy.contacts.biz.EmailManager;
import cn.sqy.contacts.biz.GroupManager;
import cn.sqy.contacts.biz.IMManager;
import cn.sqy.contacts.biz.TelManager;
import cn.sqy.contacts.model.Contact;
import cn.sqy.contacts.model.Email;
import cn.sqy.contacts.model.IM;
import cn.sqy.contacts.model.Tel;
import cn.sqy.contacts.tool.CommonUtil;
import cn.sqy.contacts.tool.ImageTools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewContactActivity extends Activity implements OnClickListener {
	private RelativeLayout rlTelTitle, rlEmailTitle, rlIMTitle;// 电话、邮箱、IM的标题栏
	private LinearLayout llSelectGroup;// 分组选择栏
	private RelativeLayout rlAddAddress, rlAddCompany, rlAddNickname,// 添加"其它"栏的属性
			rlAddBirthday, rlAddNote;
	private ImageButton imbBack;// 返回按钮
	private EditText edtName;// 姓名栏
	private Button btnAddMore, btnSave;// 添加更多属性、保存按钮
	private int telCount = 0, emailCount = 0, imCount = 0;// 记录"电话、邮箱、IM"编辑框数量
	private Context context;// 当前上下文对象
	private LayoutInflater inflater;// 反射
	private LinearLayout llTel, llEmail, llIM;// 电话、邮箱、IM的父容器
	private AlertDialog.Builder builder;// 对话框创建器
	private AlertDialog dialog;// 对话框
	private String[] mItemsGroupName;// 所有分组名的数组
	private int nowContactId;// 点击联系人传递过来的联系人ID号
	private String newTelNumber;// 信息界面点击新建联系人过来的电话号码
	private Contact nowContact = null;// 点击联系人过来的实体
	private TextView txtActivityTitle;// Activity标题
	private File mCurrentPhotoFile;
	private Bitmap cameraBitmap;
	private boolean flagBtnImage = false;// 标记是否改变头像
	private byte[] oldImage;// 保存点击过来的头像
	private static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/DCIM/Camera");
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
			if (nowContactId > 0)
				CommonUtil.Toast(context, "修改联系人成功");
			else
				CommonUtil.Toast(context, "联系人添加成功");
			setResult(RESULT_BTN_SAVE);
			finish();
		}

	};
	// 日期
	private String setDate;
	private Calendar calendar;
	private int mYear, mMonth, mDay;
	private DatePickerDialog datePickerDialog;
	// Manger
	private ContactManager contactMgr;
	private GroupManager groupMgr;
	private TelManager telMgr;
	private EmailManager emailMgr;
	private IMManager imMgr;
	// 头像选择
	private ImageView imgPickPhoto;
	private View popView;
	private PopupWindow popupWindow;
	private Gallery gallery;
	private int currentImagePosition;// 用于记录当前选中图像在图像数组中的位置，默认值为0

	public static final int RESULT_BTN_SAVE = 0;
	public static final int REQUEST_BTN_FILE_SELECT = 2;

	/** 手机类别 **/
	public final String[] mItemsTel = { "家庭电话", "手机", "工作电话", "工作传真", "家庭传真",
			"寻呼机", "其它电话", "自定义电话", "回拨电话", "车载电话", "公司电话", "数字电话", "主要电话",
			"其他传真", "无线电话", "电报", "TTY/TDD", "工作手机", "工作寻呼", "次要电话", "彩信电话" };
	/** 邮箱类别 **/
	public final String[] mItemsEmail = { "个人邮箱", "工作邮箱", "其它邮箱", "手机邮箱",
			"自定义邮箱" };
	/** IM类别 **/
	public final String[] mItemsIM = { "QQ", "MSN", "AIM", "WINDOWS LIVE",
			"YAHOO", "SKYPE", "GTALK", "自定义" };

	/** 系统头像资源 **/
	private Integer[] mImageIds = { R.drawable.icon, R.drawable.photo1,
			R.drawable.photo2, R.drawable.photo3, R.drawable.photo4,
			R.drawable.photo5, R.drawable.photo6, R.drawable.photo7,
			R.drawable.photo8, R.drawable.photo9, R.drawable.photo10,
			R.drawable.photo11, R.drawable.photo12, R.drawable.photo13,
			R.drawable.photo14, R.drawable.photo15, R.drawable.photo16,
			R.drawable.photo17, R.drawable.photo18, R.drawable.photo19,
			R.drawable.photo20, R.drawable.photo21, R.drawable.photo22,
			R.drawable.photo23, R.drawable.photo24, R.drawable.photo25,
			R.drawable.photo26, R.drawable.photo27, R.drawable.photo28,
			R.drawable.photo29, R.drawable.photo30, R.drawable.photo31,
			R.drawable.photo32, R.drawable.photo33, R.drawable.photo34 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contacts);

		init();// 初始化

		// 添加更多联系人属性
		btnAddMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = inflater.inflate(
						R.layout.dialog_newcontact_addmore, null);
				TextView txtAddTel = (TextView) view
						.findViewById(R.id.txtAddTel);
				TextView txtAddEmail = (TextView) view
						.findViewById(R.id.txtAddEmail);
				TextView txtAddIM = (TextView) view.findViewById(R.id.txtAddIM);
				TextView txtAddAddress = (TextView) view
						.findViewById(R.id.txtAddAdress);
				TextView txtAddCompany = (TextView) view
						.findViewById(R.id.txtAddCompany);
				TextView txtAddNickname = (TextView) view
						.findViewById(R.id.txtAddNickname);
				TextView txtAddBirthday = (TextView) view
						.findViewById(R.id.txtAddBirthday);
				TextView txtAddNote = (TextView) view
						.findViewById(R.id.txtAddNote);
				builder = new AlertDialog.Builder(context);
				dialog = builder.setView(view).create();
				dialog.show();
				txtAddTel.setOnClickListener(NewContactActivity.this);
				txtAddEmail.setOnClickListener(NewContactActivity.this);
				txtAddIM.setOnClickListener(NewContactActivity.this);
				txtAddAddress.setOnClickListener(NewContactActivity.this);
				txtAddCompany.setOnClickListener(NewContactActivity.this);
				txtAddNickname.setOnClickListener(NewContactActivity.this);
				txtAddBirthday.setOnClickListener(NewContactActivity.this);
				txtAddNote.setOnClickListener(NewContactActivity.this);
			}
		});
		rlAddAddress.setOnClickListener(this);// 住址栏监听
		rlAddBirthday.setOnClickListener(this);// 生日栏监听
		rlAddCompany.setOnClickListener(this);// 公司栏监听
		rlAddNickname.setOnClickListener(this);// 住址栏监听
		rlAddNote.setOnClickListener(this);// 住址栏监听
		llSelectGroup.setOnClickListener(this);// 分组选择栏监听
		imgPickPhoto.setOnClickListener(this);// 头像选择栏监听

		btnSave.setOnClickListener(onClickListener);// 保存按钮的监听

		// 返回按钮
		imbBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.context = this;
		nowContactId = this.getIntent().getIntExtra("contactId", -1);
		newTelNumber = this.getIntent().getStringExtra("tel");
		inflater = LayoutInflater.from(context);
		builder = new AlertDialog.Builder(context);
		rlTelTitle = (RelativeLayout) findViewById(R.id.rlPhoneTitle);
		rlEmailTitle = (RelativeLayout) findViewById(R.id.rlEmailTitle);
		rlIMTitle = (RelativeLayout) findViewById(R.id.rlIMTitle);
		llSelectGroup = (LinearLayout) findViewById(R.id.llgroup);
		rlAddAddress = (RelativeLayout) findViewById(R.id.rlAddress);
		rlAddBirthday = (RelativeLayout) findViewById(R.id.rlBirthday);
		rlAddCompany = (RelativeLayout) findViewById(R.id.rlCompany);
		rlAddNickname = (RelativeLayout) findViewById(R.id.rlNickname);
		rlAddNote = (RelativeLayout) findViewById(R.id.rlNote);
		imbBack = (ImageButton) findViewById(R.id.imb_new_back);
		edtName = (EditText) findViewById(R.id.edt_name);
		imgPickPhoto = (ImageView) findViewById(R.id.contact_facepic);
		btnAddMore = (Button) findViewById(R.id.btn_addmore);
		btnSave = (Button) findViewById(R.id.btn_new_save);
		llTel = (LinearLayout) findViewById(R.id.ll_rlPhone);
		llEmail = (LinearLayout) findViewById(R.id.ll_rlEmail);
		llIM = (LinearLayout) findViewById(R.id.ll_rlIM);
		txtActivityTitle = (TextView) findViewById(R.id.txtNewContactTitle);

		contactMgr = new ContactManager(context);
		groupMgr = new GroupManager(context);
		telMgr = new TelManager(context);
		emailMgr = new EmailManager(context);
		imMgr = new IMManager(context);
		// 获取所有分组名称并转化为数组
		mItemsGroupName = new String[groupMgr.getAllGroupName().size()];
		mItemsGroupName = (groupMgr.getAllGroupName()).toArray(mItemsGroupName);

		if (nowContactId == -1) {// 点击添加联系人过来的
			addViewToTel();
			addViewToEmail();
			rlIMTitle.setVisibility(View.GONE);
			llIM.setVisibility(View.GONE);
			txtActivityTitle.setText("新建联系人");
		} else {// 点击联系人过来的
			txtActivityTitle.setText("联系人详情");
			initUi();// 显示联系人详情
		}
	}

	/**
	 * 显示联系人详情界面
	 */
	public void initUi() {
		// 展示Contact的字段
		nowContact = contactMgr.getContactById(nowContactId);
		oldImage = nowContact.getImage();
		Bitmap bitmap = ImageTools.getBitmapFromByte(oldImage);
		imgPickPhoto.setImageBitmap(bitmap);
		edtName.setText(nowContact.getName());
		if (!nowContact.getNickName().equals("")) {
			AddNicknameView();
			((EditText) rlAddNickname.findViewById(R.id.edt_inputnickname))
					.setText(nowContact.getNickName());
		}
		if (!nowContact.getAddress().equals("")) {
			AddAddressView();
			((EditText) rlAddAddress.findViewById(R.id.edt_inputaddress))
					.setText(nowContact.getAddress());
		}
		if (!nowContact.getCompany().equals("")) {
			AddCompanyView();
			((EditText) rlAddCompany.findViewById(R.id.edt_inputcompany))
					.setText(nowContact.getCompany());
		}
		if (!nowContact.getBirthday().equals("")) {
			AddBirthdayView();
			((TextView) rlAddBirthday.findViewById(R.id.txt_inputbirthday))
					.setText(nowContact.getBirthday());
		}
		if (!nowContact.getNote().equals("")) {
			AddNoteView();
			((EditText) rlAddNote.findViewById(R.id.edt_inputnote))
					.setText(nowContact.getNote());
		}
		if (nowContact.getGroupId() == 0) {
			((TextView) llSelectGroup.findViewById(R.id.txt_groupname))
					.setText("无");
		} else {
			((TextView) llSelectGroup.findViewById(R.id.txt_groupname))
					.setText(groupMgr.getGroupById(nowContact.getGroupId())
							.getGroupName());
		}

		// 展示Tel的字段
		List<Tel> tels = telMgr.getTelsByContactId(nowContactId);
		if (tels.size() > 0) {
			rlTelTitle.setVisibility(View.VISIBLE);
			for (int i = 0; i < tels.size(); i++) {
				addViewToTel();
				View view = llTel.getChildAt(i);
				((EditText) view.findViewById(R.id.edt_inputphone))
						.setText(tels.get(i).getTelNumber());
				((TextView) view.findViewById(R.id.txt_phone)).setText(tels
						.get(i).getTelName());
			}
		} else
			rlTelTitle.setVisibility(View.GONE);

		// 展示Email的字段
		List<Email> emails = emailMgr.getEmailsByContactId(nowContactId);
		if (emails.size() > 0) {
			rlEmailTitle.setVisibility(View.VISIBLE);
			for (int i = 0; i < emails.size(); i++) {
				addViewToEmail();
				View view = llEmail.getChildAt(i);
				((EditText) view.findViewById(R.id.edt_inputemail))
						.setText(emails.get(i).getEmailAcount());
				((TextView) view.findViewById(R.id.txt_email)).setText(emails
						.get(i).getEmailName());
			}
		} else
			rlEmailTitle.setVisibility(View.GONE);

		// 展示IM的字段
		List<IM> ims = imMgr.getIMsByContactId(nowContactId);
		if (ims.size() > 0) {
			rlIMTitle.setVisibility(View.VISIBLE);
			for (int i = 0; i < ims.size(); i++) {
				addViewToIM();
				View view = llIM.getChildAt(i);
				((EditText) view.findViewById(R.id.edt_inputIm)).setText(ims
						.get(i).getImAcount());
				((TextView) view.findViewById(R.id.txt_im)).setText(ims.get(i)
						.getImName());
			}
		} else
			rlIMTitle.setVisibility(View.GONE);
	}

	/**
	 * 点击事件的响应
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击"添加更多属性"后的点击事件
		case R.id.txtAddTel:
			addViewToTel();// 添加一个电话栏
			dialog.dismiss();
			break;
		case R.id.txtAddEmail:// 添加一个邮箱栏
			addViewToEmail();
			dialog.dismiss();
			break;
		case R.id.txtAddIM:// 添加一个IM栏
			addViewToIM();
			dialog.dismiss();
			break;
		case R.id.txtAddAdress:// 添加地址栏
			AddAddressView();
			dialog.dismiss();
			break;
		case R.id.txtAddCompany:// 添加公司栏
			AddCompanyView();
			dialog.dismiss();
			break;
		case R.id.txtAddNickname:// 添加昵称栏
			AddNicknameView();
			dialog.dismiss();
			break;
		case R.id.txtAddBirthday:// 添加生日栏
			showDatePickerDialog();// 显示日期设置对话框
			AddBirthdayView();
			dialog.dismiss();
			break;
		case R.id.txtAddNote:// 添加备注栏
			AddNoteView();
			dialog.dismiss();
			break;
		// 点击"其它"的点击事件
		case R.id.llgroup:
			if (mItemsGroupName.length > 0) {
				builder = new AlertDialog.Builder(context);
				builder.setTitle("选择分组")
						.setItems(mItemsGroupName,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										((TextView) llSelectGroup
												.findViewById(R.id.txt_groupname))
												.setText(mItemsGroupName[which]);
									}
								}).create().show();
			} else
				CommonUtil.Toast(context, "当前无分组可选择!");
			break;
		// 头像选择
		case R.id.contact_facepic:
			if (popupWindow == null) {
				popView = inflater.inflate(R.layout.popup_photo_select, null);
				popupWindow = new PopupWindow(popView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				// 下面三个设置可以使PopupWindow中的项和外面的键都可以响应点击或触摸事件
				popupWindow.setFocusable(true);
				popupWindow.setTouchable(true);
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
			}
			TextView txtAddImageFromPhoto = (TextView) popView
					.findViewById(R.id.txtAddImageFromPhoto);
			TextView txtAddImageFromSystem = (TextView) popView
					.findViewById(R.id.txtAddImageFromSystem);
			TextView txtAddImageFromFile = (TextView) popView
					.findViewById(R.id.txtAddImageFromFile);
			popupWindow.showAsDropDown(imgPickPhoto, 70, -70);// 弹出选择头像的PopoupWindow
			txtAddImageFromPhoto.setOnClickListener(onClickListener1);// 监听从照相机获取头像
			txtAddImageFromSystem.setOnClickListener(onClickListener1);// 监听从系统获取头像
			txtAddImageFromFile.setOnClickListener(onClickListener1);// 监听从文件获取头像
			break;
		}
	}

	// 头像选择的监听事件
	private OnClickListener onClickListener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 从照相机获取头像
			case R.id.txtAddImageFromPhoto:
				try {
					// Launch camera to take photo for selected contact
					PHOTO_DIR.mkdirs();
					mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE,
							null);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(mCurrentPhotoFile));
					startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
				} catch (ActivityNotFoundException e) {
					CommonUtil.Toast(context, "not find photo");
				}
				popupWindow.dismiss();
				break;
			// 从系统获取头像
			case R.id.txtAddImageFromSystem:
				CommonUtil.Log("sqy", "onClickListener1",
						"txtAddImageFromSystem", 'i');
				View view = inflater.inflate(R.layout.gallery, null);
				builder = new AlertDialog.Builder(context);
				dialog = builder
						.setView(view)
						.setTitle("请选择头像")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										imgPickPhoto
												.setImageResource(mImageIds[currentImagePosition
														% mImageIds.length]);
										flagBtnImage = true;
									}
								}).setNegativeButton("取消", null).create();
				gallery = (Gallery) view.findViewById(R.id.img_gallery);
				gallery.setAdapter(new ImageAdapter(context));
				gallery.setSelection(10000);
				gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// 当前的头像位置为选中的位置,方便以后用到
						currentImagePosition = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
				dialog.show();
				popupWindow.dismiss();
				break;
			// 从文件获取头像
			case R.id.txtAddImageFromFile:
				CommonUtil.Log("sqy", "onClickListener1",
						"txtAddImageFromFile", 'i');
				Intent intent1 = new Intent(context, FileSelectActivity.class);
				startActivityForResult(intent1, REQUEST_BTN_FILE_SELECT);
				popupWindow.dismiss();
				break;
			}
		}
	};

	/**
	 * 创建图片名称
	 * 
	 * @return
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";

	}

	/***
	 * Constructs an intent for image cropping.
	 */
	public static Intent getCropImageIntent(Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/**");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 77);
		intent.putExtra("outputY", 77);
		intent.putExtra("return-data", true);
		return intent;
	}

	// 点击保存按钮的监听事件
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (edtName.getText().toString().equals("")) {
				CommonUtil.Toast(context, "姓名不能为空,保存联系人失败!");
				return;
			}
			View view = inflater.inflate(R.layout.progress_dialog, null);
			builder = new AlertDialog.Builder(context);
			dialog = builder.setView(view).create();
			dialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// 获取Contact的字段值,并插入数据库
					String name = edtName.getText().toString();
					// 将姓名转为首字母的拼音
					String namePinyin = "";
					HanyuPinyinCaseType caseType = HanyuPinyinCaseType.LOWERCASE;
					HanyuPinyinVCharType vcharType = HanyuPinyinVCharType.WITH_U_AND_COLON;
					HanyuPinyinToneType toneType = HanyuPinyinToneType.WITHOUT_TONE;
					HanyuPinyinOutputFormat output = new HanyuPinyinOutputFormat();
					output.setCaseType(caseType);
					output.setToneType(toneType);
					output.setVCharType(vcharType);
					String string = null;
					string = PinyinHelper
							.toHanyuPinyinString(name, output, "-");
					String[] strs = string.split("-");
					for (String str : strs)
						namePinyin = namePinyin + str.substring(0, 1);
					CommonUtil.Log("sqy", "toHanyuPinyinString", namePinyin,
							'i');

					String nickName = ((EditText) rlAddNickname
							.findViewById(R.id.edt_inputnickname)).getText()
							.toString();
					String address = ((EditText) rlAddAddress
							.findViewById(R.id.edt_inputaddress)).getText()
							.toString();
					String company = ((EditText) rlAddCompany
							.findViewById(R.id.edt_inputcompany)).getText()
							.toString();
					String birthday = ((TextView) rlAddBirthday
							.findViewById(R.id.txt_inputbirthday)).getText()
							.toString();
					if (birthday.equals("无"))
						birthday = "";
					String note = ((EditText) rlAddNote
							.findViewById(R.id.edt_inputnote)).getText()
							.toString();
					int groupId = 0;
					String groupName = ((TextView) llSelectGroup
							.findViewById(R.id.txt_groupname)).getText()
							.toString();
					if (groupName.equals("无"))
						groupId = 0;
					else
						groupId = groupMgr.getGroupsByName(groupName)
								.getGroupId();
					byte[] image;
					if (flagBtnImage || nowContactId == -1) {// 头像改变了
						Bitmap bitmap = ImageTools
								.getBitmapFromDrawable(imgPickPhoto
										.getDrawable());
						image = ImageTools.getByteFromBitmap(bitmap);
					} else {
						image = oldImage;
					}

					int id = 0;
					if (nowContactId == -1) {
						contactMgr.addContact(name, namePinyin, nickName,
								address, company, birthday, note, image,
								groupId);// 将联系人存入数据库
						// 获得插入联系人的ID号
						List<Contact> list = contactMgr.getContactsByName(name);
						if (list == null) {
							CommonUtil.Toast(context, "添加联系人失败!");
							return;
						}
						id = list.get((list.size() - 1)).getId();
					} else {
						contactMgr.modifyContact(nowContactId, name,
								namePinyin, nickName, address, company,
								birthday, note, image, groupId);// 修改联系人
					}

					// 获取Tel的字段值,并插入数据库
					if (nowContactId > 0)// 如果是修改联系人，则先删除所有电话号码
						telMgr.delTelByContactId(nowContactId);
					for (int i = 0; i < llTel.getChildCount(); i++) {
						View view = llTel.getChildAt(i);
						EditText edtTel = (EditText) view
								.findViewById(R.id.edt_inputphone);
						if (!edtTel.getText().toString().equals("")) {
							String telNumber = edtTel.getText().toString();
							String telName = ((TextView) view
									.findViewById(R.id.txt_phone)).getText()
									.toString();
							int contactId;
							if (nowContactId == -1)
								contactId = id;
							else
								contactId = nowContactId;
							telMgr.addTel(contactId, telName, telNumber);// 将Tel添加到数据库
						}
					}

					// 获取Email的字段值,并插入数据库
					if (nowContactId > 0)// 如果是修改联系人，则先删除所有电话号码
						emailMgr.delEmailByContactId(nowContactId);
					for (int i = 0; i < llEmail.getChildCount(); i++) {
						View view = llEmail.getChildAt(i);
						EditText edtEmail = (EditText) view
								.findViewById(R.id.edt_inputemail);
						if (!edtEmail.getText().toString().equals("")) {
							String emailAcount = edtEmail.getText().toString();
							String emailName = ((TextView) view
									.findViewById(R.id.txt_email)).getText()
									.toString();
							int contactId;
							if (nowContactId == -1)
								contactId = id;
							else
								contactId = nowContactId;
							emailMgr.addEmail(contactId, emailName, emailAcount);// 将Email添加到数据库
						}
					}

					// 获取IM的字段值,并插入数据库
					if (nowContactId > 0)
						imMgr.delIMByContactId(nowContactId);
					for (int i = 0; i < llIM.getChildCount(); i++) {
						View view = llIM.getChildAt(i);
						EditText edtIM = (EditText) view
								.findViewById(R.id.edt_inputIm);
						if (!edtIM.getText().toString().equals("")) {
							String imAcount = edtIM.getText().toString();
							String imName = ((TextView) view
									.findViewById(R.id.txt_im)).getText()
									.toString();
							int contactId;
							if (nowContactId == -1)
								contactId = id;
							else
								contactId = nowContactId;
							imMgr.addIM(contactId, imName, imAcount);// 将Email添加到数据库
						}
					}
					handler.sendMessage(new Message());
				}
			}).start();
		}
	};

	/**
	 * Activity回调函数
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap bitmap = null;
		// 文件返回的
		if (requestCode == REQUEST_BTN_FILE_SELECT
				&& resultCode == FileSelectActivity.RESULT_FILE_SELECTED) {
			String imagePath = data.getStringExtra("path");
			File file = new File(imagePath);
			CommonUtil.Log("sqy", "file.length()", file.length() + "", 'i');
			try {
				if (file.length() > 800000)// 压缩后转换为Bitmap
					bitmap = ImageTools.saveBefore(imagePath);
				else
					bitmap = ImageTools.getBitemapFromFile(file);
				CommonUtil.Log("sqy", "onActivityResult", imagePath, 'i');
				// 将图片转换为指定大小的图片,并显示在头像上
				imgPickPhoto.setImageBitmap(ImageTools.createBitmapBySize(
						bitmap, 77, 77));
				flagBtnImage = true;
			} catch (Exception ex) {
				CommonUtil.Toast(context, "图片过大，无法显示");
			}
		}
		// 照相机返回的
		if (requestCode == Activity.DEFAULT_KEYS_DIALER) {
			try {
				// Add the image to the media store
				Intent intentScan = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				sendBroadcast(intentScan);
				// Launch gallery to crop the photo
				final Intent intent = getCropImageIntent(Uri
						.fromFile(mCurrentPhotoFile));
				startActivityForResult(intent, 3);
			} catch (Exception e) {
				CommonUtil.Log("sqy", "Devdiv", "Cannot crop image", 'i');
				CommonUtil.Toast(context, "not find photo ");
			}
		}
		// 得到的裁剪后的photo的bitmap对象
		if (requestCode == 3)
			if (data != null) {
				cameraBitmap = data.getParcelableExtra("data");
				imgPickPhoto.setImageBitmap(cameraBitmap);
				flagBtnImage = true;
			}
	}

	/**
	 * 显示DatePickerDialog,用以选择联系人生日
	 */
	public void showDatePickerDialog() {
		calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		datePickerDialog = new DatePickerDialog(context,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						setDate = String.valueOf(year) + "-"
								+ String.valueOf(monthOfYear + 1) + "-"
								+ String.valueOf(dayOfMonth);
						((TextView) rlAddBirthday
								.findViewById(R.id.txt_inputbirthday))
								.setText(setDate);// 显示当前设置的时间
					}
				}, mYear, mMonth, mDay);
		datePickerDialog.show();
	}

	/**
	 * 添加地址栏
	 */
	public void AddAddressView() {
		rlAddAddress.setVisibility(View.VISIBLE);
		// 点击删除按钮监听事件
		rlAddAddress.findViewById(R.id.imb_del_address).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						rlAddAddress.setVisibility(View.GONE);
						((EditText) rlAddAddress
								.findViewById(R.id.edt_inputaddress))
								.setText("");
					}
				});
	}

	/**
	 * 添加公司栏
	 */
	public void AddCompanyView() {
		rlAddCompany.setVisibility(View.VISIBLE);
		// 点击删除按钮监听事件
		rlAddCompany.findViewById(R.id.imb_del_company).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						rlAddCompany.setVisibility(View.GONE);
						((EditText) rlAddCompany
								.findViewById(R.id.edt_inputcompany))
								.setText("");
					}
				});
	}

	/**
	 * 添加昵称栏
	 */
	public void AddNicknameView() {
		rlAddNickname.setVisibility(View.VISIBLE);
		// 点击删除按钮监听事件
		rlAddNickname.findViewById(R.id.imb_del_nickname).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						rlAddNickname.setVisibility(View.GONE);
						((EditText) rlAddNickname
								.findViewById(R.id.edt_inputnickname))
								.setText("");
					}
				});
	}

	/**
	 * 添加生日栏
	 */
	public void AddBirthdayView() {
		rlAddBirthday.setVisibility(View.VISIBLE);
		// 点击删除按钮监听事件
		rlAddBirthday.findViewById(R.id.imb_del_birthday).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						rlAddBirthday.setVisibility(View.GONE);
						((TextView) rlAddBirthday
								.findViewById(R.id.txt_inputbirthday))
								.setText("无");
					}
				});
		// 点击当前日期弹出对话框重新选择日期
		rlAddBirthday.findViewById(R.id.txt_inputbirthday).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						showDatePickerDialog();// 显示日期设置对话框
					}
				});
	}

	/**
	 * 添加备注栏
	 */
	public void AddNoteView() {
		rlAddNote.setVisibility(View.VISIBLE);
		// 点击删除按钮监听事件
		rlAddNote.findViewById(R.id.imb_del_note).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						rlAddNote.setVisibility(View.GONE);
						((EditText) rlAddNote.findViewById(R.id.edt_inputnote))
								.setText("");
					}
				});
	}

	/**
	 * 添加一个Tel编辑框
	 */
	public void addViewToTel() {
		telCount++;
		if (telCount == 1) {
			rlTelTitle.setVisibility(View.VISIBLE);
			llTel.setVisibility(View.VISIBLE);
		}
		final View view = inflater.inflate(R.layout.vlist_phone, null);
		llTel.addView(view);
		if (newTelNumber != null && !newTelNumber.equals("")) {
			EditText edtTel = (EditText) view.findViewById(R.id.edt_inputphone);
			edtTel.setText(newTelNumber);
			newTelNumber = "";
		}
		ImageButton imbDel = (ImageButton) view
				.findViewById(R.id.imb_del_phone);
		RelativeLayout rlSelectTel = (RelativeLayout) view
				.findViewById(R.id.phoneTitle);
		final TextView txtTel = (TextView) view.findViewById(R.id.txt_phone);
		imbDel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				llTel.removeView(view);
				telCount--;
				if (telCount == 0) {
					rlTelTitle.setVisibility(View.GONE);
					llTel.setVisibility(View.GONE);
				}
			}
		});
		rlSelectTel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder = new AlertDialog.Builder(context);
				builder.setTitle("请选择标签")
						.setItems(mItemsTel,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										txtTel.setText(mItemsTel[which]);
									}
								}).create().show();
			}
		});
	}

	/**
	 * 添加一个Email编辑框
	 */
	public void addViewToEmail() {
		emailCount++;
		if (emailCount == 1) {
			rlEmailTitle.setVisibility(View.VISIBLE);
			llEmail.setVisibility(View.VISIBLE);
		}
		final View view = inflater.inflate(R.layout.vlist_email, null);
		llEmail.addView(view);

		ImageButton imbDel = (ImageButton) view
				.findViewById(R.id.imb_del_email);
		RelativeLayout rlSelectEmail = (RelativeLayout) view
				.findViewById(R.id.emailTitle);
		final TextView txtEmail = (TextView) view.findViewById(R.id.txt_email);
		imbDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				llEmail.removeView(view);
				emailCount--;
				if (emailCount == 0) {
					rlEmailTitle.setVisibility(View.GONE);
					llEmail.setVisibility(View.GONE);
				}
			}
		});
		rlSelectEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder = new AlertDialog.Builder(context);
				builder.setTitle("请选择标签")
						.setItems(mItemsEmail,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										txtEmail.setText(mItemsEmail[which]);
									}
								}).create().show();
			}
		});
	}

	/**
	 * 添加一个IM编辑框
	 */
	public void addViewToIM() {
		imCount++;
		if (imCount == 1) {
			rlIMTitle.setVisibility(View.VISIBLE);
			llIM.setVisibility(View.VISIBLE);
		}
		final View view = inflater.inflate(R.layout.vlist_im, null);
		llIM.addView(view);

		CommonUtil.Log("sqy", "addViewToIM", "view的个数：" + llIM.getChildCount(),
				'i');

		ImageButton imbDel = (ImageButton) view.findViewById(R.id.imb_del_im);
		RelativeLayout rlSelectIM = (RelativeLayout) view
				.findViewById(R.id.imTitle);
		final TextView txtIM = (TextView) view.findViewById(R.id.txt_im);
		imbDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				llIM.removeView(view);
				imCount--;
				if (imCount == 0) {
					rlIMTitle.setVisibility(View.GONE);
					llIM.setVisibility(View.GONE);
				}
			}
		});
		rlSelectIM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder = new AlertDialog.Builder(context);
				builder.setTitle("请选择标签")
						.setItems(mItemsIM,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										txtIM.setText(mItemsIM[which]);
									}
								}).create().show();
			}
		});
	}

	/**
	 * image的布局adapter
	 */
	class ImageAdapter extends BaseAdapter {
		private Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			// return mImageIds.length;
			return 100000;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(mImageIds[position % mImageIds.length]);
			iv.setAdjustViewBounds(true);
			iv.setLayoutParams(new Gallery.LayoutParams(100, 100));// 设置Gallery中图片的大小
			iv.setPadding(15, 10, 15, 10);
			return iv;
		}

	}
}