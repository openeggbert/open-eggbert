package com.openeggbert.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.openeggbert.core.configuration.VirtualScreenResolution;
import com.openeggbert.core.main.OpenEggbertApplication;
import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.backend.libgdx.interfaces.PixelBackendLibGDX;
import com.pixelgamelibrary.backend.libgdx.game.LibGdxGame;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
        @Override
        public GwtApplicationConfiguration getConfig () {
            // Resizable application, uses available space in browser with no padding:
            GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(true);
            cfg.padVertical = 0;
            cfg.padHorizontal = 0;
            //return cfg;
            // If you want a fixed size application, comment out the above resizable section,
            // and uncomment below:
            
            return new GwtApplicationConfiguration(VirtualScreenResolution.VGA.getWidth(), VirtualScreenResolution.VGA.getHeight());
        }

        @Override
        public ApplicationListener createApplicationListener () {
            if(!Pixel.isBackendSet()) {Pixel.initBackend(new PixelBackendLibGDX());}
            return new LibGdxGame(new OpenEggbertApplication().createGame());
        }
}
