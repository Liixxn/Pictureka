package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToolbar;

import Modelo.Alerta;
import Modelo.Cifrado;
import Modelo.Cliente;
import Modelo.Registro;
import Modelo.Staff;
import Modelo.modelo_Museo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * 
 * En esta clase se maneja la informaci�n de cada usuario y se muestra en la vista <b>VentanaPerfil</b> con la respectiva información
 * del usuario iniciado en ese momento.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class ControladorPerfil {


    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private BorderPane BordPanePrincipal;

    @FXML
    private JFXToolbar barra;

    @FXML
    private ImageView imgRegresar;

    @FXML
    private ImageView imgUsuario1;

    @FXML
    private Label LabelUsuario;

    @FXML
    private Label RFechaDNa;

    @FXML
    private Label LabelFechaDNa;

    @FXML
    private Label REmail;

    @FXML
    private Label RDNI;

    @FXML
    private Label RUsuario;

    @FXML
    private Label LabelEmail;

    @FXML
    private Label LabelDNI;

    @FXML
    private Label LabelNombre;

    @FXML
    private Label LabelApellido2;

    @FXML
    private Label RApellido2;

    @FXML
    private Label RApellido1;

    @FXML
    private Label LabelApellido1;

    @FXML
    private Label RNombre;
    
    @FXML
    private ImageView imgReserva;
    
    @FXML
    private JFXTextField TextFieldUsuario;

    @FXML
    private JFXTextField TextFieldDNI;

    @FXML
    private JFXTextField TextFieldEmail;

    @FXML
    private JFXDatePicker DatePickerFecha;

    @FXML
    private ImageView imgEditUsuario;
    
    @FXML
    private JFXButton BotonGuardar;

    @FXML
    private JFXButton BotonCancelar;
    
    @FXML
    private Label LabelContrasenia;

    @FXML
    private JFXPasswordField PasswordField;
    
    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada

    boolean logged; //Este nos dira si la parsona esta logueada o no

    private int identificador;

    private Cliente cliente;

    private Staff staff;
    
    Alerta alertaNotificaciones;

    Connection conn = null;
    
    Statement stmt = null;
    
    String sql;
    
    static final String USER = "pri_Pictureka";
	
    static final String PASS = "asas";

    /**
     * 
     * Constructor de la clase <b>ControladorPerfil</b> que guarda la información del usaurio iniciado sesión.
     * 
     * @param usuario		El usuario que se encuentra iniciado sesión
     */
	 public ControladorPerfil(String usuario)  {
		 if (usuario == "vacio") {
			 logged = false;
			 this.usuario = usuario;
			 Alert error = new Alert(Alert.AlertType.ERROR);
			 error.setHeaderText("Error: PROGRAMADOR UN USUARIO NO DEBERIA VER SUS CREDENCIALES SIN HABER INICIADO SESION");
			 error.show();
		 }
		 else {
			 this.usuario = usuario;
			 logged = true;
			 modelo_Museo museo = new modelo_Museo();
			 //museo obtener identificador de usuario
			 int _identificador = museo.devolverIdentificador(usuario);
			 identificador = _identificador;
			 //guardar el usuario dependiendo del identificador
			 if (identificador == 1) {
				Cliente provisionalCli = museo.getRegistro().recuperar1Cliente(usuario);
			 	this.cliente = provisionalCli;
			 }
			 else {
				Staff provisionalStaff = museo.getRegistro().recuperar1Staff(usuario);
		 		this.staff = provisionalStaff;
			 }
		 }
	}
	 
	 
	 public ControladorPerfil(String usuario, Alerta alerta)  {
		 if (usuario == "vacio") {
			 logged = false;
			 this.usuario = usuario;
			 this.alertaNotificaciones = alerta;
			 Alert error = new Alert(Alert.AlertType.ERROR);
			 error.setHeaderText("Error: PROGRAMADOR UN USUARIO NO DEBERIA VER SUS CREDENCIALES SIN HABER INICIADO SESION");
			 error.show();
		 }
		 else {
			 this.usuario = usuario;
			 this.alertaNotificaciones = alerta;
			 logged = true;
			 modelo_Museo museo = new modelo_Museo();
			 //museo obtener identificador de usuario
			 int _identificador = museo.devolverIdentificador(usuario);
			 identificador = _identificador;
			 //guardar el usuario dependiendo del identificador
			 if (identificador == 1) {
				Cliente provisionalCli = museo.getRegistro().recuperar1Cliente(usuario);
			 	this.cliente = provisionalCli;
			 }
			 else {
				Staff provisionalStaff = museo.getRegistro().recuperar1Staff(usuario);
		 		this.staff = provisionalStaff;
			 }
		 }
	}
	 

	 @FXML
	 /**
	  * 
	  * Inicializa la ventana perfil, mostrando los diferentes campos de información, dependiendo del usuario iniciado sesión.
	  * 
	  */
	  	public void initialize() {
		 	mostrarPerfil();
	  	}
	 

	@FXML
	 /**
	  * 
	  * Muestra al cliente su lista de reservas, mostrando la información de éstas, ofreciendo la posibilidad de cancelar la 
	  * reserva que desee.
	  * 
	  * @param event	Evento causado cuando el cliente pulsa sobre la imagen de su reserva.
	  */
	 void abrirReserva(MouseEvent event) {
		//Se carga el contenido de la ventana
     	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaEditarTickets.fxml"));
     	//Se le asigna el controlador de la ventana para editar informacion de los guardias
         ControladorEditarReserva controlerEditRese = new ControladorEditarReserva(usuario);
         loaderPrincipal.setController(controlerEditRese);
         AnchorPane PaneVentanaPrincipal;

 		try {
 			//Se carga en un AnchorPane la ventana
 			PaneVentanaPrincipal = (AnchorPane) loaderPrincipal.load();

 			//Se elimina el contenido de la ventana padre
 			anchorPanePrincipal.getChildren().clear();

         	//Se ajusta el AnchorPane para que sea escalable
             AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
             AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
             AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
             AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);
             


             //Se añade el contenido de la ventana cargada en el AnchorPane del padre
 	        anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);
 	      

 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}
	 }

    @FXML
    /**
     * 
     * Devuelve al usuario a su ventana inicial, dependiendo del usuario iniciado sesión
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre la imagen de volver atrás.
     */
    void volver(MouseEvent event) {

    	
    	
    	//Comprueba que lo devuelto por el método loginUsuario se corresponde con los diferentes identificadores que tienen cada usuario
    	if (identificador==1) {
    		//Se carga el contenido de la ventana
        	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias
            ControladorVPrincipal controlerPrincipal = new ControladorVPrincipal(usuario);
            loaderPrincipal.setController(controlerPrincipal);
            AnchorPane PaneVentanaPrincipal;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneVentanaPrincipal = (AnchorPane) loaderPrincipal.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);
                controlerPrincipal.getLblBienvenido().setVisible(false);
                //controlerPrincipal.getGridPaneButton().setStyle("-fx-background-color: #00aae4");
                controlerPrincipal.getGridPaneButton().setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");
    	        controlerPrincipal.getAvatarUsuario().setImage(new Image("/avatarCliente.png"));


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
    	        anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    	}
    	else if (identificador==2) {
    		
    		alertaNotificaciones.getTimer_alert().cancel();
    		
    		//Se carga el contenido de la ventana
        	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaGuardia.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias
            ControladorGuardia controlerPrincipal = new ControladorGuardia(usuario);
            loaderPrincipal.setController(controlerPrincipal);
            AnchorPane PaneVentanaPrincipal;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneVentanaPrincipal = (AnchorPane) loaderPrincipal.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

    	}
    	else if (identificador==3) {
    		
    		alertaNotificaciones.getTimer_alert().cancel();
    		
    		//Se carga el contenido de la ventana
        	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaAdministrador.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias
            ControladorAdministrador controlerPrincipal = new ControladorAdministrador(usuario);
            loaderPrincipal.setController(controlerPrincipal);
            AnchorPane PaneVentanaPrincipal;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneVentanaPrincipal = (AnchorPane) loaderPrincipal.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

    	}

    }
    
    
    @FXML
    void EditarInformacion(MouseEvent event) {
    	habilitarModoEdicion();
    	cargarDatosEdicion();
    	
    }
    
    
	

	@FXML
    void GuardarInformacion(MouseEvent event) {
		GuardarClienteEdit();
		eliminarContenidoTxtfield();
		deshabilitarModoEdicion();
		modelo_Museo museo = new modelo_Museo();
		this.cliente = museo.getRegistro().recuperar1Cliente(usuario);
		mostrarPerfil();
		
    }
    
    @FXML
    void Cancelar(MouseEvent event) {
    	deshabilitarModoEdicion();
    }
    
    private void deshabilitarModoEdicion() {
    	DatePickerFecha.setVisible(false);
		TextFieldDNI.setVisible(false);
		TextFieldEmail.setVisible(false);
		TextFieldUsuario.setVisible(false);
		BotonCancelar.setVisible(false);
		BotonGuardar.setVisible(false);
		RUsuario.setVisible(true);
		RDNI.setVisible(true);
		REmail.setVisible(true);
		RFechaDNa.setVisible(true);
		PasswordField.setVisible(false);
		LabelContrasenia.setVisible(false);
	}

	private void habilitarModoEdicion() {
    	DatePickerFecha.setVisible(true);
		TextFieldDNI.setVisible(true);
		TextFieldEmail.setVisible(true);
		TextFieldUsuario.setVisible(true);
		BotonCancelar.setVisible(true);
		BotonGuardar.setVisible(true);
		RUsuario.setVisible(false);
		RDNI.setVisible(false);
		REmail.setVisible(false);
		RFechaDNa.setVisible(false);
		PasswordField.setVisible(true);
		LabelContrasenia.setVisible(true);
	}
	
	private void cargarDatosEdicion() {
		TextFieldDNI.setText(RDNI.getText());
		TextFieldEmail.setText(REmail.getText());
		TextFieldUsuario.setText(RUsuario.getText());
		LocalDate FechaNac = LocalDate.parse(RFechaDNa.getText());
		DatePickerFecha.setValue(FechaNac);
		
	}
	
	
	void GuardarClienteEdit() {

		Alert error = new Alert(Alert.AlertType.ERROR);
		Alert informacion = new Alert(Alert.AlertType.INFORMATION);
		Registro registro = new Registro();
		Cifrado cifrar = new Cifrado();

		// Obtenemos los datos de los diferentes jtextfield
		String Usuario = TextFieldUsuario.getText();
		String dniNuevo = TextFieldDNI.getText();
		String emailNuevo = TextFieldEmail.getText();
		LocalDate fechaNuevo = DatePickerFecha.getValue();
		String contraseniaNuevo = PasswordField.getText();

		// Comprobamos que el contenido no está vacío
		if (!(Usuario.isEmpty() | dniNuevo.isEmpty() | emailNuevo.isEmpty() | (fechaNuevo == null))) {
			if (contraseniaNuevo.isEmpty()) {
				contraseniaNuevo = cliente.getContrasenia();
			}else {
				if ((cliente.getContrasenia().equals(cifrar.hashing(contraseniaNuevo)) == false)) {
					contraseniaNuevo = cifrar.hashing(contraseniaNuevo);
				}
			}
			LocalDate fecha = LocalDate.now();
			Period periodo = Period.between(fechaNuevo, fecha);

			// Comprobacion del rango de edad
			if (dniNuevo.length() == 9) {
				if (periodo.getYears() > 18 && periodo.getYears() < 100) {
					// Valida el email nuevo
					if (registro.validarEmail(emailNuevo)) {
						if (GuardarClienteBBDD(Usuario, dniNuevo, emailNuevo, fechaNuevo, contraseniaNuevo)) {
							mostrarPerfil();
							eliminarContenidoTxtfield();
							informacion.setHeaderText("Cambios realizados con éxito.");
							informacion.showAndWait();
						}
					} else {
						error.setHeaderText("Formato de email incorrecto.");
						error.showAndWait();

					}
				} else {
					error.setHeaderText("Rango de edad no aceptable.");
					error.showAndWait();
				}
			} else {
				error.setHeaderText("El DNI debe tener una longutid de 9 digitos.");
				error.showAndWait();
			}
		} else {
			error.setHeaderText("Revise que todos los campos están completos.");
			error.showAndWait();
		}
	}

	public boolean GuardarClienteBBDD(String Usuario, String dniNuevo, String emailNuevo, LocalDate fechaNuevo,
			String contraseniaNuevo) {
		boolean registrado = true;

		Date date = Date.valueOf(fechaNuevo);


		// Comprobaciones para los distintos casos que se pueden dar
		try {
			boolean correcto = false;
			Class.forName("org.mariadb.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);

			// comprobar que no exista en la base de datos de

			sql = "SELECT * FROM (SELECT * FROM STAFF WHERE STAFF.Usuario != '" + Usuario + "') AS dd"
					+ " WHERE dd.Usuario = '" + Usuario + "' OR dd.Email = '" + emailNuevo + "';";
			stmt = conn.createStatement();
			ResultSet respuestaCliente = stmt.executeQuery(sql);
			if (respuestaCliente.first()) {
				// significa que ya alguno de los datos introducitos ya esta registrado
				correcto = true;
			}
			respuestaCliente.close();
			stmt.close();
			if (correcto == false) {
				// consulta que comprueba que el usuario no se repita, el email no se repita,
				// dni no se repita entre
				// el mismo tipo de usuario
				sql = "SELECT * FROM (SELECT * FROM CLIENTE WHERE CLIENTE.Usuario != '" + Usuario + "') AS dd"
						+ " WHERE dd.Usuario = '" + Usuario + "' OR dd.Email = '" + emailNuevo + "' OR dd.Dni = '"
						+ dniNuevo + "';";
				stmt = conn.createStatement();
				ResultSet respuestaStaff = stmt.executeQuery(sql);
				if (respuestaStaff.first()) {
					// significa que ya alguno de los datos introducitos ya esta registrado
					correcto = true;
				}
				respuestaStaff.close();
				stmt.close();
			}
			if (correcto == false) {
				sql = "UPDATE CLIENTE SET " + "Usuario = '" + Usuario + "', " + "Dni = '" + dniNuevo + "', "
						+ "FechaNacimiento = '" + date + "', " + "Email= '" + emailNuevo + "', " + "Contraseña = '"
						+ contraseniaNuevo + "' " + "WHERE " + "CLIENTE.Usuario = '" + Usuario + "';";

				stmt = conn.createStatement();
				stmt.executeQuery(sql);
				stmt.close();
			} else {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setHeaderText("Error: DNI, Emanil ya registrados.");
				error.showAndWait();
				registrado = false;
			}
		} catch (SQLException | ClassNotFoundException e) {
		}

		return registrado;
	}


    
    /**
     * 
     * Muestra una información diferente si el usuario iniciado sesión es un guardia o un administrador.
     * 
     */
    private void staffConfiguracion() {
    	RUsuario.setText(staff.getUsuario());
		RDNI.setText(staff.getDni());
		REmail.setText(staff.getEmail());
		RFechaDNa.setText((staff.getFechaNacimiento()).toString());
		RApellido1.setText(staff.getApellido1());
		RApellido2.setText(staff.getApellido2());
		RNombre.setText(staff.getNombre());
		imgReserva.setVisible(false);
		imgEditUsuario.setVisible(false);
		DatePickerFecha.setVisible(false);
		TextFieldDNI.setVisible(false);
		TextFieldEmail.setVisible(false);
		TextFieldUsuario.setVisible(false);	
		BotonCancelar.setVisible(false);
		BotonGuardar.setVisible(false);
		PasswordField.setVisible(false);
		LabelContrasenia.setVisible(false);
		
    }
    
    
    
    private void eliminarContenidoTxtfield() {
    	TextFieldDNI.clear();
		TextFieldEmail.clear();
		TextFieldUsuario.clear();	
		PasswordField.clear();
	}
    

	 private void mostrarPerfil() {
		 switch(identificador) {
	 		case 1:
	 			RUsuario.setText(cliente.getUsuario());
	 			RDNI.setText(cliente.getDni());
	 			REmail.setText(cliente.getEmail());
	 			RFechaDNa.setText((cliente.getFechaNacimiento()).toString());
	 			LabelApellido1.setVisible(false);
	 			LabelApellido2.setVisible(false);
	 			LabelNombre.setVisible(false);
	 			RApellido1.setVisible(false);
	 			RApellido2.setVisible(false);
	 			RNombre.setVisible(false);
	 			DatePickerFecha.setVisible(false);
	 			TextFieldDNI.setVisible(false);
	 			TextFieldEmail.setVisible(false);
	 			TextFieldUsuario.setVisible(false);	
	 			BotonCancelar.setVisible(false);
	 			BotonGuardar.setVisible(false);
	 			PasswordField.setVisible(false);
	 			LabelContrasenia.setVisible(false);
	 			this.barra.setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");

	 			break;
	 		case 2:
	 			staffConfiguracion();
	 			imgUsuario1.setImage(new Image("/guardiaAvatar.png"));
	 			break;
	 		case 3:
	 			staffConfiguracion();
	 			imgUsuario1.setImage(new Image("/administradorAvatar.png"));
	 			break;
	 	}
		
	}
    
    
    
    
    public JFXToolbar getBarra() {
		return barra;
	}

	public void setBarra(JFXToolbar barra) {
		this.barra = barra;
	}

}
