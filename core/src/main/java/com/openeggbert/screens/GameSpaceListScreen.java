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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.entity.common.GameSpace;
import com.openeggbert.main.OpenEggbertGame;
import com.openeggbert.mods.Mod;
import com.openeggbert.mods.ModType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author robertvokac
 */
public class GameSpaceListScreen extends AbstractOpenEggbertScreen {

    private int pageNumber = 1;
    private final int pageSize = 5;
    private final List<Mod> fullEmbeddedMods;
    private float timeSeconds = 0f;

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    class Rectangle {

        float x, y, width, height;
    }
    private Rectangle[] buttons = new Rectangle[5];
    private Rectangle previousPageButton = new Rectangle();
    private Rectangle nextPageButton = new Rectangle();

    public GameSpaceListScreen(OpenEggbertGame openEggbertGame) {
        super(openEggbertGame);
        this.fullEmbeddedMods = openEggbertGame.getEmbeddedMods().stream().filter(m -> m.getModType() == ModType.FULL).collect(Collectors.toList());
        

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            game.setHeightInPixels(Gdx.app.getGraphics().getHeight());
            game.setWidthInPixels(Gdx.app.getGraphics().getWidth());
        }
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putString("test", "abc");
        prefs.flush();
        game.getStorage().mkdir("modes");
        game.getStorage().mkdir("game_spaces");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                timeSeconds = 0f;
                return false;
            }

            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                timeSeconds = 0f;
                return false;
            }

            public boolean touchDragged(int screenX, int screenY, int pointer) {
                timeSeconds = 0f;
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                timeSeconds = 0f;
                return false;
            }

            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new TestScreen(game));
                }
                if (keyCode == Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                }

                return true;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                timeSeconds = 0f;
                Gdx.app.log("touchDown: ", "x=" + x + " " + "y=" + y);
                if (x <= Gdx.graphics.getWidth() / 3f && y >= (Gdx.graphics.getHeight() * 0.92f) && pageNumber > 1) {
                    pageNumber--;
                }
                if (x >= Gdx.graphics.getWidth() * 2f / 3f && y >= (Gdx.graphics.getHeight() * 0.92f) && (pageNumber * pageSize) < fullEmbeddedMods.size()) {
                    pageNumber++;
                }
                for (int i = 0; i < 5; i++) {
                    if (buttons[i] == null) {
                        continue;
                    }
                    System.out.println(buttons[i].toString());
                }
                y = Gdx.graphics.getHeight() - y;
                
                for (int i = 0; i < 5; i++) {
                    if (buttons[i] == null) {
                        break;
                    }
                    if (x > buttons[i].x && x < (buttons[i].x + buttons[i].width)
                            && y > buttons[i].y && y < (buttons[i].y + buttons[i].height)) {
                        
                        activateButton(i);

                    }
                }
                return true;
            }

        });

    }

    private void activateButton(int i) {
        Mod mod = fullEmbeddedMods.get(pageSize * (pageNumber - 1) + i);
        GameSpace gameSpace = new GameSpace();
        gameSpace.setFeatureLevel(mod.getFeatureLevel());
        mod.getImportedMods().stream().map(m -> game.loadMod(m))
                .filter(m -> m.getModType().name().startsWith("IMAGE"))
                .forEach(m -> {
                    if (m.getModType() == ModType.IMAGE08) {
                        gameSpace.setImage08Directory("embedded_mods/" + m.getIdentification().getGroupId() + "/" + m.getIdentification().getModId());
                    }
                    if (m.getModType() == ModType.IMAGE16) {
                        gameSpace.setImage16Directory("embedded_mods/" + m.getIdentification().getGroupId() + "/" + m.getIdentification().getModId());
                    }
                    if (m.getModType() == ModType.IMAGE24) {
                        gameSpace.setImage24Directory("embedded_mods/" + m.getIdentification().getGroupId() + "/" + m.getIdentification().getModId());
                    }
                    if (m.getModType() == ModType.IMAGE24X2) {
                        gameSpace.setImage24x2Directory("embedded_mods/" + m.getIdentification().getGroupId() + "/" + m.getIdentification().getModId());
                    }
                });
        gameSpace.setEmbeddedAssets(true);
        game.setGameSpace(gameSpace);
        game.setScreen(new InitScreen(game));

    }

    @Override
    public void renderOpenEggbertScreen(float delta) {
        Gdx.app.log(getClass().getName(), game.getStorage().debug());

        timeSeconds += Gdx.graphics.getRawDeltaTime();
        if (timeSeconds > 60) {
            activateButton(0);
        }

        ScreenUtils.clear(1f, 1f, 0.6f, 0.5f);
        int buttonHeight = (int) (game.getHeightInPixels() * 0.1f);

        batch.begin();

        BitmapFont font;
        font = new BitmapFont();
        font.getData().setScale(4.0f);
        font.setColor(Color.BLACK);
        int x = (int) (game.getWidthInPixels() * 0.1875f);
        int y = (int) (game.getHeightInPixels() * 0.95f);
        font.draw(batch, "Open Eggbert", x, y);
        List<Mod> modsForPage = fullEmbeddedMods.stream().skip(pageSize * (pageNumber - 1)).limit(5).collect(Collectors.toList());

        float margin = 0.05f * game.getWidthInPixels();

        y = (int) (game.getHeightInPixels() * 0.7f);
        font.getData().setScale(2.0f);
        final float spaceBetweenLargeButtons = game.getHeightInPixels() * 0.06f;
        for (int i = 0; i < modsForPage.size(); i++) {
            buttons[i] = new Rectangle(margin, y, game.getWidthInPixels() * 0.9f, margin * 1.5f);

            y = (int) (y - spaceBetweenLargeButtons - margin);
        }

        batch.end();

        final boolean isLastPage = !(pageNumber * pageSize < fullEmbeddedMods.size());

        final ShapeRenderer shapeRenderer = game.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1f, 1f, 0.8f, 0.5f);
        int q = 0;
        for (Rectangle r : buttons) {
            q++;
            if (q > modsForPage.size()) {
                break;
            }
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        if (pageNumber
                > 1) {
            shapeRenderer.rect(margin, margin / 4f, game.getWidthInPixels() * 0.3f, buttonHeight);
        }
        if (!isLastPage) {
            shapeRenderer.rect(game.getWidthInPixels() * 0.66f, margin / 4f, game.getWidthInPixels() * 0.3f, buttonHeight);
        }

        shapeRenderer.end();

        batch.begin();

        font.setColor(
                0f, 0f, 1f, 1f);

        for (int i = 0;
                i < modsForPage.size();
                i++) {
            Mod mod = modsForPage.get(i);
            String name = mod.getName() == null || mod.getName().isEmpty() ? mod.getIdentification().asString() : mod.getName();
            name = "#" + ((pageNumber - 1) * pageSize + (i + 1)) + " " + name;
            font.draw(batch, name, margin * 1.5f, buttons[i].y + 0.8f * buttons[i].height);
        }

        font.getData()
                .setScale(1.5f);
        font.setColor(
                0f, 0f, 1f, 1f);

        float lastRowHeight = game.getHeightInPixels() * 0.08f;
        if (pageNumber
                > 1) {
            font.draw(batch, "Previous page", margin, lastRowHeight);
        }

        if (!isLastPage) {
            font.draw(batch, "Next page", game.getWidthInPixels() * 0.765625f, lastRowHeight);
        }

        font.setColor(
                0f, 0f, 0f, 1f);
        int pageCount = fullEmbeddedMods.size() / 5;

        if (fullEmbeddedMods.size()
                > pageCount * pageSize) {
            pageCount++;
        }

        font.draw(batch,
                "Page " + pageNumber + " from " + pageCount, game.getWidthInPixels() / 2 - 80, lastRowHeight);

        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
