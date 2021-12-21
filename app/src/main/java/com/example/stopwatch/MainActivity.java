package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int hours,minutes,secs,ms;
    private int seconds = 0;
    private boolean running;
    int lapCount=0;

    ImageView playButton,pauseButton,stopButton,lapseButton;
    TextView timeView;
    TextView timeViews;
    TextView timeLapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (ImageView) findViewById(R.id.playButton);
        pauseButton = (ImageView) findViewById(R.id.pauseButton);
        stopButton = (ImageView) findViewById(R.id.stopButton);
        lapseButton = (ImageView) findViewById(R.id.lapseButton);

        timeView = (TextView) findViewById(R.id.time_view);
        timeViews = (TextView) findViewById(R.id.time_view_ms);
        timeLapse = (TextView) findViewById(R.id.timeLapse);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Started",Toast.LENGTH_LONG).show();

                playButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
                lapseButton.setVisibility(View.VISIBLE);

                running=true;
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Paused",Toast.LENGTH_LONG).show();

                playButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                lapseButton.setVisibility(View.GONE);

                running=false;
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Stopped",Toast.LENGTH_LONG).show();

                running=false;
                seconds=0;
                lapCount=0;
                timeView.setText("00:00:00");
                timeViews.setText("00");
                timeLapse.setText("");

                playButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.GONE);
                lapseButton.setVisibility(View.GONE);
            }
        });
        lapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LapseFun();
            }
        });
        runTimer();
    }
    private void runTimer()
    {
        final Handler handlertime = new Handler();
        final Handler handlerMs = new Handler();
        handlertime.post(new Runnable() {
            @Override
            public void run() {
                hours = seconds / 3600;
                minutes = (seconds % 3600) / 60;
                secs = seconds % 60;

                if(running)
                {
                    String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);

                    timeView.setText(time);

                    seconds++;
                }
                handlertime.postDelayed(this, 1000);
            }
        });
        handlerMs.post(new Runnable() {
            @Override
            public void run() {
                if (ms >= 99) {
                    ms = 0;
                }

                // if running increment the ms
                if (running) {
                    String msString = String.format(Locale.getDefault(), "%02d", ms);
                    timeViews.setText(msString);

                    ms++;
                }
                handlerMs.postDelayed(this, 1);
            }
        });
    }
    void LapseFun() {

        // increase lap count when function is called
        lapCount++;

        String laptext = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        String msString = String.format(Locale.getDefault(), "%02d", ms);

        // adding ms to lap text
        laptext = laptext + ":" + msString;

        if (lapCount >= 10) {
            laptext = " Lap " + lapCount + " ------------->       " + laptext + " \n " + timeLapse.getText();
        } else {
            laptext = " Lap " + lapCount + " --------------->       " + laptext + " \n " + timeLapse.getText();

        }

        //showing simple toast message to user
        Toast.makeText(MainActivity.this, "Lap " + lapCount, Toast.LENGTH_SHORT).show();

        // showing the lap text
        timeLapse.setText(laptext);
    }
}