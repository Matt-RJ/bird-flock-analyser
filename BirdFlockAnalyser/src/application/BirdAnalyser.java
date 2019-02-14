package application;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

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
	 * @param image - The javaFX image to base the array on.
	 */
	public void instantiateDisjointSetArray(Image image) {
		int pixelsInImage = (int) (image.getHeight() * image.getWidth());
		dset = new DisjointSet(pixelsInImage);
		
		PixelReader pr = image.getPixelReader();
		for (int i = 0; i < pixelsInImage; i++) {
			if (ImageEditor.pixelIsBlack(image, x, y)) {
				setNode(x,y,SINGLETON);
			}
		}
		
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
