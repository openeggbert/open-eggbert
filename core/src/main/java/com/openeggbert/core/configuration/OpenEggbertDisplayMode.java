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
package com.openeggbert.core.configuration;

import com.pixelgamelibrary.api.Pixel;
import com.openeggbert.core.main.OpenEggbertException;
import com.pixelgamelibrary.api.WindowMode;

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

    public static OpenEggbertDisplayMode find(boolean fullscreen, boolean window) {
        if (fullscreen && window) {
            throw new OpenEggbertException("Both arguments fullscreen and window are true.");
        }
        if (!fullscreen && !window) {
            throw new OpenEggbertException("Both arguments fullscreen and window are false.");
        }
        if (fullscreen) {
            return FULLSCREEN;
        } else {
            return WINDOW;
        }
    }

    public static OpenEggbertDisplayMode setDisplayMode(OpenEggbertDisplayMode displayMode) {
        WindowMode result = Pixel.graphics().setDisplayMode(displayMode == FULLSCREEN, displayMode == WINDOW);

        return result == null ? null : OpenEggbertDisplayMode.valueOf(result.name());
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
