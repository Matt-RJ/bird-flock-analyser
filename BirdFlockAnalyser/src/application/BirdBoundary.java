package application;

/**
 * This class stores all valid bird disjoint set trees, along
 * with the indices of the trees' topmost, rightmost, bottom-most,
 * and leftmost nodes for the purpose of drawing borders around each
 * bird.
 * 
 * @author Mantas Rajackas
 *
 */
public class BirdBoundary {
	
	private int rootIndex;
	private int topIndex; // Index of topmost node
	private int rightIndex; // Index of rightmost node
	private int bottomIndex; // Index of bottom-most node
	private int leftIndex; // Index of leftmost node
	
	private int topY; // Highest node's Y value
	private int rightX; // Rightmost node's X value
	private int bottomY; // Bottom-most node's Y value
	private int leftX; // Leftmost node's X value
	
	public BirdBoundary(int rootIndex) {
		this.rootIndex = rootIndex;
	}
	
	public BirdBoundary(int rootIndex, int topIndex, int rightIndex,
						int bottomIndex, int leftIndex) {
		this.rootIndex = rootIndex;
		this.topIndex = topIndex;
		this.rightIndex = rightIndex;
		this.bottomIndex = bottomIndex;
		this.leftIndex = leftIndex;
	}
	
	public BirdBoundary(int rootIndex, int topIndex, int rightIndex,
						int bottomIndex, int leftIndex, int topY,
						int rightX, int bottomY, int leftX) {
		this.rootIndex = rootIndex;
		this.topIndex = topIndex;
		this.rightIndex = rightIndex;
		this.bottomIndex = bottomIndex;
		this.leftIndex = leftIndex;
		
		this.topY = topY;
		this.rightX = rightX;
		this.bottomY = bottomY;
		this.leftX = leftX;
	}

	public int getRootIndex() {
		return rootIndex;
	}

	public void setRootIndex(int rootIndex) {
		this.rootIndex = rootIndex;
	}

	public int getTopIndex() {
		return topIndex;
	}

	public void setTopIndex(int topIndex) {
		this.topIndex = topIndex;
	}

	public int getRightIndex() {
		return rightIndex;
	}

	public void setRightIndex(int rightIndex) {
		this.rightIndex = rightIndex;
	}

	public int getBottomIndex() {
		return bottomIndex;
	}

	public void setBottomIndex(int bottomIndex) {
		this.bottomIndex = bottomIndex;
	}

	public int getLeftIndex() {
		return leftIndex;
	}

	public void setLeftIndex(int leftIndex) {
		this.leftIndex = leftIndex;
	}

	public int getTopY() {
		return topY;
	}

	public void setTopY(int topY) {
		this.topY = topY;
	}

	public int getRightX() {
		return rightX;
	}

	public void setRightX(int rightX) {
		this.rightX = rightX;
	}

	public int getBottomY() {
		return bottomY;
	}

	public void setBottomY(int bottomY) {
		this.bottomY = bottomY;
	}

	public int getLeftX() {
		return leftX;
	}

	public void setLeftX(int leftX) {
		this.leftX = leftX;
	}
	
	/**
	 * Gets the midpoint of a bird's boundary box.
	 * @return A 2D array, where [0] = x, [1] = y.
	 */
	public int[] getMidPoint() {
		int[] coords = new int[2];
		coords[0] = (int) ( leftX + (0.5 * (rightX-leftX)));
		coords[1] = (int) (topY + (0.5 * (bottomY-topY)));
		return coords;
	}
	
}
		
	
	