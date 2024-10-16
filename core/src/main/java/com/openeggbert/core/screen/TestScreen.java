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
package com.openeggbert.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.core.main.OpenEggbertGame;
import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.api.graphics.BitmapFont;
import java.util.function.Function;

/**
 *
 * @author robertvokac
 */
public class TestScreen extends OpenEggbertScreen {

    public TestScreen(OpenEggbertGame openEggbertGame) {
        super(openEggbertGame);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameSpaceListScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void renderOpenEggbertScreen(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        game.getBatch().begin();
        Function<String, String> removeCurrentDir = i -> i == null ? null : i.replace(game.getAbsolutePathOfRootDirectory()+ "/", "");
        if (Pixel.app().getPlatform().isDesktop() && game.getGameSpace() != null) {
            BitmapFont font;
            font = game.getFont();
            int x = 140;
            font.draw(game.getBatch(), "getFeatureLevel=" + game.getGameSpace().getFeatureLevel(), 40, x);
            x += 25;
            font.draw(game.getBatch(), "getDataDirectory=" + removeCurrentDir.apply(game.getGameSpace().getDataDirectory()), 40, x);
            x += 25;
            font.draw(game.getBatch(), "getImage08Directory=" + removeCurrentDir.apply(game.getGameSpace().getImage08Directory()), 40, x);
            x += 25;
            font.draw(game.getBatch(), "getImage16Directory=" + removeCurrentDir.apply(game.getGameSpace().getImage16Directory()), 40, x);
            x += 25;
            font.draw(game.getBatch(), "getImage24Directory=" + removeCurrentDir.apply(game.getGameSpace().getImage24Directory()), 40, x);
            x += 25;
            font.draw(game.getBatch(), "getImage24x2Directory=" + removeCurrentDir.apply(game.getGameSpace().getImage24x2Directory()), 40, x);
            x += 25;
            font.draw(game.getBatch(), "getSoundDirectory=" + removeCurrentDir.apply(game.getGameSpace().getSoundDirectory()), 40, x);
        }

        if (game.getAbsolutePathOfRootDirectory()!= null) {
            BitmapFont font;
            font = game.getFont();
            font.draw(game.getBatch(), game.getAbsolutePathOfRootDirectory(), 40, 340);
        }
        batch.draw(game.getImage(), 40, 400);
        game.getBatch().end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
