/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicmaniac;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Tyler
 */
public class SongLoader {
    
    
    final private static List<String> ext = Arrays.asList(".mp3", ".wav", ".m4a");
    
    
    public ArrayList<Song> loadSongs(File dir) {
        ArrayList<Song> songs = new ArrayList<Song>();
       
        loadDirectory(dir, songs);
        
        return songs;
    }
        
    private void loadDirectory(File dir, ArrayList<Song> songs) {
        for (File songFile : dir.listFiles()) {
            if (songFile.isDirectory()) {
                loadDirectory(songFile, songs);
            } else {
                String title = songFile.getName();
                String extension = title.substring(title.lastIndexOf("."));
                if (ext.contains(extension)) {
                    title = title.substring(0, title.lastIndexOf(".")); // Remove file extension
                    songs.add(new Song(songFile.getPath(), title));
                } else {
                    System.out.println(extension);
                }
            }
        }
    }
    
    
}
