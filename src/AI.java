import java.awt.Point;

public class AI {
	private static int N = 3;

	/**
	 * Get the score of the passed board and returns 10 if target player can win,
	 * -10 if target player may lose
	 */
	private static int getScore(Board board, int target_player, int current_player) {
		int score = 0;

		if (board.playerWins(board.getOpponent(target_player)))
			score = -10;
		else if (board.playerWins(target_player))
			score = 10;
		else if (!board.isDraw()) { // Calculate scores for all sub boards
			score = target_player == current_player ? Integer.MIN_VALUE : Integer.MAX_VALUE;

			int tmpscore;
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					if (board.isEmptyCell(i, j)) {
						Board temp_board = board.getCopy();
						temp_board.play(i, j, current_player);
						tmpscore = getScore(temp_board, target_player, board.getOpponent(current_player));

						if (target_player == current_player && tmpscore > score)
							score = tmpscore;
						else if (target_player != current_player && tmpscore < score)
							score = tmpscore;
					}
		}

		return score;
	}

	public static Point getBestPlay(Board board, int target_player) {
		Point win_point = null;
		Point draw_point = null;
		Point lose_point = null;

		int score;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (board.isEmptyCell(i, j)) {
					Board temp_board = board.getCopy();
					temp_board.play(i, j, target_player);
					score = getScore(temp_board, target_player, temp_board.getOpponent(target_player));

					if (score > 0)
						win_point = new Point(i, j);
					else if (score == 0)
						draw_point = new Point(i, j);
					else
						lose_point = new Point(i, j);
				}

		// Select the best move to be the first one available from a win move, draw
		// move and lose move
		Point best_point = null;
		if (win_point != null)
			best_point = win_point;
		else if (draw_point != null)
			best_point = draw_point;
		else if (lose_point != null)
			best_point = lose_point;

		return best_point;
	}
}