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
    private File configFile;
    
    
    public SongDirSelector () {
        String filePath = "C:\\Users\\Tyler\\Documents\\MusicManiac\\dir.dat";
        configFile = new File(filePath);
    }
    
       
    /**
     * Loads the default directory of songs to be loaded
     * @return The directory to load songs from.
     */
    public File loadDefaultDirectory() {
        // Create config file if it doesn't exist.
        if (!configFile.exists()) {
            return selectNewDir();
        }
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            File dir = new File(br.readLine());
            
            // Check if dir read is valid
            if (!dir.exists()) {
                return selectNewDir();
            } else {
                return dir;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return selectNewDir();
        }
    }
    
    
    /**
     * Allows the user to select a new default directory
     * @return The selected directory
     */
    public File selectNewDir() {
        // Choose dir
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(MusicManiac.primaryStage);
       
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
