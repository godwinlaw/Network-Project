package player;

public class Board {
  
	public static final int BLOCKED = -1;
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;

	private int[][] board;

	public Board() {
		board = new int[8][8];
		board[0][0]=board[0][7]=board[7][0]=board[7][7]=BLOCKED;
	}

	public boolean isValidMove(Move m, int color) {
		int x, y;
		if (m.moveKind==1) {
			x = m.x1;
			y = m.y1;
		} else {
			x = m.x2;
			y = m.y2;
		}
		if (color==BLACK) {
			return !(x==0) && !(x==7) && board[x][y]==EMPTY;
		} else {
			return !(y==0) && !(y==7) && board[x][y]==EMPTY;
		}
	}

	private void addChip(Move m, int color) {
		board[m.x1][m.y1] = color;
	}

	private void moveChip(Move m, int color) {
		board[m.x1][m.y1] = EMPTY;
		board[m.x2][m.y2] = color;
	}
    
	private void removeChip(Move m) {
		board[m.x1][m.y1] = EMPTY;
	}
	
	public boolean isFull() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
		        if (board[i][j] == EMPTY) {
		        	return false;
		        }	
			}
		}
		return true;
	}
	
	public boolean hasValidNetwork() {
		return false;
	}
	
	public void performMove(Move m, int color) {
		if (m.moveKind == 1) {
			addChip(m, color);
		} else if (m.moveKind == 2) {
			moveChip(m, color);
		} else {
			return;
		}
	}
	
	public void undoMove(Move m, int color) {
		if (m.moveKind == 1) {
			removeChip(m);
		} else if (m.moveKind == 2) {
			
		}
	}
	public boolean[] validMoves(int x, int y, int moveKind, int color) {
		boolean[] validMoves = new boolean[64];
		for (int i = 0; i < 64; i++) {
			validMoves[i] = false;
		}
	    if (moveKind == 1) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (isValidMove(new Move(i,j), color) && !(i == x) && !(j == y)) {
						validMoves[i + j] = true;
					}
				}
			}
	    } else if (moveKind == 2) {
	    	for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (isValidMove(new Move(i,j,x,y), color)  && !(i == x) && !(j == y)) {
						validMoves[i + j] = true;
					}
				}
			}
	    }
		return validMoves;
	} 
	
	public static void main(String[] args) {
		Board a = new Board();
		Move m = new Move(1, 1);
		a.addChip(m, BLACK);
		System.out.println(a.isValidMove(m, BLACK));
	}

}