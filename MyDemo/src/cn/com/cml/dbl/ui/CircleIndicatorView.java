package cn.com.cml.dbl.ui;

import cn.com.cml.dbl.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.PorterDuff.Mode;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

public class CircleIndicatorView extends View {

	private boolean selected;
	private Paint paint;

	public CircleIndicatorView(Context context) {
		this(context, null);
	}

	public CircleIndicatorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.paint = new Paint();

	}

	public CircleIndicatorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);

		int width = this.getWidth();
		int height = this.getHeight();

		int distance = Math.min(width, height);
		int radius = distance / 2;

		int cx = radius;
		int cy = radius;

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		canvas.drawCircle(cx, cy, radius, paint);

		if (selected) {
			paint.setStyle(Style.FILL);
			paint.setColor(getContext().getResources().getColor(
					R.color.md_amber_a200));
		}

		canvas.drawCircle(cx, cy, radius - 5, paint);
	}

}
