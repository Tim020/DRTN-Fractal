/**
 * @author DRTN
 * Team Website with download:
 * https://misterseph.github.io/DuckRelatedFractalProject/
 *
 * This Class contains either modifications or is entirely new in Assessment 3
 *
 * If you are in any doubt a complete changelog can be found here:
 * https://github.com/NotKieran/DRTN-Fractal/compare/Fractal_Initial...development
 *
 * And a more concise report can be found in our Change3 document.
 **/

package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.teamfractal.RoboticonQuest;


public class HomeMainMenu extends Table {
    private static Texture titleTexture = new Texture(Gdx.files.internal("roboticon_images/Duck-Related Roboticon Quest (Small).png"));
    private RoboticonQuest game;
    // UPDATED: New buttons for the new number of players requirement.
    private TextButton btnTwoPlayer;
	private TextButton btnThreePayer;
	private TextButton btnFourPlayer;
	private TextButton btnExit;

	/**
	 * Initialise the Home Menu.
     * UPDATED: Changed buttons for the new amount of players requirement.
	 * @param game    The game object.
	 */
	public HomeMainMenu(RoboticonQuest game) {
		this.game = game;

		// Create UI Components
		final Image imgTitle = new Image();
		imgTitle.setDrawable(new TextureRegionDrawable(new TextureRegion(titleTexture)));
		

		btnTwoPlayer = new TextButton("Two Player Game", game.skin);
		btnThreePayer = new TextButton("Three Player Game", game.skin);
		btnFourPlayer = new TextButton("Four Player Game", game.skin);

		btnExit = new TextButton("Exit", game.skin);

		// Adjust properties.
		btnTwoPlayer.pad(10);
		btnThreePayer.pad(10);
		btnFourPlayer.pad(10);

		btnExit.pad(10);

		// Bind events.
		bindEvents();

		// Add UI Components to table.
		add(imgTitle).pad(5).colspan(3);
		row();

		add(btnTwoPlayer);
		add(btnThreePayer);
		add(btnFourPlayer);
		add(btnExit);

	}

	/**
	 * Bind button events.
     * UPDATED: Changed buttons for the new amount of players requirement.
	 */
	private void bindEvents() {
		btnTwoPlayer.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(game.gameScreen);
				game.gameScreen.newGame(2);
			}
		});


		btnThreePayer.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(game.gameScreen);
				game.gameScreen.newGame(3);
			}
		});

		btnFourPlayer.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(game.gameScreen);
				game.gameScreen.newGame(4);
			}
		});

		btnExit.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}
}
