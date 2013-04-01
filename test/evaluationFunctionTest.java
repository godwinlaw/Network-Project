/**
 * 
 */
package test;

import player.Board;

/**
 * @author GodwinLaw
 *
 */
public class evaluationFunctionTest {

  public static void main(String[] args) {
    int playerColor = Board.WHITE; 
    int opponentColor = Board.BLACK;
    Board b = new Board(playerColor);

    b.addChip(playerColor);
    b.addChip(playerColor);
    b.addChip(playerColor);
    b.addChip(playerColor);
    b.addChip(playerColor);
    b.addChip(playerColor);
    
    System.out.println();
    
    int[] networks = b.countNetworks();
    
  }

}
