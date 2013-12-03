package com.example.customdialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
    private Button myButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myButton = (Button)this.findViewById(R.id.showdialog);
		myButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
	    switch (v.getId()) {
		case R.id.showdialog:
			  CustomDialog dialog=new CustomDialog(this, R.style.customDialog, R.layout.customdialog);
	            dialog.show();
			break;

		default:
			break;
		}
	}

}
