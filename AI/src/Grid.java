import java.util.ArrayList;

public class Grid {

	int rows = 0;
	int cols = 0;
	char[][] grid;
	boolean[][] visited;
	Pair teleportalPosition;
	Pair r2d2Position;

	
	public Grid(int rows, int cols, char[][]grid, Pair teleportalPosition, Pair r2d2Position,boolean [][]visited) {
		this.rows=rows;
		this.cols=cols;
		this.grid=grid;
		this.teleportalPosition=teleportalPosition;
		this.r2d2Position= r2d2Position;
		this.visited= visited;
	}
	

	public static void main(String[] args) {
		Grid g= HelpR2D2.GenGrid();
		System.out.println(g.cols);
		System.out.println(g.rows);
		for (int i = 0; i < g.rows; i++) {
			for (int j = 0; j < g.cols; j++) {
				System.out.print(g.grid[i][j] + " ");
			}
			System.out.println();
		}
	}
}
