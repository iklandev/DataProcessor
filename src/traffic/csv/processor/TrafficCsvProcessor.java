package traffic.csv.processor;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import here.traffic.flow.api.FI;
import here.traffic.flow.api.HereTrafficRecord;
import here.traffic.flow.api.Lot;
import traffic.data.analysator.City;
import traffic.data.analysator.Util;

/**
 * @author i_kla
 *
 */
public class TrafficCsvProcessor {
	
	static Logger logger = Logger.getLogger(TrafficCsvProcessor.class.getName());

	/**
	 * Lots for the city
	 */
	private List<Lot> lotsForTheCity;

	/**
	 * Decoded JSON response from the CSV file
	 */
	private List<HereTrafficRecord> trafficRecords;

	/**
	 * Radiuses for traffic rings/circles
	 */
	private int radius_1, radius_2, radius_3;

	private String filePath;

	public TrafficCsvProcessor(City city, File trafficCSVFile, int radius_1, int radius_2, int radius_3) {

		this.radius_1 = radius_1;
		this.radius_2 = radius_2;
		this.radius_3 = radius_3;
		this.filePath = trafficCSVFile.getAbsolutePath().replaceFirst(".csv", "-Processed.csv");

		// Get all lots for the city
		this.lotsForTheCity = Lot.getLotsForCity(city.getId());

		// Parse the CSV file
		this.trafficRecords = Util.parseCSVFile(trafficCSVFile);
	}

	public void processCSVFile() throws Exception {

		for (HereTrafficRecord htr : this.trafficRecords) {
			for (Lot lot : this.lotsForTheCity) {
				LotTrafficInfo lti = new LotTrafficInfo(lot, htr.getDateTime());
				for (FI fi : htr.getHereTrafficML().getAllFI()) {
					if (!fi.getCF().isEmpty() && !(fi.getCF().get(0).getJF().compareTo(new BigDecimal("-1")) == 0) ) {
						
						
						if (fi.isBelongToRadius(this.radius_1, lot)) {
							
							if (fi.getTrafficFlowDirection(lot) == TrafficFlowDirection.IN_FLOW){
								lti.incrementIncludedFiINRadius_1();
								lti.addJamForTotalJamINRadius_1(fi.getCF().get(0).getJF());
							} else {
								lti.incrementIncludedFiOUTRadius_1();
								lti.addJamForTotalJamOUTRadius_1(fi.getCF().get(0).getJF());
							}
						}

						if (fi.isBelongToRadius(this.radius_2, lot)) {
							
							if (fi.getTrafficFlowDirection(lot) == TrafficFlowDirection.IN_FLOW){
								lti.incrementIncludedFiINRadius_2();
								lti.addJamForTotalJamINRadius_2(fi.getCF().get(0).getJF());
							} else {
								lti.incrementIncludedFiOUTRadius_2();
								lti.addJamForTotalJamOUTRadius_2(fi.getCF().get(0).getJF());
							}
						}

						if (fi.isBelongToRadius(this.radius_3, lot)) {
							
							if (fi.getTrafficFlowDirection(lot) == TrafficFlowDirection.IN_FLOW){
								lti.incrementIncludedFiINRadius_3();
								lti.addJamForTotalJamINRadius_3(fi.getCF().get(0).getJF());
							} else {
								lti.incrementIncludedFiOUTRadius_3();
								lti.addJamForTotalJamOUTRadius_3(fi.getCF().get(0).getJF());
							}
						}

					}
				}
				
				//write in CSV for current Lot
				writeToCSVFile(lti);
			}
		}
	}
	
	private void writeToCSVFile(LotTrafficInfo lotTrafficInfo){
		Path filePath = Paths.get(this.filePath);
		StringBuilder csvLine = new StringBuilder();
		
		try {

			
			csvLine.append(lotTrafficInfo.getDateTime() + ";");
			csvLine.append(lotTrafficInfo.getLot().getId()+ ";");
			csvLine.append(lotTrafficInfo.getLot().getCityId()+ ";");
			csvLine.append(lotTrafficInfo.getLot().name()+ ";");
			
			csvLine.append(lotTrafficInfo.getTotalJamINRadius_1()+ ";");
			csvLine.append(lotTrafficInfo.getTotalJamOUTRadius_1()+ ";");
			
			csvLine.append(lotTrafficInfo.getTotalJamINRadius_2()+ ";");
			csvLine.append(lotTrafficInfo.getTotalJamOUTRadius_2()+ ";");
			
			csvLine.append(lotTrafficInfo.getTotalJamINRadius_3()+ ";");
			csvLine.append(lotTrafficInfo.getTotalJamOUTRadius_3()+ ";");
			
			csvLine.append(lotTrafficInfo.getAvgINForRadius_1()+ ";");
			csvLine.append(lotTrafficInfo.getAvgOUTForRadius_1()+ ";");
			
			csvLine.append(lotTrafficInfo.getAvgINForRadius_2()+ ";");
			csvLine.append(lotTrafficInfo.getAvgOUTForRadius_2()+ ";");
			
			csvLine.append(lotTrafficInfo.getAvgINForRadius_3()+ ";");
			csvLine.append(lotTrafficInfo.getAvgOUTForRadius_3()+ ";");
			
			csvLine.append("\n");
			Files.write(filePath, csvLine.toString().getBytes(), CREATE, APPEND);

		} catch (Exception e) {
			logger.error(String.format("Error in writeToCSVFile: %s %s", filePath.getFileName(), csvLine.toString()));
			logger.error(e.getMessage(), e);
		}
	}

	public List<Lot> getLotsForTheCity() {
		return lotsForTheCity;
	}

	public List<HereTrafficRecord> getTrafficRecords() {
		return trafficRecords;
	}

	public int getRadius_1() {
		return radius_1;
	}

	public int getRadius_2() {
		return radius_2;
	}

	public int getRadius_3() {
		return radius_3;
	}

	public String getFilePath() {
		return filePath;
	}
	

}
