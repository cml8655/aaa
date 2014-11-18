package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.cml.dbl.R;

/**
 * 手机定位追踪查询结果返回选择
 * 
 * @author 陈孟琳
 *
 *         2014年11月17日
 */
@EViewGroup(R.layout.view_mobile_monitor_select)
public class MobileMonitorSelectView extends LinearLayout {

	@ViewById(R.id.first_tv)
	TextView firstView;

	@ViewById(R.id.second_tv)
	TextView secondView;

	@ViewById(R.id.third_tv)
	TextView thirdView;

	@ViewById(R.id.forth_tv)
	TextView forthView;

	public MobileMonitorSelectView(Context context) {
		super(context);
		setOrientation(VERTICAL);
	}

	public MobileMonitorSelectView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		setOrientation(VERTICAL);
	}

	public MobileMonitorSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
	}

	public void bindData(String first, String second, String third,
			String cancel, OnClickListener listener) {

		firstView.setText(first);
		secondView.setText(second);
		thirdView.setText(third);
		forthView.setText(cancel);

		firstView.setOnClickListener(listener);
		secondView.setOnClickListener(listener);
		thirdView.setOnClickListener(listener);
		forthView.setOnClickListener(listener);
	}

	public void bindData(int firstRes, int secondRes, int thirdRes,
			int cancelRes, OnClickListener listener) {

		this.bindData(getContext().getString(firstRes),
				getContext().getString(secondRes),
				getContext().getString(thirdRes),
				getContext().getString(cancelRes), listener);

	}

}
