package tictactoe.game;

import java.util.Random;
import java.util.Scanner;

import tictactoe.alphabeta.AlphaBeta;

public class Game {
	static int n = 3;
	static char[][] grid = new char[n][];
	static char empty = '\0';

	static {
		for (int i = 0; i < n; i++) {
			grid[i] = new char[n];
			for (int j = 0; j < n; j++) {
				grid[i][j] = '\0';
			}
		}
		
		//grid[0][1] = 'O';
		//grid[1][0] = 'O';
	}

	public static void main(String args[]) {
		TicTacToeAI ai = new TicTacToeAI();
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
		while (true) {
			printGrid();
			int i = sc.nextInt();
			int j = sc.nextInt();
			i--;
			j--;
			if (grid[i][j] != '\0') {
				System.out.println("Cannot play here");
				continue;
			}
			grid[i][j] = 'X';
			boolean won = true;
			for (int x = 0; x < n; x++) {
				if (grid[i][x] != grid[i][j]) {
					won = false;
					break;
				}
			}
			if (won) {
				System.out.println("You won");
				break;
			}

			won = true;
			for (int x = 0; x < n; x++) {
				if (grid[x][j] != grid[i][j]) {
					won = false;
					break;
				}
			}
			if (won) {
				System.out.println("You won");
				break;
			}

			won = true;
			for (int x = 0; x < n; x++) {
				if (grid[x][x] != grid[i][j]) {
					won = false;
					break;
				}
			}
			if (won) {
				System.out.println("You won");
				break;
			}

			won = true;
			for (int x = 0; x < n; x++) {
				int y = n - x - 1;
				if (grid[x][y] != grid[i][j]) {
					won = false;
					break;
				}
			}
			if (won) {
				System.out.println("You won");
				break;
			}
			
			if (gameEnded()) {
				System.out.println("Draw");
				break;
			}

			ai.setGrid(grid);
			AlphaBeta.AlphaBetaScore score = ai.alphaBeta();
			grid = ((TicTacToeAI) score.getNewState()).getGrid();
			//if (score.getScore() > 0) {
			if (winnerExists()) {
				System.out.println("You lost");
				break;
			}

			if (gameEnded()) {
				System.out.println("Draw");
				break;
			}
		}

		printGrid();
	}

	public static boolean gameEnded() {
		boolean end = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == '\0') {
					end = false;
					break;
				}
			}
			if (!end) {
				break;
			}
		}
		
		return end;
	}

	public static void printGrid() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j != 0) {
					System.out.print("|");
				}
				if (grid[i][j] == '\0') {
					System.out.print(" ");
				} else {
					System.out.print(grid[i][j]);
				}
			}
			System.out.println();
		}
	}
	
	private static boolean winnerExists() {
		for (int i = 0; i < n; i++) {
			boolean isWinner = true;
			for (int j = 1; j < n; j++) {
				if (grid[i][j] == empty) {
					isWinner = false;
					break;
				}
				if (grid[i][j] != grid[i][j-1]) {
					isWinner = false;
					break;
				}
			}
			
			if (isWinner) {
				return isWinner;
			}
		}
		
		for (int j = 0; j < n; j++) {
			boolean isWinner = true;
			for (int i = 1; i < n; i++) {
				if (grid[i][j] == empty) {
					isWinner = false;
					break;
				}
				if (grid[i][j] != grid[i-1][j]) {
					isWinner = false;
					break;
				}
			}
			
			if (isWinner) {
				return isWinner;
			}
		}
		
		boolean isWinner = true;
		for (int i = 1; i < n; i++) {
			if (grid[i][i] == empty) {
				isWinner = false;
				break;
			}
			
			if (grid[i][i] != grid[i-1][i-1]) {
				isWinner = false;
				break;
			}
		}
		if (isWinner) {
			return isWinner;
		}
		
		isWinner = true;
		for (int i = 1; i < n; i++) {
			int j = n - i - 1;
			if (grid[i][j] == empty) {
				isWinner = false;
				break;
			}
			
			if (grid[i][j] != grid[i-1][j+1]) {
				isWinner = false;
				break;
			}
		}
		if (isWinner) {
			return isWinner;
		}
		
		return false;
	}
}
