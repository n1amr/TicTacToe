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

	private Game game;

	private JButton[][] gridButtons = new JButton[3][3];
	private JLabel resultLabel;
	private JButton startMultiPlayerButton;
	private JButton startSinglePlayerButton;
	private JButton exitButton;

	private boolean multiplayer;
	private int currentPlayer = Board.PLAYER1;

	public GameForm() {
		game = new Game(Board.PLAYER1, Board.X_SYMBOL, false);

		setTitle("Tic Tac Toe");
		setResizable(false);

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

		setLocationRelativeTo(null); // Center the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == startSinglePlayerButton) {
			startNewGame(false);
		} else if (src == startMultiPlayerButton) {
			startNewGame(true);
		} else if (src == exitButton) {
			System.exit(0);
		} else {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					if (src == gridButtons[i][j]) {
						play(src, i, j);
						return;
					}
		}
	}

	private void startNewGame(boolean multiplayer) {
		this.multiplayer = multiplayer;
		resetView();
	}

	private void play(Object src, int i, int j) {
		if (multiplayer)
			multiPlayerPlay(src, i, j);
		else
			singlePlayerPlay(src, i, j);
	}

	private void multiPlayerPlay(Object src, int i, int j) {
		boolean validPlay = game.play(i, j, currentPlayer);
		if (validPlay) {
			((JButton) src).setText("" + game.getBoard().getPlayerSymbol(currentPlayer));
			currentPlayer = game.getBoard().getOpponent(currentPlayer);

			updateResult();
		}
	}

	private void singlePlayerPlay(Object src, int i, int j) {
		boolean validPlay = game.play(i, j, Board.PLAYER1);
		if (validPlay) {
			((JButton) src).setText("" + Board.X_SYMBOL);
			updateResult();

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Point p = AI.getBestPlay(game.getBoard(), Board.PLAYER2);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (p != null) {
						boolean validPlay2 = game.play(p, Board.PLAYER2);
						if (validPlay2)
							gridButtons[(int) p.getX()][(int) p.getY()].setText("" + Board.O_SYMBOL);
						updateResult();
					}
				}
			});
			thread.start();
		}
	}

	private void updateResult() {
		boolean buttonEnabled = (game.getGameState() == Game.UNFINISHED);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setEnabled(buttonEnabled);

		resultLabel.setText(game.getStatus());

	}

	private void resetBoard() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setText("" + Board.EMPTY);
	}

	public void resetView() {
		game.resetGame(Board.PLAYER1, multiplayer);
		resetBoard();
		updateResult();
	}
}
