package musicmaniac;

public class Song {
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
