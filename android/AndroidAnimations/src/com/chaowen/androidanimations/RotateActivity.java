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

public class RotateActivity extends Activity implements AnimationListener {

	ImageView imgLogo;
	Button btnStart;

	// Animation
	Animation animRotate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotate);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate);

		animRotate.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imgLogo.setVisibility(View.VISIBLE);

				// start the animation
				imgLogo.startAnimation(animRotate);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animRotate) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}