package cn.com.cml.dbl.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Projection;

public class MyMapOverlayView extends MyLocationOverlay {

	private Bitmap bitmap;

	public MyMapOverlayView(Context context, MapView mapView) {
		super(context, mapView);
	}

	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {

		try {

			Projection projection = mapView.getProjection();
			Point point = new Point();
			projection.toPixels(myLocation, point);

			int x = point.x - bitmap.getWidth() / 2;
			int y = point.y - bitmap.getHeight();
			canvas.drawBitmap(bitmap, x, y, new Paint());

		} catch (Exception e) {
			super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
		}

	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
