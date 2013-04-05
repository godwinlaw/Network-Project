/* DList.java */

package list;
import dict.*;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 */

public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   */

  protected DListNode head;
  protected int size;

  /**
   *  newNode() calls the DListNode constructor.  
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Entry item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    head = newNode(null, null, null);
    head.next = head;
    head.prev = head;
    size = 0;
  }


  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this DList. 
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }
  
  /**
   * Checks whether given node is the sentinel node.
   * @param d is the given node
   * @returns true if it is the sentinel node
   */
  public boolean isSentinel(DListNode d) {
    if (d == head) {
      return true;
    } else {
      return false;
    }
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Entry item) {
    if (size == 0) {
      head.next = newNode(item, head, head);
      head.prev = head.next;
    } else {
      head.next = newNode(item, head, head.next);
      head.next.next.prev = head.next;
    }
    size ++;
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return null.
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    if (size == 0) {
      return null;
    } else {
      return head.next;
    }
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    if (node != null && size > 0) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
      size --;
    }
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
}
