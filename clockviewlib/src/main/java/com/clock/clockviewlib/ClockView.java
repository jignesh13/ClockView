package com.clock.clockviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class ClockView extends View{
    private  Drawable drawable;
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private int resouceid;
    private Bitmap backgroundbitmap,backimagebitmap;
    private Paint mpaint;
    private int hourcolor,mincolor,seccolor,textcolor,dotcolor,backcolor;
    private float centerx,centery,radius;
    private Path minpath,hourpath,secpath;
   private boolean isremoveborder;
    private Handler handler = new Handler();

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setborder(boolean isremoveborder) {
        this.isremoveborder = isremoveborder;
        init();
    }

    public boolean isborder() {
        return isremoveborder;
    }

    public void setbackImageResource(@DrawableRes int resId){
         drawable = ContextCompat.getDrawable(getContext(),resId);
         init();

    }

    public void setTextcolor(@ColorInt int textcolor) {
        this.textcolor = textcolor;
        this.dotcolor=textcolor;
        init();
    }


    public void setHourcolor(@ColorInt int hourcolor) {
        this.hourcolor = hourcolor;

    }

    public void setMincolor(@ColorInt int mincolor) {
        this.mincolor = mincolor;

    }

    public void setSeccolor(@ColorInt int seccolor) {
        this.seccolor = seccolor;

    }


    public ClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if(attributeSet == null){
            return;
        }

        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.MyCustomView);
        backcolor=ta.getColor(R.styleable.MyCustomView_backcolor,Color.WHITE);
        hourcolor=ta.getColor(R.styleable.MyCustomView_hourcolor,Color.BLACK);
        mincolor=ta.getColor(R.styleable.MyCustomView_mincolor,Color.BLACK);
        seccolor=ta.getColor(R.styleable.MyCustomView_seccolor,Color.RED);
        textcolor=ta.getColor(R.styleable.MyCustomView_txtcolor,Color.BLACK);
        dotcolor=ta.getColor(R.styleable.MyCustomView_dotcolor,Color.BLACK);
        drawable = ta.getDrawable(R.styleable.MyCustomView_backimage);
        isremoveborder=ta.getBoolean(R.styleable.MyCustomView_removeborder,false);



    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (getWidth()>0||getHeight()>0){

            init();
        }
    }
    private void init(){
        centerx=getWidth()/2.0f;
        centery=getHeight()/2.0f;
        radius=Math.min(getWidth()/2.0f,getHeight()/2.0f);
        drawBackGroundBitmap();
        if (drawable!=null){
            backimagebitmap=transform(drawableToBitmap(drawable));
        }

        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(radius/14.0f);
        mpaint.setColor(Color.WHITE);
        setLayerType(LAYER_TYPE_SOFTWARE, mpaint);
        mpaint.setShadowLayer(radius/21.0f, 0, 0, Color.rgb(70,70,70));;



        minpath=new Path();
        float  bottomY = getWidth()/2.0f ;
        float toppadding=(radius/14.0f)*4.0f;
        float width=radius/49.0f;
        minpath.moveTo(getWidth()/2.0f,toppadding);
        minpath.addRoundRect(new RectF(centerx-width,toppadding,centerx+width,bottomY),width,width, Path.Direction.CW);


        toppadding=(radius/14.0f)*8.0f;
        hourpath=new Path();
        hourpath.moveTo(getWidth()/2.0f,toppadding);
        hourpath.addRoundRect(new RectF(centerx-width,toppadding,centerx+width,bottomY),width,width, Path.Direction.CW);



        bottomY = getWidth()/1.7f ;
        width=radius/91.7f;
        toppadding=radius/5.0f;
        secpath=new Path();
        secpath.moveTo(getWidth()/2.0f,toppadding);
        secpath.addRoundRect(new RectF(centerx-width,toppadding,centerx+width,bottomY),width,width, Path.Direction.CW);


        paint.setStyle(Paint.Style.FILL);
    }
    public Bitmap transform(Bitmap source) {
        source=Bitmap.createScaledBitmap(source,getWidth(),getHeight(),false);
        int size = Math.min(source.getWidth(),source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r-(r/14.0f), paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path=new Path();
        path.addCircle(centerx,centery,radius, Path.Direction.CW);
        canvas.clipPath(path);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backcolor);
        if (backimagebitmap!=null)canvas.drawBitmap(backimagebitmap,0,0,paint);
        else {
            if (isremoveborder)canvas.drawCircle(centerx,centery,radius-radius/14.0f,paint);
            else canvas.drawCircle(centerx,centery,radius,paint);
        }

        if (!isremoveborder)canvas.drawCircle(centerx,centery,radius-radius/28.0f,mpaint);

        if (backgroundbitmap!=null)canvas.drawBitmap(backgroundbitmap,0,0,paint);
        paint.setColor(hourcolor);
        canvas.drawCircle(centerx,centery,radius/25.7f,paint);
        LocalDateTime now = LocalDateTime.now();


        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND); // Note: no direct getter available.
        hour=hour>12?hour-12:hour;

        paint.setColor(hourcolor);
       // canvas.drawCircle(centerx,centerx,radius/98.5f,paint);

        canvas.save();
        canvas.rotate((hour*30.0f)+((minute/60.0f)*30.0f),centerx,centery);
        canvas.drawPath(hourpath,paint);
        canvas.restore();



        paint.setColor(mincolor);

        canvas.save();
        canvas.rotate((6.0f*minute)+((second/60.0f)*6.0f),centerx,centery);
        canvas.drawPath(minpath,paint);
        canvas.restore();

        paint.setColor(seccolor);
        canvas.save();
        canvas.rotate((6.0f*second)+((millis/1000.0f)*6.0f),centerx,centery);
        canvas.drawPath(secpath,paint);
        canvas.restore();

        canvas.drawCircle(centerx,centerx,radius/45.0f,paint);


        super.onDraw(canvas);
    }

    private void drawBackGroundBitmap(){
        backgroundbitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(backgroundbitmap);
        paint.setStyle(Paint.Style.STROKE);
        float outerwidth=radius/14.0f;
        paint.setStrokeWidth(outerwidth);
        Shader shader = new LinearGradient(0, 0,0, getHeight(), Color.rgb(215,231,236), Color.rgb(117,117,117), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        if (!isremoveborder)canvas.drawCircle(centerx,centery,radius-outerwidth/2.0f,paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        int o=1;
        Log.e("radius",radius+"");
        TextPaint textPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(radius/8.f);

        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setColor(Color.BLACK);
        float minus=2*outerwidth+5;
        for (int i=-60;i<300;i+=6){
            float xstart = (float) ((radius-2*outerwidth) * Math.cos(Math.toRadians(i)) + centerx);
            float ystart = (float) ((radius-2*outerwidth) * Math.sin(Math.toRadians(i)) + centery);
            float xstart1=0;
            float ystart1=0;
            float w=textPaint.measureText(o+"");
            if (i==-60){
                textPaint.setTextAlign(Paint.Align.CENTER);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/4.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery+textPaint.getTextSize()/5.0f);
            }
            else if (i==0){
                textPaint.setTextAlign(Paint.Align.CENTER);
                xstart1 = (float) ((radius-minus) * Math.cos(Math.toRadians(i)) + centerx-w/1.3f);
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/2.5f;
            }
            else if (i==30){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/2.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/2.0f;

            }
            else if (i==60){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/2.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/1.8f;

            }
            else if (i==90){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/2.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/1.5f;

            }
            else if (i==120){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/2.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/1.5f;

            }

            else if (i==150){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/2.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/1.5f;

            }
            else if (i==180){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/2.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery)+textPaint.getTextSize()/2.4f;

            }
            else if (i==210){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/3.0f;
                ystart1 = (float) ((radius-40-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery+textPaint.getTextSize()/2.f);

            }
            else if (i==240){
                textPaint.setTextAlign(Paint.Align.RIGHT);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx)+w/3.0f;
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery+textPaint.getTextSize()/5.0f);

            }
            else {
                textPaint.setTextAlign(Paint.Align.CENTER);
                xstart1 = (float) ((radius-minus-w) * Math.cos(Math.toRadians(i)) + centerx);
                ystart1 = (float) ((radius-minus-textPaint.getTextSize()) * Math.sin(Math.toRadians(i)) +centery);
            }

            paint.setColor(Color.BLACK);
            if (i%30==0){
                paint.setColor(dotcolor);
                canvas.drawCircle(xstart,ystart,radius/78.8f,paint);
                textPaint.setColor(textcolor);
                canvas.drawText(o+"",xstart1,ystart1,textPaint);
                o++;
            }
            else {
                paint.setColor(dotcolor);
                canvas.drawCircle(xstart,ystart,radius/157.6f,paint);
            }

        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
                handler.postDelayed(this,1);
            }
        },1);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);

    }
}
