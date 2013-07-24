package com.expandlistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ExpandableListView expd_lv;
	ExpandableAdapter adapter;
	
	private List<String> GroupData;
	private List<List<String>> ChildrenData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        expd_lv = (ExpandableListView)findViewById(R.id.expd_lv);
        LoadListData();
        adapter = new ExpandableAdapter();
        expd_lv.setAdapter(adapter);
        
        
    }

    private void LoadListData() {
		// TODO Auto-generated method stub
		GroupData = new ArrayList<String>();
		GroupData.add("ÂÌÉ«Ö²Îï1");
		GroupData.add("ÂÌÉ«Ö²Îï2");
		GroupData.add("ÂÌÉ«Ö²Îï3");
		
		ChildrenData = new ArrayList<List<String>>();
		List<String> child1 = new ArrayList<String>();
		child1.add("1°×Ñî");
		child1.add("1ÎàÍ©");
		child1.add("1ºÏ»¶");
		ChildrenData.add(child1);
		List<String> child2 = new ArrayList<String>();
		child2.add("2°×Ñî");
		child2.add("2ÎàÍ©");
		child2.add("2ºÏ»¶");
		ChildrenData.add(child2);
		List<String> child3 = new ArrayList<String>();
		child3.add("3°×Ñî");
		child3.add("3ÎàÍ©");
		child3.add("3ºÏ»¶");
		ChildrenData.add(child3);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class ExpandableAdapter extends BaseExpandableListAdapter{

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return ChildrenData.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView myText = null;
			if(convertView != null){
				myText = (TextView) convertView;
				myText.setText(ChildrenData.get(groupPosition).get(childPosition));
			}else{
				myText = createView(ChildrenData.get(groupPosition).get(childPosition));
			}
			return myText;
		}

		private TextView createView(String content) {
			// TODO Auto-generated method stub
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 80);
			TextView myText = new TextView(MainActivity.this);
			myText.setLayoutParams(params);
			myText.setGravity(Gravity.CENTER_VERTICAL| Gravity.LEFT );
			myText.setPadding(80, 0, 0, 0);
			myText.setText(content);
			return myText;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return ChildrenData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return GroupData.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return GroupData.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView myText = new TextView(MainActivity.this);
			if(convertView != null){
				myText = (TextView) convertView;
				myText.setText(GroupData.get(groupPosition));
			}else{
				myText = createView(GroupData.get(groupPosition));
			}
			return myText;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
    	
    }
}
