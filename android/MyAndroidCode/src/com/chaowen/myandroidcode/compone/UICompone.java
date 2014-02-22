package com.chaowen.myandroidcode.compone;

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

//UI效果大全
public class UICompone extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	//setContentView(R.layout.main);
	    	
	    	ListView list = new ListView(UICompone.this);
	    	list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
	    	setContentView(list);
	    	list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0: //时间轴
						Intent intent = new Intent(UICompone.this,CustomToast.class);
						startActivity(intent);
						break;

					default:
						break;
					}
					
				}
			});
	     }
	     
	     
	     
	     private List<String> getData(){

	    	 List<String> data = new ArrayList<String>();
	    	 data.add("自定义Toast");

	    	 return data;
	     }
}
