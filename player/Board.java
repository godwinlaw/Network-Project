package player;

import list.*;

/*
 * The Board Class (2D int array)
 * This is used as an internal representation of the Network
 * game board for the MachinePlayer.
 */

public class Board {
  
  public static final int BLACK = 0;
  public static final int WHITE = 1;
  public static final int BLOCKED = -1;
  public static final int EMPTY = 2;
  public static final int DIMENSION = 8;
  
  /*
   * board is an array that holds Chip objects. 
   * myColor indicates MachinePlayer's color.
   * opponentColor indicates the opponent's color.
   * MachinePlayreChipsLocations is a ChipList that holds all the information about MachinePlayer's chips on the board.
   * opponentChipsLocation holds the same information about the opponent's chips.
   */
  private int[][] board;
  public int myColor;
  public int opponentColor;
  private ChipList chipsLocations;
  private ChipList opponentChipsLocations;
 
  /*
   * The default constructor Creates a 8 x 8 board holding Chip objects. The
   * corners of the board are initialized to have "fake" Chip objects (See
   * documentation in Chip class). No chips may be placed in these locations.
   */
  public Board(int playerColor) {
    board = new int[DIMENSION][DIMENSION];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        board[i][j] = EMPTY;
      }
    }
    board[0][0] = board[0][7] = board[7][0] = board[7][7] = BLOCKED;
    if (playerColor == BLACK) {
      this.myColor = BLACK;
      opponentColor = WHITE;
    } else if (playerColor == WHITE) {
      this.myColor = WHITE;
      opponentColor = BLACK;
    }
    chipsLocations = new ChipList(myColor);
    opponentChipsLocations = new ChipList(opponentColor);
  }

  /*
   * addChip() inserts a Chip object at the specified coordinates on the board if there are less than 10 chips on the board.
   * Also records it in the player's ChipList.
   * @param x and @param y are the coordinates at which chip is to be added.
   * @param color is the color of the chip that is to be added.
   */
  private void addChip(int x, int y, int color) {
    if (color == myColor && chipsLocations.length() < 10) {
      chipsLocations.insertFront(x, y, color);
      board[x][y] = myColor;
    } else if (color == opponentColor && opponentChipsLocations.length() < 10) {
      opponentChipsLocations.insertFront(x, y, color);
      board[x][y] = opponentColor;
    } else {
      System.out.println("ERROR: Cannot add chip. Board already contains twenty chips.");
    }
  }

  /*
   * moveChip() moves a Chip from one cell to another if and only if there are 10 chips on the board.
   * Also records it in the player's ChipList.
   * @param x1 and @param y1 are the new coordinates of the Chip.
   * @param x2 and @param y2 are the old coordinates of the Chip.
   * @param color is the color of the chip.
   */
  private void moveChip(int x1, int y1, int x2, int y2, int color) {
    if (color == myColor && chipsLocations.length() == 10) {
      board[x1][y1] = board[y2][x2];
      board[x2][y2] = EMPTY;
      chipsLocations.findNode(x2,y2,color).updateCoordinates(x1,y1);
    } else if (color == opponentColor && opponentChipsLocations.length() == 10) {
      board[x1][y1] = board[y2][x2];
      board[x2][y2] = EMPTY;
      opponentChipsLocations.findNode(x2,y2,color).updateCoordinates(x1,y1);
    } else {
      System.out.println("ERROR: Chip cannot be moved. There are still less than ten chips on the board.");
    }
  }

  /*
   * removeChip() removes the existing Chip at the specified coordinates.
   * @param x and @param y are the coordinates where the to-be-removed chip
   * exists.
   */
  private void removeChip(int x, int y, int color) {
    board[x][y] = EMPTY;
    if (color == myColor) {
      chipsLocations.remove(chipsLocations.findNode(x,y,color));
    } else if (color == opponentColor) {
      opponentChipsLocations.remove(chipsLocations.findNode(x,y,color));
    }
  }
  
  /**
   *  Get the valued stored in cell (x, y).
   *  @param x is the x-index.
   *  @param y is the y-index.
   *  @return the stored value (between 0 and 2).
   *  @exception ArrayIndexOutOfBoundsException is thrown if an invalid index
   *  is given.
   */

  public int elementAt(int x, int y) {
    return board[x][y];
  }

  /*
   *  isValidMove() returns a boolean indicating whether or not a given Move is valid or not.  
   */
  public boolean isValidMove(Move m, int color) {
    boolean isValidPlace = false;
    if (color == BLACK) {
      isValidPlace = !(m.x1==0) && !(m.x1==7) && board[m.x1][m.y1]==EMPTY;
    } else if (color == WHITE){
      isValidPlace = !(m.y1==0) && !(m.y1==7) && board[m.x1][m.y1]==EMPTY;
    }
    if (!isValidPlace) {
      return false;
    }
    return true;
  }

  /*
   * countNetworks() returns an int-array with information about possible
   * networks that are forming. For example, if there are 7 chips placed on the
   * board, with 2 potential networks: one with 3 linked chips and the other
   * with 4 linked chips, then the array to return will look like this [3,4].
   * !!!PLEASE USE THE CHIPLISTS I HAVE PREPARED FOR YOU (chipLocations && opponentChipLocations)!!!
   */
  public int[] countNetworks() {
    // code here
    return null;
  }

  /*
   * hasValidNetwork() returns a boolean indicating whether there is a existing
   * network on the board.
   */
  public boolean hasValidNetwork() {
    for (int i : countNetworks()) {
      if (i >= 6) {
        return true;
      }
    }
    return false;
  }

  /*
   * performMove() executes the given move depending on the moveKind of the
   * move. @param m is the move to be performed
   */
  public void performMove(Move m, int color) {
    if (m.moveKind == 1) {
      addChip(m.x1, m.x2, color);
    } else if (m.moveKind == 2) {
      moveChip(m.x1, m.y1, m.x2, m.y2, color);
    } else {
      return;
    }
  }

  /*
   * undoMove() reverses the actions of a given move. @param m is the move to be
   * reversed.
   */
  public void undoMove(Move m, int color) {
    if (m.moveKind == 1) {
      removeChip(m.x1, m.y2, color);
    } else if (m.moveKind == 2) {
      moveChip(m.x2, m.y2, m.x1, m.y1, color);
    }
  }

  /*
   * validMoves() returns an array of Moves that are valid for a given board. If
   * the board contains less than 20 chips, the method returns ADD moves that
   * can be made to the board. If the board contains more than 20 chips, the
   * method returns STEP moves that can be made to the board.
   */
  // NEEDS TO BE CHECKED FOR BUGS
  // COORDINATES!!!!
  public MoveList validMoves(int color) {
    MoveList validMoves = new MoveList();
    ChipList chipList = null;
    if (color == myColor) {
      chipList = chipsLocations; 
    } else if (color == opponentColor) {
      chipList = opponentChipsLocations;
    }
    if (chipList.length() < 10) {
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          Move newMove = new Move(i, j);
          if (isValidMove(newMove, color)) {
            validMoves.insertFront(newMove);
          }
        }
      }
    }
/*    } else {
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          if (cell != EMPTY || cell != BLOCKED) {
            ChipNode chip = chipList.findNode(i,j,color); 
            for (int c = 0; c < chip.possibleStepMoves().length; c++) {
              if (isValidMove(chip.possibleStepMoves()[c], color)) {
                validMoves.insertFront(chip.possibleStepMoves()[c]);
              }
            }
          }
        }
      }
    }*/
    return validMoves;
  }

  // INCOMPLETE
  public int evaluateBoard(int color) {
    int score = 0;
    if (hasValidNetwork()) {
      score = 10;
    } else {
      return 1;
    }
    return score;
  }

  public boolean equals(Object board) {
    try {
     Board b = (Board) board;
      if (hashCode() == b.hashCode()) {
        return true;
      } else {
        return false;
      }
    } catch (ClassCastException c) {
      return false;
    }
  }

  public int hashCode() {
    int hashVal = 0;
    int pow = DIMENSION * DIMENSION - 1;
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        hashVal += elementAt(i,j) * Math.pow(3,pow) % 16908799;
        pow --;
      }
    }
    return hashVal;
  } 

}

  /******************************************
   *                                        *
   *         HELPER METHODS BELOW           *
   *                                        *
   ******************************************
   */
 
 