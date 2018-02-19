package edu.wit.dcsn.comp2100.tankgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen implements Screen {

	final TankGame game;
	
	GameScreen gameScreen;
	OrthographicCamera camera;
	Button hostButton;
	Button joinButton;
	String hostIp;
	TextField connectToIp;
	
	public MainMenuScreen(final TankGame game) {
		this.game = game;
		
		Texture hostTexture = new Texture(Gdx.files.internal("hostbutton.png"));
		TextureRegion hostTexRegion = new TextureRegion(hostTexture);
		TextureRegionDrawable hostTexRegionDrawable = new TextureRegionDrawable(hostTexRegion);
		hostButton = new Button(hostTexRegionDrawable);
		hostButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				game.hostNetwork = new HostNetwork();
				hostIp = game.hostNetwork.getMyIpAddress();
//				game.gameScreen = new GameScreen(game);
//				game.setScreen(game.gameScreen);
			}
		});
		hostButton.setPosition(400, 500);
		
		Texture joinTexture = new Texture(Gdx.files.internal("joinbutton.png"));
		TextureRegion joinTexRegion = new TextureRegion(joinTexture);
		TextureRegionDrawable joinTexRegionDrawable = new TextureRegionDrawable(joinTexRegion);
		joinButton = new Button(joinTexRegionDrawable);
		joinButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				game.clientNetwork = new ClientNetwork(connectToIp.getText());
				game.clientNetwork.sendStart(game.elapsedTime);
				game.gameScreen = new GameScreen(game, false);
				game.setScreen(game.gameScreen);
			}
		});
		joinButton.setPosition(400, 400);
		
		Texture tfTexture = new Texture(Gdx.files.internal("textfield.png"));
		TextureRegion tfTexRegion = new TextureRegion(tfTexture);
		TextureRegionDrawable tfTexRegionDrawable = new TextureRegionDrawable(tfTexRegion);
		TextField.TextFieldStyle tfs = new TextField.TextFieldStyle();
		tfs.font = game.font;
		tfs.fontColor = Color.BLACK;
		tfs.background = tfTexRegionDrawable;
		connectToIp = new TextField("", tfs);
		connectToIp.setWidth(connectToIp.getWidth()*1.5f);
		connectToIp.setPosition(100, 400);
		
		game.stage.addActor(hostButton);
		game.stage.addActor(joinButton);
		game.stage.addActor(connectToIp);
		Gdx.input.setInputProcessor(game.stage);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1080, 720);
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		if (game.hostNetwork != null) {
			if (game.hostNetwork.receiveStart()) {
				game.gameScreen = new GameScreen(game, true);
				game.setScreen(game.gameScreen);
			}
		}
		
		game.stage.draw();
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Tank Game", 100, 700);
		if (hostIp != null) {
			game.font.draw(game.batch, hostIp, 100, 550);
		}
		game.batch.end();
//		
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

}
