package com.sickmoves.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        Game game = new Game(this);
        gameLayout.addView(game);


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