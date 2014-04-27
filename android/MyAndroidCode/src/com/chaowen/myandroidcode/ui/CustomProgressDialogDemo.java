/**
 * 系统项目名称
 * com.chaowen.myandroidcode.ui
 * CustomProgressDialogDemo.java
 * 
 * 2014年4月27日-上午11:53:56
 *  2014XX公司-版权所有
 * 
 */
package com.chaowen.myandroidcode.ui;
import com.chaowen.myandroidcode.R;
import com.chaowen.myandroidcode.widge.CustomProgressDialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * CustomProgressDialogDemo
 * 自定义进度回条ProgressDialog
 * chaowen
 * 511644784@qq.com
 * 2014年4月27日 上午11:53:56
 * 
 * @version 1.0.0
 * 
 */
public class CustomProgressDialogDemo extends Activity{
	private MainFrameTask mMainFrameTask = null;
	private CustomProgressDialog progressDialog = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        
        setContentView(R.layout.activity_customprogressdialog);
        
        mMainFrameTask = new MainFrameTask(this);
        mMainFrameTask.execute();
    }
    
	@Override
	protected void onDestroy() {
		stopProgressDialog();
		
		if (mMainFrameTask != null && !mMainFrameTask.isCancelled()){
			mMainFrameTask.cancel(true);
		}
		
		super.onDestroy();
	}

	private void startProgressDialog(){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(this);
	    	progressDialog.setMessage("正在加载中...");
		}
		
    	progressDialog.show();
	}
	
	private void stopProgressDialog(){
		if (progressDialog != null){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	public class MainFrameTask extends AsyncTask<Integer, String, Integer>{
		private CustomProgressDialogDemo mainFrame = null;
		
		public MainFrameTask(CustomProgressDialogDemo mainFrame){
			this.mainFrame = mainFrame;
		}
		
		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
			
		@Override
		protected void onPreExecute() {
			startProgressDialog();
		}

		@Override
		protected void onPostExecute(Integer result) {
			stopProgressDialog();
		}
			
		
		
	}
}
