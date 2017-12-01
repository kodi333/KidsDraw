package jetsetapp.paint;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.R.attr.path;


public class Save {

    private static Context TheThis;
    private static String NameOfFolder = "/KidsPaint";
    private static String NameOfFile = "KidsPaint";

    public void SaveImage(Context context, Bitmap ImageToSave) {
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String currentDateAndTime = getCurrentDateAndTime();

//        File dir = new File(context.getFilesDir(),NameOfFolder);
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, NameOfFile + " " + currentDateAndTime + ".png");
 //       File file = new File(dir, NameOfFile);
//
//        if (!directory.exists())
//            directory.mkdirs();
//        // Create imageDir
//        File mypath=new File(directory,"obrazek.png");
        //File dir = new File(file_path);

//        File file = new File(dir, NameOfFile +".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            ImageToSave.recycle();
            //addImageToGallery(file.getAbsolutePath(), context);
            MakeSureFileWasCreatedThenMakeAvabile(file);
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            AbleToSave();
        } catch (FileNotFoundException e) {
            UnableToSave();
        } catch (IOException e) {
            UnableToSaveIO();
        }
    }

    private static void UnableToSave() {


        Toast.makeText(TheThis, "Picture failed to save", Toast.LENGTH_SHORT).

                show();
    }

    private static void AbleToSave() {

        Toast.makeText(TheThis, "Picture saved to Gallery", Toast.LENGTH_SHORT).

                show();
    }

    private static void UnableToSaveIO() {

        Toast.makeText(TheThis, "Picture failed to save - IO exception", Toast.LENGTH_SHORT).

                show();
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file) {
        MediaScannerConnection.scanFile(TheThis,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.e("Internal Storage","Scanned " + path + ":");

                    }

                });

    }
}