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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.core.configuration.ConfigDef;
import com.openeggbert.core.main.OpenEggbertGame;
import com.openeggbert.core.configuration.OpenEggbertDisplayMode;
import java.util.Optional;

/**
 *
 * @author robertvokac
 */
public class InitScreen extends AbstractOpenEggbertScreen {

    private float timeSeconds = 0f;

    public InitScreen(OpenEggbertGame openEggbertGame) {
        super(openEggbertGame);
        System.out.println("game.getGameSpace().getDataDirectory() != null" + (game.getGameSpace().getDataDirectory() != null));
//        Pixel.app().log("###1");
//        Arrays.asList(Gdx.files.local(".").list()).forEach(e->Pixel.app().log(e.path()));
//        Pixel.app().log("###2");
        if (game.getGameSpace().getDataDirectory() != null) {
            FileHandle configDefFileHandle = null;
            String[] array = new String[]{"config.def", "Config.def", "CONFIG.DEF"};
            if (game.getGameSpace().isEmbeddedAssets()) {
//                for (String a : array) {
//                    configDefFileHandle = EmbeddedFileHandleFactory.create(game.getGameSpace().getDataDirectory() + "/" + a);
//                    if (configDefFileHandle.exists()) {
//                        break;
//                    } else {
//                        continue;
//                    }
//                }
            } else {
                for (String a : array) {
                    configDefFileHandle = Gdx.files.absolute(game.getGameSpace().getDataDirectory() + "/" + a);
                    if (configDefFileHandle.exists()) {
                        break;
                    } else {
                        continue;
                    }
                }

            }
            if (configDefFileHandle != null && configDefFileHandle.exists()) {
                game.setConfigDef(new ConfigDef(configDefFileHandle.readString()));
            }

        }
        if (game.getConfigDef() == null) {
            game.setConfigDef(new ConfigDef("CD-Rom=E:\\US\n"
                    + "FullScreen=0\n"
                    + "TrueColor=0\n"
                    + "MouseType=1\n"
                    + "SpeedRate=1\n"
                    + "Timer=150ms\n"
                    + "Language=U\n"
                    + "Benchmark=251674"));
            System.out.println("game.getConfigDef()=" + game.getConfigDef());
        }
        OpenEggbertDisplayMode openEggbertDisplayMode = OpenEggbertDisplayMode.setDisplayModeFromConfig(game.getConfigDef());
        game.setOpenEggbertDisplayMode(openEggbertDisplayMode);

    }

    protected final Optional<ScreenType> getScreenType() {
        return Optional.of(ScreenType.INIT);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//                timeSeconds = 0f;
                return false;
            }

            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
//                timeSeconds = 0f;
                return false;
            }

            public boolean touchDragged(int screenX, int screenY, int pointer) {
//                timeSeconds = 0f;
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                //timeSeconds = 0f;
                return false;
            }

            @Override
            public boolean keyDown(int keyCode) {
//                timeSeconds = 0f;

                if (keyCode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                }

                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameSpaceListScreen(game));
                }

                if (game.getConfigDef() != null && Gdx.app.getType() == Application.ApplicationType.Desktop && !game.getConfigDef().isStrictCompatibility() && keyCode == Input.Keys.F) {
                    OpenEggbertDisplayMode currentDisplayMode = game.getOpenEggbertDisplayMode();
                    OpenEggbertDisplayMode newDisplayMode = currentDisplayMode.flip();
                    boolean success = OpenEggbertDisplayMode.setDisplayMode(newDisplayMode) != null;
                    if (success) {
                        game.setOpenEggbertDisplayMode(newDisplayMode);
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void renderOpenEggbertScreen(float delta) {
        timeSeconds += Gdx.graphics.getRawDeltaTime();

        if (timeSeconds >= 50) {
            timeSeconds = 0;
            game.setScreen(new DemoScreen(game));
        }
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        batch.begin();
        drawBackgroundIfAvailable();

        batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
