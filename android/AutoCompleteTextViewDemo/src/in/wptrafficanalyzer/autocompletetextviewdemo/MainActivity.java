package in.wptrafficanalyzer.autocompletetextviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// Array of strings storing country names
    String[] countries = new String[] {
            "India",
            "Pakistan",
            "Sri Lanka",
            "China",
            "Bangladesh",
            "Nepal",
            "Afghanistan",
            "North Korea",
            "South Korea",
            "Japan"
    };
    
 // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
                R.drawable.india,
                R.drawable.pakistan,
                R.drawable.srilanka,
                R.drawable.china,
                R.drawable.bangladesh,
                R.drawable.nepal,
                R.drawable.afghanistan,
                R.drawable.nkorea,
                R.drawable.skorea,
                R.drawable.japan
    };

    // Array of strings to store currencies
    String[] currency = new String[]{
        "Indian Rupee",
        "Pakistani Rupee",
        "Sri Lankan Rupee",
        "Renminbi",
        "Bangladeshi Taka",
        "Nepalese Rupee",
        "Afghani",
        "North Korean Won",
        "South Korean Won",
        "Japanese Yen"
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
                HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", countries[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            hm.put("cur", currency[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt"};

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.autocomplete_layout, from, to);
        
        // Getting a reference to CustomAutoCompleteTextView of activity_main.xml layout file
        CustomAutoCompleteTextView autoComplete = ( CustomAutoCompleteTextView) findViewById(R.id.autocomplete);
        
        
        /** Defining an itemclick event listener for the autocompletetextview */
        OnItemClickListener itemClickListener = new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        		/** Each item in the adapter is a HashMap object. 
        		 *  So this statement creates the currently clicked hashmap object
        		 * */
        		HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
        		
        		/** Getting a reference to the TextView of the layout file activity_main to set Currency */
        		TextView tvCurrency = (TextView) findViewById(R.id.tv_currency) ;
        		
        		/** Getting currency from the HashMap and setting it to the textview */
        		tvCurrency.setText("Currency : " + hm.get("cur"));
        	}
		};
        
		/** Setting the itemclick event listener */
        autoComplete.setOnItemClickListener(itemClickListener);
                
        /** Setting the adapter to the listView */
        autoComplete.setAdapter(adapter);        
        
    }
    
    /** A callback method, which is executed when this activity is about to be killed
     * This is used to save the current state of the activity 
     * ( eg :  Configuration changes : portrait -> landscape )  
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	TextView tvCurrency = (TextView) findViewById(R.id.tv_currency) ;		
    	outState.putString("currency", tvCurrency.getText().toString());
    	super.onSaveInstanceState(outState);
    }
    
    /** A callback method, which is executed when the activity is recreated 
     * ( eg :  Configuration changes : portrait -> landscape )  
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	TextView tvCurrency = (TextView) findViewById(R.id.tv_currency) ;		
    	tvCurrency.setText(savedInstanceState.getString("currency"));
    	super.onRestoreInstanceState(savedInstanceState);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
