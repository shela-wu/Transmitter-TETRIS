package com.example.matth.transmitcobra;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    CobraView cobraView;
    Button startButton;
    int frames = 50;
    Long clockTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        cobraView = (CobraView) findViewById(R.id.cobra);
        cobraView.setFrames(frames);
        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clockTime = System.currentTimeMillis();
                Handler handler = new Handler();
                Clock2 clock2 = new Clock2(handler);
                handler.post(clock2);
                startButton.setVisibility(View.GONE);
            }
        });
    }

    class Clock2 implements Runnable {
        private Handler handler;
        int count = 0;
        boolean running = true;
        long clockDelay = 5;
        Integer color = Color.YELLOW;
        long frameDelay = 3000;

        public Clock2(Handler handler) {
            this.handler = handler;
        }

        public void run() {

            if (count == 0) {
                cobraView.setNewMat(Color.rgb(255, 0, 255), false);
                count++;
            } else if ((Math.abs(clockTime - System.currentTimeMillis()) > frameDelay) && count == (frames - 1)) {
                cobraView.setNewMat(Color.rgb(255, 0, 255), true);
                running = false;
                Log.e("clockDelay", "[" + Math.abs(clockTime - System.currentTimeMillis()) + "]");
                clockTime=System.currentTimeMillis();
            } else if ((Math.abs(clockTime - System.currentTimeMillis()) > frameDelay) && count < frames && count != 0) {
                frameDelay =10    ;
                if (color == Color.YELLOW) {
                    color = Color.WHITE;
                } else {
                    color = Color.YELLOW;
                }
                cobraView.setNewMat(color, false);
                count++;
                Log.e("clockDelay", "[" + Math.abs(clockTime - System.currentTimeMillis()) + "]");
                clockTime=System.currentTimeMillis();
            }
            if (running) {
                handler.post(this);
            }
        }
    }
}
