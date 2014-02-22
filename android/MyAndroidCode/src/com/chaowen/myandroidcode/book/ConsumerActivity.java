package com.chaowen.myandroidcode.book;

import com.chaowen.myandroidcode.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ConsumerActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consumer);
		TextView textView = (TextView) findViewById(R.id.textview);
		String text = "未指定key和secret\n\n\n请设置mobile.android.weibo.Consumer类的consumerKey、consumerSecret和redirectUrl变量(从新浪微博官方网站获取这3个值），或在SD卡根目录建立一个consumer.ini文件，第1行输入Key对应的值，第2行输入Secret对应的值、第3行输入redirectUrl。consumer.ini文件格式如下所示(下面的内容是例子，不要用这两个值）：\n\n155318436988\naa490910db9399ac47ea0d\nhttp://www.sina.com";
		textView.setText(text);

	}
}
