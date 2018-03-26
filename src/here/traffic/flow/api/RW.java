package here.traffic.flow.api;

import java.util.List;

/**
 * Roadway (RW) Item
 * This is the composite item for flow across an entire roadway or
 * linear. A roadway item will be present for each roadway with traffic flow
 * information available.
 * 
 * @author ivan
 *
 */
public class RW {

	/**
	 * Unique string identifier for this Linear. (ROADWAY_ID).
	 * Required.
	 */
	private String LI;
	
	/**
	 * Roadway description, if known. Example: I-76. (DESCRIPTION)
	 */
	private String DE;
	
	/**
	 * The base timestamp used for Predictive calculations.
	 * The UNTIL times in each prediction is in reference to this base time. 
	 * The timestamp format follows the ISO-8601 specification. Example: 2011-07-04T22:02:46Z. 
	 * (PREDICTIVE_BASE_TIME).
	 * Required.
	 */
	private String PBT;
	
	/**
	 * List of flow items, one list for each direction of the roadway (1-many).
	 * NOTE: The FI in each FIS section is guaranteed to be contiguous and in
	 * driving direction order. If there is a break in continuity on a RW
	 * (ROADWAY), there will be 2 or more FIS elements in the RW.
	 * (FLOW_ITEMS)
	 * Required.
	 */
	private List<FIS> FIS;

	public String getLI() {
		return LI;
	}

	public String getDE() {
		return DE;
	}

	public String getPBT() {
		return PBT;
	}

	public List<FIS> getFIS() {
		return FIS;
	}

}
