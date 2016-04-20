public class Board {
	private static int N = 3;

	public static final char EMPTY = ' ';
	public static final char X_SYMBOL = 'x';
	public static final char O_SYMBOL = 'o';

	public static final int PLAYER1 = 0;
	public static final int PLAYER2 = 1;

	private char player1Symbol = X_SYMBOL;
	private char player2Symbol = O_SYMBOL;

	public char[][] data;

	public Board() {
		data = new char[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				data[i][j] = EMPTY;
	}

	/** Deep copy of the board */
	public Board getCopy() {
		Board board = new Board();

		board.data = new char[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				board.data[i][j] = data[i][j];
		return board;
	}

	/** Play for player and returns true if play was valid */
	public boolean play(int i, int j, int player) {
		if (isEmptyCell(i, j)) {
			data[i][j] = getPlayerSymbol(player);
			return true;
		}
		return false;
	}

	public char getPlayerSymbol(int player) {
		return player == PLAYER1 ? player1Symbol : player2Symbol;
	}

	public char getOpponent(int player) {
		if (player == PLAYER1)
			return PLAYER2;
		if (player == PLAYER2)
			return PLAYER1;

		return EMPTY;
	}

	public boolean isEmptyCell(int i, int j) {
		return data[i][j] == EMPTY;
	}

	public boolean isDraw() {
		if (playerWins(PLAYER1) || playerWins(PLAYER2))
			return false;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (isEmptyCell(i, j))
					return false;
		return true;
	}

	/** Checks whether the player has won */
	public boolean playerWins(int player) {
		char playerSymbol = getPlayerSymbol(player);

		// Check rows
		for (int i = 0; i < N; i++) {
			boolean winrow = true;
			for (int j = 0; j < N; j++)
				if (data[i][j] != playerSymbol) {
					winrow = false;
					break;
				}
			if (winrow)
				return true;
		}

		// Check columns
		for (int j = 0; j < N; j++) {
			boolean wincol = true;
			for (int i = 0; i < N; i++)
				if (data[i][j] != playerSymbol) {
					wincol = false;
					break;
				}
			if (wincol)
				return true;
		}

		// Check diagonals
		boolean windiag = true;
		boolean winrevdiag = true;
		for (int i = 0; i < N; i++) {
			if (data[i][i] != playerSymbol)
				windiag = false;

			if (data[i][N - i - 1] != playerSymbol)
				winrevdiag = false;
		}
		return windiag || winrevdiag;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				stringBuilder.append(data[i][j]);
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}
}
