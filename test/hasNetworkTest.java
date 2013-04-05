package test;

import player.Board;

public class hasNetworkTest {

  public static void main(String[] args) {
    int playerColor = Board.BLACK; 
    Board b = new Board(playerColor);
    
    b.addChip(6, 0, playerColor);
    b.addChip(6, 5, playerColor);
    b.addChip(5, 5, playerColor);
    b.addChip(3, 3, playerColor);
    b.addChip(3, 5, playerColor);
    b.addChip(5, 7, playerColor);
    
    System.out.println("The board looks like:");
    System.out.println(b);
    
    System.out.println("The board has a valid network. Should return true: " + b.hasValidNetwork(playerColor));
    
    
    Board c = new Board(playerColor);
    
    c.addChip(2, 0, playerColor);
    c.addChip(2, 5, playerColor);
    c.addChip(3, 5, playerColor);
    c.addChip(1, 3, playerColor);
    c.addChip(3, 3, playerColor);
    c.addChip(5, 7, playerColor);
    c.addChip(5, 5, playerColor);
    
    System.out.println("The board looks like:");
    System.out.println(c);
    
    System.out.println("The board has a valid network. Should return true: " + c.hasValidNetwork(playerColor));
    
    Board d = new Board(playerColor);
    
    d.addChip(6, 0, playerColor);
    d.addChip(6, 5, playerColor);
    d.addChip(5, 5, playerColor);
    d.addChip(3, 3, playerColor);
    d.addChip(3, 5, playerColor);
    d.addChip(5, 7, playerColor);
    d.addChip(2, 0, playerColor);
    d.addChip(2, 5, playerColor);
    d.addChip(3, 5, playerColor);
    d.addChip(1, 3, playerColor);
    d.addChip(3, 3, playerColor);
    d.addChip(5, 7, playerColor);
    d.addChip(5, 5, playerColor);
    

    System.out.println("The board looks like:");
    System.out.println(d);
    
    System.out.println("The board has a valid network. Should return true: " + d.hasValidNetwork(playerColor));
    
  
  }
}
