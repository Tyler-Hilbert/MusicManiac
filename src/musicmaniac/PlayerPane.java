package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * The pane that holds all songs and has controls such as next, play, pause, ect.
 */
public class PlayerPane extends HBox {
    
    ImageView playButton; // Toggles if the song is playing
    Label songLabel = new Label(); // Displays song name
    
    ArrayList<Song> queuedSongsList; // The songs currently on the screen. Have it queued for if a new song is clicked on
    SongsList songsList = new SongsList(); // List of all the songs in the order they will be played
    
    MediaPlayer mediaPlayer; // The audio playing component
    
    public PlayerPane(ArrayList<Song> songs, Scene scene) {
        queuedSongsList = songs;
        
        this.getStyleClass().add("player-pane");
        songLabel.getStyleClass().add("playing-song-label");
        
        // Add play / pause buttons and listeners
        Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/play.png"));
        playButton = new ImageView(playImg);
        playButton.setPickOnBounds(true);
        playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                togglePlay();
            }
        });
        
        
        // add backButton
        Image backImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/back.png"));
        ImageView backButton = new ImageView(backImg);
        backButton.setPickOnBounds(true);
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

        
        // add playNextButton
        Image nextImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/forward.png"));
        ImageView nextButton = new ImageView(nextImg);
        nextButton.setPickOnBounds(true); 
        nextButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                playNextSong();
            }
        });
       
        getChildren().addAll(backButton, playButton, nextButton, songLabel);
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
        
        // Play song if the previous song was playing or ready
        if (mediaPlayer == null || mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING || mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
           Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/pause.png"));
            playButton.setImage(playImg);
        } else {
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.pause();
        }
        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playNextSong();
            }
        });
        
        songLabel.setText(song.getName() + "  --  " + song.getArtist());
    }
    
    /**
     * Plays the song that was selected from the SongsPane.
     * @param song the song that was selected to be played.
     */
    public void playSelectedSong(Song song) {        
        songsList = new SongsList(queuedSongsList);
        songsList.startSongsList();
        songsList.startPlaying(song);
        
        Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/pause.png"));
        playButton.setImage(playImg);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        
        playSong(song);
    }
       
    /**
     * Toggles between play and pause.
     * plays random song if nothing is playing
     */
    private void togglePlay() {
        if (mediaPlayer == null){
            // Generate songs list
            songsList = new SongsList(queuedSongsList);
            songsList.startSongsList();
            playNextSong();

        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            // Pause
            mediaPlayer.pause();
            Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/play.png"));
            playButton.setImage(playImg);
            
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED || mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
            // Play
            mediaPlayer.play();
        }
    }
    
    
    /**
     * Sets the current songs being shown in the SongsPane
     * @param songs The songs being shown in the SongsPane.
     */
    public void setSongs(ArrayList<Song> songs) {
        queuedSongsList = songs;
    }
    
    /**
     * Stops the music playing.
     * Is called when loading a new directory.
    */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
            songLabel.setText("");
            Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/play.png"));
            playButton.setImage(playImg);
        }
    }
}
