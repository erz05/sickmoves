package com.sickmoves.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sickmoves.R;
import com.sickmoves.listeners.MapListener;
import com.sickmoves.listeners.MenuListener;
import com.sickmoves.util.MathProblem;
import com.sickmoves.views.BackgroundView;
import com.sickmoves.views.Game;
import com.sickmoves.views.JoyStick;
import com.sickmoves.views.Map;
import com.sickmoves.views.Menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends Activity implements MenuListener, MapListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private int level;
    private int score;
    private Game game;
    private Menu menu;
    private Map map;
    private BackgroundView background;
    private boolean paused = false;

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

        JoyStick joyStickLeft = (JoyStick) findViewById(R.id.joyStickLeft);
        joyStickLeft.setOnJoystickMoveListener(new JoyStick.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                game.moveRobot(angle, power);
            }
        }, JoyStick.DEFAULT_LOOP_INTERVAL);

        JoyStick joyStickRight = (JoyStick) findViewById(R.id.joyStickRight);
        joyStickRight.setOnJoystickMoveListener(new JoyStick.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                switch (direction) {
                    case JoyStick.FRONT:
                        game.changeRobot(6);
                        break;
                    case JoyStick.FRONT_RIGHT:
                        game.changeRobot(2);
                        break;
                    case JoyStick.RIGHT:
                        game.changeRobot(1);
                        break;
                    case JoyStick.RIGHT_BOTTOM:
                        game.changeRobot(3);
                        break;
                    case JoyStick.BOTTOM:
                        game.changeRobot(2);
                        break;
                    case JoyStick.BOTTOM_LEFT:
                        game.changeRobot(4);
                        break;
                    case JoyStick.LEFT:
                        game.changeRobot(3);
                        break;
                    case JoyStick.LEFT_FRONT:
                        game.changeRobot(5);
                        break;
                    default:
                        game.changeRobot(0);
                }
            }
        }, JoyStick.DEFAULT_LOOP_INTERVAL);

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
            FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
            menuLayout.removeView(map);
            map = null;
            System.gc();
        }
    }
}