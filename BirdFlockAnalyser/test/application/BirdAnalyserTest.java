package application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

public class BirdAnalyserTest {
	
	JFXPanel jfxPanel = new JFXPanel();
	Image image = new Image("pixelTest.png");
	BirdAnalyser ba = new BirdAnalyser(image);
	
	@BeforeEach
	public void setup() {
		image = ImageEditor.toBlackAndWhite(image, 50);
		ba = new BirdAnalyser(image);
	}
	
	@Test
	public void instantiateArrayTest() {
		ba.instantiateDisjointSetArray(image);
	}
	
	@Test
	public void getNodeTest() {
		ba.instantiateDisjointSetArray(image);
		ba.getDset().getSets()[53] = 10;
		for (int i = 0; i < ba.getDset().getSets().length; i++) {
			System.out.println("Index " + i + ": " + ba.getDset().getSets()[i]);
		}
		assertEquals(10,ba.getNode(3,5));
	}
}
