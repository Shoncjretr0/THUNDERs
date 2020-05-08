package com.example.thunder;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import static com.example.thunder.messaging.mess;

public class audioshow extends AppCompatActivity {
    ImageView imgview, play;
    TextView fgd;
    TextView currenttime,totaltime;
    SeekBar bar;
    int playnumber = 0;
    String url = messaging.vedio;
    String name= mess;
    MediaPlayer mediaPlayer;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioshow);
        imgview = findViewById(R.id.imagepic);
        currenttime=findViewById(R.id.current);
        totaltime=findViewById(R.id.total);
        bar=findViewById(R.id.seekBar);
        play = findViewById(R.id.play);
        fgd = findViewById(R.id.textView6);
        fgd.setText(name);
        bar.setMax(100);

        play.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (playnumber == 0) {
                    play();
                    playnumber = 1;
                    play.setImageResource(R.drawable.pause);
                } else {
                    playnumber = 0;
                    pause();
                    play.setImageResource(R.drawable.play);
                }

            }

        });
    }
    public String millisecondstotimer(long milliseconds){

        String timerstring="";
        String secondsstring;
        int hours=(int) (milliseconds/(1000*60*60));
        int minutes=(int) (milliseconds%(1000*60*60))/(1000*60);
        int seconds=(int) ((milliseconds%(1000*60*60))%(1000*60)/1000);

        if (hours >0){

        }

        return timerstring;
    }

    public void play() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();


    }

}

