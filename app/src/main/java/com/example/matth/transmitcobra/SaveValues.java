package com.example.matth.transmitcobra;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matth on 7/11/2017.
 */

public class SaveValues {
    private Calendar calendar;
    String filename = "";
    FileOutputStream outputStream;
    OutputStreamWriter outputStreamWriter;
    FileOutputStream fileOutputStream = null;
    int count = 0;

    public SaveValues(Context context, String name) {

        calendar = Calendar.getInstance();
        filename = "I_SENT_" + name + ".txt";
        try {
            fileOutputStream = new FileOutputStream(getAlbumStorageDir(context, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    }

    public void saveBarCode(Context context,byte[] colors) {

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(getAlbumStorageDir(context, filename)));
            bos.write(colors);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public void close(Context context) {
        try {
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(Context context, String albumName) throws IOException {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir("CobraSent"), albumName);
        if (!file.exists()) {
            file.createNewFile();
        }
        if (!file.mkdirs()) {
            Log.e("file error", "Directory not created");
        }
        return file;
    }
}