package com.lxd.activity;

import com.lxd.widgets.CustomProgressDialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainFrame extends Activity {
	private MainFrameTask mMainFrameTask = null;
	private CustomProgressDialog progressDialog = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        
        setContentView(R.layout.main);
        
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
		private MainFrame mainFrame = null;
		
		public MainFrameTask(MainFrame mainFrame){
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