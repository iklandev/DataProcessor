package traffic.data.analysator.view;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import traffic.csv.processor.TrafficCsvProcessor;
import traffic.data.analysator.City;
import traffic.data.analysator.Main;
import traffic.data.analysator.Util;
import traffic.parking.merge.MergeEvent;
import traffic.parking.merge.MergeEventListener;
import traffic.parking.merge.TrafficParkingMerge;

public class MainScreenController {
	
	static Logger logger = Logger.getLogger(MainScreenController.class.getName());

	@FXML
	private Button btnChoose;
	
	@FXML
	private Button btnProcess;
	
	@FXML
	private Button btnMerge;
	
	@FXML
	private Button btnFinalMerge;
	
	@FXML
	private Label infoLabel, lblRadius;
		
	@FXML
	private WebView mapView;
	
	@FXML
	private TextField tfR1;
	
	@FXML
	private TextField tfR2;
	
	@FXML
	private TextField tfR3;
	
	@FXML
	private TextArea logTextArea;
	
	private File directory;
	
	private ResourceBundle resourceBundle;

	// Reference to the main application.
    private Main mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MainScreenController() {
    	resourceBundle = Main.loadLang("en");
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	btnChoose.setText(resourceBundle.getString("choose.btn"));
    	btnProcess.setText(resourceBundle.getString("process"));
    	infoLabel.setText(resourceBundle.getString("noFileSelected"));
    	lblRadius.setText(resourceBundle.getString("radiusInfo"));
    	btnMerge.setText(resourceBundle.getString("btnMerge"));
    	btnFinalMerge.setText(resourceBundle.getString("btnFinalMerge"));
    	
    	addNumberFilter(tfR1);
    	addNumberFilter(tfR2);
    	addNumberFilter(tfR3);
    	
    	/*WebEngine webEngine = mapView.getEngine(); 
    	webEngine.load(getClass().getResource("map.html").toString());
    	*/
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void showChooseDialog (ActionEvent event){
    	
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setTitle("Choose resource directory");
    	
    	File selectedDirectory = directoryChooser.showDialog(this.mainApp.getPrimaryStage());
    	
    	/* FileChooser fileChooser = new FileChooser();
    	 fileChooser.setTitle("Open Resource File");
    	 fileChooser.getExtensionFilters().addAll(
    	         new ExtensionFilter("CSV Files", "*.csv"));
    	 File selectedFile = fileChooser.showOpenDialog(this.mainApp.getPrimaryStage());
    	 */
    	 if (selectedDirectory!=null){
    		directory = selectedDirectory;
    		String infoString = resourceBundle.getString("selectedFile")+" "+directory.getAbsolutePath();
    		infoLabel.setText(infoString);
    	 }
    }
    
    @FXML
    private void processFile (ActionEvent event){
    	
		if (directory != null && !tfR1.getText().isEmpty() && !tfR2.getText().isEmpty() && !tfR3.getText().isEmpty()) {
    		 
			processAllFiles(directory, Integer.parseInt(tfR1.getText()),
					Integer.parseInt(tfR2.getText()), Integer.parseInt(tfR3.getText()));

    		 //Show the geo tags on map
    		/*
    		 List<HereTrafficRecord> list =  Util.parseCSVFile(file); 
    		 List<Geotag> geoTags =  list.get(0).getHereTrafficML().getAllGeoTags();
    		 Gson gson = new Gson();
             String json = gson.toJson(geoTags);
             mapView.getEngine().executeScript("addMarkers("+json+")");
             */
    	 }
    }
    
    private void processAllFiles (File directoryRoot, int radius_1, int radius_2, int radius_3){
    	
    	logTextArea.appendText("Start processing csv traffic files ...\n");
    	new Thread(() -> {
    		// Insert some method call here.
    		ArrayList<File> files = new ArrayList<File>();
    		Util.listDirectory(directoryRoot, files);

    		try {
    			int currentFile = 0;
    			final int totalFiles = files.size();
    			final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    			for (File file : files) {
    				currentFile++;

    				final String currentFileName = file.getName();
    				final int currentFileTemp = currentFile;;
    				City city = Util.getCityFromFileName(file.getName());

    				if (city!=null){
    					Platform.runLater(new Runnable() {
    						@Override public void run() {
    							logTextArea.appendText(String.format("%s: Processing %s/%s, file %s ...\n",
    									dateFormat.format(new Date()), currentFileTemp, totalFiles, currentFileName));
    						}
    					});
    					TrafficCsvProcessor tcp = new TrafficCsvProcessor(city, file, radius_1,
    							radius_2, radius_3);
    					tcp.processCSVFile();
    				} else {
    					Platform.runLater(new Runnable() {
    						@Override public void run() {
    							logTextArea.appendText(String.format("%s: Processing %s/%s, file %s don't match the template \n",
    									dateFormat.format(new Date()), currentFileTemp, totalFiles, currentFileName));
    						}
    					});
    				}
    			}	

    			Platform.runLater(new Runnable() {
    				@Override public void run() {
    					logTextArea.appendText(String.format("%s: Finish processing files \n",
    							dateFormat.format(new Date())));
    				}
    			});
    		} catch (Exception e){
    			logger.error(e.getMessage(),e);
    		}
    		
    	}).start();
    }
    
    private void addNumberFilter (TextField tf) {
    	tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    @FXML
    private void mergeTrafficAndParking (ActionEvent event){
    	
    	if (directory != null){
    		mergeAllFiles(directory);
    	}
    }
    
    private void mergeAllFiles (File directoryRoot){
    	new Thread(() -> {
    		TrafficParkingMerge tpm = new TrafficParkingMerge(directoryRoot);
    		
    		tpm.addMyEventListener(new MergeEventListener() {
    		      public void mergeEventOccurred(MergeEvent evt) {
    		    	  Platform.runLater(new Runnable() {
      		            @Override public void run() {
  							logTextArea.appendText(evt.getMessage());
  						}
      		        });
    		      }
    		    });
    		
    		tpm.mergeFiles();
    	}).start();

    }
    
    @FXML
    private void merge (ActionEvent event){
    	if (directory != null){
    		logTextArea.appendText("Start merging csv traffic files ...\n");
        	new Thread(() -> {
        		
        		Path filePath = Paths.get(directory.getAbsolutePath(), "Merged.csv");

        	    // Insert some method call here.
        		ArrayList<File> files = new ArrayList<File>();
        		Util.listDirectory(directory, files);

        		int currentFile = 0;
        		final int totalFiles = files.size();
        		final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        		for (File file : files) {
        			currentFile++;
        			
        			final String currentFileName = file.getName();
        			final int currentFileTemp = currentFile;;
        			if(currentFileName.contains(".csv") && !currentFileName.contains("merged.csv")){
        				Platform.runLater(new Runnable() {
        		            @Override public void run() {
        		            	logTextArea.appendText(String.format("%s: Merging %s/%s, file %s ...\n",
    									dateFormat.format(new Date()), currentFileTemp, totalFiles, currentFileName));
        		            }
        		        });
        				try {
							FileReader fr = new FileReader(file);
							BufferedReader br = new BufferedReader(fr);
							
							String line;
							while((line=br.readLine())!=null){
								Files.write(filePath, (line+"\n").getBytes(), CREATE, APPEND);
							}
							
							if(currentFile==totalFiles){
								Platform.runLater(new Runnable() {
		        		            @Override public void run() {
		        		            	logTextArea.appendText(String.format("%s:Finsih...\n",
		    									dateFormat.format(new Date())));
		        		            }
		        		        });
							}
							
						} catch (Exception e) {
							Platform.runLater(new Runnable() {
	        		            @Override public void run() {
	    							logTextArea.appendText(String.format("ERROR %s: Merging %s/%s, file %s. Error %s",
	    									dateFormat.format(new Date()), currentFileTemp, totalFiles, currentFileName, e.getMessage()));
	    						}
	        		        });
						}
        				
        			} else{
        				Platform.runLater(new Runnable() {
        		            @Override public void run() {
    							logTextArea.appendText(String.format("%s: Merging %s/%s, file %s don't match the template \n",
    									dateFormat.format(new Date()), currentFileTemp, totalFiles, currentFileName));
    						}
        		        });
        			}

        		}	
        	}).start();
    	}
    }
}
