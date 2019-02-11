package application;

/**
 * 
 * @author Mantas Rajackas
 *
 */
public class DisjointSet {
	
	/**
	 * Each index in the array represents a disjoint set node, where:
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
	private static int[] sets;
	
	public int[] getSets() {
		return sets;
	}
	public void setSets(int[] newSets) {
		DisjointSet.sets = newSets;
	}
	
	public DisjointSet() {
		
	}
	
	public DisjointSet(int maxSets) {
		sets = new int[maxSets];
	}
	
	// TODO: Add methods for manipulating sets
	
	/**
	 * Finds the ID of a node's root (Iteratively).
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID to look for.
	 * @return The ID (index) of a node's root in sets.
	 */
	public static int findIterative(int[] sets, int id) {

		return 0;
	}
	
	/**
	 * Finds the ID of a node's root (Recursively).
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID to look for.
	 * @return The ID (index) of a node's root in sets.
	 */
	public static int findRecursive(int[] sets, int id) {
		
		return 0;
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
		
		// TODO: Get correct bits and test
		int size = sets[id] & 0x7FFFF000;
		return size >> 12;
		
	}
	
	/**
	 * Gets the height of a parent node's tree.
	 * @param sets - The array of disjoint sets.
	 * @param id - The ID of the node.
	 * @return The height of the tree, determined by its
	 * longest branch. Returns -1 if the node is not a root.
	 */
	public static int getHeight(int[] sets, int id) {
		
		// Returns -1 if the node is not a root
		if (!isRoot(sets,id)) return -1;
		
		// TODO: Implement and test
		return sets[id] & 0x00000FFF;
		
	}
	
	// TODO: Add union methods
	
}
