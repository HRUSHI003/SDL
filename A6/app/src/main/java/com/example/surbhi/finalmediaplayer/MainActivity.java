





package com.example.surbhi.finalmediaplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    SeekBar SB;
    TextView st,et;
    MediaPlayer MP ;double finaltime,currenttime;
    Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SB = (SeekBar)findViewById(R.id.seekbar);
        st = (TextView)findViewById(R.id.starttime);
        et = (TextView)findViewById(R.id.endtime);
        MP = MediaPlayer.create(this,R.raw.faded);
        SB.setMax((int) MP.getDuration()/1000);

        SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekbar,int progress,boolean b){
                if(b)
                {
                  //  long t= (long) (((float) progress)/100)*MP.getDuration();
                    MP.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekbar){

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekbar){

            }

        });

    }





    public void startMusic(View view)
    {
        MP.start();

        finaltime = MP.getDuration();
        currenttime = MP.getCurrentPosition();

       /* if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }*/

        et.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finaltime),
                TimeUnit.MILLISECONDS.toSeconds((long) finaltime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finaltime)))
        );

        st.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) currenttime),
                TimeUnit.MILLISECONDS.toSeconds((long) currenttime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                currenttime)))
        );

        SB.setProgress((int)0);
        myHandler.postDelayed(UpdateTime,1000);
        // b2.setEnabled(true);
        //b3.setEnabled(false);

    }





    public void pauseMusic(View view)
    {
        MP.pause();
    }

    public void stopMusic(View view)
    {
        MP.pause();
        MP.seekTo(0);
        SB.setProgress(0);
    }


    private Runnable UpdateTime = new Runnable() {
        public void run() {
            currenttime = MP.getCurrentPosition();
            st.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) currenttime),
                    TimeUnit.MILLISECONDS.toSeconds((long) currenttime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) currenttime)))
            );
            SB.setProgress((int)currenttime/1000);
            myHandler.postDelayed(this, 1000);
        }
    };

}