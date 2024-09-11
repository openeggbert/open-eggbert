package com.openeggbert.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.openeggbert.core.configuration.ScreenResolution;
import com.openeggbert.core.main.OpenEggbertGame;
import com.badlogic.gdx.ai.GdxLogger;
import com.pixelgamelibrary.Pixel;
import com.pixelgamelibrary.backend.libgdx.PixelLibGDXBackend;

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
            Pixel.initBackend(new PixelLibGDXBackend());
            return new GwtApplicationConfiguration(ScreenResolution.VGA.getWidth(), ScreenResolution.VGA.getHeight());
        }

        @Override
        public ApplicationListener createApplicationListener () {
            return new OpenEggbertGame();
        }
}
