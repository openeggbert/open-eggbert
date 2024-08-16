///////////////////////////////////////////////////////////////////////////////////////////////
// Open Eggbert: Free recreation of the computer game Speedy Eggbert.
// Copyright (C) 2024 the original author or authors.
//
// This program is free software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation, either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see 
// <https://www.gnu.org/licenses/> or write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
///////////////////////////////////////////////////////////////////////////////////////////////
package com.openeggbert.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.openeggbert.core.entity.common.ConfigDef;
import com.openeggbert.core.entity.common.OpenEggbertException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author robertvokac
 */
public enum OpenEggbertDisplayMode {
    WINDOW, FULLSCREEN;

    public static OpenEggbertDisplayMode setDisplayModeFromConfig(ConfigDef configDef) {
        if (configDef == null) {
            return OpenEggbertDisplayMode.WINDOW;
        }
        return setDisplayMode(fromConfigDef(configDef));
    }

    public static OpenEggbertDisplayMode fromConfigDef(ConfigDef configDef) {
        return configDef.isFullscreen() ? FULLSCREEN : WINDOW;
    }

    public static OpenEggbertDisplayMode setDisplayModeToFullscreen() {
        return setDisplayMode(FULLSCREEN);
    }

    public static OpenEggbertDisplayMode setDisplayModeToWindow() {
        return setDisplayMode(WINDOW);
    }
    private static DisplayMode originalDisplayMode = null;

    public static OpenEggbertDisplayMode setDisplayMode(OpenEggbertDisplayMode displayMode) {

        if (displayMode == OpenEggbertDisplayMode.FULLSCREEN) {
            Graphics.Monitor currentMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode displayModeFromLibGdx = Gdx.graphics.getDisplayMode(currentMonitor);
            if (originalDisplayMode == null) {
                originalDisplayMode = displayModeFromLibGdx;
            }

            DisplayMode foundVgaDisplayMode = null;
            ////
//            System.out.println("started loading all display modes");
            List<DisplayMode> vgaDisplayModes = Arrays
                    .asList(Gdx.graphics.getDisplayModes(currentMonitor))
                    .stream()
                    .filter(d -> d.width == 640 && d.height == 480)
                    .collect(Collectors.toList());


//fixme            
//            foundVgaDisplayMode = vgaDisplayModes.stream()
//                    .sorted((a, b) -> Integer.valueOf(a.refreshRate).compareTo(b.refreshRate)).findFirst().get();

//            System.out.println("ended loading all display modes");

//            System.out.println(foundVgaDisplayMode.refreshRate);
//            System.out.println(foundVgaDisplayMode.width);
//            System.out.println(foundVgaDisplayMode.height);

            ////
            if (!Gdx.graphics.setFullscreenMode(foundVgaDisplayMode == null ? displayModeFromLibGdx : foundVgaDisplayMode)) {
                Gdx.app.error("InitScreen", "Switching to fullscreen mode failed.");
                return null;
            }
            return FULLSCREEN;

        }
        if (displayMode == OpenEggbertDisplayMode.WINDOW) {
            setToOriginalDisplayMode();
            Gdx.graphics.setWindowedMode(640, 480);
            return WINDOW;
        }
        throw new OpenEggbertException("Unsupported DisplayMode: " + displayMode);

    }

    public static void setToOriginalDisplayMode() {
        if (originalDisplayMode == null) {
            //nothing to do
            return;
        }
        if (!Gdx.graphics.setFullscreenMode(originalDisplayMode)) {
            Gdx.app.error("OpenEggbertDisplayMode", "Switching to original display mode failed.");
            return;
        }

    }

    public OpenEggbertDisplayMode flip() {
        if (this == OpenEggbertDisplayMode.FULLSCREEN) {
            return OpenEggbertDisplayMode.WINDOW;

        }
        if (this == OpenEggbertDisplayMode.WINDOW) {
            return FULLSCREEN;
        }
        throw new OpenEggbertException("Unsupported DisplayMode: " + this);
    }
}
