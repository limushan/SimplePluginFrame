package com.mushan.pluginapk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mushan.proxyplugin.PluginActivity;

public class MainActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
