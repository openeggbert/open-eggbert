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

package com.openeggbert.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.openeggbert.core.configuration.ScreenResolution;
import com.openeggbert.core.main.OpenEggbertApplication;
import com.openeggbert.core.gamespace.GameSpace;
import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.backend.libgdx.PixelBackendLibGDX;
import java.util.Optional;
import com.pixelgamelibrary.api.game.Game;
import com.pixelgamelibrary.backend.libgdx.game.LibGdxGame;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Pixel.initBackend(new PixelBackendLibGDX());
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        Optional<GameSpace> gameSpace = DesktopUtils.tryToLoadGameSpace();
        String currentDirectory = DesktopUtils.getPathOfDirectoryWhereJarIsRunning();
        OpenEggbertApplication openEggbertApplication = new OpenEggbertApplication();
        
        Game game;
        if (gameSpace.isPresent()) {
            game = openEggbertApplication.createGame("gameSpace", gameSpace.get(), "currentDirectory", currentDirectory);
        } else {
            game = openEggbertApplication.createGame("currentDirectory", currentDirectory);
        }  
        
        LibGdxGame libGdxGame = new LibGdxGame(game);
        return new Lwjgl3Application(libGdxGame, getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Blupi");
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setWindowedMode(ScreenResolution.VGA.getWidth(), ScreenResolution.VGA.getHeight());
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}