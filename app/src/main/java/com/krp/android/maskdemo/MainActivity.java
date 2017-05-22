package com.krp.android.maskdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        //setContentView(contentView);
        Toolbar toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bitmap bitmap = Utility.getMarkerBitmapFromView(this, contentView);


        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(contentView);
            }
        });

        Paint maskPaint = new Paint();
        maskPaint.setColor(Color.TRANSPARENT);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        maskPaint.setStyle(Paint.Style.FILL);

        Bitmap mask = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas maskCanvas = new Canvas(mask);
        maskCanvas.drawCircle(100, 100, 40, maskPaint);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(new OverlayView(this, contentView), params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class OverlayView extends View {
        Bitmap bitmap;
        FloatingActionButton fab;

        public OverlayView(Context context, View contentView) {
            super(context);
            bitmap = Utility.getMarkerBitmapFromView(context, contentView);

            fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        }


        @Override
        public void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            // paint.setColor(Color.CYAN);
            canvas.drawBitmap(getclip(), 30, 20, paint);
        }


        public Bitmap getclip() {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            Paint paint = new Paint();


            fab.measure(View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));


            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(61, 172, 163, 166);
            // paint.setColor(color);
            canvas.drawCircle(/*bitmap.getWidth() / 2*/fab.getX(),
                    /*bitmap.getHeight() / 2*/fab.getY(), bitmap.getWidth() / 4, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }
    }
}
