/* Best Move */

package player;

public class BestMove {
  
  /**
   * score represents the score of the move
   * move is the best move the player can perform
   */
	protected double score;
	protected Move move;
	
	/**
	 * Constructor that creates a move with a specified score.
	 * move of this BestMove is defaulted to be a quit move
	 * @param score
	 */
	public BestMove(int score) {
	  move = new Move();
		this.score = score;
	}
	
	/**
	 * Constructor that creates an empty BestMove.
	 */
	public BestMove() {
	}
	
}
