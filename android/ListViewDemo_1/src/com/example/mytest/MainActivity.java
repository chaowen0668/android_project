package com.example.mytest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	List<String> listA = new ArrayList<String>();
	List<String> listB = new ArrayList<String>();
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;
	TextView bit_tv;
	int nowZone = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloader_list);
		ListView lv = (ListView) findViewById(R.id.downloadList_list);
		bit_tv = (TextView) findViewById(R.id.downloadList_title);

		for (int n = 0; n < 10; n++) {
			 listA.add("1:" + n);
			listB.add("2:" + n);
		}

		lv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

			}

			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0
						|| (firstVisibleItem < listA.size() && nowZone == 1)) {
					bit_tv.setText("正在下载");
					nowZone = 0;
				} else if (firstVisibleItem >= listA.size() && nowZone == 0) {
					bit_tv.setText("下载完成");
					nowZone = 1;
				}
			}
		});

		lv.setAdapter(new myAdapter());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listA.size() + listB.size() + 2;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			vh v = null;
			vh2 v2 = null;
			vh3 v3 = null;

			int type = getItemViewType(position);

			if (convertView == null) {
				switch (type) {
				case 0:
					convertView = getLayoutInflater().inflate(
							R.layout.downloader_list_cell1, null);
					v = new vh();
					v.tv = (TextView) convertView
							.findViewById(R.id.downloadlist_cell1_title);
					convertView.setTag(v);
					break;
				case 1:
					convertView = getLayoutInflater().inflate(
							R.layout.downloader_list_group, null);
					break;
				case 2:
					convertView = getLayoutInflater().inflate(
							R.layout.downloader_list_cell2, null);
					v3 = new vh3();
					v3.tv = (TextView) convertView
							.findViewById(R.id.downloadlist_cell2_title);
					convertView.setTag(v3);
				}
			} else {
				switch (type) {
				case 0:
					v = (vh) convertView.getTag();
					break;
				case 2:
					v3 = (vh3) convertView.getTag();
					break;
				}
			}

			switch (type) {
			case 0:
				v.tv.setText(Integer.toString(position));
				break;
			case 2:
				v3.tv.setText(Integer.toString(position));
				break;
			}

			return convertView;
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0 || position == listA.size()
					|| (listA.size() == 0 && position == 1)) {
				return 1;
			} else if (position < listA.size()) {
				return 0;
			} else {
				return 2;
			}
		}

		class vh {
			TextView tv;
		}

		class vh2 {
			TextView tv;
		}

		class vh3 {
			TextView tv;
		}
	}
}
