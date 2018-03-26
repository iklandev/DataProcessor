package traffic.parking.merge;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import here.traffic.flow.api.Lot;
import traffic.data.analysator.City;
import traffic.data.analysator.Util;

public class TrafficParkingMerge {

	static Logger logger = Logger.getLogger(TrafficParkingMerge.class.getName());
	
	private ArrayList<File> trafficFiles = new ArrayList<File>();

	private ArrayList<File> parkingFiles = new ArrayList<File>();
	
	private String mergedDirectory;

	public TrafficParkingMerge(File directoryRoot) {
		super();
		ArrayList<File> tempFiles = new ArrayList<File>();
		Util.listDirectory(directoryRoot, tempFiles);
		separateParkingTrafficFiles(tempFiles);
		this.mergedDirectory = tempFiles.get(0).getParentFile().getParent();
	}

	public void mergeFiles() {
        int totalFiles = this.trafficFiles.size();
        int currentFile = 0;
        String message = String.format("Start merging %s files", totalFiles);
		fireMyEvent(new MergeEvent(this, message));
		for (File trafficFile : this.trafficFiles) {
			currentFile++;
			File parkingFile = findAppropriateParkingFileForTrafficFile(trafficFile.getName());
			if (parkingFile != null) {
				// Start read traffic file
				
				FileReader fr = null;
				BufferedReader br = null;
				String fileName = parkingFile.getName().replace("_traffic-Processed", "");
				try {

					String line;

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
					
					message = String.format("%s: Merging %s/%s, file %s ...\n",
							sdf.format(new Date()), currentFile, totalFiles, parkingFile.getName());
					fireMyEvent(new MergeEvent(this, message));

					City city = Util.getCityFromProcessedFile(trafficFile.getName());

					if (city != null) {
						List<Lot> lots = Lot.getLotsForCity(city.getId());
						for (Lot lot : lots) {
							fr = new FileReader(trafficFile);
							br = new BufferedReader(fr);
							Date from = null;
							Date to = null;
							while ((line = br.readLine()) != null) {
								
								String[] csvParts = line.split(";");
								if(lot.getId().equals(csvParts[1])){
								TrafficCSVRecord tcr = new TrafficCSVRecord(csvParts[0], csvParts[1], csvParts[2],
										csvParts[3], csvParts[4], csvParts[5], csvParts[6], csvParts[7], csvParts[8],
										csvParts[9], csvParts[10], csvParts[11], csvParts[12], csvParts[13],
										csvParts[14], csvParts[15]);

								to = sdf.parse(csvParts[0]);
								if (from == null) {
									Calendar c = Calendar.getInstance();
									c.setTime(to);
									c.set(Calendar.HOUR_OF_DAY, 0);
									c.set(Calendar.MINUTE, 0);
									c.set(Calendar.SECOND, 0);
									c.set(Calendar.MILLISECOND, 0);
									from = c.getTime();
								}

								Integer i = findAvgParkingSpacesForParkingLotAndDataRange(from, to, parkingFile,
										tcr.getLotId());
								if (i!=null){
									tcr.setFreeParkingSpaces(i);
									from = to;
									writeToCSV(tcr, fileName);
								}
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error(String.format("Error in TrafficCSVRecord: %s %s", trafficFile.getName(), parkingFile.getName()));
					logger.error(e.getMessage(),e);
				} finally {
					try {
						if (fr != null) {
							fr.close();
						}
						if (br != null) {
							br.close();
						}
					} catch (Exception e) {
						logger.error(String.format("Error in TrafficCSVRecord: %s %s", trafficFile.getName(), parkingFile.getName()));
						logger.error(e.getMessage(),e);
					}

				}

			}
		}
		
		fireMyEvent(new MergeEvent(this, "Finish with merging"));
	}

	private Integer findAvgParkingSpacesForParkingLotAndDataRange(Date from, Date to, File parkingFile, String lotId) throws Exception {
		
		FileReader fr = new FileReader(parkingFile);
		BufferedReader bf = new BufferedReader(fr);
		
		int avgCount=0;
		int freeSpaces=0;
		String line = null;
		while ((line=bf.readLine())!=null) {
			String[] csvParts = line.split(";");
			if(lotId.equals(csvParts[1])){
				ParkingCSVRecord pcr = new ParkingCSVRecord(csvParts[0], csvParts[1], csvParts[4]);
				if(pcr.isBetweenDateRange(from, to)){
					avgCount++;
					freeSpaces+=pcr.getFreeSpaces();
				}
			}
			
		}
		
		if (avgCount>0){
			return freeSpaces/avgCount;
		} 
		
		return null;
	}

	private File findAppropriateParkingFileForTrafficFile(String trafficFileName) {

		for (File f : this.parkingFiles) {
			if (f.getName().equals(trafficFileName.replace("_traffic-Processed", ""))) {
				return f;
			}
		}

		return null;
	}

	private void separateParkingTrafficFiles(ArrayList<File> files) {

		for (File file : files) {
			if (file.getName().contains("_traffic-Processed.csv")) {
				this.trafficFiles.add(file);
			} else {
				this.parkingFiles.add(file);
			}
		}
	}
	
	private void writeToCSV(TrafficCSVRecord tcr, String fileName) throws Exception{
		Path directoryPath = Paths.get(this.mergedDirectory, "Merged");
		if (Files.notExists(directoryPath)){
			Files.createDirectory(directoryPath);
		}
		Path filePath = Paths.get(this.mergedDirectory, "Merged", fileName);
		Files.write(filePath, tcr.toString().getBytes(), CREATE, APPEND);
	}
	
	 protected EventListenerList listenerList = new EventListenerList();

	  public void addMyEventListener(MergeEventListener listener) {
	    listenerList.add(MergeEventListener.class, listener);
	  }
	  public void removeMyEventListener(MergeEventListener listener) {
	    listenerList.remove(MergeEventListener.class, listener);
	  }
	  
	  void fireMyEvent(MergeEvent evt) {
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == MergeEventListener.class) {
	        ((MergeEventListener) listeners[i+1]).mergeEventOccurred(evt);
	      }
	    }
	  }

}
