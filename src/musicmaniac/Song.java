package musicmaniac;

import java.io.Serializable;

public class Song implements Serializable{
    private String path;
    private String name;
    private int length;
    private String artist;
    private String album;
    
    public Song(String path, String name, String artist, String album, int length) {
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
    }
    
    
    /**
     * Checks if the song contains the string looked for
     * @param searchString The token being looked for
     * @return if the song has the token being looked for
     */
    public Boolean contains(String searchString) {
        if (name.toLowerCase().contains(searchString))
            return true;
        else if (artist.toLowerCase().contains(searchString))
            return true;
        else if (album.toLowerCase().contains(searchString))
            return true;
        else
            return false;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getName() {
        return name;
    }
    
    public int getLength() {
        return length;
    }
    
    public String getArtist() {
        return artist;
    }
   
    public String getAlbum() {
        return album;
    }
    

}
