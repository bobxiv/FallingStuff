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
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by erojasfredini on 25/09/15.
 */
public class TextureManager {

    public static Texture getTexture(String path)
    {
        Integer id = path.hashCode();
        if( ourInstance.textureMap.containsKey(id) )
            return ourInstance.textureMap.get(id);
        else
        {
            Texture aux = new Texture(Gdx.files.internal(path));
            ourInstance.textureMap.put(id, aux);
            return aux;
        }
    }

    private static TextureManager ourInstance = new TextureManager();

    private TextureManager() {
        textureMap = new ArrayMap<Integer, Texture>();
    }

    private ArrayMap<Integer, Texture> textureMap;
}
