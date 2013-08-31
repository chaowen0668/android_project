package cn.sqy.contacts.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openintents.convertcsv.opencsv.ConvertCSV;

import cn.sqy.contacts.R;
import cn.sqy.contacts.biz.FileManager;
import cn.sqy.contacts.biz.SimAndSysContactManager;
import cn.sqy.contacts.tool.CommonUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CenterActivity extends Activity {
	private Context context;
	private GridView gridViewCenter;
	private SimAndSysContactManager simAndSysContactMgr;
	private LayoutInflater inflater;
	private AlertDialog dialog;
	private FileManager flieMgr;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch ((Integer) msg.obj) {
			case -1:
				dialog.dismiss();
				CommonUtil.Toast(context, "����ʧ��!");
				break;
			case 0:
				dialog.dismiss();
				CommonUtil.Toast(context, "��ϵ�˱��ݳɹ�!");
				break;
			case 1:
				dialog.dismiss();
				CommonUtil.Toast(context, "��ϵ�˻ָ��ɹ�!");
				break;
			case 2:
				dialog.dismiss();
				CommonUtil.Toast(context, "SIM������ɹ�!");
				break;
			case 3:
				dialog.dismiss();
				CommonUtil.Toast(context, "��Ϣ���ݳɹ�!");
				break;
			case 4:
				dialog.dismiss();
				CommonUtil.Toast(context, "��Ϣ�ָ��ɹ�!");
				break;
			case 5:
				dialog.dismiss();
				CommonUtil.Toast(context, "ϵͳ��ϵ�˵���ɹ�!");
				break;
			}

		}
	};
	/** gridViewBarͼƬ **/
	private int[] gridView_image_array = { R.drawable.contact_backup,
			R.drawable.contact_restore, R.drawable.sim_load,
			R.drawable.msg_backup, R.drawable.msg_restore,
			R.drawable.syscontact_load };
	/** gridViewBar���� **/
	private String[] gridView_name_array = { "��ϵ�˱���", "�ָ���ϵ��", "SIM������",
			"��Ϣ����", "��Ϣ�ָ�", "ϵͳ��ϵ�˵���" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);

		init();

		gridViewCenter.setAdapter(getAdapter(gridView_name_array,
				gridView_image_array));
		gridViewCenter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:// ��ϵ�˱���
					View view0 = inflater.inflate(R.layout.progress_dialog,
							null);
					((TextView) view0.findViewById(R.id.progress_msg))
							.setText(" �� �� �� . . .");
					dialog = new AlertDialog.Builder(context).setView(view0)
							.create();
					dialog.show();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(600);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							 int flag = flieMgr.dbFileOperation("backup","contact");
							/*int flag = -1;
							try {
								String fileName = getSDPath()
										+ "/contactBackup.csv";
								ConvertCSV convertCSV = new ConvertCSV(context,
										fileName);
								convertCSV.startExport();
								flag = 0;
							} catch (Exception ex) {
								flag = -1;
							}*/
							Message msg = new Message();
							msg.obj = flag;
							handler.sendMessage(msg);
						}
					}).start();
					break;
				case 1:
					View view1 = inflater.inflate(R.layout.progress_dialog,
							null);
					((TextView) view1.findViewById(R.id.progress_msg))
							.setText(" �� �� �� . . .");
					dialog = new AlertDialog.Builder(context).setView(view1)
							.create();
					dialog.show();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(600);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							int flag = flieMgr.dbFileOperation("restore",
									"contact");
							Message msg = new Message();
							msg.obj = flag;
							handler.sendMessage(msg);
						}
					}).start();
					break;
				case 2:// SIM����ϵ�˵���
					View view2 = inflater.inflate(R.layout.progress_dialog,
							null);
					((TextView) view2.findViewById(R.id.progress_msg))
							.setText(" �� �� �� . . .");
					dialog = new AlertDialog.Builder(context).setView(view2)
							.create();
					dialog.show();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							simAndSysContactMgr
									.insertContactToMyContactFromSIM();
							Message msg = new Message();
							msg.obj = 2;
							handler.sendMessage(msg);
						}
					}).start();
					break;
				case 3:// ��Ϣ����
					View view3 = inflater.inflate(R.layout.progress_dialog,
							null);
					((TextView) view3.findViewById(R.id.progress_msg))
							.setText(" �� �� �� . . .");
					dialog = new AlertDialog.Builder(context).setView(view3)
							.create();
					dialog.show();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(600);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							int flag = flieMgr.dbFileOperation("backup",
									"message");
							Message msg = new Message();
							msg.obj = flag;
							handler.sendMessage(msg);
						}
					}).start();
					break;
				case 4:// ��Ϣ�ָ�
					View view4 = inflater.inflate(R.layout.progress_dialog,
							null);
					((TextView) view4.findViewById(R.id.progress_msg))
							.setText(" �� �� �� . . .");
					dialog = new AlertDialog.Builder(context).setView(view4)
							.create();
					dialog.show();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(600);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							int flag = flieMgr.dbFileOperation("restore",
									"message");
							Message msg = new Message();
							msg.obj = flag;
							handler.sendMessage(msg);
						}
					}).start();
					break;
				case 5:// ϵͳ��ϵ�˵���
					View view5 = inflater.inflate(R.layout.progress_dialog,
							null);
					((TextView) view5.findViewById(R.id.progress_msg))
							.setText(" �� �� �� . . .");
					dialog = new AlertDialog.Builder(context).setView(view5)
							.create();
					dialog.show();
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							simAndSysContactMgr
									.insertContactToMyContactFromSysContacts();
							Message msg = new Message();
							msg.obj = 5;
							handler.sendMessage(msg);
						}
					}).start();
				}
			}
		});
	}

	private void init() {
		context = this;
		flieMgr = new FileManager(context);
		gridViewCenter = (GridView) findViewById(R.id.gridViewCenter);
		simAndSysContactMgr = new SimAndSysContactManager(context);
		inflater = LayoutInflater.from(context);
	}

	/**
	 * ����SD���ľ���·��
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();
	}

	/**
	 * GridView��adapter
	 * 
	 * @param nameArray
	 * @param resourceArray
	 * @return
	 */
	private SimpleAdapter getAdapter(String[] nameArray, int[] resourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < nameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", resourceArray[i]);
			map.put("itemText", nameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu1, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}
}
