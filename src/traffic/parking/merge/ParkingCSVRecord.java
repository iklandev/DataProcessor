package traffic.parking.merge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class ParkingCSVRecord {
	
	static Logger logger = Logger.getLogger(ParkingCSVRecord.class.getName());
	
	private Date dateTime;
	
	private String lotId;
	
	private int freeSpaces;

	public ParkingCSVRecord(String dateTime, String lotId, String freeSpaces) {
		super();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		try{
			this.dateTime = sdf.parse(dateTime);
		} catch (ParseException e){
			logger.error(String.format("Error in ParkingCSVRecord: %s %s %s", dateTime, lotId, freeSpaces));
			logger.error(e.getMessage(), e);
		}
		
		this.lotId = lotId;
		this.freeSpaces = Integer.parseInt(freeSpaces);
	}
	
	public boolean isBetweenDateRange(Date from, Date to){
		
		if (this.dateTime.after(from) && (this.dateTime.before(to) || this.dateTime.equals(to))) {
			return true;
		}
		
		return false;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public String getLotId() {
		return lotId;
	}

	public int getFreeSpaces() {
		return freeSpaces;
	}

}
