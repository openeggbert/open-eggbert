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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.main.OpenEggbertGame;
import com.openeggbert.mods.Mod;
import com.openeggbert.mods.ModType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.ToString;

/**
 *
 * @author robertvokac
 */
public class GameSpaceListScreen extends AbstractOpenEggbertScreen {

    private int pageNumber = 1;
    private final int pageSize = 5;
    private final List<Mod> fullEmbeddedMods;
    @ToString
    class Four {
        float x,y,width, height;
    }
    Four[] fourArray = new Four[5];

    public GameSpaceListScreen(OpenEggbertGame openEggbertGame) {
        super(openEggbertGame);
        this.fullEmbeddedMods = openEggbertGame.getEmbeddedMods().stream().filter(m -> m.getModType() == ModType.FULL).collect(Collectors.toList());
        Gdx.app.log("fullEmbeddedMods: ", String.valueOf(fullEmbeddedMods.size()));
        Gdx.app.log("openEggbertGame.getEmbeddedMods(): ", String.valueOf(openEggbertGame.getEmbeddedMods().size()));
        
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            game.setHeightInPixels(Gdx.app.getGraphics().getHeight());
            game.setWidthInPixels(Gdx.app.getGraphics().getWidth());
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new TestScreen(game));
                }
                return true;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                Gdx.app.log("touchDown: ", "x=" + x + " " + "y=" + y);
                if (x <= game.getWidthInPixels() / 3f && y >= (game.getHeightInPixels() * 0.92f) && pageNumber > 1) {
                    pageNumber--;
                }
                if (x >= game.getWidthInPixels() * 2f / 3f && y >= (game.getHeightInPixels() * 0.92f) && (pageNumber * pageSize) < fullEmbeddedMods.size()) {
                    pageNumber++;
                }
                for(int i = 0;i<5;i++) {
                    System.out.println(fourArray[i].toString());
                }
                for(int i = 0;i<5;i++) {
                if(
                        x > fourArray[i].x && x < (fourArray[i].x + fourArray[i].width)
                        &&
                        y > fourArray[i].y && y < (fourArray[i].y + fourArray[i].height)
                        ) {
                    System.out.println("button " + i);
                }
                }
                return true;
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 0.6f, 0.5f);
        batch.begin();

        BitmapFont font;
        font = new BitmapFont();
        font.getData().setScale(4.0f);
        font.setColor(Color.BLACK);
        int x = (int) (game.getWidthInPixels() * 0.1875f);
        int y = (int) (game.getHeightInPixels() * 0.9f);
        font.draw(batch, "Open Eggbert", x, y);
        List<Mod> modsForPage = fullEmbeddedMods.stream().skip(pageSize * (pageNumber - 1)).limit(5).collect(Collectors.toList());

        y = (int) (game.getHeightInPixels() * 0.75f);
        font.getData().setScale(2.0f);
        List<Integer> yS = new ArrayList<>();
        for (int i = 0; i < modsForPage.size(); i++) {
            yS.add(y);

            y = y - 60;
        }

        float margin = 0.0625f * game.getWidthInPixels();
        batch.end();

        final boolean isLastPage = !(pageNumber * pageSize < fullEmbeddedMods.size());

        int buttonHeight = (int) (game.getHeightInPixels() * 0.1f);
        final ShapeRenderer shapeRenderer = game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 0.8f, 0.5f);
        int z = 0;
        for (int e : yS) {
            Four four = new Four();
            four.x = margin;
            four.y = e-margin;
            four.width = game.getWidthInPixels() * 0.9f;
            four.height = buttonHeight;
            fourArray[z] = four;
            shapeRenderer.rect(four.x, four.y, four.width, four.height);
            z++;
        }
        if (pageNumber > 1) {
            shapeRenderer.rect(margin, margin / 4f, game.getWidthInPixels() * 0.3f, buttonHeight);
        }
        if (!isLastPage) {
            shapeRenderer.rect(game.getWidthInPixels() * 0.66f, margin / 4f, game.getWidthInPixels() * 0.3f, buttonHeight);
        }
        shapeRenderer.end();
        batch.begin();
        font.setColor(0f, 0f, 1f, 1f);

        for (int i = 0; i < modsForPage.size(); i++) {
            Mod mod = modsForPage.get(i);
            String name = mod.getName() == null || mod.getName().isEmpty() ? mod.getIdentification().asString() : mod.getName();
            name = "#" + ((pageNumber - 1) * pageSize + (i + 1)) + " " + name;
            font.draw(batch, name, 40, yS.get(i));
        }
        font.getData().setScale(1.5f);
        font.setColor(0f, 0f, 1f, 1f);

        float lastRowHeight = game.getHeightInPixels() * 0.08f;
        if (pageNumber > 1) {
            font.draw(batch, "Previous page", margin, lastRowHeight);
        }

        if (!isLastPage) {
            font.draw(batch, "Next page", game.getWidthInPixels() * 0.765625f, lastRowHeight);
        }
        font.setColor(0f, 0f, 0f, 1f);
        int pageCount = fullEmbeddedMods.size() / 5;
        if (fullEmbeddedMods.size() > pageCount * pageSize) {
            pageCount++;
        }
        font.draw(batch, "Page " + pageNumber + " from " + pageCount, game.getWidthInPixels() / 2 - 80, lastRowHeight);

        batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
