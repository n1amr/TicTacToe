import java.util.ArrayList;

public class Board {
	public static final char EMPTY = '.';
	public static final char X_PLAYER = 'x';
	public static final char O_PLAYER = 'o';

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
		if (isEmptyCell(i, j))
			data[i][j] = player;
		else
			System.out.println("Error");
	}

	public void playO(int i, int j) {
		play(i, j, O_PLAYER);
	}

	public void playX(int i, int j) {
		play(i, j, X_PLAYER);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				stringBuilder.append(data[i][j]);
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}

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

	public int getScore(char target_player, char current_player, int min_or_max, int level) {
		int score = 0;

		if (playerWins(getOpponent(target_player)))
			score = -10;
		else if (playerWins(target_player))
			score = 10;
		else if (!gameFinished()) {
			score = (target_player == current_player) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

			int tmpscore;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (isEmptyCell(i, j)) {
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

	public Board smartPlay(char target_player) {
		Board tie_board = null;
		Board lose_board = null;

		int score;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (isEmptyCell(i, j)) {
					Board board = this.getCopy();
					board.play(i, j, target_player);
					score = board.getScore(target_player, getOpponent(target_player), 0, 0);

					if (score > 0)
						return board;
					else if (score == 0)
						tie_board = board;
					else
						lose_board = board;
				}
			}
		}

		if (tie_board != null)
			return tie_board;
		else if (lose_board != null)
			return lose_board;
		else
			return null;
	}

	public char getOpponent(char player) {
		if (player == X_PLAYER)
			return O_PLAYER;
		if (player == O_PLAYER)
			return X_PLAYER;

		return EMPTY;
	}

	public boolean gameFinished() {
		return isTie() || playerWins(X_PLAYER) || playerWins(O_PLAYER);
	}

	public boolean isEmptyCell(int i, int j) {
		return data[i][j] == EMPTY;
	}

	public boolean isTie() {
		if (playerWins(X_PLAYER) || playerWins(O_PLAYER))
			return true;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (isEmptyCell(i, j))
					return false;
		return true;
	}

	public char whoWins() {
		if (playerWins(X_PLAYER))
			return X_PLAYER;
		if (playerWins(O_PLAYER))
			return O_PLAYER;
		return EMPTY;
	}

	public static final int X_WINS = 0;
	public static final int O_WINS = 1;
	public static final int DRAW = 2;
	public static final int UNFINISHED = 3;

	public int getStatus() {
		if (playerWins(X_PLAYER))
			return X_WINS;
		if (playerWins(O_PLAYER))
			return O_WINS;
		if (isTie())
			return DRAW;
		return UNFINISHED;
	}
}
