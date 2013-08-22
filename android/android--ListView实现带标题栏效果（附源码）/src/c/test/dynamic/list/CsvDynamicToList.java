package c.test.dynamic.list;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class CsvDynamicToList extends Activity {

    CListView listView;
    List<String[]> list = new ArrayList<String[]>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeData();

        String[] title = new String[] { "ID", "标题2", "标题3", "标题4", "标题5" };

        listView = new CListView(this, title, list);
        listView.setTitleTextColor(Color.GREEN);
        listView.setTitleBackgroundColor(Color.RED);
        // listView.setAutoColumnWidth(3);
        // listView.setItemBackgroundColor(Color.GREEN, Color.WHITE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] item = listView.getItem(position);
                item[2] = "12312312312";
                listView.definedSetChanged();
            }
        });
        
//        listView.definedSetChanged();

        setContentView(listView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "测试修改选中行");
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 0:
            listView.setSelectedPosition(32);
            break;
        }
        return true;
    }

    private void makeData() {
        list.add(new String[] { "1", "数据1.2", "数据1.3", "数据1.4" });
        list.add(new String[] { "2", "数据2.2", "数据2.3", "数据2.4", "数据2.5" });
        list.add(new String[] { "3", "数据3.2", "数据3.3", "数据3.4" });
        list.add(new String[] { "4", "数据4.2", "数据4.3", "数据4.4" });
        list.add(new String[] { "5", "数据5.2", "数据5.3", "数据5.4", "数据5.5" });
        list.add(new String[] { "6", "数据6.2", "数据6.3", "数据6.4" });
        list.add(new String[] { "7", "数据7.2", "数据7.3", "数据7.4", "数据7.5", "数据7.6" });
        list.add(new String[] { "8", "数据8.2", "数据8.3", "数据8.4" });
        list.add(new String[] { "9", "数据9.2", "数据9.3", "数据9.4" });
        list.add(new String[] { "10", "数据10.2", "数据10.3", "数据10.4", "数据10.5", "数据10.6", "数据10.7" });
        list.add(new String[] { "11", "数据11.2", "数据11.3", "数据11.4", "数据11.5" });
        list.add(new String[] { "12", "数据12.2", "数据12.3", "数据12.4", "数据12.5" });
        list.add(new String[] { "13", "数据13.2", "数据13.3", "数据13.4", "数据13.5" });
        list.add(new String[] { "14", "数据14.2", "数据14.3", "数据14.4", "数据14.5" });
        list.add(new String[] { "15", "数据15.2", "数据15.3", "数据15.4", "数据15.5" });
        list.add(new String[] { "16", "数据16.2", "数据16.3", "数据16.4", "数据16.5" });
        list.add(new String[] { "17", "数据17.2", "数据17.3", "数据17.4", "数据17.5" });
        list.add(new String[] { "18", "数据18.2", "数据18.3", "数据18.4", "数据18.5" });
        list.add(new String[] { "19", "数据19.2", "数据19.3", "数据19.4", "数据19.5" });
        list.add(new String[] { "20", "数据20.2", "数据20.3", "数据20.4", "数据20.5" });
        list.add(new String[] { "21", "数据21.2", "数据21.3", "数据21.4", "数据21.5" });
        list.add(new String[] { "22", "数据22.2", "数据22.3", "数据22.4", "数据22.5" });
        list.add(new String[] { "23", "数据23.2", "数据23.3", "数据23.4", "数据23.5" });
        list.add(new String[] { "24", "数据24.2", "数据24.3", "数据24.4", "数据24.5" });
        list.add(new String[] { "25", "数据25.2", "数据25.3", "数据25.4", "数据25.5" });
        list.add(new String[] { "26", "数据26.2" });
        list.add(new String[] { "27", "数据27.2", "数据27.3", "数据27.4", "数据27.5" });
        list.add(new String[] { "28", "数据28.2", "数据28.3", "数据28.4", "数据28.5" });
        list.add(new String[] { "29", "数据29.2", "数据29.3", "数据29.4", "数据29.5" });
        list.add(new String[] { "30", "数据12.2", "数据12.3", "数据12.4", "数据12.5" });
        list.add(new String[] { "31", "数据13.2", "数据13.3", "数据13.4", "数据13.5" });
        list.add(new String[] { "32", "数据14.2", "数据14.3", "数据14.4", "数据14.5" });
        list.add(new String[] { "33", "数据15.2", "数据15.3", "数据15.4", "数据15.5" });
        list.add(new String[] { "34", "数据16.2", "数据16.3", "数据16.4", "数据16.5" });
        list.add(new String[] { "35", "数据17.2", "数据17.3", "数据17.4", "数据17.5" });
        list.add(new String[] { "36", "数据18.2", "数据18.3" });
        list.add(new String[] { "37", "数据19.2", "数据19.3", "数据19.4", "数据19.5" });
        list.add(new String[] { "38", "数据20.2", "数据20.3", "数据20.4", "数据20.5" });
        list.add(new String[] { "39", "数据21.2", "数据21.3", "数据21.4", "数据21.5" });
        list.add(new String[] { "40", "数据22.2", "数据22.3", "数据22.4", "数据22.5" });
        list.add(new String[] { "41", "数据23.2", "数据23.3", "数据23.4", "数据23.5" });
        list.add(new String[] { "42", "数据24.2", "数据24.3", "数据24.4", "数据24.5" });
        list.add(new String[] { "43", "数据25.2", "数据25.3", "数据25.4", "数据25.5" });
        list.add(new String[] { "44", "数据26.2", "数据26.3", "数据26.4", "数据26.5" });
        list.add(new String[] { "45", "数据27.2", "数据27.3", "数据27.4", "数据27.5" });
        list.add(new String[] { "46", "数据28.2", "数据28.3", "数据28.4", "数据28.5" });
        list.add(new String[] { "47", "数据29.2", "数据29.3", "数据29.4", "数据29.5" });
    }
}