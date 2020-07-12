package com.depplenny.tennews;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;

public class ReaderActivity  extends AppCompatActivity {
    private String mThumbnail;
    private String mContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        Intent intent = getIntent();
        mContent = intent.getStringExtra("content");
        mThumbnail = intent.getStringExtra("thumbnail");

        ImageView imageView = findViewById(R.id.thumbnailReader);
        // show the image from web in a ImageView
        new DownloadImageAsyncTask(imageView)
                .execute(mThumbnail);

        TextView textView = findViewById(R.id.content);
        textView.setText(addLineBreak(Jsoup.parse(mContent).text()) + "\n");

    }

    private String addLineBreak(String input) {
        return input.replace(". ",".\n\n");
    }

}
