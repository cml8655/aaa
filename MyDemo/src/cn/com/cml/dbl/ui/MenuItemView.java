package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.cml.dbl.R;

@EViewGroup(R.layout.view_menu_item)
public class MenuItemView extends LinearLayout {

	@ViewById(R.id.menu_icon)
	FontIconTextView icon;

	@ViewById(R.id.menu_selected)
	TextView selectedView;

	@ViewById(R.id.menu_txt)
	TextView text;

	private String iconStr;
	private String textStr;

	public MenuItemView(Context context) {
		super(context);
	}

	public MenuItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initView(context, attrs);
	}

	public MenuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {

		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.menu_item);

		iconStr = typeArray.getString(R.styleable.menu_item_icon);
		textStr = typeArray.getString(R.styleable.menu_item_text);

		typeArray.recycle();
	}

	@AfterViews
	public void injectValues() {
		this.bind(iconStr, textStr);
	}

	public void bind(String iconStr, String textStr) {
		icon.setText(iconStr);
		text.setText(textStr);
	}

	@Override
	public void setSelected(boolean selected) {
		selectedView.setVisibility(selected ? VISIBLE : GONE);
	}

}
