package musicmaniac;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Playlist implements Serializable{
    final static String dirPath = "playlists\\";
    private final static String ext = ".tmp"; // The file extension for playlists   
    
    private String path;    
    
    /** 
     * Creates the playlist file.
     * It will only be called once to initialize file.
     */
    Playlist () {
        // Find valid unique path
        File file = new File(dirPath + "playlist" + ext);
        int count = 0;
        while (file.exists()){
            file = new File(dirPath + "playlist" + count + ext);
            count++;
        }
        this.path = file.toString();
        
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ArrayList<Song> songs = new ArrayList<Song>();
            oos.writeObject(songs);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /** 
     * Loads an already created playlist
     */
    Playlist(String path) {
        this.path = path;
    }
    
    
    /**
     * Adds a song to the playlist
     * @param song the song that will be added to the playlist
     */
    public void addSong(Song song) {
        SavedSongLoader loader = new SavedSongLoader();
        ArrayList<Song> songs = loader.loadSongs(path);
        songs.add(song);
        
       try {
            File playlistData = new File(path);
            FileOutputStream fos = new FileOutputStream(playlistData);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(songs);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
       
    public String getPath() {
        return path;
    }
    
    public String getName() {
        return path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.'));
    }
    
    public void rename(String name) {
        File oldFile = new File(path);
        File newFile = new File(path.toString().substring(0, path.lastIndexOf('\\')+1) + name + ".tmp");
                
        if(!newFile.exists()){
            oldFile.renameTo(newFile);
        }
    }
}
