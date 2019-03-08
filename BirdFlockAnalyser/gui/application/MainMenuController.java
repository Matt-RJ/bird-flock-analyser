package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	
	@FXML
	/**
	 * Opens a file browser and loads in a chosen image into the program.
	 * Creates an instance of BirdAnalyser based on this image.
	 * @param event
	 */
	public void openImage(ActionEvent event) {
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
		int threshold = (int) blackWhiteThreshold.getValue();
		Image image = ImageEditor.getLoadedImage();
		Image blackWhiteImage = ImageEditor.toBlackAndWhite(image, threshold);
		ImageEditor.setBlackWhiteImage(blackWhiteImage);
		imagePanel.setImage(blackWhiteImage);
	}
	
	public void toBlackWhite() {
		int threshold = (int) blackWhiteThreshold.getValue();
		Image image = ImageEditor.getLoadedImage();
		Image blackWhiteImage = ImageEditor.toBlackAndWhite(image, threshold);
		ImageEditor.setBlackWhiteImage(blackWhiteImage);
		imagePanel.setImage(blackWhiteImage);
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
		Main.birdAnalyser.setImage(ImageEditor.getBlackWhiteImage());
		Main.birdAnalyser.instantiateDisjointSetArray((ImageEditor.getBlackWhiteImage()));
		Main.birdAnalyser.combinePixels(
				ImageEditor.getBlackWhiteImage(), (int) minSize.getValue());
		Main.birdAnalyser.createBirdBoundaries(ImageEditor.getBlackWhiteImage());
		imagePanel.setImage(ImageEditor.drawAllRects(ImageEditor.getLoadedImage(),
													 Main.birdAnalyser.getBirdBoundaries()));
		
		birdNumberLabel.setText("Birds found: " + 
				Main.birdAnalyser.countBirds((int) minSize.getValue()) );
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