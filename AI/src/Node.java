
public class Node implements Comparable<Node> {
	
	State state;
	int pathCost;
	Node Parent;
	
	public Node(State initialState, int pathCost, Node parent) {
		this.state = initialState;
		this.pathCost = pathCost;
		this.Parent = parent;
	}

	@Override
	public int compareTo(Node n) {
		if (this.pathCost == n.pathCost)
			return 0;
		else
			return this.pathCost-n.pathCost;
	}

}
