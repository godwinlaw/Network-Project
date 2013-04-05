package test;

import player.Board;

public class eFT {

  /**
   * @param args
   */
  public static void main(String[] args) {
    int playerColor = Board.BLACK; 
    Board b = new Board(playerColor);
    
    /*
     * Testing 
     *      -----------------------------------------
     *      ||||||    |    |    |    |  50 |    ||||||
     *      -----------------------------------------
     *      |    |    |    |    |  41 |    |    |    |
     *      -----------------------------------------
     *      |    |    |    |    |    |    |    |    |
     *      -----------------------------------------
     *      |    |    |    |    |    |    |    |    |
     *      -----------------------------------------
     *      |    |  14 |    |    |    |    |    |    |
     *      -----------------------------------------
     *      |    |    |    |    |    |    |    |    |
     *      -----------------------------------------
     *      |    |  16 |    | 36   |    |    |    |    |
     *      -----------------------------------------
     *      ||||||    |   |    |    |    |    ||||||
     *      -----------------------------------------
     *
    */
    
    b.addChip(5,0,playerColor);
    b.addChip(3,6,playerColor);
    //b.addChip(2,7,playerColor);
    b.addChip(1,6,playerColor);
    b.addChip(4,1,playerColor);
    //b.addChip(5,2,opponentColor);
    b.addChip(1,4,playerColor);
    
    System.out.println("The board looks like: ");
    System.out.println(b);
    System.out.println("The score of this board is: " + b.evaluateBoard(playerColor));
  }

}
