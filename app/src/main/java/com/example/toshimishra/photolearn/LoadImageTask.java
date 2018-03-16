package com.example.toshimishra.photolearn;

/**
 * Created by toshimishra on 16/03/18.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    public LoadImageTask(Listener listener) {

        mListener = listener;
    }

    public interface Listener{

        void onImageLoaded(Bitmap bitmap);

    }

    private Listener mListener;
    @Override
    protected Bitmap doInBackground(String... args) {

        try {

            return Bitmap.createScaledBitmap(BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent()),200,150,true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {

            mListener.onImageLoaded(bitmap);

        } else {

            // error
        }
    }
}