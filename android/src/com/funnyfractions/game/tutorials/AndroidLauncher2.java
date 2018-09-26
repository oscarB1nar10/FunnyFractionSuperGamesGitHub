package com.funnyfractions.game.tutorials;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.funnyfractions.game.archery_game.FirstScreen;
import com.funnyfractions.game.archery_game.FirstScreen;

public class AndroidLauncher2 extends AndroidApplication {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useGLSurfaceView20API18=true;
        config.useAccelerometer=true;
        config.useCompass=true;



        initialize(new FirstScreen(), config);
    }
}
