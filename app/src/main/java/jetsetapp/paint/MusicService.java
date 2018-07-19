package jetsetapp.paint;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class MusicService extends Service {

    private MediaPlayer player;
    private int[] playList = {R.raw.kids, R.raw.dreams, R.raw.forkids};
    private int randomSong = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        randomSong = new Random().nextInt(3);
        player = MediaPlayer.create(this, playList[randomSong]);
        player.setLooping(true);
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
