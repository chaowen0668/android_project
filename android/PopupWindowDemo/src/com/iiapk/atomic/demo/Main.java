package com.iiapk.atomic.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 *  
 * @author Atomic
 */
public class Main extends Activity {
	PopupWindow mPop;
	RelativeLayout rlRoot;

	private void initPopMenu() {
		if (mPop == null) {
			mPop = new PopupWindow(getLayoutInflater().inflate(R.layout.pop, null),
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		if (mPop.isShowing()) {
			mPop.dismiss();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
	}

	public void btn1$Click(View v) {
		initPopMenu();
		mPop.showAsDropDown(v);
	}

	public void btn2$Click(View v) {
		initPopMenu();
		mPop.showAsDropDown(v, 50, -30);
	}

	public void btn3$Click(View v) {
		initPopMenu();
		mPop.showAtLocation(rlRoot, Gravity.CENTER, 0, 0);

	}

	public void btn5$Click(View v) {
		initPopMenu();
		mPop.showAtLocation(rlRoot, Gravity.TOP | Gravity.LEFT, 20, 20);
	}

	public void btn6$Click(View v) {
		initPopMenu();

		// �㷨���� ��Ļˮƽ����|��Ļ�ײ�Ϊ������ ,����ƫ��"�ر�"��ť�ĸ߶�

		mPop.showAtLocation(rlRoot, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, v.getHeight());
	}

	public void btn4$Click(View v) {
		if (mPop != null) {
			mPop.dismiss();
		}
	}

}