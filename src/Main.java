import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		final Game game = new Game(Board.PLAYER1, Board.X_SYMBOL, false);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameForm(game).showForm();
			}
		});
	}
}