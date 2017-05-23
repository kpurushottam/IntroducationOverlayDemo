package com.krp.android.maskdemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

/**
 * Created by purushottam on 23/5/17.
 */

public class CustomBrifcase extends View {

    public CustomBrifcase(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas1) {
        super.onDraw(canvas1);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_work_black_24dp);
        drawable = (DrawableCompat.wrap(drawable)).mutate();;

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawBitmap(bitmap, 0, 0, new Paint());
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        canvas1.drawBitmap(bitmap, 0, 0, new Paint());
    }
}
