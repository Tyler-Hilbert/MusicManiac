package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    
    ArrayList<Song> songs; // The list of all songs
    
    MediaPlayer mediaPlayer; // The audio playing component
    
    public PlayerPane(ArrayList<Song> songs, Scene scene) {
        this.songs = songs;
        
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
        
        // add playPreviousButton
        Button resetButton = new Button("Reset song");
        resetButton.setPrefSize(100, 20);
        resetButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        resetButton.setMaxWidth(Control.USE_PREF_SIZE);
        resetButton.setMinWidth(Control.USE_PREF_SIZE);
        
        // add playNextButton
        Button playNextButton = new Button("Next song");
        playNextButton.setPrefSize(100, 20);
        playNextButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                playRandomSong();
            }
        });
        playNextButton.setMaxWidth(Control.USE_PREF_SIZE);
        playNextButton.setMinWidth(Control.USE_PREF_SIZE);
       
        getChildren().addAll(resetButton, playButton, playNextButton, songLabel);
        
        
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

    
    public void playSong(Song song) {
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
                playRandomSong();
            }
        });
        
        playButton.setText("Pause");
        songLabel.setText(song.getName() + "  --  " + song.getArtist());
    }
    
    private void playRandomSong() {
        int randIndex = (int)(Math.random() * songs.size());
        playSong(songs.get(randIndex));
    }
    
    /**
     * Toggles between play and pause.
     * plays random song if nothing is playing
     */
    private void togglePlay() {
        if (mediaPlayer == null){
            playRandomSong();
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playButton.setText("Play");
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
            playButton.setText("Pause");
        }
    }
    
    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
