package musicmaniac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    private ArrayList<Song> songs;
    
    // Tell what the pane is sorted by
    private enum Sorted {
        TITLE_ASC, TITLE_DESC, 
        ARTIST_ASC, ARTIST_DESC,
        ALBUM_ASC, ALBUM_DESC,
        LENGTH_ASC, LENGTH_DESC
    }
    private Sorted sortedBy = null;
        
    
    /**
     * Create songs pane with songs in MusicManiac.songs
     */
    public SongsPane() {
        this.getStyleClass().add("songs-pane");
        updateSongs();
    }
    
    /**
     * Displays default songs for music maniac
     */
    public void updateSongs() {
        songs = MusicManiac.songs;
        build();
    }
    
    /**
     * Displays custom passed songs
     * @param songs The songs to be displayed
     */
    public void updateSongs(ArrayList<Song> songs) {
        this.songs = songs;
        build();
    }
    
    private void build() {
        VBox songsVBox = new VBox(); 
        addLabels(songsVBox);
        songsVBox.setPadding(new Insets(10));
        songsVBox.setSpacing(8);
        addSongsToVBox(songsVBox);
        this.setContent(songsVBox); 
    }
    
    /**
     * adds the songs HBoxs to the songsVBox.
     */
    private void addSongsToVBox(VBox songsVBox) {
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
            
            if (MusicManiac.playlists != null) {
                for (Playlist playlist : MusicManiac.playlists) {
                    MenuItem playlistItem = new MenuItem("add to " + playlist.getName());
                    playlistItem.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            playlist.addSong(song);
                        }
                    });
                    contextMenu.getItems().add(playlistItem);
                }
            }

            titleLabel.setContextMenu(contextMenu);
            
            
            // Add song action listener
            songHBox.setOnMousePressed((MouseEvent me) -> {
                if (me.isPrimaryButtonDown()) {
                    MusicManiac.playerPane.playSelectedSong(song);
                } else if (me.isSecondaryButtonDown()) {
                    contextMenu.show(songHBox, me.getScreenX(), me.getScreenY());
                }
            });
            
       
            
            songsVBox.getChildren().add(songHBox);
        }
    }    
    
    private void addLabels(VBox songsVBox) {
        HBox labelHBox = new HBox();
        labelHBox.getStyleClass().add("song-hbox");

        // Create labels
        Label titleLabel = new Label("Title");
        Label lengthLabel = new Label("Length");
        Label artistLabel = new Label("Artist");
        Label albumLabel = new Label("Album");

        titleLabel.setPrefWidth(300);
        artistLabel.setPrefWidth(300);
        albumLabel.setPrefWidth(300);
        
        titleLabel.setOnMousePressed((MouseEvent e) -> {
            sortTitle();
        });
        lengthLabel.setOnMousePressed((MouseEvent e) -> {
            sortLength();
        });
        artistLabel.setOnMousePressed((MouseEvent e) -> {
            sortArtist();
        });
        albumLabel.setOnMousePressed((MouseEvent e) -> {
            sortAlbum();
        });

        labelHBox.getChildren().addAll(titleLabel, albumLabel, artistLabel, lengthLabel);  
        songsVBox.getChildren().add(labelHBox);
    }
    
    private void sortTitle() {
        if (sortedBy == Sorted.TITLE_ASC) {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s2.getName().compareToIgnoreCase(s1.getName());
                }
            });
            
            sortedBy = Sorted.TITLE_DESC;
        } else {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s1.getName().compareToIgnoreCase(s2.getName());
                }
            });
            
            sortedBy = Sorted.TITLE_ASC;
        }
        
        build();
    }
    
    private void sortArtist() {
        if (sortedBy == Sorted.ARTIST_ASC) {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s2.getArtist().compareToIgnoreCase(s1.getArtist());
                }
            });
            
            sortedBy = Sorted.ARTIST_DESC;
        } else {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s1.getArtist().compareToIgnoreCase(s2.getArtist());
                }
            });
            
            sortedBy = Sorted.ARTIST_ASC;
        }
        
        build();
    }
       
    private void sortAlbum() {
        if (sortedBy == Sorted.ALBUM_ASC) {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s2.getAlbum().compareToIgnoreCase(s1.getAlbum());
                }
            });
            
            sortedBy = Sorted.ALBUM_DESC;
        } else {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s1.getAlbum().compareToIgnoreCase(s2.getAlbum());
                }
            });
            
            sortedBy = Sorted.ALBUM_ASC;
        }
        
        build();
    }
          
    private void sortLength() {
        if (sortedBy == Sorted.LENGTH_ASC) {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s2.getLength() - s1.getLength();
                }
            });
            
            sortedBy = Sorted.LENGTH_DESC;
        } else {
            Collections.sort(songs, new Comparator<Song>() {
                @Override 
                public int compare(Song s1, Song s2) {
                    return s1.getLength() - s2.getLength();   
                }
            });
            
            sortedBy = Sorted.LENGTH_ASC;
        }
        
        build();
    }
}
