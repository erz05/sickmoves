package com.sickmoves.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sickmoves.R;
import com.sickmoves.listeners.MapListener;
import com.sickmoves.listeners.MenuListener;
import com.sickmoves.views.BackgroundView;
import com.sickmoves.views.Game;
import com.sickmoves.views.Map;
import com.sickmoves.views.Menu;

public class MainActivity extends Activity implements MenuListener, MapListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private int level;
    private int score;
    private Game game;
    private Menu menu;
    private Map map;
    private BackgroundView background;
    private boolean paused = true;

    private GestureDetector detector;

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout gameLayout = (FrameLayout) findViewById(R.id.gameLayout);

        game = new Game(this);
        gameLayout.addView(game);

        background = new BackgroundView(this);
        gameLayout.addView(background);

        FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
        menu = new Menu(this);
        menu.setListener(this);
        menuLayout.addView(menu);

        final LinearLayout pauseMenu = (LinearLayout) findViewById(R.id.pauseLayout);
        final Button quitYes = (Button) findViewById(R.id.quitYes);
        final Button quitNo = (Button) findViewById(R.id.quitNo);
        pauseMenu.setVisibility(View.GONE);
        quitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused = true;
                onBackPressed();
            }
        });
        quitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused = false;
                pauseMenu.setVisibility(View.GONE);
                game.resume();
            }
        });

        detector = new GestureDetector(this, this);
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
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        game = null;
        menu = null;
        map = null;
        background = null;
    }

    @Override
    public void onBackPressed(){
        if(paused){
            super.onBackPressed();
        }else {
            game.pause();
            LinearLayout pauseMenu = (LinearLayout) findViewById(R.id.pauseLayout);
            pauseMenu.setVisibility(View.VISIBLE);
            paused = true;
        }
        //todo pause/are you sure you want to quit
    }

    @Override
    public void onPlay() {
        map = new Map(this);
        map.setListener(this);
        FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
        menuLayout.removeView(menu);
        menuLayout.addView(map);
        menu = null;
        System.gc();
    }

    @Override
    public void onLevelSelected() {
        if(game != null){
            game.start();
            paused = false;
            FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
            menuLayout.removeView(map);
            map = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        detector.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(!paused)
            game.robotJump();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}