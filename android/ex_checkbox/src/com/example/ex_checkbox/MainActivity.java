package com.example.ex_checkbox;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.MenuItem;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends SherlockFragmentActivity {
    ArrayList<Group> groups; 
	ListView list;
	NavListAdapter adapter;
	String[] title;
	Fragment fragment1 = new Fragment1();
	Fragment fragment2 = new Fragment2();
	
    private int fragIndex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();
		//fragIndex = bundle.getInt("fragIndex");
		// Generate title
		title = new String[] { "老师", "家长"};

		// Pass results to NavListAdapter Class
		adapter = new NavListAdapter(this, title);
		
		// Hide the ActionBar Title
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		// Create the Navigation List in your ActionBar
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Listen to navigation list clicks
		ActionBar.OnNavigationListener navlistener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {FragmentTransaction ft = getSupportFragmentManager()
				.beginTransaction();
			// Locate Position
			switch (position) {
			case 0:
				ft.replace(android.R.id.content, fragment1);
				break;
			case 1:
				ft.replace(android.R.id.content, fragment2);
				break;
			
			}
			ft.commit();
			return true;
			
			}

		};
		
		// Set the NavListAdapter into the ActionBar Navigation
		getSupportActionBar().setListNavigationCallbacks(adapter, navlistener);
		getSupportActionBar().setSelectedNavigationItem(fragIndex);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_tab_bg));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
       switch (item.getItemId()) {
          case android.R.id.home:
        	 /* intent = new Intent(this, TeachPlan_Main.class);
        	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);*/
              break;
          default:
              return super.onOptionsItemSelected(item);  
       }
       return true;
    }

	
	

}
