package jetsetapp.paint;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;

/**
 * Created by Ja on 2017-12-22.
 */

class FillTask extends AsyncTask<Void, Integer, Void> {

    Bitmap bmp;
    Point pt;
    int replacementColor, targetColor;
    ProgressDialog pd;
    CanvasView canvasView;

    public FillTask(Bitmap bm, Point p, int sc, int tc) {
        this.bmp = bm;
        this.pt = p;
        this.replacementColor = tc;
        this.targetColor = sc;
//        canvasView = MainActivity.getCanvasView();
//        pd = canvasView.getPd();
//        pd.setMessage("Filling....");
//        pd.show();
    }

    @Override
    protected void onPreExecute() {
        pd.show();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected Void doInBackground(Void... params) {
//        FloodFill f = new FloodFill();
//        f.floodFill();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        pd.dismiss();
        canvasView.invalidate();
    }
}

