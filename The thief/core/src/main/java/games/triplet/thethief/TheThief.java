package games.triplet.thethief;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.SceneVO;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import games.triplet.thethief.script.LockpickMinigame;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TheThief extends ApplicationAdapter {

	public SceneLoader mSceneLoader;

	private Viewport mViewport;
	private OrthographicCamera mCamera;
	private PooledEngine mEngine;
	private SceneVO sceneVO;

	@Override
	public void create() {
		mEngine = new PooledEngine();
		mCamera = new OrthographicCamera();
		mViewport = new FitViewport(800, 480, mCamera);

		//Initialize HyperLap2D's SceneLoader, all assets will be loaded here
		mSceneLoader = new SceneLoader();
		//Load the desired scene with custom viewport
		mSceneLoader.loadScene("MainScene", mViewport);
		sceneVO = mSceneLoader.getSceneVO();

	}

	@Override
	public void render() {
		mCamera.update(); //Update camera

		//Clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Apply ViewPort and update SceneLoader's ECS engine
		mViewport.apply();
		mSceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

		if((Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) && (sceneVO.sceneName.equals("MainScene"))){
			mSceneLoader.loadScene("MinigameScene", mViewport);
			sceneVO = mSceneLoader.getSceneVO();
			ItemWrapper miniGameScene = new ItemWrapper(mSceneLoader.getRoot());
			ItemWrapper bar = miniGameScene.getChild("contactor");
			LockpickMinigame key = new LockpickMinigame(miniGameScene.getChild("Box").getEntity(), miniGameScene.getChild("key").getEntity(), miniGameScene.getChild("progress_bar").getEntity());
			bar.addScript(key, mEngine);
		}

		if((LockpickMinigame.isSuccess) && (sceneVO.sceneName.equals("MinigameScene"))){
			mSceneLoader.loadScene("GameScene", mViewport);
			sceneVO = mSceneLoader.getSceneVO();
			ItemWrapper GameScene = new ItemWrapper(mSceneLoader.getRoot());
		}

	}

	@Override
	public void resize(int width, int height) {
		mViewport.update(width, height);
		if (width != 0 && height != 0)
			mSceneLoader.resize(width, height);
	}

	@Override
	public void dispose() {
		mSceneLoader.dispose();
	}
}