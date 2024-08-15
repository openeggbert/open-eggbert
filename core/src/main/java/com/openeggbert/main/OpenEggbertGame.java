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
package com.openeggbert.main;

import com.openeggbert.utils.AssetsTxt;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.openeggbert.entity.common.ConfigDef;
import com.openeggbert.entity.common.GameSpace;
import com.openeggbert.mods.Mod;
import com.openeggbert.mods.ModIdentification;
import com.openeggbert.screens.GameSpaceListScreen;
import com.openeggbert.screens.InitScreen;
import com.openeggbert.gdx.storage.Storage;
import com.openeggbert.gdx.storage.StorageImplementationLoader;
import com.openeggbert.utils.OpenEggbertDisplayMode;
import com.openeggbert.utils.OpenEggbertUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
@Data
public class OpenEggbertGame extends Game {

    private Texture image;
    private GameSpace gameSpace = null;
    private String currentDirectory;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private ObjectMap<String, Texture> imageTextures = new ObjectMap<>();
    private List<Mod> embeddedMods = new ArrayList<>();
    private int heightInPixels = 480;
    private int widthInPixels = 640;
    private Camera camera;
    private Viewport viewport;
    private AssetsTxt assets;
    private ConfigDef configDef;
    private OpenEggbertDisplayMode openEggbertDisplayMode = OpenEggbertDisplayMode.WINDOW;
    private Storage storage;

    public OpenEggbertGame() {
        this(null, null);
    }

    public OpenEggbertGame(String currentDirectory) {
        this(null, currentDirectory);
    }

    public OpenEggbertGame(GameSpace gameSpace, String currentDirectory) {
        this.gameSpace = gameSpace;
        this.currentDirectory = currentDirectory;

    }
    
    public Storage getStorage() {
        if(storage == null) {
            this.storage = StorageImplementationLoader.getStorage();
        }
        return storage;
    }

    @Override
    public void create() {
//         viewport = new FitViewport(640,480);
//         viewport.apply();
        //camera = new OrthographicCamera();
        
                //.setToOrtho(false,640,480);

        assets = new AssetsTxt(Gdx.files.internal("assets.txt").readString());
        System.out.println("Searching mods");

        for(FileHandle f:Gdx.files.internal(".").list()) {
            System.out.println("assets contains also: " + f.name());
        }
        FileHandle embeddedModsDirectory = Gdx.files.internal("embedded_mods");
        System.out.println("embeddedModsDirectory.exists=" + embeddedModsDirectory.exists());
        System.out.println("embeddedModsDirectory.list().length=" + embeddedModsDirectory.list().length);
        for (FileHandle embeddedModGroup : assets.list(embeddedModsDirectory)) {
            if(embeddedModGroup.name().equals("README.md"))continue;
            System.out.println("Found group " + embeddedModGroup.name());
            for (FileHandle embeddedMod : assets.list(embeddedModGroup)) {
                System.out.println("Found mod " + embeddedMod.name());
                
                FileHandle modXml = null;
                for(FileHandle file: assets.list(embeddedMod)) {
                    if(file.name().equals("mod.xml")) {
                        modXml = file;
                    }
                }
                
                if (modXml == null) {
                    continue;
                }
                System.out.println("Found mod: " + embeddedMod.name());
                
                Mod mod = new Mod(modXml.readString());
                embeddedMods.add(mod);
                System.out.println("embeddedMods.size(): " + embeddedMods.size());
//                for (int i = 0; i < 42; i++) embeddedMods.add(mod);//for testing purposes
            }

        }
        ////
        batch = new SpriteBatch();
        //batch.setProjectionMatrix(viewport.getCamera().combined);
        image = new Texture("libgdx.png");
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(gameSpace == null ? new GameSpaceListScreen(this) : new InitScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();

        shapeRenderer.dispose();
        font.dispose();
        for (String key : imageTextures.keys()) {
            imageTextures.get(key).dispose();
        }
    }

    public void loadImageTexture(FileHandle fileHandle) {
        Texture texture = new Texture(fileHandle);
        imageTextures.put(OpenEggbertUtils.getFileNameWithoutExtension(fileHandle.name().toUpperCase()), texture);
    }

    public boolean existsImageTexture(String key) {
        return imageTextures.containsKey(key);
    }

    public void disposeImageTexture(String key) {
        if (imageTextures.containsKey(key)) {
            imageTextures.get(key).dispose();
            imageTextures.remove(key);
        }
    }

    public void disposeImageTextures() {
        for (String key : imageTextures.keys()) {
            imageTextures.get(key).dispose();
            imageTextures.remove(key);
        }
    }

    public Optional<Texture> getImageTexture(String key) {
        if (imageTextures.containsKey(key)) {
            return Optional.of(imageTextures.get(key));
        } else {
            return Optional.empty();
        }
    }
    public Mod loadMod(ModIdentification modIdentification) {
        return embeddedMods.stream().filter(m->m.getIdentification().asString().equals(modIdentification.asString())).findFirst().get();
    }

}
