package jetsetapp.paint;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static jetsetapp.paint.MainActivity.canvasView;


public class CanvasView extends View {

    private static final float TOLERANCE = 5;
    final Point p1 = new Point();
    protected Paint paint = new Paint();
    protected Paint _paintBlur = new Paint();
    Context context;
    FileOutputStream fos = null;
    View undoButton;
    ProgressDialog pd;
    private List<Path> paths = new ArrayList<Path>();
    private List<Integer> colors = new ArrayList<Integer>();
    private List<Float> strokes = new ArrayList<Float>();
    private List<Point> points = new ArrayList<Point>();
    private List<Point> undonePoints = new ArrayList<Point>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Integer> undoneColors = new ArrayList<Integer>();
    private ArrayList<Float> undoneStrokes = new ArrayList<Float>();
    private int currentColor = Color.BLACK; // was black
    private float currentStroke = 10F;
    private Bitmap mBitmap;
    private Bitmap newBitmap;
    private Canvas canvas;
    private Path path = new Path();
    private float mX;
    private float mY;
    private ViewStub view;
    private int cx;
    private int cy;
    private Rect imageRect;


    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setFocusable(true);
        setFocusableInTouchMode(true);

        //setLayerType(LAYER_TYPE_SOFTWARE, paint);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK); // BLACK
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(currentStroke);

//        pd = new ProgressDialog(context);

        newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat2).copy(Bitmap.Config.ARGB_8888, true);


//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int width = windowManager.getDefaultDisplay().getWidth();
//        int height = windowManager.getDefaultDisplay().getHeight();
//        newBitmap = Bitmap.createScaledBitmap(mBitmap,width,height,false);
//        MainActivity.getCanvasView().buildDrawingCache();
//        mBitmap = Bitmap.createBitmap(MainActivity.getCanvasView().getDrawingCache());

