package musicmaniac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Selects and saves the default directory to load songs from.
 */
public class SongDirSelector {
    // The file that contains config info for default directory
    private final static File configFile = new File("C:\\Users\\Tyler\\Documents\\NetBeansProjects\\MusicManiac\\src\\resources\\config\\dir.dat");
    
       
    /**
     * Loads the default directory of songs to be loaded
     * @return The directory to load songs from.
     */
    public File loadDefaultDirectory(Stage primaryStage) {
        // Create config file if it doesn't exist.
        if (!configFile.exists()) {
            return selectDefaultDirectory(primaryStage);
        }
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            File dir = new File(br.readLine());
            
            // Check if dir read is valid
            if (!dir.exists()) {
                return selectDefaultDirectory(primaryStage);
            } else {
                return dir;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return selectDefaultDirectory(primaryStage);
        }
    }
    
    
    /**
     * Recreates config file and forces a default directory to be selected.
     */
    private File selectDefaultDirectory(Stage primaryStage) {
        // Choose dir
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        
        saveDir(selectedDirectory);
        
        return new File(selectedDirectory.getAbsolutePath());
    }
    
    /**
     * Allows the user to select a new default directory
     * @return The selected directory
     */
    public File selectNewDir(Stage primaryStage) {
        // Choose dir
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
       
        saveDir(selectedDirectory);
        
        return new File(selectedDirectory.getAbsolutePath());
    }
    
    /**
     * Saves the new directory so it will be loaded on startup.
     * @param selectedDir The new directory
     */
    private void saveDir(File selectedDir) {
        if (selectedDir.exists()) {
            try {
                PrintWriter writer = new PrintWriter(configFile, "UTF-8");
                writer.println(selectedDir.toString());
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}