package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DisjointSetTest {
	
	int[] sets = new int[15];
	
	@BeforeClass
	public void setup() {
		
	}
	
	@BeforeEach
	public void setupEach() {
		sets[0] = 14;
		sets[1] = -1;
		sets[2] = 11;
		sets[3] = 4;
		sets[4] = -1;
		sets[5] = -1;
		sets[6] = 5;
		sets[7] = 5;
		sets[8] = 0;
		sets[9] = 14;
		sets[10] = 3;
		sets[11] = 1;
		sets[12] = 2;
		sets[13] = 1;
		sets[14] = -1;
		
		DisjointSet.setHeight(sets, 5, 2);
		DisjointSet.setSize(sets, 5, 3);
		
		DisjointSet.setHeight(sets, 14, 3);
		DisjointSet.setSize(sets, 14, 4);
		
		DisjointSet.setHeight(sets, 4, 3);
		DisjointSet.setSize(sets, 4, 3);
		
		DisjointSet.setHeight(sets, 1, 4);
		DisjointSet.setSize(sets, 1, 5);
	}
	
	@AfterEach
	public void reset() {
		
	}
	
	
	@Test
	public void getSizeReturnsTreeSizeWithRootNodes() {
		
		assertEquals(3, DisjointSet.getSize(sets, 5));
		assertEquals(4, DisjointSet.getSize(sets, 14));
		assertEquals(3, DisjointSet.getSize(sets, 4));
		assertEquals(5, DisjointSet.getSize(sets, 1));
		
	}
	
	@Test
	public void getSizeReturnsMinusOneWithNonRootNodes() {
		
		assertEquals(-1, DisjointSet.getSize(sets, 7));
		assertEquals(-1, DisjointSet.getSize(sets, 6));
		assertEquals(-1, DisjointSet.getSize(sets, 9));
		assertEquals(-1, DisjointSet.getSize(sets, 0));
		assertEquals(-1, DisjointSet.getSize(sets, 3));
		assertEquals(-1, DisjointSet.getSize(sets, 13));
		assertEquals(-1, DisjointSet.getSize(sets, 11));
		assertEquals(-1, DisjointSet.getSize(sets, 8));
		assertEquals(-1, DisjointSet.getSize(sets, 10));
		assertEquals(-1, DisjointSet.getSize(sets, 2));
		assertEquals(-1, DisjointSet.getSize(sets, 12));
		
	}
	
	@Test
	public void getHeightReturnsTreeHeightWithRootNodes() {
		
		assertEquals(2, DisjointSet.getHeight(sets, 5));
		assertEquals(2, DisjointSet.getHeight(sets, 5));
		assertEquals(2, DisjointSet.getHeight(sets, 5));
		assertEquals(2, DisjointSet.getHeight(sets, 5));
		
	}
	
	@Test
	public void getHeightReturnsMinusOneWithNonRootNodes() {
		
		assertEquals(-1, DisjointSet.getHeight(sets, 7));
		assertEquals(-1, DisjointSet.getHeight(sets, 6));
		assertEquals(-1, DisjointSet.getHeight(sets, 9));
		assertEquals(-1, DisjointSet.getHeight(sets, 0));
		assertEquals(-1, DisjointSet.getHeight(sets, 3));
		assertEquals(-1, DisjointSet.getHeight(sets, 13));
		assertEquals(-1, DisjointSet.getHeight(sets, 11));
		assertEquals(-1, DisjointSet.getHeight(sets, 8));
		assertEquals(-1, DisjointSet.getHeight(sets, 10));
		assertEquals(-1, DisjointSet.getHeight(sets, 2));
		assertEquals(-1, DisjointSet.getHeight(sets, 12));
		
	}
	
	// Union by height - Different height trees
	
	@Test
	public void unionByHeightMergesRootsCorrectlyWithDifferentHeightTrees() {
		DisjointSet.unionByHeight(sets, 6, 14);
		assertTrue(sets[5] == 14);
	}
	
	@Test
	public void unionByHeightGetsCorrectNewHeightWithDifferentHeightTrees() {
		DisjointSet.unionByHeight(sets, 6, 14);
		assertTrue(DisjointSet.getHeight(sets, 14) == 3);
	}
	
	@Test
	public void unionByHeightGetsCorrectNewSizeWithDifferentHeightTrees() {
		DisjointSet.unionByHeight(sets, 5, 14);
		assertTrue(DisjointSet.getSize(sets, 14) == 7);
	}
	
	// Union by height - Same height trees
	
	@Test
	public void unionByHeightMergesRootsCorrectlyWithSameHeightTrees() {
		DisjointSet.unionByHeight(sets, 9, 3);
		assertEquals(4, DisjointSet.findRecursive(sets, 9));
		assertEquals(4, DisjointSet.findRecursive(sets, 3));
		
	}
	
	@Test
	public void unionByHeightGetsCorrectNewHeightWithSameHeightTrees() {
		DisjointSet.unionByHeight(sets, 9, 3);
		assertEquals(4, DisjointSet.getHeight(sets, 4));
	}
	
	@Test
	public void unionByHeightGetsCorrectNewSizeWithSameHeightTrees() {
		DisjointSet.unionByHeight(sets, 9, 3);
		assertEquals(7, DisjointSet.getSize(sets, 4));
	}
	
	// Union by height - Nodes in the same tree
	
	@Test
	public void unionByHeightWithNodesInTheSameTreeDoesNothing() {
		DisjointSet.unionByHeight(sets, 13, 2);
		assertEquals(5, DisjointSet.getSize(sets, DisjointSet.findRecursive(sets, 13)));
		assertEquals(5, DisjointSet.getSize(sets, DisjointSet.findRecursive(sets, 2)));
		assertEquals(1, DisjointSet.findRecursive(sets, 13));
		assertEquals(1, DisjointSet.findRecursive(sets, 2));
	}
	
	
	
	// Union by size - Different size trees
	
	@Test
	public void unionBySizeMergesRootsCorrectlyWithDifferentSizeTrees() {
		DisjointSet.unionBySize(sets, 4, 1);
		assertEquals(1, sets[4]);
	}
	
	@Test
	public void unionBySizeGetsCorrectNewHeightWithDifferentSizeTrees() {
		DisjointSet.unionBySize(sets, 4, 1);
		assertEquals(4, DisjointSet.getHeight(sets, 1));
	}
	
	@Test
	public void unionBySizeGetsCorrectNewSizeWithDifferentSizeTrees() {
		DisjointSet.unionBySize(sets, 4, 1);
		assertEquals(8, DisjointSet.getSize(sets, 1));
	}
	
	// Union by height - Same height trees
	
	@Test
	public void unionBySizeMergesRootsCorrectlyWithSameSizeTrees() {
		DisjointSet.unionBySize(sets, 5, 4);
		assertEquals(4, DisjointSet.findRecursive(sets, 5));
		assertEquals(4, DisjointSet.findRecursive(sets, 4));
	}
	
	@Test
	public void unionBySizeGetsCorrectNewHeightWithSameSizeTrees() {
		DisjointSet.unionBySize(sets, 5, 4);
		assertEquals(3, DisjointSet.getHeight(sets, 4));
	}
	
	@Test
	public void unionBySizeGetsCorrectNewSizeWithSameSizeTrees() {
		DisjointSet.unionBySize(sets, 5, 4);
		assertEquals(6, DisjointSet.getSize(sets, 4));
	}
	
	// Union by size - Nodes in the same tree
	
	@Test
	public void unionBySizeWithNodesInTheSameTreeDoesNothing() {
		DisjointSet.unionBySize(sets, 13, 2);
		assertEquals(5, DisjointSet.getSize(sets, DisjointSet.findRecursive(sets, 13)));
		assertEquals(5, DisjointSet.getSize(sets, DisjointSet.findRecursive(sets, 2)));
		assertEquals(1, DisjointSet.findRecursive(sets, 13));
		assertEquals(1, DisjointSet.findRecursive(sets, 2));
	}
	
}
