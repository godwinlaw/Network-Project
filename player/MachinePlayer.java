/* MachinePlayer.java */

package player;

import java.util.Random;

/**
 * An implementation of an automatic Network player. Keeps track of moves made
 * by both players. Can select a move for itself.
 */
public class MachinePlayer extends Player {

	private int playerColor;
	private int opponentColor;
	private int searchDepth;
	private Board board;

	// Creates a machine player with the given color. Color is either 0 (black)
	// or 1 (white). (White has the first move.)
	public MachinePlayer(int color) {
		this(color, Integer.MAX_VALUE);
	}

	// Creates a machine player with the given color and search depth. Color is
	// either 0 (black) or 1 (white). (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		playerColor = color;
		opponentColor = Math.abs(playerColor - 1);
		this.searchDepth = searchDepth;
		board = new Board(color);
	}

	// Returns a new move by "this" player. Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove() {
		Move[] moves = board.validMoves(playerColor);
		Random generator = new Random();
		return moves[generator.nextInt(moves.length)];
	}

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true. If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player. This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		return recordMove(m, opponentColor);
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true. If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player. This method is used to help set up "Network problems" for your
	// player to solve.
	public boolean forceMove(Move m) {
		return recordMove(m, playerColor);
	}

	private boolean recordMove(Move m, int color) {
		if (!board.isValidMove(m, color)) {
			return false;
		} else {
			board.performMove(m, color);
		}
		return true;
	}
  /**
	private BestMove gameTreeSearch(int color, int searchDepth) {
		BestMove myBest = new BestMove(0);
		BestMove reply;

		//?
		if (searchDepth == 0 || board.hasValidNetwork()) {
		  return new BestMove(board.evaluateBoard());
		}
		if (color == playerColor) {
			myBest.score = -1; 
		} else if (color == opponentColor) {
		  myBest.score = 1;
		}

		for (Move m : board.validMoves(color)) {
			board.performMove(m, color);
			reply = gameTreeSearch(Math.abs(color - 1), searchDepth - 1);
			board.undoMove(m, color);
			if (((color == playerColor) && (reply.score >= myBest.score)) 
					|| ((color == opponentColor) && (reply.score <= myBest.score))) {
				myBest.move = m;
				myBest.score = reply.score;
			}
		}
		return myBest;	
	}
  */

	private Move gameTreeSearchwithAlphaBetaPruning() {
		return null;
	}
}
