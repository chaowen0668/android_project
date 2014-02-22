package com.chaowen.myandroidcode.compone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaowen.myandroidcode.R;

/**
 * ×Ô¶¨ÒåToast
 * @author wuchaowen
 *  
 */
public class CustomToast extends Activity{
	Button button ;
	Toast toastStart;
	 @Override  
	    public void onCreate(Bundle savedInstanceState)  
	    {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.custom_toast_main);  
	        initToast(); 
	         button = (Button)this.findViewById(R.id.button_toast);
	        button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					 toastStart.show();  
				}
			});
	         
	    }  
	  
	    private void initToast()  
	    {  
	        View toastRoot = getLayoutInflater().inflate(R.layout.custom_toast, null);  
	        TextView message = (TextView) toastRoot.findViewById(R.id.message);  
	        message.setText("My Toast");  
	  
	         toastStart = new Toast(this);  
	        toastStart.setGravity(Gravity.BOTTOM, 0, 10);  
	        toastStart.setDuration(Toast.LENGTH_LONG);  
	        toastStart.setView(toastRoot);  
	       
	    }  
}
