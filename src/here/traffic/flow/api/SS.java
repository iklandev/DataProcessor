package here.traffic.flow.api;

import java.math.BigDecimal;
import java.util.List;

public class SS {

	/**
	 * Length of the stretch of road. The units are defined in the file header.
	 * (LENGTH)
	 * Required.
	 */
	private BigDecimal LE;
	
	/**
	 * TS is the traversable state. The options are “C” for Closed, “O” for Open
	 * (Subsegments cannot be set to “RNR”).
	 * Required.
	 */
	private String TS;
	
	/**
	 * The average speed, capped by the speed limit, that current traffic is
	 * travelling. The units are defined in the file header.
	 * (SPEED)
	 * Required.
	 */
	private BigDecimal SP;
	
	/**
	 * The average speed, uncapped by the speed limit, that current traffic is
	 * travelling. The units are defined in the file header. 
	 * (SPEED_UNCAPPED)
	 */
	private BigDecimal SU;
	
	/**
	 * The free flow speed in on this stretch of road. The units are defined in
	 * the file header. 
	 * (FREE_FLOW)
	 * Required.
	 */
	private BigDecimal FF;
	
	/**
	 * The number between 0.0 and 10.0 indicating the expected quality of
	 * travel. When there is a road closure, the Jam Factor will be 10. As the
	 * number approaches 10.0 the quality of travel is getting worse. -1.0
	 * indicates that a Jam Factor could not be calculated. 
	 * (JAM_FACTOR)
	 * Required.
	 */
	private BigDecimal JF;
	
	/**
	 * LN is only provided if there are different lane speeds along a
	 * SubSegment. When there is lane level traffic along a roadway, there will
	 * be 2 or more LN elements under a SubSegment element. The number of lanes
	 * along a roadway can be determined by looking at the maximum lane number
	 * in all LN elements under the particular subsegment. If the lane level
	 * data is available for the CF element, it will be represented under the CF
	 * element rather than under a subSegment. 
	 * (LANE)
	 */
	private List<LN> LN;

	public BigDecimal getLE() {
		return LE;
	}

	public String getTS() {
		return TS;
	}

	public BigDecimal getSP() {
		return SP;
	}

	public BigDecimal getSU() {
		return SU;
	}

	public BigDecimal getFF() {
		return FF;
	}

	public BigDecimal getJF() {
		return JF;
	}

	public List<LN> getLN() {
		return LN;
	}
	
	
}
