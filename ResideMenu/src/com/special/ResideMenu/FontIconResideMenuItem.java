package com.special.ResideMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FontIconResideMenuItem extends LinearLayout {

	/** menu item icon */
	private FontIconTextView iconView;

	/** menu item title */
	private TextView tv_title;

	private int itemIndex;

	public FontIconResideMenuItem(Context context, int iconRes, String title,
			Integer itemIndex) {
		super(context);
		initViews(context);
		this.itemIndex = itemIndex;
		iconView.setText(context.getString(iconRes));
		tv_title.setText(title);
	}

	private void initViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.residemenu_item, this);
		iconView = (FontIconTextView) findViewById(R.id.iv_icon);
		tv_title = (TextView) findViewById(R.id.tv_title);
	}

	/**
	 * set the icon color;
	 *
	 * @param icon
	 */
	public void setIcon(int iconRes) {
		iconView.setText(getContext().getString(iconRes));
	}

	/**
	 * set the title with resource ;
	 * 
	 * @param title
	 */
	public void setTitle(int title) {
		tv_title.setText(title);
	}

	/**
	 * set the title with string;
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

}
