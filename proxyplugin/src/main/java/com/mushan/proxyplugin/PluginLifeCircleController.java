package com.mushan.proxyplugin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.lang.reflect.Constructor;

/**
 * Created by libinbin on 2019/5/4.
 */

public class PluginLifeCircleController implements PluginLifeCircle {

    private static final String PLUGIN_CLASS_NAME = PluginIntent.PLUGIN_CLASS_NAME;

    private static final String PACKAGE_NAME = PluginIntent.PACKAGE_NAME;

    Activity mProxy;

    PluginActivity mPlugin;

    Resources mResources;

    Resources.Theme mTheme;

    PluginApk mPluginApk;

    String mPluginClazz;

    public PluginLifeCircleController(Activity activity) {
        this.mProxy = activity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        mPluginClazz = bundle.getString(PLUGIN_CLASS_NAME);
        String packageName = bundle.getString(PACKAGE_NAME);
        mPluginApk = PluginManager.getInstance().getPluginApk(packageName);
        try {
            mPlugin = (PluginActivity) loadPlugin(mPluginApk.getClassLoader(),mPluginClazz);
            mPlugin.attach(mProxy,mPluginApk);
            mResources = mPluginApk.getPluginRes();
            mPlugin.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Object loadPlugin(ClassLoader classLoader, String pluginActivityClass) throws Exception {
        Class<?> pluginClz = classLoader.loadClass(pluginActivityClass);
        Constructor<?> constructor = pluginClz.getConstructor(new Class[]{});
        constructor.setAccessible(true);
        return constructor.newInstance(new Object[] {});
    }

    @Override
    public void onStart() {
        if (mPlugin != null) {
            mPlugin.onStart();
        }
    }

    @Override
    public void onResume() {
        if (mPlugin != null) {
            mPlugin.onResume();
        }
    }

    @Override
    public void onStop() {
        mPlugin.onStop();
    }

    @Override
    public void onPause() {
        mPlugin.onPause();
    }

    @Override
    public void onDestroy() {
        mPlugin.onDestroy();
    }

    public Resources getResources() {
        return mResources;
    }

    public Resources.Theme getTheme() {
        return mTheme;
    }

    public AssetManager getAssets() {
        return mResources.getAssets();
    }
}
