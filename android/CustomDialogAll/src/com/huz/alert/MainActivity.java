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
				String s = "abc\n12345\n����ABC\nabc123\n����abc123\nabc\n12345\n����ABC\nabc123\n����abc123";
				String svs[] = s.split("\n");
				switch (v.getId()) {
				case R.id.button1:
					bd.setTitle("�Զ���Ի���");
					bd.setMessage("Huz alert");
					bd.setPositiveButton("��", null);
					bd.setNeutralButton("��", null);
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button2:
					bd.setTitle("�Զ���Ի���");
					final EditText edittext_Msg = new EditText(
							MainActivity.this);
					edittext_Msg.setGravity(Gravity.TOP);
					edittext_Msg.setLines(8);
					bd.setView(edittext_Msg);
					bd.setPositiveButton("ȷ��", null);
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button3:
					bd.setTitle("�Զ���Ի���");
					bd.setItems(svs, null);
					bd.setPositiveButton("��", null);
					bd.setNeutralButton("��", null);
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button4:
					bd.setTitle("�Զ���Ի���");
					bd.setSingleChoiceItems(svs, 2, null);
					bd.setPositiveButton("��", null);
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button5:
					bd.setTitle("�Զ���Ի���");
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button6:
					bd.setMessage("Huz alert");
					bd.setPositiveButton("��", null);
					bd.setNeutralButton("��", null);
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button7:
					bd.setTitle("�Զ���Ի���");
					boolean[] cks = new boolean[svs.length];
					cks[0] = true;
					bd.setMultiChoiceItems(svs, cks, null);
					bd.setPositiveButton("��", null);
					bd.setNegativeButton("ȡ��", null);
					bd.show();
					break;
				case R.id.button8:
					bd.setTitle("�Զ���Ի���");
					bd.setMessage("Huz alert");
					bd.show();
					break;
				case R.id.button9:
					bd.setTitle("�Զ���Ի���");
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
