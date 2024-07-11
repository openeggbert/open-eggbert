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
package com.openeggbert.core;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.openeggbert.entity.common.EntityType;
import java.io.File;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * @author <a href="mailto:mail@robertvokac.com">Robert Vokac</a>
 * @since 0.0.0
 */
public class OpenEggbertApp extends GameApplication {

    private Entity player;
    private Entity coin;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(640);
        settings.setHeight(480);
        settings.setTitle("Open Eggbert");
        settings.setVersion("0.0.0-SNAPSHOT");
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }
    private static int r = 0;

    @Override
    protected void initInput() {
        int width = Companion.getAppWidth();
        int height = Companion.getAppHeight();
        PropertyMap state = FXGL.getWorldProperties();
//        if(state.keys().contains("x")) {
//        int x = state.getInt("x");
//        int y = state.getInt("y");

        onKey(KeyCode.RIGHT, () -> {
            checkXAndMovePlayerIfNeeded();
            player.translateX(move); // move right 5 pixels
            inc("pixelsMoved", +move);
            inc("x", +move);
            r++;
            System.out.println("Pressed RIGHT " + r);
            lastPress = System.nanoTime();
            if (lastStart == 0) {
                lastStart = lastPress;
            } else {
                if (move < 20) {
                    move = move + 4;
                }
                if ((lastPress - lastStart) > 5000000000l) {
//                    move = 5;
                    lastStart = 0;
                }
            }

        });

        onKey(KeyCode.LEFT, () -> {
            checkXAndMovePlayerIfNeeded();
            player.translateX(-move); // move left 5 pixels
            inc("pixelsMoved", -move);
            inc("x", -move);
            lastPress = System.nanoTime();
            if (lastStart == 0) {
                lastStart = lastPress;
            } else {
                if (move < 20) {
                    move++;
                }
                if ((lastPress - lastStart) > 5000000000l) {
//                    move = 5;
                    lastStart = 0;
                }
            }
        });

        onKey(KeyCode.UP, () -> {
            checkYAndMovePlayerIfNeeded();
            player.translateY(-move); // move up 5 pixels
            inc("pixelsMoved", +move);
            inc("y", -move);
            lastPress = System.nanoTime();
            if (lastStart == 0) {
                lastStart = lastPress;
            } else {
                if (move < 20) {
                    move++;
                }
                if ((lastPress - lastStart) > 5000000000l) {
//                    move = 5;
                    lastStart = 0;
                }
            }
        });

        onKey(KeyCode.DOWN, () -> {
            checkYAndMovePlayerIfNeeded();
            player.translateY(move); // move down 5 pixels
            inc("pixelsMoved", -move);
            inc("y", +move);
            lastPress = System.nanoTime();
            if (lastStart == 0) {
                lastStart = lastPress;
            } else {
                if (move < 20) {
                    move++;
                }
                if ((lastPress - lastStart) > 5000000000l) {
//                    move = 5;
                    lastStart = 0;
                }
            }
        });
    }
    private static int move = 5;
    private static long lastPress = 0l;
    private static long lastStart = 0l;

    private void checkXAndMovePlayerIfNeeded() {
        double diff = player.getX() - coinX;
        if (diff < 300 && diff > 0) {
            move = 10;

            coin.translateX((diff < 0 ? 1 : (-1)) * randomNumberSource.apply(2, 8));
            coinX = coin.getX();
            if (coinX < 20) {
                coin.setX(620);
            }
            if (coinX > 620) {
                coin.setX(20);
            }
        }

        if (player != null && player.getX() < 0) {
            player.setX(620);
            inc("x", (int) (620 + (-player.getX())));
        }
        if (player != null && player.getX() > 640) {
            player.setX(20);
            inc("x", (int) (20 - player.getX()));
        }
    }

    private void checkYAndMovePlayerIfNeeded() {
        double diff = player.getY() - coinY;

        if (diff < 300 && diff > 0) {
            move = 10;
            coin.translateY((diff < 0 ? 1 : (-1)) * randomNumberSource.apply(2, 8));
            coinY = coin.getY();

            if (coinY < 20) {
                coin.setY(460);
            }
            if (coinY > 460) {
                coin.setY(20);
            }
        }

        if (player != null && player.getY() < 0) {
            player.setY(460);
            inc("y", (int) (460 + (-player.getY())));
        }
        if (player != null && player.getY() > 480) {
            player.setY(20);
            inc("y", (int) (20 - player.getY()));
        }

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("x", 0);
        vars.put("y", 0);
        vars.put("coins", 0);
        vars.put("seconds", 0);
        getGameTimer().runAtInterval(() -> {
            inc("seconds", +1);
        }, Duration.seconds(1));

        getGameTimer().runAtInterval(() -> {
            if (coin != null && coin.isActive()) {

                double translateX = (Math.random() > 0.5 ? 1 : (-1)) * randomNumberSource.apply(2, 10);
                double newX = coinX + translateX;
                if (newX < 10) {
                    translateX = 20;newX = coinX + translateX;
                }
                if (newX > 460) {
                    translateX = -20;newX = coinX + translateX;
                }
                coinX = newX;
                coin.translateX(translateX);

                
                double translateY = (Math.random() > 0.5 ? 1 : (-1)) * randomNumberSource.apply(2, 10);
                double newY = coinY + translateY;
                if (newY < 10) {
                    translateY = 20;newY = coinY + translateY;
                }
                if (newY > 630) {
                    translateX = -20;newY = coinY + translateY;
                }
                coinY = newY;
                coin.translateY((Math.random() > 0.5 ? 1 : (-1)) + randomNumberSource.apply(2, 10));
            }
        }, Duration.millis(20));
    }

    private BiFunction<Integer, Integer, Integer> randomNumberSource = (min, max) -> (min + ((int) (Math.random() * (max < min ? min : (max - min)))));

    private static double currentX, currentY, coinX, coinY;

    @Override
    protected void initGame() {
        int rectancleSide = 10 + ((int) (Math.random() * 40d));
        Supplier<Integer> randomByteSupplier = () -> (randomNumberSource.apply(0, 255));

        PropertyMap state = FXGL.getWorldProperties();

        final double x = Math.random() * 600;
        final double y = Math.random() * 400;
        state.setValue("x", (int) x);
        state.setValue("y", ((int) y));

        ImageView blupiView = createBlupiView();

        player = entityBuilder()
                .type(EntityType.PLAYER)
                .at(x, y)
                //                .viewWithBBox(new Rectangle(rectancleSide, rectancleSide, Color.rgb(
                //                        randomByteSupplier.get(),
                //                        randomByteSupplier.get(),
                //                        randomByteSupplier.get()
                //                )))
                .viewWithBBox(blupiView)
                //                .viewWithBBox("brick.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();

        spawnNewCoin();

    }

    private Circle createCoin() {
        int value = random(1, 5);
        final Circle circle = new Circle(15, 15, 15 + value, Color.YELLOW);
        final Text text = new Text(String.valueOf(value));
        final StackPane stack = new StackPane();
        stack.getProperties().put("coinValue", value);
        circle.getProperties().put("coinValue", value);
        stack.getChildren().addAll(circle, text);
        return circle;
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
                inc("coins", +coin.getProperties().getInt("coinValue"));
                move = 5;
                spawnNewCoin();
            }
        });
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);
        textPixels.textProperty().bind(getWorldProperties().intProperty("pixelsMoved").asString());

        Text x = new Text();
        x.setTranslateX(150);
        x.setTranslateY(100);
        x.textProperty().bind(getWorldProperties().intProperty("x").asString());

        Text y = new Text();
        y.setTranslateX(250);
        y.setTranslateY(100);
        y.textProperty().bind(getWorldProperties().intProperty("y").asString());

        Text coins = new Text();
        coins.setTranslateX(350);
        coins.setTranslateY(100);
        coins.textProperty().bind(getWorldProperties().intProperty("coins").asString());

        Text seconds = new Text();
        seconds.setTranslateX(450);
        seconds.setTranslateY(100);
        seconds.textProperty().bind(getWorldProperties().intProperty("seconds").asString());

        getGameScene().addUINode(textPixels);
        getGameScene().addUINode(x);
        getGameScene().addUINode(y);
        getGameScene().addUINode(coins);
        getGameScene().addUINode(seconds);

        var brickTexture = getAssetLoader().loadTexture("brick.png");
        brickTexture.setTranslateX(50);
        brickTexture.setTranslateY(450);

        getGameScene().addUINode(brickTexture);
    }

    public static void main(String[] args) {

        launch(args);
    }

    private void spawnNewCoin() {
        final Circle coinCircle = createCoin();
        int coinValue = (int) coinCircle.getProperties().get("coinValue");
        final Integer x = randomNumberSource.apply(40, 600);
        final Integer y = randomNumberSource.apply(40, 400);
        coinX = x;
        coinY = y;
        Entity newCoin = entityBuilder()
                .type(EntityType.COIN)
                .at(x, y)
                .viewWithBBox(coinCircle)
                //                .viewWithBBox(createBlupiView())
                .with("coinValue", coinValue)
                .with(new CollidableComponent(true))
                .buildAndAttach();
        coin = newCoin;
        PropertyMap state = FXGL.getWorldProperties();

        getGameTimer().runOnceAfter(() -> {
            if (newCoin.isActive()) {
                newCoin.removeFromWorld();
                spawnNewCoin();
                inc("coins", -coinValue);
            }
        }, Duration.seconds(randomNumberSource.apply(5, 15)));
    }

    private ImageView createBlupiView() {
        final String im = new File("/home/robertvokac/Desktop/speedy_eggbert/speedy_eggbert_I/IMAGE08/blupi000.blp").toURI().toString();
        Image image = new Image(im, false);
        ImageView result = new ImageView(image);
        result.setViewport(new Rectangle2D(165, 118, 30, 47));
        return result;
    }

}
