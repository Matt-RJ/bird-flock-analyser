package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * This class contains the methods which allow the DisjointSet class to
 * interact with the GUI and the rest of the system.
 * @author Mantas Rajackas
 *
 */
public class BirdAnalyser {
	
	private DisjointSet dset;
	private Image image;
	private final int SINGLETON = 0x80001001;
	
	private ArrayList<BirdBoundary> birdBoundaries = new ArrayList<>();
	
	public DisjointSet getDset() {
		return this.dset;
	}
	public void setDset(DisjointSet dset) {
		this.dset = dset;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	public Image getImage() {
		return this.image;
	}
	
	public ArrayList<BirdBoundary> getBirdBoundaries() {
		return this.birdBoundaries;
	}
	
	public void setBirdBoundaries(ArrayList<BirdBoundary> birdBoundaries) {
		this.birdBoundaries = birdBoundaries;
	}
	
	public BirdAnalyser() {
		
	}
	public BirdAnalyser(Image image) {
		this.image = image;
	}
	
	/**
	 * Instantiates an array of disjoint set nodes based on
	 * a black and white image's pixels and dimensions. All of
	 * the black pixels are turned into singletons.
	 * 
	 * @param image - The javaFX image to base the array on.
	 */
	public void instantiateDisjointSetArray(Image image) {
		int pixelsInImage = (int) (image.getHeight() * image.getWidth());
		dset = new DisjointSet(pixelsInImage);
		
		int setsMade = 0;
		for (int i = 0; i < pixelsInImage; i++) {
			int[] coords = getCoordinates(i);
			if (ImageEditor.pixelIsBlack(image, coords[0], coords[1])) {
				// Black pixels - Single nodes for each
				setNode(coords[0],coords[1],SINGLETON);
				setsMade++;
			}
			else {
				// White pixels
				setNode(coords[0],coords[1],0);
			}
		}
		
		System.out.println(setsMade + " sets have been created, 1 for each black pixel.");
	}
		
	/**
	 * Performs an union by size of two disjoint sets.
	 * @param image - The image the set array is based on
	 * @param index - The index in the dset array of the node to
	 * merge with the node to its immediate right.
	 */
	public void mergeRight(Image image, int index) {
		int[] currentCoords = getCoordinates(index);
		
		if (currentCoords[0] + 1 < image.getWidth() &&
			currentCoords[0] + 1 < dset.getSets().length &&
			dset.getSets()[index+1] != 0) {
			DisjointSet.unionBySize(dset.getSets(), index, index+1);
		}
	}
	
	/**
	 * Performs an union by size of two disjoint sets.
	 * @param image - The image the set array is based on
	 * @param index - The index in the dset array of the node to
	 * merge with the node to the bottom-right.
	 */
	public void mergeBottomRight(Image image, int index) {
		int[] currentCoords = getCoordinates(index);
		int imageHeight = (int) image.getHeight();
		int imageWidth = (int) image.getWidth();
		
		if (currentCoords[0]+1 < imageWidth &&
			currentCoords[1]+1 < imageHeight &&
			currentCoords[0]+2 < dset.getSets().length) {
			
			if (dset.getSets()[index+imageWidth+1] != 0) {
				DisjointSet.unionBySize(dset.getSets(), index, index+imageWidth+1);
			}
		}
	}
	
	/**
	 * Performs an union by size of two disjoint sets.
	 * @param image - The image the set array is based on
	 * @param index - The index in the dset array of the node to
	 * merge with the node below it.
	 */
	public void mergeBottom(Image image, int index) {
		int[] currentCoords = getCoordinates(index);
		int imageHeight = (int) image.getHeight();
		int imageWidth = (int) image.getWidth();
		if (currentCoords[1]+1 < imageHeight &&
			currentCoords[1]+2 < dset.getSets().length) {
			if (dset.getSets()[index+imageWidth] != 0) {
				DisjointSet.unionByHeight(dset.getSets(), index, index+imageWidth);
			}
		}
	}
	
	public void mergeBottomLeft(Image image, int index) {
		// TODO
	}
	
	/**
	 * Combines a disjoint set array of singletons into trees,
	 * one tree per bird.
	 * 
	 * @param image - The currently-loaded image in the program
	 */
	public void combinePixels(Image image, int minSize) {		
		// TODO: Remove print when done
		System.out.println("Length of array: " + dset.getSets().length);
		
		for (int i = 0; i < dset.getSets().length; i++) {
			if (dset.getSets()[i] != 0) {
				mergeRight(image, i);				
				mergeBottomRight(image,i); 
				mergeBottom(image, i);
			}
		}
		
		int birdsFound = countBirds(minSize);
		System.out.println(birdsFound + " birds have been found.");
		
		
		// TODO: Test and edit
		createBirdBoundaries(image);
		// Creates boxes around each bird
		// birdCorners = new int[birdsFound][5];
	}
	
	public void createBirdBoundaries(Image image) {
		int[] sets = dset.getSets();
		// TODO: Finish this method
		
		for (int current = 0; current < dset.getSets().length; current++) { // i = current pixel
			
			if (sets[current] != 0) { // Ignores white pixels
				
				int currentSet = DisjointSet.findRecursive(sets, current);
				int[] currentCoords = getCoordinates(current);
				
				// TODO: No coordinates are currently updating
				
				for (BirdBoundary a : birdBoundaries) { // Finds right bb object
					if (currentSet == a.getRootIndex()) {
						
						// Check if topmost
						int highestY = getCoordinates(a.getTop())[1];
						if (currentCoords[1] <= highestY) { // TODO: Always false
							a.setTop(current); 
							a.setTopCoord(getCoordinates(current)[1]);
						}
						// Check if rightmost
						int highestX = getCoordinates(a.getRight())[0];
						if (currentCoords[0] >= highestX) {
							a.setRight(current);
							a.setRightCoord(getCoordinates(current)[0]);
						}
						// Check if bottom-most
						int lowestY = getCoordinates(a.getBottom())[1];
						if (currentCoords[1] >= lowestY) {
							a.setBottom(current);
							a.setBottomCoord(getCoordinates(current)[0]);
						}
						// Check if leftmost
						int lowestX = getCoordinates(a.getLeft())[0];
						if (currentCoords[0] <= lowestX) { // TODO: Always false
							a.setLeft(current);
							a.setLeftCoord(getCoordinates(current)[1]);
						}
					}
				} 
			}
		}
		
		for (BirdBoundary a : birdBoundaries) {
			System.out.println(a.getRootIndex() + " has a topmost node at y: " + getCoordinates(a.getTop())[1]);
			System.out.println(a.getRootIndex() + " has a rightmost node at x: " + getCoordinates(a.getRight())[0]);
			System.out.println(a.getRootIndex() + " has a bottom-most node at y: " + getCoordinates(a.getBottom())[1]);
			System.out.println(a.getRootIndex() + " has a leftmost node at x: " + getCoordinates(a.getLeft())[0]);
			System.out.println();
		}
		
	}
	
	/**
	 * Prints out a version of the disjoint set array to the console.
	 */
	public void printSetsToConsole() {
		int imageWidth = (int) image.getWidth();
		int[] sets = dset.getSets();
		
		for (int i = 0; i < sets.length; i++) {
			if (DisjointSet.isRoot(sets, i)) System.out.print("Root ");
			else System.out.print(sets[i] + " ");
			
			if (getCoordinates(i)[0] == imageWidth-1) {
				System.out.println();
			}
		}
		
	}
	
	/**
	 * Counts the total number of disjoint sets in the dset array,
	 * ignoring all sets that are smaller than minSize. All of the found
	 * sets are added to birdBoundaries.
	 * 
	 * @param minSize - The smallest size of a tree for its root to count
	 * @return The number of roots/trees in the dset array.
	 */
	public int countBirds(int minSize) {
		int roots = 0;
		int imageHeight = (int) image.getHeight();
		int imageWidth = (int) image.getWidth();
				
		for (int i = 0; i < dset.getSets().length; i++) {
			if ((dset.getSets()[i] < 0) && 
				DisjointSet.getSize(dset.getSets(), i) >= minSize) {
				roots++;
				// Updates birdBoundaries with the found root
				
				BirdBoundary bb = new BirdBoundary(i, imageHeight,0,0,imageWidth);
				birdBoundaries.add(bb);
			}
		}
		return roots;
		
	}
	
	/**
	 * Finds a particular disjoint set node's coordinates in the image.
	 * 
	 * @return A 2D array, where array[0] = node's X, array[1] = node's Y
	 * in the image.
	 */
	public int[] getCoordinates(int index) throws NullPointerException {
		int[] coords = new int[2];
		
		coords[0] = (int) (index % image.getWidth());
		coords[1] = (int) (index / image.getWidth());
		
		return coords;
	}
	
	/**
	 * Gets the disjoint set node from a pixel in the loaded image
	 * @param x - The column of the pixel
	 * @param y - The row of the pixel
	 * @return
	 */
	public int getNode(int x, int y) {
		return dset.getSets()[((int) image.getWidth() * y) + x];
	}
	
	/**
	 * Sets a disjoint set node based on its pixel location in the image
	 * @param x - The column of the pixel
	 * @param y - The row of the pixel
	 */
	public void setNode(int x, int y, int newNode) {
		dset.getSets()[((int) image.getWidth() * y) + x] = newNode;
	}
	
}
