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

/**
 * 
 * @author chaowen
 *
 */
public class FadeInActivity extends Activity implements AnimationListener {

	TextView txtMessage;
	Button btnStart;

	Animation animFadein;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadein);

		txtMessage = (TextView) findViewById(R.id.txtMessage);
		btnStart = (Button) findViewById(R.id.btnStart);

		// 加载动画
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		
		// 设置监听
		animFadein.setAnimationListener(this);

		// 按钮
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				txtMessage.setVisibility(View.VISIBLE);
				
				// 开始动画
				txtMessage.startAnimation(animFadein);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// 在动画结束后使用

		// check for fade in animation
		if (animation == animFadein) {
			Toast.makeText(getApplicationContext(), "动画停止",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		//当动画重复时使用

	}

	@Override
	public void onAnimationStart(Animation animation) {
		//当动画开始使用

	}

}
