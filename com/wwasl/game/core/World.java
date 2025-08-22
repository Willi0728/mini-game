package com.wwasl.game.core;

import com.wwasl.game.geo.Triangle;
import com.wwasl.game.geo.Vertex;

import java.util.ArrayList;
public class World {
    ArrayList<Triangle> triangles = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    public World() {
        for (float i = -49.5f; i <= 49.5f; i++) {
            for (float j = -49.5f; i <= 49.5f; i++) {
                triangles.add(new Triangle(
                        new Vertex(i - .5f, 0, j - .5f),
                        new Vertex(i + .5f, 0, j + .5f),
                        new Vertex(i - .5f, 0, j + .5f)
                ));
                triangles.add(new Triangle(
                        new Vertex(i - .5f, 0, j - .5f),
                        new Vertex(i + .5f, 0, j + .5f),
                        new Vertex(i + .5f, 0, j - .5f)
                ));
            }
        }
    }
}