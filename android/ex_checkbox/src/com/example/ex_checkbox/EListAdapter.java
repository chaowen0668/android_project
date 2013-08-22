package com.example.ex_checkbox;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class EListAdapter extends BaseExpandableListAdapter {
	 

    private Context context;

    private ArrayList<Group> groups;
 

    public EListAdapter(Context context, ArrayList<Group> groups) {

        this.context = context;

        this.groups = groups;

    }
 

    public Object getChild(int groupPosition, int childPosition) {

        groups.get(groupPosition).getChildItem(childPosition);

        return null;

    }
 

    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;

    }
 

    public int getChildrenCount(int groupPosition) {

        return groups.get(groupPosition).getChildrenCount();

    }
 

    public Object getGroup(int groupPosition) {

        return groups.get(groupPosition);

    }
 

    public int getGroupCount() {

        return groups.size();

    }
 

    public long getGroupId(int groupPosition) {

        return groupPosition;

    }
 

    public boolean hasStableIds() {

        return false;

    }
 

    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;

    }
 

    /** 設定 Group 資料 */

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
 

        Group group = (Group) getGroup(groupPosition);
 

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.fragment_group_layout, null);

        }
 

        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);

        tv.setText(group.getTitle());
 

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbGroup);

        checkBox.setChecked(group.getChecked());
 

        // 點擊 CheckBox 時，將狀態存起來

        checkBox.setOnClickListener(new Group_CheckBox_Click(Integer.valueOf(groupPosition)));
 

        return convertView;

    }
 

    /** 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態 */

    class Group_CheckBox_Click implements OnClickListener {

        private int groupPosition;
 

        Group_CheckBox_Click(int groupPosition) {

            this.groupPosition = groupPosition;

        }
 

        public void onClick(View v) {

            groups.get(groupPosition).toggle();
 

            // 將 Children 的 isChecked 全面設成跟 Group 一樣

            int childrenCount = groups.get(groupPosition).getChildrenCount();

            boolean groupIsChecked = groups.get(groupPosition).getChecked();

            for (int i = 0; i < childrenCount; i++)

                groups.get(groupPosition).getChildItem(i).setChecked(groupIsChecked);
 

            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面

            notifyDataSetChanged();

        }

    }
 

    /** 設定 Children 資料 */

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
 

        Child child = groups.get(groupPosition).getChildItem(childPosition);
 

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.fragment_child_layout, null);

        }
 

        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);

        tv.setText(child.getFullname());
 
        TextView tvnum = (TextView)convertView.findViewById(R.id.tvphonenum);
        tvnum.setText(child.getPhonenum());

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbChild);

        checkBox.setChecked(child.getChecked());
 

        // 點擊 CheckBox 時，將狀態存起來

        checkBox.setOnClickListener(new Child_CheckBox_Click(Integer.valueOf(groupPosition), Integer.valueOf(childPosition)));
 

        return convertView;

    }
 

    /** 勾選 Child CheckBox 時，存 Child CheckBox 的狀態 */

    class Child_CheckBox_Click implements OnClickListener {

        private int groupPosition;

        private int childPosition;
 

        Child_CheckBox_Click(int groupPosition, int childPosition) {

            this.groupPosition = groupPosition;

            this.childPosition = childPosition;

        }
 

        public void onClick(View v) {

            groups.get(groupPosition).getChildItem(childPosition).toggle();
 

            // 檢查 Child CheckBox 是否有全部勾選，以控制 Group CheckBox

            int childrenCount = groups.get(groupPosition).getChildrenCount();

            boolean childrenAllIsChecked = true;

            for (int i = 0; i < childrenCount; i++) {

                if (!groups.get(groupPosition).getChildItem(i).getChecked())

                    childrenAllIsChecked = false;

            }
 

            groups.get(groupPosition).setChecked(childrenAllIsChecked);
 

            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面

            notifyDataSetChanged();

        }

    }
}
