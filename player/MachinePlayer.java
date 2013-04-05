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
  private int moveCount;
  private Board board;
  private HashTableChained hashTable;
  private boolean alphaBetaPrune;
  private boolean hash;

  // Creates a machine player with the given color. Color is either 0 (black)
  // or 1 (white). (White has the first move.)
  public MachinePlayer(int color) {
    this(color, 4);
  }

  // Creates a machine player with the given color and search depth. Color is
  // either 0 (black) or 1 (white). (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    playerColor = color;
    opponentColor = Math.abs(playerColor - 1);
    moveCount = 0;
    this.searchDepth = searchDepth;
    hashTable = new HashTableChained();
    board = new Board(color);
    alphaBetaPrune = true;
    hash = true;
  }

  // Returns a new move by "this" player. Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    	moveCount++;
	Move m;
	if (moveCount==1) {
		if (playerColor==Board.WHITE) {
			m = new Move(0, 3);
		} else {
			m = new Move(3, 0);
		}
		forceMove(m);
		return m;
	} else if (moveCount==2) {
		if (playerColor==Board.WHITE) {
			m = new Move(7, 4);
		} else {
			m = new Move(4, 7);
		}
		forceMove(m);
		return m;
	}
    if (alphaBetaPrune && hash) {
      m = gameTreeSearchPrunedandHashed(playerColor, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, searchDepth).move;    
    } else if (alphaBetaPrune && !hash) {
      m = gameTreeSearchPruned(playerColor, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, searchDepth).move;
    } else if (!alphaBetaPrune && hash) {
      m = gameTreeSearchHashed(playerColor, searchDepth).move;
    } else {
      m = gameTreeSearch(playerColor, searchDepth).move;
    }
    forceMove(m);
    return m;
/*  MoveList moves = board.validMoves(playerColor);
    Random generator = new Random();
    Move move = moves.elementAt(generator.nextInt(moves.length()));
    forceMove(move);
    return move;*/
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
      return true;
    }
  }

  private BestMove gameTreeSearch(int color, int searchDepth) {
    BestMove myBest = new BestMove();
    BestMove reply;
    
    if (board.hasValidNetwork(color)) {
      if (color == playerColor) {
        return new BestMove(500);
      } else {
        return new BestMove(-500);
      }
    }
    if (searchDepth == 0) {
      return new BestMove(board.evaluateBoard(color));
    }

    if (color == playerColor) {
      myBest.score = -500;
    } else {
      myBest.score = 500;
    }
    
    MoveList v = board.validMoves(color);
    while(v.hasNext()) {
      Move m = v.nextMove();
      board.performMove(m, color);
      reply = gameTreeSearch(Math.abs(color - 1), searchDepth - 1);
      board.undoMove(m, color);
      if (((color == playerColor) && (reply.score >= myBest.score)) 
          || ((color == opponentColor) && (reply.score <= myBest.score))) {
        double depthPenalty = 0;
        if (color == playerColor) {
          depthPenalty = -0.005;
        } else if (color == opponentColor) {
          depthPenalty = 0.005;
        }
        myBest.move = m;
        myBest.score = reply.score + depthPenalty;
      }
    }
    return myBest;  
  }
  
  private BestMove gameTreeSearchHashed(int color, int searchDepth) {
		BestMove myBest = new BestMove();
		BestMove reply;
    int boardScore;
    
    if (hashTable.has(board)) {
      System.out.println("encountered");
	    boardScore = hashTable.findScore(board);
		} else {
		  boardScore = board.evaluateBoard(color);
		  hashTable.insert(board, boardScore);
		}
    
    if (board.hasValidNetwork(color)) {
      if (color == playerColor) {
        return new BestMove(500);
      } else {
        return new BestMove(-500);
      }
    }
    if (searchDepth == 0) {
      return new BestMove(boardScore);
    }
    
    if (color == playerColor) {
      myBest.score = -500;
    } else {
      myBest.score = 500;
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

  private BestMove gameTreeSearchPruned(int color, double alpha, double beta, int searchDepth) {
      BestMove myBest = new BestMove();
      BestMove reply;

      if (searchDepth == 0) {
        return new BestMove(board.evaluateBoard(color) * (searchDepth + 1));
      }
      
      if (color == playerColor) {
        myBest.score = Double.NEGATIVE_INFINITY;
      } else {
        myBest.score = Double.POSITIVE_INFINITY;
      }
      
      MoveList v = board.validMoves(color);
      while(v.hasNext()) {
        Move m = v.nextMove();
        board.performMove(m, color);
        reply = gameTreeSearchPruned(Math.abs(color - 1), alpha, beta, searchDepth - 1);
        board.undoMove(m, color);

        if ((color == playerColor) && (reply.score >= myBest.score)) {
          myBest.move = m;
          myBest.score = reply.score;
          alpha = myBest.score;
        } else if ((color == opponentColor) && (reply.score <= myBest.score)) {
          myBest.move = m;
          myBest.score = reply.score;
          beta = myBest.score;
        }
        if (alpha >= beta) {
          return myBest;
        }
      }
      return myBest;
  }
  
  private BestMove gameTreeSearchPrunedandHashed(int color, double alpha, double beta, int searchDepth) {
    BestMove myBest = new BestMove();
    BestMove reply;
    int boardScore;
    
    if (hashTable.has(board)) {
      System.out.println("encountered");
      boardScore = hashTable.findScore(board);
    } else {
      boardScore = board.evaluateBoard(color);
      Board newBoard = new Board(board);
      hashTable.insert(newBoard, boardScore);
    }
    boardScore *= (searchDepth + 1);
    
    if (searchDepth == 0) {
      return new BestMove(boardScore);
    }
    
    if (color == playerColor) {
      myBest.score = Double.NEGATIVE_INFINITY;
    } else {
      myBest.score = Double.POSITIVE_INFINITY;
    }
    
    MoveList v = board.validMoves(color);
    while(v.hasNext()) {
      Move m = v.nextMove();
      board.performMove(m, color);
      reply = gameTreeSearchPruned(Math.abs(color - 1), alpha, beta, searchDepth - 1);
      board.undoMove(m, color);

      if ((color == playerColor) && (reply.score > myBest.score)) {
        myBest.move = m;
        myBest.score = reply.score;
        alpha = myBest.score;
      } else if ((color == opponentColor) && (reply.score < myBest.score)) {
        myBest.move = m;
        myBest.score = reply.score;
        beta = myBest.score;
      }
      if (alpha >= beta) {
        return myBest;
      }
    }
    if (searchDepth == this.searchDepth) {
      hashTable.makeEmpty();
    }
    return myBest;  
  }
}
