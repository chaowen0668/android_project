package com.chaowen.myandroidcode.ui;
import com.chaowen.myandroidcode.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class PopWindowDemo extends Activity{
	private Button button;
	private PopupWindow popupWindow;
	private LinearLayout layout;
	private ListView listView;
	private String title[] = { "全部", "我的微博", "周边", "智能排版", "同学" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popwindowdemo);
		button = (Button) findViewById(R.id.tv_title);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button.getTop();
				int y = button.getBottom() * 3 / 2;
				int x = getWindowManager().getDefaultDisplay().getWidth() / 4;

				showPopupWindow(x, y);
			}
		});
	}

	public void showPopupWindow(int x, int y) {
		layout = (LinearLayout) LayoutInflater.from(PopWindowDemo.this).inflate(
				R.layout.dialog, null);
		listView = (ListView) layout.findViewById(R.id.lv_dialog);
		listView.setAdapter(new ArrayAdapter<String>(PopWindowDemo.this,
				R.layout.text, R.id.tv_text, title));

		popupWindow = new PopupWindow(PopWindowDemo.this);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow
				.setWidth(getWindowManager().getDefaultDisplay().getWidth() / 2);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);//LayoutParams，只能是FrameLayout ，要不会报异常。
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		// showAsDropDown会把里面的view作为参照物，所以要那满屏幕parent
		// popupWindow.showAsDropDown(findViewById(R.id.tv_title), x, 10);
		popupWindow.showAtLocation(findViewById(R.id.main), Gravity.LEFT
				| Gravity.TOP, x, y);//需要指定Gravity，默认情况是center.

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				button.setText(title[arg2]);
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}

}
