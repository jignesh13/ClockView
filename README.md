# ClockView
clockwidget with real time and animation

This repository contains an Android project that showcases a custom view called "ClockView". The ClockView is a customizable and interactive view that displays the current time and allows users to interact with the clock face.

<image src=/clockk.gif
 width=225 height=400>
 
**note:** use only square view

## Features

- Customizable clock face design, including background color, clock hands, and markings.
- Real-time updating of the clock to display the current time accurately.
- Interactive features such as rotating the clock hands, changing time zones, and setting alarms.
- Integration with system time or the ability to set a custom time for demonstration purposes.
- Customizable animations for smooth transitions and visual effects.
- Support for different time formats and time zone conversions.
- User-friendly UI with intuitive controls and gestures for an engaging experience.


## Installation

This is published on `jcenter` and you can use clockview as:

```groovy
// build.gradle
buildscript {
    repositories {
        jcenter()
    }
    ...
}

// app/build.gradle
dependencies {
    compile 'com.clock.clockviewlib:clockviewlibrary:1.0.0'
}
```

### how to use
```xml
<com.clock.clockviewlib.ClockView
        android:layout_width="350dp"
        android:layout_height="350dp"
        app:backimage="#fff"
        app:removeborder="false"
        app:hourcolor="#000"
        app:mincolor="#000"
        app:seccolor="#FF0000"
        app:txtcolor="#000"
        app:dotcolor="#000"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

```

```java
ClockView clockView=findViewById(R.id.clockView);
clockView.setbackImageResource(R.mipmap.pexelsmip);//set background image resource or color resource
clockView.setborder(true);//add or remove outline border
clockView.setHourcolor(Color.WHITE);//hour needle color change
clockView.setMincolor(Color.WHITE);//min needle color change
clockView.setSeccolor(Color.RED);//sec needle color change
clockView.setTextcolor(Color.WHITE);//dot and next color change

```
 
##  Developer
  jignesh khunt
  (jigneshkhunt13@gmail.com)
  
##  License

Copyright 2019 jignesh khunt

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
