import java.util.ArrayList;
import java.util.TreeSet;

public abstract class SearchProblem {
	
	State initialState;
	TreeSet<String> operators;
	
	
	
	
	public abstract boolean goalTest(Node n);
	public abstract long pathCost(Node n);
	public abstract ArrayList<Node> expand(Node n);
	

}
