package jetsetapp.paint;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected static ImageButton undoButton;
    protected static ImageButton redoButton;
    protected static ImageButton clearButton;
    protected static CanvasView canvasView;
    private static Bitmap newBitmap;
    private static boolean fillFloodSelected = false;
    int lastChosenColor = Color.BLACK;
    ProgressDialog progressDialog;
    Integer count;
    PorterDuffColorFilter greenFilter =
            new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
    private Bitmap mBitmap;
    private HorizontalScrollView horizontalPaintsView;
    private Bitmap backgroundPicture;
    private ImageButton eraseButton;
    private ImageButton drawSmallButton;
    private ImageButton drawBigButton;
    private ImageButton drawRollerButton;
    private ImageButton saveFileButton;
    private ImageButton addPictureButton;
    private ImageButton floodFillButton;
    private ImageButton[] btn = new ImageButton[7];
    private ImageButton btn_unfocus;
    private int[] btn_id = {R.id.floodFill, R.id.addPicture, R.id.erase, R.id.drawSmallbutton, R.id.drawBigbutton, R.id.drawRoller, R.id.saveFile};
    private float buttonUnfocusTransparency = 0.65f;

//    public static int getCanvasViewHeight() {
//        return canvasView.getHeight();
//    }
//
//    public int getWindowManagerWidth(){
//        return getWindowManager().getDefaultDisplay().getWidth();
//    }

    public static Bitmap getNewBitmap() {
        return newBitmap;
    }

    public static boolean isFillFloodSelected() {
        return fillFloodSelected;
    }

    public static CanvasView getCanvasView() {
        return canvasView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasView) findViewById(R.id.canvas);
        canvasView.setDrawingCacheEnabled(true);

        undoButton = (ImageButton)findViewById(R.id.undoButton);
        redoButton = (ImageButton)findViewById(R.id.redoButton);
        clearButton = (ImageButton) findViewById(R.id.clearButton);

        eraseButton = (ImageButton) findViewById(R.id.erase);
        eraseButton.setOnClickListener(this);

        drawBigButton = (ImageButton) findViewById(R.id.drawBigbutton);
        drawBigButton.setOnClickListener(this);

        drawSmallButton = (ImageButton) findViewById(R.id.drawSmallbutton);
        drawSmallButton.setOnClickListener(this);

        drawRollerButton = (ImageButton) findViewById(R.id.drawRoller);
        drawRollerButton.setOnClickListener(this);

        saveFileButton = (ImageButton) findViewById(R.id.saveFile);
        saveFileButton.setOnClickListener(this);

        addPictureButton = (ImageButton) findViewById(R.id.addPicture);
        addPictureButton.setOnClickListener(this);

        floodFillButton = (ImageButton) findViewById(R.id.floodFill);
        floodFillButton.setOnClickListener(this);

        horizontalPaintsView = (HorizontalScrollView) findViewById(R.id.HorizontalScroll);
        horizontalPaintsView.setHorizontalScrollBarEnabled(false);

        // Set background to all buttons


        for (int i = 0; i < btn.length; i++) {
            btn[i] = (ImageButton) findViewById(btn_id[i]);
            btn[i].getBackground().clearColorFilter();
//            btn[i].setAlpha(buttonUnfocusTransparency);
            btn[i].setOnClickListener(this);
        }

        btn_unfocus = btn[0];

        if (CatGallery.isPictureChosen()) {
            setCanvasViewBackground();
        }


