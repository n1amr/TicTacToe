import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameForm extends JFrame {
	public GameForm() {
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 20);
		setLayout(flowLayout);

		JButton button = new JButton("Press");
		JLabel label = new JLabel("Hello World");

		add(label);
		add(button);

	}

	void showForm() {
		setSize(1000, 1000);
		pack();

		setLocationRelativeTo(null); // Center the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
