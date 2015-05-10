package musicmaniac;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class SavedSongLoader {
    
    public ArrayList<Song> loadSongs(String filePath) {
        try {
            File loadedSongsData = new File(filePath);
            FileInputStream fis = new FileInputStream(loadedSongsData);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Song> songs = (ArrayList<Song>) ois.readObject();
            ois.close();
            return songs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
