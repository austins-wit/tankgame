package edu.wit.dcsn.comp2100.tankgame;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

	final TankGame game;
	
	LinkedList<Tank> tanks;
	private int myTank;
	
	public GameScreen(final TankGame game, boolean isServer) {
		this.game = game;
		
		tanks = new LinkedList<Tank>();
		tanks.add(new Tank());
		tanks.add(new Tank());
		tanks.get(0).setPosition(10f, 10f);
		tanks.get(1).setPosition(800f, 600f);
		tanks.get(1).rotate(180);
		if (isServer) {
			myTank = 0;
		}
		else {
			myTank = 1;
		}
			
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	private void update() {
		int input = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			float rotation = tanks.get(myTank).getRotation() * 0.01745329f;
			tanks.get(myTank).translateX((float)Math.sin(rotation)*-1f);
			tanks.get(myTank).translateY((float)Math.cos(rotation)*1f);
			input = 1;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			float rotation = tanks.get(myTank).getRotation() * 0.01745329f;
			tanks.get(myTank).translateX((float)Math.sin(rotation)*1f);
			tanks.get(myTank).translateY((float)Math.cos(rotation)*-1f);
			input = 1;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			tanks.get(myTank).rotate(-1f);
			input = 1;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			tanks.get(myTank).rotate(1f);
			input = 1;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			tanks.get(myTank).turret.rotate(1f);
			input = 1;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			tanks.get(myTank).turret.rotate(-1f);
			input = 1;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if (tanks.get(myTank).canFire()) {
				tanks.get(myTank).fire();
				input = 2;
			}
		}
		ByteBuffer bfsend = null;
		ByteBuffer bfrec = null;
		if (input != 0) {
			bfsend = ByteBuffer.allocate(24);
			bfsend.putInt(game.elapsedTime);
			bfsend.putInt(input);
			bfsend.putFloat(tanks.get(myTank).getX());
			bfsend.putFloat(tanks.get(myTank).getY());
			bfsend.putFloat(tanks.get(myTank).getRotation());
			bfsend.putFloat(tanks.get(myTank).turret.getRotation());
			bfsend.rewind();
		}
		if (game.hostNetwork != null) {
			bfrec = game.hostNetwork.receive();
			if (input != 0) {
				game.hostNetwork.send(bfsend);
			}
		}
		else if (game.clientNetwork != null) {
			bfrec = game.clientNetwork.receive();
			if (input != 0) {
				game.clientNetwork.send(bfsend);
			}
		}
		Tank tank = tanks.get(myTank);
		Tank other = tanks.get((myTank + 1) % 2);
		if (bfrec != null) {
			bfrec.rewind();
			int timeReceived = bfrec.getInt();
			if (timeReceived > game.lastReceivedTime) {
				game.lastReceivedTime = timeReceived;
			}
			int msgType = bfrec.getInt();
			switch (msgType) {
			case 0:
				//nothing
				break;
			case 1: // move
				other.setX(bfrec.getFloat());
				other.setY(bfrec.getFloat());
				other.setRotation(bfrec.getFloat());
				other.turret.setRotation(bfrec.getFloat());
				break;
			case 2:
				other.setX(bfrec.getFloat());
				other.setY(bfrec.getFloat());
				other.setRotation(bfrec.getFloat());
				other.turret.setRotation(bfrec.getFloat());
				other.fire();
				break;
			case 3:
				other.destroy();
				break;
			}
		}
		
		tank.update();
		other.update();
		if (tank.shell != null) {
			if (tank.shell.getX() < 0 || tank.shell.getX() > 1080 || tank.shell.getY() < 0 || tank.shell.getY() > 720) {
				tank.shell = null;
			}
			else {	
				Rectangle bbox = other.getBoundingRectangle();
				if (bbox.contains(tank.shell.getX(), tank.shell.getY())) {
					other.destroy();
					tank.shell = null;
				}
			}
		}
		if (other.shell != null) {
			if (other.shell.getX() < 0 || other.shell.getX() > 1080 || other.shell.getY() < 0 || other.shell.getY() > 720) {
				other.shell = null;
			}
			else {	
				Rectangle bbox = tank.getBoundingRectangle();
				if (bbox.contains(other.shell.getX(), other.shell.getY())) {
					tank.destroy();
					other.shell = null;
				}
			}
		}
	}
	
	private void draw() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		for (Tank tank : tanks) {
			tank.draw(game.batch);
		}
		game.batch.end();
	}
}
