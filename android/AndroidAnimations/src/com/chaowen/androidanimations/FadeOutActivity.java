package com.chaowen.androidanimations;

import info.androidhive.androidanimations.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FadeOutActivity extends Activity implements AnimationListener {

	TextView txtMessage;
	Button btnStart;

	Animation animFadeOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadeout);

		txtMessage = (TextView) findViewById(R.id.txtMessage);
		btnStart = (Button) findViewById(R.id.btnStart);

		animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_out);

		animFadeOut.setAnimationListener(this);

		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// start the animation
				txtMessage.startAnimation(animFadeOut);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == animFadeOut) {
			Toast.makeText(getApplicationContext(), "动画停止",
					Toast.LENGTH_SHORT).show();
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