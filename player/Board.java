package player;

import list.*;

/*
 * The Board Class. This is used as an internal representation of the Network
 * game board for the MachinePlayer.
 */

public class Board {

  public static final int BLACK = 0;
  public static final int WHITE = 1;
  public static final int BLOCKED = -1;
  public static final int EMPTY = 2;
  public static final int DIMENSION = 8;

  /*
   * Board is a 2-dimensional integer array that holds chips that are represented by
   * integers. myColor indicates MachinePlayer's color. opponentColor indicates
   * the opponent's color. machinePlayerChips and opponentChips keep track of
   * the number of chips on board for the machine player and opponent,
   * respectively.
   */
  public int[][] board;
  public int myColor;
  public int opponentColor;

  public int machinePlayerChips;
  public int opponentChips;

  /*
   * The default constructor Creates an 8 x 8 board with four blocked corners, 
   * represented by the BLOCKED constant, and all empty entries represented
   * by the EMPTY constant.
   * @param playerColor indicates the color of machine player
   */
  public Board(int playerColor) {
    board = new int[DIMENSION][DIMENSION];
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        board[i][j] = EMPTY;
      }
    }
    board[0][0] = board[0][DIMENSION - 1] = board[DIMENSION - 1][0] = board[DIMENSION - 1][DIMENSION - 1] = BLOCKED;
    if (playerColor == BLACK) {
      this.myColor = BLACK;
      opponentColor = WHITE;
    } else if (playerColor == WHITE) {
      this.myColor = WHITE;
      opponentColor = BLACK;
    }
    machinePlayerChips = 0;
    opponentChips = 0;
  }

  /*
   * This constructor creates a new board that is the exact replica of a given
   * board.
   * @param b is the given board.
   */
  public Board(Board b) {
    board = new int[DIMENSION][DIMENSION];
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        board[i][j] = b.board[i][j];
      }
    }
  }

  /*
   * addChip() inserts a chip at the specified coordinates on the board
   * @param x and @param y are the coordinates at which chip is to be added.
   * @param color is the color of the chip that is to be added.
   */
  private void addChip(int x, int y, int color) {
    if (color == myColor) {
      board[x][y] = myColor;
      machinePlayerChips++;
    } else if (color == opponentColor) {
      board[x][y] = opponentColor;
      opponentChips++;
    } else {
      System.out.println("ERROR: Cannot add chip. Board already contains twenty chips.");
    }
  }

  /*
   * moveChip() moves a chip from one cell to another. 
   * @param x1 and @param y1 are the new coordinates of the Chip.
   * @param x2 and @param y2 are the old coordinates of the Chip.
   * @param color is the color of the chip.
   */
  private void moveChip(int x1, int y1, int x2, int y2, int color) {
    if (color == myColor) {
      board[x1][y1] = board[x2][y2];
      board[x2][y2] = EMPTY;
    } else if (color == opponentColor) {
      board[x1][y1] = board[x2][y2];
      board[x2][y2] = EMPTY;
    } else {
      System.out.println("ERROR: Chip cannot be moved. Less than ten chips on the board.");
    }
  }

  /*
   * removeChip() removes the existing Chip at the specified coordinates.
   * @param x and @param y are the coordinates where the to-be-removed chip
   * exists. @param color is the color of that chip.
   */
  private void removeChip(int x, int y, int color) {
    if (color == myColor) {
      machinePlayerChips--;
    } else if (color == opponentColor) {
      opponentChips--;
    }
    board[x][y] = EMPTY;
  }

  /**
   * Get the valued stored in cell (x, y).
   * 
   * @param x
   *          is the x-index.
   * @param y
   *          is the y-index.
   * @return the stored value (between 0 and 2).
   */
  public int elementAt(int x, int y) {
    return board[x][y];
  }

  /*
   * isValidMove() returns a boolean indicating whether or not a given Move is
   * valid or not.
   * @param m is the given Move, @param color is the color of the player making
   * the Move
   * @return true if m is valid
   */
  public boolean isValidMove(Move m, int color) {
    if (!isValidPlace(m.x1, m.y1, color)) {
      return false;
    }
    int prev = board[m.x2][m.y2];
    if (m.moveKind == Move.STEP) {
      board[m.x2][m.y2] = EMPTY;
    }
    int adjacents[] = adjacents(m.x1, m.y1);
    int adjCount = adjacentCount(adjacents, color);
    if (adjCount >= 2) {
      board[m.x2][m.y2] = prev;
      return false;
    } else if (adjCount == 1) {
      int[][] adjCoordinates = adjacentCoordinates(m.x1, m.y1);
      int[] adjColor = new int[2];
      for (int[] adj : adjCoordinates) {
        if (board[adj[0]][adj[1]] == color) {
          adjColor[0] = adj[0];
          adjColor[1] = adj[1];
        }
      }
      if (adjacentCount(adjacents(adjColor[0], adjColor[1]), color) >= 1) {
        board[m.x2][m.y2] = prev;
        return false;
      }
    }
    board[m.x2][m.y2] = prev;
    return true;
  }

  /*
   * performMove() executes the given move depending on the moveKind of the
   * move. 
   * @param m is the move to be performed
   */
  public void performMove(Move m, int color) {
    if (m.moveKind == Move.ADD) {
      addChip(m.x1, m.y1, color);
    } else if (m.moveKind == Move.STEP) {
      moveChip(m.x1, m.y1, m.x2, m.y2, color);
    } else {
      return;
    }
  }

  /*
   * undoMove() reverses the actions of a given move. 
   * @param m is the move to be
   * reversed.
   */
  public void undoMove(Move m, int color) {
    if (m.moveKind == 1) {
      removeChip(m.x1, m.y1, color);
    } else if (m.moveKind == 2) {
      moveChip(m.x2, m.y2, m.x1, m.y1, color);
    }
  }

  /*
   * validMoves() returns a MoveList of moves that are valid for a given board. If
   * the board contains less than 20 chips, the method returns ADD moves that
   * can be made to the board. If the board contains more than 20 chips, the
   * method returns STEP moves that can be made to the board.
   * @param color represents the player whose valid moves it's seeking
   * @return MoveList containing moves
   */
  public MoveList validMoves(int color) {
    MoveList validMoves = new MoveList();
    int numOfChips = 0;
    if (color == myColor) {
      numOfChips = machinePlayerChips;
    } else if (color == opponentColor) {
      numOfChips = opponentChips;
    }

    if (numOfChips < 10) {
      for (int i = 0; i < DIMENSION; i++) {
        for (int j = 0; j < DIMENSION; j++) {
          Move newMove = new Move(i, j);
          if (isValidMove(newMove, color)) {
            validMoves.insertFront(newMove);
          }
        }
      }
    } else {
      int[][] emptyCells = new int[DIMENSION * DIMENSION][];
      for (int i = 0; i < DIMENSION; i++) {
        for (int j = 0; j < DIMENSION; j++) {
          if (board[i][j] == EMPTY) {
            emptyCells[i * DIMENSION + j] = new int[2];
            emptyCells[i * DIMENSION + j][0] = i;
            emptyCells[i * DIMENSION + j][1] = j;
          }
        }
      }
      for (int i = 0; i < DIMENSION; i++) {
        for (int j = 0; j < DIMENSION; j++) {
          if (board[i][j] == color) {
            for (int[] cell : emptyCells) {
              if (cell != null) {
                Move newMove = new Move(cell[0], cell[1], i, j);
                if (isValidMove(newMove, color)) {
                  validMoves.insertFront(newMove);
                }
              }
            }
          }
        }
      }
    }
    return validMoves;
  }

  /*
   * hasValidNetwork() returns a boolean indicating whether there is a existing
   * network on the board.
   * @param color is the color being checked for a network.
   * @return true if valid network exists
   */
  public boolean hasValidNetwork(int color) {

    int[][] start = starting(color);
    int[][] noneTransversed = {};
    boolean[] paths = new boolean[start.length];
    int pathsIndex = 0;
    for (int[] s : start) {
      paths[pathsIndex++] = canReachEnd(s[0], s[1], noneTransversed, color, 1, 0, 0);
    }
    for (boolean end : paths) {
      if (end) {
        return true;
      }
    }
    return false;
  }

  /*
   * evaluateBoard() returns an integer indicating the score of the board. The max
   * value of a board is 500, which indicates that machine player has a valid network. 
   * The minimum score is -500, which indicates that the opponent has a valid network.
   * @param color is the color of the chips to be evaluated
   * @return an integer representing the score of the board.
   */
  public int evaluateBoard(int color) {
    if (hasValidNetwork(color)) {
      if (color == myColor) {
        return 500;
      } else {
        return -500;
      }
    }
    return pairCount(color) - pairCount(Math.abs(color - 1));
  }

  /*
   * equals() tests equality of the board, which is defined by identical board
   * configuration.
   * @param board is the board to be compared to the current board.
   * @return true if the boards are equal
   */
  public boolean equals(Object board) {
    try {
      Board b = (Board) board;
      for (int i = 0; i < DIMENSION; i++) {
        for (int j = 0; j < DIMENSION; j++) {
          if (b.board[i][j] != this.board[i][j]) {
            return false;
          }
        }
      }
      return true;
    } catch (ClassCastException c) {
      System.out.println("Object is not board.");
      return false;
    }
  }

  /*
   * hashCode() returns a unique number for a board.
   * @return integer that is the hash code
   */
  public int hashCode() {
    int hashVal = 0;
    int pow = DIMENSION * DIMENSION - 1;
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        hashVal += elementAt(i, j) * Math.pow(3, pow) % 16908799;
        pow--;
      }
    }
    return hashVal;
  }

  /*
   * numberOfChips() returns the number of chips of a specified color.
   * @param color is the specified color.
   */
  public int numberOfChips(int color) {
    if (color == myColor) {
      return machinePlayerChips;
    } else {
      return opponentChips;
    }
  }

  /******************************************
   * * HELPER METHODS BELOW * *
   */

  /*
   * isValidPlace() is a helper function for isValidMove().
   */
  public boolean isValidPlace(int x, int y, int color) {
    if (color == BLACK) {
      return !(x == 0) && !(x == 7) && board[x][y] == EMPTY;
    } else {
      return !(y == 0) && !(y == 7) && board[x][y] == EMPTY;
    }
  }

  /*
   * pairCount() counts the number of connected good pairs.
   */
  public int pairCount(int color) {
    int pairConnectionCount = 0;
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        if (board[i][j] == color) {
          int[][] coords = connectionCoordinates(i, j, color);
          for (int[] c : coords) {
            if (color == BLACK && (j == 0 || j == 7) && (c[1] == 0 || c[1] == 7)) {
              continue;
            } else if (color == WHITE && (i == 0 || j == 7) && (c[0] == 0 || c[0] == 7)) {
              continue;
            } else {
              pairConnectionCount++;
            }
          }
        }
      }
    }
    return pairConnectionCount;
  }

  /*
   * adjacentCoordinates() is a method that returns a double array of ints,
   * representing the coordinates of the spaces next to the given x and y, the
   * indices of the first array representing the different coordinates, and the
   * second array representing the x and y values.
   * @param cx and cy are the given x and y values.
   */
  private int[][] adjacentCoordinates(int cx, int cy) {
    if (cx == 0) {
      int[][] coords = { { cx, cy - 1 }, { cx + 1, cy - 1 }, { cx + 1, cy }, { cx + 1, cy + 1 },
          { cx, cy + 1 } };
      return coords;
    } else if (cx == 7) {
      int[][] coords = { { cx, cy - 1 }, { cx, cy + 1 }, { cx - 1, cy + 1 }, { cx - 1, cy },
          { cx - 1, cy - 1 } };
      return coords;
    } else if (cy == 0) {
      int[][] coords = { { cx + 1, cy }, { cx + 1, cy + 1 }, { cx, cy + 1 }, { cx - 1, cy + 1 },
          { cx - 1, cy } };
      return coords;
    } else if (cy == 7) {
      int[][] coords = { { cx, cy - 1 }, { cx + 1, cy - 1 }, { cx + 1, cy }, { cx - 1, cy },
          { cx - 1, cy - 1 } };
      return coords;
    } else {
      int[][] coords = { { cx, cy - 1 }, { cx + 1, cy - 1 }, { cx + 1, cy }, { cx + 1, cy + 1 },
          { cx, cy + 1 }, { cx - 1, cy + 1 }, { cx - 1, cy }, { cx - 1, cy - 1 } };
      return coords;
    }
  }

  /*
   * adjacents() is a helper function for isValidMove(). it returns an array of
   * ints that are adjacent (up, down, left, right, all four diagonals) to a
   * location given by two coordinates.
   * @param cx and @param cy are the coordinates given.
   */
  private int[] adjacents(int cx, int cy) {
    int[][] adjc = adjacentCoordinates(cx, cy);
    int[] adjacents;
    if (cx == 0 || cx == 7 || cy == 0 || cy == 7) {
      int[] sub_adjacents = { board[adjc[0][0]][adjc[0][1]], board[adjc[1][0]][adjc[1][1]],
          board[adjc[2][0]][adjc[2][1]], board[adjc[3][0]][adjc[3][1]],
          board[adjc[4][0]][adjc[4][1]] };
      adjacents = sub_adjacents;
    } else {
      int[] mid_adjacents = { board[adjc[0][0]][adjc[0][1]], board[adjc[1][0]][adjc[1][1]],
          board[adjc[2][0]][adjc[2][1]], board[adjc[3][0]][adjc[3][1]],
          board[adjc[4][0]][adjc[4][1]], board[adjc[5][0]][adjc[5][1]],
          board[adjc[6][0]][adjc[6][1]], board[adjc[7][0]][adjc[7][1]] };
      adjacents = mid_adjacents;
    }
    return adjacents;
  }

  /*
   * adjacentCount() is yet another helper method for isValidMove(). this method
   * returns the number of adjacent Chips of the same color.
   * @param adjacents is the Chip array of adjacent Chips, @param color is the
   * color we compare to.
   */
  private int adjacentCount(int[] adjacents, int color) {
    int adjCount = 0;
    for (int c : adjacents) {
      if (c == color) {
        adjCount++;
      }
    }
    return adjCount;
  }

  /*
   * starting() returns a double array of the starting chips of the player of
   * the given color
   * @param color is the color of the player in consideration
   */
  public int[][] starting(int color) {
    int count = 0, index = 0;
    int[][] start;
    if (color == WHITE) {
      for (int i : board[0]) {
        if (i == color) {
          count++;
        }
      }
      start = new int[count][2];
      for (int i = 1; i < DIMENSION - 1; i++) {
        if (board[0][i] == color) {
          start[index][0] = 0;
          start[index++][1] = i;
        }
      }
    } else {
      for (int[] i : board) {
        if (i[0] == color) {
          count++;
        }
      }
      start = new int[count][2];
      for (int i = 1; i < DIMENSION - 1; i++) {
        if (board[i][0] == color) {
          start[index][0] = i;
          start[index++][1] = 0;
        }
      }
    }
    return start;
  }

  /*
   * addBolean() is a helper function for hasValidNetwork().
   */
  public static boolean[] addBoolean(boolean[] booleans, boolean addend) {
    boolean[] added = new boolean[booleans.length + 1];
    for (int i = 0; i < booleans.length; i++) {
      added[i] = booleans[i];
    }
    added[added.length - 1] = addend;
    return added;
  }

  /*
   * transpose() returns the board's transpose.
   */
  private int[][] transpose() {
    int[][] t = new int[DIMENSION][DIMENSION];
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        t[j][i] = board[i][j];
      }
    }
    return t;
  }

  /*
   * singleMerge() merges two int arrays.
   * @param first is the first array, @param second is the second array we want
   * to combine.
   */
  public static int[] singleMerge(int[] first, int[] second) {
    int[] merged = new int[first.length + second.length];
    int index = 0;
    for (int i = 0; i < first.length; i++) {
      merged[index++] = first[i];
    }
    for (int j = 0; j < second.length; j++) {
      merged[index++] = second[j];
    }
    return merged;
  }

  /*
   * doubleMerge() merges two int[] arrays. 0 index represents the x coordinate,
   * 1 corresponds to y.
   * @param first is the first array, @param second is the second array we want
   * to combine
   */
  public static int[][] doubleMerge(int[][] first, int[][] second) {
    int[][] merged = new int[first.length + second.length][2];
    int index = 0;
    for (int i = 0; i < first.length; i++) {
      merged[index][0] = first[i][0];
      merged[index++][1] = first[i][1];
    }
    for (int j = 0; j < second.length; j++) {
      merged[index][0] = second[j][0];
      merged[index++][1] = second[j][1];
    }
    return merged;
  }

  /*
   * subtract() subtracts the coordinates in the second double array of ints
   * from the first.
   * @param first is the array we want to subtract from, @param second has the
   * coordinates we want to subtract
   */
  public static int[][] subtract(int[][] first, int[][] second) {
    int[][] passed = new int[second.length][2];
    int passedIndex = 0;
    int subtractLength = first.length;
    for (int i = 0; i < first.length; i++) {
      for (int j = 0; j < second.length; j++) {
        if (first[i][0] == second[j][0] && first[i][1] == second[j][1]) {
          boolean skip = false;
          for (int[] p : passed) {
            if (p != null && p[0] == second[j][0] && p[1] == second[j][1]) {
              skip = true;
            }
          }
          if (skip) {
            continue;
          }
          subtractLength--;
          passed[passedIndex][0] = second[j][0];
          passed[passedIndex++][1] = second[j][1];
        }
      }
    }
    int[][] subtracted = new int[subtractLength][2];
    int index = 0;
    for (int i = 0; i < first.length; i++) {
      boolean inSecond = false;
      for (int j = 0; j < second.length; j++) {
        if (first[i][0] == second[j][0] && first[i][1] == second[j][1]) {
          inSecond = true;
        }
      }
      if (!inSecond) {
        subtracted[index][0] = first[i][0];
        subtracted[index++][1] = first[i][1];
      }
    }
    return subtracted;
  }

  /*
   * connectionCoordinates() returns a double array of coordinates of chips that
   * can be connected to from the space and color determined by the given x, y,
   * and color
   * @param x is the given x, @param y is the given y, @color is the color of
   * the chips we want to connect to
   */
  public int[][] connectionCoordinates(int x, int y, int color) {
    int coordinateCount = 0;
    int opp;
    if (color == BLACK) {
      opp = WHITE;
    } else {
      opp = BLACK;
    }
    int[][] coordinates = { dirCoords(x, y, 0, -1, color, opp), dirCoords(x, y, 1, -1, color, opp),
        dirCoords(x, y, 1, 0, color, opp), dirCoords(x, y, 1, 1, color, opp),
        dirCoords(x, y, 0, 1, color, opp), dirCoords(x, y, -1, 1, color, opp),
        dirCoords(x, y, -1, 0, color, opp), dirCoords(x, y, -1, -1, color, opp) };
    for (int[] coord : coordinates) {
      if (coord != null) {
        coordinateCount++;
      }
    }
    int[][] shortenedCoordinates = new int[coordinateCount][2];
    int index = 0;
    for (int[] coord : coordinates) {
      if (coord != null) {
        shortenedCoordinates[index][0] = coord[0];
        shortenedCoordinates[index++][1] = coord[1];
      }
    }
    return shortenedCoordinates;
  }

  /*
   * dirCoords() returns the coordinates of the nearest space in a given
   * direction containing the same color. returns null if the method reaches the
   * end of the board or reaches a space that's blocked or of the opposing
   * player.
   * @param cx and cy determine the starting coordinates, @param hMod and vMod
   * indicate which direction we are considering, @param color is this player's
   * color, @param oppColor is the opponent's color.
   */
  public int[] dirCoords(int cx, int cy, int hMod, int vMod, int color, int oppColor) {
    while (true) {
      cx += hMod;
      cy += vMod;
      if (cx < 0 || cy < 0 || cx > 7 || cy > 7 || (color == BLACK && cy == 0)
          || (color == WHITE && cx == 0)) {
        return null;
      }
      int compare = board[cx][cy];
      if (compare == color) {
        int[] coordinates = { cx, cy };
        return coordinates;
      } else if (compare == oppColor || compare == BLOCKED) {
        return null;
      }
    }
  }

  /*
   * canReachEnd() is a helper method for hasValidNetwork(). tells us if a chip
   * can read the goal.
   */
  public boolean canReachEnd(int x, int y, int[][] used, int color, int length, int prevx, int prevy) {
    int oppColor;
    if (color == BLACK) {
      oppColor = WHITE;
    } else {
      oppColor = BLACK;
    }
    if (color == WHITE && x == 7 || color == BLACK && y == 7) {
      return length > 5;
    }
    boolean[] endPoints = {};
    int[][] connections = connectionCoordinates(x, y, color);
    connections = subtract(connections, used);
    int[] unusedDirection = sameDirection(prevx, prevy, x, y);
    if (dirCoords(x, y, unusedDirection[0], unusedDirection[1], color, oppColor) != null) {
      int[][] prevDirection = { dirCoords(x, y, unusedDirection[0], unusedDirection[1], color,
          oppColor) };
      connections = subtract(connections, prevDirection);
    }
    for (int[] i : connections) {
      int[][] currentUsed = { { x, y } };
      int[][] newUsed = doubleMerge(currentUsed, used);
      boolean open = canReachEnd(i[0], i[1], newUsed, color, length + 1, x, y);
      endPoints = addBoolean(endPoints, open);
    }
    for (boolean e : endPoints) {
      if (e) {
        return true;
      }
    }
    return false;
  }

  /*
   * sameDirection() is another helper for hasValidNetwork. helps to make sure
   * you don't make connections in the a direction more than once in a row.
   */
  public int[] sameDirection(int x, int y, int prevx, int prevy) {
    int[] empty = {};
    if (prevx == 0 && prevy == 0) {
      return empty;
    }
    int subx = prevx - x;
    int suby = prevy - y;
    int factor = 1;
    if (subx != 0) {
      factor = Math.abs(subx);
    } else if (suby != 0) {
      factor = Math.abs(suby);
    }
    int[] coords = { subx / factor, suby / factor };
    return coords;
  }

  /*
   * toString() prints a string representation of the board.
   */
  public String toString() {
    String border = "-----------------------------------------\n";
    String b = "";
    for (int[] x : transpose()) {
      for (int y : x) {
        if (y == BLOCKED) {
          b += "|||||";
        } else if (y == EMPTY) {
          b += "|    ";
        } else if (y == BLACK) {
          b += "|  B ";
        } else if (y == WHITE) {
          b += "|  W ";
        }
      }
      b += "|\n" + border;
    }
    return border + b;
  }
}
