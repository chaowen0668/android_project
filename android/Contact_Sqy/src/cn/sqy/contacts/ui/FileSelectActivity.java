package cn.sqy.contacts.ui;

/* import���class */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sqy.contacts.R;
import cn.sqy.contacts.myview.FileAdapter;
import cn.sqy.contacts.tool.CommonUtil;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.content.DialogInterface.OnClickListener;

/**
 * Demo2��Դ����������
 * 
 * @author dell
 * 
 */
public class FileSelectActivity extends ListActivity {
	/*
	 * �������� items�������ʾ������ paths������ļ�·�� rootPath����ʼĿ¼
	 */
	private List<String> items = null;
	private List<String> paths = null;
	private String rootPath = "/";
	private TextView mPath;
	private String returnImagePath = null;

	public static final int RESULT_FILE_SELECTED = 1;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		/* ����main.xml Layout */

		setContentView(R.layout.activity_file_select);
		/* ��ʼ��mPath��������ʾĿǰ·�� */
		mPath = (TextView) findViewById(R.id.mPath);
		getFileDir(rootPath);
	}

	/* ȡ���ļ��ܹ���method */
	private void getFileDir(String filePath) {
		/* �趨Ŀǰ����·�� */
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();

		File f = new File(filePath);
		File[] files = f.listFiles();

		if (!filePath.equals(rootPath)) {
			/* ��һ���趨Ϊ[������Ŀ¼] */
			items.add("b1");
			paths.add(rootPath);
			/* �ڶ����趨Ϊ[���ײ�] */
			items.add("b2");
			paths.add(f.getParent());
		}
		/* �������ļ�����ArrayList�� */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}

		/* ʹ���Զ����MyAdapter�������ݴ���ListActivity */
		setListAdapter(new FileAdapter(this, items, paths));
	}

	/* �趨ListItem������ʱҪ���Ĳ��� */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		returnImagePath = paths.get(position);
		if (file.canRead()) {
			if (file.isDirectory()) {
				/* ������ļ��о�����getFileDir() */
				getFileDir(paths.get(position));
			} else {
				/* ������ļ�����fileHandle() */
				fileHandle(file);
			}
		} else {
			/* ����AlertDialog��ʾȨ�޲��� */
			new AlertDialog.Builder(this)
					.setTitle("Message")
					.setMessage("Ȩ�޲���!")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
		}
	}

	/* �����ļ���method */
	private void fileHandle(final File file) {
		/* �����ļ�ʱ��OnClickListener */
		OnClickListener listener1 = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					/* ѡ���itemΪ���ļ� */
					openFile(file);
				} else if (which == 1) {
					String type = getMIMEType(file);
					//CommonUtil.Toast(FileSelectActivity.this, type);
					if (type.equals("image/*")) {
						CommonUtil.Log("sqy", "openFile", returnImagePath, 'i');
						Intent data = new Intent();
						data.putExtra("path", returnImagePath);
						setResult(RESULT_FILE_SELECTED, data);
						finish();
					} else
						CommonUtil
								.Toast(FileSelectActivity.this, "ѡ����ļ�����ͼƬ!!");
				} else {
					/* ѡ���itemΪɾ���ļ� */
					new AlertDialog.Builder(FileSelectActivity.this)
							.setTitle("ע��!")
							.setMessage("ȷ��Ҫɾ���ļ���?")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											/* ɾ���ļ� */
											file.delete();
											getFileDir(file.getParent());
										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				}
			}
		};

		/* ѡ�񼸸��ļ�ʱ������Ҫ�����ļ���ListDialog */
		String[] menu = { "���ļ�", "��ȡͼƬ", "ɾ���ļ�" };
		new AlertDialog.Builder(FileSelectActivity.this).setTitle("��Ҫ����ô?")
				.setItems(menu, listener1)
				.setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	/* �ֻ����ļ���method */
	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		// ����getMIMEType()��ȡ��MimeType
		String type = getMIMEType(f);
		// �趨intent��file��MimeType
		intent.setDataAndType(Uri.fromFile(f), type);
		startActivity(intent);
	}

	/* �ж��ļ�MimeType��method */
	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		/* ȡ����չ�� */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();
		Log.i("sqy", end);

		/* ����չ�������;���MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equalsIgnoreCase("jpg") || end.equalsIgnoreCase("gif")
				|| end.equalsIgnoreCase("png") || end.equalsIgnoreCase("jpeg")
				|| end.equalsIgnoreCase("bmp")) {
			type = "image";
		} else {
			type = "*";
		}
		/* ����޷�ֱ�Ӵ򿪣��͵�������б���û�ѡ�� */
	  type += "/*";
		return type;
	}
}
