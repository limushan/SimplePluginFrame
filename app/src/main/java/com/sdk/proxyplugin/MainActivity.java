 package com.sdk.proxyplugin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mushan.proxyplugin.PluginIntent;
import com.mushan.proxyplugin.PluginManager;

import java.io.File;

 public class MainActivity extends AppCompatActivity {

     public final static String PLUGIN_NAME = "plugin.apk";
     public final static String PLUGIN_PACKAGE_NAME = "com.mushan.pluginapk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PluginIntent(MainActivity.this);
                intent.putExtra(PluginIntent.PACKAGE_NAME, PLUGIN_PACKAGE_NAME);
                intent.putExtra(PluginIntent.PLUGIN_CLASS_NAME, "com.mushan.pluginapk.MainActivity");
                MainActivity.this.startActivity(intent);
            }
        });

        verifyStoragePermissions(this);

        PluginManager.init(getApplicationContext());
        String pluginApkPath = Environment.getExternalStorageDirectory() +
                File.separator + "plugins" + File.separator + PLUGIN_NAME;
        PluginManager.getInstance().loadApk(pluginApkPath);
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
