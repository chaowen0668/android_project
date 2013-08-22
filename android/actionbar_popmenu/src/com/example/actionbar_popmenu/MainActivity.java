package com.example.actionbar_popmenu;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.example.actionbar_popmenu.widge.MySwitch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends SherlockActivity {
    PopMenu popMenu;
    private Context context = MainActivity.this;
    private MySwitch switch1 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 初始化弹出菜单
				popMenu = new PopMenu(context);
				
				// 菜单项点击监听器
				popMenu.setOnClickListener(popmenuOnClickListener);
	}

	 @Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuItem pop = menu.add(Menu.NONE, 100, 1, "popmenu");
		pop.setIcon(R.drawable.ic_launcher);
		pop.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		/*pop.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
			    Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
			    popMenu.showAsDropDown(item.getActionView());
				return false;
			}
		});*/
		return super.onCreateOptionsMenu(menu);
	}
	 
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			 popMenu.showAsDropDown(findViewById(item.getItemId()));
			break;

		default:
			break;
		}
		return false;
	};
   
	 
	// 弹出菜单监听器
		OnClickListener popmenuOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.testBtn:
					System.out.println("下拉菜单点击" + v.getId()+"testBtn");
					break;
				case R.id.switch1:
					switch1 = (MySwitch)v;
					if(switch1.isChecked()){
						Toast.makeText(context, "开", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "关", Toast.LENGTH_SHORT).show();
					}
					
					break;
				default:
					break;
				}
				
				//popMenu.dismiss();
				
			}
		};
}
