package jetsetapp.paint;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    protected static ImageButton undoButton;
    protected static ImageButton redoButton;
    private static Boolean setGlow;
    FileOutputStream fos = null;
    int lastChosenColor = Color.BLACK;
    private CanvasView canvasView;
    private Bitmap saveBitMap;
    private Canvas saveCanvas;
    private View view;
    private Bitmap mBitmap;
    private HorizontalScrollView horizontalPaintsView;

    public static Boolean getSetGlow() {
        return setGlow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasView) findViewById(R.id.canvas);
        canvasView.setDrawingCacheEnabled(true);

        undoButton = (ImageButton)findViewById(R.id.undoButton);
        redoButton = (ImageButton)findViewById(R.id.redoButton);

        horizontalPaintsView = (HorizontalScrollView) findViewById(R.id.HorizontalScroll);
        horizontalPaintsView.setHorizontalScrollBarEnabled(false);

        // Disable hardware acceleration for shadow color o work

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//            canvasView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
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


    public void clearCanvas(View v){
        canvasView.clearCanvas();
    }

    public void setColorBlue(View v){
       canvasView.changeColor(Color.BLUE);
        lastChosenColor = Color.BLUE;
    }
    public void setColorRed(View v){
        canvasView.changeColor(Color.RED);
        lastChosenColor = Color.RED;
    }
    public void setColorGreen(View v){
        canvasView.changeColor(Color.GREEN);
        lastChosenColor = Color.GREEN;
    }
    public void setColorYellow(View v){
        canvasView.changeColor(Color.YELLOW);
        lastChosenColor = Color.YELLOW;
    }
    public void setColorBlack(View v){
        canvasView.changeColor(Color.BLACK);
        lastChosenColor = Color.BLACK;
    }
    public void setColorBrown(View v){
        canvasView.changeColor(Color.rgb(140,70,0));
        lastChosenColor = Color.rgb(140,70,0);
    }
    public void setColorPink(View v){
        canvasView.changeColor(Color.rgb(255,0,255));
        lastChosenColor = Color.rgb(255,0,255);
    }

    public void setColorSilver(View v) {
        canvasView.changeColor(Color.parseColor("#C0C0C0"));
        lastChosenColor = Color.parseColor("#C0C0C0");
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
//


}
