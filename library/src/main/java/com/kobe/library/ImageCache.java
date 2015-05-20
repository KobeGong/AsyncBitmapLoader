package com.kobe.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by kobe-mac on 15/5/20.
 */
public class ImageCache extends LruCache<String, Bitmap> {

    private static ImageCache instance = null;

    private ImageCache(int maxSize) {
        super(maxSize);
    }

    public static ImageCache getInstance(){
        if(instance == null){
            int maxSize = (int) (Runtime.getRuntime().totalMemory()*4/100);
            instance = new ImageCache(maxSize);
        }
        return instance;

    }
}
