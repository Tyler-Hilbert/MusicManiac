package musicmaniac;

/**
 * Various utilities 
 */
public class Util {
    /**
     * Formats seconds into a string of minutes.
     */
    public static String secondsToMinutes(int s) {
        int mins = s / 60;
        int seconds = s % 60;
        return String.format("%d:%02d", mins, seconds);
    }
}
