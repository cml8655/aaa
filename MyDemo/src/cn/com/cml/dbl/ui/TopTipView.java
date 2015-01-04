package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.cml.dbl.R;

@EViewGroup(R.layout.view_top_tip)
public class TopTipView extends LinearLayout {

	private static final int ANIM_DURATION = 500;
	private String message;
	private String leftIcon;
	private OnClickListener listener;

	@ViewById(R.id.toptip_icon)
	TextView leftIconView;

	@ViewById(R.id.toptip_text)
	TextView messageView;

	public TopTipView(Context context, OnClickListener clickListener,
			String message, String leftIcon) {
		super(context);
		this.setOnClickListener(listener);
		this.message = message;
		this.leftIcon = leftIcon;
	}

	@AfterViews
	protected void afterView() {
		leftIconView.setText(leftIcon);
		messageView.setText(message);
	}

	public void show() {

		TranslateAnimation showAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF,
				1.0f, Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 1.0f);
		showAnim.setDuration(ANIM_DURATION);
		showAnim.setInterpolator(new OvershootInterpolator());
		this.startAnimation(showAnim);
	}

	public void hide() {
		
		TranslateAnimation showAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
				0.2f, Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 1.0f);
		showAnim.setDuration(ANIM_DURATION);
		showAnim.setInterpolator(new LinearInterpolator());
		this.startAnimation(showAnim);
		
	}

}
