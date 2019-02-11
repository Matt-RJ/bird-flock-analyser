package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * This class handles all the image manipulation in this program,
 * such as converting images to gray scale, etc.
 * 
 * @author Mantas Rajackas
 *
 */
public abstract class ImageEditor {
	
	private static File loadedImageFile = null;  // Currently-loaded image
	private static Image loadedImage = null;	 // An Image version of loadedImageFile
	private static Image grayScaleImage = null;  // Gray scale version of loadedImage
	private static Image blackWhiteImage = null; // A black/white version of the original image

	// Getters / Setters
	public static File getLoadedImageFile() {
		return loadedImageFile;
	}
	public static void setLoadedImageFile(File loadedImageFile) {
		ImageEditor.loadedImageFile = loadedImageFile;
	}
	public static Image getLoadedImage() {
		return loadedImage;
	}
	public static void setLoadedImage(Image loadedImage) {
		ImageEditor.loadedImage = loadedImage;
	}
	public static Image getGrayScaleImage() {
		return grayScaleImage;
	}
	public static void setGrayScaleImage(Image grayScaleImage) {
		ImageEditor.grayScaleImage = grayScaleImage;
	}
	public static Image getBlackWhiteImage() {
		return blackWhiteImage;
	}
	public static void setBlackWhiteImage(Image blackWhiteImage) {
		ImageEditor.blackWhiteImage = blackWhiteImage;
	}
	
	
	/**
	 * Converts an Image to grayscale based on 
	 * the average RGB values of every pixel
	 * 
	 * @param image - The Image to convert
	 * @return a Grayscale version of image
	 */
	public static Image toGrayScale(Image image) {
		WritableImage processedImage = new WritableImage(
				(int) image.getWidth(), (int) image.getHeight());
		
		PixelWriter pw = processedImage.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				
				Color pixelColor = pr.getColor(x, y);
				double pixelRed = pixelColor.getRed();
				double pixelGreen = pixelColor.getGreen();
				double pixelBlue = pixelColor.getBlue();
				double pixelGray = (pixelRed + pixelGreen + pixelBlue) / 3;
				Color grayColor = new Color(pixelGray,
											pixelGray,
											pixelGray,pixelColor.getOpacity());
				
				pw.setColor(x, y, grayColor);
				
			}
		}
		
		return processedImage;
	}
	
	/**
	 * Converts an Image to a black and white version
	 * 
	 * @param image - The image to convert.
	 * @param threshold - The threshold of color - Average RGB values of each pixel.
	 * are calculated. Those below the threshold are turned to black, others are white.
	 * @return A black/white version of image.
	 */
	public static Image toBlackAndWhite(Image image, int threshold) {
		WritableImage processedImage = new WritableImage(
				(int) image.getWidth(), (int) image.getHeight());
		
		if (threshold < 0 || threshold > 255) threshold = 127;
		
		PixelWriter pw = processedImage.getPixelWriter();
		PixelReader pr = image.getPixelReader();
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				
				Color pixelColor = pr.getColor(x, y);
				double pixelRed = pixelColor.getRed();
				double pixelGreen = pixelColor.getGreen();
				double pixelBlue = pixelColor.getBlue();
				double pixelAverage = (pixelRed + pixelGreen + pixelBlue) / 3;
				
				// Determines whether the pixel will be black or white
				int newRGB = (pixelAverage * 255 < threshold) ? 0 : 1;
				
				pw.setColor(x, y, new Color(newRGB,newRGB,newRGB,pixelColor.getOpacity()));
				
			}
		}
		
		return processedImage;
	}
	
	/**
	 * Determines if an image has been loaded into the system.
	 * @return True if an image exists, false otherwise.
	 */
	public static boolean imageLoaded() {
		return (loadedImage == null) ? true : false;
	}
}