package jetsetapp.paint;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

    public static Bitmap getNewBitmap() {
        return newBitmap;
    }

//    public static int getCanvasViewHeight() {
//        return canvasView.getHeight();
//    }
//
//    public int getWindowManagerWidth(){
//        return getWindowManager().getDefaultDisplay().getWidth();
//    }

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

        if (MainGallery.isPictureChosen()) {

            setCanvasViewBackground();
        }
//         Disable hardware acceleration for shadow color o work

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//            canvasView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

    }

    @Override
    public void onClick(View v) {

        int whiteColorValue = Color.WHITE;
        switch (v.getId()) {
            case R.id.erase:
                fillFloodSelected = false;
                setColorWhite();
                break;

            case R.id.drawSmallbutton:
                fillFloodSelected = false;
                canvasView.changeStroke(3F);
                if (canvasView.getColor() == whiteColorValue) {
                    int lastColor = lastChosenColor;
                    canvasView.changeColor(lastColor);
                }
                break;

            case R.id.drawBigbutton:
                fillFloodSelected = false;
                canvasView.changeStroke(10F);
                if (canvasView.getColor() == whiteColorValue) {
                    int lastColor = lastChosenColor;
                    canvasView.changeColor(lastColor);
                }
                break;

            case R.id.drawRoller:
                fillFloodSelected = false;
                canvasView.changeStroke(30F);
                if (canvasView.getColor() == whiteColorValue) {
                    int lastColor = lastChosenColor;
                    canvasView.changeColor(lastColor);
                }
                break;

            case R.id.addPicture:
                new LoadViewTask().execute();
                break;

            case R.id.saveFile:
                saveFile(v);
                break;

            case R.id.floodFill:
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

            Intent mainGallery = new Intent(MainActivity.this, MainGallery.class);
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
            setContentView(R.layout.activity_main_gallery);
        }
    }
//


}

