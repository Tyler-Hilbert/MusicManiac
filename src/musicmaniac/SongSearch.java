package musicmaniac;

import java.util.ArrayList;


public class SongSearch {
    
    /**
     * Returns all songs found in the search
     * @param songs List of all the songs
     * @param searchString The string searched for
     * @return The found songs
     */
    public ArrayList<Song> getSongs(ArrayList<Song> songs, String searchString) {
        // Check if something was searched for
        String token = searchString.toLowerCase().trim();
        if (token.isEmpty()) {
            return songs;
        }
        
        ArrayList<Song> foundSongs = new ArrayList<>();
        songs.stream().forEach((song) -> {
            if (song.contains(token))
                foundSongs.add(song);
        });
        return foundSongs;
    }
    
}
