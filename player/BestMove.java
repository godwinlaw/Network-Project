package player;

public class BestMove {

	protected double score;
	protected Move move;
	
	public BestMove(int score) {
	  move = new Move();
		this.score = score;
	}
	
	public BestMove() {
	}
	
	public double score() {
		return score;
	}
}
