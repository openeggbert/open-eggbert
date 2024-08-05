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
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.openeggbert.entity.common.OpenEggbertException;
import com.openeggbert.entity.common.OpenEggbertScreenType;
import com.openeggbert.main.OpenEggbertGame;
import java.util.Optional;

/**
 *
 * @author robertvokac
 */
public abstract class AbstractOpenEggbertScreen extends ScreenAdapter {

    protected OpenEggbertGame game;
    protected SpriteBatch batch;

    public AbstractOpenEggbertScreen(OpenEggbertGame openEggbertGame) {
        this.game = openEggbertGame;
        this.batch = openEggbertGame.getBatch();
        loadBackgroundTextureIfNeeded();
    }

    private final String getBackgroundFileName() {
        return getScreenType().isPresent() ? getScreenType().get().getFileName() : "";
    }

    protected Optional<OpenEggbertScreenType> getScreenType() {
        return Optional.empty();
    }

    protected Optional<Texture> getBackgroundTexture() {
        if (getBackgroundFileName().isEmpty()) {
            return Optional.empty();
        }
        return game.getImageTexture(getBackgroundFileName());
    }

    private void loadBackgroundTextureIfNeeded() {
        if (getBackgroundFileName().isEmpty()) {
            return;
        }
        String fileName = getBackgroundFileName();
        if (!game.existsImageTexture(fileName)) {
            String nameUpperCase = game.getGameSpace().getImage08Directory() + "/" + fileName;
            String nameLowerCase = game.getGameSpace().getImage08Directory() + "/" + fileName.toLowerCase();
            System.out.println("nameUpperCase=" + nameUpperCase);
            System.out.println("nameLowerCase=" + nameLowerCase);
            FileHandle fileHandleUpperCase = null;
            FileHandle fileHandleLowerCase = null;
            if (game.getGameSpace().isEmbeddedAssets()) {

                if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.WebGL) {
                    System.out.println("loading from internal");
                    fileHandleUpperCase = Gdx.files.internal(nameUpperCase);
                    fileHandleLowerCase = Gdx.files.internal(nameLowerCase);
                } else {

                    System.out.println("loading from classpath");
                    fileHandleUpperCase = Gdx.files.classpath(nameUpperCase);
                    fileHandleLowerCase = Gdx.files.classpath(nameLowerCase);
                    System.out.println("fileHandleUpperCase.exists()=" + fileHandleUpperCase.exists());
                    System.out.println("fileHandleLowerCase.exists()=" + fileHandleLowerCase.exists());

                }
            } else {
                System.out.println("loading from absolute");

                fileHandleUpperCase = Gdx.files.absolute(nameUpperCase);
                fileHandleLowerCase = Gdx.files.absolute(nameLowerCase);
            }

            if (fileHandleUpperCase.exists()) {
                game.loadImageTexture(fileHandleUpperCase);
            } else {
                if (fileHandleLowerCase.exists()) {
                    game.loadImageTexture(fileHandleLowerCase);    
                } else {
                    throw new OpenEggbertException("Could not load file: " + fileName);
                }
                
            }
        }
    }

}
