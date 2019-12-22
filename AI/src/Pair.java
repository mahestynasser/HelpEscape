

public class Pair implements Comparable<Pair> {
		int i;
		int j;

		public Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public int compareTo(Pair p) {
			if (this.i == p.i)
				return this.j - p.j;
			return this.i - p.i;
		}
	}