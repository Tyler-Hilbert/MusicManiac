package musicmaniac;

import java.io.File;
import java.util.ArrayList;



public class PlaylistLoader {
    
    public ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    
    PlaylistLoader() {
        loadDirectory(new File(Playlist.dirPath));
    }
    
    private void loadDirectory(File dir) {
        for (File playlistFile : dir.listFiles()) {
            if (playlistFile.isDirectory()) {
                loadDirectory(playlistFile);
            } else {
                if (playlistFile.toString().toLowerCase().contains(".tmp")) {
                    playlists.add(new Playlist(playlistFile.toString())); 
                }
            }
        }
    }
    
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
