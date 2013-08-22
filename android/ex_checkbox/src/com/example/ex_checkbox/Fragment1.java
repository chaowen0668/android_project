package com.example.ex_checkbox;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Fragment1 extends SherlockFragment {
	ArrayList<Group> groups; 
	ExpandableListView elv;
	EListAdapter eadapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from fragment1.xml
		//getActivity().setContentView(R.layout.fragment1);
		addData();
		setHasOptionsMenu(true);
	}
	
	
	@Override
	public SherlockFragmentActivity getSherlockActivity() {
		return super.getSherlockActivity();
	}

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment1, container, false);
		this.setHasOptionsMenu(true);
		elv = (ExpandableListView)view.findViewById(R.id.tea_listView);
		eadapter = new EListAdapter(getSherlockActivity(), groups);
		elv.setAdapter(eadapter);
				return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	
	public void addData(){
		groups = new ArrayList<Group>();
		
		Group g1 = new Group("1","主班老师");
		Child c1 = new Child("100", "张一", "张一","15818106018");
		Child c2 = new Child("101","张二","张二","15818106019");
		g1.addChildrenItem(c1);
		g1.addChildrenItem(c2);
		groups.add(g1);
		
		Group g2 = new Group("2","副班老师");
		Child c3 = new Child("200", "李一", "李一","15818106020");
		Child c4 = new Child("201","李二","李二","15818106021");
		g2.addChildrenItem(c3);
		g2.addChildrenItem(c4);
		groups.add(g2);
		
	}

	
	 @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		 MenuItem refreshItem = menu.add(Menu.NONE, 12, 0, "test");
		 refreshItem.setIcon(R.drawable.ic_launcher);
		super.onCreateOptionsMenu(menu, inflater);
	}
	 
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 12:
			Toast.makeText(getSherlockActivity(), "12", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	 
	 
}