//         Disable hardware acceleration for shadow color o work

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//            canvasView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

    }

    private void setFocus(ImageButton btn_unfocus, ImageButton btn_focus) {
//        btn_unfocus.setAlpha(buttonUnfocusTransparency);
//        btn_unfocus.setHovered(false);
        btn_unfocus.getBackground().clearColorFilter(); //.setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        btn_focus.getBackground().setColorFilter(0x25990000, PorterDuff.Mode.DARKEN);
//        btn_focus.setAlpha(1f);
//        btn_focus.setHovered(true);
        this.btn_unfocus = btn_focus;
    }

    @Override
    public void onClick(View v) {

        int whiteColorValue = Color.WHITE;
        switch (v.getId()) {
            case R.id.erase:
                fillFloodSelected = false;
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                setColorWhite();
                break;

            case R.id.drawSmallbutton:
                fillFloodSelected = false;
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                canvasView.changeStroke(3F);
                if (canvasView.getColor() == whiteColorValue) {
                    int lastColor = lastChosenColor;
                    canvasView.changeColor(lastColor);
                }
                break;

            case R.id.drawBigbutton:
                fillFloodSelected = false;
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                canvasView.changeStroke(10F);
                if (canvasView.getColor() == whiteColorValue) {
                    int lastColor = lastChosenColor;
                    canvasView.changeColor(lastColor);
                }
                break;

            case R.id.drawRoller:
                fillFloodSelected = false;
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                canvasView.changeStroke(30F);
                if (canvasView.getColor() == whiteColorValue) {
                    int lastColor = lastChosenColor;
                    canvasView.changeColor(lastColor);
                }
                break;

            case R.id.addPicture:
                new LoadViewTask().execute();
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                break;

            case R.id.saveFile:
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                saveFile(v);
                break;

            case R.id.floodFill:
                setFocus(btn_unfocus, (ImageButton) findViewById(v.getId()));
                fillFloodSelected = true;
                canvasView.changeStroke(0);
                break;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            canvasView.buildDrawingCache();
            mBitmap = Bitmap.createBitmap(canvasView.getDrawingCache());
            Save savefile = new Save();
            savefile.SaveImage(this,mBitmap);
            canvasView.destroyDrawingCache();
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void setCanvasViewBackground() {
        Bundle extras = getIntent().getExtras();
        String b = extras.getString("picture");
        newBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(b, "drawable", getPackageName())).copy(Bitmap.Config.ARGB_8888, true);
//        Drawable d = getResources().getDrawable(getResources().getIdentifier(b, "drawable", getPackageName()));
//        this.backgroundPicture = BitmapFactory.decodeResource(getResources(),
//                getResources().getIdentifier(b, "drawable", getPackageName()));
//        d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * 0.5), (int) (d.getIntrinsicHeight() * 0.5));
//        Drawable s = new ScaleDrawable(d, Gravity.FILL_VERTICAL, 0.50f, 0.50f);
//        s.setLevel(10000);
//
        canvasView.setNewBitmap(newBitmap);
    }

    public void setCanvasColor(View v) {
        ImageButton x = (ImageButton) v;
        String buttonTag = String.valueOf(x.getTag());
        Log.v("TAG", buttonTag);
        canvasView.changeColor(Color.parseColor(buttonTag));
        lastChosenColor = Color.parseColor(buttonTag);

    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();
    }

    public void doFloodFill() {

//        floodFill(backgroundPicture,);
    }

    public void smallButton(View v) {
        canvasView.changeStroke(3F);
        int whiteColorValue = Color.WHITE;
        if (canvasView.getColor() == whiteColorValue) {
            int lastColor = lastChosenColor;
            canvasView.changeColor(lastColor);
        }
    }

    public void bigButton(View v) {
        canvasView.changeStroke(10F);
        int whiteColorValue = Color.WHITE;
        if (canvasView.getColor() == whiteColorValue) {
            int lastColor = lastChosenColor;
            canvasView.changeColor(lastColor);
        }
    }

    public void drawRoller(View v) {
        canvasView.changeStroke(30F);
        int whiteColorValue = Color.WHITE;
        if (canvasView.getColor() == whiteColorValue) {
            int lastColor = lastChosenColor;
            canvasView.changeColor(lastColor);
        }
    }

    public void erase(View v) {
        setColorWhite();
    }

    public void saveFile(View v) {

        canvasView.buildDrawingCache();
        mBitmap = Bitmap.createBitmap(canvasView.getDrawingCache());
        Save savefile = new Save();

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                savefile.SaveImage(this, mBitmap);
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            savefile.SaveImage(this, mBitmap);
        }

        canvasView.destroyDrawingCache();

    }

    public void setGlow(View v) {
//        BlurMaskFilter blur = new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL);
        canvasView.paint.setShadowLayer(50, 0, 0, Color.rgb(100, 255, 255));
        canvasView.changeColor(Color.rgb(255, 255, 255));
        //canvasView.paint.setMaskFilter(blur);
        //canvasView.paint.setStyle(Paint.Style.FILL);

    }

    public void setColorWhite(){
        canvasView.changeColor(Color.rgb(255,255,255));
    }

    // Undo  Draw
    public void undo(View v){
        canvasView.undoLastDraw();
        canvasView.invalidate();
    }

    // Redo Draw
    public void redo(View v){
        canvasView.redoLastDraw();
        canvasView.invalidate();
    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {

        //Before running code in separate thread
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
//            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
//            //Display the progress dialog
            ProgressDialog.show(MainActivity.this, "", "Loading...");

        }

        @Override
        protected Void doInBackground(Void... params) {

            Intent mainGallery = new Intent(MainActivity.this, CatGallery.class);
            MainActivity.this.startActivity(mainGallery);
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            setContentView(R.layout.activity_cat_gallery);
        }
    }
//


}

