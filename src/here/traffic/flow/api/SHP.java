package here.traffic.flow.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Provided if SHP is the location referencing type for the containing RWS. Or,
 * if the provider wishes to include shape points, further describing the
 * referenced road segment, it can also use this element. GeoPolyline is simply
 * a space-separated list of two or more lat,long pairs. The lat,long pairs are
 * ordered in the driving direction as opposed to queue direction. Example:
 * <SHP FC="2" LID="1007182580F"> 40.83177,-72.75229 40.83186,-72.752
 * 40.83199,-72.75162</SHP>. 0-Many
 * 
 * @author ivan
 *
 */
public class SHP {

	/**
	 * Indicates the HERE Functional Class of the referenced road segment.
	 * Values are 1-5.
	 */
	private int FC;
	
	/**
	 * Comma delimited set of LinkIDs that contain the list of linkIDs
	 * associated with the SHP reference. The linkIDs will be in driving
	 * direction order; “F” represents travel direction FROM reference node, “T”
	 * represents travel direction TO reference node (For example, 123456789T or
	 * 987654321F).
	 */
	private String LID;
	
	/**
	 * Length of stretch of road defined by SHP element. The units are defined
	 * in the file header.
	 */
	private BigDecimal LE;
	
	/**
	 * Form of way. Possible values are Motorway (MW), Multi-digitized road
	 * which is not a motorway(MD), single digitized road (SD), Ramp/Slip (RA),
	 * Roundabout (RO), Undefined (UN). MW(Motorway) – roads or motorway
	 * connectors that are controlled access. MD(MultiDig) – multi digitized
	 * roads that are not motorways SD(SingleDig) – single digitized roads
	 * that do not fall in other categories. RA(Ramp/Slip) – Ramps that are
	 * not motorway connectors RO(Roundabout) - Roundabout UN(Undefined) –
	 * roadway type is unknown NOTE : The FEATURES element will contain
	 * FORM_OF_WAY element when this feature becomes active.
	 */
	private String FW;
	
	private String [] value;

	public int getFC() {
		return FC;
	}

	public String getLID() {
		return LID;
	}

	public BigDecimal getLE() {
		return LE;
	}

	public String getFW() {
		return FW;
	}

	public String [] getValue() {
		return value;
	}
	
	public List<Geotag> getGeoTags(TMC tmc) {
		List<Geotag> result = new ArrayList<Geotag>();

		if (this.value.length > 0) {
			String[] latLngArray = this.value[0].trim().split(" ");
			for (String latLng : latLngArray) {
				String[] latLngTemp = latLng.split(",");
				result.add(new Geotag(latLngTemp[0], latLngTemp[1], tmc));

			}
		}

		return result;
	}
	
	public Geotag getFirstGeoTag (TMC tmc) {
		
		if (this.value.length > 0){
			
			String[] latLngArray = this.value[0].trim().split(" ");
			
			if (latLngArray.length>1){
				String[] start = latLngArray[0].split(",");
				Geotag startGeo = new Geotag(start[0], start[1], tmc);
				startGeo.setFirst(true);
				return startGeo;
			}

		}
		
		return null;
	}
	
	public Geotag getLastGeoTag (TMC tmc) {
		
		if (this.value.length > 0){
			
			String[] latLngArray = this.value[0].trim().split(" ");

			if (latLngArray.length>1){
				String[] end = latLngArray[latLngArray.length - 1].split(",");
				Geotag endGeo = new Geotag(end[0], end[1], tmc);
				endGeo.setLast(true);
				return endGeo;
			}
			

		}
		
		return null;
	}
}
