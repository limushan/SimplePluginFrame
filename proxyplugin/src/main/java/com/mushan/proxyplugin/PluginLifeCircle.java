package com.mushan.proxyplugin;

import android.os.Bundle;

/**
 * Created by libinbin on 2019/5/4.
 */

public interface PluginLifeCircle {

    void onCreate(Bundle bundle);

    void onStart();

    void onResume();

    void onStop();

    void onPause();

    void onDestroy();
}
