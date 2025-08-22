package com.wwasl.game.core;

import com.wwasl.game.geo.Vertex;

public class Player {
    Inventory inventory = new Inventory();
    Vertex position = new Vertex(0.0f, 0.0f, 0.0f);
    public Player() {}
}