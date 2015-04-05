package musicmaniac;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The pane that shows current song and has play/pause button.
 */
public class PlayerPane extends HBox {
    
    Button playButton; // Toggles if the song is playing
    Label songLabel = new Label(); // Displays song name
    
    MediaPlayer mediaPlayer; // The audio playing component
    
    public PlayerPane() {
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setStyle("-fx-background-color: #0099CC;");

        
        // Add play / pause buttons and listeners
        playButton = new Button("Play");
        playButton.setPrefSize(100, 20);
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                System.out.println(mediaPlayer.getStatus());
                if (mediaPlayer == null){
                    
                } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                    playButton.setText("Play");
                } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                    playButton.setText("Pause");
                }
            }
        });
 
        getChildren().addAll(playButton, songLabel);
    }

    
    public void playSong(Song song) {
        // stop current player
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        
        Media media = new Media(new File(song.getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        
        playButton.setText("Pause");
        songLabel.setText(song.getName());
    }
    
}
