package cn.com.cml.dbl.model.map;

public class MapOptions {
	
	private boolean compassEnabled;
	private boolean overlookingGesturesEnabled;
	private boolean rotateGesturesEnabled;
	private boolean scrollGesturesEnabled;
	private boolean zoomGesturesEnabled;
	private boolean allGesturesEnabled;

	public boolean isCompassEnabled() {
		return compassEnabled;
	}

	public void setCompassEnabled(boolean compassEnabled) {
		this.compassEnabled = compassEnabled;
	}

	public boolean isOverlookingGesturesEnabled() {
		return overlookingGesturesEnabled;
	}

	public void setOverlookingGesturesEnabled(boolean overlookingGesturesEnabled) {
		this.overlookingGesturesEnabled = overlookingGesturesEnabled;
	}

	public boolean isRotateGesturesEnabled() {
		return rotateGesturesEnabled;
	}

	public void setRotateGesturesEnabled(boolean rotateGesturesEnabled) {
		this.rotateGesturesEnabled = rotateGesturesEnabled;
	}

	public boolean isScrollGesturesEnabled() {
		return scrollGesturesEnabled;
	}

	public void setScrollGesturesEnabled(boolean scrollGesturesEnabled) {
		this.scrollGesturesEnabled = scrollGesturesEnabled;
	}

	public boolean isZoomGesturesEnabled() {
		return zoomGesturesEnabled;
	}

	public void setZoomGesturesEnabled(boolean zoomGesturesEnabled) {
		this.zoomGesturesEnabled = zoomGesturesEnabled;
	}

	public boolean isAllGesturesEnabled() {
		return allGesturesEnabled;
	}

	public void setAllGesturesEnabled(boolean allGesturesEnabled) {
		this.allGesturesEnabled = allGesturesEnabled;
	}

}
