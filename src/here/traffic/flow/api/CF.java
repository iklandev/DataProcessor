package here.traffic.flow.api;

import java.math.BigDecimal;

/**
 * 
 * This represents the complex data type for current flow elements for the full
 * Flow Item stretch. If there is more precise traffic data than the Flow Item
 * than there will be 2 or more SUB_SEGMENT sections in the SUB_SEGMENTS
 * section.
 * 
 * @author ivan
 *
 */
public class CF {

	/**
	 * TY will be TR when lanes are normal traffic lanes. If it is a RAMP, the
	 * TY will be RA (ramp). If it is an Express lane road, it will be EX.
	 * Express lanes are called out separately to enable different usage in
	 * certain applications, such as map display. Express lanes may or may not
	 * be reversible. (TYPE) NOTE : The FEATURES element will contain EXPRESS
	 * element when this Exrpess lanes type is active.
	 * Required.
	 */
	private String TY;
	
	/**
	 * TS is the traversability status. The options are:
	 *  “C” for Closed, 
	 *  “O” for Open, and 
	 *  “RNR”for Reversible not routable.
	 *  Required.
	 */
	private String TS;
	
	/**
	 * The average speed, capped by the speed limit, that current traffic is
	 * travelling. -1.0 indicates that the average speed could not be
	 * calculated. The units are defined in the file header. 
	 * (SPEED)
	 * Required.
	 */
	private BigDecimal SP;
	
	/**
	 * The average speed, uncapped by the speed limit, that current traffic is
	 * travelling. -1.0 indicates that the average speed could not be
	 * calculated. The units are defined in the file header. 
	 * (SPEED_UNCAPPED)
	 */
	private BigDecimal SU;
	
	/**
	 * The free flow speed on this stretch of road. 
	 * (FREE_FLOW)
	 * Required.
	 */
	private BigDecimal FF;
	
	/**
	 * The number between 0.0 and 10.0 indicating the expected quality of
	 * travel. When there is a road closure, the Jam Factor will be 10. However,
	 * a reversible not routableError! will use a Jam
	 * Factor of -1 as most applications do not want to “black” out as a closure
	 * on the map. As the number approaches 10.0 the quality of travel is
	 * getting worse. -1.0 indicates that a Jam Factor could not be calculated.
	 * (JAM_FACTOR)
	 * Required.
	 */
	private BigDecimal JF;
	
	/**
	 * The number between 0.0 and 1.0 indicating the percentage of real time
	 * data included in the speed calculation.
	 *  0.7 < CF <= 1.0 indicates real time speeds 
	 *  0.5 < CF <= 0.7 indicates historical speeds 
	 *  0.0 < CF <= 0.5 indicates speed limit 
	 * To only report speeds where real time data is
	 * available, it is recommended to only include speeds with a confidence
	 * greater than 0.7. (CONFIDENCE) A confidence value of -1 is possible. It
	 * indicates that the confidence could not be calculated.
	 */
	private BigDecimal CN;
	
	/**
	 * Only provided if there is a current traffic data of SubSegments is
	 * different than the current traffic data of the FlowItem.
	 * (SUB_SEGMENTS/SUB_SEGMENT)
	 */
	private SSS SSS;
	
	/**
	 * LN is only provided if there are different lane speeds along a roadway
	 * represented by a CF element. When there is lane level traffic along a
	 * roadway, there will be 2 or more LN elements under a CF element. The
	 * number of lanes along a roadway can be determined by looking at the
	 * maximum lane number in all LN elements under the particular segment. If
	 * the lane level data is available at a subSegment granularity, it will be
	 * represented under the SubSegment element.
	 * (LANE)
	 */
	private LN LN;

	public String getTY() {
		return TY;
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

	public BigDecimal getCN() {
		return CN;
	}

	public SSS getSSS() {
		return SSS;
	}

	public LN getLN() {
		return LN;
	}
	
}
