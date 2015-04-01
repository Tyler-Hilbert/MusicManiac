package musicmaniac;

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

/**
 *
 * @author Tyler
 */
public class SongsView {
    
    ArrayList<Label> songs = new ArrayList<Label>();
    
    public SongsView(Stage primaryStage) {
        // Set up grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Add labels and action listeners
        for (int i = 0; i < 100; i++) {
            Label song = new Label("Song number " + i);

            song.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    song.setText("Clicked");
                }
            });
            
            grid.add(song, 0, i);
            songs.add(song);
        }
        
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(grid);
        Scene scene = new Scene(sp, 1000, 800);
        
        primaryStage.setTitle("Music Maniac");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
