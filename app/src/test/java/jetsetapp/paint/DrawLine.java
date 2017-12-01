//package jetsetapp.paint;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static jetsetapp.paint.R.id.canvas;
//
///**
// * Created by Ja on 2017-11-05.
// */
//
//public class DrawLine extends View {
//
//    private Path path = new Path();
//    private Paint paint = new Paint();
//    private Bitmap mBitmap;
//    private Canvas canvas;
//    private int currentColor = Color.BLACK;
//    private List<Path> paths = new ArrayList<Path>();
//    private List<Integer> colors = new ArrayList<Integer>();
//    private float x;
//    private float y;
//    Context context;
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        canvas = new Canvas(mBitmap);
//    }
//
//    public DrawLine(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public DrawLine(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.context = context;
//        init(context);
//    }
//
//    public DrawLine(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(context);
//    }
//
//    public void init(Context context) {
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setColor(currentColor);
//        paint.setStrokeWidth(4f);
//    }
//
//    public void changeColor(int color) {
//        currentColor = color;
//        path = new Path();
//    }
//
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        for (int x = 0; x < paths.size(); x++) {
//            paint.setColor(colors.get(x));
//            canvas.drawPath(paths.get(x), paint);
//        }
//    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                path.moveTo(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                x = event.getX();
//                y = event.getY();
//                path.lineTo(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                paths.add(path);
//                colors.add(currentColor);
//                invalidate();
//                break;
//        }
//        invalidate();
//        return true;
//    }
//
//    public void clearCanvas(){
//        paths.clear();
//        invalidate();
//    }
//}
