package edu.wit.dcsn.comp2100.tankgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Turret extends Sprite {
	
	public static Texture texture = new Texture("turret.png");
	
	public Turret() {
		super(texture);
		setOriginCenter();
	}

}
