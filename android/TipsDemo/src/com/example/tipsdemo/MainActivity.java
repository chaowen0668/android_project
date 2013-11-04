package com.example.tipsdemo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private static TipsToast tipsToast;
	private LoadingDialog dialog;
	private Handler mHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			dialog = new LoadingDialog(this);
			dialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(1);
				}
			}).start();
			break;
		case R.id.btn2:
			showTips(R.drawable.tips_warning, R.string.hello_world);
			break;
		case R.id.btn3:
			showTips(R.drawable.tips_success, R.string.hello_world);
			break;
		case R.id.btn4:
			showTips(R.drawable.tips_error, R.string.hello_world);
			break;
		case R.id.btn5:
			showTips(R.drawable.tips_smile, R.string.hello_world);
			break;
		}
	}

	private void showTips(int iconResId, int msgResId) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = TipsToast.makeText(getApplication().getBaseContext(), msgResId, TipsToast.LENGTH_SHORT);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(msgResId);
	}
}