package com.sickmoves.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sickmoves.R;
import com.sickmoves.listeners.MenuListener;
import com.sickmoves.views.Game;
import com.sickmoves.views.Menu;

public class MainActivity extends Activity implements MenuListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private int level;
    private int score;
    private Game game;
    private Menu menu;

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
        game = new Game(this);
        gameLayout.addView(game);

        ImageView red = (ImageView) findViewById(R.id.red);
        ImageView blue = (ImageView) findViewById(R.id.blue);
        ImageView green = (ImageView) findViewById(R.id.green);
        ImageView yellow = (ImageView) findViewById(R.id.yellow);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "red Clicked");
                game.changeRobot(1);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "blue Clicked");
                game.changeRobot(2);
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "green Clicked");
                game.changeRobot(3);
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "yellow Clicked");
                game.changeRobot(4);
            }
        });

        RelativeLayout frame = (RelativeLayout) findViewById(R.id.content_frame);
        menu = new Menu(this);
        menu.setListener(this);
        frame.addView(menu);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    }

    @Override
    public void onStop(){
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        game = null;
        menu = null;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //todo pause/are you sure you want to quit
    }

    @Override
    public void onPlay() {
        if(game != null){
            game.start();
            RelativeLayout frame = (RelativeLayout) findViewById(R.id.content_frame);
            frame.removeView(menu);
            menu = null;
            System.gc();
        }
    }
}