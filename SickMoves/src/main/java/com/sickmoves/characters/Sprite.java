package com.sickmoves.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by erz on 12/10/13.
 */
public class Sprite {
    private static final int BMP_ROWS = 2;
    private static final int BMP_COLUMNS = 4;
    private int x = 0;
    private int y = 0;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int canvasW;
    private int canvasH;
    private Paint paint;
    private Rect src;
    private Rect dst;

    //constant variables
    private double gravity = 9.8;

    private boolean goingup = false;
    private int groundLevel;

    private int row = 0;

    public Sprite(int w, int h, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.bmp = bmp;
        this.canvasW = w;
        this.canvasH = h;

        x = w/3 - width/2;
        groundLevel = h/2 - height/2;
        Log.v("DELETE_THIS", "groundLevel " + groundLevel);
        y = groundLevel;

        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        src = new Rect();
        dst = new Rect();
    }

    private void update() {
        currentFrame = ++currentFrame % BMP_COLUMNS;

        if(goingup){
            y -= 45;
            if(y<height/2){
                goingup = false;
            }
        }else {
            y += 45;
            if(y > groundLevel){
                row = 0;
                y = groundLevel;
            }
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = row * height;
        src.set(srcX, srcY, srcX + width, srcY + height);
        dst.set(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public void setRow(int i){
        row = i;
    }

    public Rect getRect(){
        return new Rect(x, y, x+width, y+height);
    }

    public void jump(){
        row = 1;
        goingup = true;
    }
}
