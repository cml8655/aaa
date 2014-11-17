package cn.com.cml.dbl.model;

public class LocationModel extends BaseModel {

	private double latitude;
	private double longitude;
	private double radius;
	private String address;
	private String getTime;
	private String userCode;
	private String imei;
	/** 0:imei,1:组合编号 */
	private int imeiType;

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

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getImeiType() {
		return imeiType;
	}

	public void setImeiType(int imeiType) {
		this.imeiType = imeiType;
	}

}
