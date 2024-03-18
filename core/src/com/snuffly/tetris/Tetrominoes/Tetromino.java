package com.snuffly.tetris.Tetrominoes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.snuffly.tetris.Tetris;

import java.util.Random;

public class Tetromino {
    public TetrominoType type;
    public TetrominoDirection direction;

    public static final float normalSpeedY = 500; // in millis
    public static final float fastSpeedY = 25; // in millis

    private long lastMoveTimeX;
    private long lastMoveTimeY;

    private static final float moveSpeedX = 150; // in millis
    private float moveSpeedY;

    public Vector2 position;

    public boolean canMoveRight = true;
    public boolean canMoveLeft = true;

    private String nextMoveX;

    public Block[] blocks;
    public TextureAtlas blockTextures;

    public boolean justRotated;

    public Tetromino(TextureAtlas atlas) {
        blockTextures = atlas;
        moveSpeedY = normalSpeedY;

        position = new Vector2();
        blocks = new Block[4];
    }

    public void reset(TetrominoType type) {
        position.y = Tetris.tileSize * 8;
        position.x = 0;
        direction = TetrominoDirection.UP;

        lastMoveTimeX = TimeUtils.millis();
        lastMoveTimeY = TimeUtils.millis();

        createPiece(type.ordinal());
    }

    private void createPiece(int type) {
        switch (type) {
            case 0:
                createI();
                break;
            case 1:
                createO();
                break;
            case 2:
                createS();
                break;
            case 3:
                createZ();
                break;
            case 4:
                createL();
                break;
            case 5:
                createJ();
                break;
            case 6:
                createT();
                break;
        }
    }

    private void createI() {
        blocks[0] = new Block(blockTextures.findRegion("LightBlue"));
        blocks[1] = new Block(blockTextures.findRegion("LightBlue"));
        blocks[2] = new Block(blockTextures.findRegion("LightBlue"));
        blocks[3] = new Block(blockTextures.findRegion("LightBlue"));

        type = TetrominoType.I;
    }

    private void createO() {
        blocks[0] = new Block(blockTextures.findRegion("Yellow"));
        blocks[1] = new Block(blockTextures.findRegion("Yellow"));
        blocks[2] = new Block(blockTextures.findRegion("Yellow"));
        blocks[3] = new Block(blockTextures.findRegion("Yellow"));

        type = TetrominoType.O;
    }

    private void createS() {
        blocks[0] = new Block(blockTextures.findRegion("Green"));
        blocks[1] = new Block(blockTextures.findRegion("Green"));
        blocks[2] = new Block(blockTextures.findRegion("Green"));
        blocks[3] = new Block(blockTextures.findRegion("Green"));

        type = TetrominoType.S;
    }

    private void createZ() {
        blocks[0] = new Block(blockTextures.findRegion("Red"));
        blocks[1] = new Block(blockTextures.findRegion("Red"));
        blocks[2] = new Block(blockTextures.findRegion("Red"));
        blocks[3] = new Block(blockTextures.findRegion("Red"));

        type = TetrominoType.Z;
    }

    private void createJ() {
        blocks[0] = new Block(blockTextures.findRegion("Blue"));
        blocks[1] = new Block(blockTextures.findRegion("Blue"));
        blocks[2] = new Block(blockTextures.findRegion("Blue"));
        blocks[3] = new Block(blockTextures.findRegion("Blue"));

        type = TetrominoType.J;
    }

    private void createL() {
        blocks[0] = new Block(blockTextures.findRegion("Orange"));
        blocks[1] = new Block(blockTextures.findRegion("Orange"));
        blocks[2] = new Block(blockTextures.findRegion("Orange"));
        blocks[3] = new Block(blockTextures.findRegion("Orange"));

        type = TetrominoType.L;
    }

    private void createT() {
        blocks[0] = new Block(blockTextures.findRegion("Purple"));
        blocks[1] = new Block(blockTextures.findRegion("Purple"));
        blocks[2] = new Block(blockTextures.findRegion("Purple"));
        blocks[3] = new Block(blockTextures.findRegion("Purple"));

        type = TetrominoType.T;
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
        justRotated = true;
    }

