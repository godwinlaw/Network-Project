/* MachinePlayer.java */

package player;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

  private int color;
	private int searchDepth;
	private Board board;

	// Creates a machine player with the given color.  Color is either 0 (black)
	// or 1 (white).  (White has the first move.)
	public MachinePlayer(int color) {
		this(color, Integer.MAX_VALUE);
	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this.color = color;
		this.searchDepth = searchDepth;
		board = new Board();
	}

	// Returns a new move by "this" player.  Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove(Board b, int moveKind) {
	    Best myBest = new Best();
	    Best reply;
	    
	    if (b.hasValidNetwork() || b.isFull()) {
	    	return "a Best with Grid's score, no move;"
	    }
    	myBest.score = -1;
	    
	    for (b.validMoves()) {
	    	perform move m;
	    	reply = chooseMove(!side);
	    	undo move m;
	    	if (((side == COMPUTER) && (reply.score >= myBest.score)) || ((side == HUMAN) && (reply.score <= myBest.score))) {
	    		myBest.move = m;
	    		myBest.score = reply.score;
	    	}
	    }
	    return myBest;	
	}	 
	

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		int oppColor;
		if (color==0) {
			oppColor = Board.WHITE;
		} else {
			oppColor = Board.BLACK;
		}
		return recordMove(m, oppColor);
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.
	public boolean forceMove(Move m) {
		return recordMove(m, color+1);
	}

	private boolean recordMove(Move m, int color) {
		if (!board.isValidMove(m, color)) {
			return false;
		} else if (m.moveKind==1) {
			board.addChip(m, color);
		} else if (m.moveKind==2) {
			board.moveChip(m, color);
		}
		return true;
	}

}