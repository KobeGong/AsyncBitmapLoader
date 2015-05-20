package com.kobe.library;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by kobe-mac on 15/5/20.
 */
public class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> imageViewRef;
    private String data;
    public BitmapAsyncTask(ImageView imageView, String url) {
        imageViewRef = new WeakReference<ImageView>(imageView);
        this.data = url;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = ImageCache.getInstance().get(data);
        if(bitmap == null){
            bitmap = BitmapDownloader.loadBitmap(data);
            ImageCache.getInstance().put(data, bitmap);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled())
            bitmap = null;
        ImageView imageView = imageViewRef.get();
        if(imageView == null||bitmap == null){
            return;
        }
        if(!shouldTaskDisplay(imageView)){
            imageView.setImageBitmap(bitmap);
        }
    }

    private boolean shouldTaskDisplay(ImageView imageView){
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof AsyncDrawable){
            AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            if(asyncDrawable.getTask() == this)
                return false;
        }
        return true;
    }

    public String getData(){
        return data;
    }


}
