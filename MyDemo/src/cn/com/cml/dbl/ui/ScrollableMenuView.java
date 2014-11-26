package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.EViewGroup;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

@EViewGroup
public class ScrollableMenuView extends LinearLayout implements OnTouchListener {

	private Point figureDownPoint = new Point();

	public ScrollableMenuView(Context context) {
		super(context);
		setOnTouchListener(this);
	}

	public ScrollableMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnTouchListener(this);
	}

	public ScrollableMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		Log.d("ScrollableMenuView", "x:" + event.getX() + ",y:" + event.getY()
				+ ":" + (event.getAction() == MotionEvent.ACTION_MOVE));

		FrameLayout.LayoutParams param = (android.widget.FrameLayout.LayoutParams) v
				.getLayoutParams();

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			figureDownPoint.x = (int) (event.getX() - param.leftMargin);// 距离view左边的数据
			figureDownPoint.y = (int) (event.getY() - param.topMargin);// 距离view上边缘的数据

			break;

		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:

			param.leftMargin = (int) (event.getX() - figureDownPoint.x);
			param.topMargin = (int) (event.getY() - figureDownPoint.y);

			v.setLayoutParams(param);

			break;
		}
		return true;
	}
}
