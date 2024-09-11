///////////////////////////////////////////////////////////////////////////////////////////////
// Pixel: Game library.
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
package com.pixelgamelibrary.backend.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.pixelgamelibrary.WindowMode;
import com.pixelgamelibrary.PixelException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.pixelgamelibrary.api.GraphicsI;

/**
 *
 * @author robertvokac
 */
public class GraphicsLibGDXImpl implements GraphicsI {

    
    @Override
    public String setDisplayMode(boolean fullscreen, boolean window) {

        if (fullscreen) {
            Graphics.Monitor currentMonitor = Gdx.graphics.getMonitor();
            Graphics.DisplayMode displayModeFromLibGdx = Gdx.graphics.getDisplayMode(currentMonitor);
            if (originalDisplayMode == null) {
                originalDisplayMode = displayModeFromLibGdx;
            }

            Graphics.DisplayMode foundVgaDisplayMode = null;
            ////
//            System.out.println("started loading all display modes");
            List<Graphics.DisplayMode> vgaDisplayModes = Arrays
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
            return WindowMode.FULLSCREEN;

        }
        if (window) {
            setToOriginalDisplayMode();
            Gdx.graphics.setWindowedMode(640, 480);
            return WindowMode.WINDOW;
        }
        throw new PixelException("Unsupported DisplayMode: fullscreen=" + fullscreen + " window=" + window);

    }
    @Override
    public boolean setToOriginalDisplayMode() {
        if (originalDisplayMode == null) {
            //nothing to do
            return true;
        }
        if (!Gdx.graphics.setFullscreenMode(originalDisplayMode)) {
            Gdx.app.error("DefinitiveFrameworkLibGdxImpl", "Switching to original display mode failed.");
            return false;
        } else {
            return true;
        }

    }

    private Graphics.DisplayMode originalDisplayMode = null;

}
