package com.mushan.proxyplugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * Created by libinbin on 2019/5/4.
 */

public class PluginManager {
//    public void startActivity(Context context,Intent intent) {
//        context.startActivity(intent);
//    }

    private static class PluginMangerHolder {
        private static PluginManager mManager = new PluginManager();
    }


    private static Context mContext;

    HashMap<String,PluginApk> mPluginApkMap = new HashMap<>();

    public static PluginManager getInstance(){
        return PluginMangerHolder.mManager;
    }

    public PluginApk getPluginApk(String packageName){
        return mPluginApkMap.get(packageName);
    }

    public static void init(Context context){
        mContext = context.getApplicationContext();
    }

    public final void loadApk(String apkPath){
        PackageInfo packageInfo = queryPackageInfo(apkPath);
        if (packageInfo == null || TextUtils.isEmpty(packageInfo.packageName)){
            return;
        }
        PluginApk pluginApk = mPluginApkMap.get(packageInfo.packageName);

        if (pluginApk == null){
            pluginApk = createApk(apkPath);
            if (pluginApk != null){
                pluginApk.setPackageInfo(packageInfo);
                mPluginApkMap.put(packageInfo.packageName,pluginApk);
            }else {
                throw new RuntimeException("PluginApk is null");
            }
        }
    }

    private PluginApk createApk(String apkPath) {
        String addAssetPahMethod = "addAssetPath";
        PluginApk pluginApk = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod(addAssetPahMethod,String.class);
            addAssetPath.invoke(assetManager , apkPath);
            Resources pluginRes = new Resources(assetManager,
                    mContext.getResources().getDisplayMetrics(),
                    mContext.getResources().getConfiguration());
            pluginApk = new PluginApk(pluginRes);
            pluginApk.setClassLoader(createDexClassLoader(apkPath));

        } catch (InstantiationException
                |IllegalAccessException
                |NoSuchMethodException
                |InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginApk;
    }

    private PackageInfo queryPackageInfo(String apkPath){
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null){
            return null;
        }
        return packageInfo;
    }

    private DexClassLoader createDexClassLoader(String apkPath){
        return new DexClassLoader(apkPath, mContext.getDir("dex",
                Context.MODE_PRIVATE).getAbsolutePath(), null, mContext.getClassLoader());
    }

}
