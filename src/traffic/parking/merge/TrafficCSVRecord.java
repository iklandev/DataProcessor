package traffic.parking.merge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class TrafficCSVRecord {
	
	static Logger logger = Logger.getLogger(TrafficCSVRecord.class.getName()); 
		
	private String date;
	
	private String time;
	
	private String timeId;
	
	private int dayType;
	
	private String lotId;
	
	private String cityId;
	
	private String lotName;
	
	private String totalJamINRadius_1;
	private String totalJamOUTRadius_1;
	
	private String totalJamINRadius_2;
	private String totalJamOUTRadius_2;
	
	private String totalJamINRadius_3;
	private String totalJamOUTRadius_3;
	
	private String avgINForRadius_1;
	private String avgOUTForRadius_1;
	
	private String avgINForRadius_2;
	private String avgOUTForRadius_2;
	
	private String avgINForRadius_3;
	private String avgOUTForRadius_3;
	
	private int freeParkingSpaces;
	
	public TrafficCSVRecord(String dateTime, String lotId, String cityId, String lotName, String totalJamINRadius_1,
			String totalJamOUTRadius_1, String totalJamINRadius_2, String totalJamOUTRadius_2,
			String totalJamINRadius_3, String totalJamOUTRadius_3, String avgINForRadius_1, String avgOUTForRadius_1,
			String avgINForRadius_2, String avgOUTForRadius_2, String avgINForRadius_3, String avgOUTForRadius_3) {
		
		super();
		
		String [] dateTimeParts = dateTime.split(" ");
		String [] timeParts = dateTimeParts[0].split(":");
		
		DateFormat df =  new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		try {
			
			Date d = df.parse(dateTime);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			
			switch (c.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				this.dayType = 1;
				break;
			case Calendar.TUESDAY:
				this.dayType = 2;
				break;
			case Calendar.WEDNESDAY:
				this.dayType = 3;
				break;
			case Calendar.THURSDAY:
				this.dayType = 4;
				break;
			case Calendar.FRIDAY:
				this.dayType = 5;
				break;
			case Calendar.SATURDAY:
				this.dayType = 6;
				break;
			case Calendar.SUNDAY:
				this.dayType = 7;
				break;

			default:
				this.dayType = 0;
				break;
			}
			
			
			
		} catch (ParseException e) {
			logger.error(String.format("Error in TrafficCSVRecord: %s %s %s", dateTime, lotId, lotName));
			logger.error(e.getMessage(), e);
		}
		
		this.time = timeParts[0]+":"+timeParts[1]; 
		this.date = dateTimeParts[1];
		this.timeId = findTimeRange(this.time);
		this.lotId = lotId;
		this.cityId = cityId;
		this.lotName = lotName;
		this.totalJamINRadius_1 = totalJamINRadius_1;
		this.totalJamOUTRadius_1 = totalJamOUTRadius_1;
		this.totalJamINRadius_2 = totalJamINRadius_2;
		this.totalJamOUTRadius_2 = totalJamOUTRadius_2;
		this.totalJamINRadius_3 = totalJamINRadius_3;
		this.totalJamOUTRadius_3 = totalJamOUTRadius_3;
		this.avgINForRadius_1 = avgINForRadius_1;
		this.avgOUTForRadius_1 = avgOUTForRadius_1;
		this.avgINForRadius_2 = avgINForRadius_2;
		this.avgOUTForRadius_2 = avgOUTForRadius_2;
		this.avgINForRadius_3 = avgINForRadius_3;
		this.avgOUTForRadius_3 = avgOUTForRadius_3;
	}
	public String getDate() {
		return date;
	}
	public String getLotId() {
		return lotId;
	}
	public String getCityId() {
		return cityId;
	}
	public String getLotName() {
		return lotName;
	}
	public String getTotalJamINRadius_1() {
		return totalJamINRadius_1;
	}
	public String getTotalJamOUTRadius_1() {
		return totalJamOUTRadius_1;
	}
	public String getTotalJamINRadius_2() {
		return totalJamINRadius_2;
	}
	public String getTotalJamOUTRadius_2() {
		return totalJamOUTRadius_2;
	}
	public String getTotalJamINRadius_3() {
		return totalJamINRadius_3;
	}
	public String getTotalJamOUTRadius_3() {
		return totalJamOUTRadius_3;
	}
	public String getAvgINForRadius_1() {
		return avgINForRadius_1;
	}
	public String getAvgOUTForRadius_1() {
		return avgOUTForRadius_1;
	}
	public String getAvgINForRadius_2() {
		return avgINForRadius_2;
	}
	public String getAvgOUTForRadius_2() {
		return avgOUTForRadius_2;
	}
	public String getAvgINForRadius_3() {
		return avgINForRadius_3;
	}
	public String getAvgOUTForRadius_3() {
		return avgOUTForRadius_3;
	}
	public String getTime() {
		return time;
	}
	public int getDayType() {
		return dayType;
	}
	public int getFreeParkingSpaces() {
		return freeParkingSpaces;
	}
	public void setFreeParkingSpaces(int freeParkingSpaces) {
		this.freeParkingSpaces = freeParkingSpaces;
	}
	
	public String getTimeId() {
		return timeId;
	}
	
	private String findTimeRange(String time){
		
		String [] timeParts = time.split(":");
		String minuteRange = "";
		int minutes = Integer.parseInt(timeParts[1]);
		
		if (minutes>=0 && minutes<4){
			minuteRange = "00";
		} else if (minutes>=4 && minutes<8){
			minuteRange = "04";
		} else if (minutes>=8 && minutes<12){
			minuteRange = "08";
		} else if (minutes>=12 && minutes<16){
			minuteRange = "12";
		} else if (minutes>=16 && minutes<20){
			minuteRange = "16";
		} else if (minutes>=20 && minutes<24){
			minuteRange = "20";
		} else if (minutes>=24 && minutes<28){
			minuteRange = "24";
		} else if (minutes>=28 && minutes<32){
			minuteRange = "28";
		} else if (minutes>=32 && minutes<36){
			minuteRange = "32";
		} else if (minutes>=36 && minutes<40){
			minuteRange = "36";
		} else if (minutes>=40 && minutes<44){
			minuteRange = "40";
		} else if (minutes>=44 && minutes<48){
			minuteRange = "44";
		} else if (minutes>=48 && minutes<52){
			minuteRange = "48";
		} else if (minutes>=52 && minutes<56){
			minuteRange = "52";
		} else if (minutes>=56 && minutes<=59){
			minuteRange = "56";
		}
		
		return timeParts[0]+":"+minuteRange;
	}
	
	@Override
	public String toString() {
		return date + ";" + time + ";" + timeId+ ";" +dayType + ";" + lotId
				+ ";" + cityId + ";" + lotName + ";" +freeParkingSpaces+";"+ totalJamINRadius_1
				+ ";" + totalJamOUTRadius_1 + ";" + totalJamINRadius_2
				+ ";" + totalJamOUTRadius_2 + ";" + totalJamINRadius_3
				+ ";" + totalJamOUTRadius_3 + ";" + avgINForRadius_1
				+ ";" + avgOUTForRadius_1 + ";" + avgINForRadius_2
				+ ";" + avgOUTForRadius_2 + ";" + avgINForRadius_3
				+ ";" + avgOUTForRadius_3 + "\n";
	}
		
}
