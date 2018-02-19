package edu.wit.dcsn.comp2100.tankgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tank extends Sprite {
	
	public static Texture texture = new Texture("tank.png");
	
	public Shell shell;
	public Turret turret;
	
	private boolean destroyed;
	
	public Tank() {
		super(texture);
		setOriginCenter();
		destroyed = false;
		
		turret = new Turret();
		turret.setPosition(this.getX(), this.getY());
	}
	
	public void update() {
		if (shell != null) {
			shell.update();
		}
	}
	
	public boolean canFire() {
		return shell == null;
	}
	
	public void fire() {
		shell = new Shell();
		shell.setPosition(this.getX(), this.getY());
		float rotation = turret.getRotation() * 0.01745329f;
		shell.translateX((float)Math.sin(rotation)*-32f);
		shell.translateY((float)Math.cos(rotation)*32f);
		shell.setRotation(turret.getRotation());
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	@Override
	public void draw(Batch batch) {
		if (!destroyed) {	
			super.draw(batch);
			turret.draw(batch);
			if (shell != null) {
				shell.draw(batch);
			}
		}
	}
	
	@Override
	public void rotate(float degrees) {
		super.rotate(degrees);
		turret.rotate(degrees);
	}
	
	@Override
	public void translateX(float x) {
		super.translateX(x);
		turret.translateX(x);
	}
	
	@Override
	public void translateY(float y) {
		super.translateY(y);
		turret.translateY(y);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		turret.setPosition(x, y);
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		turret.setX(x);
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		turret.setY(y);
	}
}
