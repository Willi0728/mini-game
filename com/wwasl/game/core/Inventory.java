package com.wwasl.game.core;

import java.util.HashMap;

public class Inventory {
    private HashMap<Item, Double> inventory = new HashMap<>();
    public double capacity = 1024;
    public HashMap<Item, Double> getInventory() {
        return inventory;
    }
    public void setInventory(HashMap<Item, Double> inventory) {
        this.inventory = inventory;
    }
    public double getAmtOfItem(Item item) {
        return inventory.get(item);
    }
    public void setAmtOfItem(Item item, double amt) {
        inventory.put(item, amt);
    }
    public boolean removeItem(Item item, double amt) {
        if (getAmtOfItem(item) < amt) return false;
        setAmtOfItem(item, getAmtOfItem(item) + amt);
        return true;
    }
    public void addItem(Item item, double amt) {
        setAmtOfItem(item, getAmtOfItem(item) + amt);
    }
}
