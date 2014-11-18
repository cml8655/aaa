package cn.com.cml.dbl.util;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.ui.MobileMonitorSelectView;
import cn.com.cml.dbl.ui.MobileMonitorSelectView_;

@EBean
public class PopupWindowUtil {

	@RootContext
	Context context;

	private PopupWindow window;

	public void toggleRouteSelectWindow(View parent) {

		if (null == window) {
			window = new PopupWindow(LayoutParams.MATCH_PARENT, 500);
			window.setOutsideTouchable(false);
			window.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

			MobileMonitorSelectView view = MobileMonitorSelectView_
					.build(context);

			view.bindData(R.string.by_bus_text, R.string.by_driver_text,
					R.string.by_walk_text, R.string.cancel_text,
					new OnClickListener() {
						@Override
						public void onClick(View v) {

							final int id = v.getId();

							switch (id) {
							case R.id.first_tv:

								break;
							case R.id.second_tv:

								break;
							case R.id.third_tv:

								break;
							case R.id.forth_tv:
								if (null != window) {
									window.dismiss();
									window=null;
								}
								break;

							default:
								break;
							}

						}
					});

			window.setContentView(view);
			window.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
		} else {
			window.dismiss();
			window = null;
		}

	}

	public void releaseRouteSelectWindow() {
		if (null != window) {
			window.dismiss();
		}
	}
}
