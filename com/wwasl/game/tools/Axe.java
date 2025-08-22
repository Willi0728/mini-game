package com.wwasl.game.tools;

import com.wwasl.game.Brush;
import com.wwasl.game.Player;
import com.wwasl.game.Tool;
import com.wwasl.game.Vertex;

import java.awt.*;

public class Axe implements Tool {

    @Override
    public Brush compute(Player player) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void leftClick(Vertex v) {

    }

    @Override
    public void rightClick(Vertex v) {

    }

    @Override
    public Image getImage() {
        return null;
    }
}
