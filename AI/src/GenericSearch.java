import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class GenericSearch  {
	
	
	static void Search( Grid grid, String strategy, boolean visualize) {
		
		PrintWriter out = new PrintWriter(System.out);
		
			R2D2State s= new R2D2State(grid, grid.cols*grid.rows/8, "");
			
			for (int i = 0; i < s.grid.rows; i++) {
				out.println(Arrays.toString(s.grid.grid[i]));
			}
			TreeSet<String> op= new TreeSet();
			
			op.add("R");
			op.add("U");
			op.add("L");
			op.add("D");
			HelpR2D2 p= new HelpR2D2(s, op);
			Node ans = null;
			if(strategy.equals("ID")) {
				int depth = 0;
				while(true) {
					ans = depthLimitSearch(p, depth);
					if(ans!=null)
						break;
					else
						depth++;
				}
			}
			
			if(!strategy.equals("ID")) {
			ans= genericSearchAlgorithm(p, strategy);
			}
			
			if(ans==null) {
				out.println("NO SOLUTION");

				for (int i = 0; i < s.grid.rows; i++) {
					out.println(Arrays.toString(s.grid.grid[i]));
				}
			}
			else
			{
				if(visualize) {
					
			char[][]gr=((R2D2State)ans.state).grid.grid;
			
			for (int i = 0; i < gr.length; i++) {
				out.println(Arrays.toString(gr[i]));
			}
			out.println(((R2D2State)ans.state).previousOperation);
			Node path=ans;
			while(path.Parent!=null) {
				path=path.Parent;
				char[][]pathGrid=((R2D2State)path.state).grid.grid;
				for (int i = 0; i < pathGrid.length; i++) {
					out.println(Arrays.toString(pathGrid[i]));
				}
				out.println();
				out.println(((R2D2State)path.state).previousOperation); //printing the operations
			}
		}
				else {
					Node path=ans;
					out.println(((R2D2State)path.state).previousOperation);
					while(path.Parent!=null) {
						path=path.Parent;
						out.println(((R2D2State)path.state).previousOperation); //printing the operations
				}
		}
			}
		
		
		out.flush();
		out.close();
	}
	
	static Node depthLimitSearch(SearchProblem p, int depth) {
		ArrayList<Node> q= new ArrayList<>();
		Node initial = new Node(p.initialState,0, null);
		q.add(initial);
		int curDepth = 0;
        while(curDepth< Math.min(depth, q.size())) {
			
			Node node= q.remove(0);
			if(p.goalTest(node)) 
				return node;
		curDepth = node.pathCost;
			
			ArrayList<Node> expandedNodes= p.expand(node);
		
		for (int i = 0; i < expandedNodes.size(); i++) {
			q.add(0, expandedNodes.get(i));
		}
        }
        return null;
	}
	
	
	
	
	
	static Node genericSearchAlgorithm(SearchProblem p, String QingFun) {
		
		ArrayList<Node> q= new ArrayList<>();
		Node initial = new Node(p.initialState,0, null);
		q.add(initial);
		
		while(!q.isEmpty()) {
			
			Node node= q.remove(0);
			if(p.goalTest(node)) 
				return node;
			
			ArrayList<Node> expandedNodes= p.expand(node);
			
			switch (QingFun) {
			case "DF": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(0, expandedNodes.get(i));
				}
			}
				break;
			case "BF": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(expandedNodes.get(i));
				
				}
			}
				break;
			case "UC": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(expandedNodes.get(i));
					Collections.sort(q);
				}
			}
			case "GR1": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(expandedNodes.get(i));
					Collections.sort(q, new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							if (((R2D2State)n1.state).heuristicFun1() == ((R2D2State)n2.state).heuristicFun1())
								return 0;
							else
								return ((R2D2State)n1.state).heuristicFun1()-((R2D2State)n2.state).heuristicFun1();
						}
					});
				}
			}
			case "GR2": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(expandedNodes.get(i));
					Collections.sort(q, new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							if (((R2D2State)n1.state).heuristicFun2() == ((R2D2State)n2.state).heuristicFun2())
								return 0;
							else
								return ((R2D2State)n1.state).heuristicFun2()-((R2D2State)n2.state).heuristicFun2();
						}
					});
				}
			}
			case "AS1": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(expandedNodes.get(i));
					Collections.sort(q, new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							if ((((R2D2State)n1.state).heuristicFun1())+n1.pathCost == (((R2D2State)n2.state).heuristicFun1())+n2.pathCost)
								return 0;
							else
								return (((R2D2State)n1.state).heuristicFun1()+ n1.pathCost)-(((R2D2State)n2.state).heuristicFun1()+n2.pathCost);
						}
					});
				}
			}
			case "AS2": {
				for (int i = 0; i < expandedNodes.size(); i++) {
					q.add(expandedNodes.get(i));
					Collections.sort(q, new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							if ((((R2D2State)n1.state).heuristicFun2())+n1.pathCost == (((R2D2State)n2.state).heuristicFun2())+n2.pathCost)
								return 0;
							else
								return (((R2D2State)n1.state).heuristicFun2()+ n1.pathCost)-(((R2D2State)n2.state).heuristicFun2()+n2.pathCost);
						}
					});
				}
			}
			
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		Grid g= HelpR2D2.GenGrid();
		Search( g, "AS1", false);
		

	}

}
