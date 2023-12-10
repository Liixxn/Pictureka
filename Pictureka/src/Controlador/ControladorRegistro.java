package Controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import Modelo.Cifrado;
import Modelo.modelo_Museo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * 
 * En esta clase, se maneja la funcionalidad de registro de un usuario, en la vista <b>VentanaRegistro</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class ControladorRegistro {

    @FXML
    private AnchorPane anchorPaneRegistro;

    @FXML
    private GridPane gridRegistro;

    @FXML
    private Region regionRegistro;

    @FXML
    private ImageView imgPaneRegistro;

    @FXML
    private JFXButton btnCancelRegistro;

    @FXML
    private JFXButton btnRegistrar;

    @FXML
    private GridPane gridInfoRegistro;

    @FXML
    private Label lblUsuarioRegistro;

    @FXML
    private TextField textUsuarioRegistro;

    @FXML
    private Label lblCorreoElectronico;

    @FXML
    private TextField textCorreoElectronico;

    @FXML
    private Label lblDni;

    @FXML
    private TextField textDni;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private JFXDatePicker chooserCalendario;

    @FXML
    private Label lblContraseniaRegistro;

    @FXML
    private PasswordField txtFieldPassword;

    @FXML
    private Label lblRepeatPassword;

    @FXML
    private PasswordField textFieldRepeatPassword;

    @FXML
    /**
     * 
     * Cancela el registro del usuario y devuelve la ventana de inicio de sesión.
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre el boton de Cancelar.
     */
    void CancelRegistro(ActionEvent event) {

    	abrirLogin();
    }

    @FXML
    /**
     * 
     * Método que recoge los datos introducidos por el usuario en los diferentes JTextFields. Se comprueba que la información sea válida,
     * y una vez lo sea, se registra el usuario, escribiéndose en el Json de "usuarios".
     * 
     * @param event		Evento causado cuando el usaurio pulsa sobre el botón de Registrar.
     */
    void RegistrarUsuario(ActionEvent event) {

    	modelo_Museo museo = new modelo_Museo();
    	Alert error = new Alert(Alert.AlertType.ERROR);
    	Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
    	Cifrado cifrar = new Cifrado();
    	
    	String estado;
    	//Se obtienen las contrasenias escritas
    	String contrasenia = txtFieldPassword.getText();
    	String repetirContrasenia = textFieldRepeatPassword.getText();
    	LocalDate fechaNuevo = chooserCalendario.getValue();
    	//Se comprueba que ninguno de los campos este vacio
	    if(!(textUsuarioRegistro.getText().isEmpty() | textDni.getText().isEmpty() | textCorreoElectronico.getText().isEmpty() | contrasenia.isEmpty()
					| repetirContrasenia.isEmpty() |(fechaNuevo == null) )) {	
			//Se comprueba que la fecha de nacimiento se encuentre entre un rango aceptable
    		LocalDate fecha = LocalDate.now();
        	Period periodo = Period.between(chooserCalendario.getValue(), fecha);
    		if (periodo.getYears() > 18 && periodo.getYears() < 100) {
    			//Se comrpueba que las contrasenias sean iguales
    			if(textUsuarioRegistro.getText().length()<=10) {
    				if(contrasenia.equals(repetirContrasenia)&&!(contrasenia.equals(""))&&!(contrasenia.equals(" "))) {
    					if(textDni.getText().length()==9) {
    						//Se registra el usuario
	                		estado = museo.getRegistro().registrarCliente(textUsuarioRegistro.getText(), textDni.getText(), textCorreoElectronico.getText(), cifrar.hashing(txtFieldPassword.getText()), chooserCalendario.getValue());
	                		if (estado.equals("Validacion completada con exito")) {
	                			confirmacion.setHeaderText(estado);
	                			confirmacion.showAndWait();
	                			abrirLogin();
	                			
	                		}
	                		else {
	                			error.setHeaderText(estado);
	                			error.show();
	                		}
    					}else {
    						error.setHeaderText("Error: El DNI introducido no es valido");
	                		error.show();
    					}
                	}
                	else {
                		 error.setHeaderText("Error: las contraseñas no coinciden");
                		 error.show();
                	}
            	}
            	else {
            		 error.setHeaderText("Error: El usuario sobrepasa 10 digitos");
            		 error.show();
            	}
    			
        	}
    		else {
    			error.setHeaderText("Error: Rango de edad no aceptable");
       		 	error.show();
    		}
    	}
    	else {
    		error.setHeaderText("¡Porfavor rellene todos los campos!");
    		error.show();
    			
    	}
	}
    /**
     * 
     * Método que muestra la ventana de Login.
     * 
     */
    void abrirLogin() {
    	FXMLLoader loaderApp = new FXMLLoader(getClass().getResource("/application/InterfazLogin.fxml"));
        ControladorInicioSesion controlerInicioSesion = new ControladorInicioSesion();
        loaderApp.setController(controlerInicioSesion);
        
        AnchorPane registerPane;
		try {
			registerPane = (AnchorPane) loaderApp.load();
	        anchorPaneRegistro.getChildren().clear();
	        AnchorPane.setTopAnchor(registerPane, 0.0);
	        AnchorPane.setRightAnchor(registerPane, 0.0);
	        AnchorPane.setLeftAnchor(registerPane, 0.0);
	        AnchorPane.setBottomAnchor(registerPane, 0.0);
	        anchorPaneRegistro.getChildren().setAll(registerPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
