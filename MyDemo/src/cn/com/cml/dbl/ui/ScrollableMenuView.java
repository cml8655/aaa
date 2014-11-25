package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.EViewGroup;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

@EViewGroup
public class ScrollableMenuView extends LinearLayout implements OnTouchListener {

	private Point mPoint;

	public ScrollableMenuView(Context context) {
		super(context);
	}

	public ScrollableMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScrollableMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		final int action = event.getAction();

		switch (action) {

		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_MOVE:

			this.setX(event.getX());
			this.setY(event.getY());

			break;

		case MotionEvent.ACTION_UP:
			break;

		}

		return true;
	}

}
