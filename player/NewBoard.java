package player;

/*
 * The Board Class This is used as an internal representation of the Network
 * game board for the MachinePlayer.
 */

public class Board {

  /*
   * board is an array that holds Chip objects. numOfOwnChips specifies how many
   * chips of the MachinePlayer's color is on the board.
   */
  private Chip[][] board;
  private int numOfOwnChips;

  /*
   * The default constructor Creates a 8 x 8 board holding Chip objects. The
   * corners of the board are initialized to have "fake" Chip objects (See
   * documentation in Chip class). No chips may be placed in these locations.
   */
  public Board() {
    board = new Chip[8][8];
    board[0][0] = board[0][7] = board[7][0] = board[7][7] = (new Chip(false));
    numOfOwnChips = 0;
  }

  /*
   * numOfOwnChips() returns the numberOfOwnChips variable, which specifies how
   * many chips of the MachinePlayer's color is on the board.
   */public int numOfOwnChips() {
    return numOfOwnChips;
  }

  /*
   * addChip() inserts a Chip object at the specified coordinates on the board.
   * @param x is the x-coordinate at which chip is to be added.
   * @param y is the y-coordinate at which chip is to be added.
   * @param color is the color of the chip that is to be added.
   */
  private void addChip(int x, int y, int color) {
    board[y][x] = new Chip(x, y, color);
    numOfOwnChips += 1;
  }

  /*
   * moveChip() moves a Chip from one cell to another
   * @param x1 and @param y1 are the new coordinates of the Chip.
   * @param x2 and @param y2 are the old coordinates of the Chip.
   * @param color is the color of the chip.
   */
  private void moveChip(int x1, int y1, int x2, int y2, int color) {
    board[y1][x1] = board[y2][x2];
    board[y2][x2] = null;
    board[y1][x1].xpos = x1;
    board[y1][x1].ypos = y2;
  }

  /*
   * removeChip() removes the existing Chip at the specified coordinates.
   * @param x and @param y are the coordinates where the to-be-removed chip
   * exists.
   */
  private void removeChip(int x, int y) {
    board[y][x] = null;
    Chip.chipsRemoved(1);
    numOfOwnChips -= 1;
  }

  /* Description of method here */
  public boolean isValidMove(Move m, int color) {
    // new implementation here
  }

  /* Description of method here */
  public boolean hasValidNetwork() {
    // new implementation here
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
      removeChip(m.x1, m.y2);
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