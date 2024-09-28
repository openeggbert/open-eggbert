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
package com.openeggbert.core.main;

import com.openeggbert.core.gamespace.GameSpace;
import com.pixelgamelibrary.api.PixelApplication;
import java.util.Map;
import com.pixelgamelibrary.api.game.Game;

/**
 * {@link com.pixelgamelibrary.api.PixelApplication} implementation shared by all
 * platforms.
 */
public class OpenEggbertApplication extends PixelApplication {

    private static final String GAME_SPACE = "gameSpace";
    private static final String ABSOLUTE_PATH_OF_ROOT_DIRECTORY_IN = "absolutePathOfRootDirectoryIn";
    
    @Override
    public Game createGameViaMap(Map<String, Object> objects) {

        String absolutePathOfRootDirectoryIn = null;
        GameSpace gameSpace = null;
        if (objects.containsKey(ABSOLUTE_PATH_OF_ROOT_DIRECTORY_IN)) {
            absolutePathOfRootDirectoryIn = (String) objects.get(ABSOLUTE_PATH_OF_ROOT_DIRECTORY_IN);
        }
        if (objects.containsKey(GAME_SPACE)) {
            gameSpace = (GameSpace) objects.get(GAME_SPACE);
        }

        if (absolutePathOfRootDirectoryIn != null) {

            return gameSpace == null ? new OpenEggbertGame(absolutePathOfRootDirectoryIn) : new OpenEggbertGame(gameSpace, absolutePathOfRootDirectoryIn); 
        } else {
            return new OpenEggbertGame();
        }

    }

}
