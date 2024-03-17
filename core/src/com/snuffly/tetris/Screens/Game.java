package com.snuffly.tetris.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snuffly.tetris.Tetris;
import com.snuffly.tetris.Tetrominoes.Block;
import com.snuffly.tetris.Tetrominoes.Tetromino;
import com.snuffly.tetris.Tetrominoes.TetrominoDirection;
import com.snuffly.tetris.Tetrominoes.TetrominoType;

import java.util.ArrayList;

public class Game implements Screen {
    private final Tetris game;

    private static final float leftBound = -Tetris.tileSize * 5;
    private static final float rightBound = Tetris.tileSize * 4;
    private static final float floorBound = -Tetris.tileSize * 10;

    private final Tetromino currentTetromino;

    private final ArrayList<Block> fallenBlocks;
    private ArrayList<Block> fallenBlocksCopy;

    public Game(final Tetris game) {
        this.game = game;

        // temp
        currentTetromino = new Tetromino(TetrominoType.I, game.blockTextures, leftBound, rightBound);
        currentTetromino.position.y = Tetris.tileSize * 8;

        fallenBlocks = new ArrayList<>();
        fallenBlocksCopy = new ArrayList<>(fallenBlocks);
    }

    // move to tetromino class
    public void resetTetromino() {
        currentTetromino.position.y = Tetris.tileSize * 8;
        currentTetromino.position.x = 0;
        currentTetromino.direction = TetrominoDirection.UP;

        fallenBlocksCopy = new ArrayList<>(fallenBlocks);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.06f, 0.05f, 0.05f, 1.0f);

        /////////////////////////////////////////
        // updates                             //
        /////////////////////////////////////////

        currentTetromino.update();

        // resets
        currentTetromino.canMoveRight = true;
        currentTetromino.canMoveLeft = true;
        boolean added = false;

        for (Block ctb : currentTetromino.blocks) {
            // wall check
            if (ctb.position.x < leftBound) {
                currentTetromino.position.x += Tetris.tileSize;
            }
            if (ctb.position.x > rightBound) {
                currentTetromino.position.x -= Tetris.tileSize;
            }

            for (Block fb : fallenBlocks) {
                if (ctb.position.x - Tetris.tileSize == fb.position.x && ctb.position.y == fb.position.y && currentTetromino.justRotated) {
                    if (currentTetromino.type == TetrominoType.I) {
                        currentTetromino.position.x += Tetris.tileSize * 2;
                    } else {
                        currentTetromino.position.x += Tetris.tileSize;
                    }
                }
                if (ctb.position.x - Tetris.tileSize == fb.position.x && ctb.position.y == fb.position.y && currentTetromino.justRotated) {
                    currentTetromino.position.x -= Tetris.tileSize;
                }

                if (ctb.position.x + Tetris.tileSize == fb.position.x && ctb.position.y == fb.position.y) {
                    currentTetromino.canMoveRight = false;
                }
                if (ctb.position.x - Tetris.tileSize == fb.position.x && ctb.position.y == fb.position.y) {
                    currentTetromino.canMoveLeft = false;
                }
            }

            if (ctb.position.x <= leftBound) {
                currentTetromino.canMoveLeft = false;
            }
            if (ctb.position.x >= rightBound) {
                currentTetromino.canMoveRight = false;
            }

            // fallen block check
            for (Block fbc : fallenBlocksCopy) {
                if (ctb.hitbox.overlaps(fbc.hitbox) && !added && !currentTetromino.justRotated) {
                    added = true;
                    for (Block b : currentTetromino.blocks) {
                        Block copy = new Block(b.texture);

                        copy.position.set(b.position.x, b.position.y + Tetris.tileSize);
                        copy.hitbox = new Rectangle(b.hitbox.x, b.hitbox.y + Tetris.tileSize, Tetris.tileSize, Tetris.tileSize);
                        fallenBlocks.add(copy);
                    }
                    resetTetromino();
                }
            }

            // new floor check
            if (ctb.position.y <= floorBound && !added) {
                for (Block b : currentTetromino.blocks) {
                    Block copy = new Block(b.texture);

                    copy.position.set(b.position);
                    copy.hitbox = new Rectangle(b.hitbox);
                    fallenBlocks.add(copy);
                }
                added = true;
                resetTetromino();
            }
        }

