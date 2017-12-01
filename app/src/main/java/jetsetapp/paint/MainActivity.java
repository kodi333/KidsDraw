package jetsetapp.paint;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private CanvasView canvasView;

    FileOutputStream fos = null;
    private Bitmap saveBitMap;
    private Canvas saveCanvas;
    private View view;
    private Bitmap mBitmap;
    int lastChosenColor = Color.BLACK;
    public static ImageButton undoButton;
    public static ImageButton redoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasView) findViewById(R.id.canvas);
        canvasView.setDrawingCacheEnabled(true);
        undoButton = (ImageButton)findViewById(R.id.undoButton);
        redoButton = (ImageButton)findViewById(R.id.redoButton);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.drawBigbutton) {
            canvasView.changeStroke(20F);
            int whiteColorValue = Color.WHITE;
            if(canvasView.getColor() == whiteColorValue){
                int lastColor = lastChosenColor;
                canvasView.changeColor(lastColor);
            }
        }

        if (id == R.id.drawSmallbutton) {
            canvasView.changeStroke(4F);
            int whiteColorValue = Color.WHITE;
            if(canvasView.getColor() == whiteColorValue){
                int lastColor = lastChosenColor;
                canvasView.changeColor(lastColor);
            }
        }

        if (id == R.id.drawRoller) {
            canvasView.changeStroke(60F);
            int whiteColorValue = Color.WHITE;
            if(canvasView.getColor() == whiteColorValue){
                int lastColor = lastChosenColor;
                canvasView.changeColor(lastColor);
            }
        }

        if (id == R.id.erase) {
            setColorWhite();
        }

        if (id == R.id.saveFile) {

            canvasView.buildDrawingCache();
            mBitmap = Bitmap.createBitmap(canvasView.getDrawingCache());
            Save savefile = new Save();

            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    savefile.SaveImage(this,mBitmap);
                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    return false;
                }
            }
            else { //permission is automatically granted on sdk<23 upon installation
                savefile.SaveImage(this,mBitmap);
            }

            canvasView.destroyDrawingCache();

        }
        return super.onOptionsItemSelected(item);
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
    public void setColorWhite(){
        canvasView.changeColor(Color.rgb(255,255,255));
    }

    // Undo Redo Draw
    public void undo(View v){
        canvasView.undoLastDraw();
        canvasView.invalidate();
    }

    public void redo(View v){
        canvasView.redoLastDraw();
        canvasView.invalidate();
    }
//


}
