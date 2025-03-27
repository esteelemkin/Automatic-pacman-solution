package exe.ex3;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * The algorithm initializes the necessary variables and data structures.
 * On each move, it retrieves the current position of Pac-Man and the game map.
 * It identifies the dots on the map and determines the closest dot to Pac-Man's current position.
 * The algorithm calculates the shortest path from Pac-Man's position to the closest dot using the map's shortestPath method.
 * It selects the next step on the calculated path as the direction for Pac-Man to move.
 * If Pac-Man reaches the current destination or if there is no valid path, the algorithm recalculates the closest dot and the shortest path.
 * The algorithm repeats these steps on subsequent moves, guiding Pac-Man towards the closest dot.
 */
public class Ex3Algo implements PacManAlgo {
	private Map map = null;
	private Pixel2D[] dots = null;
	private Pixel2D closestDot = null;
	private Pixel2D pos;
	private Pixel2D[] path;
	private int pathIndex;
	private int _count;

	public Ex3Algo() {
		_count = 0;
	}

	@Override
	/**
	 *  Add a short description for the algorithm as a String.
	 */
	public String getInfo() {
		return "This algorithm guides Pac-Man towards the closest dot on the map by calculating the shortest path. It continuously updates the path and adjusts Pac-Man's movement direction to reach the closest dot efficiently.";
	}

	public int move(PacmanGame game) {
		pos = fromString(game.getPos(0));
		if (map == null) {
			map = new Map(game.getGame(0));
			if (!game.isCyclic()) {
				map.setCyclic(false);
			}
		}
		if (dots == null) {
			dots = findDots(game.getGame(0));
		}
		if (closestDot == null) {
			closestDot = findClosestDot(pos);
		}
		if (path == null) {
			path = map.shortestPath(pos, closestDot, 1);
			pathIndex = 0;
		}

		if (_count == 0 || _count == 300) {
			int code = 0;
			int[][] board = game.getGame(0);
			printBoard(board);
			int blue = Game.getIntColor(Color.BLUE, code);
			int pink = Game.getIntColor(Color.PINK, code);
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
			String pos = game.getPos(code);
			System.out.println("Pacman coordinate: " + pos);
			GhostCL[] ghosts = game.getGhosts(code);
			printGhosts(ghosts);
			int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
		}
		_count++;
		pathIndex++;
		if (pathIndex > path.length - 1) {  //means that Pac-Man has reached the last position in the path. In this case, reset the path array, find all the dots again
			path = null;
			dots = findDots(game.getGame(0));
			closestDot = findClosestDot(pos);
			path = map.shortestPath(pos, closestDot, 1);
			pathIndex = 1;
		}
		return findDir();                     //determines the appropriate direction based on Pac-Man's current position and the next position in the path.

	}

	private Pixel2D findClosestDot(Pixel2D current) {
		Pixel2D closest = dots[0];
		Pixel2D tempDot;
		Pixel2D[] shortPath = map.shortestPath(current, closest, 1); //CALCULATES USING SHORTEST PATH
		int shortestDist = shortPath.length;                                // representing the shortest distance found so far.
		int tempDist;
		for (int i = 1; i < dots.length; i++) {                           //finding the closest dot
			tempDot = dots[i];
			tempDist = (map.shortestPath(current, tempDot, 1)).length;
			if (tempDist < shortestDist) {
				shortestDist = tempDist;
				closest = tempDot;
			}
		}
		return closest;
	}

	private Pixel2D[] findDots(int[][] board) {
		List<Pixel2D> dots = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {                 // Iterate over the rows of the board.
			for (int j = 0; j < board[0].length; j++) {             // Iterate over the columns of the board.
				if (board[i][j] == 3) {                         // Check if the current position contains a dot (value of 3).
					dots.add(new Index2D(i, j));
				}
			}
		}
		return ListToArray(dots);
	}

	private int findDir() {
		Pixel2D nextDot = path[pathIndex];     // Get the position of the next dot in the path.
		if ((pos.getX() + 1) % map.getHeight() == nextDot.getX() && (pos.getY() == nextDot.getY())) {
			return Game.RIGHT;                   // If the next dot is to the right of the current position, return the RIGHT direction.
		}
		if ((pos.getX() - 1 + map.getHeight()) % map.getHeight() == nextDot.getX() && (pos.getY() == nextDot.getY())) {
			return Game.LEFT;                     // If the next dot is to the left of the current position, return the LEFT direction.
		}
		if ((pos.getX() == nextDot.getX() && ((pos.getY() + 1) % map.getWidth() == nextDot.getY()))) {
			return Game.UP;                       // If the next dot is above the current position, return the UP direction.
		}
		if (pos.getX() == nextDot.getX() && (((pos.getY() - 1 + map.getWidth()) % map.getWidth() == nextDot.getY()))) {
			return Game.DOWN;                      // If the next dot is below the current position, return the DOWN direction.

		}
		return 0;
	}

	private Pixel2D[] ListToArray(List<Pixel2D> list) {
		Pixel2D[] parray = new Pixel2D[list.size()];
		for (int i = 0; i < parray.length; i++) {
			parray[i] = list.get(i);
		}
		return parray;
	}

	public static Pixel2D fromString(String s) throws NumberFormatException {
		s = s.replace("(", "").replace(")", "");
		String[] parts = s.split(",");
		if (parts.length != 2) {
			throw new NumberFormatException("Invalid format for Pixel2D. Expected (x,y).");
		}
		int x = Integer.parseInt(parts[0].trim());
		int y = Integer.parseInt(parts[1].trim());

		return new Index2D(x, y);
	}

	/**
	 * This ia the main method - that you should design, implement and test.
	 */
	public int moveRotations(PacmanGame game) {
		if (_count == 0 || _count == 300) {
			int code = 0;
			int[][] board = game.getGame(0);
			printBoard(board);
			int blue = Game.getIntColor(Color.BLUE, code);
			int pink = Game.getIntColor(Color.PINK, code);
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
			String pos = game.getPos(code).toString();
			System.out.println("Pacman coordinate: " + pos);
			GhostCL[] ghosts = game.getGhosts(code);
			printGhosts(ghosts);
			int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
		}

		return 0;
	}

	private int moveRandomly(PacmanGame game) {
		if (_count == 0 || _count == 300) {
			int code = 0;
			int[][] board = game.getGame(0);
			printBoard(board);
			int blue = Game.getIntColor(Color.BLUE, code);
			int pink = Game.getIntColor(Color.PINK, code);
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
			String pos = game.getPos(code).toString();
			System.out.println("Pacman coordinate: " + pos);
			GhostCL[] ghosts = game.getGhosts(code);
			printGhosts(ghosts);
			int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
		}
		_count++;
		int dir = randomDir();
		return dir;
	}

	private static void printBoard(int[][] b) {
		for (int y = 0; y < b[0].length; y++) {
			for (int x = 0; x < b.length; x++) {
				int v = b[x][y];
				System.out.print(v + "\t");
			}
			System.out.println();
		}
	}

	private static void printGhosts(GhostCL[] gs) {
		for (int i = 0; i < gs.length; i++) {
			GhostCL g = gs[i];
			System.out.println(i + ") status: " + g.getStatus() + ",  type: " + g.getType() + ",  pos: " + g.getPos(0) + ",  time: " + g.remainTimeAsEatable(0));
		}
	}

	private static int randomDir() {
		int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
		int ind = (int) (Math.random() * dirs.length);
		return dirs[ind];
	}

}