import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameForm extends JFrame implements ActionListener {
	private JButton[][] gridButtons = new JButton[3][3];
	private GameController gameController;
	private JLabel resultLabel;

	public GameForm(GameController gameController) {
		this.gameController = gameController;
		this.setTitle("Tic Tac Toe");
		this.setResizable(false);

		GridLayout mainLayout = new GridLayout(1, 0, 10, 0);
		setLayout(mainLayout);

		GridLayout controlsLayout = new GridLayout(3, 1, 0, 5);
		JPanel controlsPanel = new JPanel(controlsLayout);
		JButton resetButton = new JButton("Reset");
		JButton exitButton = new JButton("Exit");
		resultLabel = new JLabel("None", SwingConstants.CENTER);
		resultLabel.setFont(new Font(Font.MONOSPACED, 0, 14));

		controlsPanel.add(resultLabel);
		controlsPanel.add(resetButton);
		controlsPanel.add(exitButton);

		GridLayout gridLayout = new GridLayout(3, 3, 0, 0);
		JPanel gridPanel = new JPanel(gridLayout);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gridButtons[i][j] = new JButton();
				gridButtons[i][j].addActionListener(this);
				gridButtons[i][j].setFont(new Font(Font.MONOSPACED, 0, 25));
				gridButtons[i][j].setBackground(Color.WHITE);
				gridPanel.add(gridButtons[i][j]);
			}
		}

		add(controlsPanel);
		add(gridPanel);

		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetView();
			}
		});
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		resetView();
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
		if (gameController.isFinished()) {
			return;
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (src == gridButtons[i][j]) {
					boolean validPlay = gameController.play(i, j);
					if (validPlay) {
						((JButton) src).setText("" + Board.X_PLAYER);

						Point p = gameController.respond();
						if (p != null)
							gridButtons[(int) p.getX()][(int) p.getY()].setText("" + Board.O_PLAYER);
						resultLabel.setText(gameController.getStatus());
					}

					return;
				}
			}
		}
	}

	public void resetView() {
		gameController.reset();
		resultLabel.setText("Your turn");
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setText("" + Board.EMPTY);
	}
}
