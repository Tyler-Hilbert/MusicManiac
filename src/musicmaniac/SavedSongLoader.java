package musicmaniac;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SavedSongLoader {
    
    public ArrayList<Song> loadSongs() {
        try {
            File loadedSongsData = new File(MusicManiac.dir, "\\loadedSongsData.tmp");
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
