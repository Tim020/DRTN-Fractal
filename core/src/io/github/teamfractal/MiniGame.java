package io.github.teamfractal;

import java.util.Random;
import java.util.Scanner;

public class MiniGame {
	private static final Random rand = new Random();
	public int WinGame(){
		int max = 6;
		int min = 1;
		int generatedNumber = rand.nextInt((max - min) + 1) + min;

		return generatedNumber;
	}
}
