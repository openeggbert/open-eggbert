package com.openeggbert.teavm;

import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import com.openeggbert.core.main.OpenEggbertApplication;
import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.backend.libgdx.interfaces.PixelBackendLibGDX;
import com.pixelgamelibrary.backend.libgdx.game.LibGdxGame;

/**
 * Launches the TeaVM/HTML application.
 */
public class TeaVMLauncher {
    public static void main(String[] args) {
        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");
        //// If width and height are each greater than 0, then the app will use a fixed size.
        //config.width = 640;
        //config.height = 480;
        //// If width and height are both 0, then the app will use all available space.
        //config.width = 0;
        //config.height = 0;
        //// If width and height are both -1, then the app will fill the canvas size.
        config.width = -1;
        config.height = -1;
        if(!Pixel.isBackendSet()) {Pixel.initBackend(new PixelBackendLibGDX());}
        TeaApplication application = new TeaApplication(new LibGdxGame(new OpenEggbertApplication().createGame()), config);
    }
}
