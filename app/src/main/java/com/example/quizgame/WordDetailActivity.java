package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizgame.model.WordItem;

public class WordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Intent intent=getIntent();
        String itemJson = intent.getStringExtra("item");
      // WordItem item= new Gson().fromJson(itemJson, WordItem.class);
        String word = intent.getStringExtra("word");
        int img=intent.getIntExtra("image",0); // ไม่ได้ส่งมาจะ default เป็น 0
        ImageView iv =findViewById(R.id.image_view);
        TextView tv =findViewById(R.id.word_text_view);
        iv.setImageResource(img);
        tv.setText(word);
       // iv.setImageResource(item.imageResId);
       // tv.setText(item.word);
    }
}