package here.traffic.flow.api;

import java.util.ArrayList;
import java.util.List;

import traffic.csv.processor.TrafficFlowDirection;
import traffic.data.analysator.Util;

/**
 * Entry for each Flow item in the specified direction. (FLOW_ITEM)
 * This is the main item that incorporates flow information for a single
 * location from traffic services. It describes all of the current flow
 * information in terms of travel times and speeds for a stretch of road. This
 * section includes the current flow data and predictive data.
 * 
 * @author ivan
 *
 */
/**
 * @author i_kla
 *
 */
public class FI {

	/**
	 * This represents the current flow conditions of this location.
	 * (CURRENT_FLOW)
	 * Required.
	 */
	private List<CF> CF;
	
	/**
	 * This represents the predictive speeds for the next 12 hours for this location. 0-Many.
	 * (PREDICTIVE_FLOW)
	 */
	private PF PF;
	
	/**
	 * Provided if TMC is the location referencing type for the containing RWS.
	 */
	private TMC TMC;
	
	/**
	 * Provided if GENERIC_DLR is the location referencing type for the
	 * containing RWS.
	 */
	private GENERICDLR GENERIC_DLR;
	
	/**
	 * Provided if SHP is the location referencing type for the containing RWS.
	 * Or, if the provider wishes to include shape points, further describing
	 * the referenced road segment, it can also use this element.
	 */
	private List<SHP> SHP;

	public List<SHP> getSHP() {
		return SHP;
	}

	public List<CF> getCF() {
		return CF;
	}

	public PF getPF() {
		return PF;
	}

	public TMC getTMC() {
		return TMC;
	}

	public GENERICDLR getGENERIC_DLR() {
		return GENERIC_DLR;
	}
	
	public boolean isEqual (FI validateObject){
		
		if (this.getTMC().getPC().equals(validateObject.getTMC().getPC())){
			List <SHP> validator = this.getSHP();
			List <SHP> temp = validateObject.getSHP();
			
			for (int i = 0; i<validator.size(); i++){
				if (!validator.get(i).getValue()[0].toString()
						.equals(temp.get(i).getValue()[0].toString())){
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	
	public List<Geotag> getBorderGeoTags () {
		
		List<Geotag> result = new ArrayList<Geotag>();
		if (this.SHP.size()>0){
			
			result.add(this.SHP.get(0).getFirstGeoTag(this.TMC));
			Geotag endTag = this.SHP.get(this.SHP.size()-1).getLastGeoTag(this.TMC);
			if (endTag!=null){
				result.add(endTag);
			}	
		}
		
		return result;
	}
	
	public boolean isBelongToRadius(int radius, Lot lot) {
		
		for (SHP shp : this.getSHP()) {
			for (Geotag geotag : shp.getGeoTags(this.TMC)) {
				int distanceToLot = Util.distance(lot.getLatitude().doubleValue(), geotag.getLatitude().doubleValue(),
						lot.getLongitude().doubleValue(), geotag.getLongitude().doubleValue(), 0, 0);
					
				if(distanceToLot<=radius){
					return true;
				}
			
			}
		}
		return false;
	}
	
	public TrafficFlowDirection getTrafficFlowDirection(Lot lot) {
		
		List <Geotag> borderTags = this.getBorderGeoTags();
		if (borderTags.size()==2){
			
			int startPointDistance = Util.distance(lot.getLatitude().doubleValue(), borderTags.get(0).getLatitude().doubleValue(),
					lot.getLongitude().doubleValue(), borderTags.get(0).getLongitude().doubleValue(), 0, 0);
			
			int endPointDistance = Util.distance(lot.getLatitude().doubleValue(), borderTags.get(1).getLatitude().doubleValue(),
					lot.getLongitude().doubleValue(), borderTags.get(1).getLongitude().doubleValue(), 0, 0);
			
			if(startPointDistance<endPointDistance){
				
				return TrafficFlowDirection.OUT_FLOW;
			}
			
		}
		
		return TrafficFlowDirection.IN_FLOW;
		
	}

}
