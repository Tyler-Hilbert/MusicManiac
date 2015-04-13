package musicmaniac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class SongsList extends ArrayList<Song> {
    
    int currentSong = -1; // Index of the song playing in the list
    
    public SongsList() {
        super();
    }

    public SongsList(ArrayList<Song> songs) {
        super(songs);
    }
    
    public void startSongsList() {
        long seed = System.nanoTime();
        Collections.shuffle(this, new Random(seed));
        currentSong = -1;
    }
    
    public Song getNextSong() {
        currentSong = (currentSong + 1) % this.size(); // Gets next song or resets list
        
        return this.get(currentSong);
    }
    
    public Song getPreviousSong() {
        currentSong--;
        if (currentSong < 0) 
            currentSong = this.size() -1;
        
        return this.get(currentSong);
    }
    
    /**
     * Set the song in the songs list that is playing
     * @param song the song that is being played
     */
    public void setPlayingSong(Song song) {
        for (int i = 0; i < this.size(); i++) {
            if (song == this.get(i)) {
                currentSong = i;
                return;
            }
        }
    }
}
