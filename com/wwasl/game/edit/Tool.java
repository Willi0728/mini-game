package com.wwasl.game.edit;

import com.wwasl.game.core.Player;
import com.wwasl.game.geo.Vertex;

public interface Tool {
    Brush compute(Player player, Vertex v);
}
