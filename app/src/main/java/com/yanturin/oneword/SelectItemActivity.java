package com.yanturin.oneword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectItemActivity extends AppCompatActivity {

    public static final String WORD = "word";
    public static final String DATE = "date";
    public static final String EXPLANATION = "explanation";
    public static final String FAVORITE = "favorite";
    int favorite;
    String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_home);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        Intent intent = getIntent();
        word = intent.getStringExtra(WORD);
        favorite = intent.getIntExtra(FAVORITE,0);
        String date = intent.getStringExtra(DATE);
        String explanation = intent.getStringExtra(EXPLANATION);

        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvWord = findViewById(R.id.tvWord);
        TextView tvExplanation = findViewById(R.id.tvExplanation);
        ImageView ivFavorite = findViewById(R.id.ivFavorite);
        tvWord.setText(word);
        tvExplanation.setText(explanation);
        tvDate.setText(date);

        ivFavorite.setImageResource((favorite == 1)? R.drawable.greenheart24: R.drawable.heart24);

        ivFavorite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // создаем объект для данных
                if(favorite == 1){
                    SqlQueries.Instance().UpdateRowByWordsTable("favorite", 0, word,SelectItemActivity.this);
                    favorite = 0;
                }else{
                    SqlQueries.Instance().UpdateRowByWordsTable("favorite", 1, word,SelectItemActivity.this);
                    favorite = 1;
                }
                ImageView ivFavorite1 = findViewById(R.id.ivFavorite);
                ivFavorite1.setImageResource((favorite == 1)? R.drawable.greenheart24: R.drawable.heart24);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
