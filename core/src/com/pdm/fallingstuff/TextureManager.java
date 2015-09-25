package com.pdm.fallingstuff;

/**
 * Created by erojasfredini on 25/09/15.
 */
public class TextureManager {
    private static TextureManager ourInstance = new TextureManager();

    public static TextureManager getInstance() {
        return ourInstance;
    }

    private TextureManager() {
    }
}
