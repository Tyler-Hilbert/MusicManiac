package musicmaniac;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class SearchPane extends HBox {
    TextField searchField;
    
    public SearchPane() {
        searchField = new TextField();
        this.getChildren().add(searchField);
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
