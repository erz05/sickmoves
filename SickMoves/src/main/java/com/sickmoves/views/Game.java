package com.sickmoves.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sickmoves.R;
import com.sickmoves.characters.Monsters;
import com.sickmoves.characters.Sprite;
import com.sickmoves.util.GameLoopThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erz on 12/9/13.
 */
public class Game extends SurfaceView{

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite robot;
    private Monsters monsters;

    public Game(Context context) {
        super(context);
        setBackgroundColor(Color.TRANSPARENT);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(gameLoopThread != null){
                    boolean retry = true;
                    gameLoopThread.setRunning(false);
                    while (retry) {
                        try {
                            gameLoopThread.join();
                            retry = false;
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                robot = createSprite(R.drawable.sick);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.monstars);
                monsters = new Monsters(getWidth(), getHeight(), bmp);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(getWidth(), getHeight(), bmp);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(canvas != null){
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            if(robot != null && monsters != null){
                robot.onDraw(canvas);
                monsters.onDraw(canvas);

                if(monsters.checkCollision(robot.getFrontX())){
                    Log.v("DELETE_THIS", "Collision");
                }
            }
        }
    }

    public void changeRobot(int i){
        robot.setRow(i);
    }

    public void start(){
        if(gameLoopThread == null){
            gameLoopThread = new GameLoopThread(this);
        }
        if(gameLoopThread != null && !gameLoopThread.isRunning()){
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }


    public void resume(){
        if(gameLoopThread == null){
            gameLoopThread = new GameLoopThread(this);
        }
        if(gameLoopThread != null && !gameLoopThread.isRunning()){
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }

    public void pause(){
        if(gameLoopThread != null && gameLoopThread.isRunning()){
            gameLoopThread.setRunning(false);
            boolean retry = true;
            while (retry) {
                try {
                    gameLoopThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
            gameLoopThread = null;
        }
    }

    public void moveRobot(int angle, int power) {
        if(robot != null){
            robot.move(angle, power);
        }
    }
}
