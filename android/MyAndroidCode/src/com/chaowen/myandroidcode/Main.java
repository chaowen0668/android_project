package com.chaowen.myandroidcode;

import java.util.ArrayList;
import java.util.List;
import com.chaowen.myandroidcode.compone.Compone;
import com.chaowen.myandroidcode.ui.UIMain;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main  extends BaseActivity {
	private View mainActionBarView;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//setContentView(R.layout.main);
    	initActionBar();
    	ListView list = new ListView(Main.this);
    	list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
    	setContentView(list);
    	list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0: //Android组件
					Intent intent = new Intent(Main.this,Compone.class);
					startActivity(intent);
					break;
				case 1: 
					 intent = new Intent(Main.this,UIMain.class);
						startActivity(intent);
					break;
				default:
					break;
				}
				
			}
		});
     }
     
     
     List<String> data = null;
     private List<String> getData(){
    	 data = new ArrayList<String>();
    	 data.add("Android组件");
         data.add("Android UI效果");
    	 return data;
     }


     private void initActionBar() {
 		//可以自定义actionbar
 		getSupportActionBar().setDisplayShowCustomEnabled(true);
 		getSupportActionBar().setDisplayShowTitleEnabled(false);
 		//不在actionbar显示logo
 		getSupportActionBar().setDisplayShowHomeEnabled(false);
 		
         getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_tab_bg));
         mainActionBarView = LayoutInflater.from(this).inflate(R.layout.main_action_bar, null);
 		getSupportActionBar().setCustomView(mainActionBarView);
 	}
	



     
}
