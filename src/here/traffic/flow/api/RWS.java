package here.traffic.flow.api;

import java.util.List;

/**
 * ROADWAYS. RWS contains 1 to many instances of RW (ROADWAY). In the normal
 * case where the feed represents one TMC Table, there will be only one RWS
 * element
 * 
 * @author ivan
 *
 */
public class RWS {


	private List<RW> RW;
	
	/**
	 * Type of roadway in this element. For TMC Roadways, this will always be
	 * “TMC”; for ULR referenced roadways, “ULR”; for referencing via shape
	 * points, “SHP”; for other types of referencing, “GENERIC_DLR”. In general,
	 * a TrafficML file can contain any combination of any of these types.
	 * IMPORTANT NOTE: You must check this value, and only process the
	 * Roadways/RWS when you know how to interpret the RWS type. For example, if
	 * you are only interested in processing TMC referenced roadways, then your
	 * application must ignore all RWS elements for which TY != ”TMC”.
	 * Required.
	 */
	private String TY;
	
	/**
	 * The Extended TMC Country Code associated with all Roadways within this RWS section.
	 * Example: “A0” for the United States.
	 * Required.
	 */
	private String EXTENDED_COUNTRY_CODE;
	
	/**
	 * TMC Country code identifier associated with all Roadways within this RWS
	 * section (i.e., ‘1’ for the U.S., ‘D’ for Germany).
	 * Required.
	 */
	private String EBU_COUNTRY_CODE;
	
	/**
	 * TMC regional table identifier associated with all Roadways within this RWS section. 
	 * Items without TMC codes (i.e. ULR or DLR types) are still grouped according to the 
	 * generally understood geographical boundaries of the TMC table.
	 * Required.
	 */
	private String TABLE_ID;
	
	/**
	 * Version of the TMC table used to produce this element.
	 */
	private String TMC_TABLE_VERSION;
	
	/**
	 * If used, this attribute overrides the global UNITS setting for this
	 * particular RWS element. Values are the same as the root UNITS attribute.
	 */
	private String 	UNITS;
	
	
	/**
	 * If used, this attribute overrides the global MAP_VERSION setting for this
	 * particular RWS element. Values are the same as the root MAP_VERSION
	 * attribute.
	 * Not Required.
	 */
	private String MAP_VERSION;


	public List<RW> getRoadways() {
		return RW;
	}


	public String getTY() {
		return TY;
	}


	public String getEXTENDED_COUNTRY_CODE() {
		return EXTENDED_COUNTRY_CODE;
	}


	public String getEBU_COUNTRY_CODE() {
		return EBU_COUNTRY_CODE;
	}


	public String getTABLE_ID() {
		return TABLE_ID;
	}


	public String getTMC_TABLE_VERSION() {
		return TMC_TABLE_VERSION;
	}


	public String getUNITS() {
		return UNITS;
	}


	public String getMAP_VERSION() {
		return MAP_VERSION;
	}
	
}
