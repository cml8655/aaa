package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.com.cml.dbl.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

@EViewGroup(R.layout.view_mobile_monitor_menu)
public class PopupMenuView extends LinearLayout implements OnClickListener {

	private PopupMenu menu;
	private boolean menuShow;

	@ViewById(R.id.popup_menu_icon)
	FontIconTextView iconView;

	public PopupMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnClickListener(this);
	}

	public PopupMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(this);
	}

	public PopupMenuView(Context context) {
		super(context);
		setOnClickListener(this);
	}

	public void bindMenuListener(String menuIcon, int menuLayout,
			OnMenuItemClickListener listener) {

		iconView.setText(menuIcon);

		menu = new PopupMenu(getContext(),this);

		menu.inflate(menuLayout);

		menu.setOnMenuItemClickListener(listener);
	}

	@Override
	public void onClick(View v) {

		if (null != menu) {

			if (menuShow) {
				menuShow = false;
				menu.dismiss();
			} else {
				menuShow = true;
				menu.show();
			}
		}

	}

}
