package com.chaowen.androidanimations;

import info.androidhive.androidanimations.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class ZoomInActivity extends Activity implements AnimationListener {

	ImageView imgPoster;
	Button btnStart;

	Animation animZoomIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom_in);

		imgPoster = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoom_in);
		
		animZoomIn.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imgPoster.startAnimation(animZoomIn);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animZoomIn) {			
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}
