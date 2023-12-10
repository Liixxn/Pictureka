package Controlador;

import java.time.LocalDate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import Modelo.Cifrado;
import Modelo.Guardia;
import Modelo.modelo_Museo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * 
 * En esta clase se maneja el registro de un guardia, en la vista <b>TabAniadirGuardia</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class ControladorTabAniadirGuardia {

	ControladorEditGuardias controlerEdit;
	
    @FXML
    private AnchorPane anchorPaneTab1;

    @FXML
    private GridPane gridTab1;

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lbl1Apellido;

    @FXML
    private Label lbl2Apellido;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblDni;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private Label lblContrasenia;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private JFXTextField textUsuario;

    @FXML
    private JFXTextField textNombre;

    @FXML
    private JFXTextField text1Apellido;

    @FXML
    private JFXTextField text2Apellido;

    @FXML
    private JFXTextField textEmail;

    @FXML
    private JFXTextField textDNI;

    @FXML
    private JFXTextField textContrasenia;

    @FXML
    private JFXDatePicker DateGuardiaNacimiento;
     
	
    /**
     * 
     * Constructor de la clase <b>ControladorTabAdniadirGuardia</b> al que se le pasa el Controlador de ediccion de guardias.
     * 
     * @param controladorEdit	Controlador de la clase <b>ControladorEditGuardias</b> con su respectivos atributos y métodos. 
     */
    public ControladorTabAniadirGuardia(ControladorEditGuardias controladorEdit) {
    	controlerEdit = controladorEdit;
    	
	}
    
    
    @FXML
    /**
     * 
     * Registra un nuevo guardia con la información introducida por el administrador. Se escribe en el Json de Staff y se muestra
     * en la tabla de guardias.
     * 
     * @param event		Evento causado cuando el administrador pulsa sobre el botón "Añadir".
     */
    void GuardarNuevoGuardia(ActionEvent event) {
    	
    	modelo_Museo modelo = new modelo_Museo();
    	Alert error = new Alert(Alert.AlertType.ERROR);
    	Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    	Cifrado cifrar = new Cifrado();
    	
    	//Recoge lo introducido por el usuario
    	String usuarioNuevo = textUsuario.getText();
    	String nombreNuevo = textNombre.getText();
    	String apellido1Nuevo = text1Apellido.getText();
    	String apellido2Nuevo = text2Apellido.getText();
    	String emailNuevo = textEmail.getText();
    	String dniNuevo = textDNI.getText();
    	String contraseniaNuevo = textContrasenia.getText();
    	LocalDate fechaNacimientoNuevo = DateGuardiaNacimiento.getValue();
    	
    	//Comprueba que los diferentes campos no esten vacios
    	if (usuarioNuevo.isEmpty() || nombreNuevo.isEmpty() || apellido1Nuevo.isEmpty() || apellido2Nuevo.isEmpty() || emailNuevo.isEmpty() ||
    			dniNuevo.isEmpty() || contraseniaNuevo.isEmpty() || fechaNacimientoNuevo==null) {
    			
    		error.setHeaderText("Error");
    		error.setContentText("Compruebe los campos a rellenar para añadir un guardia");
    		error.showAndWait();
    	}
    	else {
			
			// Dependiendo del estado que devuelva el metodo registrarGuardias, se realizara
			// una accion u otra
			String estado = modelo.getRegistro().registrarStaff(usuarioNuevo, dniNuevo, emailNuevo, cifrar.hashing(contraseniaNuevo), nombreNuevo,
					apellido1Nuevo, apellido2Nuevo, fechaNacimientoNuevo, 2);
			if (estado.equals("Validacion completada con exito")) {
				// se muestra en la tabla al nuevo guardia
				this.controlerEdit.getTableView().getItems().add(new Guardia(usuarioNuevo, dniNuevo, emailNuevo,
						cifrar.hashing(contraseniaNuevo), fechaNacimientoNuevo, nombreNuevo, apellido1Nuevo, apellido2Nuevo));
			}
			confirmacion.setHeaderText(estado);
			confirmacion.showAndWait();
		}
	}

	public AnchorPane getAnchorPaneTab1() {
		return anchorPaneTab1;
	}

	public void setAnchorPaneTab1(AnchorPane anchorPaneTab1) {
		this.anchorPaneTab1 = anchorPaneTab1;
	}

}
