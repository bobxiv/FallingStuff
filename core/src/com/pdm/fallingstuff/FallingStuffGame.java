package com.pdm.fallingstuff;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class FallingStuffGame extends ApplicationAdapter {
	SpriteBatch batch;
	Array<Fruit> fruits;
	Texture backgroundTexture;
	Sprite backgroundSprite;

	OrthographicCamera camera;

	float frecuenciaAparicion;


	@Override
	public void create () {

		// ----- Creamos propiedades -----
		batch = new SpriteBatch();
		fruits = new Array<Fruit>();

		backgroundTexture = TextureManager.getTexture("Img/fondo.png");

		// ----- Configuracion -----
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		frecuenciaAparicion = 3.0f;

		backgroundSprite = new Sprite(backgroundTexture);
		//backgroundSprite.setPosition(0, 0);
		float ratio = backgroundTexture.getWidth()/(float)backgroundTexture.getHeight();
		//backgroundSprite.setSize(600.0f*ratio, 600);
		backgroundSprite.setBounds(0.0f, 0.0f, Gdx.graphics.getHeight()*ratio, Gdx.graphics.getHeight());
/*
		// ----- Creamos Timer -----
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				Fruit aux = null;
				float height = 128;
				switch(MathUtils.random(0, 4))
				{
					case 0:
						aux = new Fruit(FruitType.Banana, height);
						break;
					case 1:
						aux = new Fruit(FruitType.Grapes, height);
						break;
					case 2:
						aux = new Fruit(FruitType.Pineapple, height);
						break;
					case 3:
						aux = new Fruit(FruitType.Strawberry, height);
						break;
					case 4:
						aux = new Fruit(FruitType.Watermelon, height);
						break;
				}
				float randomX = MathUtils.random(0, Gdx.graphics.getWidth() - aux.getWidth());
				float randomY = Gdx.graphics.getHeight();
				aux.setPosition(randomX, randomY);
				fruits.add(aux);
			}
		}, 0, frecuenciaAparicion);*/
		Fruit aux = null;
		float height = 128;
		switch(MathUtils.random(0, 4))
		{
			case 0:
				aux = new Fruit(FruitType.Banana, height);
				break;
			case 1:
				aux = new Fruit(FruitType.Grapes, height);
				break;
			case 2:
				aux = new Fruit(FruitType.Pineapple, height);
				break;
			case 3:
				aux = new Fruit(FruitType.Strawberry, height);
				break;
			case 4:
				aux = new Fruit(FruitType.Watermelon, height);
				break;
		}
		float randomX = MathUtils.random(0, Gdx.graphics.getWidth() - aux.getWidth());
		float randomY = Gdx.graphics.getHeight();
		aux.setPosition(randomX, randomY);
		fruits.add(aux);
	}

	@Override
	public void render () {

		// ----- Eventos -----

		// hay algun toque?
		if( Gdx.input.isTouched() ) {
			// obtenemos la posicion del toque y lo convertimos al mundo
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(pos);

			// hay alguna fruta que estemos tocando?
			for(Fruit f: fruits)
			{
				// si colisiona lo eliminamos
				if( f.getBoundingRectangle().contains(pos.x, pos.y) )
					fruits.removeValue(f, true);
			}
		}

		// ----- actualizamos -----

		for(Fruit f: fruits)
			f.update();

		// ----- dibujamos -----

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// establecemos la matriz de transformacion
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			// dibujamos fondo
			backgroundSprite.draw(batch);
			// dibujamos las frutas
			for(Fruit f: fruits)
				f.draw(batch);
		batch.end();
	}
}
/*
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
		fruitTextures.add( new Texture(Gdx.files.internal("Img/banana.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img/uvas.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img/anana.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img/frutilla.png")));
		fruitTextures.add( new Texture(Gdx.files.internal("Img/sandia.png")));

		backgroundTexture = new Texture(Gdx.files.internal("Img/fondo.png"));

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
				aux.setPosition(MathUtils.random(0, 800 - aux.getTexture().getWidth()), 600);
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
*/