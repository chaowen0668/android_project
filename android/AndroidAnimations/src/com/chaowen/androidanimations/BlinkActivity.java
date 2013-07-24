package com.chaowen.androidanimations;

import info.androidhive.androidanimations.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class BlinkActivity extends Activity implements AnimationListener {

	TextView txtMessage;
	Button btnStart;

	
	Animation animBlink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blink);

		txtMessage = (TextView) findViewById(R.id.txtMessage);
		btnStart = (Button) findViewById(R.id.btnStart);

		animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		
		animBlink.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				txtMessage.setVisibility(View.VISIBLE);
				
				txtMessage.startAnimation(animBlink);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animBlink) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}