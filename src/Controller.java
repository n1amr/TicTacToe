import java.util.ArrayList;

public class Controller {
	// gameXO is the game
	static String[] gameXO = new String[9];
	// _sdepth is used to control the depth
	static int sdepth;

	/**
	 * Step to do in this method 1- update gameXO. put X in the gameXO[index] 2-
	 * test if game is finished (draw or X win) 3- call MinMax algorithm and
	 * return the score and return the best position for O 4- update gameXO. put O
	 * in its position 5- test if game is finished (draw or O win)
	 *
	 * @return -1 to know that player X wins </br>
	 *         -2 to know that the game is draw
	 *
	 */
	public int makeMove(int index) {
		// 1
		gameXO[index] = "X";
		// 2
		if (gameOver(gameXO))
			return -1;
		if (drawGame(gameXO))
			return -2;

		// 3
		ResultMM res = MinMax(gameXO, "MAX", 0, 0);
		int i = res.getIntrus();

		// 4
		gameXO[i] = "O";

		// 5
		// return i+20 to know that o wins (i used this method for programming
		// issues)
		// retrun i-30 to know that the game is draw (i used this method for
		// programming issues)
		if (gameOver(gameXO))
			return i + 20;
		if (drawGame(gameXO))
			return i - 30;

		return i;
	}

	/**
	 * MinMax algorithm 1- generate successor 2- if no successor or game is
	 * finished return score 3- if there is successor a) apply MinMax for each
	 * successor b) after recursive call, i return the good score
	 */
	public ResultMM MinMax(String[] demo, String level, int fils, int depth) {
		// 1---------------
		ArrayList<String[]> children = genere_succ(demo, level);
		// 2------------------
		if (children == null && sdepth != -1) {
			sdepth = -1;
			depth = depth + 1;
		}

		if (children == null || gameOver(demo))
			return new ResultMM(demo, getScore(demo), depth);
		else {// 3------------------
			if (sdepth > children.size()) {
				sdepth = children.size();
				depth = depth + 1;
			}

			ArrayList<ResultMM> listScore = new ArrayList<ResultMM>();
			// pass into each child
			for (int i = 0; i < children.size(); i++)
				listScore.add(MinMax(children.get(i), inverse(level), 1, depth + 1));
			// 3 b)----------------
			ResultMM res = getResult(listScore, level);
			if (fils == 1)
				res.updateMatrix(demo);

			return res;
		}
	}

	/**
	 * this method is used to get the appropriate score if level is MAX, i search
	 * for the higher score in the nearer depth if level is MIN, i search for the
	 * lowest score in the nearer depth
	 */
	public ResultMM getResult(ArrayList<ResultMM> listScore, String level) {
		ResultMM result = listScore.get(0);
		if (level.equals("MAX")) {
			for (int i = 1; i < listScore.size(); i++)
				if (listScore.get(i).getScore() > result.getScore() || listScore.get(i).getScore() == result.getScore() && listScore.get(i).depth < result.depth)
					result = listScore.get(i);
		} else
			for (int i = 1; i < listScore.size(); i++)
				if (listScore.get(i).getScore() < result.getScore() || listScore.get(i).getScore() == result.getScore() && listScore.get(i).depth < result.depth)

					result = listScore.get(i);
		return result;
	}

	/**
	 * generate successor if level is MAX, generate successor with o ( o in
	 * lowerCase) if level is MIN, generate successor with x ( x in lowerCase) if
	 * demo has no successor, return null
	 */
	public ArrayList<String[]> genere_succ(String[] demo, String level) {
		ArrayList<String[]> succ = new ArrayList<String[]>();
		for (int i = 0; i < demo.length; i++)
			if (demo[i].equals(" ")) {
				String[] child = new String[9];
				for (int j = 0; j < 9; j++)
					child[j] = demo[j];

				if (level.equals("MAX"))
					child[i] = "o";
				else
					child[i] = "x";
				succ.add(child);
			}
		return succ.size() == 0 ? null : succ;
	}

	/** reInitialise the gameXO when i create a new controller */
	public Controller() {
		for (int i = 0; i < 9; i++)
			gameXO[i] = " ";
	}

	/** inverse level from MIN to MAX */
	public String inverse(String level) {
		return level.equals("MIN") ? "MAX" : "MIN";
	}

	/** if X win return -1; if O win return 1; else return 0, this mean draw */
	public int getScore(String[] demo) {
		if (demo[0].equalsIgnoreCase("x") && demo[1].equalsIgnoreCase("x") && demo[2].equalsIgnoreCase("x") || demo[3].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[5].equalsIgnoreCase("x") || demo[6].equalsIgnoreCase("x") && demo[7].equalsIgnoreCase("x") && demo[8].equalsIgnoreCase("x") || demo[0].equalsIgnoreCase("x") && demo[3].equalsIgnoreCase("x") && demo[6].equalsIgnoreCase("x") || demo[1].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[7].equalsIgnoreCase("x") || demo[2].equalsIgnoreCase("x") && demo[5].equalsIgnoreCase("x") && demo[8].equalsIgnoreCase("x") || demo[0].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[8].equalsIgnoreCase("x") || demo[2].equalsIgnoreCase("x") && demo[4].equalsIgnoreCase("x") && demo[6].equalsIgnoreCase("x"))
			return -1;

		if (demo[0].equalsIgnoreCase("o") && demo[1].equalsIgnoreCase("o") && demo[2].equalsIgnoreCase("o") || demo[3].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[5].equalsIgnoreCase("o") || demo[6].equalsIgnoreCase("o") && demo[7].equalsIgnoreCase("o") && demo[8].equalsIgnoreCase("o") || demo[0].equalsIgnoreCase("o") && demo[3].equalsIgnoreCase("o") && demo[6].equalsIgnoreCase("o") || demo[1].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[7].equalsIgnoreCase("o") || demo[2].equalsIgnoreCase("o") && demo[5].equalsIgnoreCase("o") && demo[8].equalsIgnoreCase("o") || demo[0].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[8].equalsIgnoreCase("o") || demo[2].equalsIgnoreCase("o") && demo[4].equalsIgnoreCase("o") && demo[6].equalsIgnoreCase("o"))
			return 1;

		return 0;
	}

	/**
	 * if the score of the game is 0 then return false. this mean we have a winner
	 */
	public boolean gameOver(String[] demo) {
		return getScore(demo) != 0 ? true : false;
	}

	/**
	 * test if the game is draw. if demo is full, this mean that game is draw if
	 * demo still has empty square, this mean that the game isn't finished
	 */
	public boolean drawGame(String[] demo) {
		for (int i = 0; i < 9; i++)
			if (demo[i].equals(" "))
				return false;
		return true;
	}
}