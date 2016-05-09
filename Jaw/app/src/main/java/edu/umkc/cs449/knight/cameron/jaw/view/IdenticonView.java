package edu.umkc.cs449.knight.cameron.jaw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by camjknight on 3/6/16.
 */
public class IdenticonView extends View {

    private byte[] mHash;
    private Paint mPaint;
    private final Path mClippingPath = new Path();
    private int mCellHeight;
    private int mCellWidth;
    private int mTop;
    private int mLeft;

    private
    @ColorInt
    int mForegroundColor;

    public IdenticonView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setText(null);
        mPaint = new Paint();
    }

    public void setText(String text) {
        if (text == null) {
            text = "";
        }

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }

        setHash(messageDigest.digest(text.getBytes()));
    }

    private void setHash(byte[] hash) {
        mHash = hash;

        float[] hsv = {0, 0, 0};
        Color.RGBToHSV(mHash[0], mHash[1], mHash[2], hsv);
        hsv[1] = 0.75f;
        hsv[2] = 0.75f;
        mForegroundColor = Color.HSVToColor(hsv);

        invalidate();
    }

    public IdenticonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IdenticonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        float scale = 1f / 12f;
        mLeft = (int) (width * scale);
        mTop = (int) (height * scale);

        mClippingPath.reset();
        float rectScale = 1f / 8f;
        int rectLeft = (int) (width * rectScale);
        int rectTop = (int) (height * rectScale);
        RectF rect = new RectF(rectLeft, rectTop, width - rectLeft, height - rectTop);
        mClippingPath.addRoundRect(rect, mLeft, mTop, Path.Direction.CCW);
        mClippingPath.close();

        int newWidth = width - 2 * mLeft;
        int newHeight = height - 2 * mTop;

        mCellHeight = newWidth / 5;
        mCellWidth = newHeight / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipPath(mClippingPath, Region.Op.INTERSECT);
        int backgroundColor = 0xFFEEEEEE;
        canvas.drawColor(backgroundColor);
        mPaint.setColor(mForegroundColor);

        for (int i = 0; i < 15; ++i) {
            if (mHash[i] % 2 == 0) {
                continue;
            }

            int row = i % 5;
            int column = i / 5 + 2;
            drawTile(canvas, row, column);
            if (column != 2) {
                drawTile(canvas, row, 4 - column);
            }
        }
    }

    private void drawTile(Canvas canvas, int row, int column) {
        int left = column * mCellWidth + mLeft;
        int top = row * mCellHeight + mTop;
        int right = left + mCellWidth;
        int bottom = top + mCellHeight;
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

}
