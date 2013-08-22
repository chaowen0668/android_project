package com.example.barchart;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	LinearLayout li1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//�õ���Դ
		li1 = (LinearLayout) findViewById(R.id.li1);
		//��ʼ����״ͼ
		initView();
	}

	private void initView() {
		//��״ͼ���������е�����
		String[] titles = new String[] { "������", "����˰" };
        //�����״ͼ�������е�ֵ
		ArrayList<double[]> value = new ArrayList<double[]>();
		double[] d1 = new double[] { 0.1, 0.3, 0.7, 0.8, 0.5 };
		double[] d2 = new double[] { 0.2, 0.3, 0.4, 0.8, 0.6 };
		value.add(d1);
		value.add(d2);
        //����״����ɫ
		int[] colors = { Color.BLUE, Color.GREEN};

		//Ϊli1�����״ͼ
		li1.addView(
				xychar(titles, value, colors, 6, 1, new double[] { 2007,
						2012.5, 0, 1 }, new int[] { 2008, 2009, 2010, 2011,
						2012 }, "�����ܶ��뾻����(����)", true),
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
	}

	public GraphicalView xychar(String[] titles, ArrayList<double[]> value,
			int[] colors, int x, int y,double[] range, int []xLable ,String xtitle, boolean f) {
		//�����Ⱦ
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		//������е����ݼ�
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		//�������ݼ��Լ���Ⱦ
		for (int i = 0; i < titles.length; i++) {
		
			XYSeries series = new XYSeries(titles[i]);
			double [] yLable= value.get(i);
			for (int j=0;j<yLable.length;j++) {
				series.add(xLable[j],yLable[j]);
			}
			dataset.addSeries(series);
			XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
			// ������ɫ
			xyRenderer.setColor(colors[i]);
			// ���õ����ʽ //
			xyRenderer.setPointStyle(PointStyle.SQUARE);
			// ��Ҫ���Ƶĵ���ӵ����������

			renderer.addSeriesRenderer(xyRenderer);
		}
		//����x���ǩ��
		renderer.setXLabels(x);
		//����Y���ǩ��
		renderer.setYLabels(y);
		//����x������ֵ
		renderer.setXAxisMax(x - 0.5);
		//���������ɫ
		renderer.setAxesColor(Color.BLACK);
		//����x���y��ı�ǩ���뷽ʽ
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		// ������ʵ����
		renderer.setShowGrid(true); 
	
		renderer.setShowAxes(true); 
		// ��������ͼ֮��ľ���
		renderer.setBarSpacing(0.2);
		renderer.setInScroll(false);
		renderer.setPanEnabled(false, false);
		renderer.setClickEnabled(false);
		//����x���y���ǩ����ɫ
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0,Color.RED);
      
		int length = renderer.getSeriesRendererCount();
		//����ͼ��ı���
		renderer.setChartTitle(xtitle);
		renderer.setLabelsColor(Color.RED);

		//����ͼ���������С
		renderer.setLegendTextSize(18);
		//����x���y��������Сֵ
		renderer.setRange(range);
		renderer.setMarginsColor(0x00888888);
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer ssr = renderer.getSeriesRendererAt(i);
			ssr.setChartValuesTextAlign(Align.RIGHT);
			ssr.setChartValuesTextSize(12);
			ssr.setDisplayChartValues(f);
		}
		GraphicalView mChartView = ChartFactory.getBarChartView(getApplicationContext(),
				dataset, renderer, Type.DEFAULT);

		return mChartView;

	}
}
