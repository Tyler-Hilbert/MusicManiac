package musicmaniac;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The view that has all of the songs loaded in it. 
 * Includes the action listeners for playing a song when clicked on.
 */
public class SongsView {    
    /**
     * Adds the VBox and ScrollPane that has all the songs 
     * @param songs all the songs
     * @param playerPane the song playing pane
     * @return a ScrollPane that has all songs within it.
     */
    public ScrollPane getSongsPane(ArrayList<Song> songs, PlayerPane playerPane) {
        // Create vbox
        VBox songsVBox = new VBox();
        songsVBox.setPadding(new Insets(10));
        songsVBox.setSpacing(8);
        
        addSongsToVBox(songsVBox, songs, playerPane);
        
        // Create sp to hold vbox so you can scroll through songs
        ScrollPane sp = new ScrollPane();
        sp.setContent(songsVBox);        
        
        return sp;
    }
    
    /**
     * adds the songs HBoxs to the songsVBox.
     */
    private void addSongsToVBox(VBox songsVBox, ArrayList<Song> songs, PlayerPane playerPane) {
        // Add to form
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            
            HBox songHBox = new HBox();
            
            // Create labels
            Label titleLabel = new Label(song.getName());
            Label lengthLabel = new Label(Util.secondsToMinutes(song.getLength()));
            Label artistLabel = new Label(song.getArtist());
            Label albumLabel = new Label(song.getAlbum());
         
            titleLabel.setPrefWidth(300);
            artistLabel.setPrefWidth(300);
            albumLabel.setPrefWidth(300);
            
            songHBox.getChildren().addAll(titleLabel, albumLabel, artistLabel, lengthLabel);

            // Add song action listener
            songHBox.setOnMousePressed((MouseEvent me) -> {
                playerPane.playSong(song);
            });
            
            songsVBox.getChildren().add(songHBox);
        }
    }    
}
