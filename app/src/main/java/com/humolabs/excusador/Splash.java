package com.humolabs.excusador;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.Timer;
import java.util.TimerTask;


public class Splash extends AppCompatActivity {

    private KenBurnsView kenBurnsView;
    private ImageView imgView;
    private HTextView hTextView;
    private Button skip;

    private static final long LOGO_DELAY = 5000;
    private static final long SPLASH_SCREEN_DELAY = 9000;
    private boolean blnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        kenBurnsView = (KenBurnsView) findViewById(R.id.kbvSplash);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kenBurnsView.setTransitionGenerator(generator);

        imgView = (ImageView) findViewById(R.id.imgLogo);
        Animation fadein = AnimationUtils.loadAnimation(Splash.this, R.anim.fade_in);
        imgView.startAnimation(fadein);

        skip = (Button) findViewById(R.id.btnSkip);
        Animation ffadein = AnimationUtils.loadAnimation(Splash.this, R.anim.fast_fade_in);
        skip.startAnimation(ffadein);
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                blnSkip=true;
                skip.setTextColor(getResources().getColor(R.color.colorAccent));
                pasarActividad();
            }
        });


        Timer timer = new Timer();
        TimerTask taskTexto = new TimerTask() {
            @Override
            public void run() {
                //como manejo elementos de la UI tengo que usar el método runOnUiThread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hTextView = (HTextView) findViewById(R.id.htext);
                        hTextView.setTextSize(32);
                        hTextView.setAnimateType(HTextViewType.TYPER);
                        hTextView.animateText(getString(R.string.humolabs));
                    }
                });
            }//fin run
        }; //fin TimerTask
        timer.schedule(taskTexto, LOGO_DELAY);

        Timer timerActivity = new Timer();
        TimerTask taskActivity = new TimerTask() {

            @Override
            public void run() {
                if (!blnSkip) {
                    pasarActividad();
                }
            }
        }; //fin TimerTask
        timerActivity.schedule(taskActivity, SPLASH_SCREEN_DELAY);

    }//fin OnCreate


    private void pasarActividad(){
        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        startActivity(mainIntent);
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

}
