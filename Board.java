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
	
	void addChip(Move m, int color) {
		board[m.x1][m.y1] = color;
	}
	
	void moveChip(Move m, int color) {
		board[m.x1][m.y1] = EMPTY;
		board[m.x2][m.y2] = color;
	}
	
	public static void main(String[] args) {
		Board a = new Board();
		Move m = new Move(1, 1);
		a.addChip(m, BLACK);
		System.out.println(a.isValidMove(m, BLACK));
	}
	
}
