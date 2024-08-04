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
package com.openeggbert;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.openeggbert.entity.common.GameSpace;
import java.util.function.Function;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class OpenEggbertMainClass extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture image;
    private GameSpace gameSpace = null;
    private String currentDirectory;

    public OpenEggbertMainClass() {

    }

    public OpenEggbertMainClass(GameSpace gameSpace, String currentDirectory) {
        this.gameSpace = gameSpace;
        this.currentDirectory = currentDirectory;
    }

    public OpenEggbertMainClass(String currentDirectory) {
        this.gameSpace = null;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        Function<String, String> removeCurrentDir = i -> i == null ? null : i.replace(currentDirectory + "/", "");
        if (Gdx.app.getType() == Application.ApplicationType.Desktop && gameSpace != null) {
            BitmapFont font;
            font = new BitmapFont();
            int x = 140;
            font.draw(batch, "getFeatureLevel=" + gameSpace.getFeatureLevel(), 40, x);x+=25;
            font.draw(batch, "getDataDirectory=" + removeCurrentDir.apply(gameSpace.getDataDirectory()), 40, x);x+=25;
            font.draw(batch, "getImage08Directory=" + removeCurrentDir.apply(gameSpace.getImage08Directory()), 40, x);x+=25;
            font.draw(batch, "getImage16Directory=" + removeCurrentDir.apply(gameSpace.getImage16Directory()), 40, x);x+=25;
            font.draw(batch, "getImage24Directory=" + removeCurrentDir.apply(gameSpace.getImage24Directory()), 40, x);x+=25;
            font.draw(batch, "getImage24x2Directory=" + removeCurrentDir.apply(gameSpace.getImage24x2Directory()), 40, x);x+=25;
            font.draw(batch, "getSoundDirectory=" + removeCurrentDir.apply(gameSpace.getSoundDirectory()), 40, x);x+=25;
        }

        if (currentDirectory != null) {
            BitmapFont font;
            font = new BitmapFont();
            font.draw(batch, currentDirectory, 40, 340);
        }
        //batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
