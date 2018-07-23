package jetsetapp.paint;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import static jetsetapp.paint.MusicManager.musicAlreadyPlayedAtBegining;

public class CatGallery extends AppCompatActivity implements View.OnClickListener {

    private static boolean pictureChosen = false;

    ImageButton dogs;
    ImageButton cats;
    ImageButton other;
    View.OnClickListener handler;
    MusicManager musicManager;


    public static boolean isPictureChosen() {
        return pictureChosen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_gallery);

//        final AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);


        Log.d("Music", String.valueOf(musicAlreadyPlayedAtBegining));

        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (!manager.isMusicActive() && !musicAlreadyPlayedAtBegining) {
            Log.d("Music", "started.");
            startService(new Intent(this, MusicService.class));
            musicAlreadyPlayedAtBegining = true;
        }


        dogs = findViewById(R.id.dogs);
        cats = findViewById(R.id.cats);
        other = findViewById(R.id.other);

        dogs.setOnClickListener(this);
        cats.setOnClickListener(this);
        other.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dogs:
                Intent intentApp = new Intent(CatGallery.this,
                        DogGallery.class);
                CatGallery.this.startActivity(intentApp);
                Log.v("TAG", "dogsStart");
                break;

            case R.id.other:
                intentApp = new Intent(CatGallery.this,
                        OtherGallery.class);
                CatGallery.this.startActivity(intentApp);
                Log.v("TAG", "otherStart");
                break;

        }


    }

    public void setBackground(View v) {
        pictureChosen = true;
        ImageView x = (ImageView) v;
        String buttonId = String.valueOf(x.getTag());

        Intent mainActivity = new Intent(CatGallery.this, MainActivity.class);
        mainActivity.putExtra("picture", buttonId);

        startActivity(mainActivity);

    }

//        protected void onPause(){
//        super.onPause();
//
//        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        if (manager.isMusicActive()) {
//            stopService(new Intent(this, MusicService.class));
//        }
//    }


//    protected void onStop(){
//        super.onStop();
//
//        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        if (manager.isMusicActive()) {
//            stopService(new Intent(this, MusicService.class));
//        }
//    }


}
