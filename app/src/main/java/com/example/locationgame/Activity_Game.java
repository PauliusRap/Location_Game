package com.example.locationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Game extends AppCompatActivity {

    TextView timer;
    MediaPlayer music;
    Intent intent;
    final int MUSIC_ON = 1;
    final int MUSIC_OFF = 0;
    int musicStatus = MUSIC_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game);
        timer = findViewById(R.id.text_countdownTimer);
        music = MediaPlayer.create(this, R.raw.game_music);
        music.setLooping(true);
        intent = getIntent();
        if (intent.hasExtra("musicPos")) {
            music.seekTo(intent.getIntExtra("musicPos", 0));
            musicStatus = MUSIC_ON;
        }

        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timer.setText("");
            }
        }.start();
    }

    public void onResume() {
        super.onResume();

        //If the sound should be played, resumes it
        if (musicStatus == MUSIC_ON) {
            music.start();
        }
    }

    public void onPause() {
        super.onPause();

        //when the screen exits, pause the music
        if (music.isPlaying()) {
            music.pause();
        }
    }

    public void onDestroy() {
        super.onDestroy();

        //Releases the sound file
        music.release();
    }
}