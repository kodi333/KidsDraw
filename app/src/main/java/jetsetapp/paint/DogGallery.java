package jetsetapp.paint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class DogGallery extends AppCompatActivity {

    private static boolean pictureChosen = false;

    ImageView dogs;
    ImageView cats;
    ImageView other;
    View.OnClickListener handler;

    public static boolean isPictureChosen() {
        return pictureChosen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_gallery);

        dogs = (ImageView) findViewById(R.id.dogs);
        cats = (ImageView) findViewById(R.id.cats);
        other = (ImageView) findViewById(R.id.other);

        dogs.setOnClickListener(handler);
        cats.setOnClickListener(handler);
        other.setOnClickListener(handler);

        handler = new View.OnClickListener() {

            public void onClick(View v) {

                if (v == dogs) {
                    // doStuff
                    Intent intentMain = new Intent(DogGallery.this,
                            DogGallery.class);
                    DogGallery.this.startActivity(intentMain);
                }

                if (v == other) {
                    // doStuff
                    Intent intentApp = new Intent(DogGallery.this,
                            OtherGallery.class);
                    DogGallery.this.startActivity(intentApp);
                }
            }
        };


    }

    public void setBackground(View v) {
        pictureChosen = true;
        ImageView x = (ImageView) v;
        String buttonId = String.valueOf(x.getTag());
//       Log.v("TAG",buttonId);

        Intent mainActivity = new Intent(DogGallery.this, MainActivity.class);
        mainActivity.putExtra("picture", buttonId);

        startActivity(mainActivity);

    }
}
