/* MoveList.java */

package list;

import player.Move;

/**
 * A MoveList is a mutable doubly-linked list ADT. Its implementation is
 * circularly-linked and employs a sentinel (dummy) node at the head of the
 * list.
 */

public class MoveList {

  /**
   * head references the sentinel node. size is the number of items in the list.
   */

  protected MoveListNode head;
  protected int size;
  protected MoveListNode current;
  public int count = 0;

  /**
   * newNode() calls the MoveListNode constructor.
   * 
   * @param x
   *          and @param y are the coordinates of the chip
   * @param color
   *          is the color of the chip
   * @param prev
   *          is the node previous to this node.
   * @param next
   *          is the node following this node.
   */
  protected MoveListNode newNode(Move m, MoveListNode prev, MoveListNode next) {
    return new MoveListNode(m, prev, next);
  }

  /**
   * MoveList() constructor for an empty MoveList.
   */
  public MoveList() {
    head = newNode(null , null, null);
    head.next = head;
    head.prev = head;
    size = 0;
  }

  /**
   * isSentinel returns a boolean indicating whether the node in question is the
   * sentinel node.
   * @param n is the node in question
   * @returns true if the node is a sentinel
   */
  public boolean isSentinel(MoveListNode n) {
    return n.move == null;
  }

  /**
   * length() returns the length of this MoveList.
   */
  public int length() {
    return size;
  }

  /**
   * insertFront() inserts an item at the front of this MoveList.
   * 
   * @param x, @param y, and @param color are the coordinates and the color of
   *          the chip to be inserted
   */
  public void insertFront(Move m) {
    if (size == 0) {
      head.next = newNode(m, head, head);
      head.prev = head.next;
    } else {
      head.next = newNode(m, head, head.next);
      head.next.next.prev = head.next;
    }
    current = head.next;
    size++;
  }

  /**
   * front() returns the node at the front of this MoveList.
   * @returns a MoveListNode
   */
  private MoveListNode front() {
    if (size == 0) {
      return null;
    } else {
      return head.next;
    }
  }

  /**
   * elementAt returns the move at index k
   * @param k is the index
   * @returns a move
   */
  public Move elementAt(int k) {
    MoveListNode current = head.next;
    while (k > 0) {
      current = current.next;
      k --;
    }
    return current.move;
  }
  
  /**
   * hasNext() checks to see if the iterator has reached the end of the list.
   * @returns true if it hasn't.
   */
  public boolean hasNext() {
    if (current == head) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * nextMove() returns the move the iterator is pointed to
   * @returns a Move object
   */
  public Move nextMove() {
    MoveListNode c = current;
    current = current.next;
    return c.move;
  }
  
  /**
   * resetIterator() resets the iterator to the front of the list.
   */
  public void resetIterator() {
    current = front();
  }
}
