package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        
        BorderPane root = new BorderPane();
        root.setCenter(sp);
        
        addPlayerPane(root);
        
        Scene scene = new Scene(root, 1000, 800);
        
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void loadSongs(GridPane grid) {
        File dir = new File("D:\\Music\\Current");
        
        // Loads songs from dir
        SongLoader loader = new SongLoader();
        ArrayList<Song> songs = loader.loadSongs(dir);
        
        // Add to form
        for (int i = 0; i < songs.size(); i++) {
            Song song = new Song(songs.get(i).getPath(), songs.get(i).getName());
            
            Label songLabel = new Label(song.getName());

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
        // stop current player
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    
    private void addPlayerPane(BorderPane root) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button playButton = new Button("Play");
        playButton.setPrefSize(100, 20);
 
        hbox.getChildren().addAll(playButton);

        root.setTop(hbox);
    }
}
