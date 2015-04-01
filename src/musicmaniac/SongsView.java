package musicmaniac;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class SongsView {
    
    MediaPlayer mediaPlayer;
    
    public SongsView(Stage primaryStage) {
        // Set up grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        loadSongs(grid);
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(grid);
        Scene scene = new Scene(sp, 1000, 800);
        
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void loadSongs(GridPane grid) {
        ArrayList<Song> songs = new ArrayList<Song>();
        
        try {
            Files.walk(Paths.get("C:\\Users\\Tyler\\Music\\df")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    songs.add(new Song(filePath.toString()));
                }
            });
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        
        
        for (int i = 0; i < songs.size(); i++) {
            Song song = new Song(songs.get(i).getPath());
            
            Label songLabel = new Label(song.getPath());

            songLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    playSong(song.getPath());
                }
            });
            
            grid.add(songLabel, 0, i);
        }
    }
    
    private void playSong(String path) {
        // stop current player:
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
