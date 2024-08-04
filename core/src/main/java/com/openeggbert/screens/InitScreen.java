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
package com.openeggbert.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.entity.common.OpenEggbertScreenType;
import com.openeggbert.main.OpenEggbertGame;
import java.util.Optional;

/**
 *
 * @author robertvokac
 */
public class InitScreen extends AbstractOpenEggbertScreen {

    public InitScreen(OpenEggbertGame openEggbertGame) {
        super(openEggbertGame);
        
    }

    protected final Optional<OpenEggbertScreenType> getScreenType() {
        return Optional.of(OpenEggbertScreenType.INIT);
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
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        batch.begin();
        if (getBackgroundTexture().isPresent()) {
            batch.draw(getBackgroundTexture().get(), 0, 0);
        }

        batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
