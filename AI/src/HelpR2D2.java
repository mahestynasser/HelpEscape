import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class HelpR2D2 extends SearchProblem {

	public static Grid GenGrid() {

		int rows = (int) (Math.random()) + 3;   
		int cols = (int) (Math.random()) + 3;
		char[][] grid = new char[rows][cols];
		int rocksNo = (rows * cols) / 8;
		int PressurePadsNo = (rows * cols) / 8;
		int obstaclesNo = (rows * cols) / 8;
		TreeSet<Pair> used = new TreeSet<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = '-'; // - means a blank space
			}
		}
		while (rocksNo > 0) { // 'R' means there is a rock at that position
			int positioni = (int) (Math.random() * (rows));
			int positionj = (int) (Math.random() * (cols));
			Pair position = new Pair(positioni, positionj);
			if (!used.contains(position)) {
				used.add(position);
				grid[positioni][positionj] = 'R';
				rocksNo--;
			}
		}
		while (PressurePadsNo > 0) { // 'P' means there is a Pressure pad at that position
										// If R2D2 is standing on a pressure pad it becomes 'B'
			int positioni = (int) (Math.random() * (rows));
			int positionj = (int) (Math.random() * (cols));
			Pair position = new Pair(positioni, positionj);
			if (!used.contains(position)) {
				used.add(position);
				grid[positioni][positionj] = 'P';
				PressurePadsNo--;
			}
		}
		while (obstaclesNo > 0) { // 'O' means there is an obstacle at that position
			int positioni = (int) (Math.random() * (rows));
			int positionj = (int) (Math.random() * (cols));
			Pair position = new Pair(positioni, positionj);
			if (!used.contains(position)) {
				used.add(position);
				grid[positioni][positionj] = 'O';
				obstaclesNo--;
			}
		}
		int teleportal = 1;
		int initial = 1;
		Pair initialPosition = null;
		while (initial > 0) { // 'I' means the initial location
			int positioni = (int) (Math.random() * (rows));
			int positionj = (int) (Math.random() * (cols));
			Pair position = new Pair(positioni, positionj);
			if (!used.contains(position)) {
				used.add(position);
				grid[positioni][positionj] = 'I';
				initialPosition = new Pair(positioni, positionj);
				initial--;
			}
		}
		Pair teleportalPosition = null;
		while (teleportal > 0) { // 'T' means the teleportal position
			// If R2D2 is standing on a Teleportal it becomes 'E'
			// If a rock is pushed on a Teleportal it becomes 'W'
			int positioni = (int) (Math.random() * (rows));
			int positionj = (int) (Math.random() * (cols));
			Pair position = new Pair(positioni, positionj);
			if (!used.contains(position)) {
				used.add(position);
				grid[positioni][positionj] = 'T';
				teleportalPosition = new Pair(positioni, positionj);
				teleportal--;
			}
		}

		boolean[][] visited = new boolean[rows][cols];
		visited[initialPosition.i][initialPosition.j] = true;
		return new Grid(rows, cols, grid, teleportalPosition, initialPosition, visited);
	}

	
	
	
	
	public HelpR2D2(R2D2State initialState, TreeSet<String> operators) {
		this.initialState = initialState;
		this.operators = operators;
	}

	
	
	
	public boolean goalTest(Node n) {
		Grid grid = ((R2D2State)n.state).grid;
		if (grid.grid[grid.teleportalPosition.i][grid.teleportalPosition.j] == 'E' && ((R2D2State)n.state).pressurePadsLeft == 0)
			return true;
		return false;
	}

	
	
	public long pathCost(Node n) {
		return n.pathCost;
	}

	
	
	
	
	
	public ArrayList<Node> expand(Node n) {
		ArrayList<Node> expandedNodes = new ArrayList<>();

		R2D2State state= ((R2D2State) n.state);
		boolean[][] visited = state.grid.visited;
		Pair r2pos = state.grid.r2d2Position;
		Grid g = state.grid;

		if (operators.contains("R") && valid(r2pos.i, r2pos.j + 1, state.grid, 'R') && !visited[r2pos.i][r2pos.j + 1]) {
			char[][] newGrid = new char[g.rows][g.cols];
			for (int i = 0; i < g.rows; i++) {
				for (int j = 0; j < g.cols; j++) {
					newGrid[i][j] = g.grid[i][j];
				}
			}
			int change = update(newGrid, r2pos.i, r2pos.j, r2pos.i, r2pos.j + 1);
			boolean[][] vis;

			if (change > 0)
				vis = new boolean[g.rows][g.cols];
			else {
				vis = new boolean[g.rows][g.cols];
			for (int i = 0; i < g.rows; i++) {
				for (int j = 0; j < g.cols; j++) {
					vis[i][j] = g.visited[i][j];
				}
			}
			}

			vis[r2pos.i][r2pos.j + 1] = true;

			Grid newG = new Grid(g.rows, g.cols, newGrid, g.teleportalPosition, new Pair(r2pos.i,r2pos.j + 1), vis);
			R2D2State s = new R2D2State(newG, state.pressurePadsLeft - ((change == 2) ? 1 : 0), "R");
			Node newN = new Node(s, n.pathCost + 1, n);

			expandedNodes.add(newN);
		}
		if (operators.contains("D") && valid(r2pos.i + 1, r2pos.j, state.grid, 'D') && !visited[r2pos.i + 1][r2pos.j]) {
			char[][] newGrid = new char[g.rows][g.cols];
			for (int i = 0; i < g.rows; i++) {
				for (int j = 0; j < g.cols; j++) {
					newGrid[i][j] = g.grid[i][j];
				}
			}
			int change = update(newGrid, r2pos.i, r2pos.j, r2pos.i + 1, r2pos.j);
			boolean[][] vis;

			if (change > 0)
				vis = new boolean[g.rows][g.cols];
			else {
				vis = new boolean[g.rows][g.cols];
				for (int i = 0; i < g.rows; i++) {
					for (int j = 0; j < g.cols; j++) {
						vis[i][j] = g.visited[i][j];
					}
				}
			}

			vis[r2pos.i + 1][r2pos.j] = true;

			Grid newG = new Grid(g.rows, g.cols, newGrid, g.teleportalPosition,  new Pair(r2pos.i+1,r2pos.j), vis);
			R2D2State s = new R2D2State(newG, state.pressurePadsLeft - ((change == 2) ? 1 : 0), "D");
			Node newN = new Node(s, n.pathCost + 1, n);

			expandedNodes.add(newN);
		}
		if (operators.contains("L") && valid(r2pos.i, r2pos.j - 1, state.grid, 'L') && !visited[r2pos.i][r2pos.j - 1]) {
			char[][] newGrid = new char[g.rows][g.cols];
			for (int i = 0; i < g.rows; i++) {
				for (int j = 0; j < g.cols; j++) {
					newGrid[i][j] = g.grid[i][j];
				}
			}
			int change = update(newGrid, r2pos.i, r2pos.j, r2pos.i, r2pos.j - 1);
			boolean[][] vis;

			if (change > 0)
				vis = new boolean[g.rows][g.cols];
			else {
				vis = new boolean[g.rows][g.cols];
				for (int i = 0; i < g.rows; i++) {
					for (int j = 0; j < g.cols; j++) {
						vis[i][j] = g.visited[i][j];
					}
				}
			}

			vis[r2pos.i][r2pos.j - 1] = true;

			Grid newG = new Grid(g.rows, g.cols, newGrid, g.teleportalPosition, new Pair(r2pos.i,r2pos.j - 1), vis);
			R2D2State s = new R2D2State(newG, state.pressurePadsLeft - ((change == 2) ? 1 : 0), "L");
			Node newN = new Node(s, n.pathCost + 1, n);

			expandedNodes.add(newN);
		}

		if (operators.contains("U") && valid(r2pos.i - 1, r2pos.j, state.grid, 'U') && !visited[r2pos.i - 1][r2pos.j]) {
			char[][] newGrid = new char[g.rows][g.cols];
			for (int i = 0; i < g.rows; i++) {
				for (int j = 0; j < g.cols; j++) {
					newGrid[i][j] = g.grid[i][j];
				}
			}
			int change = update(newGrid, r2pos.i, r2pos.j, r2pos.i - 1, r2pos.j);
			boolean[][] vis;

			if (change > 0)
				vis = new boolean[g.rows][g.cols];
			else {
				vis = new boolean[g.rows][g.cols];
				for (int i = 0; i < g.rows; i++) {
					for (int j = 0; j < g.cols; j++) {
						vis[i][j] = g.visited[i][j];
					}
				}
			}

			vis[r2pos.i - 1][r2pos.j] = true;

			Grid newG = new Grid(g.rows, g.cols, newGrid, g.teleportalPosition,  new Pair(r2pos.i-1,r2pos.j), vis);
			R2D2State s = new R2D2State(newG, state.pressurePadsLeft - ((change == 2) ? 1 : 0), "U");
			Node newN = new Node(s, n.pathCost + 1, n);

			expandedNodes.add(newN);
		}

		return expandedNodes;
	}

	
	
	
	
	
	public static boolean valid(int i, int j, Grid g, char d) { // d indicates the direction r2d2 is moving.
		boolean f = validHelper(i, j, g);

		if (f && (g.grid[i][j] == 'R' || g.grid[i][j] == 'W')) { // position has a rock
			switch (d) {
			case 'R':
				f &= validHelper(i, j + 1, g) && g.grid[i][j + 1] != 'R' && g.grid[i][j + 1] != 'W';
				break;
			case 'D':
				f &= validHelper(i + 1, j, g) && g.grid[i + 1][j] != 'R' && g.grid[i + 1][j] != 'W';
				break;
			case 'L':
				f &= validHelper(i, j - 1, g) && g.grid[i][j - 1] != 'R' && g.grid[i][j - 1] != 'W';
				break;
			case 'U':
				f &= validHelper(i - 1, j, g) && g.grid[i - 1][j] != 'R' && g.grid[i - 1][j] != 'W';
				break;
			}
		}

		return f;
	}

	
	
	
	
	public static boolean validHelper(int i, int j, Grid g) { // checks !out of bound & !obstacle
		return i < g.rows && i >= 0 && j < g.cols && j >= 0 && g.grid[i][j] != 'O';
	}

	
	
	
	
	// updates the grid and returns 0 if no rocks were moved, 1 if a rock was moved,
	// 2 if that rock was moved onto a pressure pad.
	public static int update(char[][] grid, int oldi, int oldj, int newi, int newj) {
		updateOldPosition(grid, oldi, oldj);
		int change = 0;
		char c = 'I';
		switch (grid[newi][newj]) {
		case '-':
			c = 'I';
			break;
		case 'P':
			c = 'B';
			break;
		case 'T':
			c = 'E';
			break;
		case 'R':
			c = 'I';
			change = 1;
			if (updateRockNewPosition(grid, (newi - oldi) + newi, (newj - oldj) + newj)) {
				change = 2;
			}
			// (newPos - oldPos) is the change, added to the new pos to get the rock
			// position.

			break;

		case 'W':
			c = 'E';
			change = 1;
			if (updateRockNewPosition(grid, (newi - oldi) + newi, (newj - oldj) + newj)) {
				change = 2;
			}
			break;
		}
		grid[newi][newj] = c;

		return change;
	}

	
	
	
	
	// returns false if no pressure pads were activated, true if one was.
	public static boolean updateRockNewPosition(char[][] grid, int rocki, int rockj) {
		char c = 'R';
		boolean pressurePadActivated = false;
		switch (grid[rocki][rockj]) {
		case '-':
			c = 'R';
			break;
		case 'P':
			c = 'O';
			pressurePadActivated = true;
			break;
		case 'T':
			c = 'W';
			break;
		}

		grid[rocki][rockj] = c;
		return pressurePadActivated;
	}

	
	
	
	
	public static void updateOldPosition(char[][] grid, int oldi, int oldj) {
		char c = '-';
		switch (grid[oldi][oldj]) {
		case 'I':
			c = '-';
			break;
		case 'B':
			c = 'P';
			break;
		case 'E':
			c = 'T';
			break;
		}

		grid[oldi][oldj] = c;
	}
	
	
	
	
	
	public static void main(String[] args) {
//		Scanner sc= new Scanner(System.in);
//		Grid g= GenGrid();
//		
//		char [][] x= new char[3][3];
//		
//		for (int i = 0; i < x.length; i++) {
//				x[i]= sc.next().toCharArray();
//		}
//		
//		for (int i = 0; i < x.length; i++) {
//			System.out.println(Arrays.toString(x[i]));
//		}
//		
//		g.grid=x;
		
		
	}
}
