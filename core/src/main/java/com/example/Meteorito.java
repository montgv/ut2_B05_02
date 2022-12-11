package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

public class Meteorito extends Actor {
    private final TextureRegion meteoroRegion;
    private final float velocidad;
    private final int rotation;
    private TextureRegion actual;
    private float statetime = 0f;
    private TextureRegion explosion;
    Animation<TextureRegion> walkAnimation;
    Boolean explo = false;
    Boolean fin = false;
    private Sound boomSound;

    public Meteorito(TextureRegion textureRegion, int velocidad, float x, float y, Texture completo) {
        if (walkAnimation == null) {
            TextureRegion[] walkFrames;
            TextureRegion e1 = new TextureRegion(completo, 6, 165, 12, 13);
            TextureRegion e2 = new TextureRegion(completo, 23, 162, 19, 19);
            TextureRegion e3 = new TextureRegion(completo, 45, 159, 25, 25);
            TextureRegion e4 = new TextureRegion(completo, 74, 150,46,41);
            TextureRegion e5 = new TextureRegion(completo, 0, 195, 52, 47);
            TextureRegion e6 = new TextureRegion(completo, 50,192,61,55);
            TextureRegion e7 = new TextureRegion(completo, 119, 192, 64, 60);
            TextureRegion e8 = new TextureRegion(completo, 188, 196, 61, 52);
            walkFrames = new TextureRegion[]{e1, e2, e3, e4, e5, e6, e7, e8};
            walkAnimation = new Animation<>(0.099f, walkFrames);
        }
        boomSound = Gdx.audio.newSound(Gdx.files.internal("boom.wav"));
        meteoroRegion = textureRegion;
        setSize(meteoroRegion.getRegionWidth(), meteoroRegion.getRegionHeight());
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        this.velocidad = velocidad;
        rotation = MathUtils.random(1, 2);
        setOrigin(Align.center);
        actual = meteoroRegion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(actual, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)&& !explo) {
            Vector3 vector = new Vector3();
            vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            getStage().getCamera().unproject(vector);
            if (getShape().contains(vector.x, vector.y)) {
                explosion();
            }
        }

        if (explo) {
            explosion = walkAnimation.getKeyFrame(statetime);
            actual = explosion;
        }

        if (walkAnimation.isAnimationFinished(statetime) && getFin()) this.remove();

        if (rotation == 1 && !fin) {
            setRotation(getRotation() + 90 * delta);
        } else {
            setRotation(getRotation() + -90 * delta);
        }

        if (!fin) {
            this.moveBy(0, -velocidad * delta);
        }

        if (this.getY() < -40) this.remove();
    }

    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void explosion() {
        statetime = 0f;
        explo = true;
        fin = true;
        boomSound.play();
    }

    public Boolean getFin() {
        return fin;
    }
}
