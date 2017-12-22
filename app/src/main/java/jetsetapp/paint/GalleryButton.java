package jetsetapp.paint;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Ja on 2017-12-21.
 */

public class GalleryButton extends ImageButton implements View.OnClickListener {

    public GalleryButton(Context context) {
        super(context);
    }

    public GalleryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        String buttonText = b.getText().toString();
        Drawable d = getResources().getDrawable(getResources().getIdentifier(buttonText, "drawable", "jetsetapp.paint"));

        Intent mainActivity = new Intent(v.getContext(), MainActivity.class);
        getContext().startActivity(mainActivity);

        MainActivity.getCanvasView().setBackground(d);
    }
}
