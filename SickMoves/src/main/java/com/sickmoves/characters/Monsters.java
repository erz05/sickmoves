package com.sickmoves.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by erz on 12/13/13.
 */
public class Monsters {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 2;
    private static final int MAX_SPEED = 5;
    private int[] posX;
    private int[] posY;
    private int[] speed;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int canvasW;
    private int canvasH;
    private boolean[] alive;
    private Rect src;
    private Rect dst;

    private Random random;

    private int rMon1 = 0, rMon2 = 1, rMon3 = 2, rMon4 = 3;

    public Monsters(int w, int h, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.bmp = bmp;

        this.canvasW = w;
        this.canvasH = h;
        posY = new int[4];

        int x = w;
        posX = new int[4];
        alive = new boolean[4];

        random = new Random();

        speed = new int[4];

        for(int i=0; i<4; i++){
            posX[i] = x;
            x += 150;
            alive[i] = true;
            posY[i] = random.nextInt((canvasH-height) - height)+height;
            speed[i] = random.nextInt(30-20)+20;
        }

        src = new Rect();
        dst = new Rect();
    }

    private void update() {
        for(int i=0; i<4; i++){
            posX[i] -= speed[i];
            if(posX[i] < 0){
                posX[i] = canvasW + random.nextInt(300-150)+150;
                posY[i] = random.nextInt((canvasH-height) - height)+height;
                speed[i] = random.nextInt(30-20)+20;
            }
        }

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY;
        if(alive[0]){
            srcY = rMon1 * height;
            src.set(srcX, srcY, srcX + width, srcY + height);
            dst.set(posX[0], posY[0], posX[0] + width, posY[0] + height);
            canvas.drawBitmap(bmp, src, dst, null);
        }

        if(alive[1]){
            srcY = rMon2 * height;
            src.set(srcX, srcY, srcX + width, srcY + height);
            dst.set(posX[1], posY[1], posX[1] + width, posY[1] + height);
            canvas.drawBitmap(bmp, src, dst, null);
        }

        if(alive[2]){
            srcY = rMon3 * height;
            src.set(srcX, srcY, srcX + width, srcY + height);
            dst.set(posX[2], posY[2], posX[2] + width, posY[2] + height);
            canvas.drawBitmap(bmp, src, dst, null);
        }

        if(alive[3]){
            srcY = rMon4 * height;
            src.set(srcX, srcY, srcX + width, srcY + height);
            dst.set(posX[3], posY[3], posX[3] + width, posY[3] + height);
            canvas.drawBitmap(bmp, src, dst, null);
        }
    }

    public boolean checkCollision(Rect rect){
        for(int i=0; i<4; i++){
            dst.set(posX[i], posY[i], posX[i]+width, posY[i] + height);
            if(dst.intersect(rect)){
                posX[i] = canvasW + 150;
                posY[i] = random.nextInt((canvasH-height) - height)+height;
                speed[i] = random.nextInt(30-20)+20;
                return true;
            }
        }
        return false;
    }
}
