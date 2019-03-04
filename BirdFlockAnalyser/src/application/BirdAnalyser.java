package application;

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
	
	public BirdAnalyser() {
		
	}
	public BirdAnalyser(Image image) {
		this.image = image;
	}
	
	/**
	 * Instantiates an array of disjoint set nodes based on
	 * a black and white image's pixels and dimensions.
	 * 
	 * @param image - The javaFX image to base the array on.
	 */
	public void instantiateDisjointSetArray(Image image) {
		int pixelsInImage = (int) (image.getHeight() * image.getWidth());
		dset = new DisjointSet(pixelsInImage);
		
		// TODO: Test with setsMade and remove
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
		
//		for (int i = 0; i < pixelsInImage; i++) {
//			System.out.println("Index: " + i + "   Value: " + dset.getSets()[i]);
//		}
	}
	
	public void instantiateDisjointSetArray2(Image image) {
		int pixelsInImage = (int) (image.getHeight() * image.getWidth());
		dset = new DisjointSet(pixelsInImage);
		
		for (int i = 0; i < pixelsInImage; i++) {
			int[] coords = getCoordinates(i);
			int rightNode = getRightNode(coords[0], coords[1]);
			int bottomRightNode = getBottomRightNode(coords[0], coords[1]);
			int bottomNode = getBottomNode(coords[0], coords[1]);
			
			if (ImageEditor.pixelIsBlack(image, coords[0], coords[1])) {
				if (dset.getSets()[i] == 0) {
					setNode(coords[0], coords[1], SINGLETON);
				}
				if (rightNode == 0) {
					int rightNodeCoords;
					
				}
			}
			//if (ImageEditor.pixelIsBlack(image, , y))
		}
	}
	
	// TODO: Finish these methods and add them to conbinePixels
	
	public void mergeRight(Image image, int index) {
		int[] currentCoords = getCoordinates(index);
		
		if (currentCoords[0] + 1 < image.getWidth() &&
			currentCoords[0] + 1 < dset.getSets().length &&
			dset.getSets()[index+1] != 0) {
			DisjointSet.unionBySize(dset.getSets(), index, index+1);
		}
	}
	
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
	 * @param image - The currently-loaded image in the program
	 * //TODO: Move countRoots and minSize to another method
	 */
	public void combinePixels(Image image, int minSize) {		
		// TODO: Re-factor and split up this method
		
		System.out.println("Length of array: " + dset.getSets().length);
		
		for (int i = 0; i < dset.getSets().length; i++) {
			if (dset.getSets()[i] != 0) {
				mergeRight(image, i);				
				mergeBottomRight(image,i); 
				mergeBottom(image, i);
			}
		}
		//printSetsToConsole();
		System.out.println(countRoots(minSize) + " birds have been found.");
	}
	
	public void printSetsToConsole() {
		int imageHeight = (int) image.getHeight();
		int imageWidth = (int) image.getWidth();
		int[] sets = dset.getSets();
		
		for (int i = 0; i < sets.length; i++) {
			System.out.print("   " + sets[i] + "   ");
			if (getCoordinates(i)[0] == imageWidth-1) {
				System.out.println("\n");
			}
		}
		
	}
	
	public int countRoots(int minSize) {
		int roots = 0;
		for (int i = 0; i < dset.getSets().length; i++) {
			if ((dset.getSets()[i] < 0) && 
				DisjointSet.getSize(dset.getSets(), i) >= minSize) {
				roots++;
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
	
	public int getTopNode(int x, int y) {
		return getNode(x, y-1);
	}
	
	public int getTopRightNode(int x, int y) {
		return getNode(x+1,y-1);
	}
	
	public int getRightNode(int x, int y) {
		return getNode(x+1,y);
	}
	
	public int getBottomRightNode(int x, int y) {
		return getNode(x+1,y+1);
	}
	
	public int getBottomNode(int x, int y) {
		return getNode(x,y+1);
	}
	
	public int getBottmLeftNode(int x, int y) {
		return getNode(x-1,y+1);
	}
	
	public int getLeftNode(int x, int y) {
		return getNode(x-1,y);
	}
	
	public int getTopLeftNode(int x, int y) {
		return getNode(x-1,y-1);
	}
	
}
