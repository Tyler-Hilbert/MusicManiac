package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Loads the songs
 */
public class SongLoader {
    final private static List<String> ext = Arrays.asList(".mp3", ".wav", ".m4a"); // The compatiable file formats
    
    /**
     * Loads songs from a directory
     * @param dir the directory to load from
     * @return an arraylist of the loaded songs
     */
    public ArrayList<Song> loadSongs(File dir) {
        ArrayList<Song> songs = new ArrayList<Song>();
       
        loadDirectory(dir, songs);
        
        return songs;
    }
        
    /**
     * Loads all songs in a directory and calls itself back up if another directory is found.
     * @param dir the directory to load from
     * @param songs the arraylist of loaded songs
     */
    private void loadDirectory(File dir, ArrayList<Song> songs) {
        for (File songFile : dir.listFiles()) {
            if (songFile.isDirectory()) {
                loadDirectory(songFile, songs);
            } else {
                String title = songFile.getName();
                String extension = title.substring(title.lastIndexOf("."));
                if (ext.contains(extension)) {
                    title = title.substring(0, title.lastIndexOf(".")); // Remove file extension
                    songs.add(new Song(songFile.getPath(), title));
                }
            }
        }
    }
    
    
}
