package com.pdm.fallingstuff;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.List;

public class FallingStuffGame extends ApplicationAdapter {
	SpriteBatch batch;
	Array<Texture> fruitTextures;
	Array<Sprite> fruitSprites;
	Array<Float> velocidades;
	Texture backgroundTexture;
	Sprite backgroundSprite;

	OrthographicCamera camera;

	float gravedad;
	float frecuenciaAparicion;

	
	@Override
	public void create () {

		// ----- Creamos propiedades -----
		batch = new SpriteBatch();
		fruitTextures = new Array<Texture>();
		fruitSprites = new Array<Sprite>();
		velocidades = new Array<Float>();

		// ----- Cargamos Texturas -----
		fruitTextures.add( new Texture(Gdx.files.internal("Img\\banana.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img\\uvas.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img\\anana.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img\\frutilla.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img\\sandia.png")));

		backgroundTexture = new Texture(Gdx.files.internal("Img\\fondo.png"));

		// ----- Configuracion -----
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		gravedad = 100;

		frecuenciaAparicion = 3.0f;

		backgroundSprite = new Sprite(backgroundTexture);
		//backgroundSprite.setPosition(0, 0);
		float ratio = backgroundTexture.getWidth()/(float)backgroundTexture.getHeight();
		//backgroundSprite.setSize(600.0f*ratio, 600);
		backgroundSprite.setBounds(0.0f, 0.0f, 600.0f*ratio, 600.0f);

		// ----- Creamos Timer -----
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				Sprite aux = new Sprite(fruitTextures.get(MathUtils.random(0,4)));
				aux.setPosition(MathUtils.random(0, 800 - aux.getTexture().getWidth()), 600 /*- aux.getTexture().getHeight()*/);
				aux.setSize(128, 128);
				fruitSprites.add(aux);
				velocidades.add(new Float(0));
			}
		}, 0, frecuenciaAparicion);
	}

	@Override
	public void render () {

		// ----- Eventos -----

		// hay algun toque?
		if( Gdx.input.isTouched() ) {
			// obtenemos la posicion del toque y lo convertimos al mundo
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(pos);

			// hay algun sprite que estemos tocando?
			for(int s=0; s < fruitSprites.size ; ++s)
			{
				Sprite fruit = fruitSprites.get(s);
				// si colisiona lo eliminamos
				if( fruit.getBoundingRectangle().contains(pos.x, pos.y) ) {
					fruitSprites.removeValue(fruit, true);
					Float f = velocidades.get(s);
					velocidades.removeValue(f, true);
				}
			}
		}

		// ----- actualizamos -----

		//Gdx.app.log("Cant", String.valueOf(velocidades.size));
		// aplicamos la gravedad
		for(int s=0; s < fruitSprites.size ; ++s)
		{
			Sprite fruit = fruitSprites.get(s);
			Float vel = velocidades.get(s);
			vel -= gravedad*Gdx.graphics.getDeltaTime();
			fruit.setPosition(fruit.getX(), fruit.getY() + vel * Gdx.graphics.getDeltaTime());
			velocidades.set(s, vel);
			//Gdx.app.log("Vel", vel.toString());
		}

		// ----- dibujamos -----

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// establecemos la matriz de transformacion
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// dibujamos fondo
		backgroundSprite.draw(batch);
		// dibujamos las frutas
		for(Sprite s: fruitSprites)
		{
			s.draw(batch);
		}
		batch.end();
	}
}
