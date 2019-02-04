package com.example.homeira.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Button button;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        seekBar.setEnabled(true);
        button.setText("start");
        countDownTimer.cancel();
    }

    public void timer( Long timerLength){
        countDownTimer = new CountDownTimer(timerLength+100,1000) { //add 100 ms to the length would help timer to start at correct time. if we don't use it, by clicking on start, timer immediately will go for example from 14 to 13 which is not correct.
            @Override
            public void onTick(long millisUntilFinished) {
                int currentTimerPosition = (int) (millisUntilFinished /1000);
                textView.setText(String.format("%02d:%02d", currentTimerPosition / 60, currentTimerPosition % 60));
                seekBar.setProgress(currentTimerPosition);
            }
            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.sound);
                mediaPlayer.start();
                resetTimer();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.timerTextView);
        button = (Button) findViewById(R.id.button);

        seekBar.setMax(300);
        textView.setText("00:00");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               textView.setText(String.format("%02d:%02d", progress / 60, progress % 60));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seekBar.getProgress()!=0) {
                    if (button.getText().toString().equals("start")) {
                        button.setText("stop");
                        seekBar.setEnabled(false);
                        timer((long) (seekBar.getProgress() * 1000));
                    } else {
                        resetTimer();
                    }
                }
            }
        });
    }
}
