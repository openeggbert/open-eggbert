package com.openeggbert.core.main;

import com.openeggbert.core.configuration.ConfigDef;
import com.openeggbert.core.configuration.OpenEggbertDisplayMode;
import com.openeggbert.core.gamespace.GameSpace;
import com.openeggbert.core.mod.Mod;
import com.openeggbert.core.mod.ModIdentification;
import com.openeggbert.core.screen.GameSpaceListScreen;
import com.openeggbert.core.screen.InitScreen;
import com.openeggbert.core.utils.OpenEggbertUtils;
import com.pixelgamelibrary.api.game.GameAdapter;
import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.api.graphics.BitmapFont;
import com.pixelgamelibrary.api.graphics.SpriteBatch;
import com.pixelgamelibrary.api.graphics.Texture;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import com.pixelgamelibrary.api.files.File;
import com.pixelgamelibrary.api.graphics.ShapeRenderer;
import com.pixelgamelibrary.api.utils.collections.Map;
import lombok.Getter;

/**
 *
 * @author robertvokac
 */
@Data
public class OpenEggbertGame extends GameAdapter {

    private Texture image;
    private GameSpace gameSpace = null;
    private String absolutePathOfRootDirectory;

    private SpriteBatch batch;
    @Getter
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Map<String, Texture> imageTextures = Pixel.utils().collections().objectMap();
    private List<Mod> embeddedMods = Pixel.utils().collections().list();
    private int heightInPixels = 480;
    private int widthInPixels = 640;
    
    private ConfigDef configDef;
    private OpenEggbertDisplayMode openEggbertDisplayMode = OpenEggbertDisplayMode.WINDOW;

    public OpenEggbertGame() {
        this(null, null);
    }

    public OpenEggbertGame(String absolutePathOfRootDirectoryIn) {
        this(null, absolutePathOfRootDirectoryIn);
    }

    public OpenEggbertGame(GameSpace gameSpace, String absolutePathOfRootDirectoryIn) {
        this.gameSpace = gameSpace;
        this.absolutePathOfRootDirectory = absolutePathOfRootDirectoryIn;
        Pixel.app().setGame(this);

    }

    @Override
    public void create() {
        System.out.println("Searching mods");

//        for(FileHandle f:Gdx.files.internal(".").list()) {
//            System.out.println("assets contains also: " + f.name());
//        }
        com.pixelgamelibrary.api.files.File embeddedModsDirectory = Pixel.files().assets("/embedded_mods");
        System.out.println("embeddedModsDirectory.exists=" + embeddedModsDirectory.exists());
        System.out.println("embeddedModsDirectory.list().size()=" + embeddedModsDirectory.list().size());
        embeddedModsDirectory.list().forEach(e -> System.out.println(e.path()));

        Pixel.files().assetsFileSystem().list().forEach(e -> System.out.println(e));

        for (File embeddedModGroup : embeddedModsDirectory.list()) {
            if (embeddedModGroup.name().equals("README.md")) {
                continue;
            }
            System.out.println("Found group " + embeddedModGroup.name());
            for (File embeddedMod : embeddedModGroup.list()) {
                if (embeddedMod.name().equals("README.md")) {
                    continue;
                }
                System.out.println("Found mod " + embeddedMod.name());

                File modXml = null;
                for (File file : embeddedMod.list()) {
                    if (file.name().equals("mod.xml")) {
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
        batch = Pixel.graphics().newSpriteBatch();
        //batch.setProjectionMatrix(viewport.getCamera().combined);
        image = Pixel.graphics().newTexture("libgdx.png");
        shapeRenderer = batch.drawShape();
        font = Pixel.graphics().newBitmapFont(
        Pixel.files().assets("com/badlogic/gdx/utils/lsans-15.fnt"), Pixel.files().assets("com/badlogic/gdx/utils/lsans-15.png"),
			false
        );
        
        System.out.println("Going to set screen");
        setScreen(gameSpace == null ? new GameSpaceListScreen(this) : new InitScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();

        shapeRenderer.dispose();
        font.dispose();
        for (String key : imageTextures.keySet()) {
            imageTextures.get(key).dispose();
        }
    }

    public void loadImageTexture(File fileHandle) {
        Texture texture = Pixel.graphics().newTexture(fileHandle);
        if(!fileHandle.exists()) {
            throw new OpenEggbertException("File does not exist: " + fileHandle.path());
        }
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
        for (String key : imageTextures.keySet()) {
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
        return embeddedMods.stream().filter(m -> m.getIdentification().asString().equals(modIdentification.asString())).findFirst().get();
    }

}
