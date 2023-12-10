package Controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;
import com.jfoenix.controls.JFXButton;
import Modelo.Cifrado;
import Modelo.Guardia;
import Modelo.Registro;
import Modelo.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * 
 * En esta clase se maneja la edición de la información de un guardia en
 * específico, en la vista de <b>TabEditarGuardia</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class ControladorTabEditarGuardia {

	ControladorEditGuardias controlerEditGuardia;
	@FXML
	private AnchorPane AnchorTabEditarGuardia;

	@FXML
	private GridPane gridPaneEditarGuardia;

	@FXML
	private Label lblUsuarioGuardia;

	@FXML
	private TextField textUsuarioGuardia;

	@FXML
	private Label lblNombreGuardia;

	@FXML
	private TextField textNombreGuardia;

	@FXML
	private Label lblApellido1Guardia;

	@FXML
	private Label lblApellido2Guardia;

	@FXML
	private TextField textApellido1Guardia;

	@FXML
	private TextField textApellido2Guardia;

	@FXML
	private Label lblEmailGuardia;

	@FXML
	private TextField textEmailGuardia;

	@FXML
	private Label lblDniGuardia;

	@FXML
	private TextField textDniGuardia;

	@FXML
	private Label lblFechaGuardia;

	@FXML
	private Label lblContraseniaGuardia;

	@FXML
	private TextField textContraseniaGuardia;

	@FXML
	private DatePicker DateFechaGuardia;

	@FXML
	private JFXButton btnGuardar;
	
	static final String USER = "pri_Pictureka";
	static final String PASS = "asas";
	Connection conn = null;
	Statement stmt = null;
	String sql;

	Guardia guardia;

	/**
	 * 
	 * Constructor de la clase <b>ControladorTabEditarGuardia</b> al que se le pasa
	 * el Controlador de edicciones de guardias.
	 * 
	 * @param controlerEdit Controlador de la clase <b>ControladorEditGuardias</b>
	 *                      con sus respecitvos atributos y métodos.
	 */
	public ControladorTabEditarGuardia(ControladorEditGuardias controlerEdit) {

		this.controlerEditGuardia = controlerEdit;
	}

	@FXML
	/**
	 * 
	 * Inicializa el Tab, comrobando si el administrador ha seleccionado un guardia
	 * a editar.
	 * 
	 */
	public void initialize() {

		// Se comprueba que el administrador ha seleccionado a un guardia de la tabla
		if (!controlerEditGuardia.getTableView().getSelectionModel().isEmpty()) {

			// Se recoge la informacion del guardia que el administrador ha seleccionado y
			// se muestra en los diferentes campos
			textUsuarioGuardia
					.setText(controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getUsuario());
			textNombreGuardia
					.setText(controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getNombre());
			textApellido1Guardia
					.setText(controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getApellido1());
			textApellido2Guardia
					.setText(controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getApellido2());
			textDniGuardia.setText(controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getDni());
			textEmailGuardia
					.setText(controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getEmail());
			DateFechaGuardia.setValue(
					controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getFechaNacimiento());
			textContraseniaGuardia.setText(
					controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getContrasenia());
		}
	}

	@FXML
	/**
	 * 
	 * Guarda los cambios realizados en uno o más de los campos de información del guardia, cambiando el contenido del Json de Staff.
	 * 
	 * @param event		Evento causado cuando el administrador pulsa sobre el botón "Guardar Cambios".
	 */
	void GuardarGuardiaEdit(ActionEvent event) {

		Alert error = new Alert(Alert.AlertType.ERROR);
		Alert informacion = new Alert(Alert.AlertType.INFORMATION);
		Registro registro = new Registro();
		Cifrado cifrar = new Cifrado();

		// Se guarda en un vector la informacion del json del personal de staff

		// Obtenemos los datos de los diferentes jtextfield
		String Usuario = textUsuarioGuardia.getText();
		String nombreNuevo = textNombreGuardia.getText();
		String apellido1Nuevo = textApellido1Guardia.getText();
		String apellido2Nuevo = textApellido2Guardia.getText();
		String dniNuevo = textDniGuardia.getText();
		String emailNuevo = textEmailGuardia.getText();
		LocalDate fechaNuevo = DateFechaGuardia.getValue();
		String contraseniaNuevo = textContraseniaGuardia.getText();


		// Comprobamos que haya seleccionado un administrador
		if (!controlerEditGuardia.getTableView().getSelectionModel().isEmpty()) {
			
			// Comprobamos que el contenido no está vacío
			if (!(Usuario.isEmpty() | nombreNuevo.isEmpty() | apellido1Nuevo.isEmpty() | apellido2Nuevo.isEmpty()
					| dniNuevo.isEmpty() | emailNuevo.isEmpty() | (fechaNuevo == null) | contraseniaNuevo.isEmpty())) {
				
				Staff staff = buscarGuardiaSelecc();
				if((staff.getContrasenia().equals(cifrar.hashing(contraseniaNuevo))==false)) {
					contraseniaNuevo = cifrar.hashing(contraseniaNuevo);
					
				}
				LocalDate fecha = LocalDate.now();
				Period periodo = Period.between(fechaNuevo, fecha);

				// Comprobacion del rango de edad
				if (dniNuevo.length() == 9) {
					if (periodo.getYears() > 18 && periodo.getYears() < 100) {

						// Valida el email nuevo
						if (registro.validarEmail(emailNuevo)) {
							if(nombreNuevo.length()<=10) {
								if(GuardarGuardiaBBDD(Usuario, nombreNuevo, apellido1Nuevo, apellido2Nuevo, dniNuevo,
										emailNuevo, fechaNuevo, contraseniaNuevo)) {
									controlerEditGuardia.actualizarTablaGuardias();
									eliminarContenidoTxtfield();
									informacion.setHeaderText("Cambios realizados con éxito.");
									informacion.showAndWait();
								}
							}
							else {
								error.setHeaderText("La longitud del nombre es mayor que 10 ");
								error.showAndWait();
							}
						} else {
							error.setHeaderText("Formato de email incorrecto.");
							error.showAndWait();

						}
					}
					else {
					error.setHeaderText("Rango de edad no aceptable.");
					error.showAndWait();
					}
				}
				else {
					error.setHeaderText("El DNI debe tener una longutid de 9 digitos.");
					error.showAndWait();
				}
			}
			else {
				error.setHeaderText("Revise que todos los campos están completos.");
				error.showAndWait();
			}
		} else {
			error.setHeaderText("No se ha seleccionado ningún guardia a modificar.");
			error.showAndWait();
		}

	}
	public boolean GuardarGuardiaBBDD(String Usuario,String nombreNuevo,String apellido1Nuevo,String apellido2Nuevo,String dniNuevo,String emailNuevo,LocalDate fechaNuevo,String contraseniaNuevo) {
		boolean registrado = true;
		
		Date date = Date.valueOf(fechaNuevo);
		
		String userSelecc = controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getUsuario();
		
		if (!controlerEditGuardia.getTableView().getSelectionModel().isEmpty()) {

			// Comprobamos que el contenido no está vacío
			if (!(Usuario.isEmpty() | nombreNuevo.isEmpty() | apellido1Nuevo.isEmpty() | apellido2Nuevo.isEmpty()
					| dniNuevo.isEmpty() | emailNuevo.isEmpty() | (fechaNuevo == null) | contraseniaNuevo.isEmpty())) {
				LocalDate fecha = LocalDate.now();
				Period periodo = Period.between(fechaNuevo, fecha);

				// Comprobaciones para los distintos casos que se pueden dar

				// Comprobacion del rango de edad
				if (periodo.getYears() > 18 && periodo.getYears() < 100) {
					
					try {
						boolean correcto = false;
						Class.forName("org.mariadb.jdbc.Driver");
						
			            conn = DriverManager.getConnection(
			                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
			            
			            //comprobar que no exista en la base de datos de 
					    
						sql = "SELECT * FROM (SELECT * FROM STAFF WHERE STAFF.Usuario != '"+Usuario+"') AS dd"
					    		+ " WHERE dd.Usuario = '"+Usuario+"' OR dd.Email = '"+emailNuevo+"' OR dd.Dni = '"+dniNuevo+"';";
				        stmt = conn.createStatement();
						ResultSet respuestaCliente = stmt.executeQuery( sql );
						if(respuestaCliente.first()) {
							//significa que ya alguno de los datos introducitos ya esta registrado
							correcto = true;
						}
						respuestaCliente.close();
						stmt.close();
						if (correcto == false) {
							//consulta que comprueba que el usuario no se repita, el email no se repita, dni no se repita entre 
							//el mismo tipo de usuario
							sql = "SELECT * FROM (SELECT * FROM CLIENTE WHERE CLIENTE.Usuario != '"+Usuario+"') AS dd"
						    		+ " WHERE dd.Usuario = '"+Usuario+"' OR dd.Email = '"+emailNuevo+"' ;";
					        stmt = conn.createStatement();
							ResultSet respuestaStaff = stmt.executeQuery( sql );
							if(respuestaStaff.first()) {
								//significa que ya alguno de los datos introducitos ya esta registrado
								correcto = true;
							}
							respuestaStaff.close();
							stmt.close();
						}
						if(correcto == false) {
			            	sql = "UPDATE STAFF SET "
				            		+ "Usuario = '"+Usuario+"', "
				            		+ "Nombre = '"+ nombreNuevo +"', "
				            		+ "Apellido1 = '"+apellido1Nuevo +"',"
		            				+ "Apellido2 = '"+apellido2Nuevo+"', "
				            		+ "identificadorUser = 2, "
				            		+ "Dni = '"+dniNuevo +"', "
		            				+ "FechaNacimiento = '"+date+"', "
				            		+ "Email= '"+ emailNuevo +"', "
				            		+ "Contraseña = '"+contraseniaNuevo +"' "
				            				+ "WHERE "
				            				+ "STAFF.Usuario = '"+userSelecc+"';";

				            stmt = conn.createStatement();
				   			stmt.executeQuery( sql );
				   			stmt.close();
						}
						else {
							Alert error = new Alert(Alert.AlertType.ERROR);
							error.setHeaderText("Revise que todos los campos están completos.");
							error.showAndWait();
							registrado = false;
						}
			   		}
					catch(SQLException | ClassNotFoundException e){
					}
				}
			}
		}
		return registrado;
	}
	
	
	
	
	
	public Staff buscarGuardiaSelecc() {
		Registro registro = new Registro();
		Vector<Staff> staff = registro.recuperarStaff();
		registro.recuperarClientes();
		Staff nuevo = null;
		int i=0;
		boolean encontrado = false;
		while(i<staff.size() && encontrado==false) {

			// Comrpobamos que el guardia a modificar que ha seleccionado el administrador
			// se encuentra en el json
			if (staff.get(i).getUsuario().equals(
					controlerEditGuardia.getTableView().getSelectionModel().getSelectedItem().getUsuario())) {

				nuevo = new Staff(2, this.getTextUsuarioGuardia().getText(), this.textNombreGuardia.getText(), this.textApellido1Guardia.getText(),
						this.textApellido2Guardia.getText(), this.textDniGuardia.getText(), this.textEmailGuardia.getText(), this.textContraseniaGuardia.getText(), this.DateFechaGuardia.getValue());
            	encontrado=true;
			
			}
			i++;
		}
		return nuevo;
	}
	
	void eliminarContenidoTxtfield() {
			
			textUsuarioGuardia.clear();
			textNombreGuardia.clear();
			textApellido1Guardia.clear();
			textApellido2Guardia.clear();
			textDniGuardia.clear();
			textEmailGuardia.clear();
			textContraseniaGuardia.clear();
			DateFechaGuardia.getEditor().clear();
		}
		
	
	
	
	
	
	
	
	

	public AnchorPane getAnchorTabEditarGuardia() {
		return AnchorTabEditarGuardia;
	}

	public void setAnchorTabEditarGuardia(AnchorPane anchorTabEditarGuardia) {
		AnchorTabEditarGuardia = anchorTabEditarGuardia;
	}

	public TextField getTextUsuarioGuardia() {
		return textUsuarioGuardia;
	}

	public JFXButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JFXButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public void setTextUsuarioGuardia(TextField textUsuarioGuardia) {
		this.textUsuarioGuardia = textUsuarioGuardia;
	}

	public TextField getTextNombreGuardia() {
		return textNombreGuardia;
	}

	public void setTextNombreGuardia(TextField textNombreGuardia) {
		this.textNombreGuardia = textNombreGuardia;
	}

	public TextField getTextApellido1Guardia() {
		return textApellido1Guardia;
	}

	public void setTextApellido1Guardia(TextField textApellido1Guardia) {
		this.textApellido1Guardia = textApellido1Guardia;
	}

	public DatePicker getDateFechaGuardia() {
		return DateFechaGuardia;
	}

	public void setDateFechaGuardia(DatePicker dateFechaGuardia) {
		DateFechaGuardia = dateFechaGuardia;
	}

	public TextField getTextApellido2Guardia() {
		return textApellido2Guardia;
	}

	public void setTextApellido2Guardia(TextField textApellido2Guardia) {
		this.textApellido2Guardia = textApellido2Guardia;
	}

	public TextField getTextEmailGuardia() {
		return textEmailGuardia;
	}

	public void setTextEmailGuardia(TextField textEmailGuardia) {
		this.textEmailGuardia = textEmailGuardia;
	}

	public TextField getTextDniGuardia() {
		return textDniGuardia;
	}

	public void setTextDniGuardia(TextField textDniGuardia) {
		this.textDniGuardia = textDniGuardia;
	}

	public TextField getTextContraseniaGuardia() {
		return textContraseniaGuardia;
	}

	public void setTextContraseniaGuardia(TextField textContraseniaGuardia) {
		this.textContraseniaGuardia = textContraseniaGuardia;
	}

}
