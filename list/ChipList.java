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
    ChipNode current = head;
    while(current != head) {
      if (current.xpos == x && current.ypos == y && current.color == color) {
        return current;
      }
      current = current.next;
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
    } else {
      head.next = newNode(x, y, color, head, head.next);
      head.next.next.prev = head.next;
    }
    size++;
  }

  /**
   * insertBack() inserts an item at the back of this ChipList.
   * 
   * @param x, @param y, and @param color are the coordinates and the color of
   *          the chip to be inserted
   */
  public void insertBack(int x, int y, int color) {
    if (size == 0) {
      insertFront(x, y, color);
    } else {
      head.prev = newNode(x, y, color, head.prev, head);
      head.prev.prev.next = head.prev;
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
   * back() returns the node at the back of this ChipList.
   */
  public ChipNode back() {
    if (size == 0) {
      return null;
    } else {
      return head.prev;
    }
  }

  /**
   * next() returns the node following "node" in this ChipList. If "node" is
   * null, or "node" is the last node in this ChipList, returns null.
   */
  public ChipNode next(ChipNode node) {
    if (node == null || node == head.prev) {
      return null;
    } else {
      return node.next;
    }
  }

  /**
   * prev() returns the node prior to "node" in this ChipList. If "node" is
   * null, or "node" is the first node in this ChipList, returns null.
   */
  public ChipNode prev(ChipNode node) {
    if (node == null || node == head.next) {
      return null;
    } else {
      return node.prev;
    }
  }

  /**
   * insertAfter() inserts an item in this ChipList immediately following
   * "node". If "node" is null, do nothing.
   * @param x, @param y, and @param color are the coordinates and the color of
   *          the chip to be inserted
   */
  public void insertAfter(int x, int y, int color, ChipNode node) {
    if (node != null) {
      node.next = newNode(x, y, color, node, node.next);
      node.next.next.prev = node.next;
      size++;
    }
  }

  /**
   * insertBefore() inserts an item in this ChipList immediately before "node".
   * If "node" is null, do nothing.
   * @param x, @param y, and @param color are the coordinates and the color of
   *          the chip to be inserted
   */
  public void insertBefore(int x, int y, int color, ChipNode node) {
    if (node != null) {
      node.prev = newNode(x, y, color, node.prev, node);
      node.prev.prev.next = node.prev;
      size++;
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


}
