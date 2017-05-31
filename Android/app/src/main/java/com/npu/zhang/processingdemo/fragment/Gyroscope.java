package com.npu.zhang.processingdemo.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import processing.core.PApplet;

/**
 * Created by zhang on 2017/5/18.
 */

public class Gyroscope extends PApplet {
    private float ox, oy, oz;
    private float ox1, oy1, oz1;
    public void settings() {
        fullScreen(P3D);
    }

    public void setup() {
        smooth();
        SensorManager manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        OrientationListener listener = new OrientationListener();
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);

    }

    class OrientationListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            ox = sensorEvent.values[0];
            oy = sensorEvent.values[1];
            oz = -sensorEvent.values[2];
            System.out.println(ox + "_" + oy + "_" + oz);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }



    public void draw() {
        lights();
        background(0);
        stroke(255);
        if (mousePressed){
            ox1 = ox;
            oy1 = oy;
            oz1 = oz;
        }

        textSize(50);
        text("X: " + ox, 0, 50);
        text("Y: " + oy, 0, 100);
        text("Z: " + oz, 0, 150);

        text("X-X1: " + (ox-ox1), 2*width/3, 50);
        text("Y-Y1: " + (oy-oy1), 2*width/3, 100);
        text("Z-Z1: " + (oz-oz1), 2*width/3, 150);

        translate(width/2, 3*height/4);

        rotateZ((ox - ox1)*PI/180);
        rotateY((oz - oz1)*PI/180);
        rotateX((106 + oy - oy1)*PI/180);
        fill(100);
        box(50, 50, 800);
        line(0, 0, 0, -500, 0, 0);
        line(0, 0, 0, 0, 500, 0);
        line(0, 0, 0, 0, 0, 500);

        translate(0,0,450);
        rotateZ((ox - ox1)*PI/180);
        rotateY((oz - oz1)*PI/180);
        rotateX((106 + oy - oy1)*PI/180);
        fill(255);
        box(50,50,100);

    }

    @Override
    public void keyPressed() {
        Toast.makeText(getActivity(), "点击！", Toast.LENGTH_SHORT).show();
        super.keyPressed();
    }
}
