package jetsetapp.paint;

public class MusicManager {


    public static boolean musicAlreadyPlayedAtBegining;
    static MusicService musicService;
    static int numberOfSongs = MusicService.getPlayListLength();
    public static int lastSong = numberOfSongs;

    public static boolean isMusicAlreadyPlayedAtBegining() {
        return musicAlreadyPlayedAtBegining;
    }

    public static void setMusicAlreadyPlayedAtBegining(boolean musicAlreadyPlayedAtBegining) {
        musicAlreadyPlayedAtBegining = musicAlreadyPlayedAtBegining;
    }

    public int getLastSong() {
        return lastSong;
    }

    public void setLastSong(int lastSong) {
        MusicManager.lastSong = lastSong;
    }


}
