package io.github.teamfractal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MiniGameTest extends TesterFile {

	private MiniGame miniGame;

	/**
	 * Reset market to its default status.
	 */
	@Before
	public void Contractor() {
		miniGame = new MiniGame();

	}

	@Test
	public void minGameShouldShowBooleanIfWinningTheGame() {
		// assertEquals(true, miniGame.WinGame(3));
		assertEquals(0, miniGame.getPrice(false));
	}
}
