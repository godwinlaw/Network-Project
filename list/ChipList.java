/* ChipList.java */

package list;

/**
 * A ChipList is a mutable doubly-linked list ADT. Its implementation is
 * circularly-linked and employs a sentinel (dummy) node at the head of the
 * list.
 */

public class ChipList {

  /**
   * head references the sentinel node. size is the number of items in the list.
   */

  protected ChipNode head;
  protected int size;
  protected ChipNode current;
  public int color;

  /**
   * newNode() calls the ChipNode constructor.
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
  protected ChipNode newNode(int x, int y, int color, ChipNode prev, ChipNode next) {
    return new ChipNode(x, y, color, prev, next);
  }

  /**
   * ChipList() constructor for an empty ChipList.
   */
  public ChipList(int color) {
    head = newNode(-1, -1, -1, null, null);
    head.next = head;
    head.prev = head;
    size = 0;
    this.color = color;
  }

  /**
   * isSentinel returns a boolean indicating whether the node in question is the
   * sentinel node.
   */
  public boolean isSentinel(ChipNode n) {
    return (n.xpos == -1) && (n.ypos == -1) && (n.color == -1);
  }

  /**
   * isEmpty() returns true if this ChipList is empty, false otherwise.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * length() returns the length of this ChipList.
   */
  public int length() {
    return size;
  }

  /*
   * findNode locates a ChipNode with the specified coordinates and color.
   * @param x and @param y are the coordinates of the node sought.
   * @param color is the color of that node.
   */
  public ChipNode findNode(int x, int y, int color) {
    resetIterator();
    while(hasNext()) {
      ChipNode node = nextChip();
      if (node.xpos == x && node.ypos == y && node.color == color) {
        return node;
      }
    }
    System.out.println("ERROR: Node not found");
    return null;
  }
  
  /**
   * insertFront() inserts an item at the front of this ChipList.
   * 
   * @param x, @param y, and @param color are the coordinates and the color of
   *          the chip to be inserted
   */
  public void insertFront(int x, int y, int color) {
    if (size == 0) {
      head.next = newNode(x, y, color, head, head);
      head.prev = head.next;
      current = head.next;
    } else {
      head.next = newNode(x, y, color, head, head.next);
      head.next.next.prev = head.next;
    }
    size++;
  }

  /**
   * front() returns the node at the front of this ChipList.
   */
  public ChipNode front() {
    if (size == 0) {
      return null;
    } else {
      return head.next;
    }
  }

  /**
   * remove() removes "node" from this ChipList. If "node" is null, do nothing.
   */
  public void remove(ChipNode node) {
    if (node != null && size > 0) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
      size--;
    }
  }

  public boolean hasNext() {
    if (current == head) {
      resetIterator();
      return false;
    } else {
      return true;
    }
  }
  
  public ChipNode nextChip() {
    ChipNode c = current;
    current = current.next;
    return c;
  }
  
  private void resetIterator() {
    current = front();
  }
  
  public String toString() {
    String l = "";
    resetIterator();
    while(hasNext()) {
      ChipNode chip = nextChip();
      l += "( " + chip.xpos + "," + chip.ypos + " ) ";
    }
    return l;
  }
}
