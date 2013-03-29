package player;

public class BestMove extends Move {

	protected int score;
	protected Move move;
	
	public BestMove(int score) {
	  move = new Move();
		this.score = score;
	}
	
	public BestMove() {
	}
	
	public int score() {
		return score;
	}
}
