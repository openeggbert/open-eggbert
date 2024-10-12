package com.openeggbert.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.openeggbert.core.main.OpenEggbertApplication;
import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.backend.libgdx.interfaces.PixelBackendLibGDX;
import com.pixelgamelibrary.backend.libgdx.game.LibGdxGame;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.
        Pixel.initBackend(new PixelBackendLibGDX());
        initialize(new LibGdxGame(new OpenEggbertApplication().createGame()), configuration);
    }
}