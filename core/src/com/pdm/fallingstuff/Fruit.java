package com.pdm.fallingstuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.assets.AssetManager;

/**
 * La clase reepresenta una fruta. Esta clase tiene la responsabilidad de actualizar la fisica de la fruta y permitir dibujarla
 * Created by erojasfredini on 25/09/15.
 */
public class Fruit {

    //region Metodos publicos
    /**
     * Constructor de la clase
     * @param type El tipo de fruta que se desea construrir
     * @param height La altura de la fruta en coordenadas del mundo
     * @param assets El administrador de assets que contiene las texturas necesarias
     */
    public Fruit(FruitType type, float height, AssetManager assets)
    {
        // cargamos la textura que corresponde
        Texture texture = null;
        switch(type)
        {
            case Banana:
                texture = assets.get("Img/banana.png", Texture.class);
                break;
            case Grapes:
                texture = assets.get("Img/uvas.png", Texture.class);
                break;
            case Pineapple:
                texture = assets.get("Img/anana.png", Texture.class);
                break;
            case Strawberry:
                texture = assets.get("Img/frutilla.png", Texture.class);
                break;
            case Watermelon:
                texture = assets.get("Img/sandia.png", Texture.class);
                break;
        }
        sprite = new Sprite(texture);

        // configuramos el sprite
        float ratio = texture.getWidth()/(float)texture.getHeight();
        // notar que el orden del setSize y setOrigin es importante
        sprite.setSize(height * ratio, height);
        sprite.setOriginCenter();
        sprite.setPosition(0,0);

        // inicializamos el estado de la fruta
        velocity = new Vector2(0,0);
        position = new Vector2(0,0);
    }

    /**
     * Actualiza la posicion de la fruta
     */
    public void update()
    {
        // Symplectic Euler
        velocity = velocity.mulAdd(WorldConfig.gravity, Gdx.graphics.getDeltaTime());
        position = position.mulAdd(velocity, Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        //Gdx.app.log("Position", position.toString());
        //Gdx.app.log("Velocity", velocity.toString());
    }

    /**
     * Dibuja la fruta
     * @param batch El spritebatch a utilizar
     */
    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    /**
     * Devuelve el ancho de la fruta
     * @return El ancho en unidades del mundo
     */
    public float getWidth()
    {
        return sprite.getWidth();
    }

    /**
     * Devuelve el alto de la fruta
     * @return El alto en unidades del mundo
     */
    public float getHeight()
    {
        return sprite.getHeight();
    }

    /**
     * Devuelve la coordenada x de la fruta
     * @return La coordenada x en unidades del mundo
     */
    public float getX()
    {
        return position.x;
    }

    /**
     * Devuelve la coordenada y de la fruta
     * @return La coordenada y en unidades del mundo
     */
    public float getY()
    {
        return position.y;
    }

    /**
     * Establece la posicion de la fruta
     * @param x La coordenada x en unidades del mundo
     * @param y La coordenada y en unidades del mundo
     */
    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;
    }

    /**
     * Dice si el punto que se recibe esta sobre la fruta
     * @param x La coordenada x en unidades del mundo para probar
     * @param y La coordenada y en unidades del mundo para probar
     * @return
     */
    public boolean isHitting(float x, float y)
    {
        return sprite.getBoundingRectangle().contains(x, y);
    }
    //endregion

    //region Propiedades privadas
    /**
     * La velocidad de la fruta en unidades del mundo
      */
    private Vector2 velocity;

    //
    /**
     * La posicion de la fruta en unidades del mundo
     */
    private Vector2 position;

    /**
     * El sprite para dibujar la fruta
     */
    private Sprite sprite;
    //endregion

}
