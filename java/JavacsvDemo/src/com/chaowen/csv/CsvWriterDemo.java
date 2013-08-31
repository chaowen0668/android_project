package com.chaowen.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvWriter;

public class CsvWriterDemo {

	
	public static void main(String[] args) throws IOException {
		 CsvWriter wr =new CsvWriter("C://info.csv",',',Charset.forName("GBK"));
		 ArrayList<String> titleList = new ArrayList<String>();
		 String[] title = new String[titleList.size()];
		 titleList.add("宝贝名称");
		 titleList.add("宝贝类目");
		 wr.writeRecord(titleList.toArray(title));
		 
		 ArrayList<String> contentList = new ArrayList<String>();
		 String[] content = new String[contentList.size()];
		 contentList.add("宝贝名称");
		 contentList.add("宝贝类目");
		 wr.writeRecord(contentList.toArray(content));
		 wr.close();
	}

}
