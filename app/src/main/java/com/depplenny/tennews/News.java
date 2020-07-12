package com.depplenny.tennews;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class News {
    private String mTime;
    private String mTitle;
    private String mContent;
    private String mThumbnail;
    private Bitmap mBitmap;

    public News(String time, String title, String content, String thumbnail) {
        mTime = time;
        mTitle = title;
        mContent = content;
        mThumbnail = thumbnail;
    }

    public String getTime() {
        return mTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
