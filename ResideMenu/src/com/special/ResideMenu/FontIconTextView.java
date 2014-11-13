package com.special.ResideMenu;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontIconTextView extends TextView {

	public FontIconTextView(Context context) {
		super(context);
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				"font/fontello.ttf"));
	}

	public FontIconTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				"font/fontello.ttf"));
	}

	public FontIconTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				"font/fontello.ttf"));
	}

}
