package application;

/**
 * 
 * @author Mantas Rajackas
 *
 */
public class DisjointSet {
	
	/**
	 * Each index in the array represents a disjoint set node, where:
	 * <br> - 0 represents a null node.
	 * <br> - Positive int values are non-root nodes of a disjoint set tree.
	 * A non-root node's int value is the index of its parent in the sets array.
	 * <br> - Negative int values are root nodes (Leftmost bit of the int is 1)
	 * 
	 * <br><br> - Root node integers contain information about their trees:
	 * <br>- Starting on the 2nd bit from the left, the next 19 bits contain
	 * the size of the tree.
	 * <br>- The last 12 bits contain the height of the tree.
	 * 110011
	 * <br>- Example: 100000000 00000011 00110000 00001010 is a root node with
	 * a size of 51 and a height of 10
	 * 
	 */
	private int[] sets;
	
	public int[] getSets() {
		return sets;
	}
	public void setSets(int[] newSets) {
		sets = newSets;
	}
	
	public DisjointSet() {
		
	}
	
	public DisjointSet(int maxSets) {
		sets = new int[maxSets];
		for (int i = 0; i < maxSets; i++) {
			sets[i] = 0;
		}
	}
	
	// TODO: Add methods for manipulating sets
	
	/**
	 * Finds the ID of a node's root (Iteratively).
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID to look for.
	 * @return The ID (index) of a node's root in sets.
	 */
	public static int findIterative(int[] sets, int id) {
		while (!isRoot(sets, id)) id = sets[id];
		return id;
	}
	
	/**
	 * Finds the ID of a node's root (Recursively).
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID to look for.
	 * @return The ID (index) of a node's root in sets.
	 */
	public static int findRecursive(int[] sets, int id) {
		return isRoot(sets,id) ? id : findRecursive(sets,sets[id]);
	}
	
	/**
	 * Determines if a node is a root node.
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the node.
	 * @return True if the node is a root, false otherwise.
	 */
	public static boolean isRoot(int[] sets, int id) {
		return sets[id] < 0;
	}
	
	/**
	 * Gets the size of a parent node's tree.
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the node.
	 * @return The number of nodes in a tree. Returns -1 if
	 * the node is not a root.
	 */
	public static int getSize(int[] sets, int id) {
		
		// Returns -1 if the node is not a root
		if (!isRoot(sets,id)) return -1;
		
		// TODO: Add JUnit test
		int size = sets[id] & 0x7FFFF000;
		return size >> 12;
		
	}
	
	/**
	 * Sets the size of a disjoint set tree.
	 * 
	 * @param sets - The array of disjoint set nodes.
	 * @param id - The ID of the root node of the tree.
	 * @param size - The new size of the disjoint set tree, between 0 and 4095.
	 * @throws IllegalArgumentException if the size is negative or above 4095.
	 * @throws IllegalArgumentException if sets[id] contains a non-root node.
	 */
	public static void setSize(int[] sets, int id, int size)
			throws IllegalArgumentException {
		
		// TODO: MAKE THIS THE HEIGHT
		
		if (size < 0 || size > 4095) throw new 
		IllegalArgumentException("Invalid size " + size + ". Size must be between 0 and 4095.");
		
		if (!isRoot(sets,id)) throw new IllegalArgumentException(
				"The element with id: " + id + "is not a root.");
		
		// TODO: Test this
		int newNode = sets[id] & 0xFFFFF000;
		sets[id] = newNode + size;
		
		
	}
	
	/**
	 * Gets the height of a parent node's tree.
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the root node.
	 * @return The height of the tree, determined by its
	 * longest branch. Returns -1 if the node is not a root.
	 */
	public static int getHeight(int[] sets, int id) {
		
		// Returns -1 if the node is not a root
		if (!isRoot(sets,id)) return -1;
		
		// TODO: Add JUnit test
		return sets[id] & 0x00000FFF;
		
	}
	
	/**
	 * Sets the height of a disjoint set tree's root node
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the root node.
	 * @param height - The new height of the tree, between 0 and 524287.
	 * @throws IllegalArgumentException
	 */
	public static void setHeight(int[] sets, int id, int height)
			throws IllegalArgumentException {
		
		if (height < 0 || height > 524287) throw new 
		IllegalArgumentException("Invalid height " + height + ". Height must be between 0"
				+ " and 524287.");
		
		if (!isRoot(sets,id)) throw new IllegalArgumentException(
				"The element with id: " + id + "is not a root.");
		
		// TODO: Fix this method
		int temp = sets[id];
		
		// Clears the current height
		temp &= 0xFFFFF000;
		
		// Adds new height to root node
		sets[id] = temp |= (new Integer(height << 12));
		
		System.out.println("done");
		
		
	}
	
	// TODO: Add union methods
	
	/**
	 * Merges two disjoint set trees by their height. The
	 * tree with the bigger height is used as the merged root.
	 * @param sets
	 * @param p
	 * @param q
	 */
	public static void unionByHeight(int[] sets, int p, int q) {
		
		int rootP = findRecursive(sets, p);
		int rootQ = findRecursive(sets, q);
		
		int pTreeHeight = getHeight(sets, rootP);
		int qTreeHeight = getHeight(sets, rootQ);
		
		int deeperRoot = (pTreeHeight > qTreeHeight) ? rootP : rootQ;
		int deeperRootIndex = (deeperRoot == rootP) ? p : q;
		int shallowerRoot = (deeperRoot == rootP) ? rootQ : rootP;
		
		// The height of the new tree
		int newHeight = (pTreeHeight == qTreeHeight) 
				? pTreeHeight + 1 : getHeight(sets, deeperRoot);
		
		sets[shallowerRoot] = deeperRootIndex;
		setSize(sets, deeperRootIndex, newHeight);
		
	}
	
	public static void unionBySize(int[] sets, int p, int q) {
		
		int rootP = findRecursive(sets, p);
		int rootQ = findRecursive(sets, q);
		
		int pTreeSize = getSize(sets, rootP);
		int qTreeSize = getSize(sets, rootQ);
		int totalSize = pTreeSize + qTreeSize; // The size of the new tree
		
		int smallerRoot = (pTreeSize < qTreeSize) ? rootP : rootQ;
		int largerRoot = (smallerRoot == rootP) ? rootQ : rootP;
		
		int temp = sets[smallerRoot];
	}
	
}
