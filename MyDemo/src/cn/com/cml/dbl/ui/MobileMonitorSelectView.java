package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.EViewGroup;

import cn.com.cml.dbl.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * 手机定位追踪查询结果返回选择
 * 
 * @author 陈孟琳
 *
 *         2014年11月17日
 */
@EViewGroup(R.layout.view_mobile_monitor_select)
public class MobileMonitorSelectView extends LinearLayout {

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

	public void bindClickListener(OnClickListener listener) {

	}

}
