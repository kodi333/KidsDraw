package jetsetapp.paint;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static jetsetapp.paint.MusicManager.lastSong;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    private static MediaPlayer player;
    static Foreground.Listener myListener = new Foreground.Listener() {

        public void onBecameForeground() {
            if (player != null) {
                player.start();
            }

        }

        public void onBecameBackground() {
            player.pause();
        }
    };
    private static int[] playList = {R.raw.ridehorse, R.raw.oldman_short, R.raw.dadyfinger};

    public void onCreate() {
        super.onCreate();

        lastSong++;

        currentSong = (lastSong % 3);
        Log.d("currentSong ", String.valueOf(lastSong % 3));

        player = MediaPlayer.create(this, playList[currentSong]);
        player.setOnCompletionListener(this);

        Foreground.get(getApplication()).addListener(myListener);

    }


    private int currentSong;

    public static int getPlayListLength() {
        return playList.length;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //to play all songs in order

        player.start();

        return START_STICKY;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        lastSong++;
        currentSong = (lastSong % 3);

        player = MediaPlayer.create(this, playList[currentSong]);
        player.setOnCompletionListener(this);
        player.start();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Foreground.get(this).removeListener(myListener);
        player.stop();
        player.release();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
