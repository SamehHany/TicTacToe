package tictactoe.alphabeta;

import java.util.Iterator;
import java.util.List;

public abstract class AlphaBeta implements Iterable<AlphaBeta> {
	protected int minValue = Integer.MIN_VALUE;
	protected int maxValue = Integer.MAX_VALUE;

	public AlphaBetaScore alphaBeta() {
		return alphaBeta(minValue, maxValue);
	}
	
	public AlphaBetaScore alphaBeta(int alpha, int beta) {
		if (isLeaf())
			return new AlphaBetaScore(estimate(), null);
		List<AlphaBeta> children = expand();
		AlphaBeta winningChild = null;
		for (AlphaBeta child : children) {
			AlphaBetaScore alphaBetaScore = child.alphaBeta(-beta, -alpha).negate();
			int score = alphaBetaScore.getScore();
			if (score >= beta)
				return new AlphaBetaScore(beta, child); // fail
																				// hard
																				// beta-cutoff
			if (score > alpha) {
				alpha = score; // alpha acts like max in MiniMax
				winningChild = child;
			}
		}
		return new AlphaBetaScore(alpha, winningChild);
	}

	public abstract List<AlphaBeta> expand();

	@Override
	public Iterator<AlphaBeta> iterator() {
		return expand().iterator();
	}

	public abstract boolean isLeaf();

	public abstract int estimate();

	public static class AlphaBetaScore {
		private int score;
		private AlphaBeta newState;

		public AlphaBetaScore(int score, AlphaBeta newState) {
			this.score = score;
			this.newState = newState;
		}

		public int getScore() {
			return score;
		}

		public AlphaBeta getNewState() {
			return newState;
		}

		public AlphaBetaScore negate() {
			AlphaBetaScore ret = new AlphaBetaScore(score, newState);
			ret.score = -ret.score;
			return ret;
		}
	}
}
