package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
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
		DisjointSet.setSize(sets, 14, 3);
		
		DisjointSet.setHeight(sets, 1, 4);
		DisjointSet.setSize(sets, 1, 5);
	}
	
	@Test
	public void getSizeTest() {
		int size = DisjointSet.getSize(sets, 5);
		assertEquals(5, size);
	}
	
	@Test
	public void setSizeTest() {
		
	}
	
	@Test
	public void getHeightTest() {
		// TODO: Fix this
		int height = DisjointSet.getHeight(sets, 5);
		assertEquals(2, height);
	}
	
	@Test
	public void setHeightTest() {
		
	}
	
	@Test
	public void mergeByHeightMergesRootsCorrectly() {
		DisjointSet.unionByHeight(sets, 6, 14);
		assertTrue(sets[5] == 14);
	}
	
	@Test
	public void mergeByHeightGetsCorrectNewHeight() {
		DisjointSet.unionByHeight(sets, 6, 14);
		assertTrue(DisjointSet.getHeight(sets, 14) == 3);
	}
	
	@Test
	public void mergeByHeightGetsCorrectNewSize() {
		DisjointSet.unionByHeight(sets, 6, 14);
		assertTrue(DisjointSet.getSize(sets, 14) == 7);
	}
	
}
