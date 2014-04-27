package com.chaowen.myandroidcode.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UIMain extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	//setContentView(R.layout.main);
	    	
	    	ListView list = new ListView(UIMain.this);
	    	list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
	    	setContentView(list);
	    	list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0: //仿有道 的菜单
						Intent intent = new Intent(UIMain.this,YouDao_gridview.class);
						startActivity(intent);
						break;
					case 1://2.popupwindow 模拟新浪、腾讯title弹框效果
						Intent intent2 = new Intent(UIMain.this,PopWindowDemo.class);
						startActivity(intent2);
						break;
					case 2://2.popupwindow的基本使用
						Intent intent3 = new Intent(UIMain.this,PopWindowDemo2.class);
						startActivity(intent3);
						break;
					default:
						break;
					}
					
				}
			});
	     }
	     
	     
	     
	     private List<String> getData(){

	    	 List<String> data = new ArrayList<String>();
	    	 data.add("1.仿有道 的菜单");
	    	 data.add("2.popupwindow 模拟新浪、腾讯title弹框效果");
	    	 data.add("3.popupwindow的基本使用");
	    	 return data;
	     }
}
