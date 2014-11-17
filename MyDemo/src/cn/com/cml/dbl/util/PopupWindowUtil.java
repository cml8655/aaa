package cn.com.cml.dbl.util;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import cn.com.cml.dbl.ui.MobileMonitorSelectView_;

@EBean
public class PopupWindowUtil {

	@RootContext
	Context context;

	private PopupWindow window;

	public void toggleRouteSelectWindow(View parent) {

		if (null == window) {
			window = new PopupWindow(LayoutParams.MATCH_PARENT, 500);
			window.setOutsideTouchable(true);
			window.setBackgroundDrawable(new ColorDrawable(Color.RED));
			window.setContentView(MobileMonitorSelectView_.build(context));
		}
		window.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
	}

	public void releaseRouteSelectWindow() {
		if (null != window) {
			window.dismiss();
		}
	}
}
