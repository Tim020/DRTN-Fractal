package io.github.teamfractal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
		int value = miniGame.WinGame();
		int min = 1;
		int max = 6;
		assertTrue("Generated number is out of range: " + value, min <= value && value <= max);
	}
}