//        _paintBlur = new Paint();
//        _paintBlur.set(paint);
//        _paintBlur.setColor((Color.rgb(249, 80, 75)));
//        _paintBlur.setStrokeWidth(currentStroke + 10F);
//        //_paintBlur.setShadowLayer(50,0,0,(Color.rgb(249, 80, 75)));
//        _paintBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));

    }

    public ProgressDialog getPd() {
        return pd;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
        imageRect = new Rect(0, 0, w, h);

    }

    public void changeColor(int color) {
        currentColor = color;
        path = new Path();
    }

    public int getColor() {
        int color = currentColor;
       return color;
    }

    public void changeStroke(float size) {
        currentStroke = size;
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (imageRect == null) { // I think it is always false as we are intializing it onSizeChanged
            imageRect = new Rect(0, 0, getWidth(), getHeight());
        }
        if (newBitmap != null) {
            canvas.drawBitmap(newBitmap, null, imageRect, paint);
        }
//        if(MainActivity.getSetGlow()) {
//            canvas.drawARGB(255, 0, 0, 0);
//            invalidate();
//        }
//        else {
//            canvas.drawARGB(255, 255, 255, 255);
//            invalidate();
//        }

        for (int x = 0; x < paths.size(); x++) {
            paint.setColor(colors.get(x));
            paint.setStrokeWidth(strokes.get(x));
            canvas.drawPath(paths.get(x), paint);
        }
        paint.setColor(currentColor);
        paint.setStrokeWidth(currentStroke);
        canvas.drawPath(path, paint);


        // Additional effect path
//        canvas.drawPath(path, _paintBlur);


    }

    private void startTouch(float x, float y) {
        path.moveTo(x, y);
        mX = x;
        mY = y;

    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void upTouch() {
        path.lineTo(mX, mY);
        canvas = new Canvas();
        path = new Path();

//         Show undo redo buttons
        if(paths.size() > 0){
            MainActivity.undoButton.setVisibility(View.VISIBLE);
            MainActivity.clearButton.setVisibility(View.VISIBLE);
        }
    }

    public void undoLastDraw(){
        if(paths.size() > 0) {
            if (points.get(points.size() - 1).x > 0) {
                FloodFill fill = new FloodFill(newBitmap, colors.get(colors.size() - 1), Color.WHITE);
                fill.floodFill(points.get(points.size() - 1).x, points.get(points.size() - 1).y);
            }
            undonePaths.add(paths.remove(paths.size() - 1));
            undoneColors.add(colors.remove(colors.size() - 1));
            undoneStrokes.add(strokes.remove(strokes.size() - 1));
            undonePoints.add(points.remove(points.size() - 1));
//            Log.v("TAG", String.valueOf(points.get(points.size()).toString()));
            Log.v("TAG", String.valueOf(colors.size()));


            if(paths.size() <= 0){
                MainActivity.undoButton.setVisibility(View.INVISIBLE);
            }

            if(undonePaths.size() > 0){
                MainActivity.redoButton.setVisibility(View.VISIBLE);
            }


//              invalidate();
        }
    }

    public void redoLastDraw(){
        if(undonePaths.size()>0) {
            if (undonePoints.get(undonePoints.size() - 1).x > 0) {
                FloodFill fill = new FloodFill(newBitmap, Color.WHITE, undoneColors.get(undoneColors.size() - 1));
                fill.floodFill(undonePoints.get(undonePoints.size() - 1).x, undonePoints.get(undonePoints.size() - 1).y);
            }
            paths.add(undonePaths.remove(undonePaths.size()-1));
            colors.add(undoneColors.remove(undoneColors.size()-1));
            strokes.add(undoneStrokes.remove(undoneStrokes.size()-1));
            points.add(undonePoints.remove(undonePoints.size() - 1));

            if(undonePaths.size() <= 0){
                MainActivity.redoButton.setVisibility(View.INVISIBLE);
            }

            if(undonePaths.size() > 0){
                MainActivity.redoButton.setVisibility(View.VISIBLE);
            }

            if(paths.size() > 0){
                MainActivity.undoButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void clearCanvas() {

        path.reset();
        undonePaths.clear();
        paths.clear();
        colors.clear();
        strokes.clear();
        path = new Path();
        if(paths.size() <= 0){
            MainActivity.undoButton.setVisibility(View.INVISIBLE);
            MainActivity.clearButton.setVisibility(View.INVISIBLE);
        }
//
        if(undonePaths.size() <= 0){
            MainActivity.redoButton.setVisibility(View.INVISIBLE);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (MainActivity.isFillFloodSelected()) {
                    p1.x = (int) x;
                    p1.y = (int) y;
                    //canvasView.buildDrawingCache();
                    paint.setColor(currentColor);
                    final int sourceColor = newBitmap.getPixel((int) x, (int) y);
                    final int targetColor = paint.getColor();
//                    new TheTask(newBitmap, p1, sourceColor, targetColor).execute();
                    canvasView.destroyDrawingCache();
                    newBitmap = Bitmap.createBitmap(canvasView.getDrawingCache());
                    FloodFill fill = new FloodFill(newBitmap, sourceColor, targetColor);
                    fill.floodFill(p1.x, p1.y);
                } else {
                    p1.x = 0;
                    p1.y = 0;
                }
                startTouch(x, y);
                invalidate();
                break;


            case MotionEvent.ACTION_MOVE:
//                if (!MainActivity.isFillFloodSelected()) {
                    moveTouch(x, y);
//                }
                invalidate();
                break;


            case MotionEvent.ACTION_UP:
//                if (!MainActivity.isFillFloodSelected()) {
//
//                    strokes.add(currentStroke);
//                }
                points.add(new Point(p1.x, p1.y));
                strokes.add(currentStroke);
                paths.add(path);
                colors.add(currentColor);
                upTouch();
                invalidate();
                break;

        }

        return true;
    }

}
