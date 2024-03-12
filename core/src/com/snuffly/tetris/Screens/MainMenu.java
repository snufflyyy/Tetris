package com.snuffly.tetris.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.snuffly.tetris.Tetris;

public class MainMenu implements Screen {
    private final Tetris game;

    private long lastFlashTime;

    public MainMenu(final Tetris game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.06f, 0.05f, 0.05f, 1.0f);

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

            /////////////////////////////////////////
            // border and background               //
            /////////////////////////////////////////

            // checker pattern
            int counter = 0;
            for (int y = 0; y < game.initScreenHeight / Tetris.tileSize; y++) {
                for (int x = 0; x < game.initScreenWidth / Tetris.tileSize; x++) {
                    if (y != 0 && y != game.initScreenHeight / Tetris.tileSize || x != 0 && x != game.initScreenWidth / Tetris.tileSize) {
                        if (counter > 1) {
                            counter = 0;
                        }

                        // 0 = black, 1 = gray
                        if (counter == 0) {
                            game.batch.draw(game.blockTextures.findRegion("Black"),
                                -(float) game.initScreenWidth / 2 + x * Tetris.tileSize,
                                -(float) game.initScreenHeight / 2 + y * Tetris.tileSize,
                                Tetris.tileSize,
                                Tetris.tileSize
                            );
                        } else {
                            game.batch.draw(game.blockTextures.findRegion("Gray"),
                                -(float) game.initScreenWidth / 2 + x * Tetris.tileSize,
                                -(float) game.initScreenHeight / 2 + y * Tetris.tileSize,
                                Tetris.tileSize,
                                Tetris.tileSize
                            );
                        }
                        counter++;
                    }
                }
                counter--;
            }

            // left screen border
            for (int i = 0; i < game.initScreenHeight / Tetris.tileSize; i++) {
                game.batch.draw(
                        game.blockTextures.findRegion("Border"),
                        -(float) game.initScreenWidth / 2,
                        -(float) game.initScreenHeight / 2 + Tetris.tileSize * i,
                        Tetris.tileSize,
                        Tetris.tileSize
                );
            }

            // right screen border
            for (int i = 0; i < game.initScreenHeight / Tetris.tileSize; i++) {
                game.batch.draw(
                        game.blockTextures.findRegion("Border"),
                        (float) game.initScreenWidth / 2 - Tetris.tileSize,
                        -(float) game.initScreenHeight / 2 + Tetris.tileSize * i,
                        Tetris.tileSize,
                        Tetris.tileSize
                );
            }

            // top screen border
            for (int i = 0; i < game.initScreenWidth / Tetris.tileSize; i++) {
                if (i != 0 && i != game.initScreenWidth / Tetris.tileSize) {
                    game.batch.draw(
                            game.blockTextures.findRegion("Border"),
                            -(float) game.initScreenWidth / 2 + Tetris.tileSize * i,
                            (float) game.initScreenHeight / 2 - Tetris.tileSize,
                            Tetris.tileSize,
                            Tetris.tileSize
                    );
                }
            }

            // bottom screen border
            for (int i = 0; i < game.initScreenWidth / Tetris.tileSize; i++) {
                if (i != 0 && i != game.initScreenWidth / Tetris.tileSize) {
                    game.batch.draw(
                            game.blockTextures.findRegion("Border"),
                            -(float) game.initScreenWidth / 2 + Tetris.tileSize * i,
                            -(float) game.initScreenHeight / 2,
                            Tetris.tileSize,
                            Tetris.tileSize
                    );
                }
            }

            /////////////////////////////////////////
            // text & stuff                        //
            /////////////////////////////////////////

            game.classicFont.draw(game.batch, "made by snuffly", -135, 22);

            if (TimeUtils.nanoTime() - lastFlashTime > 1000000000) {
                game.classicFont.draw(game.batch, "press any key to play", -135, -100);
                lastFlashTime = TimeUtils.nanoTime();
            }

            /////////////////////////////////////////
            // tetris title                        //
            /////////////////////////////////////////

            // T
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 12,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 11,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 10,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // lower part
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 11,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 11,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 11,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Red"),
                -Tetris.tileSize * 11,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // E
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 8,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 7,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 6,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );

            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 8,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // middle part (idk what to call it)
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 8,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 7,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 6,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );

            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 8,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // bottom part
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 8,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 7,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Blue"),
                -Tetris.tileSize * 6,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // second T
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 4,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 3,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 2,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // lower part
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 3,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 3,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 3,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Purple"),
                -Tetris.tileSize * 3,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // R (this R is pretty bad, might need to redo it)
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 1,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 2,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // bottom part of circle
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 2,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // circle closer ig?
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 3,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // diagonal part
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 3,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 3,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // stem? (again idk what to call it)
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 1,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 1,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 1,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Green"),
                Tetris.tileSize * 1,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // I
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 5,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 6,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 7,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // stem?
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 6,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 6,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 6,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // bottom part
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 5,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 6,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("Orange"),
                Tetris.tileSize * 7,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // S
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 11,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 10,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 9,
                Tetris.tileSize * 6,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // connector? (again for like the 3rd time, idk what to call this)
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 9,
                Tetris.tileSize * 5,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // middle part
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 11,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 10,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 9,
                Tetris.tileSize * 4,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // connector? (again for like the 4rd time, idk what to call this)
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 11,
                Tetris.tileSize * 3,
                Tetris.tileSize,
                Tetris.tileSize
            );

            // bottom part
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 11,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 10,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );
            game.batch.draw(
                game.blockTextures.findRegion("LightBlue"),
                Tetris.tileSize * 9,
                Tetris.tileSize * 2,
                Tetris.tileSize,
                Tetris.tileSize
            );

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
