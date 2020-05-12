package com.yteam.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView start;
    TextView timeText;
    TextView gameTiles[];
    TextView scoreView;
    TextView questionView;
    TextView finalView;
    int answeredQs = 0;
    int correctQs = 0;
    int firstVal;
    int secondVal;
    int answer;

    public void newQuestion(){
        Random random  = new Random();
        firstVal = random.nextInt(21);
        secondVal = random.nextInt(21);
        answer = firstVal + secondVal;
        questionView.setText(""+firstVal+"+"+secondVal);

        int answerBlock = random.nextInt(4);

        for(int i = 0 ; i < 4 ; i++){
            Log.i("Info","newQuestion1");
            if(i==answerBlock){
                gameTiles[i].setText(String.valueOf(answer));
            }else {
                gameTiles[i].setText(String.valueOf(random.nextInt(21)));
            }
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            gameTiles[i].setBackgroundColor(color);
            Log.i("Info","newQuestion2");
        }

    }

    public void updateScores(){
        scoreView.setText(""+correctQs+"/"+answeredQs);
    }

    public void pressButton(View view){
        Log.i("Info","Pressed tile");
        TextView pressed = (TextView) view;
        int pressedValue = Integer.valueOf(pressed.getText().toString());
        if(pressedValue==answer){
            correctQs++;
        }
        answeredQs++;
        updateScores();
        newQuestion();
    }
    public void startGame(View view){
        Log.i("Info","Pressed Start1");
        start.animate().alpha(0).translationYBy(-1000).setDuration(1000);
        Log.i("Info","Pressed Start2");
        timeText.setTextColor(Color.DKGRAY);
        finalView.setText("");
        answeredQs = 0;
        correctQs = 0;
        Log.i("Info","Pressed Start3");
        updateScores();
        Log.i("Info","Pressed Start4");
        newQuestion();
        Log.i("Info","Pressed Start5");
        new CountDownTimer(31000,1000){
            @Override
            public void onTick(long l) {
                int timeRemaining = (int) (l / 1000);
                if(timeRemaining < 5){
                    timeText.setTextColor(Color.RED);
                }
                timeText.setText(String.valueOf(timeRemaining));
            }

            @Override
            public void onFinish() {
                start.animate().alpha(1).translationYBy(1000).setDuration(1000);
                questionView.setText("");
                finalView.setText("You answered "+correctQs+" out of "+answeredQs);
                for(TextView tile : gameTiles){
                    tile.setText("");
                }
            }
        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.startImageView);
        timeText = findViewById(R.id.timeTextView);
        scoreView = findViewById(R.id.scoreTextView);
        questionView = findViewById(R.id.questionTextView);
        finalView = findViewById(R.id.finalScoreView);
        gameTiles = new TextView[4];
        gameTiles[0] = findViewById(R.id.answerTextView0);
        gameTiles[1] = findViewById(R.id.answerTextView1);
        gameTiles[2] = findViewById(R.id.answerTextView2);
        gameTiles[3] = findViewById(R.id.answerTextView3);
    }
}
