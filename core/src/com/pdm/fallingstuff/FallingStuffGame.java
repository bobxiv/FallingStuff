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

public class FallingStuffGame extends ApplicationAdapter {
	SpriteBatch batch;
	Array<Texture> fruitTextures;
	Array<Sprite> fruitSprites;
	Texture img;

	OrthographicCamera camera;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		fruitTextures = new Array<Texture>();
		fruitSprites = new Array<Sprite>();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				Sprite aux = new Sprite(img);
				aux.setPosition(MathUtils.random(0, 800 - img.getWidth()), 600 - img.getHeight());
				fruitSprites.add(aux);
			}
		}, 0, 3);
	}

	@Override
	public void render () {

		if( Gdx.input.isTouched() ) {
			Vector3 pos = new Vector3();
			pos.x = Gdx.input.getX();
			pos.y = Gdx.input.getY();
			pos.z = 0;
			camera.unproject(pos);

			for(Sprite s: fruitSprites)
			{

				if( s.getBoundingRectangle().contains(pos.x, pos.y) )
					fruitSprites.removeValue(s, true);
			}
		}

		float gravedad = 300;

		// actualizamos
		for(Sprite s: fruitSprites)
		{
			s.setPosition(s.getX(), s.getY() - gravedad*Gdx.graphics.getDeltaTime()*Gdx.graphics.getDeltaTime());
		}

		// dibujamos
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Sprite s: fruitSprites)
		{
			s.draw(batch);
		}
		batch.end();
	}
}
