package player;

import list.*;

/*
 * The Board Class This is used as an internal representation of the Network
 * game board for the MachinePlayer.
 */

public class Board {

  /*
   * board is an array that holds Chip objects. 
   * myColor indicates MachinePlayer's color.
   * opponentColor indicates the opponent's color.
   * MachinePlayreChipsLocations is a ChipList that holds all the information about MachinePlayer's chips on the board.
   * opponentChipsLocation holds the same information about the opponent's chips.
   */
  private Chip[][] board;
  public int myColor;
  public int opponentColor;
  private ChipList chipsLocations;
  private ChipList opponentChipsLocations;

  /*
   * The default constructor Creates a 8 x 8 board holding Chip objects. The
   * corners of the board are initialized to have "fake" Chip objects (See
   * documentation in Chip class). No chips may be placed in these locations.
   */
  public Board() {
    board = new Chip[8][8];
    board[0][0] = board[0][7] = board[7][0] = board[7][7] = (new Chip(false));
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
      board[y][x] = new Chip(x, y, color);
    } else if (color == opponentColor && opponentChipsLocations.length() < 10) {
      opponentChipsLocations.insertFront(x, y, color);
      board[y][x] = new Chip(x, y, color);
    } else {
      System.out.println("ERROR: Cannot add chip. Board already has twenty chips.");
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
      board[y1][x1] = board[y2][x2];
      board[y2][x2] = null;
      board[y1][x1].xpos = x1;
      board[y1][x1].ypos = y2;
      chipsLocations.findNode(x2,y2,color).updateCoordinates(x1,y1);
    } else if (color == opponentColor && opponentChipsLocations.length() == 10) {
      board[y1][x1] = board[y2][x2];
      board[y2][x2] = null;
      board[y1][x1].xpos = x1;
      board[y1][x1].ypos = y2;
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
    board[y][x] = null;
    Chip.chipsRemoved(1);
    if (color == myColor) {
      chipsLocations.remove(chipsLocations.findNode(x,y,color));
    } else if (color == opponentColor) {
      opponentChipsLocations.remove(chipsLocations.findNode(x,y,color));
    }
  }

  /*
   * Description of method here
   * !!!PLEASE WRITE THIS FUNCTION ASAP AND SEND IT TO ME SO I CAN TEST OUT validMoves()!!!!
   */
  public boolean isValidMove(Move m, int color) {
    // new implementation here
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
  }

  /*
   * hasValidNetwork() returns a boolean indicating whether there is a existing
   * network on the board.
   */
  public boolean hasValidNetwork() {
    for (int i : countNetworks()) {
      if (i == 6) {
        return true;
      }
    }
    return false;
  }

  /*
   * performMove() executes the given move depending on the movekind of the
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
  public Move[] validMoves(int color) {
    Move[] validMoves = new Move[64];
    if (Chip.numOfChipsCreated() < 20) {
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          Move newMove = new Move(i, j);
          if (isValidMove(newMove, color)) {
            validMoves[i + j] = newMove;
          }
        }
      }
    } else {
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          Chip chip = board[i][j];
          if (chip != null && chip.isRealChip()) {
            for (int c = 0; c < chip.possibleStepMoves().length; c++) {
              validMoves[i + j + c] = chip.possibleStepMoves()[c];
            }
          }
        }
      }
    }
    return validMoves;
  }

  // INCOMPLETE
  public int evaluateBoard() {
    int score = 0;
    if (hasValidNetwork()) {
      score = 10;
    } else {
      return 1;
    }
    return score;
  }

}