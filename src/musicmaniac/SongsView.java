package musicmaniac;

import java.io.File;
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
 * The main view for the application.
 * Loads the songs and saves them inside the action listener (in the loadSongs() method).
 * Instantiates the player pane.
 
 * The application layout is a borderPane that is the root.
 * The top of the root is playerPane.
 * The center is a VBox, songsVBox that has all the songs.
 * Each song is shown as an songsHBox within VBox
 */
public class SongsView {
    
    PlayerPane playerPane; // The song playing pane
    

    public SongsView(Stage primaryStage) {
        // Setup scene and root
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
               
        addSongsVBox(root);
        addPlayerPane(root);
    }
    
    /**
     * Adds the VBox that has all the songs 
     */
    private void addSongsVBox(BorderPane root) {
        // Create vbox
        VBox songsVBox = new VBox();
        songsVBox.setPadding(new Insets(10));
        songsVBox.setSpacing(8);
        
        // Create sp to hold vbox so you can scroll through songs
        ScrollPane sp = new ScrollPane();
        sp.setContent(songsVBox);
        root.setCenter(sp);
        
        root.setCenter(sp);
        
        loadSongs(songsVBox);
    }
    
    /**
     * Loads the songs to the view.
     */
    private void loadSongs(VBox songsVBox) {
        File dir = new File("D:\\Music\\Current");
        
        // Loads songs from dir
        SongLoader loader = new SongLoader();
        ArrayList<Song> songs = loader.loadSongs(dir);
        
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
            songHBox.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    playerPane.playSong(song);
                }
            });
            
            songsVBox.getChildren().add(songHBox);
        }
    }
 
 
    /**
     * Adds the player pane that displays song and play/pause button
     */
    private void addPlayerPane(BorderPane root) {
        playerPane = new PlayerPane();
        root.setTop(playerPane);
    }
    
}
