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
 * En esta clase se maneja la edición de información de un administrador, en la vista <b>TabEditarAdministrador</b>.
 *
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class ControladorTabEditarAdmin {

	ControladorEditarAdministrador controlerEditAdmin;
    @FXML
    private AnchorPane AnchorTabEditarAdmin;

    @FXML
    private GridPane gridPaneEditarAdmin;

    @FXML
    private Label lblUsuarioAdmin;

    @FXML
    private TextField textUsuarioAdmin;

    @FXML
    private Label lblNombreAdmin;

    @FXML
    private TextField textNombreAdmin;

    @FXML
    private Label lblApellido1Admin;

    @FXML
    private Label lblApellido2Admin;

    @FXML
    private TextField textApellido1Admin;

    @FXML
    private TextField textApellido2Admin;

    @FXML
    private Label lblEmailAdmin;

    @FXML
    private TextField textEmailAdmin;

    @FXML
    private Label lblDniAdmin;

    @FXML
    private TextField textDniAdmin;

    @FXML
    private Label lblFechaAdmin;

    @FXML
    private Label lblContraseniaAdmin;

    @FXML
    private TextField textContraseniaAdmin;

    @FXML
    private DatePicker DateFechaAdmin;

    @FXML
    private JFXButton btnGuardar;

    static final String USER = "pri_Pictureka";
    static final String PASS = "asas";
    Connection conn = null;
    Statement stmt = null;
    String sql;

    /**
     *
     * Constructor de la clase <b>ControladorTabEditarAdmin</b> al que se le pasa
	 * el Controlador de edicciones de administradores.
     *
     * @param controlerEdit		Controlador de la clase <b>ControladorEditarAdministrador</b>
	 *                      	con sus respecitvos atributos y métodos.
     */
    public ControladorTabEditarAdmin(ControladorEditarAdministrador controlerEdit) {

    	this.controlerEditAdmin = controlerEdit;

    }


	@FXML
	/**
	 *
	 * Inicializa la ventana, comprobando la selección del administrador y mostrando su información en los campos.
	 *
	 */
	public void initialize() {

		// Se comprueba que el administrador ha seleccionado a un administrador de la tabla
		if (!controlerEditAdmin.getTableViewAdministrador().getSelectionModel().isEmpty()) {

			// Se recoge la informacion del administrador que se ha seleccionado y
			// se muestra en los diferentes campos
			textUsuarioAdmin
					.setText(controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getUsuario());
			textNombreAdmin
					.setText(controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getNombre());
			textApellido1Admin
					.setText(controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getApellido1());
			textApellido2Admin
					.setText(controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getApellido2());
			textDniAdmin.setText(controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getDni());
			textEmailAdmin
					.setText(controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getEmail());
			DateFechaAdmin.setValue(
					controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getFechaNacimiento());
			textContraseniaAdmin.setText(
					controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getContrasenia());

		}

	}



	@FXML
	/**
	 *
	 * Guarda los cambios realizados en uno o más de los campos de información del administrador, cambiando el contenido del Json de Staff.
	 *
	 * @param event		Evento causado cuando el administrador pulsa sobre el botón "Guardar Cambios".
	 */
	 void GuardarAdminEdit(ActionEvent event) {

		Alert error = new Alert(Alert.AlertType.ERROR);
		Alert informacion = new Alert(Alert.AlertType.INFORMATION);
		Registro registro = new Registro();
		Cifrado cifrar = new Cifrado();


		// Obtenemos los datos de los diferentes jtextfield
		String Usuario = textUsuarioAdmin.getText();
		String nombreNuevo = textNombreAdmin.getText();
		String apellido1Nuevo = textApellido1Admin.getText();
		String apellido2Nuevo = textApellido2Admin.getText();
		String dniNuevo = textDniAdmin.getText();
		String emailNuevo = textEmailAdmin.getText();
		LocalDate fechaNuevo = DateFechaAdmin.getValue();
		String contraseniaNuevo = textContraseniaAdmin.getText();

		// Comprobamos que haya seleccionado un administrador
		if (!controlerEditAdmin.getTableViewAdministrador().getSelectionModel().isEmpty()) {

			// Comprobamos que el contenido no está vacío
			if (!(Usuario.isEmpty() | nombreNuevo.isEmpty() | apellido1Nuevo.isEmpty() | apellido2Nuevo.isEmpty()
					| dniNuevo.isEmpty() | emailNuevo.isEmpty() | (fechaNuevo == null) | contraseniaNuevo.isEmpty())) {

				Staff staff = buscarAdminSelecc();
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
								if(GuardarAdminBBDD(Usuario, nombreNuevo, apellido1Nuevo, apellido2Nuevo, dniNuevo,
										emailNuevo, fechaNuevo, contraseniaNuevo)) {
									controlerEditAdmin.actualizarTablaAdmins();
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
			error.setHeaderText("No se ha seleccionado ningún administrador a modificar.");
			error.showAndWait();
		}

	}
	public boolean GuardarAdminBBDD(String Usuario,String nombreNuevo,String apellido1Nuevo,String apellido2Nuevo,String dniNuevo,String emailNuevo,LocalDate fechaNuevo,String contraseniaNuevo) {
		boolean registrado = true;

		Date date = Date.valueOf(fechaNuevo);

		String userSelecc = controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getUsuario();

		if (!controlerEditAdmin.getTableViewAdministrador().getSelectionModel().isEmpty()) {

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
				            		+ "identificadorUser = 3, "
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
							error.setHeaderText("Error: DNI, Emanil ya registrados.");
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


	//busca el admin que se haya seleccionado en la tabla
	public Staff buscarAdminSelecc(){
		Registro registro = new Registro();
		Vector<Staff> staff = registro.recuperarStaff();
		registro.recuperarClientes();
		Staff nuevo = null;
		int i=0;
		boolean encontrado = false;

		// Recorremos el json staff
        while(i<staff.size() && encontrado==false) {

            // Comrpobamos que el administrador a modificar que se ha seleccionado
            if (staff.get(i).getUsuario().equals(
                    controlerEditAdmin.getTableViewAdministrador().getSelectionModel().getSelectedItem().getUsuario())){

            	nuevo = new Staff(3, this.getTextUsuarioAdmin().getText(), this.textNombreAdmin.getText(), this.textApellido1Admin.getText(),
						this.textApellido2Admin.getText(), this.textDniAdmin.getText(), this.textEmailAdmin.getText(), this.textContraseniaAdmin.getText(), this.DateFechaAdmin.getValue());
            	encontrado=true;

            }
            i++;
        }
		return nuevo;
	}


	void eliminarContenidoTxtfield() {

		textUsuarioAdmin.clear();
		textNombreAdmin.clear();
		textApellido1Admin.clear();
		textApellido2Admin.clear();
		textDniAdmin.clear();
		textEmailAdmin.clear();
		textContraseniaAdmin.clear();
		DateFechaAdmin.getEditor().clear();
	}


	public ControladorEditarAdministrador getControlerEditAdmin() {
		return controlerEditAdmin;
	}

	public void setControlerEditAdmin(ControladorEditarAdministrador controlerEditAdmin) {
		this.controlerEditAdmin = controlerEditAdmin;
	}

	public AnchorPane getAnchorTabEditarAdmin() {
		return AnchorTabEditarAdmin;
	}

	public void setAnchorTabEditarAdmin(AnchorPane anchorTabEditarAdmin) {
		AnchorTabEditarAdmin = anchorTabEditarAdmin;
	}

	public GridPane getGridPaneEditarAdmin() {
		return gridPaneEditarAdmin;
	}

	public void setGridPaneEditarAdmin(GridPane gridPaneEditarAdmin) {
		this.gridPaneEditarAdmin = gridPaneEditarAdmin;
	}

	public TextField getTextUsuarioAdmin() {
		return textUsuarioAdmin;
	}

	public void setTextUsuarioAdmin(TextField textUsuarioAdmin) {
		this.textUsuarioAdmin = textUsuarioAdmin;
	}

	public TextField getTextNombreAdmin() {
		return textNombreAdmin;
	}

	public void setTextNombreAdmin(TextField textNombreAdmin) {
		this.textNombreAdmin = textNombreAdmin;
	}

	public TextField getTextApellido1Admin() {
		return textApellido1Admin;
	}

	public void setTextApellido1Admin(TextField textApellido1Admin) {
		this.textApellido1Admin = textApellido1Admin;
	}

	public TextField getTextApellido2Admin() {
		return textApellido2Admin;
	}

	public void setTextApellido2Admin(TextField textApellido2Admin) {
		this.textApellido2Admin = textApellido2Admin;
	}

	public TextField getTextEmailAdmin() {
		return textEmailAdmin;
	}

	public void setTextEmailAdmin(TextField textEmailAdmin) {
		this.textEmailAdmin = textEmailAdmin;
	}

	public TextField getTextDniAdmin() {
		return textDniAdmin;
	}

	public void setTextDniAdmin(TextField textDniAdmin) {
		this.textDniAdmin = textDniAdmin;
	}

	public TextField getTextContraseniaAdmin() {
		return textContraseniaAdmin;
	}

	public void setTextContraseniaAdmin(TextField textContraseniaAdmin) {
		this.textContraseniaAdmin = textContraseniaAdmin;
	}

	public DatePicker getDateFechaAdmin() {
		return DateFechaAdmin;
	}

	public void setDateFechaAdmin(DatePicker dateFechaAdmin) {
		DateFechaAdmin = dateFechaAdmin;
	}

	public JFXButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JFXButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

}
