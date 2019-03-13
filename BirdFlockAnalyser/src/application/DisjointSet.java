package application;

/**
 * This class allows for the creation and management of disjoint
 * set trees.
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
		return (isRoot(sets,id) ? (sets[id] & 0x7FFFF000) >> 12 : -1);
	}
	
	/**
	 * Sets the size of a disjoint set tree.
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the root node.
	 * @param height - The new size of the tree, between 0 and 524287.
	 * @throws IllegalArgumentException if the size is under 0 or above 524287.
	 */
	public static void setSize(int[] sets, int id, int size)
			throws IllegalArgumentException {
		
		if (size < 0 || size > 524287) throw new 
		IllegalArgumentException("Invalid size " + size + ". Height must be between 0"
				+ " and 524287.");
		
		if (!isRoot(sets,id)) throw new IllegalArgumentException(
				"The element with id: " + id + "is not a root.");
		
		// Clears the current size
		int temp = sets[id] &= 0x80000FFF;
		
		// Adds new height to root node
		sets[id] = temp |= (new Integer(size << 12));
		
	}
	
	/**
	 * Gets the height of a parent node's tree.
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the root node.
	 * @return The height of the tree, determined by its
	 * longest branch. Returns -1 if the node is not a root.
	 */
	public static int getHeight(int[] sets, int id) {
		return (isRoot(sets,id) ? sets[id] & 0x00000FFF : -1);
	}
	
	/**
	 * Sets the size of a disjoint set tree, as displayed
	 * in the tree's root node.
	 * 
	 * @param sets - The array of disjoint set nodes.
	 * @param id - The ID of the root node of the tree.
	 * @param size - The new size of the disjoint set tree, between 0 and 4095.
	 * @throws IllegalArgumentException if the size is negative or above 4095.
	 * @throws IllegalArgumentException if sets[id] contains a non-root node.
	 */
	public static void setHeight(int[] sets, int id, int height)
			throws IllegalArgumentException {
		
		if (height < 0 || height > 4095) throw new 
		IllegalArgumentException("Invalid height " + height
							   + ". Height must be between 0 and 4095.");
		
		if (!isRoot(sets,id)) throw new IllegalArgumentException(
				"The element with id " + id + " is not a root.");
		
		sets[id] = (sets[id] & 0xFFFFF000) | height;
		
	}
	
	/**
	 * Merges two disjoint set trees by their height. The
	 * root of the shorter tree becomes a child or the root of
	 * the taller tree. If the trees are of equal height, then
	 * q is treated as the taller tree (arbitrarily). If p and q
	 * are in the same tree, then nothing happens.
	 * 
	 * @param sets - The array of disjoint set nodes.
	 * @param p - A node in one tree.
	 * @param q - A node in another tree.
	 */
	public static void unionByHeight(int[] sets, int p, int q) {
		
		int rootP = findRecursive(sets, p);
		int rootQ = findRecursive(sets, q);
		
		// Does nothing if both nodes are in the same tree.
		if (rootP == rootQ) return;
		
		int pTreeHeight = getHeight(sets, rootP);
		int qTreeHeight = getHeight(sets, rootQ);
		
		// Gets both nodes and determines which one will be the root
		
		int deeperRoot = (pTreeHeight > qTreeHeight) ? rootP : rootQ;
		int deeperRootIndex = (deeperRoot == rootP) ? p : q;
		int shallowerRoot = (deeperRoot == rootP) ? rootQ : rootP;
		
		// The height and size of the new tree
		int newHeight = (pTreeHeight == qTreeHeight) ?
				pTreeHeight + 1 : getHeight(sets, deeperRoot);
		int newSize = getSize(sets, rootP) + getSize(sets, rootQ);
		
		// Joins the two nodes
		sets[shallowerRoot] = deeperRootIndex;
		
		// Updates the new tree's height and size
		setHeight(sets, deeperRoot, newHeight);
		setSize(sets, deeperRoot, newSize);
		
	}
	
	/**
	 * Merges two disjoint set trees by their sizes. The
	 * root of the tree with the smaller size becomes a child
	 * of the root of the bigger tree's root node.
	 * 
	 * If the trees are of equal size, then q is treated as the
	 * bigger tree (arbitrarily). If p and q are in the same tree,
	 * then nothing happens.
	 * @param sets
	 * @param p - A node in one tree.
	 * @param q - A node in another tree.
	 */
	public static void unionBySize(int[] sets, int p, int q) {
		
		int rootP = findRecursive(sets, p);
		int rootQ = findRecursive(sets, q);
		
		if (rootP == rootQ) return; // Does nothing if both nodes are in the same tree.
		
		int pTreeSize = getSize(sets, rootP);
		int qTreeSize = getSize(sets, rootQ);
		
		// Gets both nodes and determines which one will be the root
		int biggerRoot = (pTreeSize > qTreeSize) ? rootP : rootQ;
		int biggerRootIndex = (biggerRoot == rootP) ? p : q;
		int smallerRoot = (biggerRoot == rootP) ? rootQ : rootP;
		
		// The height and size of the new tree
		int pTreeHeight = getHeight(sets, rootP);
		int qTreeHeight = getHeight(sets, rootQ);
		int deeperRoot = (pTreeHeight > qTreeHeight) ? rootP : rootQ;
		int newHeight = (pTreeHeight == qTreeHeight) ?
				pTreeHeight + 1 : getHeight(sets, deeperRoot);
		
		int newSize = pTreeSize + qTreeSize;
		
		// Joins the two nodes
		sets[smallerRoot] = biggerRootIndex;
		
		// Updates the new tree's height and size
		setHeight(sets, biggerRoot, newHeight);
		setSize(sets, biggerRoot, newSize);
	}
	
}
