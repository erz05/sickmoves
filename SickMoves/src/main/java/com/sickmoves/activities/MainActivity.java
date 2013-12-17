package com.sickmoves.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sickmoves.R;
import com.sickmoves.listeners.MapListener;
import com.sickmoves.listeners.MenuListener;
import com.sickmoves.util.MathProblem;
import com.sickmoves.views.Game;
import com.sickmoves.views.Map;
import com.sickmoves.views.Menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends Activity implements MenuListener, MapListener {

    private static final String PREFS_NAME = "MyPrefsFile";
    private int level;
    private int score;
    private Game game;
    private Menu menu;
    private Map map;

    //private HashMap<String, Integer> questions;
    private MathProblem mathProblem;

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

        final TextView red = (TextView) findViewById(R.id.red);
        final TextView blue = (TextView) findViewById(R.id.blue);
        final TextView green = (TextView) findViewById(R.id.green);
        final TextView yellow = (TextView) findViewById(R.id.yellow);
        final TextView question = (TextView) findViewById(R.id.question);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "red Clicked");
                game.changeRobot(1);
                checkAnswer(red.getText().toString());
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "blue Clicked");
                game.changeRobot(2);
                checkAnswer(blue.getText().toString());
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "green Clicked");
                game.changeRobot(3);
                checkAnswer(green.getText().toString());
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DELETE_THIS", "yellow Clicked");
                game.changeRobot(4);
                checkAnswer(yellow.getText().toString());
            }
        });

        FrameLayout menuLayout = (FrameLayout) findViewById(R.id.menuLayout);
        menu = new Menu(this);
        menu.setListener(this);
        menuLayout.addView(menu);

        //questions = new HashMap<String, Integer>();
        addQuestion();
        question.setText(mathProblem.problem);
        setAnswers();

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
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
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

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {

            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void addQuestion(){
        Random random = new Random();
        int x = random.nextInt(20);
        int y = random.nextInt(20);
        int z = x * y;
        String s = x + " * " + y + " = ?";
        //questions.put(s, z);
        mathProblem = new MathProblem(z, s);
    }

    public void setAnswers(){
        TextView red = (TextView) findViewById(R.id.red);
        TextView blue = (TextView) findViewById(R.id.blue);
        TextView green = (TextView) findViewById(R.id.green);
        TextView yellow = (TextView) findViewById(R.id.yellow);

        Random r = new Random();
        red.setText(""+r.nextInt(400));
        blue.setText(""+r.nextInt(400));
        green.setText(""+r.nextInt(400));
        yellow.setText(""+r.nextInt(400));

        int answer = r.nextInt(4);
        switch (answer){
            case 0:
                red.setText(""+mathProblem.answer);
                break;
            case 1:
                blue.setText(""+mathProblem.answer);
                break;
            case 2:
                green.setText(""+mathProblem.answer);
                break;
            case 3:
                yellow.setText(""+mathProblem.answer);
                break;
        }
    }

    public void checkAnswer(String str){
        if(str.equals(mathProblem.answer+"")){
            addQuestion();
            final TextView question = (TextView) findViewById(R.id.question);
            question.setText(mathProblem.problem);
            setAnswers();
        }
    }
}