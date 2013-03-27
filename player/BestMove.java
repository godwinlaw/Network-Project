package player;

public class BestMove extends Move {

	protected int score;
	protected Move move;
	
	public BestMove(int xx1, int yy1, int xx2, int yy2) {
		super(xx1, yy1, xx2, yy2);
		// TODO Auto-generated constructor stub
		score = -1;
	}

	public BestMove(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		score = -1;
	}

	public BestMove() {
		// TODO Auto-generated constructor stub
		score = -1;
	}
	
	public int score() {
		return score;
	}
}
