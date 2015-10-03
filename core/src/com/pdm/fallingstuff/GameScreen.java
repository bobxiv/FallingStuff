package com.pdm.fallingstuff;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

/**
 * Pantalla de juego
 * Created by erojasfredini on 28/09/15.
 */
public class GameScreen  implements Screen {

    float stateTime;

    //region Metodos publicos
    /**
     * Constructor de la clase
     * @param gam Instancia unica del juego
     */
    public GameScreen(final FallingStuffGame gam) {
        game = gam;

        // ----- Creamos las frutas      -----
        fruits = new Array<Fruit>();

        // ----- Configuracion de camara -----
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        frecuenciaAparicion = 3.0f;

        // ----- Cargamos las texturas   -----
        assets = new AssetManager();
        assets.load("Img/banana.png", Texture.class);
        assets.load("Img/uvas.png", Texture.class);
        assets.load("Img/anana.png", Texture.class);
        assets.load("Img/frutilla.png", Texture.class);
        assets.load("Img/sandia.png", Texture.class);

        assets.load("Img/fondo.png", Texture.class);

        assets.load("Img/explosion.png", Texture.class);

        // hasta ahora solo estaban encoladas para cargarse y ahora se cargan sincronicamente
        assets.finishLoading();

        // ----- Configuramos Explosion -----

        Texture explotionTexture = assets.get("Img/explosion.png");
        TextureRegion[][] tmp = TextureRegion.split(explotionTexture, explotionTexture.getWidth() / 8, explotionTexture.getHeight() / 6);
        TextureRegion[] explotionFrames = new TextureRegion[8*6];
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                explotionFrames[index++] = tmp[i][j];
            }
        }
        explotion = new Animation(0.025f, explotionFrames);
        stateTime = 0.0f;

        // ----- Configuramos Fondo   -----
        Texture backgroundTexture = assets.get("Img/fondo.png", Texture.class);
        backgroundSprite = new Sprite(backgroundTexture);
        //backgroundSprite.setPosition(0, 0);
        float ratio = backgroundTexture.getWidth()/(float)backgroundTexture.getHeight();
        //backgroundSprite.setSize(600.0f*ratio, 600);
        backgroundSprite.setBounds(0.0f, 0.0f, Gdx.graphics.getHeight() * ratio, Gdx.graphics.getHeight());

        // ----- Creamos Timer de Frutas -----
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Fruit aux = null;
                float height = 128;
                switch (MathUtils.random(0, 4)) {
                    case 0:
                        aux = new Fruit(FruitType.Banana, height, assets);
                        break;
                    case 1:
                        aux = new Fruit(FruitType.Grapes, height, assets);
                        break;
                    case 2:
                        aux = new Fruit(FruitType.Pineapple, height, assets);
                        break;
                    case 3:
                        aux = new Fruit(FruitType.Strawberry, height, assets);
                        break;
                    case 4:
                        aux = new Fruit(FruitType.Watermelon, height, assets);
                        break;
                }
                float randomX = MathUtils.random(0, Gdx.graphics.getWidth() - height);
                float randomY = Gdx.graphics.getHeight();
                aux.setPosition(randomX, randomY);
                fruits.add(aux);
            }
        }, 0, frecuenciaAparicion);
    }

    /**
     * Dibujamos la pantalla de juego
     * @param delta El delta de tiempo entre frames en segundos
     */
    @Override
    public void render (float delta) {

        // ----- Eventos -----

        // hay algun toque?
        for(int i=0; i < 20 ; ++i)
        if( Gdx.input.isTouched(i) ) {
            // obtenemos la posicion del toque y lo convertimos al mundo
            Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(pos);

            // hay alguna fruta que estemos tocando?
            for(Fruit f: fruits)
            {
                // si colisiona lo eliminamos
                if( f.isHitting(pos.x, pos.y) ) {
                    fruits.removeValue(f, true);
                }else
                {
                    Gdx.app.log("Y", String.valueOf(f.getY()));
                    if( (f.getY()) < 0 )
                        fruits.removeValue(f, true);
                }
            }
        }

        for(Fruit f: fruits)
        {
            //Gdx.app.log("Y", String.valueOf(f.getY()));
            if( (f.getY()+f.getHeight()) < 0 )
                fruits.removeValue(f, true);
        }

        //Gdx.app.log("Count", String.valueOf(fruits.size));

        // ----- actualizamos -----

        for(Fruit f: fruits)
            f.update();

        // ----- dibujamos -----
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // establecemos la matriz de transformacion
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // dibujamos fondo
        backgroundSprite.draw(game.batch);
        // dibujamos las frutas
        for(Fruit f: fruits)
            f.render(game.batch);

        stateTime += delta;
        TextureRegion currentFrame = explotion.getKeyFrame(stateTime, true);
        //game.batch.draw(currentFrame, 50, 50);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        fruits.clear();
    }
    //endregion

    //region Propiedades privadas
    /**
     * Instancia unica del juego
     */
    final FallingStuffGame game;

    /**
     * Frutas de la pantalla
     */
    private Array<Fruit> fruits;

    /**
     * Sprite del fondo
     */
    private Sprite backgroundSprite;

    /**
     * Instancia de la camara
     */
    private OrthographicCamera camera;

    /**
     * Frecuencia a la que aparecen frutas en segundos
     */
    private float frecuenciaAparicion;

    /**
     * Efecto de explosion
     */
    private Animation explotion;

    /**
     * Administrador de assets de la pantalla
     */
    private AssetManager assets;
    //endregion
}
