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

public class MoveActivity extends Activity implements AnimationListener {

	ImageView imgLogo;
	Button btnStart;

	Animation animMove;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_move);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		animMove = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.move);

		animMove.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgLogo.startAnimation(animMove);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		if (animation == animMove) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}