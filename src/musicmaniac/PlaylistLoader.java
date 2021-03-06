package musicmaniac;

import java.io.File;
import java.util.ArrayList;



public class PlaylistLoader {
    
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    
    PlaylistLoader() {
        loadDirectory(new File(Playlist.dirPath));
    }
    
    private void loadDirectory(File dir) {
        if (dir.listFiles() != null) {
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
    }
    
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
