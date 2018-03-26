package here.traffic.flow.api;

public class PF {

	/**
	 * Time in minutes that prediction is effective until. This time is the
	 * number of minutes beyond the time that is defined in the PBT
	 * (PREDICTIVE_BASE_TIME) attribute.
	 * (UNTIL)
	 *  Required.
	 */
	private int UT;
	
	/**
	 * The predicted speed. The units are defined in the file header.
	 * (SPEED)
	 * Required.
	 */
	private int SP;

	public int getUT() {
		return UT;
	}

	public int getSP() {
		return SP;
	}
	
	
}
