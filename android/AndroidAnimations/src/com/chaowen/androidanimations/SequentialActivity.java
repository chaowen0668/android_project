package com.chaowen.androidanimations;

import info.androidhive.androidanimations.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

public class SequentialActivity extends Activity implements AnimationListener {

	ImageView imgLogo;
	Button btnStart;

	Animation animSequential;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sequential);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		animSequential = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.sequential);

		animSequential.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgLogo.startAnimation(animSequential);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animSequential) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}