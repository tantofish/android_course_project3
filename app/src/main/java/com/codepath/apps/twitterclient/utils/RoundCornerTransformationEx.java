package com.codepath.apps.twitterclient.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class RoundCornerTransformationEx implements com.squareup.picasso.Transformation{

    private int mBorderSize;
    private int mCornerRadius;
    private int mColor;

    public RoundCornerTransformationEx() {
        this.mBorderSize = 4;
        this.mCornerRadius = 10;
        this.mColor = Color.WHITE;
    }
    public RoundCornerTransformationEx(int mBorderSize, int mCornerRadius, int mColor) {
        this.mBorderSize = mBorderSize;
        this.mCornerRadius = mCornerRadius;
        this.mColor = mColor;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        // TODO Auto-generated method stub
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = mColor;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = mCornerRadius;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);

        // draw border
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) mBorderSize);
        canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);
        //-------------------

        if(source != output) source.recycle();

        return output;
    }

    @Override
    public String key() {
        // TODO Auto-generated method stub
        return "grayscaleTransformation()";
    }

}