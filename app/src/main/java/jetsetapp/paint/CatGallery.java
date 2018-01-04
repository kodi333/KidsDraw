package jetsetapp.paint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CatGallery extends AppCompatActivity implements View.OnClickListener {

    private static boolean pictureChosen = false;

    ImageButton dogs;
    ImageButton cats;
    ImageButton other;
    View.OnClickListener handler;

    public static boolean isPictureChosen() {
        return pictureChosen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_gallery);

        dogs = (ImageButton) findViewById(R.id.dogs);
        cats = (ImageButton) findViewById(R.id.cats);
        other = (ImageButton) findViewById(R.id.other);

//        dogs.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.v("TAG","dogsStart");
//            }
//        });

        dogs.setOnClickListener(this);
        cats.setOnClickListener(this);
        other.setOnClickListener(this);

//        dogs.setOnClickListener(handler);
//        cats.setOnClickListener(handler);
//        other.setOnClickListener(handler);
//
//        handler = new View.OnClickListener(){
//
//            public void onClick(View v) {
//
//                if(v.getTag()==dogs){
//                    // doStuff
//                    Intent intentMain = new Intent(CatGallery.this ,
//                            DogGallery.class);
//                    CatGallery.this.startActivity(intentMain);
//                    Log.v("TAG","dogsStart");
//                }
//
//                if(v.getTag()==other){
//                    // doStuff
//                    Intent intentApp = new Intent(CatGallery.this,
//                            OtherGallery.class);
//                    CatGallery.this.startActivity(intentApp);
//                    Log.v("TAG","otherStart");
//                }
//            }
//        };


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

            case R.id.cats:
                intentApp = new Intent(CatGallery.this,
                        CatGallery.class);
                CatGallery.this.startActivity(intentApp);
                Log.v("TAG", "catsStart");
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
//       Log.v("TAG",buttonId);

        Intent mainActivity = new Intent(CatGallery.this, MainActivity.class);
        mainActivity.putExtra("picture", buttonId);

        startActivity(mainActivity);

    }


}
