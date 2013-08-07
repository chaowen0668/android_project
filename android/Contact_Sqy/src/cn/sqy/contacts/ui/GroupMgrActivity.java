package cn.sqy.contacts.ui;

import java.util.ArrayList;
import java.util.List;

import cn.sqy.contacts.R;
import cn.sqy.contacts.biz.ContactManager;
import cn.sqy.contacts.biz.GroupManager;
import cn.sqy.contacts.model.Contact;
import cn.sqy.contacts.model.Group;
import cn.sqy.contacts.tool.CommonUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class GroupMgrActivity extends Activity implements OnClickListener {
	private ImageButton imbAddGroup, imbBack;// ��ӷ��顢���ذ�ť
	private ListView lsvGroupMgr;// �����ListView
	private Context context;// ��ǰ�����Ķ���
	private GroupManager groupMgr;
	private ContactManager contactMgr;
	private List<Contact> contacts;// ���յ�ǰָ���������ϵ��
	private List<Group> groups;// �������з���
	private AlertDialog.Builder builder;// Dialog������
	private LayoutInflater inflater;// XML����
	private AlertDialog dialog;// Dialog
	private int nowGroupId;// ��¼���ڲ����ķ���

	public static final int REQUEST_TXTADDCONTACTS = 1;
	public static final int REQUEST_TXTREMOVECONTACTS = 2;
	public static final int RESULT_GROUPMGRACTIVITY_BACK = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_mgr);

		init();// ��ʼ��

		MyAdapter adapter = new MyAdapter(context);
		lsvGroupMgr.setAdapter(adapter);

		imbAddGroup.setOnClickListener(this);// ���һ���µķ���
		imbBack.setOnClickListener(this);// ����
		// GroupItem�ĵ�������¼�
		lsvGroupMgr
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						nowGroupId = (int) id;
						View dialogViewItem = inflater.inflate(
								R.layout.dialog_group_item_click, null);
						TextView txtDialogTitle = (TextView) dialogViewItem
								.findViewById(R.id.txtDialogTitle);
						TextView txtAddContacts = (TextView) dialogViewItem
								.findViewById(R.id.txtAddContacts);
						TextView txtRemoveContacts = (TextView) dialogViewItem
								.findViewById(R.id.txtRemoveContacts);
						TextView txtEditGroupName = (TextView) dialogViewItem
								.findViewById(R.id.txtEditGroupName);
						CommonUtil.Log("sqy",
								"lsvGroupMgr.setOnItemClickListener",
								groups.get(position) + ",id=" + id, 'i');
						txtDialogTitle.setText(groupMgr
								.getGroupById(nowGroupId).getGroupName());
						builder = new AlertDialog.Builder(context);
						dialog = builder.setView(dialogViewItem).create();
						dialog.show();
						txtAddContacts
								.setOnClickListener(GroupMgrActivity.this);
						txtRemoveContacts
								.setOnClickListener(GroupMgrActivity.this);
						txtEditGroupName
								.setOnClickListener(GroupMgrActivity.this);
					}
				});

	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		this.context = this;
		groupMgr = new GroupManager(context);
		contactMgr = new ContactManager(context);
		groups = new ArrayList<Group>();
		groups = groupMgr.getAllGroups();
		imbAddGroup = (ImageButton) findViewById(R.id.imbAddGroup);
		imbBack = (ImageButton) findViewById(R.id.imb_groupmgr_back);
		lsvGroupMgr = (ListView) findViewById(R.id.lsvGroupMgr);
		builder = new AlertDialog.Builder(context);
		inflater = LayoutInflater.from(context);
	}

	/**
	 * ˢ��ListView
	 */
	public void refleshLsvGroupMgr() {
		groups = groupMgr.getAllGroups();
		MyAdapter adapter = new MyAdapter(context);
		lsvGroupMgr.setAdapter(adapter);
	}

	/**
	 * OnClickListener�ӿڵķ���
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbAddGroup:// ��ӷ���
			showMyDialog(R.id.imbAddGroup, null);
			break;

		case R.id.imb_groupmgr_back:// ����
			setResult(RESULT_GROUPMGRACTIVITY_BACK);
			finish();
			break;
		case R.id.txtAddContacts:// �������ӳ�Ա
			Intent intent1 = new Intent(context,
					AddRemoveContactToGroupActivity.class);
			intent1.putExtra("groupId", nowGroupId);
			intent1.putExtra("isAdd", true);
			startActivityForResult(intent1, REQUEST_TXTADDCONTACTS);
			dialog.dismiss();
			break;
		case R.id.txtRemoveContacts:// �Ƴ������Ա
			Intent intent2 = new Intent(context,
					AddRemoveContactToGroupActivity.class);
			intent2.putExtra("groupId", nowGroupId);
			intent2.putExtra("isAdd", false);
			startActivityForResult(intent2, REQUEST_TXTREMOVECONTACTS);
			dialog.dismiss();
			break;
		case R.id.txtEditGroupName:// ����������
			AlertDialog dialog1 = dialog;
			showMyDialog(R.id.txtEditGroupName,
					groupMgr.getGroupById(nowGroupId));
			CommonUtil.Log("sqy", "�޸ķ�������=>nowGroupId:", nowGroupId + "", 'i');
			dialog1.dismiss();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		refleshLsvGroupMgr();
	}

	/**
	 * ��ʾһ���Ի���
	 * 
	 * @param viewId
	 *            ����Ŀؼ�ID��
	 */
	public void showMyDialog(int viewId, final Group group) {

		View dialog_view = inflater.inflate(R.layout.dialog_add_group, null);
		final EditText edtNewGroupName = (EditText) dialog_view
				.findViewById(R.id.edtNewGroupName);
		TextView txtDialogTitleName = (TextView) dialog_view
				.findViewById(R.id.txtNewGroupTitle);
		TextView txtMsgAlert = (TextView) dialog_view
				.findViewById(R.id.txtMsgAlert);
		Button btnSure = (Button) dialog_view.findViewById(R.id.btnAddNewGroup);
		Button btnCancel = (Button) dialog_view
				.findViewById(R.id.btnGroupCancel);

		switch (viewId) {
		// ɾ��һ������ʱ��ʾ�ĶԻ���
		case R.id.imbDelGroup:
			txtDialogTitleName.setText("��ܰ��ʾ");
			txtMsgAlert.setVisibility(View.VISIBLE);
			txtMsgAlert.setText("ȷ�Ͻ�ɢ����'" + group.getGroupName() + "'?");
			edtNewGroupName.setVisibility(View.GONE);
			builder = new AlertDialog.Builder(context);
			dialog = builder.setView(dialog_view).create();
			dialog.show();
			btnSure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					contacts.clear();
					contacts = contactMgr.getContactsByGroupId(group
							.getGroupId());
					// �Ȱ���ϵ�˷���ID��Ϊ-1(δ����)
					for (Contact contact : contacts) {
						contact.setGroupId(0);
						contactMgr.modifyContact(contact);
					}
					// Ȼ����ɾ������
					groupMgr.delGroupById(group.getGroupId());
					CommonUtil.Log("sqy", "ɾ����groupId = ", group.getGroupId()
							+ "", 'i');
					refleshLsvGroupMgr();
					dialog.dismiss();
				}
			});
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			break;

		// ���һ������ʱ��ʾ�ĶԻ���
		case R.id.imbAddGroup:
			txtDialogTitleName.setText("�½�����");
			txtMsgAlert.setVisibility(View.GONE);
			edtNewGroupName.setVisibility(View.VISIBLE);
			edtNewGroupName.setHint("������������ƣ�");
			builder = new AlertDialog.Builder(context);
			dialog = builder.setView(dialog_view).create();
			dialog.show();
			btnSure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtil.Log("sqy", "GroupMgrActivity:R.id.imbAddGroup",
							"btnSure", 'i');
					String newGroupName = edtNewGroupName.getText().toString();
					if (newGroupName.equals("")) {
						CommonUtil.Toast(context, "����ʧ��,����������Ϊ��!");
						dialog.dismiss();
					} else if (newGroupName.equals("ȫ����ϵ��")
							|| newGroupName.equals("δ����")) {
						CommonUtil.Toast(context, "����ʧ��,�÷�����Ϊϵͳ����!");
						dialog.dismiss();
					} else {
						List<String> groupNames = new ArrayList<String>();
						groupNames = groupMgr.getAllGroupName();
						for (String groupName : groupNames) {
							if (groupName.equals(newGroupName)) {
								CommonUtil.Toast(context, "����ʧ��,�÷����Ѿ�����!");
								dialog.dismiss();
								return;
							}
						}
						groupMgr.addGroup(newGroupName);// �����ݿ�����½��ķ���
						CommonUtil.Toast(context, "��ӷ���ɹ�!");
						// ��ȡ�½������ID��
						nowGroupId = groupMgr.getGroupsByName(newGroupName)
								.getGroupId();
						Intent intent3 = new Intent(context,
								AddRemoveContactToGroupActivity.class);
						intent3.putExtra("groupId", nowGroupId);
						intent3.putExtra("isAdd", true);
						startActivityForResult(intent3, REQUEST_TXTADDCONTACTS);
						dialog.dismiss();
						//refleshLsvGroupMgr();
					}
				}
			});
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtil.Log("sqy", "GroupMgrActivity", "btnCancel", 'i');
					dialog.dismiss();
				}
			});
			break;
		case R.id.txtEditGroupName:// ����������
			txtDialogTitleName.setText("�༭����");
			txtMsgAlert.setVisibility(View.GONE);
			edtNewGroupName.setVisibility(View.VISIBLE);
			edtNewGroupName.setHint("�������µķ������ƣ�");
			edtNewGroupName.setText(group.getGroupName());
			builder = new AlertDialog.Builder(context);
			dialog = builder.setView(dialog_view).create();
			dialog.show();
			btnSure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtil.Log("sqy",
							"GroupMgrActivity:R.id.txtEditGroupName",
							"btnSure", 'i');
					String newGroupName = edtNewGroupName.getText().toString();
					if (newGroupName.equals("")) {
						CommonUtil.Toast(context, "�޸�ʧ��,����������Ϊ��!");
						dialog.dismiss();
						return;
					} else {
						List<String> groupNames = new ArrayList<String>();
						groupNames = groupMgr.getAllGroupName();
						for (String groupName : groupNames) {
							if (groupName.equals(newGroupName)) {
								CommonUtil.Toast(context, "�޸�ʧ��,�÷����Ѿ�����!");
								dialog.dismiss();
								return;
							}
						}
						group.setGroupName(newGroupName);
						groupMgr.modifyGroup(group);
						CommonUtil.Toast(context, "�޸ķ���ɹ�!");
						dialog.dismiss();
						refleshLsvGroupMgr();
						CommonUtil.Log("sqy", "�޸ķ�������=>nowGroupName:",
								group.getGroupName(), 'i');
					}
				}
			});
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtil.Log("sqy", "GroupMgrActivity", "btnCancel", 'i');
					dialog.dismiss();
				}
			});
			break;
		}
	}

	/**
	 * ����ϵͳ�ķ��ؼ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_GROUPMGRACTIVITY_BACK);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �Զ���Adapter
	 * 
	 * @author dell
	 * 
	 */
	public class MyAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return groups.size();
		}

		@Override
		public Object getItem(int position) {
			return groups.get(position);
		}

		@Override
		public long getItemId(int position) {
			return groups.get(position).getGroupId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = inflater.inflate(R.layout.vlist_group_mgr, null);
			TextView txtGroupName = (TextView) convertView
					.findViewById(R.id.txtGroupName);
			TextView txtGroupCount = (TextView) convertView
					.findViewById(R.id.txtContactCount);
			ImageButton imbDelGroup = (ImageButton) convertView
					.findViewById(R.id.imbDelGroup);

			final Group group = groups.get(position);
			contacts = contactMgr.getContactsByGroupId(group.getGroupId());
			CommonUtil.Log("sqy", "GroupMgrActivity:groupId = ",
					group.getGroupId() + "", 'i');

			txtGroupName.setText(group.getGroupName());
			txtGroupCount.setText(contacts.size() + "λ��ϵ��");
			imbDelGroup.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					showMyDialog(R.id.imbDelGroup, group);
				}
			});

			return convertView;
		}
	}

}
