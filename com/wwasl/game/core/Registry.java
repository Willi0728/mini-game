package com.wwasl.game.core;

import java.util.HashMap;

public class Registry {
    HashMap registry;
    public <T> Registry(Class<T> tClass) {
        registry = new HashMap<T, Integer>();
    }
}
