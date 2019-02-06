package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	
	
	@FXML
	/**
	 * Opens a file browser and loads in a chosen image into the program
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
		ImageEditor.setBlackWhiteImage(null);
		imagePanel.setImage(ImageEditor.getLoadedImage());
	}
	
	@FXML
	public void showOriginalImage(ActionEvent event) {
		if (ImageEditor.getLoadedImage() != null) {
			imagePanel.setImage(ImageEditor.getLoadedImage());
		}
	}
	
	@FXML
	public void toBlackWhite(ActionEvent event) {
		int threshold = (int) blackWhiteThreshold.getValue();
		Image image = ImageEditor.getLoadedImage();
		Image blackWhiteImage = ImageEditor.toBlackAndWhite(image, threshold);
		ImageEditor.setBlackWhiteImage(blackWhiteImage);
		imagePanel.setImage(blackWhiteImage);
	}
	
	@FXML
	public void quitProgram(ActionEvent event) {
		System.exit(0);
	}
}
