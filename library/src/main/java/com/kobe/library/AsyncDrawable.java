package com.kobe.library;

import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created by kobe-mac on 15/5/20.
 */
public class AsyncDrawable extends BitmapDrawable {

    private WeakReference<BitmapAsyncTask> taskRef;
    public AsyncDrawable( BitmapAsyncTask task) {
        this.taskRef = new WeakReference<BitmapAsyncTask>(task);
    }


    public BitmapAsyncTask getTask(){
        return taskRef.get();
    }
}
