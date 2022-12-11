package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Nave extends Actor {


    enum VerticalMovement {UP, NONE, DOWN}
    enum HorizontalMovement {LEFT, NONE, RIGHT}

    private static TextureRegion naveReposo = null;
    private static TextureRegion naveIzq = null;
    private static TextureRegion naveDcha = null;
    private static TextureRegion naveArriba = null;
    private static TextureRegion naveArribaIzq = null;
    private static TextureRegion naveArribaDcha = null;
    private static TextureRegion naveAbajo = null;
    private static TextureRegion naveAbajoIzq = null;
    private static TextureRegion naveAbajoDcha = null;

    HorizontalMovement horizontalMovement;
    VerticalMovement verticalMovement;
    TextureRegion regionActual;

    private float statetime = 0f;
    private TextureRegion explosion;
    Animation<TextureRegion> walkAnimation;

    Boolean explo = false;
    Boolean fin = false;
    Boolean endGame = false;
    private Sound boomSound;

    public Nave(float x, float y) {
        if (naveAbajoIzq == null || walkAnimation == null) {
            Texture completo = new Texture(Gdx.files.internal("spacetheme.png"));
            naveArribaIzq = new TextureRegion(completo, 0, 44, 39, 43);
            naveArriba = new TextureRegion(completo, 42, 44, 39, 43);
            naveArribaDcha = new TextureRegion(completo, 84, 44, 39, 43);
            naveIzq = new TextureRegion(completo, 0, 0, 39, 43);
            naveReposo = new TextureRegion(completo, 42, 0, 39, 43);
            naveDcha = new TextureRegion(completo, 84, 0, 39, 43);
            naveAbajoIzq = new TextureRegion(completo, 0, 88, 39, 43);
            naveAbajo = new TextureRegion(completo, 42, 88, 39, 43);
            naveAbajoDcha = new TextureRegion(completo, 84, 88, 39, 43);

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

        regionActual = naveReposo;
        boomSound = Gdx.audio.newSound(Gdx.files.internal("boom.wav"));
        horizontalMovement = HorizontalMovement.NONE;
        verticalMovement = VerticalMovement.NONE;
        setSize(regionActual.getRegionWidth(), regionActual.getRegionHeight());
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        addListener(new NaveInputListener());
    }

    public Nave() {
        this(400, 240);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(regionActual, getX(), getY());
    }

    public Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        //Movimiento por la pantalla
        if (verticalMovement == VerticalMovement.UP) {
            this.moveBy(0, 200 * delta);
        }
        if (verticalMovement == VerticalMovement.DOWN) {
            this.moveBy(0, -200 * delta);
        }
        if (horizontalMovement == HorizontalMovement.LEFT) {
            this.moveBy(-200 * delta, 0);
        }
        if (horizontalMovement == HorizontalMovement.RIGHT) {
            this.moveBy(200 * delta, 0);
        }
        //Limites de la pantalla
        if (getX() < 0) setX(0);
        if (getY() < 0) setY(0);
        if (getX() >= 799 - getWidth()) setX(799 - getWidth());
        if (getY() >= 479 - getHeight()) setY(479 - getHeight());

        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveArribaIzq;
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveArriba;
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveArribaDcha;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveIzq;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveReposo;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveDcha;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveAbajoIzq;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveAbajo;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveAbajoDcha;

        if (explo) {
            explosion = walkAnimation.getKeyFrame(statetime);
            regionActual = explosion;

        }

        if (walkAnimation.isAnimationFinished(statetime) && fin) {
            endGame = true;
        }
    }

    class NaveInputListener extends InputListener {

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.DOWN:
                    verticalMovement = VerticalMovement.DOWN;
                    break;
                case Input.Keys.UP:
                    verticalMovement = VerticalMovement.UP;
                    break;
                case Input.Keys.LEFT:
                    horizontalMovement = HorizontalMovement.LEFT;
                    break;
                case Input.Keys.RIGHT:
                    horizontalMovement = HorizontalMovement.RIGHT;
                    break;
            }
            return true;
        }
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.DOWN:
                    if (verticalMovement == VerticalMovement.DOWN) {
                        verticalMovement = VerticalMovement.NONE;
                    }
                    break;
                case Input.Keys.UP:
                    if (verticalMovement == VerticalMovement.UP) {
                        verticalMovement = VerticalMovement.NONE;
                    }
                    break;
                case Input.Keys.LEFT:
                    if (horizontalMovement == HorizontalMovement.LEFT) {
                        horizontalMovement = HorizontalMovement.NONE;
                    }
                    break;
                case Input.Keys.RIGHT:
                    if (horizontalMovement == HorizontalMovement.RIGHT) {
                        horizontalMovement = HorizontalMovement.NONE;
                    }
                    break;
            }
            return true;
        }
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


    public Boolean getEndGame() {
        return endGame;
    }
}
