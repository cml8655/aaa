package cn.com.cml.dbl.model;

public class LocationModel {

	private double latitude;
	private double longitude;
	private float radius;
	private int locType;

	public int getLocType() {
		return locType;
	}

	public void setLocType(int locType) {
		this.locType = locType;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

}
