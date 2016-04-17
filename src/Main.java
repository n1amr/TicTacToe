import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Hello");
		Board board = new Board();

		// board.playX(0, 1);
		// board.playX(1, 1);
		// board.playX(2, 2);
		//
		// board.playO(0, 0);
		// board.playO(0, 2);
		// board.playO(1, 0);
		// board.playO(1, 2);

		// char[][] data = {
		// { 'x', '.', '.' },
		// { '.', 'x', '.' },
		// { '.', '.', 'o' } };
		// board.data = data;
		// System.out.println(board.getScore(board.O, board.O, 0, 0));
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 3; j++) {
		// if (board.data[i][j] == board.EMPTY) {
		// Board board2 = board.getCopy();
		// board2.data[i][j] = board.O;
		// board2.print();
		// System.out.println(board2.getScore(board.O, board.O, 0, 0));
		// }
		// }
		// }

		char player = Board.X;
		System.out.println(board);

		while (!board.gameFinished()) {
			int i = in.nextInt() - 1;
			int j = in.nextInt() - 1;
			board.play(i, j, player);
			System.out.println(board);

			board = board.playSmart(board.O);
			System.out.println(board);
		}
		if (board.playerWins(board.X))
			System.out.println("X wins");
		else if (board.playerWins(board.O))
			System.out.println("O wins");
		else
			System.out.println("Draw");
		// Controller controller = new Controller();
		// printArr(controller.gameXO);

		// printArr(controller.gameXO);
		// controller.makeMove(4);
		// printArr(controller.gameXO);
		// controller.makeMove(0);
		// printArr(controller.gameXO);
		// controller.makeMove(0);
		// printArr(controller.gameXO);
		// controller.makeMove(0);
		// printArr(controller.gameXO);
	}

	public static void printArr(String[] arr) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				char c = arr[i * 3 + j].charAt(0);
				if (c == ' ')
					System.out.print(".");
				else
					System.out.print(c);
			}
			System.out.println();
		}
	}
}