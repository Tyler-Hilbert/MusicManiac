package musicmaniac;

import javafx.application.Application;
import javafx.stage.Stage;

public class MusicManiac extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        SongsView view = new SongsView(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
