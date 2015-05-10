package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
    
    public static File dir; // The dir of the songs 

    
    @Override
    public void start(Stage primaryStage) {     
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resources/img/logo.png")));
        primaryStage.setResizable(false);
        
        // Setup scene and root
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add("resources/css/style.css");
         
        // Setup stage
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        // Loads songs and playlists
        SongDirSelector dirSelector = new SongDirSelector();
        dir = dirSelector.loadDefaultDirectory(primaryStage);
        if (!dir.exists()) { // Close program if can't get a valid songs directory.
            return;
        }
        SongLoader loader = new SongLoader();
        ArrayList<Song> songs = loader.loadSongs();
       
        PlaylistLoader playlistLoader = new PlaylistLoader();
        ArrayList<Playlist> playlists = playlistLoader.getPlaylists();
        
        // Create PlayerPane
        PlayerPane playerPane = new PlayerPane(songs, scene);
        
        // Add SongsPane     
        SongsPane songsPane = new SongsPane(songs, playerPane, playlists);
        root.setCenter(songsPane);
        
        // Add SearchPane
        SearchPane searchPane = new SearchPane();
        root.setBottom(searchPane);
        searchPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    SongSearch songSearch = new SongSearch();
                    ArrayList<Song> foundSongs = songSearch.getSongs(songs, searchPane.getSearch());
                    
                    SongsPane songsPane = new SongsPane(foundSongs, playerPane, playlists);
                    root.setCenter(songsPane);
                    
                    playerPane.setSongs(foundSongs);
                }
            }
        });
        
        // Create menu
        // File menu
        Menu fileMenu = new Menu("File");
        
        MenuItem refresh = new MenuItem("Refresh library");
        refresh.setOnAction(actionEvent -> refreshLibrary(root, playerPane));
        fileMenu.getItems().add(refresh);
        
        MenuItem selectFolder = new MenuItem("Select folder");
        selectFolder.setOnAction(actionEvent -> selectFolder(root, playerPane, primaryStage));
        fileMenu.getItems().add(selectFolder);
        
        MenuItem search = new MenuItem("Search library");
        search.setOnAction(actionEvent -> searchPane.requestFocus());
        fileMenu.getItems().add(search);
        
        // Playlist menu
        PlaylistMenu playlistMenu = new PlaylistMenu(playerPane, root, playlists);
        
        // Add menu
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, playlistMenu);
        
        // Add top box (the menu and PlayerPane)
        VBox topBox = new VBox();
        topBox.getChildren().addAll(menuBar, playerPane);
        
        root.setTop(topBox);        
    }
    
    /**
     * Reloads all the songs from the directory (static class variable dir).
     */
    private void refreshLibrary(BorderPane root, PlayerPane playerPane) {
        // Load songs
        SongLoader loader = new SongLoader();
        ArrayList<Song> songs = loader.reloadSongs();
        
        PlaylistLoader playlistLoader = new PlaylistLoader();
        ArrayList<Playlist> playlists = playlistLoader.getPlaylists();
    
        // Add songs view
        SongsPane songsPane = new SongsPane(songs, playerPane, playlists);
        root.setCenter(songsPane);
        
        playerPane.setSongs(songs);
    }
    
    /**
     * Select the directory that contains the songs that should be displayed.
     */
    private void selectFolder(BorderPane root, PlayerPane playerPane, Stage primaryStage) {        
        playerPane.stop();
        
        SongDirSelector dirSelector = new SongDirSelector();
        File selectedDir = dirSelector.selectNewDir(primaryStage);
        
        // Check if valid directory was choosen
        if (selectedDir.exists()) {
            dir = selectedDir;
            refreshLibrary(root, playerPane);
        }
    }
    
 
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
