package player;

/**
 * Chip class
 * Represents the pieces that the player interact with in the game Network.
 * No more than 20 pieces of chips may be added to the board, according to the
 * rules of the game. This is enforced with the class variable numOfChips. 
*/

public class Chip {

	/**
	 * color - color of the chip xpos - the x-coordinate of the chip ypos - the
	 * y-coordinate of the chip
	 */
	public int color;
	public int xpos;
	public int ypos;

	/**
	 * The variable fakeChip specifies whether a chip is a real or fake chip.
	 * 'Fake' chips are placed on the blocked entries of the game board. All
	 * other chips are initialized to be false (fakeChip = false). Constant
	 * FAKECHIPS specifies the number of fake chips that can be created.
	 */
	public boolean fakeChip = false;
	private static int numOfFakeChips = 0;

	/**
	 * Class variable numOfChips keeps track of the number of chips that have
	 * been initialized. May be modified with static method chipsRemoved() when
	 * chips are removed from board.
	 */
	private static int numOfChips = 0;

	/**
	 * Default constructor. Only creates a chip if the number of chips
	 * initialized does not exceed 20, which is the maximum number of chips
	 * permitted to be on the board at one time.
	 */
	public Chip() {
		if (numOfChips < 20) {
			xpos = -1;
			ypos = -1;
			numOfChips++;
		} else {
			return;
		}
	}

	/**
	 * Chip constructor with specified color and coordinates. Only creates a
	 * chip if the number of chips initialized does not exceed 20, which is the
	 * maximum number of chips permitted to be on the board at one time.
	 * @param color - color of the chip
	 * @param xpos - x-coordinate of the chip
	 * @param ypos - y-coordinate of the chip
	 */
	public Chip(int color, int xpos, int ypos) {
		if (numOfChips < 20) {
			this.color = color;
			this.xpos = xpos;
			this.ypos = ypos;
			numOfChips++;
		} else {
			return;
		}
	}

	/**
	 * Chip constructor with chipType specified.
	 * Used to construct fakeChips if number of extant fake chips does not exceed 1.
	 * Otherwise, if Chip(true) is created, a chip is created with negative coordinates.
	 * @param chipType - type of the chip, whether real or fake
	 */
	public Chip(boolean chipType) {
		this(-1, -1, -1);
		if (!chipType && numOfFakeChips < 1) {
			fakeChip = true;
			numOfFakeChips++;
		} else if (!chipType) {
			return;
		}
	}

	/** 
	 * coordinates() returns the coordinates of the chip in an int-array. 
	*/
	public int[] coordinates() {
		int[] c = { xpos, ypos };
		return c;
	}

    /**
     * numOfChipsCreated() returns the total number of chips that have been created
    */
	public static int numOfChipsCreated() {
		return numOfChips;
	}

    /**
     * isRealChip() returns a boolean value indicating whether the chip in question is a fakeChip.
    */
	public boolean isRealChip() {
		return !fakeChip;
	}

    /**
     * chipsRemoved() modifies the numOfChips existing by decrementing the class variable by parameter num.
     * @param num - number of chips removed
    */
	public static void chipsRemoved(int num) {
		if (num >= 0) {
		    numOfChips -= num;
		} else {
			System.out.println("ERROR: Cannot remove a negative number of chips.");
			return;
		}
	}
}
