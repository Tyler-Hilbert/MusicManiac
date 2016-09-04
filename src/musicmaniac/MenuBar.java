package musicmaniac;

import java.io.File;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


public class MenuBar extends javafx.scene.control.MenuBar {
    
    MenuBar() {
        Menu fileMenu = new Menu("File");
        
        MenuItem refresh = new MenuItem("Refresh library");
        refresh.setOnAction(actionEvent -> refreshLibrary());
        fileMenu.getItems().add(refresh);
        
        MenuItem selectFolder = new MenuItem("Select folder");
        selectFolder.setOnAction(actionEvent -> selectFolder());
        fileMenu.getItems().add(selectFolder);
        
        MenuItem search = new MenuItem("Search library");
        search.setOnAction(actionEvent -> MusicManiac.searchPane.requestFocus());
        fileMenu.getItems().add(search);
        
        // Playlist menu
        PlaylistMenu playlistMenu = new PlaylistMenu();
        
        // Add menu
        
        //javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
        this.getMenus().addAll(fileMenu, playlistMenu);
    }
    
     /**
      * Reloads all the songs from the directory (static class variable dir).
      */
    private void refreshLibrary() {
        // Load songs
        SongLoader loader = new SongLoader();
        MusicManiac.songs = loader.reloadSongs();
        
        PlaylistLoader playlistLoader = new PlaylistLoader();
        MusicManiac.playlists = playlistLoader.getPlaylists();
    

        MusicManiac.songsPane.updateSongs();        
        MusicManiac.playerPane.setSongs(MusicManiac.songs);
    }
    
    /**
     * Select the directory that contains the songs that should be displayed.
     */
    private void selectFolder() {        
        MusicManiac.playerPane.stop();
        
        SongDirSelector dirSelector = new SongDirSelector();
        File selectedDir = dirSelector.selectNewDir();
        
        // Check if valid directory was choosen
        if (selectedDir.exists()) {
            MusicManiac.dir = selectedDir;
            refreshLibrary();
        }
    }
}
