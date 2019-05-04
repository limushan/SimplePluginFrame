package com.mushan.proxyplugin;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by libinbin on 2019/5/4.
 */

public class PluginApk {
    private PackageInfo mPackageInfo;

    private DexClassLoader mClassLoader;

    private Resources mPluginRes;

    public PluginApk(Resources pluginRes) {
        this.mPluginRes = pluginRes;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public void setPackageInfo(PackageInfo mPackageInfo) {
        this.mPackageInfo = mPackageInfo;
    }

    public DexClassLoader getClassLoader() {
        return mClassLoader;
    }

    public void setClassLoader(DexClassLoader mClassLoader) {
        this.mClassLoader = mClassLoader;
    }

    public Resources getPluginRes() {
        return mPluginRes;
    }

    public void setPluginRes(Resources mPluginRes) {
        this.mPluginRes = mPluginRes;
    }
}
