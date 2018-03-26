package traffic.csv.processor;

public enum TrafficFlowDirection {

	IN_FLOW (1), 
	OUT_FLOW(2);
	
	
	private int code;
	
	TrafficFlowDirection (int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