    public void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && canMoveLeft) {
            nextMoveX = "Left";
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && canMoveRight) {
            nextMoveX = "Right";
        } else {
            nextMoveX = "";
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveSpeedY = fastSpeedY;
        } else {
            moveSpeedY = normalSpeedY;
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP)) && (canMoveRight || canMoveLeft)) {
            rotate();
        }
    }

    public void update() {
        justRotated = false;
        input();

        switch (type) {
            case I:
                switch (direction) {
                    case UP:
                    case DOWN:
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
                    case LEFT:
                        blocks[0].position.x = position.x - Tetris.tileSize * 2;
                        blocks[1].position.x = position.x - Tetris.tileSize;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y;
                        break;

                }
                break;

            case O:
                // no need to rotate the O because it is a square
                blocks[0].position.x = position.x - Tetris.tileSize;
                blocks[1].position.x = position.x;
                blocks[2].position.x = position.x - Tetris.tileSize;
                blocks[3].position.x = position.x;

                blocks[0].position.y = position.y;
                blocks[1].position.y = position.y;
                blocks[2].position.y = position.y - Tetris.tileSize;
                blocks[3].position.y = position.y - Tetris.tileSize;
                break;

            case S:
                switch (direction) {
                    case UP:
                    case DOWN:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x - Tetris.tileSize;
                        blocks[3].position.x = position.x - Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case LEFT:
                    case RIGHT:
                        blocks[0].position.x = position.x + Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x - Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y - Tetris.tileSize;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                }
                break;

            case Z:
                switch (direction) {
                    case UP:
                    case DOWN:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x + Tetris.tileSize;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case LEFT:
                    case RIGHT:
                        blocks[0].position.x = position.x - Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y - Tetris.tileSize;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                }
                break;

            case L:
                switch (direction) {
                    case UP:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y - Tetris.tileSize;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case RIGHT:
                        blocks[0].position.x = position.x + Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x - Tetris.tileSize;
                        blocks[3].position.x = position.x - Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case DOWN:
                        blocks[0].position.x = position.x - Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y + Tetris.tileSize;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case LEFT:
                        blocks[0].position.x = position.x - Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x + Tetris.tileSize;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y + Tetris.tileSize;
                        break;
                }
                break;

            case J:
                switch (direction) {
                    case UP:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x - Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y - Tetris.tileSize;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case RIGHT:
                        blocks[0].position.x = position.x + Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x - Tetris.tileSize;
                        blocks[3].position.x = position.x - Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y + Tetris.tileSize;
                        break;
                    case DOWN:
                        blocks[0].position.x = position.x + Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y + Tetris.tileSize;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case LEFT:
                        blocks[0].position.x = position.x - Tetris.tileSize;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x + Tetris.tileSize;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                }
                break;

            case T:
                switch (direction) {
                    case UP:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x - Tetris.tileSize;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y;
                        break;
                    case RIGHT:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x + Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y - Tetris.tileSize;
                        blocks[3].position.y = position.y;
                        break;
                    case DOWN:
                        blocks[0].position.x = position.x - Tetris.tileSize;
                        blocks[1].position.x = position.x + Tetris.tileSize;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x;

                        blocks[0].position.y = position.y;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y;
                        blocks[3].position.y = position.y - Tetris.tileSize;
                        break;
                    case LEFT:
                        blocks[0].position.x = position.x;
                        blocks[1].position.x = position.x;
                        blocks[2].position.x = position.x;
                        blocks[3].position.x = position.x - Tetris.tileSize;

                        blocks[0].position.y = position.y + Tetris.tileSize;
                        blocks[1].position.y = position.y;
                        blocks[2].position.y = position.y - Tetris.tileSize;
                        blocks[3].position.y = position.y;
                        break;
                }
                break;
        }

        if (TimeUtils.millis() - lastMoveTimeY > moveSpeedY) {
            position.y -= Tetris.tileSize;
            lastMoveTimeY = TimeUtils.millis();
        }

        if (nextMoveX != null && TimeUtils.millis()- lastMoveTimeX > moveSpeedX) {
            if (nextMoveX.equals("Left") && canMoveLeft) {
                position.x -= Tetris.tileSize;
                lastMoveTimeX = TimeUtils.millis();
                nextMoveX = "";
            }
            if (nextMoveX.equals("Right") && canMoveRight) {
                position.x += Tetris.tileSize;
                lastMoveTimeX = TimeUtils.millis();
                nextMoveX = "";
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
