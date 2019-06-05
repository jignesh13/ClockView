# ClockView
clockwidget with real time and animation
you can edit source code based on your requirment please look at speedometerview.java it easy to customize needle,guage and all other stuff

<image src=https://user-images.githubusercontent.com/20221469/58963056-1cedc980-87ca-11e9-9134-eb03611694e3.gif
 width=225 height=400>
 
**note:** use only square view

### how to use
```xml
 <com.example.speedometer.SpeedoMeterView
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:id="@+id/speedometerview"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

```
### how to increase speed
```java
      SpeedoMeterView speedoMeterView=findViewById(R.id.speedometerview);
      speedoMeterView.setSpeed(60,true);//speed set 0 to 140
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