        /////////////////////////////////////////
        // rendering                           //
        /////////////////////////////////////////

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        // game board and background

        for (int x = 0; x <= game.initScreenWidth / Tetris.tileSize; x++) {
            for (int y = 0; y <= game.initScreenHeight / Tetris.tileSize; y++) {
                game.batch.draw(
                    game.blockTextures.findRegion("Border"),
                    -(float) game.initScreenWidth / 2 + x * Tetris.tileSize,
                    -(float) game.initScreenHeight / 2 + y * Tetris.tileSize,
                    Tetris.tileSize,
                    Tetris.tileSize
                );
            }
        }

        // game board
        int counter = 0;
        for (int y = 0; y <= 20; y++) {
            for (int x = 0; x <= 10; x++) {
                if (y != 0 && y != game.initScreenHeight / Tetris.tileSize && x != 0 && x != game.initScreenWidth / Tetris.tileSize) {
                    if (counter > 1) {
                        counter = 0;
                    }

                    // 0 = black, other = gray
                    if (counter == 0) {
                        game.batch.draw(
                            game.blockTextures.findRegion("Black"),
                            -(float) game.initScreenWidth / 2 + x * Tetris.tileSize + 9 * Tetris.tileSize,
                            -(float) game.initScreenHeight / 2 + y * Tetris.tileSize,
                            Tetris.tileSize,
                            Tetris.tileSize
                        );
                    } else {
                        game.batch.draw(
                            game.blockTextures.findRegion("Gray"),
                            -(float) game.initScreenWidth / 2 + x * Tetris.tileSize + 9 * Tetris.tileSize,
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

        // stats & related things

        // left side of the board

        // next piece box
        for (int x = 0; x <= 6; x++) {
            for (int y = 0; y <= 3; y++) {
                game.batch.draw(
                    game.blockTextures.findRegion("Black"),
                    -(float) game.initScreenWidth / 2 + Tetris.tileSize + x * Tetris.tileSize,
                    -(float) game.initScreenHeight / 2 + Tetris.tileSize * 17 + y * Tetris.tileSize,
                    Tetris.tileSize,
                    Tetris.tileSize
                );
            }
        }
        game.classicFont.draw(game.batch, "Next", -370, 310);

        // holding piece box
        for (int x = 0; x <= 6; x++) {
            for (int y = 0; y <= 3; y++) {
                game.batch.draw(
                    game.blockTextures.findRegion("Black"),
                    -(float) game.initScreenWidth / 2 + Tetris.tileSize + x * Tetris.tileSize,
                    -(float) game.initScreenHeight / 2 + Tetris.tileSize * 12 + y * Tetris.tileSize,
                    Tetris.tileSize,
                    Tetris.tileSize
                );
            }
        }
        game.classicFont.draw(game.batch, "Holding", -403, 150);

        // right side of board

        // current score and high score box
        for (int x = 0; x <= 6; x++) {
            for (int y = 0; y <= 4; y++) {
                game.batch.draw(
                    game.blockTextures.findRegion("Black"),
                    -(float) game.initScreenWidth / 2 + Tetris.tileSize * 22 + x * Tetris.tileSize,
                    -(float) game.initScreenHeight / 2 + Tetris.tileSize * 16 + y * Tetris.tileSize,
                    Tetris.tileSize,
                    Tetris.tileSize
                );
            }
        }

        game.classicFont.draw(game.batch, "High Score", 243, 310);
        game.classicFont.draw(game.batch, "Score", 285, 227);

        game.batch.end();

        for (Block b : fallenBlocks) {
            b.render(game.batch);
        }

        currentTetromino.render(game.batch);

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
