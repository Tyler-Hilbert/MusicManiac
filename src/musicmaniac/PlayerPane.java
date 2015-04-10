package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * The pane that holds all songs and has controls such as next, play, pause, ect.
 */
public class PlayerPane extends HBox {
    
    Button playButton; // Toggles if the song is playing
    Label songLabel = new Label(); // Displays song name
    
    ArrayList<Song> queuedSongsList; // The songs currently on the screen. Have it queued for if a new song is clicked on
    SongsList songsList = new SongsList(); // List of all the songs in the order they will be played
    
    MediaPlayer mediaPlayer; // The audio playing component
    
    public PlayerPane(ArrayList<Song> songs, Scene scene) {
        queuedSongsList = songs;
        
        this.getStyleClass().add("player-pane");
        songLabel.getStyleClass().add("playing-song-label");
        
        // Add play / pause buttons and listeners
        playButton = new Button("Play");
        playButton.setPrefSize(100, 20);
        playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                togglePlay();
            }
        });
        playButton.setMaxWidth(Control.USE_PREF_SIZE);
        playButton.setMinWidth(Control.USE_PREF_SIZE);
        
        // add backButton
        Button backButton = new Button("Back");
        backButton.setPrefSize(100, 20);
        backButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                if (mediaPlayer.currentTimeProperty().get().lessThan(new Duration(5000))) {
                    playPreviousSong();
                } else {
                    mediaPlayer.seek(Duration.ZERO);
                }
            }
        });
        backButton.setMaxWidth(Control.USE_PREF_SIZE);
        backButton.setMinWidth(Control.USE_PREF_SIZE);
        
        // add playNextButton
        Button playNextButton = new Button("Next song");
        playNextButton.setPrefSize(100, 20);
        playNextButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                playNextSong();
            }
        });
        playNextButton.setMaxWidth(Control.USE_PREF_SIZE);
        playNextButton.setMinWidth(Control.USE_PREF_SIZE);
       
        getChildren().addAll(backButton, playButton, playNextButton, songLabel);
        
        
        // Play/pause song when space bar is pressed
        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() != KeyCode.SPACE) {
                    togglePlay();
                }
                
                ke.consume();
            }
        });
    }
    
    private void playNextSong() {
        playSong(songsList.getNextSong());
    }
    
    private void playPreviousSong() {
        playSong(songsList.getPreviousSong());
    }
    
    private void playSong(Song song) {   
        // stop current player
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        
        Media media = new Media(new File(song.getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playNextSong();
            }
        });
        
        playButton.setText("Pause");
        songLabel.setText(song.getName() + "  --  " + song.getArtist());
    }
    
    public void playSelectedSong(Song song) {        
        songsList = new SongsList(queuedSongsList);
        songsList.startSongsList();
        songsList.startPlaying(song);
        
        playSong(song);
    }
       
    /**
     * Toggles between play and pause.
     * plays random song if nothing is playing
     */
    private void togglePlay() {
        if (mediaPlayer == null){
            songsList = new SongsList(queuedSongsList);
            songsList.startSongsList();
            playNextSong();
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playButton.setText("Play");
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
            playButton.setText("Pause");
        }
    }
    
    
    public void setSongs(ArrayList<Song> songs) {
        queuedSongsList = songs;
    }
}
