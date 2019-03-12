package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Links the main FXML window to the rest of the program.
 * @author Mantas Rajackas
 *
 */
public class MainMenuController {
	
	// Menu Bar
	@FXML private MenuItem quit;
	@FXML private MenuItem openImage;
	
	// Image display
	@FXML private ImageView imagePanel;
	
	// Image manipulation
	@FXML private Button showOriginalImage;
	@FXML private Slider blackWhiteThreshold;
	@FXML private Button toBlackWhite;
	
	// Disjoint set operations
	@FXML private Button locateBirds;
	@FXML private Label minSizeLabel;
	@FXML private Slider minSize;
	@FXML private Label birdNumberLabel;
	
	// Advanced analysis
	@FXML private Button findIsolatedBird;
	@FXML private TextArea infoPanel;
	
	@FXML
	/**
	 * Opens a file browser and loads in a chosen image into the program.
	 * Creates an instance of BirdAnalyser based on this image.
	 * @param event
	 */
	public void openImage(ActionEvent event) {
		try {
			// File browser for selecting image
			FileChooser fc = new FileChooser();
			fc.setTitle("Open Image");
			File imageFile = fc.showOpenDialog(new Stage());
			
			// Stores and displays the image
			ImageEditor.setLoadedImageFile(imageFile);
			ImageEditor.setLoadedImage(new Image(imageFile.toURI().toString()));
			ImageEditor.setGrayScaleImage(null);
			toBlackWhite();
			imagePanel.setImage(ImageEditor.getLoadedImage());
			
			// Creates an instance of BirdAnalyser for the loaded image
			Main.birdAnalyser = new BirdAnalyser(ImageEditor.getBlackWhiteImage());
		}
		catch (NullPointerException e) {
			System.out.println("Image loading cancelled.");
		}
	}
	
	/**
	 * Displays the original, unmodified image that's loaded in.
	 * @param event
	 */
	@FXML
	public void showOriginalImage(ActionEvent event) {
		if (ImageEditor.getLoadedImage() != null) {
			imagePanel.setImage(ImageEditor.getLoadedImage());
		}
	}
	
	/**
	 * Converts the currently-loaded image to black and white,
	 * based on the colour threshold slider.
	 * @param event
	 */
	@FXML
	public void toBlackWhite(ActionEvent event) {
		try {
			int threshold = (int) blackWhiteThreshold.getValue();
			Image image = ImageEditor.getLoadedImage();
			Image blackWhiteImage = ImageEditor.toBlackAndWhite(image, threshold);
			ImageEditor.setBlackWhiteImage(blackWhiteImage);
			imagePanel.setImage(blackWhiteImage);
		}
		catch (NullPointerException e) {
			infoPanel.setText("No image is loaded!");
		}
	}
	
	public void toBlackWhite() {
		try {
			int threshold = (int) blackWhiteThreshold.getValue();
			Image image = ImageEditor.getLoadedImage();
			Image blackWhiteImage = ImageEditor.toBlackAndWhite(image, threshold);
			ImageEditor.setBlackWhiteImage(blackWhiteImage);
			imagePanel.setImage(blackWhiteImage);
		}
		catch (NullPointerException e) {
			System.out.println("Error in toBlackWhite()");
		}
	}
	
	
	
	// BIRD SCANNING
	
	@FXML
	public void updatePixelSliderLabel(MouseEvent event) {
		minSizeLabel.setText("Minimum Bird Size (Pixels) - " + (int) minSize.getValue());
	}
	
	@FXML
	public void createSetsArray(ActionEvent event) {
		Main.birdAnalyser.instantiateDisjointSetArray(ImageEditor.getBlackWhiteImage());
		Main.birdAnalyser.combinePixels(ImageEditor.getBlackWhiteImage(), 0);
	}
	
	@FXML
	public void locateBirds(ActionEvent event) {
		try {
			Main.birdAnalyser.setImage(ImageEditor.getBlackWhiteImage());
			Main.birdAnalyser.instantiateDisjointSetArray((ImageEditor.getBlackWhiteImage()));
			Main.birdAnalyser.combinePixels(
					ImageEditor.getBlackWhiteImage(), (int) minSize.getValue());
			birdNumberLabel.setText("Birds found: " + 
					Main.birdAnalyser.countBirds((int) minSize.getValue()) );
			Main.birdAnalyser.createBirdBoundaries(ImageEditor.getBlackWhiteImage());
			imagePanel.setImage(ImageEditor.drawAllRects(ImageEditor.getLoadedImage(),
														 Main.birdAnalyser.getBirdBoundaries()));
		}
		catch (NullPointerException e) {
			infoPanel.setText("No image is loaded!");
		}
		
		
	}
	
	
	// ADVANCED ANALYSIS
	
	@FXML
	public void findIsolatedBird(ActionEvent event) {
		try {
			Main.birdAnalyser.findMostIsolatedBird();
			imagePanel.setImage(Main.birdAnalyser.drawIsoBirdRect(
					Main.birdAnalyser.findMostIsolatedBird(), new Color(1,0,0,1)));
			String currentInfo = "Highlighted bird average distance to others: " 
					+ Main.birdAnalyser.getBiggestDistToOtherBirds() + "px";
			String restInfo = "Average distance across the board: "
					+ Main.birdAnalyser.getAvgDistToOtherBirds() + "px";
			infoPanel.setText(currentInfo + "\n" + restInfo);
		}
		catch (NullPointerException e) {
			infoPanel.setText("Use 'count birds' before performing"
							 + " an advanced analysis.");
		}
	}
	
	
	// MISC
	
	/**
	 * Shuts down the program.
	 * @param event
	 */
	@FXML
	public void quitProgram(ActionEvent event) {
		System.exit(0);
	}
}