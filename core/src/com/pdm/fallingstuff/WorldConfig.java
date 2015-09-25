package com.pdm.fallingstuff;

/**
 * Created by erojasfredini on 25/09/15.
 */
public class WorldConfig {
    private static WorldConfig ourInstance = new WorldConfig();

    public static WorldConfig getInstance() {
        return ourInstance;
    }

    private WorldConfig() {
    }
}
