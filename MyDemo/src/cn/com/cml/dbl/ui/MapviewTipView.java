package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import cn.com.cml.dbl.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

@EViewGroup(R.layout.view_maptip)
public class MapviewTipView extends LinearLayout {

	@ViewById(R.id.maptip_icon)
	FontIconTextView iconView;

	@ViewById(R.id.maptip_text)
	TextView textView;

	public MapviewTipView(Context context) {
		super(context);
	}

	public MapviewTipView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MapviewTipView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void bind(String iconStr, String text) {
		iconView.setText(iconStr);
		textView.setText(text);
	}

	public void bind(int icon, int text) {
		this.bind(getContext().getString(icon), getContext().getString(text));
	}
	public void bind(int icon, String text) {
		this.bind(getContext().getString(icon),text);
	}

}
