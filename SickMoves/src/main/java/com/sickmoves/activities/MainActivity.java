package com.sickmoves.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sickmoves.R;
import com.sickmoves.views.Game;

public class MainActivity extends Activity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private int level;
    private int score;

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
        final Game game = new Game(this);
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

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    }

    @Override
    public void onStop(){
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.commit();
    }
}