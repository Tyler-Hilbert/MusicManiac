package musicmaniac;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;


public class SearchPane extends HBox {
    TextField searchField;
    
    public SearchPane() {
        searchField = new TextField();
        this.getChildren().add(searchField);
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    SongSearch songSearch = new SongSearch();
                    ArrayList<Song> foundSongs = songSearch.getSongs(MusicManiac.songs, getSearch());
                    
                    MusicManiac.songsPane.updateSongs(foundSongs);                    
                    MusicManiac.playerPane.setSongs(foundSongs);
                }
            }
        });
    }
    
    /**
     * @return The string searched for
     */
    public String getSearch() {
        return searchField.getText();
    }
    
    @Override
    public void requestFocus() {
        searchField.requestFocus();
    }
    
}
