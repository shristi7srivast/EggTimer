package com.camera.eggtimer;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    SeekBar mSeekBar;
    ImageView mImage;
    TextView mText;
    Button mButton,mButton2;
    CountDownTimer mCountDownTimer;
    Boolean counterIsActive=false;
    public void back(View view){
        Intent mIntent=new Intent(MainActivity.this,MainActivity.class);
        startActivity(mIntent);
    }
    public void updateTimer(int secondLeft){

        int minute=(int)secondLeft/60;
        int second=(int)secondLeft-minute*60;//since progress is in seconds;
        String secondString=Integer.toString(second);
        if(secondString.equals("0")){
            secondString="00";
        }
        else if(second<=9){
            secondString="0"+secondString;

        }
        mText.setText(Integer.toString(minute)+":"+(secondString));
    }
    public void controlTimer(View view){
        if(counterIsActive==false) {
            counterIsActive=true;
            mSeekBar.setEnabled(false);
            mButton.setText("Stop");

            mCountDownTimer=new CountDownTimer(mSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mText.setText("0" + ":" + "00");
                    Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();
                    mImage.setImageResource(R.drawable.chicken);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    mText.setVisibility(View.GONE);
                    mButton.setVisibility(View.GONE);
                    mButton2.setVisibility(View.VISIBLE);

                }
            }.start();
        }else {
            mText.setText("0:30");
            mCountDownTimer.cancel();
            mSeekBar.setProgress(30);
            mButton.setText("GO!!");
            mSeekBar.setEnabled(true);
            counterIsActive=false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar=findViewById(R.id.seekBar);
        mImage=findViewById(R.id.egg);
        mText=findViewById(R.id.timer);
        mButton=findViewById(R.id.Go);
        mButton2=findViewById(R.id.repeat);
        mButton2.setVisibility(View.GONE);
        mSeekBar.setMax(600);
        mSeekBar.setProgress(30);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             updateTimer(progress);
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
