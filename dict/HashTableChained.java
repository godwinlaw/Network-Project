/* HashTableChained.java */

package dict;

import list.*;
import player.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 **/

public class HashTableChained {

   protected DList[] hashTable;
   protected int tableSize;
   protected int collisions;

  /** 
   *  Constructs a new empty hash table intended to hold roughly sizeEstimate
   *  entries. 
   **/

  public HashTableChained(int sizeEstimate) {
    tableSize = nearestPrime(sizeEstimate * 2);
    hashTable = new DList[tableSize];
    collisions = 0;
  }

  /** 
   *  Construct a new empty hash table with a default size. 
   **/

  public HashTableChained() {
    tableSize = 101;
    hashTable = new DList[tableSize];
    collisions = 0;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   **/

  int compFunction(int code) {
    int a = 3, b = 7, largePrime = 16908799;
    int index = (Math.abs(a * code + b) % largePrime) % tableSize;
    return index;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    int size = 0;
    for (DList entry : hashTable) {
      if (entry != null) {
        size += entry.length();
      }
    }
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Board b, int bscore) {
    Entry entry = new Entry();
    entry.board = b;
    entry.boardScore = bscore;
    DList chain = hashTable[compFunction(b.hashCode())];
    if (chain != null) {
      chain.insertFront(entry);
      collisions ++;
    } else {
      chain = new DList();
      chain.insertFront(entry);
      hashTable[compFunction(b.hashCode())] = chain;
    }
    return entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public int findScore(Board b) {
    DList chain = hashTable[compFunction(b.hashCode())];
    if (chain != null) {
      DListNode current = chain.front();
      while (current != null) {
        Entry currentEntry = current.item();
        if ((currentEntry.board).equals(b)) {
          return currentEntry.boardScore;
        }
        current = current.next();
      }
    }
    return 0;
  }
  
  /**
   * The function checks to see if the board already exists
   * in the hash table.
   * @param b is the board being searched for
   * @return true if the hash table has the board specified
   */
  
  public boolean has(Board b) {
    DList chain = hashTable[compFunction(b.hashCode())];
    if (chain != null) {
      DListNode current = chain.front();
      while (!chain.isSentinel(current)) {
        Board currentBoard = current.item().board();
        if (currentBoard.equals(b)) {
          return true;
        }
        current = current.next();
      }
    }
    return false;
  }

  /**
   *  Removes all entries from the dictionary.
   */
  public void makeEmpty() {
    hashTable = new DList[tableSize];
  }
  
  /**
   * Counts how many collisions occurred while using the hash table.
   * @return
   */
  public int countCollisions() {
    return collisions;
  }
 
  /*
   * A helper method that returns the nearest prime to a specified number.
   * @param num is the specified number
   */
  private int nearestPrime(int num) {
    boolean[] primes = new boolean[num * 2];
    int i;
    for (i = 0; i < primes.length; i++) {
      primes[i] = true;
    }
    int divisor;
    for (divisor = 2; divisor * divisor < primes.length; divisor ++) {
      if (primes[divisor]) {
        for (i = 2 * divisor; i < primes.length; i += divisor) {
          primes[i] = false;
        }
      }
    }
    for (i = num; i < primes.length; i ++) {
      if (primes[i]) {
        return i;
      }
    }
    return 0;
  }

}
