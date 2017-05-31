package com.npu.zhang.processingdemo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.npu.zhang.processingdemo.fragment.Gyroscope;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Gyroscope();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}
