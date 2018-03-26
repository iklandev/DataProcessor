package here.traffic.flow.api;

import java.math.BigDecimal;

/**
 * TMC codes are a reference system designed to give a unique alpha-numeric code
 * to road segment for the purposes of assigning traffic information to that
 * segment. These codes are assigned and certified by TISA (Traveler Information
 * Services Association). These are initially delivered in a specific tabular
 * format known as the TMC Location Table or Traffic Table. These are then
 * referenced to HERE map links and delivered as part of the HERE map content.
 * 
 * @author ivan
 *
 */
public class TMC {

	/**
	 * Point location code for this Flow Item. This is the defined location code
	 * based on the TMC Traffic Tables. 
	 * (POINT_CODE)
	 *  Required.
	 */
	private Integer PC;
	
	/**
	 * Description of the point. 
	 * (DESCRIPTION)
	 * Required.
	 */
	private String DE;
	
	/**
	 * This represents the TMC queuing direction of traffic in positive or
	 * negative notation. Therefore, if the direction of travel is Eastbound
	 * (+), the queuing direction will be Westbound (-). (+, -) .
	 * (QUEUE_DIRECTION)
	 * Required.
	 */
	private String QD;
	
	/**
	 * Length of the stretch of road. The units are defined in the file header.
	 * (LENGTH)
	 * Required.
	 */
	private BigDecimal LE;
	
	/**
	 * HERE Functional Class of the referenced TMC location. Values are 1-5.
	 */
	private Integer FC;
	
	/**
	 * Reserved for future use, to indicate when the data has changed.
	 */
	private String SN;

	public Integer getPC() {
		return PC;
	}

	public String getDE() {
		return DE;
	}

	public String getQD() {
		return QD;
	}

	public BigDecimal getLE() {
		return LE;
	}

	public Integer getFC() {
		return FC;
	}

	public String getSN() {
		return SN;
	}
	
	
}
