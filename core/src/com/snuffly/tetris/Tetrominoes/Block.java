package com.snuffly.tetris.Tetrominoes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.snuffly.tetris.Tetris;

public class Block {

    public Vector2 position;
    public Rectangle hitbox;

    public final TextureAtlas.AtlasRegion texture;

    public Block(TextureAtlas.AtlasRegion texture) {
        this.texture = texture;

        position = new Vector2();
        hitbox = new Rectangle(position.x, position.y, Tetris.tileSize, Tetris.tileSize);
    }

    public void update() {
        hitbox.x = position.x;
        hitbox.y = position.y;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
            batch.draw(texture, position.x, position.y, Tetris.tileSize, Tetris.tileSize);
        batch.end();
    }
}
