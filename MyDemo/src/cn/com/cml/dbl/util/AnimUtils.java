package cn.com.cml.dbl.util;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.com.cml.dbl.R;

public class AnimUtils {

	public static void checkingAnim(final RelativeLayout container) {

		ImageView checkingImg = new ImageView(container.getContext());

		checkingImg.setImageResource(R.drawable.launcher);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

		container.addView(checkingImg, params);

		Animation anim = AnimationUtils.loadAnimation(container.getContext(),
				R.anim.checking_start);

		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				container.removeAllViews();
			}
		});

		checkingImg.startAnimation(anim);

	}

}
