package com.snuffly.tetris.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snuffly.tetris.Tetris;
import com.snuffly.tetris.Tetrominoes.Block;
import com.snuffly.tetris.Tetrominoes.Tetromino;
import com.snuffly.tetris.Tetrominoes.TetrominoType;

import java.util.ArrayList;
import java.util.Random;

public class Game implements Screen {
    private final Tetris game;

    public boolean gameOver;

    private static final float leftBound = -Tetris.tileSize * 5;
    private static final float rightBound = Tetris.tileSize * 4;
    private static final float roofBound = Tetris.tileSize * 9;
    private static final float floorBound = -Tetris.tileSize * 10;

    private final Tetromino currentTetromino;

    private final ArrayList<TetrominoType> tetrominoQueue;

    private final ArrayList<Block> fallenBlocks;
    private ArrayList<Block> fallenBlocksCopy;

    public Game(final Tetris game) {
        this.game = game;

        // temp
        currentTetromino = new Tetromino(game.blockTextures);
        currentTetromino.position.y = Tetris.tileSize * 8;

        tetrominoQueue = new ArrayList<>(2);

        addToQueue();

        currentTetromino.reset(tetrominoQueue.getLast());

        fallenBlocks = new ArrayList<>();
        fallenBlocksCopy = new ArrayList<>(fallenBlocks);
    }

    private void addToQueue() {
        while (tetrominoQueue.size() != 2) {
            int rand = new Random().nextInt(TetrominoType.values().length);

            tetrominoQueue.add(TetrominoType.values()[rand]);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.06f, 0.05f, 0.05f, 1.0f);

        /////////////////////////////////////////
        // updates                             //
        /////////////////////////////////////////

        if (!gameOver) {
            currentTetromino.update();
        }

        addToQueue();

        System.out.println("Next Piece: " + tetrominoQueue.getLast());

        // resets
        currentTetromino.canMoveRight = true;
        currentTetromino.canMoveLeft = true;
        boolean added = false;

        if (!gameOver) {
            for (Block ctb : currentTetromino.blocks) {
                // wall check
                if (ctb.position.x < leftBound) {
                    currentTetromino.position.x += Tetris.tileSize;
                }
                if (ctb.position.x > rightBound) {
                    currentTetromino.position.x -= Tetris.tileSize;
                }

                for (Block fb : fallenBlocks) {
                    // game over check
                    if (fb.position.y >= roofBound) {
                        gameOver = true;
                    }

                    // checks if there is a fallen block next to the current tetromino
                    if (ctb.position.x + Tetris.tileSize == fb.position.x && ctb.position.y == fb.position.y) {
                        currentTetromino.canMoveRight = false;
                    }
                    if (ctb.position.x - Tetris.tileSize == fb.position.x && ctb.position.y == fb.position.y) {
                        currentTetromino.canMoveLeft = false;
                    }
                }

                // checks if the left wall is next to the current tetromino
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
                        currentTetromino.reset(tetrominoQueue.getLast());
                        tetrominoQueue.removeLast();
                        fallenBlocksCopy = new ArrayList<>(fallenBlocks);
                    }
                }

                // floor check
                if (ctb.position.y <= floorBound && !added) {
                    for (Block b : currentTetromino.blocks) {
                        Block copy = new Block(b.texture);

                        copy.position.set(b.position);
                        copy.hitbox = new Rectangle(b.hitbox);
                        fallenBlocks.add(copy);
                    }
                    added = true;
                    currentTetromino.reset(tetrominoQueue.getLast());
                    tetrominoQueue.removeLast();
                    fallenBlocksCopy = new ArrayList<>(fallenBlocks);
                }
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
