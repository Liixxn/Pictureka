package Controlador;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.Bounce;
import animatefx.animation.BounceIn;
import animatefx.animation.Pulse;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * En la clase ControladorLogo, manejamos los eventos que ocurran en la vista <b>VentanaLogo</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerán López
 *
 */

public class ControladorLogo {


	@FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPaneLogo;

    @FXML
    private ImageView imgFondo;

    @FXML
    private JFXButton btnEntrar;

    @FXML
    private ImageView imageLogo;
    
    
    @FXML
    public void initialize() {
    	
    	Media sound = new Media(new File("src/camera_shutter.mp3").toURI().toString());
    	
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.setCycleCount(2);
    	mediaPlayer.play();
		
		
    	new BounceIn(imageLogo).playOnFinished(new Bounce(imageLogo).setSpeed(0.4)).setSpeed(0.4).play();
    	new Pulse(btnEntrar).setCycleCount(Timeline.INDEFINITE).setSpeed(0.6).setDelay(new Duration(5000)).play();
    	
    	
    }
    
    
    @FXML
    /**
     * 
     * El usuario pulsa el botón <b>Entrar</b> y se muestra la ventana principal del programa.
     * 
     * @param event    Momento en el que el usuario pulsa el botón.
     */
    void entrarApp(ActionEvent event) {

    	//Cliente clienteDefault = new Cliente(0, "vacio", "", "", "", null);
    	
    	
    	//Se carga el contenido de la ventana principal
        FXMLLoader loaderApp = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
        //Se le asigna el controlador de la ventana principal
        ControladorVPrincipal controlerPrincipal = new ControladorVPrincipal("vacio");
        loaderApp.setController(controlerPrincipal);
        
        //Sacamos una ventana nueva
        Parent root;
        try {
            root = loaderApp.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            //Hacemos que la ventana se muestre maximizada
            stage.setMaximized(true);
            //Medidas minimas de la ventana
	        stage.setMinHeight(600);
	        stage.setMinWidth(700);
            stage.show();
            stage.getIcons().add(new Image("Pictureka2.png"));
            
            
            //Obtenemos la ventanaLogo
            Stage primaryStage = (Stage)btnEntrar.getScene().getWindow();
            //Escondemos la ventanaLogo
            primaryStage.hide();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	
    }
    

	public JFXButton getBtnEntrar() {
		return btnEntrar;
	}


	public void setBtnEntrar(JFXButton btnEntrar) {
		this.btnEntrar = btnEntrar;
	}


	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public void setAnchorPane(AnchorPane anchorPane) {
		this.anchorPane = anchorPane;
	}

	public ImageView getImageLogo() {
		return imageLogo;
	}

	public void setImageLogo(ImageView imageLogo) {
		this.imageLogo = imageLogo;
	}

}
