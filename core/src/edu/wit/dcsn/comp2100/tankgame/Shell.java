package edu.wit.dcsn.comp2100.tankgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shell extends Sprite {

	public static Texture texture = new Texture("shell.png");
	private boolean destroyMe;
	
	public Shell() {
		super(texture);
		setOriginCenter();
		destroyMe = false;
	}
	
	public void update() {
		float rotation = this.getRotation() * 0.01745329f;
		this.translateX((float)Math.sin(rotation)*-5f);
		this.translateY((float)Math.cos(rotation)*5f);
	}
}
