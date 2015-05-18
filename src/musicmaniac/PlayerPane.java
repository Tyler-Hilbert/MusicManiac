package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
    
    public PlayerPane() {
        queuedSongsList = MusicManiac.songs;
        
        this.getStyleClass().add("player-pane");
        songLabel.getStyleClass().add("playing-song-label");
        
        // Add play / pause buttons and listeners
        Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/play.png"));
        playButton = new ImageView(playImg);
        playButton.setPickOnBounds(true);
        playButton.setOnMousePressed((MouseEvent e) -> {
            if (mediaPlayer == null) {
                startPlayingSongsList();
                
            } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                // Pause
                Image pressedImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/pause_pressed.png"));
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(playButton.imageProperty(), pressedImg)),
                    new KeyFrame(Duration.millis(100), new KeyValue(playButton.imageProperty(), playImg))
                );
                timeline.play();
                mediaPlayer.pause();

            } else {
                // Play
                Image pressedImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/play_pressed.png"));
                Image pauseImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/pause.png"));
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(playButton.imageProperty(), pressedImg)),
                    new KeyFrame(Duration.millis(100), new KeyValue(playButton.imageProperty(), pauseImg))
                );
                timeline.play();
                mediaPlayer.play();
            }
        });
        
        
        // add backButton
        Image backImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/back.png"));
        ImageView backButton = new ImageView(backImg);
        backButton.setPickOnBounds(true);
        backButton.setOnMousePressed((MouseEvent e) -> {
            Image pressedImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/back_pressed.png"));
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(backButton.imageProperty(), pressedImg)),
                    new KeyFrame(Duration.millis(100), new KeyValue(backButton.imageProperty(), backImg))
            );
            timeline.play();
            if (mediaPlayer == null || mediaPlayer.currentTimeProperty().get().lessThan(new Duration(5000))) {
                previousSong();
            } else {
                mediaPlayer.seek(Duration.ZERO);
            }
        });

        
        // add playNextButton
        Image nextImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/forward.png"));
        ImageView nextButton = new ImageView(nextImg);
        nextButton.setPickOnBounds(true); 
        nextButton.setOnMousePressed((MouseEvent e) -> {
            Image pressedImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/forward_pressed.png"));
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(nextButton.imageProperty(), pressedImg)),
                    new KeyFrame(Duration.millis(100), new KeyValue(nextButton.imageProperty(), nextImg))
            );
            timeline.play();
            nextSong();
        });
       
        getChildren().addAll(backButton, playButton, nextButton, songLabel);
    }
    
    /**
     * Sets the MediaPlayer to the next song
     */
    private void nextSong() {
        if (mediaPlayer == null) {
            startPlayingSongsList();
        } else if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            playSong(songsList.getNextSong(), true);
        } else {
            playSong(songsList.getNextSong(), false);
        }
    }
    
    /**
     * Sets the MediaPlayer to the previous song
     */
    private void previousSong() {
        if (mediaPlayer == null) {
            startPlayingSongsList();
        } else if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            playSong(songsList.getPreviousSong(), true);
        } else {
            playSong(songsList.getPreviousSong(), false);
        }
    }
    
    /**
     * Plays the passed song and queues the next song.
     * @param song The song to be played
     * @param playSong if the song should start playing. False means the song will be paused.
     */
    private void playSong(Song song, boolean playSong) {   
        // stop current player
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        
        Media media = new Media(new File(song.getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        // Set the song as playing or paused
        if (playSong) {
            mediaPlayer.play();
           Image playImg = new Image(MusicManiac.class.getResourceAsStream("/resources/img/pause.png"));
            playButton.setImage(playImg);
        } else {
            mediaPlayer.pause();
        }
        
        // Set the next song to be played
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                nextSong();
            }
        });
        
        songLabel.setText(song.getName() + "  --  " + song.getArtist());
    }
    
    /**
     * Plays the song that was selected from the SongsPane.
     * @param song the song that was selected to be played.
     */
    public void playSelectedSong(Song song) { 
        // Set up the next songs that are going to be played
        songsList = new SongsList(queuedSongsList);
        songsList.startSongsList();
        songsList.setPlayingSong(song); // Sets the current playing song in the list
        
       // Stop current song and set mediaPlayer to null so playSong() will play the selected song.
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        
        playSong(song, true);
    }
       
    
    /**
     * Generate songs list and start playing
     */
    private void startPlayingSongsList() {
        // Generate songs list and play songs
        songsList = new SongsList(queuedSongsList);
        songsList.startSongsList();
        playSelectedSong(songsList.getNextSong());
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
