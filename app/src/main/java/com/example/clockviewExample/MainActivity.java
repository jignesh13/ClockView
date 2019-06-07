package com.example.clockviewExample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.clock.clockviewlib.ClockView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int resource[]={R.mipmap.pexelsmip,R.mipmap.theme,R.mipmap.theme2,R.mipmap.theme3,R.mipmap.theme5,R.mipmap.theme6};
    private int[] colorresource={android.R.color.holo_red_dark,android.R.color.black,android.R.color.white,android.R.color.holo_blue_bright};
    private ClockView clockView;
    Random random=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         clockView=findViewById(R.id.clockView);
         clockView.setBackgroundColor(100);



    }

    public void setImageClick(View view) {
        clockView.setbackImageResource(resource[random.nextInt(resource.length)]);
    }

    public void removeBorder(View view) {
        clockView.setborder(!clockView.isborder());
    }

    public void hourcolorchange(View view) {
        clockView.setHourcolor(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
    }
    public void mincolorchange(View view) {
        clockView.setMincolor(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
    }
    public void seccolorchange(View view) {
        clockView.setSeccolor(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
    }
    public void textcolorchange(View view) {
        clockView.setTextcolor(Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
    }

    public void setbackground(View view) {
        clockView.setbackImageResource(colorresource[random.nextInt(colorresource.length)]);
    }
}
