package cn.sqy.contacts.ui;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sqy.contacts.R;

public class MainActivity extends ActivityGroup {
	private GridView bottomMenu;// Ӧ�õĵײ��˵�
	private LinearLayout container;// ��Activity
	private Intent intent;

	/** gridViewBarͼƬ **/
	private int[] gridViewBar_image_array = { R.drawable.bg_tab_dial_normal,
			R.drawable.bg_tab_contact_normal, R.drawable.bg_tab_sms_normal,
			R.drawable.bg_tab_setting_normal };
	/** gridViewBar���� **/
	private String[] gridViewBar_name_array = { "�� ��", "��ϵ��", "�� Ϣ", "��������" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bottomMenu = (GridView) findViewById(R.id.main_gridViewBar);
		container = (LinearLayout) findViewById(R.id.llContainer);

		bottomMenu.setNumColumns(4);// ����ÿ������
		bottomMenu.setGravity(Gravity.CENTER);// ����λ�þ���
		bottomMenu.setVerticalSpacing(10);// ��ֱ���
		bottomMenu.setHorizontalSpacing(10);// ˮƽ���
		bottomMenu.setAdapter(new SimpleAdapter_1(this));// ���ò˵�Adapter

		switchActivity(1);// Ĭ�ϵ�һ�ν�����ϵ�˽���

		bottomMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ���õ�ǰѡ������»��߿ɼ�
				View view1 = parent.getChildAt(0).findViewById(R.id.select);
				View view2 = parent.getChildAt(1).findViewById(R.id.select);
				View view3 = parent.getChildAt(2).findViewById(R.id.select);
				View view4 = parent.getChildAt(3).findViewById(R.id.select);
				switch (position) {
				case 0:
					view1.setVisibility(View.VISIBLE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					break;
				case 1:
					view1.setVisibility(View.GONE);
					view2.setVisibility(View.VISIBLE);
					view3.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					break;
				case 2:
					view1.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.VISIBLE);
					view4.setVisibility(View.GONE);
					break;
				case 3:
					view1.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
					view4.setVisibility(View.VISIBLE);
					break;
				}
				// ת����Activity
				switchActivity(position);
			}
		});
	}

	/**
	 * Activity֮���ת��
	 * 
	 * @param position
	 *            ��ǰ�������
	 */
	private void switchActivity(int position) {
		container.removeAllViews();
		switch (position) {
		case 0:
			intent = new Intent(this, DialActivity.class);
			break;
		case 1:
			intent = new Intent(this, ContactActivity.class);
			break;
		case 2:
			intent = new Intent(this, MessageActivity.class);
			break;
		case 3:
			intent = new Intent(this, CenterActivity.class);
			break;
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// ��Activity�л�ʱ������״̬
		// Activity תΪ View
		Window subActivity = getLocalActivityManager().startActivity(
				"subActivity", intent);
		container.addView(subActivity.getDecorView(),
				new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT));

	}

	/**
	 * ����Activityת����,�ͷŲ˵���,ʹ���"Menu"ʱ������ʾ�˵�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			this.getLocalActivityManager().getCurrentActivity()
					.openOptionsMenu();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �Զ���������������GridView����������ײ��˵�����
	 * 
	 * @author dell
	 * 
	 */
	public class SimpleAdapter_1 extends BaseAdapter {

		private LayoutInflater inflater;

		public SimpleAdapter_1(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return gridViewBar_name_array.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = inflater.inflate(R.layout.item_menu, null);
			ImageView item_img = (ImageView) convertView
					.findViewById(R.id.item_image);
			TextView item_txt = (TextView) convertView
					.findViewById(R.id.item_text);
			View view = convertView.findViewById(R.id.select);

			item_img.setImageResource(gridViewBar_image_array[position]);
			item_txt.setText(gridViewBar_name_array[position]);
			if (position == 1)// Ĭ����������ʱ��ʾ"��ϵ�˽���"
				view.setVisibility(View.VISIBLE);

			return convertView;
		}
	}
}
