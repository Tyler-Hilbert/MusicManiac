package musicmaniac;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
    
    public SongsPane(ArrayList<Song> songs, PlayerPane playerPane) {
        // Create vbox
        VBox songsVBox = new VBox();
        songsVBox.setPadding(new Insets(10));
        songsVBox.setSpacing(8);
        
        addSongsToVBox(songsVBox, songs, playerPane);
        
        this.setContent(songsVBox);        
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
