package com.npu.zhang.processingdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity {
    private String data;
    private boolean flag = false;

    private EditText et_ip;
    private EditText et_port;

    private boolean stopFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        et_ip = (EditText) findViewById(R.id.et_ip);
        et_port = (EditText) findViewById(R.id.et_port);

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        manager.registerListener(new OrientationListener(), sensor, SensorManager.SENSOR_DELAY_GAME);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFlag = false;
                findViewById(R.id.btn_start).setEnabled(false);
                findViewById(R.id.btn_stop).setEnabled(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket(et_ip.getText().toString(), Integer.parseInt(et_port.getText().toString()));
                            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                            System.out.println("建立连接");
                            while(true){
                                if (stopFlag){
                                    break;
                                }
                                if (!flag){
                                    continue;
                                }
                                System.out.println("send");
                                flag = false;
                                outputStream.writeUTF(" " + data + "\r\n");
                                outputStream.flush();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFlag = true;
                findViewById(R.id.btn_start).setEnabled(true);
                findViewById(R.id.btn_stop).setEnabled(false);
            }
        });
    }

    private class OrientationListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            data = sensorEvent.values[0] + " " + sensorEvent.values[1] + " " + sensorEvent.values[2] + " ";
            flag = true;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }


}
