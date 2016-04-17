import java.util.ArrayList;

public class Board {
	public static final char EMPTY = '.';
	public static final char X = 'x';
	public static final char O = 'o';

	private int n;

	public char[][] data;

	public Board() {
		n = 3;
		data = new char[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				data[i][j] = EMPTY;
	}

	public void play(int i, int j, char player) {
		if (data[i][j] == EMPTY)
			data[i][j] = player;
		else
			System.out.println("Error");
	}

	public void playO(int i, int j) {
		play(i, j, O);
	}

	public void playX(int i, int j) {
		play(i, j, X);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				stringBuilder.append(data[i][j]);
			}
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}

	// public String toString(int level) {
	// StringBuilder stringBuilder = new StringBuilder();
	//
	// for (int i = 0; i < n; i++) {
	// for (int j = 0; j < level; j++) {
	// stringBuilder.append(" ");
	// }
	// for (int j = 0; j < n; j++) {
	// stringBuilder.append(data[i][j]);
	// }
	// stringBuilder.append('\n');
	// }
	// return stringBuilder.toString();
	// }

	// public void print() {
	// for (int j = 0; j < n + 2; j++) {
	// System.out.print('-');
	// }
	// System.out.println();
	//
	// for (int i = 0; i < n; i++) {
	// System.out.print('|');
	// for (int j = 0; j < n; j++) {
	// System.out.print(data[i][j]);
	// }
	// System.out.print('|');
	// System.out.println();
	// }
	//
	// for (int j = 0; j < n + 2; j++) {
	// System.out.print('-');
	// }
	// System.out.println();
	//
	// }

	public Board getCopy() {
		Board board = new Board();
		board.n = n;
		board.data = new char[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				board.data[i][j] = data[i][j];
		return board;
	}

	public boolean playerWins(char player) {
		boolean wins = false;
		for (int i = 0; i < n; i++) {
			boolean winrow = true;
			for (int j = 0; j < n; j++) {
				if (data[i][j] != player) {
					winrow = false;
					break;
				}
			}
			if (winrow)
				return true;
		}
		for (int j = 0; j < n; j++) {
			boolean wincol = true;
			for (int i = 0; i < n; i++) {
				if (data[i][j] != player) {
					wincol = false;
					break;
				}
			}
			if (wincol)
				return true;
		}
		boolean windiag = true;
		boolean winrevdiag = true;
		for (int i = 0; i < n; i++) {
			if (data[i][i] != player)
				windiag = false;

			if (data[i][n - i - 1] != player)
				winrevdiag = false;
		}
		return windiag || winrevdiag;
	}

	int MAX = 1;
	int MIN = -1;

	public int getScore(char target_player, char current_player, int min_or_max, int level) {
		int score = 0;

		if (playerWins(getOpponent(target_player))) {
			score = -10;
		} else if (playerWins(target_player)) {
			score = 10;
		} else if (!gameFinished()) {
			if (target_player == current_player)
				score = Integer.MIN_VALUE;
			else
				score = Integer.MAX_VALUE;

			int tmpscore;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (data[i][j] == EMPTY) {
						Board board = this.getCopy();
						board.play(i, j, current_player);
						tmpscore = board.getScore(target_player, getOpponent(current_player), min_or_max, level + 1);

						if (target_player == current_player && tmpscore > score)
							score = tmpscore;
						else if (target_player != current_player && tmpscore < score)
							score = tmpscore;
					}
				}
			}
		}

		return score;
	}

	public Board playSmart(char target_player) {
		Board win_board = null;
		Board tie_board = null;
		Board lose_board = null;

		int score;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (data[i][j] == EMPTY) {
					Board board = this.getCopy();
					board.play(i, j, target_player);
					score = board.getScore(target_player, getOpponent(target_player), 0, 0);
					// System.out.println("i = " + i + " j = " + j + ",, " + score);
					if (score > 0) {
						win_board = board;
						// return win_board;
					} else if (score == 0) {
						tie_board = board;
					} else {
						lose_board = board;
					}
				}
			}
		}
		if (win_board != null)
			return win_board;
		else if (tie_board != null)
			return tie_board;
		else if (lose_board != null)
			return lose_board;
		else
			return this;
	}

	public char getOpponent(char player) {
		if (player == X)
			return O;
		if (player == O)
			return X;

		return EMPTY;
	}

	public boolean gameFinished() {
		return tie() || playerWins(X) || playerWins(O);
	}

	public boolean tie() {
		if (playerWins(X) || playerWins(O)) {
			return true;
		}
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (data[i][j] == EMPTY)
					return false;
		return true;
	}
}
