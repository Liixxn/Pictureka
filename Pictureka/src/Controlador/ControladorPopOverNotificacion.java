package Controlador;

import java.util.Vector;

import org.omg.CORBA.INITIALIZE;

import Modelo.Alerta;
import Modelo.Sensor;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class ControladorPopOverNotificacion {

    @FXML
    private AnchorPane anchorPanePop;

    @FXML
    private ScrollPane scrollPanePopNoti;

	@FXML
    private AnchorPane anchorScrollNoti;

    @FXML
    private TextArea textHistorial;
    
    private String usuario;
    
    boolean logged; //Este nos dira si la parsona esta logueada o no
    
    private String tipoStaff;
    
    Vector<Sensor> sensores;
    
    ControladorGuardia controladorGuardia;
    
    ControladorAdministrador controladorAdministrador;
    
    Alerta a;
    
    @FXML
    public void initialize() {
    	
    	if (tipoStaff.equals("Guardia")) {
    		this.controladorGuardia.getImgNotificaciones().setImage(new Image("/NotificationVacio.png"));
    	}
    	else if (tipoStaff.equals("Administrador")) {
    		this.controladorAdministrador.getImgNotiAdmin().setImage(new Image("/NotificationVacio.png"));
    	}
    	
    	
    	sensores = a.getSensores();
    	textHistorial.clear();
    	
    	for(int i=0; i<sensores.size(); i++) {
    		
    		textHistorial.setText("\nSala: " + sensores.get(i).getID_Sala() + " | Posicion: "+ sensores.get(i).getPosicion() + " ---> " + sensores.get(i).getTipo() + ": " + sensores.get(i).getLectura() + "\n" + textHistorial.getText());
    		
    	}
    	
    	
    	
    }
    
    
    public ControladorPopOverNotificacion(String usuario, String tipoStaff, ControladorGuardia controllerG, Alerta alert) {
    	this.usuario = usuario;
    	this.tipoStaff = tipoStaff;
    	this.controladorGuardia = controllerG;
    	this.a = alert;
    }
    
    public ControladorPopOverNotificacion(String usuario, String tipoStaff, ControladorAdministrador controllerA, Alerta alert) {
    	this.usuario = usuario;
    	this.tipoStaff = tipoStaff;
    	this.controladorAdministrador = controllerA;
    	this.a = alert;
    }
    
    
    public ControladorPopOverNotificacion(Vector<Sensor> sensores1) {
    	
    	this.sensores = sensores1;
    }
    
    
    public TextArea getTextHistorial() {
		return textHistorial;
	}

	public void setTextHistorial(TextArea textHistorial) {
		this.textHistorial = textHistorial;
	}

}