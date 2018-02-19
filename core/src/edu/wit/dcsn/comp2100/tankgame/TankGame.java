package edu.wit.dcsn.comp2100.tankgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TankGame extends Game {
	
	GameScreen gameScreen;
	
	SpriteBatch batch;
	BitmapFont font;
	Stage stage;
	HostNetwork hostNetwork;
	ClientNetwork clientNetwork;
	
	long startTime;
	int elapsedTime;
	int lastReceivedTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage(new FitViewport(1080, 720));
		font = new BitmapFont(Gdx.files.internal("basic.fnt"), false);
		startTime = System.currentTimeMillis();
		elapsedTime = 0;
		lastReceivedTime = 0;
		
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		elapsedTime = (int)(System.currentTimeMillis() - startTime);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		Tank.texture.dispose();
		Turret.texture.dispose();
		Shell.texture.dispose();
		
		if (hostNetwork != null ) {
			hostNetwork.close();
		}
		if (clientNetwork != null ) {
			clientNetwork.close();
		}
	}
}
