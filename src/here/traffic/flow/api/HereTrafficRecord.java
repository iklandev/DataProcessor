package here.traffic.flow.api;

public class HereTrafficRecord {

	/**
	 * Decoded JSON response from HERE Traffic API
	 */
	private HereTrafficML hereTrafficML;
	
	/**
	 * Date and time when record was retrieved from HERE Traffic API
	 */
	private String dateTime;
	
	/**
	 * Id for the city for which get the traffic
	 */
	private String cityId;

	public HereTrafficRecord(HereTrafficML hereTrafficML, String dateTime, String cityId) {
		super();
		this.hereTrafficML = hereTrafficML;
		this.dateTime = dateTime;
		this.cityId = cityId;
	}

	public HereTrafficML getHereTrafficML() {
		return hereTrafficML;
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getCityId() {
		return cityId;
	}
	
}
