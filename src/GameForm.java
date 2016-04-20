import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameForm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Game state variables
	private GameController gameController;
	private boolean multiplayer;
	private int currentPlayer = Board.PLAYER1;

	// GUI components
	private JButton[][] gridButtons = new JButton[3][3];
	private JLabel resultLabel;
	private JButton startMultiPlayerButton;
	private JButton startSinglePlayerButton;
	private JButton exitButton;

	public GameForm() {
		setTitle("Tic Tac Toe");
		setResizable(false);

		gameController = new GameController(Board.PLAYER1, Board.X_SYMBOL, false);

		// Game result label
		resultLabel = new JLabel("None", SwingConstants.CENTER);
		resultLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		resultLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Control Buttons
		startSinglePlayerButton = new JButton("Start Singleplayer");
		startSinglePlayerButton.addActionListener(this);

		startMultiPlayerButton = new JButton("Start Multiplayer");
		startMultiPlayerButton.addActionListener(this);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);

		// Grid buttons
		GridLayout gridLayout = new GridLayout(3, 3, 0, 0);
		JPanel gridPanel = new JPanel(gridLayout);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				gridButtons[i][j] = new JButton();
				gridButtons[i][j].addActionListener(this);
				gridButtons[i][j].setFont(new Font(Font.MONOSPACED, 0, 25));
				gridButtons[i][j].setBackground(Color.WHITE);
				gridPanel.add(gridButtons[i][j]);
			}

		// Packing panels
		GridLayout mainLayout = new GridLayout(1, 0, 1, 1);
		setLayout(mainLayout);

		GridLayout controlsLayout = new GridLayout(4, 1, 0, 5);
		JPanel controlsPanel = new JPanel(controlsLayout);
		controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		controlsPanel.add(resultLabel);
		controlsPanel.add(startSinglePlayerButton);
		controlsPanel.add(startMultiPlayerButton);
		controlsPanel.add(exitButton);

		add(controlsPanel);
		add(gridPanel);

		resetView();
		updateResult();
	}

	void showForm() {
		pack();

		// Show form on the center
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Invoked when an action occurs.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Get the GUI component of the event
		Object src = e.getSource();

		if (src instanceof JButton) {
			// Get the clicked button
			JButton button = (JButton) src;

			if (button == startSinglePlayerButton)
				startNewGame(false); // Start new a non-multiplayer game
			else if (button == startMultiPlayerButton)
				startNewGame(true); // Start new a multiplayer game
			else if (button == exitButton)
				System.exit(0);
			else
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++)
						if (button == gridButtons[i][j]) {
							play(button, i, j);
							return;
						}
		}
	}

	/**
	 * Starts a new game
	 *
	 * @param multiplayer</br>
	 *          true: for multi player game </br>
	 *          false: for single player game
	 */
	private void startNewGame(boolean multiplayer) {
		this.multiplayer = multiplayer;
		resetView();
	}

	private void play(JButton button, int i, int j) {
		if (multiplayer)
			multiPlayerPlay(button, i, j);
		else
			singlePlayerPlay(button, i, j);
	}

	private void multiPlayerPlay(JButton button, int i, int j) {
		boolean validPlay = gameController.play(i, j, currentPlayer);
		if (validPlay) {
			button.setText("" + gameController.getBoard().getPlayerSymbol(currentPlayer));
			currentPlayer = gameController.getBoard().getOpponent(currentPlayer);

			updateResult();
		}
	}

	private void singlePlayerPlay(JButton button, int i, int j) {
		boolean validPlay = gameController.play(i, j, Board.PLAYER1);
		if (validPlay) {
			button.setText("" + gameController.getBoard().getPlayerSymbol(currentPlayer));
			updateResult();

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Point p = AI.getBestPlay(gameController.getBoard(), Board.PLAYER2);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (p != null) {
						boolean validPlay2 = gameController.play(p, Board.PLAYER2);
						if (validPlay2)
							gridButtons[(int) p.getX()][(int) p.getY()].setText("" + gameController.getBoard().getPlayerSymbol(Board.PLAYER2));
						updateResult();
					}
				}
			});
			thread.start();
		}
	}

	private void updateResult() {
		boolean buttonEnabled = gameController.getGameState() == GameController.UNFINISHED;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setEnabled(buttonEnabled);

		resultLabel.setText(gameController.getStatus());

	}

	private void resetBoard() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setText("" + Board.EMPTY);
	}

	public void resetView() {
		gameController.resetGame(Board.PLAYER1, multiplayer);
		resetBoard();
		updateResult();
	}
}
