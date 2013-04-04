package test;

import player.*;

public class isValidMoveTest {
 
  public static void main(String[] args) {
    int playerColor = Board.WHITE; 
    int opponentColor = Board.BLACK;
    Board b = new Board(playerColor);
    
    //Testing Starting Placement
    Move ma = new Move(1,0); 
    Move mb = new Move(5,7);
    Move mc = new Move(0,5);
    Move md = new Move(7,5);
    /*System.out.println("Should return false: " + b.isValidMove(ma, playerColor));
    System.out.println("Should return true: " + b.isValidMove(ma, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(mb, playerColor));
    System.out.println("Should return true: " + b.isValidMove(mb, opponentColor));
    System.out.println("Should return true: " + b.isValidMove(mc, playerColor));
    System.out.println("Should return false: " + b.isValidMove(mc, opponentColor));
    System.out.println("Should return true: " + b.isValidMove(md, playerColor));
    System.out.println("Should return false: " + b.isValidMove(md, opponentColor));*/
    
    /*
     * Testing adjacent
     *      -----------------------------------------
     *      |    |  X |  X | BB |  X |    |    |    |
     *      -----------------------------------------
     *      |    |  X | BB |  X |  X |  X |  X |    |
     *      -----------------------------------------
     *      |    |  X |  X |  X |  X | BB |  X |    |
     *      -----------------------------------------
     *      |  X |  X |  X |  X |  X | BB |  X |    |
     *      -----------------------------------------
     *      |  X | BB | BB |  X |  X |  X |  X |    |
     *      -----------------------------------------
     *      |  X |  X |  X |  X |    |    | BB |    |
     *      -----------------------------------------
     *      |  X | BB | BB |  X |    |  X |    |    |
     *      -----------------------------------------
     *      |  X |  X |  X |  X | BB |    |    |    |
     *      -----------------------------------------
     *
    */
    b.addChip(3,0,opponentColor);
    b.addChip(2,1,opponentColor);
    b.addChip(5,2,opponentColor);
    b.addChip(5,3,opponentColor);
    b.addChip(2,4,opponentColor);
    b.addChip(6,5,opponentColor);
    b.addChip(1,6,opponentColor);
    b.addChip(4,7,opponentColor);
    
    b.addChip(2, 6, opponentColor);
    b.addChip(1, 4, opponentColor);
    
    
    Move m1 = new Move(1,0);
    Move m2 = new Move(2,0);
    Move m3 = new Move(4,0);
    Move m4 = new Move(1,1);
    Move m5 = new Move(3,1);
    Move m6 = new Move(4,1);
    Move m7 = new Move(5,1);
    Move m8 = new Move(6,1);
    Move m9 = new Move(1,2);
    Move m10 = new Move(2,2);
    Move m11 = new Move(3,2);
    Move m12 = new Move(4,2);
    Move m13 = new Move(6,2);
    Move m14 = new Move(4,3);
    Move m15 = new Move(6,3);
    Move m16 = new Move(4,4);
    Move m17 = new Move(5,4);
    Move m18 = new Move(6,4);
    Move m19 = new Move(1,5);
    Move m20 = new Move(2,5);
    Move m21 = new Move(5,6);
    
    /*System.out.println("Should return false: " + b.isValidMove(m1, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m2, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m3, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m4, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m5, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m6, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m7, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m8, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m9, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m10, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m11, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m12, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m13, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m14, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m15, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m16, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m17, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m18, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m19, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m20, opponentColor));
    System.out.println("Should return false: " + b.isValidMove(m21, opponentColor));*/
    
    //System.out.println(b);
    //b.validMoves(opponentColor);
    System.out.println("Created new board: ");
    b = new Board(playerColor);
    System.out.println(b);
    
    System.out.println("Adding to 11");
    Move m = new Move(1,1);
    b.performMove(m, playerColor);
    System.out.println(b);
    
    System.out.println("Undoing move");
    b.undoMove(m, playerColor);
    System.out.println(b);
  }
}
