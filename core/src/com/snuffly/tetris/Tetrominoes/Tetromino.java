package com.snuffly.tetris.Tetrominoes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.snuffly.tetris.Tetris;

public class Tetromino {
    public TetrominoType type;
    public TetrominoDirection direction;
    public Vector2 position;

    public Block[] blocks;
    public TextureAtlas blockTextures;

    public Tetromino(TetrominoType type, TextureAtlas atlas) {
        this.type = type;
        direction = TetrominoDirection.UP;
        blockTextures = atlas;

        position = new Vector2();
        blocks = new Block[4];

        switch (type) {
            case I:
                createI();
                break;
        }
    }

    private void createI() {
        blocks[0] = new Block(blockTextures.findRegion("LightBlue"));
        blocks[1] = new Block(blockTextures.findRegion("LightBlue"));
        blocks[2] = new Block(blockTextures.findRegion("LightBlue"));
        blocks[3] = new Block(blockTextures.findRegion("LightBlue"));
    }

    private void rotate() {
            switch (direction) {
                case UP:
                    direction = TetrominoDirection.RIGHT;
                    break;
                case RIGHT:
                    direction = TetrominoDirection.DOWN;
                    break;
                case DOWN:
                    direction = TetrominoDirection.LEFT;
                    break;
                case LEFT:
                    direction = TetrominoDirection.UP;
                    break;
            }
    }

    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            position.x -= Tetris.tileSize;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            position.x += Tetris.tileSize;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            position.y -= Tetris.tileSize;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            rotate();
        }
    }

    public void update() {
        input();

        if (type == TetrominoType.I) {
            switch (direction) {
                case UP:
                    blocks[0].position.x = position.x;
                    blocks[1].position.x = position.x;
                    blocks[2].position.x = position.x;
                    blocks[3].position.x = position.x;

                    blocks[0].position.y = position.y + Tetris.tileSize;
                    blocks[1].position.y = position.y;
                    blocks[2].position.y = position.y - Tetris.tileSize;
                    blocks[3].position.y = position.y - Tetris.tileSize * 2;
                    break;
                case RIGHT:
                    blocks[0].position.x = position.x - Tetris.tileSize * 2;
                    blocks[1].position.x = position.x - Tetris.tileSize;
                    blocks[2].position.x = position.x;
                    blocks[3].position.x = position.x + Tetris.tileSize;

                    blocks[0].position.y = position.y;
                    blocks[1].position.y = position.y;
                    blocks[2].position.y = position.y;
                    blocks[3].position.y = position.y;
                    break;
                case DOWN:
                    blocks[0].position.x = position.x - Tetris.tileSize;
                    blocks[1].position.x = position.x - Tetris.tileSize;
                    blocks[2].position.x = position.x - Tetris.tileSize;
                    blocks[3].position.x = position.x - Tetris.tileSize;

                    blocks[0].position.y = position.y + Tetris.tileSize;
                    blocks[1].position.y = position.y;
                    blocks[2].position.y = position.y - Tetris.tileSize;
                    blocks[3].position.y = position.y - Tetris.tileSize * 2;
                    break;
                case LEFT:
                    blocks[0].position.x = position.x - Tetris.tileSize * 3;
                    blocks[1].position.x = position.x - Tetris.tileSize * 2;
                    blocks[2].position.x = position.x - Tetris.tileSize;
                    blocks[3].position.x = position.x;

                    blocks[0].position.y = position.y;
                    blocks[1].position.y = position.y;
                    blocks[2].position.y = position.y;
                    blocks[3].position.y = position.y;
                    break;
            }
        }

        for (Block b : blocks) {
            b.update();
        }
    }

    public void render(SpriteBatch batch) {
        for (Block b : blocks) {
            b.render(batch);
        }
    }
}
