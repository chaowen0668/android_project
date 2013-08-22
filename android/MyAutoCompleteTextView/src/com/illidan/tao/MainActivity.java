package com.illidan.tao;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	MyAutoCompleteTextView myedit = null;
	ArrayList<String> data = null;
	RelativeLayout mianLayout = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myedit = (MyAutoCompleteTextView) findViewById(R.id.editText1);
		mianLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		
		data = new ArrayList<String>();
		data.add("10086");
		data.add("12345上山打老虎");
		data.add("1+1=2");
		data.add("10000");
		data.add("1234567");
		data.add("123哥想跳槽( ⊙ o ⊙ )嘛蛋");
		
		myedit.setFatherRelativeLayouyt(mianLayout);
		myedit.setMemoryData(data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(myedit.isListShowing()){
				myedit.removeTheShowView();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
