package traffic.data.analysator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import here.traffic.flow.api.FI;
import here.traffic.flow.api.HereTrafficML;
import here.traffic.flow.api.HereTrafficRecord;

public class Util {
	
	static Logger logger = Logger.getLogger(Util.class.getName());

	public static List<HereTrafficRecord> parseCSVFile (File csvFile){

		BufferedReader br = null;
		FileReader fr = null;

		ArrayList<HereTrafficRecord> result = new ArrayList<HereTrafficRecord>();
		
		try {
			fr = new FileReader(csvFile);
			br = new BufferedReader(fr);
			
			String currentLine;
			while ((currentLine = br.readLine())!=null){
				
				String[] csvColumns = currentLine.split(";");
				if(csvColumns.length==3){
					try {
						Gson gson = new Gson();
						HereTrafficRecord htr = new HereTrafficRecord(gson.fromJson(csvColumns[2], HereTrafficML.class),
							csvColumns[0], csvColumns[1]);
						result.add(htr);
					} catch (Exception e){
						logger.error(String.format("Error in parseCSVFile: %s", csvFile.getName()));
						logger.error(e.getMessage(),e);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(String.format("Error in parseCSVFile: %s", csvFile.getName()));
			logger.error(e.getMessage(),e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}

				if (fr != null) {
					fr.close();
				}
				
			} catch (Exception e) {
				logger.error(String.format("Error in parseCSVFile: %s", csvFile.getName()));
				logger.error(e.getMessage(),e);
			}
		}
		
		return result;
		
	}
	
	public static void validateTheList (List<HereTrafficML> trafficInfo) {
		
		if (trafficInfo.size()>0){
			List <FI> validatorFis = trafficInfo.get(0).getAllFI();
			int currentItem = 0;
			for (HereTrafficML hereTrafficML : trafficInfo) {
				System.out.println("Checing Item "+currentItem);
				List <FI> tempFis = hereTrafficML.getAllFI();
				for(int i = 0; i<validatorFis.size(); i++){
					if(!validatorFis.get(i).isEqual(tempFis.get(i))){
						throw new RuntimeException("NOT EQUAL");
					}
				}
				
				System.out.println("Item "+currentItem+": OK");
				currentItem++;
			}
			
			System.out.println("***ALL ARE EQUAL***");
		}
	}
	
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static int distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return (int) Math.sqrt(distance);
	}
	
	public static void listDirectory(File directory, ArrayList<File> files) {

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	listDirectory(file, files);
	        }
	    }
	}
	
	public static City getCityFromFileName (String fileName){
		
		if (fileName.contains("annarbor_traffic.csv")){
			return City.AnnArbor;
		} else if (fileName.contains("asheville_traffic.csv")){
			return City.Asheville;
		} else if (fileName.contains("madison_traffic.csv")){
			return City.Madison;
		} else if (fileName.contains("pittsburgh_traffic.csv")){
			return City.Pittsburgh;
		} else if (fileName.contains("santabarbara_traffic.csv")){
			return City.SantaBarbara;
		} else if (fileName.contains("santamonica_traffic.csv")){
			return City.SantaMonica;
		} else if (fileName.contains("seattle_traffic.csv")){
			return City.Seattle;
		} else if (fileName.contains("winchester_traffic.csv")){
			return City.Winchester;
		}
		
		return null;
	}
	
	public static City getCityFromProcessedFile (String fileName){
		
		if (fileName.contains("annarbor_traffic-Processed.csv")){
			return City.AnnArbor;
		} else if (fileName.contains("asheville_traffic-Processed.csv")){
			return City.Asheville;
		} else if (fileName.contains("madison_traffic-Processed.csv")){
			return City.Madison;
		} else if (fileName.contains("pittsburgh_traffic-Processed.csv")){
			return City.Pittsburgh;
		} else if (fileName.contains("santabarbara_traffic-Processed.csv")){
			return City.SantaBarbara;
		} else if (fileName.contains("santamonica_traffic-Processed.csv")){
			return City.SantaMonica;
		} else if (fileName.contains("seattle_traffic-Processed.csv")){
			return City.Seattle;
		} else if (fileName.contains("winchester_traffic-Processed.csv")){
			return City.Winchester;
		}
		
		return null;
	}
}
