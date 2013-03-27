/* ChipNode.java */

package list;

/**
 *  A ChipNode is a node in a Chip (doubly-linked list). It represents a chip on the board.
 */

public class ChipNode {

  /**
   *  xpos and ypos references the coordinates of the current chip.
   *  color references the color of the current chip.
   *  prev references the previous node in the Chip.
   *  next references the next node in the Chip.
   *
   */

  public int xpos;
  public int ypos;
  public int color;
  protected ChipNode prev;
  protected ChipNode next;

  /**
   *  ChipNode() constructor.
   *  @param x and @param y are the coordinates of the chip.
   *  @param color is the color of the chip.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  ChipNode(int x, int y, int color, ChipNode p, ChipNode n) {
    xpos = x;
    ypos = y;
    prev = p;
    next = n;
  }
  
  /*
   * updateCoordinates() changes the coordinates of this node to the new
   * coordinates specified.
   * @param x and @param y are the new coordinates.
   */
  public void updateCoordinates(int x, int y) {
    xpos = x;
    ypos = y;
  }
}
