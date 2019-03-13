package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * This class contains the methods which allow the DisjointSet class to
 * interact with the GUI and the rest of the system for bird counting and
 * analysis.
 * 
 * @author Mantas Rajackas
 *
 */
public class BirdAnalyser {
	
	private DisjointSet dset;
	private Image image;
	private final int SINGLETON = 0x80001001; // A single disjoint set node
	
	private int avgDistToOtherBirds;
	private int biggestDistToOtherBirds;
	
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
	
	public int getAvgDistToOtherBirds() {
		return avgDistToOtherBirds;
	}
	public void setAvgDistToOtherBirds(int avgDistToOtherBirds) {
		this.avgDistToOtherBirds = avgDistToOtherBirds;
	}
	public int getBiggestDistToOtherBirds() {
		return biggestDistToOtherBirds;
	}
	public void setBiggestDistToOtherBirds(int biggestDistToOtherBirds) {
		this.biggestDistToOtherBirds = biggestDistToOtherBirds;
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
		
		for (int i = 0; i < pixelsInImage; i++) {
			int[] coords = getCoordinates(i);
			if (ImageEditor.pixelIsBlack(image, coords[0], coords[1])) {
				// Black pixels - Single nodes for each
				setNode(coords[0],coords[1],SINGLETON);
			}
			else {
				// White pixels
				setNode(coords[0],coords[1],0);
			}
		}
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
	
	/**
	 * Combines a disjoint set array of singletons into trees,
	 * one tree per bird.
	 * 
	 * @param image - The currently-loaded image in the program
	 */
	public void combinePixels(Image image, int minSize) {		
		for (int i = 0; i < dset.getSets().length; i++) {
			if (dset.getSets()[i] != 0) {
				mergeRight(image, i);				
				mergeBottomRight(image,i); 
				mergeBottom(image, i);
			}
		}
		int birdsFound = countBirds(minSize);
		System.out.println(birdsFound + " birds have been found.");
	}
	
	public void createBirdBoundaries(Image image) {
		int[] sets = dset.getSets();
		
		for (int current = 0; current < dset.getSets().length; current++) {
			
			if (sets[current] != 0) { // Ignores white pixels
				
				int currentSet = DisjointSet.findRecursive(sets, current);
				int[] currentCoords = getCoordinates(current);
				
				for (BirdBoundary a : birdBoundaries) { // Finds right bb object
					if (currentSet == a.getRootIndex()) {
						
						// Check if topmost
						int topMost = a.getTopY();
						if (currentCoords[1] < topMost) {
							a.setTopIndex(current); 
							a.setTopY(getCoordinates(current)[1]);
						}
						// Check if rightmost
						int rightMost = a.getRightX();
						if (currentCoords[0] > rightMost) {
							a.setRightIndex(current);
							a.setRightX(getCoordinates(current)[0]);
						}
						// Check if bottom-most
						int bottomMost = a.getBottomY();
						if (currentCoords[1] > bottomMost) {
							a.setBottomIndex(current);
							a.setBottomY(getCoordinates(current)[1]);
						}
						// Check if leftmost
						int leftMost = a.getLeftX();
						if (currentCoords[0] < leftMost) {
							a.setLeftIndex(current);
							a.setLeftX(getCoordinates(current)[0]);
						}
					}
				} 
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
		birdBoundaries = new ArrayList<>();
		int roots = 0;
		int imageHeight = (int) image.getHeight();
		int imageWidth = (int) image.getWidth();
				
		for (int i = 0; i < dset.getSets().length; i++) {
			if ((dset.getSets()[i] < 0) && 
				DisjointSet.getSize(dset.getSets(), i) >= minSize) {
				roots++;
				
				// Updates birdBoundaries with the found root
				BirdBoundary bb = new BirdBoundary(i,0,0,0,0,imageHeight,0,0,imageWidth);
				birdBoundaries.add(bb);
			}
		}
		return roots;
	}
	
	
	
	// ADVANCED ANALYSIS
	
	/**
	 * Locates the most isolated bird in the image,
	 * i.e. the bird with the biggest average distance
	 * to all other birds.
	 * @return The index of the most isolated bird in the sets array
	 */
	public BirdBoundary findMostIsolatedBird() {
		BirdBoundary isoBird = null; // Isolated bird ID in the sets array
		int highestAvgDist = 0;
		int averageAvgDist = 0;
		
		for (BirdBoundary current: birdBoundaries) {
			int currentAvgDist = 0;
			int otherBirds = 0;
			for (BirdBoundary other: birdBoundaries) {
				currentAvgDist+= getDist(current, other);
				otherBirds++;
			}
			currentAvgDist /= otherBirds;
			averageAvgDist += currentAvgDist;
			if (currentAvgDist > highestAvgDist) {
				isoBird = current;
				highestAvgDist = currentAvgDist;
			}
		}
		biggestDistToOtherBirds = highestAvgDist;
		avgDistToOtherBirds = averageAvgDist / birdBoundaries.size();
		return isoBird;
	}
		
	/**
	 * Draws a rectangle around the most isolated bird.
	 * @param iso - The BirdBoundary object corresponding
	 * to the bird.
	 * @param c - The colour of the rectangle to draw.
	 */
	public Image drawIsoBirdRect(BirdBoundary iso, Color c) {
		int topLeftX = getCoordinates(iso.getLeftIndex())[0];
		int topLeftY = getCoordinates(iso.getTopIndex())[1];
		int width = iso.getRightX() - iso.getLeftX();
		int height = iso.getBottomY() - iso.getTopY();
		
		return ImageEditor.rect(ImageEditor.getLoadedImage(), topLeftX,
				topLeftY, width, height, c);
	}
	
	public double getDist(BirdBoundary a, BirdBoundary b) {
		int[] aMid = a.getMidPoint();
		int[] bMid = b.getMidPoint();
		
		return Math.sqrt( Math.pow((bMid[0] - aMid[0]),2) + Math.pow((bMid[1] - aMid[1]),2));
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
