import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		final GameController gameController = new GameController();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameForm(gameController).showForm();
			}
		});
	}
}