package com.example.musicplayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    TextView txtSongName;
    Button btnPlay, btnNext, btnPrevious;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    int currentSongIndex = 0;
    int[] songs = {
            R.raw.song1,
            R.raw.song2,
            R.raw.song3
    };
    String[] songNames = {
            "Enna Sona",
            "Mayakkama Kalakkama",
            "Thendral Vanthu"
    };
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSongName = findViewById(R.id.txtSongName);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        seekBar = findViewById(R.id.seekBar);
        loadSong();
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setText("▶");
            } else {
                mediaPlayer.start();
                btnPlay.setText("⏸");
                updateSeekBar();
            }
        });
        btnNext.setOnClickListener(v -> {
            currentSongIndex++;
            if (currentSongIndex >= songs.length)
                currentSongIndex = 0;
            loadSong();
        });
        btnPrevious.setOnClickListener(v -> {
            currentSongIndex--;
            if (currentSongIndex < 0)
                currentSongIndex = songs.length - 1;
            loadSong();
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mediaPlayer.seekTo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    private void loadSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        txtSongName.setText(songNames[currentSongIndex]);
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        btnPlay.setText("⏸");
        updateSeekBar();
    }
    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }
    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}