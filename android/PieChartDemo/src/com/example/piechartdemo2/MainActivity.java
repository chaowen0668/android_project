package com.example.piechartdemo2;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
	// Instantiating a renderer for the Pie Chart
	DefaultRenderer defaultRenderer  = new DefaultRenderer();  
	// 数据元名字
	String[] code = new String[] {
			"体弱幼儿", "重点观察幼儿", "服药提醒幼儿"};    	
	
	// 数据元在饼状图的值
	double[] distribution = { 23.5,26.5,50.0 } ;
	
	// 对应数据元的颜色
	int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN};
	
	private GraphicalView mChartView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		openChart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
       private void openChart(){    	
    	   LinearLayout layout = (LinearLayout)findViewById(R.id.chart);
    	  
    	// Instantiating CategorySeries to plot Pie Chart    	
    	 
    	for(int i=0 ;i < distribution.length;i++){
    		// Adding a slice with its values and name to the Pie Chart
    		distributionSeries.add(code[i], distribution[i]);
    	}   
    	
    	  	
    	for(int i = 0 ;i<distribution.length;i++){    		
    		SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();    	
    		seriesRenderer.setColor(colors[i]);
    		seriesRenderer.setDisplayChartValues(true);
    		// Adding a renderer for a slice
    		defaultRenderer.addSeriesRenderer(seriesRenderer);
    	}
    	
    	defaultRenderer.setChartTitle("Android version distribution as on October 1, 2012 ");
    	defaultRenderer.setChartTitleTextSize(30);
    	defaultRenderer.setStartAngle(180);
    	defaultRenderer.setZoomButtonsVisible(true);    	    		
    	defaultRenderer.setDisplayValues(true);
    	defaultRenderer.setClickEnabled(true);
    	defaultRenderer.setLabelsTextSize(20);
    	defaultRenderer.setLegendTextSize(18);
    	defaultRenderer.setLabelsColor(Color.BLACK);
    	defaultRenderer.setAntialiasing(true);
        defaultRenderer.setPanEnabled(false);  //设置不可移动
       
    	// Creating an intent to plot bar chart using dataset and multipleRenderer    	
    	/*Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");    	
    	
    	// Start Activity
    	startActivity(intent);*/
    	 mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
    	 mChartView.setOnClickListener(new View.OnClickListener() {
    	        @Override
    	        public void onClick(View v) {
    	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
    	          if (seriesSelection == null) {
    	            Toast.makeText(MainActivity.this, "No chart element selected", Toast.LENGTH_SHORT)
    	                .show();
    	          } else {
    	            for (int i = 0; i < distributionSeries.getItemCount(); i++) {
    	            	defaultRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
    	            }
    	            mChartView.repaint();
    	            Toast.makeText(
    	            		MainActivity.this,
    	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
    	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
    	          }
    	        }
    	      });
    	 
    	  layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
    	          LayoutParams.FILL_PARENT));
    	  
    }
	

}
