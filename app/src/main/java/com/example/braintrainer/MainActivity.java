package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    int correctTag;

    TextView scoreTextView;
    TextView questionTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    int countCorrect = 0;
    int countTotal = 0;

    CountDownTimer timer;

    Boolean gameOver = false;


    // Create a function to generate a question and answer buttons
    public void updateQuestion () {

        if (!gameOver) {
            // Generate a "plus" question
            final int random1 = new Random().nextInt(10);
            final int random2 = new Random().nextInt(10);
            questionTextView.setText(Integer.toString(random1) + "+" + Integer.toString(random2));

            // Calculate the correct result
            final int correctResult = random1 + random2;

            // Set textView for the four buttons
            List<Button> buttons = new ArrayList<Button>();
            buttons.add(button0);
            buttons.add(button1);
            buttons.add(button2);
            buttons.add(button3);

            correctTag = new Random().nextInt(4);
            for (Button b: buttons) {
                if (b.getTag().equals(Integer.toString(correctTag))) {
                    b.setText(Integer.toString(correctResult));
                } else {
                    int randomAnswer = new Random().nextInt(20);
                    while (randomAnswer == correctResult) {
                        randomAnswer = new Random().nextInt(20);
                    }
                    b.setText(Integer.toString(randomAnswer));
                }
            }
        }
    }

    // onClick function to check if the correct answer is clicked, and update the question
    public void pickAnswer(View view) {
        Button clicked = (Button) view;

        if (!gameOver) {
            if (clicked.getTag().equals(Integer.toString(correctTag))) {
                countCorrect += 1;
            }
            countTotal += 1;
            scoreTextView.setText(String.valueOf(countCorrect) + "/" + String.valueOf(countTotal));
            updateQuestion ();
        }
    }

    // Create a function to convert seconds to hh:mm:ss
    public String convertTime (Date milliSeconds) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(milliSeconds);
    }

    // onClick function to restart the game
    public void playAgain(View view) {
        gameOver = false;
        timer.start();
        updateQuestion();
        countTotal = 0;
        countCorrect = 0;
        scoreTextView.setText(String.valueOf(countCorrect) + "/" + String.valueOf(countTotal));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        TextView timerTextView = findViewById(R.id.timerTextView);

        TextView gameOverTextView = findViewById(R.id.gameOverTextView);
        gameOverTextView.setVisibility(View.INVISIBLE);

        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);

        // Update question
        updateQuestion ();

        // Update score counter
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("0/0");

        // Countdown timer
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                String timeLeft = convertTime (new Date(l));
                timerTextView.setText(timeLeft);
                gameOverTextView.setVisibility(View.INVISIBLE);
                playAgainButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {
                gameOverTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                gameOver = true;

            }
        };

        timer.start();







    }
}