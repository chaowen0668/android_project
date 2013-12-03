package com.huz.alert;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initControls();
	}

	public Builder createAlertDlgBuilder() {
		RadioButton rdb = (RadioButton) this.findViewById(R.id.radio0);
		if (rdb.isChecked())
			return new AlertDialog.Builder(this);
		else{
			rdb = (RadioButton) this.findViewById(R.id.radio1);
			if (rdb.isChecked())
				return new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyTheme));
			else
				return new HuzAlertDialog.Builder(this);
		}
	}

	private void initControls() {
		Button btn;
		OnClickListener ls = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder bd = createAlertDlgBuilder();
				String s = "abc\n12345\n中文ABC\nabc123\n中文abc123\nabc\n12345\n中文ABC\nabc123\n中文abc123";
				String svs[] = s.split("\n");
				switch (v.getId()) {
				case R.id.button1:
					bd.setTitle("自定义对话框");
					bd.setMessage("Huz alert");
					bd.setPositiveButton("是", null);
					bd.setNeutralButton("否", null);
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button2:
					bd.setTitle("自定义对话框");
					final EditText edittext_Msg = new EditText(
							MainActivity.this);
					edittext_Msg.setGravity(Gravity.TOP);
					edittext_Msg.setLines(8);
					bd.setView(edittext_Msg);
					bd.setPositiveButton("确定", null);
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button3:
					bd.setTitle("自定义对话框");
					bd.setItems(svs, null);
					bd.setPositiveButton("是", null);
					bd.setNeutralButton("否", null);
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button4:
					bd.setTitle("自定义对话框");
					bd.setSingleChoiceItems(svs, 2, null);
					bd.setPositiveButton("是", null);
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button5:
					bd.setTitle("自定义对话框");
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button6:
					bd.setMessage("Huz alert");
					bd.setPositiveButton("是", null);
					bd.setNeutralButton("否", null);
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button7:
					bd.setTitle("自定义对话框");
					boolean[] cks = new boolean[svs.length];
					cks[0] = true;
					bd.setMultiChoiceItems(svs, cks, null);
					bd.setPositiveButton("是", null);
					bd.setNegativeButton("取消", null);
					bd.show();
					break;
				case R.id.button8:
					bd.setTitle("自定义对话框");
					bd.setMessage("Huz alert");
					bd.show();
					break;
				case R.id.button9:
					bd.setTitle("自定义对话框");
					bd.setItems(svs, null);
					bd.show();
					break;
				}
			}

		};

		btn = (Button) this.findViewById(R.id.button1);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button2);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button3);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button4);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button5);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button6);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button7);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button8);
		btn.setOnClickListener(ls);

		btn = (Button) this.findViewById(R.id.button9);
		btn.setOnClickListener(ls);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;

	}

}
