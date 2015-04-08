package musicmaniac;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main for the application.
 * Instantiates the view.
 */
public class MusicManiac extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.jpg")));
        primaryStage.setResizable(false);
        
        SongsView view = new SongsView(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
