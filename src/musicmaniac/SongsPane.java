package musicmaniac;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The view that has all of the songs loaded in it. 
 * Is a ScrollPane that has a VBox of songs within it
 * Includes the action listeners for playing a song when clicked on.
 */
public class SongsPane extends ScrollPane{
    
    public SongsPane(ArrayList<Song> songs, PlayerPane playerPane, ArrayList<Playlist> playlists) {
        this.getStyleClass().add("songs-pane");
        
        // Create vbox
        VBox songsVBox = new VBox();
        songsVBox.setPadding(new Insets(10));
        songsVBox.setSpacing(8);
        addSongsToVBox(songsVBox, songs, playerPane, playlists);
        this.setContent(songsVBox);    
    }
    
    
    /**
     * adds the songs HBoxs to the songsVBox.
     */
    private void addSongsToVBox(VBox songsVBox, ArrayList<Song> songs, PlayerPane playerPane, ArrayList<Playlist> playlists) {
        // Add to form
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            
            HBox songHBox = new HBox();
            songHBox.getStyleClass().add("song-hbox");

            // Create labels
            Label titleLabel = new Label(song.getName());
            Label lengthLabel = new Label(Util.secondsToMinutes(song.getLength()));
            Label artistLabel = new Label(song.getArtist());
            Label albumLabel = new Label(song.getAlbum());
         
            titleLabel.setPrefWidth(300);
            artistLabel.setPrefWidth(300);
            albumLabel.setPrefWidth(300);
            
            songHBox.getChildren().addAll(titleLabel, albumLabel, artistLabel, lengthLabel);            
            
            // Context menu
            final ContextMenu contextMenu = new ContextMenu();
            
            for (Playlist playlist : playlists) {
                MenuItem playlistItem = new MenuItem("add to " + playlist.getName());
                playlistItem.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        playlist.addSong(song);
                    }
                });
                contextMenu.getItems().add(playlistItem);
            }

            titleLabel.setContextMenu(contextMenu);
            
            
            // Add song action listener
            songHBox.setOnMousePressed((MouseEvent me) -> {
                if (me.isPrimaryButtonDown()) {
                    playerPane.playSelectedSong(song);
                } else if (me.isSecondaryButtonDown()) {
                    contextMenu.show(songHBox, me.getScreenX(), me.getScreenY());
                }
            });
            
       
            
            songsVBox.getChildren().add(songHBox);
        }
    }    
}
