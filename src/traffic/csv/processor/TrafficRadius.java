package traffic.csv.processor;

public enum TrafficRadius {

	RADIUS_1(1),
	RADIUS_2(2),
	RADIUS_3(3);
	
	private int code;
	
	private TrafficRadius(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
