package com.openeggbert.core.main;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
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
import com.pixelgamelibrary.api.storage.FileHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;

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
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private ObjectMap<String, Texture> imageTextures = new ObjectMap<>();
    private List<Mod> embeddedMods = new ArrayList<>();
    private int heightInPixels = 480;
    private int widthInPixels = 640;
    private Camera camera;
    private Viewport viewport;
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
//         viewport = new FitViewport(640,480);
//         viewport.apply();
        //camera = new OrthographicCamera();

        //.setToOrtho(false,640,480);
        System.out.println("Searching mods");

//        for(FileHandle f:Gdx.files.internal(".").list()) {
//            System.out.println("assets contains also: " + f.name());
//        }
        com.pixelgamelibrary.api.storage.FileHandle embeddedModsDirectory = Pixel.files().assets().file("/embedded_mods");
        System.out.println("embeddedModsDirectory.exists=" + embeddedModsDirectory.exists());
        System.out.println("embeddedModsDirectory.list().size()=" + embeddedModsDirectory.list().size());
        embeddedModsDirectory.list().forEach(e -> System.out.println(e.path()));

        Pixel.files().assets().list().forEach(e -> System.out.println(e));

        for (FileHandle embeddedModGroup : embeddedModsDirectory.list()) {
            if (embeddedModGroup.name().equals("README.md")) {
                continue;
            }
            System.out.println("Found group " + embeddedModGroup.name());
            for (FileHandle embeddedMod : embeddedModGroup.list()) {
                System.out.println("Found mod " + embeddedMod.name());

                FileHandle modXml = null;
                for (FileHandle file : embeddedMod.list()) {
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
        batch = new SpriteBatch();
        //batch.setProjectionMatrix(viewport.getCamera().combined);
        image = new Texture("libgdx.png");
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        System.out.println("Going to set screen");
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

    public void loadImageTexture(com.badlogic.gdx.files.FileHandle fileHandle) {
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
        return embeddedMods.stream().filter(m -> m.getIdentification().asString().equals(modIdentification.asString())).findFirst().get();
    }

}
