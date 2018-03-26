package here.traffic.flow.api;

import java.math.BigDecimal;

public class Geotag {

	private BigDecimal latitude;
	
	private BigDecimal longitude;
	
	private TMC tmc;
	
	private boolean isFirst = false;
	
	private boolean isLast = false;
	
	public Geotag (String latitude, String longitude, TMC tmc){
		this.latitude = new BigDecimal(latitude);
		this.longitude = new BigDecimal(longitude);
		this.tmc = tmc;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public TMC getTmc() {
		return tmc;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
}
