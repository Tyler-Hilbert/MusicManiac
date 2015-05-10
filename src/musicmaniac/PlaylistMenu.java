package musicmaniac;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;

public class PlaylistMenu extends Menu {
    PlaylistMenu(PlayerPane playerPane, BorderPane root, ArrayList<Playlist> playlists) {
        this.setText("Playlists");
        
        // Create new playlist
        MenuItem newPlaylist = new MenuItem("Create playlist");
        this.getItems().add(newPlaylist);   
        
        newPlaylist.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Playlist playlist = new Playlist();
                playlists.add(playlist);
                addPlaylistToMenu(playlist, root, playerPane, playlists);
            }
        }); 
        
        // Add playlists
        for (Playlist playlist : playlists) {
            addPlaylistToMenu(playlist, root, playerPane, playlists);
        }
    }
    
    private void addPlaylistToMenu(Playlist playlist, BorderPane root, PlayerPane playerPane, ArrayList<Playlist> playlists) {
        MenuItem playlistItem = new MenuItem(playlist.getName());
        this.getItems().add(playlistItem);

        playlistItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                SavedSongLoader loader = new SavedSongLoader();
                ArrayList<Song> foundSongs = loader.loadSongs(playlist.getPath());

                SongsPane songsPane = new SongsPane(foundSongs, playerPane, playlists);
                root.setCenter(songsPane);

                playerPane.setSongs(foundSongs);
            }
        });  
    }
}
