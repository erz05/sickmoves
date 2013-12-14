package com.sickmoves.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
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

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                robot = createSprite(R.drawable.sick);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.monstars);
                monsters = new Monsters(getWidth(), getHeight(), bmp);
                /*if(!gameLoopThread.isRunning()){
                    robot = createSprite(R.drawable.sick);
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.monstars);
                    monsters = new Monsters(getWidth(), getHeight(), bmp);
                    gameLoopThread.setRunning(true);
                    gameLoopThread.start();
                }*/
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
            canvas.drawColor(Color.BLACK);
            robot.onDraw(canvas);
            monsters.onDraw(canvas);
        }
    }

    public void changeRobot(int i){
        robot.setRow(i);
    }

    public void start(){
        if(gameLoopThread != null && !gameLoopThread.isRunning()){
            //robot = createSprite(R.drawable.sick);
            //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.monstars);
            //monsters = new Monsters(getWidth(), getHeight(), bmp);
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }
}
