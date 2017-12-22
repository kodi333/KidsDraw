package jetsetapp.paint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainGallery extends AppCompatActivity {

    private static boolean pictureChosen = false;

    public static boolean isPictureChosen() {
        return pictureChosen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);

    }

    public void setBackground(View v) {
        pictureChosen = true;
        ImageView x = (ImageView) v;
        String buttonId = String.valueOf(x.getTag());
//       Log.v("TAG",buttonId);

        Intent mainActivity = new Intent(MainGallery.this, MainActivity.class);
        mainActivity.putExtra("picture", buttonId);

        startActivity(mainActivity);

    }
}
