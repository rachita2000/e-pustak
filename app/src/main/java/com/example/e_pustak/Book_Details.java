package com.example.e_pustak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Book_Details extends AppCompatActivity {

    String title, publisher, publishedDate, description, thumbnail, previewLink, buyLink;
    private ArrayList<String> authors;

    TextView titleTV, publisherTV, descTV, publishDateTV , authorsTV;
    Button previewBtn, buyBtn;
    private ImageView bookImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        titleTV = findViewById(R.id.title);
        publisherTV = findViewById(R.id.publisher);
        descTV = findViewById(R.id.description);
        publishDateTV = findViewById(R.id.publisheddate);
        authorsTV=findViewById(R.id.author);
        previewBtn = findViewById(R.id.prebtn);
        buyBtn = findViewById(R.id.buybtn);
        bookImage = findViewById(R.id.timage);


        Intent intent=getIntent();
        title = intent.getStringExtra("title");
        authors=intent.getStringArrayListExtra("authors");
        publisher = intent.getStringExtra("publisher");
        publishedDate = intent.getStringExtra("publishedDate");
        description = intent.getStringExtra("description");
        thumbnail = intent.getStringExtra("thumbnail");
        previewLink = intent.getStringExtra("previewLink");
        buyLink = intent.getStringExtra("buyLink");


        titleTV.setText(title);
        for(int i=0;i<authors.size();i++)
            authorsTV.setText(authors.get(i));
        publisherTV.setText(publisher);
        if(publishedDate != null)
        publishDateTV.setText("Published: "+publishedDate);
        descTV.setText(description);

        Glide.with(this).load(thumbnail).into(bookImage);

        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewLink.isEmpty()) {
                    Toast.makeText(Book_Details.this, "No preview Link present", Toast.LENGTH_SHORT).show();
                    return;
                }

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(Book_Details.this, Uri.parse(previewLink));
            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyLink.isEmpty()) {
                    Toast.makeText(Book_Details.this, "No buy page present for this book", Toast.LENGTH_SHORT).show();
                    return;
                }

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(Book_Details.this, Uri.parse(buyLink));
            }
        });
    }
}