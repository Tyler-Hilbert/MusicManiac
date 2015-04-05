package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    public PlayerPane(ArrayList<Song> songs) {
        this.songs = songs;
        
        // Setup pane/view
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setStyle("-fx-background-color: #0099CC;");

        
        // Add play / pause buttons and listeners
        playButton = new Button("Play");
        playButton.setPrefSize(100, 20);
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
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
        });
        
        // add playPreviousButton
        Button resetButton = new Button("Reset song");
        resetButton.setPrefSize(100, 20);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        
        // add playNextButton
        Button playNextButton = new Button("Next song");
        playNextButton.setPrefSize(100, 20);
        playNextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                playRandomSong();
            }
        });
        
 
        getChildren().addAll(resetButton, playButton, playNextButton, songLabel);
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
        songLabel.setText(song.getName());
    }
    
    private void playRandomSong() {
        int randIndex = (int)(Math.random() * songs.size());
        playSong(songs.get(randIndex));
    }
}
