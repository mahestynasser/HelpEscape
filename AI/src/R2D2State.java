
public class R2D2State extends State {

	Grid grid;
	int pressurePadsLeft;
	String previousOperation;

	public R2D2State(Grid grid, int pressurePadsLeft, String previousOperation) {
		this.grid = grid;
		this.pressurePadsLeft = pressurePadsLeft;
		this.previousOperation = previousOperation;
	}
	
	public int heuristicFun1() { // the Manhattan distance between R2D2 and the teleportal
		int R2D2posX = grid.r2d2Position.i;
		int R2D2posY = grid.r2d2Position.j;
		int telposX = grid.teleportalPosition.i;
		int telposY = grid.teleportalPosition.j;
		int ManhattanDist = Math.abs(telposX-R2D2posX) + Math.abs(R2D2posY-telposY);
		return ManhattanDist;
	}
	public int heuristicFun2() { // the number of pressure pads left with no rocks on them
		
		return pressurePadsLeft;
	}

}
