package com.chaowen.myandroidcode.compone;

import java.util.ArrayList;
import java.util.List;

import com.chaowen.myandroidcode.BaseActivity;
import com.chaowen.myandroidcode.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Compone extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
        initActionBar();
		ListView list = new ListView(Compone.this);
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
		setContentView(list);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0: //自定义Toast
					Intent intent = new Intent(Compone.this,CustomToast.class);
					startActivity(intent);
					break;

				default:
					break;
				}

			}
		});
	}

	private void initActionBar() {
		//隐藏ActionBar Title  false: 隐藏 
  		getSupportActionBar().setDisplayShowTitleEnabled(true);
  		//隐藏ActionBar Icon
  		getSupportActionBar().setDisplayShowHomeEnabled(true);
     	// Create Actionbar Tabs
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
  		
         getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_tab_bg));
  		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}  

	private List<String> getData(){

		List<String> data = new ArrayList<String>();
		data.add("自定义Toast");

		return data;
	}
}
