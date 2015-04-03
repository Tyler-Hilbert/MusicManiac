package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * The main view for the application.
 * Loads the songs and saves them inside the action listener (in the loadSongs() method).
 * Instantiates the player pane.
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
               
        // Set up grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        loadSongs(grid);
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(grid);
        root.setCenter(sp);
        
        // Setup play pane
        addPlayerPane(root);
    }
    
    /**
     * Loads the songs to the view.
     * 
     * It instantiates the SongLoader class to load songs.
     * Adds songs to grid.
     * Adds action listeners that play the song.
     */
    private void loadSongs(GridPane grid) {
        File dir = new File("D:\\Music\\Current");
        
        // Loads songs from dir
        SongLoader loader = new SongLoader();
        ArrayList<Song> songs = loader.loadSongs(dir);
        
        // Add to form
        for (int i = 0; i < songs.size(); i++) {
            Song song = new Song(songs.get(i).getPath(), songs.get(i).getName());
            
            Label songLabel = new Label(song.getName());

            songLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    playerPane.playSong(song);
                }
            });
            
            grid.add(songLabel, 0, i);
        }
    }
 
    private void addPlayerPane(BorderPane root) {
        playerPane = new PlayerPane();
        root.setTop(playerPane);
    }
}
