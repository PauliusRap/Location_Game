package com.example.locationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Activity_MainScreen extends AppCompatActivity {

    Button playGame;
    Button highscores;
    Button exit;
    ImageButton sound;
    MediaPlayer music;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("AppData", Context.MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        music = MediaPlayer.create(this, R.raw.game_music);
        music.setLooping(true);
        startService(new Intent(this, music.getClass()));

        sound = findViewById(R.id.button_sound);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "button pressed", Toast.LENGTH_SHORT).show();
                if (!music.isPlaying()) {
                    sound.setImageResource(R.drawable.volume_on);
                    sound.setTag(R.drawable.volume_on);
                    music.start();
                    prefsEditor.putBoolean("musicPlaying", true);
                } else {
                    sound.setImageResource(R.drawable.volume_off);
                    sound.setTag(R.drawable.volume_off);
                    music.pause();
                    prefsEditor.putBoolean("musicPlaying", false);
                }
                prefsEditor.apply();
            }
        });

        if (prefs.contains("musicPlaying")) {
            if (prefs.getBoolean("musicPlaying", true)) {
                music.start();
                sound.setImageResource(R.drawable.volume_on);
                sound.setTag(R.drawable.volume_on);
            } else {
                sound.setImageResource(R.drawable.volume_off);
                sound.setTag(R.drawable.volume_off);
            }
        } else {
            music.start();
            sound.setImageResource(R.drawable.volume_on);
            sound.setTag(R.drawable.volume_on);
            prefsEditor.putBoolean("musicPlaying", true);
            prefsEditor.apply();
        }


        playGame = findViewById(R.id.button_play);
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGameOptions = new Intent(getApplicationContext(), Activity_GameOptions.class);
                if (music.isPlaying()) {
                    music.pause();
                    goToGameOptions.putExtra("musicPos", music.getCurrentPosition());
                }
                startActivity(goToGameOptions);
            }
        });

        exit = findViewById(R.id.button_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }

    public void onResume() {
        super.onResume();

        //If the sound should be played, resumes it
        if ((int) sound.getTag() == R.drawable.volume_on) {
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