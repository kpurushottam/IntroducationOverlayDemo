package com.krp.android.maskdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(contentView);
        Toolbar toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.hellow);

        fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final ViewGroup vg = (ViewGroup) getWindow().getDecorView().getRootView();
        final OverlayView overlayView = new OverlayView(this);
        overlayView.setOnRedeemOverlayListener(new OnRedeemOverlayListener() {
            @Override
            public void onRedeemedOverlay() {
                vg.removeView(overlayView);
            }
        });
        vg.addView(overlayView);
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


    public interface OnRedeemOverlayListener {
        void onRedeemedOverlay();
    }

    public class OverlayView extends View {
        private Bitmap mWindowFrame;
        private Canvas mCanvas;
        private RectF mOuterRectangle;
        private final Paint mPaint;
        private final PorterDuffXfermode mXfermode;

        private OnRedeemOverlayListener mOnRedeemOverlayListener;

        public OverlayView(Context pContext) {
            this(pContext, null);
        }

        public OverlayView(Context pContext, AttributeSet attr) {
            super(pContext, attr);
            mOuterRectangle = new RectF();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        }

        public void setOnRedeemOverlayListener(OnRedeemOverlayListener listener) {
            mOnRedeemOverlayListener = listener;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (mWindowFrame == null) {
                mWindowFrame = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mWindowFrame);

                mPaint.setColor(Color.parseColor("#A0000000"));
                mOuterRectangle.set(0, 0, getWidth(), getHeight());
                mCanvas.drawRect(mOuterRectangle, mPaint);

                mPaint.setColor(Color.TRANSPARENT);
                mPaint.setXfermode(mXfermode);

                int[] location = new int[2];
                fab.getLocationOnScreen(location);
                float centerX = location[0] + fab.getWidth()/2;
                float centerY = location[1] + fab.getHeight()/2;
                float radius = 150f;
                mCanvas.drawCircle(centerX, centerY, radius, mPaint);


                textView.getLocationOnScreen(location);
                float centerXt = location[0] + textView.getWidth()/2;
                float centerYt = location[1] + textView.getHeight()/2;
                float radiust = 150f;
                mCanvas.drawCircle(centerXt, centerYt, radiust, mPaint);

                // custom drawing code here
                // remember: y increases from top to bottom
                // x increases from left to right
                mCanvas.save();
                mCanvas.translate(100, 200);
                // make the entire canvas white
               // mCanvas.drawColor(Color.WHITE);

                Paint textPaint = new Paint();
                // draw some text using STROKE style
                textPaint.setStyle(Paint.Style.STROKE);
                textPaint.setStrokeWidth(1);
                textPaint.setColor(Color.MAGENTA);
                textPaint.setTextSize(100);
                mCanvas.drawText("Style.STROKE", 0, 0, textPaint);

                mCanvas.translate(0, 200);

                // draw some text using FILL style
                textPaint.setStyle(Paint.Style.FILL);
                //turn antialiasing on
                textPaint.setAntiAlias(true);
                //paint.setTextSize(30);
                mCanvas.drawText("Style.FILL", 0, 0, textPaint);
//                textPaint.setTextSize(50f);
//                textPaint.setTextScaleX(6f);
//                textPaint.setTextSkewX(0.1f);

                mCanvas.translate(0, 200);
                // draw some rotated text
                // get text width and height
                // set desired drawing location
                int x = 75;
                int y = 185;
                textPaint.setColor(Color.WHITE);
                //paint.setTextSize(25);
                String str2rotate = "Rotated!";
                // draw bounding rect before rotating text
                Rect rect = new Rect();
                textPaint.getTextBounds(str2rotate, 0, str2rotate.length(), rect);
                mCanvas.translate(x, y);
                textPaint.setStyle(Paint.Style.FILL);

                // draw unrotated text
                mCanvas.drawText("!Rotated", 0, 0, textPaint);

                textPaint.setStyle(Paint.Style.STROKE);
                mCanvas.drawRect(rect, textPaint);
                // undo the translate
                mCanvas.translate(-x, -y);

                // rotate the canvas on center of the text to draw
                mCanvas.rotate(-45, x + rect.exactCenterX(),
                        y + rect.exactCenterY());
                // draw the rotated text
                textPaint.setStyle(Paint.Style.FILL);
                mCanvas.drawText(str2rotate, x, y, textPaint);

                //undo the translation and rotation
                mCanvas.restore();
            }

            canvas.drawBitmap(mWindowFrame, 0, 0, null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int eventAction = event.getAction();

            // you may need the x/y location
            float x = event.getX();
            float y = event.getY();

            // put your code here to handle the event
            switch (eventAction) {
                case MotionEvent.ACTION_DOWN : {
                    int[] location = new int[2];
                    fab.getLocationOnScreen(location);

                    if(x >= location[0] && x <= location[0]+fab.getWidth() &&
                            y >= location[1] && y <= location[1]+fab.getHeight() &&
                            mOnRedeemOverlayListener != null) {
                        mOnRedeemOverlayListener.onRedeemedOverlay();
                    }
                    break;
                }

                case MotionEvent.ACTION_UP : {
                    break;
                }

                case MotionEvent.ACTION_MOVE : {
                    break;
                }
            }

            // tell the View to redraw the Canvas
            invalidate();

            // tell the View that we handled the event
            return true;
        }

    }
}
