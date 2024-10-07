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
import com.badlogic.gdx.InputAdapter;
import com.pixelgamelibrary.api.graphics.Color;

import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.core.main.OpenEggbertGame;
import com.pixelgamelibrary.api.graphics.BitmapFont;
import java.util.Optional;

/**
 *
 * @author robertvokac
 */
public class SubHubScreen extends AbstractGameScreen {

    public SubHubScreen(OpenEggbertGame openEggbertGame) {
        super(openEggbertGame);

    }

    protected final Optional<ScreenType> getScreenType() {
        return Optional.of(ScreenType.SUB_HUB);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                game.setScreen(new InitScreen(game));
                return true;
            }

            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new InitScreen(game));
                return true;
            }
        });
    }

    @Override
    public void renderOpenEggbertScreen(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        batch.begin();
        drawBackgroundIfAvailable();
        
        BitmapFont font;
        font = game.getFont();
        font.setScale(2.0f);
        font.setColor(Color.RED);
        
        font.draw(batch, "Sorry, demo is not yet implemented", 40, 400);
        font.draw(batch, "Please, press any key", 40, 300);
        font.draw(batch, "to return to the main screen", 40, 250);
        

        batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
