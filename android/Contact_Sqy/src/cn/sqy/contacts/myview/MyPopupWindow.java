package cn.sqy.contacts.myview;

import cn.sqy.contacts.R;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {
	private ImageButton imbDown;

	public MyPopupWindow(View contentView, int width, int height,
			ImageButton imbDown) {
		super(contentView, width, height);
		this.imbDown = imbDown;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		imbDown.setBackgroundResource(R.drawable.spinner_undown);
	}

}
