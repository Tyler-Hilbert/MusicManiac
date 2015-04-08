package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main for the application.
 * Instantiates the view.
 * 
 * 
 * The application layout is a borderPane that is the root.
 * The top of the root is playerPane.
 * The center is a VBox, songsVBox that has all the songs.
 * Each song is shown as an songsHBox within VBox
 */
public class MusicManiac extends Application {
    
    public static File dir = new File("D:\\Music\\Current"); // The dir of the songs and any saved files
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.png")));
        primaryStage.setResizable(false);
        
        // Setup scene and root
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);
         
        // Setup stage
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Loads songs
        SongLoader loader = new SongLoader();
        ArrayList<Song> songs = loader.loadSongs();
        
        // Add PlayerPane
        PlayerPane playerPane = new PlayerPane(songs, scene);
        root.setTop(playerPane);
        
        // Add SongsView     
        SongsView songsView = new SongsView();
        ScrollPane songsPane = songsView.getSongsPane(songs, playerPane);
        root.setCenter(songsPane);
    }
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
