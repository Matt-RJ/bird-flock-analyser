package application;

/**
 * 
 * @author Mantas Rajackas
 *
 */
public class BirdBoundary {
	
	private int rootIndex;
	private int top;
	private int right;
	private int bottom;
	private int left;
	
	private int topCoord;
	private int rightCoord;
	private int bottomCoord;
	private int leftCoord;
	
	public BirdBoundary(int rootIndex) {
		this.rootIndex = rootIndex;
	}
	
	public BirdBoundary(int rootIndex, int top, int right,
						int bottom, int left) {
		this.rootIndex = rootIndex;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}
	
	public BirdBoundary(int rootIndex, int top, int right,
						int bottom, int left, int topCoord,
						int rightCoord, int bottomCoord,
						int leftCoord) {
		this.rootIndex = rootIndex;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		this.topCoord = topCoord;
		this.rightCoord = rightCoord;
		this.bottomCoord = bottomCoord;
		this.leftCoord = leftCoord;
	}
	
	public int getRootIndex() {
		return rootIndex;
	}
	public void setRootIndex(int rootIndex) {
		this.rootIndex = rootIndex;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getBottom() {
		return bottom;
	}
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}

	public int getTopCoord() {
		return topCoord;
	}

	public void setTopCoord(int topCoord) {
		this.topCoord = topCoord;
	}

	public int getRightCoord() {
		return rightCoord;
	}

	public void setRightCoord(int rightCoord) {
		this.rightCoord = rightCoord;
	}

	public int getBottomCoord() {
		return bottomCoord;
	}

	public void setBottomCoord(int bottomCoord) {
		this.bottomCoord = bottomCoord;
	}

	public int getLeftCoord() {
		return leftCoord;
	}

	public void setLeftCoord(int leftCoord) {
		this.leftCoord = leftCoord;
	}
}
