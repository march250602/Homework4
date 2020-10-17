package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizgame.model.WordItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mQuestionImageView;
    private Button[] mButtons = new Button[4];

    private String mAnswerWord;
    private Random mRandom;
    private List<WordItem> mItemList;

    private int score = 0;
    TextView scoreTextView;
    private int round = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choice1);
        mButtons[1] = findViewById(R.id.choice2);
        mButtons[2] = findViewById(R.id.choice3);
        mButtons[3] = findViewById(R.id.choice4);

        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        scoreTextView = findViewById(R.id.score_text_view);
        mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        mRandom = new Random();
        newQuiz();
    }

    private void newQuiz() {


        // random word for game
        int answerIndex = mRandom.nextInt(mItemList.size());

        WordItem item = mItemList.get(answerIndex);
        // set image for test
        mQuestionImageView.setImageResource(item.imageResId);
        mAnswerWord = item.word;

        // random choice button answer
        int randomButton = mRandom.nextInt(4);
        mButtons[randomButton].setText(item.word);
        // pull answer item out form list
        mItemList.remove(item);
        // shuffle data
        Collections.shuffle(mItemList);

        for (int i = 0; i < 4 ; i++){
            if(i == randomButton){
                continue;
            }
            mButtons[i].setText(mItemList.get(i).word);
        }
    }


    private void checkRound(){
        round++;
        if(round==5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("สรุปผล");
            builder.setMessage("คุณได้ "+score+" คะแนน\n\nคุณต้องการเล่นเกมใหม่หรือไม่");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    round=0;
                    score=0;
                    scoreTextView.setText(score+" คะแนน");
                    mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
                    newQuiz();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }else{
            newQuiz();
        }

    }

    @Override
    public void onClick(View view) {
        Button b = findViewById(view.getId());
        String buttonText = b.getText().toString();

        if(mAnswerWord.equals(buttonText)){
            Toast.makeText(GameActivity.this,"✔ CORRECT",Toast.LENGTH_SHORT).show();
            score++;
            scoreTextView.setText(score+" คะแนน");

        }else {
            Toast.makeText(GameActivity.this,"✖ INCORRECT!",Toast.LENGTH_SHORT).show();
        }
        checkRound();


    }
}