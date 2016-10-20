package musicmaniac;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PlaylistMenu extends Menu {
    PlaylistMenu() {
        this.setText("Playlists");
        
        // Create new playlist
        MenuItem newPlaylist = new MenuItem("Create playlist");
        this.getItems().add(newPlaylist);   
        
        newPlaylist.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Playlist playlist = new Playlist();
                MusicManiac.playlists.add(playlist);
                addPlaylistToMenu(playlist);
            }
        }); 
        
        // Add playlists
        if (MusicManiac.playlists != null) {
            for (Playlist playlist : MusicManiac.playlists) {
                addPlaylistToMenu(playlist);
            }
        }
    }
    
    private void addPlaylistToMenu(Playlist playlist) {
        MenuItem playlistItem = new MenuItem(playlist.getName());
        this.getItems().add(playlistItem);

        playlistItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                SavedSongLoader loader = new SavedSongLoader();
                ArrayList<Song> foundSongs = loader.loadSongs(playlist.getPath());

                MusicManiac.songsPane.updateSongs(foundSongs);
                MusicManiac.songs = foundSongs;
                MusicManiac.playerPane.setSongs(foundSongs);
            }
        });  
    }
}
