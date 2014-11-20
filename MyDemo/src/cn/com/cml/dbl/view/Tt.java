package cn.com.cml.dbl.view;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Tt  extends MyLocationOverlay{

	public Tt(Context context, MapView mapView) {
		super(context, mapView);
	}

	@Override
	protected boolean dispatchTap() {
		// TODO Auto-generated method stub
		return super.dispatchTap();
	}

	@Override
	public synchronized boolean draw(Canvas canvas, MapView mapView,
			boolean shadow, long when) {
		// TODO Auto-generated method stub
		return super.draw(canvas, mapView, shadow, when);
	}

	@Override
	protected void drawCompass(Canvas canvas, float bearing) {
		// TODO Auto-generated method stub
		super.drawCompass(canvas, bearing);
	}

	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {
		// TODO Auto-generated method stub
		super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
	}
	

}
