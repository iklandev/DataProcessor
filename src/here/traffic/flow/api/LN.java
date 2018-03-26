package here.traffic.flow.api;

import java.math.BigDecimal;

public class LN {

	/**
	 * Comma delimited list of lanes associated with the traffic. The lane
	 * numbers are referenced from the HERE Map (Left Lane = 1, Right Lane = x).
	 * Lane number 1 will always be the left lane, independent on the driving
	 * side. 
	 * (Numbers)
	 * Required.
	 */
	private String NM;
	
	/**
	 * The average uncapped speed on these lanes of traffic that current traffic
	 * is travelling. The units are defined in the file header. 
	 * (SPEED_UNCAPPED)
	 *  Required.
	 */
	private BigDecimal SU;
	
	/**
	 * The number between 0.0 and 10.0 indicating the expected quality of
	 * travel. As the number approaches 10.0 the quality of travel is getting
	 * worse. -1.0 indicates that a Jam Factor could not be calculated.
	 * (JAM_FACTOR)
	 *  Required.
	 */
	private BigDecimal JF;

	public String getNM() {
		return NM;
	}

	public BigDecimal getSU() {
		return SU;
	}

	public BigDecimal getJF() {
		return JF;
	}

}
