package main;

import java.awt.Graphics;

public abstract class GameObject { //everything in this class is inherited by classes that extends it

//cities have an x and y value
protected int x,y;
protected int population;

//id of objects within the game such as cities/gold

public abstract void tick();
public abstract void render(Graphics g);


}