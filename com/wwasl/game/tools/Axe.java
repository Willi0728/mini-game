package com.wwasl.game.tools;

import com.wwasl.game.edit.Brush;
import com.wwasl.game.core.Player;
import com.wwasl.game.edit.Tool;
import com.wwasl.game.geo.Vertex;

public class Axe implements Tool {
    @Override
    public Brush compute(Player player, Vertex v) {
        return new Brush.Builder()
                .shape(Brush.Shape.WEDGE)
                .energy(10)
                .materialId(0)
                .op(Brush.Operation.SUB)
                .center(v)
                .build();
    }
}
