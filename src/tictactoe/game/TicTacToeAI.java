package tictactoe.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tictactoe.alphabeta.AlphaBeta;

public class TicTacToeAI extends AlphaBeta {
	private char [][]grid;
	private char token;
	private int depth;
	
	private final static int n = 3;
	private static final char xToken = 'X';
	private static final char oToken = 'O';
	private static final char empty = '\0';
	private static final int maxDepth = 9;
	
	public TicTacToeAI(char token) {
		minValue = -maxDepth;
		maxValue = maxDepth;
		grid = new char[n][];
		for (int i = 0; i < n; i++) {
			grid[i] = new char[n];
			for (int j = 0; j < n; j++) {
				grid[i][j] = empty;
			}
		}
		
		if (token == xToken || token == oToken) {
			this.token = token;
		} else {
			this.token = oToken;
		}
		
		depth = 0;
	}
	
	public TicTacToeAI() {
		this(oToken);
	}
	
	public void setGrid(char [][]grid) {
		this.grid = grid;
	}
	
	public TicTacToeAI clone() {
		TicTacToeAI aiGrid = new TicTacToeAI(token);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				aiGrid.grid[i][j] = grid[i][j];
			}
		}
		aiGrid.token = token;
		aiGrid.depth = depth;
		
		return aiGrid;
	}
	
	public AlphaBetaScore randomMove() {
		List<AlphaBeta> children = expand();
		Random rand = new Random();
		int index = rand.nextInt(children.size());
		return new AlphaBetaScore(0, children.get(index));
	}

	@Override
	public List<AlphaBeta> expand() {
		List<AlphaBeta> children = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == empty) {
					TicTacToeAI child = clone();
					child.token = token == xToken? oToken : xToken;
					child.grid[i][j] = token;
					child.depth = depth + 1;
					children.add(child);
				}
			}
		}
		
		return children;
	}

	@Override
	public boolean isLeaf() {
		if (winnerExists()) {
			estimate = depth - (maxDepth + 1);
			return true;
		} else {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (grid[i][j] == empty) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private int estimate = 0;

	@Override
	public int estimate() {
		return estimate;
	}
	
	private boolean winnerExists() {
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
	
	public char[][] getGrid() {
		return grid;
	}
}
