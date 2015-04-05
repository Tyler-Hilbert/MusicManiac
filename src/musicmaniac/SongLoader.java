package musicmaniac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import java.io.File;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;


/**
 * Loads the songs
 */
public class SongLoader {
    final private static List<String> ext = Arrays.asList(".mp3", ".wav", ".m4a"); // The compatiable file formats
    
    /**
     * Loads songs from a directory
     * @param dir the directory to load from
     * @return an arraylist of the loaded songs
     */
    public ArrayList<Song> loadSongs(File dir) {
        ArrayList<Song> songs = new ArrayList<Song>();
        loadDirectory(dir, songs);
        return songs;
    }
        
    /**
     * Loads all songs in a directory and calls itself back up if another directory is found.
     * @param dir the directory to load from
     * @param songs the arraylist of loaded songs
     */
    private void loadDirectory(File dir, ArrayList<Song> songs) {
        for (File songFile : dir.listFiles()) {
            if (songFile.isDirectory()) {
                loadDirectory(songFile, songs);
            } else {
                String title = songFile.getName();
                String extension = title.substring(title.lastIndexOf("."));
                title = title.substring(0, title.lastIndexOf(".")); // Remove file extension

                
                // Check if file format is supported
                if (ext.contains(extension)) {  
                    // Get details about song
                    
                    if (extension.equals(".mp3")) {
                        addMP3(songFile, title, songs);
                    } else {
                        Song song = new Song(songFile.getPath(), title);
                        songs.add(song);
                    }
                }
            }
        }
    }
        
    /**
     * Parses the meta data/details of the mp3 and adds it to the list of songs
     * @param songFile The mp3 file that will be added to the list
     * @param title The title of the mp3
     * @param songs The current list of all songs. This is the list that will be added to.
     */
    private void addMP3(File songFile, String title, ArrayList<Song> songs) {
        try {
            MP3File fileRead = (MP3File)AudioFileIO.read(songFile);

            MP3AudioHeader audioHeader = (MP3AudioHeader) fileRead.getAudioHeader();
            int length = audioHeader.getTrackLength();

            Tag fileTag = fileRead.getTag();
            String artist = fileTag.getFirst(FieldKey.ARTIST);
            String album = fileTag.getFirst(FieldKey.ALBUM);

            songs.add(new Song(songFile.getPath(), title, artist, album, length));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
