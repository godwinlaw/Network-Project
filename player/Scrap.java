package player;

public class Scrap {
  public int evaluateBoard(Board b) {
	int score = 0;
    if (b.hasValidNetwork()) {
    	score = 10;
    } else {
    	score += b.countChips(side);
    	
    }
  }
  
  public Move chooseMove(int x, int y, int moveKind) {
    Best myBest = new Best();
    Best reply;
    
    if (the current Grid is full or has a win) {
    	return "a Best with Grid's score, no move;"
    }
    
    if (side == COMPUTER) {
    	myBest.score = -1;
    }
    for (validMoves()) {
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
}

