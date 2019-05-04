package com.mushan.proxyplugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by libinbin on 2019/5/4.
 */

public class PluginIntent extends Intent {
    
    public static final String PLUGIN_CLASS_NAME = "plugin_class_name";
    
    public static final String PACKAGE_NAME = "package_name";


    public PluginIntent(Context packageContext) {
        super(packageContext, ProxyActivity.class);
    }
}
