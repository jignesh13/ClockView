package com.example.clockviewExample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class ClockView extends View{
    private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap backgroundbitmap;
    float centerx,centery,radius;
   private Path minpath,hourpath,secpath;
    private Handler handler = new Handler();

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClockView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getWidth()>0||getHeight()>0){
            centerx=getWidth()/2.0f;
            centery=getHeight()/2.0f;
            radius=Math.min(getWidth()/2.0f,getHeight()/2.0f);
            drawBackGroundBitmap();
            minpath=new Path();
            float  bottomY = getWidth()/1.7f ;
            minpath.moveTo(getWidth()/2.0f,(radius/14)*3.5f);
            minpath.lineTo(centerx-4,(radius/14)*3.5f);
            minpath.lineTo(centerx-8,bottomY);
            minpath.lineTo(centerx+8,bottomY);
            minpath.lineTo(centerx+4,(radius/14)*3.5f);

            hourpath=new Path();
            hourpath.moveTo(getWidth()/2.0f,(radius/14)*7.0f);
            hourpath.lineTo(centerx-5,(radius/14)*7.0f);
            hourpath.lineTo(centerx-12,bottomY);
            hourpath.lineTo(centerx+12,bottomY);
            hourpath.lineTo(centerx+5,(radius/14)*7.0f);

            secpath=new Path();
            secpath.moveTo(getWidth()/2.0f,(radius/14)*5.0f);
            secpath.lineTo(centerx-2,(radius/14)*5.0f);
            secpath.lineTo(centerx-5,bottomY);
            secpath.lineTo(centerx+5,bottomY);
            secpath.lineTo(centerx+2,(radius/14)*5.0f);

            paint.setStyle(Paint.Style.FILL);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      if (backgroundbitmap!=null)canvas.drawBitmap(backgroundbitmap,0,0,paint);




        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawCircle(centerx,centery,20,paint);
        LocalDateTime now = LocalDateTime.now();


        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND); // Note: no direct getter available.
        hour=hour>12?hour-12:hour;
        paint.setColor(Color.argb(245,255,255,255));
        canvas.save();
        canvas.rotate((6.0f*second)+((millis/1000.0f)*6.0f),centerx,centery);
        canvas.drawPath(secpath,paint);
        canvas.restore();

        canvas.save();
        canvas.rotate((6.0f*minute)+((second/60.0f)*6.0f),centerx,centery);
        canvas.drawPath(minpath,paint);
        canvas.restore();

        canvas.save();
        canvas.rotate((hour*30.0f)+((minute/60.0f)*30.0f),centerx,centery);
        canvas.drawPath(hourpath,paint);
        canvas.restore();
        paint.setColor(Color.rgb(35,35,35));
        canvas.drawCircle(centerx,centerx,4,paint);

    }

    private void drawBackGroundBitmap(){
        backgroundbitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(backgroundbitmap);
        paint.setStyle(Paint.Style.STROKE);
        float outerwidth=radius/14;
        paint.setStrokeWidth(outerwidth);
        Shader shader = new LinearGradient(0, 0, getWidth(), getHeight(), Color.rgb(90,90,90), Color.rgb(0,0,0), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(centerx,centery,radius-outerwidth/2.0f,paint);
        Shader shader1 = new LinearGradient(0, 0, getWidth(), getHeight(), Color.rgb(0,0,0), Color.rgb(90,90,90), Shader.TileMode.CLAMP);
        paint.setShader(shader1);
        canvas.drawCircle(centerx,centerx,radius-outerwidth-outerwidth/2.0f,paint);
        paint.setShader(null);
        paint.setColor(Color.rgb(35,35 ,35));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerx,centerx,radius-(2*outerwidth)+2.0f,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        int o=1;
        Log.e("radius",radius+"");
        TextPaint textPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(radius/8.f);

        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setColor(Color.WHITE);
        float minus=2*outerwidth+15;
        for (int i=-60;i<300;i+=6){
            float xstart = (float) ((radius-2*outerwidth-10) * Math.cos(Math.toRadians(i)) + centerx);
            float ystart = (float) ((radius-2*outerwidth-10) * Math.sin(Math.toRadians(i)) + centery);
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

            paint.setColor(Color.WHITE);
            if (i%30==0){
                canvas.drawCircle(xstart,ystart,5,paint);
                canvas.drawText(o+"",xstart1,ystart1,textPaint);
                o++;
            }
            else {
                canvas.drawCircle(xstart,ystart,2.5f,paint);
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
