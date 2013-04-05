package test;

import player.Board;
import dict.*;

public class HashTest {

  public static void main(String[] args) {
    int playerColor = Board.BLACK; 
    int opponentColor = Board.WHITE;
    Board a = new Board(playerColor);
    Board b = new Board(opponentColor);
    
    System.out.println("Creating identical boards:");
    a.addChip(1,1,playerColor);
    a.addChip(2,2,playerColor);
    a.addChip(3,3,playerColor);
    a.addChip(4,4,playerColor);
    a.addChip(5,5,playerColor);
    a.addChip(6,6,playerColor);
    System.out.println(a);
    
    b.addChip(1,1,playerColor);
    b.addChip(2,2,playerColor);
    b.addChip(3,3,playerColor);
    b.addChip(4,4,playerColor);
    b.addChip(5,5,playerColor);
    b.addChip(6,6,playerColor);
    System.out.println(b);
    
    HashTableChained table = new HashTableChained();
    
    System.out.println("a's hashcode: " + a.hashCode());
    System.out.println("b's hashcode: " + b.hashCode());
    
    System.out.println("Inserting board into hash table.");
    table.insert(a, 10);
    
    System.out.println("Locating board.");
    System.out.println("Board located = " + table.has(b));
    
    int score = table.findScore(b);
    System.out.println("Score of board is " + score);
  }

}
