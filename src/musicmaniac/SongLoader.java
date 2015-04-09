package musicmaniac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;


/**
 * Loads the songs
 */
public class SongLoader {
    
    final private static List<String> ext = Arrays.asList(".mp3", ".wav", ".m4a"); // The compatiable file formats
    

    /**
     * Loads songs.
     * If songs have previously been loaded it quick loads it from the file,
     *  else wise it reloads each song individually from the directory.
     */
    public ArrayList<Song> loadSongs() {
        // Checks if the songs have already been loaded
        if (new File(MusicManiac.dir, "\\loadedSongsData.tmp").exists()) {
            SavedSongLoader loader = new SavedSongLoader();
            return loader.loadSongs();
            
        } else {
            return reloadSongs();
        }
    }
    
    /**
     * Reloads each song individually from the directory.
     */
    public ArrayList<Song> reloadSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();
        loadDirectory(MusicManiac.dir, songs);

        saveLoadedSongData(songs);  

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
                
                // Add song to songs list
                if (extension.equals(".mp3")) {
                    addMP3(songFile, songs);
                } else if (extension.equals(".m4a")){
                    addM4A(songFile, songs);
                }
            }
        }
    }
        
    /**
     * Parses the meta data/details of the mp3 and adds it to the list of songs
     * @param songFile The mp3 file that will be added to the list
     * @param songs The current list of all songs. This is the list that will be added to.
     */
    private void addMP3(File songFile, ArrayList<Song> songs) {
        try {
            MP3File fileRead = (MP3File)AudioFileIO.read(songFile);

            MP3AudioHeader audioHeader = (MP3AudioHeader) fileRead.getAudioHeader();
            int length = audioHeader.getTrackLength();

            Tag fileTag = fileRead.getTag();
            
            String artist = fileTag.getFirst(FieldKey.ARTIST);
            String album = fileTag.getFirst(FieldKey.ALBUM);
            String title = fileTag.getFirst(FieldKey.TITLE);
            // Take the filename if title isn't found
            if (title.trim().isEmpty()) {
                title = songFile.getName();
                title = title.substring(0, title.lastIndexOf(".")); // Remove ext
            }
          
            songs.add(new Song(songFile.getPath(), title, artist, album, length));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Parses the meta data/details of the m4a and adds it to the list of songs
     * @param songFile The m4a file that will be added to the list
     * @param songs The current list of all songs. This is the list that will be added to.
     */
    private void addM4A(File songFile, ArrayList<Song> songs) {
        try {
            AudioFile audioFile = AudioFileIO.read(songFile);
            int length = audioFile.getAudioHeader().getTrackLength();
            
            Tag tag = audioFile.getTag();
            String album = tag.getFirst(FieldKey.ALBUM);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String title = tag.getFirst(FieldKey.TITLE);
            // Take the filename if title isn't found
            if (title.trim().isEmpty()) {
                title = songFile.getName();
                title = title.substring(0, title.lastIndexOf(".")); // Remove ext
            }

            Song song = new Song(songFile.getPath(), title, artist, album, length);
            songs.add(song);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Saves the data about the songs so they can be loaded quicker.
     * @param songs the loaded songs
     */
    private void saveLoadedSongData(ArrayList<Song> songs) {
        try {
            File loadedSongsData = new File(MusicManiac.dir, "\\loadedSongsData.tmp");
            FileOutputStream fos = new FileOutputStream(loadedSongsData);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(songs);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
