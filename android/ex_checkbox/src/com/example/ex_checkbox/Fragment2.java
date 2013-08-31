package com.example.ex_checkbox;

import com.actionbarsherlock.app.SherlockFragment;
import android.os.Bundle;

public class Fragment2 extends SherlockFragment {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from fragment2.xml
		getActivity().setContentView(R.layout.fragment2);
	}

}
