package jetsetapp.paint;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static jetsetapp.paint.MusicManager.lastSong;

public class MusicService extends Service {

    private MediaPlayer player;
    private static int[] playList = {R.raw.kids, R.raw.dreams, R.raw.forkids};
    Foreground.Listener myListener = new Foreground.Listener() {

        public void onBecameForeground() {
            player.start();

        }

        public void onBecameBackground() {
            player.pause();
        }
    };
    private int currentSong;

    public static int getPlayListLength() {
        return playList.length;
    }

//    protected void onPause() {
//        if (player.isPlaying()) {
//            player.pause();
//        }
//    }

////    @Override
//    protected void onResume() {
//        if(player != null && !player.isPlaying())
//            player.start();
////        super.onResume();
//    }
//    ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//    // The first in the list of RunningTasks is always the foreground task.
//    ActivityManager.RunningTaskInfo foregroundTaskInfo;
//
//    {
//        foregroundTaskInfo = am.getRunningTasks(1).get(0);
//    }

    //    Random rand = new Random();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        // below is to avoid playing of same song twice
//        currentSong = rand.nextInt(3);
//        int lastSong = musicManager.getLastSong();


        Log.d("getLastSong ", String.valueOf(lastSong));
        Log.d("getCurrentSong ", String.valueOf(currentSong));

        //to play all songs in order
//        lastSong = playList.length;
        lastSong++;

        currentSong = (lastSong % 3);
        Log.d("currentSong ", String.valueOf(lastSong % 3));

        player = MediaPlayer.create(this, playList[currentSong]);
        player.setLooping(true);
        player.start();


//        Log.d("getCurrrentSong", String.valueOf(currentSong));
//        musicManager.setLastSong(currentSong);
        return START_STICKY;
    }

    public void onCreate() {
        super.onCreate();
        Foreground.get(getApplication()).addListener(myListener);
    }


//    @Override
//    public void onTrimMemory(final int level) {
//        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
//            if(player != null){
//                player.pause();
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Foreground.get(this).removeListener(myListener);
//        player.release();
        player.stop();
//        player.release();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
