package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.com.cml.dbl.R;

@EViewGroup(R.layout.view_mobile_monitor_tools)
public class MobileMonitorToolsView extends LinearLayout implements
		OnClickListener {

	public static final int TYPE_SETTING = 1;
	public static final int TYPE_MOBILE = 2;
	public static final int TYPE_USER = 3;

	@ViewById(R.id.tools_setting)
	FontIconTextView settingView;

	@ViewById(R.id.tools_mobile)
	FontIconTextView mobileView;

	@ViewById(R.id.tools_user)
	FontIconTextView userView;

	private OnToolsCliskLinstener clickListener;

	public interface OnToolsCliskLinstener {
		public void onClick(View v, int type);
	}

	public MobileMonitorToolsView(Context context) {
		super(context);
	}

	public MobileMonitorToolsView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public MobileMonitorToolsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@AfterViews
	protected void injectListener() {

		settingView.setOnClickListener(this);
		mobileView.setOnClickListener(this);
		userView.setOnClickListener(this);
	}

	public void bindListener(OnToolsCliskLinstener listener) {
		this.clickListener = listener;
	}

	@Override
	public void onClick(View v) {

		if (clickListener == null) {
			return;
		}

		if (v == settingView) {
			clickListener.onClick(v, TYPE_SETTING);
		} else if (v == mobileView) {
			clickListener.onClick(v, TYPE_MOBILE);
		} else if (v == userView) {
			clickListener.onClick(v, TYPE_USER);
		}

	}

}
