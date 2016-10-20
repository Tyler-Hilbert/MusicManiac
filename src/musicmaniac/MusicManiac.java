package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main for the application.
 * Contains and instantiates all the components 
 */
public class MusicManiac extends Application {
    
    static File                     dir; // The dir of the songs 
    static BorderPane               root;
    static ArrayList<Song>          songs;
    static ArrayList<Playlist>      playlists;
    static PlayerPane               playerPane;
    static SongsPane                songsPane;
    static SearchPane               searchPane;
    static PlaylistMenu             playlistMenu;
    static Stage                    primaryStage;
    

    
    @Override
    public void start(Stage primaryStage) {     
        MusicManiac.primaryStage = primaryStage;
        
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resources/img/logo.png")));
        primaryStage.setResizable(false);
        
        // Setup scene and root
        root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add("resources/css/style.css");
         
        // Setup stage
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        // Loads songs and playlists
        SongDirSelector dirSelector = new SongDirSelector();
        dir = dirSelector.loadDefaultDirectory();
        
        if (!dir.exists()) { // Close program if can't get a valid songs directory.
            return;
        }
        SongLoader loader = new SongLoader();
        songs = loader.loadSongs();
       
        //PlaylistLoader playlistLoader = new PlaylistLoader();
        //playlists = playlistLoader.getPlaylists();
        
        
        
        playerPane = new PlayerPane();
    
        songsPane = new SongsPane();
        root.setCenter(songsPane);

        searchPane = new SearchPane();
        root.setBottom(searchPane);
        
        MenuBar menuBar = new MenuBar();
        
        // Add top box (the menu and PlayerPane)
        VBox topBox = new VBox();
        topBox.getChildren().addAll(menuBar, playerPane);
        
        root.setTop(topBox);        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
