package com.example.locationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_GameOptions extends AppCompatActivity {

    Button play;
    MediaPlayer music;
    Intent intent;
    final int MUSIC_ON = 1;
    final int MUSIC_OFF = 0;
    int musicStatus = MUSIC_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game_options);
        music = MediaPlayer.create(this, R.raw.game_music);
        music.setLooping(true);
        intent = getIntent();
        if (intent.hasExtra("musicPos")) {
            music.seekTo(intent.getIntExtra("musicPos", 0));
            musicStatus = MUSIC_ON;
        }

        play = findViewById(R.id.button_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(getApplicationContext(), Activity_Game.class);
                if (music.isPlaying()) {
                    music.pause();
                    startGame.putExtra("musicPos", music.getCurrentPosition());
                }
                startActivity(startGame);
                finish();
            }
        });
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