package com.example.chickentimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    boolean counterIsActive = false;

    SeekBar timerSeekBar;
    Button timerButton;
    ImageView eggImageView;
    ImageView omelleteImageView;
    TextView timerTextView;
    CountDownTimer countDownTimer;


    public void updateTimerTextView(int secondsLeft){


        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String minutesText = Integer.toString(minutes);
        String secondsText = Integer.toString(seconds);

        if( minutes < 10 ){
            minutesText = "0" + minutesText;
        }
        if( seconds < 10 ){
            secondsText = "0" + secondsText;
        }

        timerTextView.setText(minutesText + ":" + secondsText);
    }

    public void timerController(View view){
        if( !counterIsActive ){
            timerButton.setText("Reset");
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            final int timer = timerSeekBar.getProgress()*1000;
            countDownTimer = new CountDownTimer( timer, 1000 ){
                @Override
                public void onTick(long milliSecondsUntilDone) {
                    updateTimerTextView((int) milliSecondsUntilDone/1000 );
                }

                @Override
                public void onFinish() {
                    MediaPlayer airHorn;
                    airHorn = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    airHorn.start();
                    eggImageView.setVisibility(View.INVISIBLE);
                    omelleteImageView.setVisibility(View.VISIBLE);
                }
            }.start();
        }else{
            timerButton.setText("Go!");
            timerTextView.setText("00:30");
            timerSeekBar.setProgress(30);
            countDownTimer.cancel();
            timerSeekBar.setEnabled(true);
            counterIsActive = false;
            omelleteImageView.setVisibility(View.INVISIBLE);
            eggImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)(findViewById(R.id.timerSeekBar));
        timerButton = (Button)(findViewById(R.id.timerButton));
        eggImageView = (ImageView)(findViewById(R.id.eggImageView));
        omelleteImageView = (ImageView)(findViewById(R.id.omeletteImageView));
        timerTextView = (TextView)(findViewById(R.id.timerTextView));

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimerTextView(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}