package com.pdm.fallingstuff;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.assets.AssetManager;

/**
 * Objeto de juego
 * Created by erojasfredini on 28/09/15.
 */
public class FallingStuffGame extends Game {

	//region Propiedades publicas
	/**
	 * El spritebatch principal a usar en la aplicacion
	 */
	public SpriteBatch batch;

	/**
	 * La fuente principal a usar en la aplicacion
	 */
	public BitmapFont font;
	//endregion

	//region Metodos publicos
	/**
	 * Callback al crear la aplicacion
	 */
	public void create() {
		batch = new SpriteBatch();
		// Usamos la fuente Arial que tiene por defecto LibGDX
		font = new BitmapFont();

		// cremos la pantalla de menu y le damos control
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Callback al dibujar el juego
	 */
	public void render() {
		// Importante! sin esto no se actualizara la pantalla establecida
		super.render();
	}

	/**
	 * Callback al liberamos los recursos
	 */
	public void dispose() {
		// limpiamos los objetos que creamos
		batch.dispose();
		font.dispose();
	}
	//endregion

}