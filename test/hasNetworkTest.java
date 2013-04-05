package test;

import player.Board;

public class hasNetworkTest {

  public static void main(String[] args) {
    int playerColor = Board.BLACK; 
    int opponentColor = Board.WHITE;
    Board b = new Board(playerColor);
    
    b.addChip(6, 0, playerColor);
    b.addChip(6, 5, playerColor);
    b.addChip(5, 5, playerColor);
    b.addChip(3, 3, playerColor);
    b.addChip(3, 5, playerColor);
    b.addChip(5, 7, playerColor);
    
    System.out.println("The board looks like:");
    System.out.println(b);
    
    System.out.println("The board has a valid network: " + b.hasValidNetwork(playerColor));
  }

}
