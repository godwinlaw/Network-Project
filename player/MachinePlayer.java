/* MachinePlayer.java */

package player;

import java.util.Random;
import list.*;
import dict.*;

/**
 * An implementation of an automatic Network player. Keeps track of moves made
 * by both players. Can select a move for itself.
 */
public class MachinePlayer extends Player {

  private int playerColor;
  private int opponentColor;
  private int searchDepth;
  private Board board;
  private HashTableChained hashTable;
  private boolean alphaBetaPrune;

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
    MoveList moves = board.validMoves(playerColor);
    Random generator = new Random();
    System.out.println("cM:" + moves.length());
    Move move = moves.elementAt(generator.nextInt(moves.length()));
    forceMove(move);
    System.out.println(board);
    return move;
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

  private BestMove gameTreeSearch(int color, int searchDepth) {
		BestMove myBest = new BestMove();
		BestMove reply;
    int boardScore;
    
    if (hashTable.has(board)) {
	    boardScore = hashTable.findScore(board);
		} else {
		  boardScore = board.evaluateBoard(color);
		  hashTable.insert(board, boardScore);
		}
    if (searchDepth == 0 || board.hasValidNetwork()) {
  	  return new BestMove(boardScore);
  	}
    
    if (color == playerColor) {
      myBest.score = -1;
    } else {
      myBest.score = 1;
    }
    
    MoveList v = board.validMoves(color);
		while(v.hasNext()) {
		  Move m = v.nextMove();
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

  //check for faster move
  private BestMove gameTreeSearchwithAlphaBetaPruning(int color, int alpha, int beta, int searchDepth) {
      BestMove myBest = new BestMove();
      BestMove reply;
      int boardScore;

      if (hashTable.has(board)) {
        boardScore = hashTable.findScore(board);
      } else {
        boardScore = board.evaluateBoard(color);
        hashTable.insert(board, boardScore);
      }
      
      if (searchDepth == 0 || board.hasValidNetwork()) {
        return new BestMove(boardScore);
      }
      
      if (color == playerColor) {
        myBest.score = alpha;
      } else {
        myBest.score = beta;
      }
      
      MoveList v = board.validMoves(color);
      while(v.hasNext()) {
        Move m = v.nextMove();
        board.performMove(m, color);
        reply = gameTreeSearchwithAlphaBetaPruning(Math.abs(color - 1),0 ,0 ,searchDepth - 1);
        board.undoMove(m, color);
        if ((color == playerColor) && (reply.score >= myBest.score)) {
          myBest.move = m;
          myBest.score = reply.score;
          alpha = reply.score;
        } else if ((color == opponentColor) && (reply.score <= myBest.score)) {
          myBest.move = m;
          myBest.score = reply.score;
          beta = reply.score;
        }
        if (alpha >= beta) {
          return myBest;
        }
      }
      return myBest;
  }
}
