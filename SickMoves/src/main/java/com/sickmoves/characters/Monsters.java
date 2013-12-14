package com.sickmoves.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by erz on 12/13/13.
 */
public class Monsters {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 2;
    private static final int MAX_SPEED = 5;
    private int[] posX;
    private int posY = 0;
    private int[] speed;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int canvasW;

    private int rMon1 = 0, rMon2 = 1, rMon3 = 2, rMon4 = 3;

    public Monsters(int w, int h, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.bmp = bmp;

        this.canvasW = w;
        posY = h/2 - height/2;
        int x = w;
        posX = new int[4];
        for(int i=0; i<4; i++){
            posX[i] = x;
            x += 150;
        }
    }

    private void update() {
        for(int i=0; i<4; i++){
            posX[i] -= 15;
            if(posX[i] < 0){
                posX[i] = canvasW + 150;
            }
        }

        /*if (x >= game.getWidth() - width - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y >= game.getHeight() - height - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;*/
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = rMon1 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(posX[0], posY, posX[0] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);

        srcY = rMon2 * height;
        src = new Rect(srcX, srcY, srcX + width, srcY + height);
        dst = new Rect(posX[1], posY, posX[1] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);

        srcY = rMon3 * height;
        src = new Rect(srcX, srcY, srcX + width, srcY + height);
        dst = new Rect(posX[2], posY, posX[2] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);

        srcY = rMon4 * height;
        src = new Rect(srcX, srcY, srcX + width, srcY + height);
        dst = new Rect(posX[3], posY, posX[3] + width, posY + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }
}
