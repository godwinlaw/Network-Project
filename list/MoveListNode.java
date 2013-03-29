/* MoveListNode.java */

package list;

import player.Move;

/**
 *  A MoveListNode is a node in a Chip (doubly-linked list). It represents a chip on the board.
 */

public class MoveListNode {

  /**
   *  xpos and ypos references the coordinates of the current chip.
   *  color references the color of the current chip.
   *  prev references the previous node in the Chip.
   *  next references the next node in the Chip.
   *
   */

  protected Move move;
  protected MoveListNode prev;
  protected MoveListNode next;

  /**
   *  MoveListNode() constructor.
   *  @param x and @param y are the coordinates of the chip.
   *  @param color is the color of the chip.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  MoveListNode(Move m, MoveListNode p, MoveListNode n) {
    move = m;
    prev = p;
    next = n;
  }

  public Move move() {
    return move;
  }
  
  public MoveListNode next() {
    return next;
  }
  
  public MoveListNode prev() {
    return prev;
  }
}
