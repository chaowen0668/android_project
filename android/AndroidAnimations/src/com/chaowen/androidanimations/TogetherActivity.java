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

public class TogetherActivity extends Activity implements AnimationListener {

	ImageView imgLogo;
	Button btnStart;

	Animation animTogether;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_together);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		animTogether = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.together);

		animTogether.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgLogo.startAnimation(animTogether);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animTogether) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}