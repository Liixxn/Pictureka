package Controlador;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import Modelo.Cliente;
import Modelo.modelo_Museo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * En esta clase se maneja el Pop Up donde se muestra la posibilidad de enviar emails al museo, en el pop up de <b>PopOverCorreo<b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class ControladorPopOverCorreo {

    @FXML
    private AnchorPane anchorPopOver;

    @FXML
    private AnchorPane anchorScrollPopOver;

    @FXML
    private JFXTextField textTituloCorreo;

    @FXML
    private JFXTextArea textAreaCorreo;

    @FXML
    private JFXButton btnEnviarCorreo;

    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada
	
    boolean logged; //Este nos dira si la parsona esta logueada o no
    
	 
    /**
     * 
     * Constructor de la clase <b>ControladorPopOverCorreo</b> que guarda la informaci�n del cliente.
     * 
     * @param usuario	Cliente que se encuentre iniciado sesi�n en ese momento.
     */
	 public ControladorPopOverCorreo(String usuario) {
		 if (usuario == "vacio") {
			 logged = false;
			 this.usuario = usuario;
		 }
		 else {
			 this.usuario = usuario;
			 logged = true;
		 }
		 
	}
    
    
    @FXML
    /**
     * 
     * Env�a un email al email del museo <i>Pictureka</i> con su respectivo t�tulo y cuerpo.
     * 
     * @param event		Evento causado cuando el cliente pulsa sobre el bot�n "Enviar".
     */
    void EnviarEmail(ActionEvent event) {
    	
    	modelo_Museo modelo = new modelo_Museo();
    	Cliente cliente = modelo.devolverCliente(usuario);
    	//Se obtiene el titulo y el cuerpo del email
    	String tituloEmail = textTituloCorreo.getText();
    	String cuerpoEmail = textAreaCorreo.getText();
    	
    	//Se establece el email del receptor
    	String to = "picturekasfw@gmail.com";

        //Se establece el email del cliente
        String from = cliente.getEmail();

        //Envio del email a traves de google smtp
        String host = "smtp.gmail.com";

        //Se obtienen las propiedades del sistema
        Properties properties = System.getProperties();
        
        //Establecer el servidor de email
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //Obtener el objeto de Session y pasar el usuario y la contrasenia
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("picturekasfw@gmail.com", "abajoCrYpt");

            }

        });
        
        
        session.setDebug(true);

        try {
            //Crear un MimeMessage por defecto
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //Establecer el titulo del email
            message.setSubject(cliente.getUsuario() + "(" + cliente.getDni()+"): " + tituloEmail);

            //Establecer el cuerpo del mensaje
            message.setText(cuerpoEmail);

            
            //Envio del mensaje
            Transport.send(message);
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setHeaderText("El email se envió correctamente");
            confirmacion.showAndWait();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        
        
    	
    }

    
    
    
    
	public AnchorPane getAnchorPopOver() {
		return anchorPopOver;
	}

	public void setAnchorPopOver(AnchorPane anchorPopOver) {
		this.anchorPopOver = anchorPopOver;
	}

	public AnchorPane getAnchorScrollPopOver() {
		return anchorScrollPopOver;
	}

	public void setAnchorScrollPopOver(AnchorPane anchorScrollPopOver) {
		this.anchorScrollPopOver = anchorScrollPopOver;
	}

	public JFXTextField getTextTituloCorreo() {
		return textTituloCorreo;
	}

	public void setTextTituloCorreo(JFXTextField textTituloCorreo) {
		this.textTituloCorreo = textTituloCorreo;
	}

	public JFXTextArea getTextAreaCorreo() {
		return textAreaCorreo;
	}

	public void setTextAreaCorreo(JFXTextArea textAreaCorreo) {
		this.textAreaCorreo = textAreaCorreo;
	}

	public JFXButton getBtnEnviarCorreo() {
		return btnEnviarCorreo;
	}

	public void setBtnEnviarCorreo(JFXButton btnEnviarCorreo) {
		this.btnEnviarCorreo = btnEnviarCorreo;
	}
    

}

