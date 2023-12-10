package Controlador;

import java.io.IOException;
import java.util.Vector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToolbar;
import Modelo.Alerta;
import Modelo.Guardia;
import Modelo.Registro;
import Modelo.Staff;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * 
 * En esta clase se manejan las funciones de edicción de los guardias, en la
 * vista <b>VentanaEditGuardias</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class ControladorEditGuardias {

	@FXML
	private AnchorPane anchorPaneEditGuardia;

	@FXML
	private VBox VBoxEditGuardia;

	@FXML
	private JFXToolbar ToolBarAdmin;

	@FXML
	private ImageView imgAdministrador;

	@FXML
	private ImageView imgCerrarSesion;

	@FXML
	private GridPane gridPaneMaster;

	@FXML
	private TableView<Guardia> tableView;

	@FXML
	private TableColumn<Guardia, String> Usuario;

	@FXML
	private TableColumn<Guardia, String> Nombre;

	@FXML
	private TableColumn<Guardia, String> PrimerApellido;

	@FXML
	private TableColumn<Guardia, String> SegundoApellido;

	@FXML
	private TableColumn<Guardia, String> Email;

	@FXML
	private TableColumn<Guardia, String> DNI;

	@FXML
	private TableColumn<Guardia, String> FechaNacimiento;

	@FXML
	private TableColumn<Guardia, String> Contrasenia;

	@FXML
	private JFXTabPane TabPaneGuardia;

	@FXML
	private Tab TabAniadirGuardia;

	@FXML
	private AnchorPane AnchorTabAniadir;

	@FXML
	private Tab TabEditGuardia;

	@FXML
	private AnchorPane AnchorEditGuardia;

	@FXML
	private GridPane gridPaneEdit;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private ImageView btnGuardarCambios;

	private String usuario; // esta el usuario o mail del usuario que tiene la sesion iniciada

	boolean logged; // Este nos dira si la parsona esta logueada o no

	ControladorTabEditarGuardia controlerTabEdit;
	
	Alerta alertaNotiAdmin;

	/**
	 * 
	 * Constructor de la clase <b>ControladorEditGuardias</b> que guarda la
	 * información del administrador.
	 * 
	 * @param usuario El administrador que se encuentra iniciado sesión.
	 */
	public ControladorEditGuardias(String usuario, Alerta alerta) {
		if (usuario == "vacio") {
			logged = false;
			this.usuario = usuario;
			this.alertaNotiAdmin = alerta;
		} else {
			this.usuario = usuario;
			this.alertaNotiAdmin = alerta;
			logged = true;
		}

	}

	@FXML
	/**
	 * 
	 * Inicializa la ventana cargando la información del Json de staff, en concreto
	 * los guardias en la tabla.
	 * 
	 */
	public void initialize() {
		actualizarTablaGuardias();
	}

	@FXML
	/**
	 * 
	 * Dirige al administrador a la ventana principal, habiendo cerrado su sesión.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre la imagen
	 *              para cerrar sesión.
	 */
	void cerrarSesion(MouseEvent event) {

		alertaNotiAdmin.getTimer_alert().cancel();
		
		FXMLLoader loaderApp = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
		ControladorVPrincipal controlerPrincipal = new ControladorVPrincipal("vacio");
		loaderApp.setController(controlerPrincipal);

		try {
			AnchorPane registerPane = (AnchorPane) loaderApp.load();
			anchorPaneEditGuardia.getChildren().clear();
			AnchorPane.setTopAnchor(registerPane, 0.0);
			AnchorPane.setRightAnchor(registerPane, 0.0);
			AnchorPane.setLeftAnchor(registerPane, 0.0);
			AnchorPane.setBottomAnchor(registerPane, 0.0);
			anchorPaneEditGuardia.getChildren().setAll(registerPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * 
	 * Muestra los diferentes campos que se necesitan para registrar un nuevo
	 * guardia.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre el Tab
	 *              "Añadir guardia".
	 */
	void AniadirGuardia(Event event) {

		// Se carga la segunda ventana del TabPane
		FXMLLoader loaderTabAniadir = new FXMLLoader(getClass().getResource("/application/TabAniadirGuardia.fxml"));
		// Se le asigna el controlador de la ventana para editar informacion de los
		// guardias
		ControladorTabAniadirGuardia controlerTabAniadir = new ControladorTabAniadirGuardia(this);
		loaderTabAniadir.setController(controlerTabAniadir);
		AnchorPane anchorTabAniadir;

		try {
			anchorTabAniadir = (AnchorPane) loaderTabAniadir.load();
			AnchorTabAniadir.getChildren().clear();
			AnchorPane.setBottomAnchor(anchorTabAniadir, 0.0);
			AnchorPane.setRightAnchor(anchorTabAniadir, 0.0);
			AnchorPane.setLeftAnchor(anchorTabAniadir, 0.0);
			AnchorPane.setBottomAnchor(anchorTabAniadir, 0.0);
			AnchorTabAniadir.getChildren().setAll(anchorTabAniadir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * 
	 * Muestra la información del guardia seleccionado y permite modificarlos.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre el Tab
	 *              "Editar Guardia".
	 */
	void EditarGuardia(Event event) {

		FXMLLoader loaderApp = new FXMLLoader(getClass().getResource("/application/TabEditarGuardia.fxml"));
		ControladorTabEditarGuardia controlerPrincipal = new ControladorTabEditarGuardia(this);
		loaderApp.setController(controlerPrincipal);
		AnchorPane registerPane;

		try {
			registerPane = (AnchorPane) loaderApp.load();
			AnchorEditGuardia.getChildren().clear();
			AnchorPane.setTopAnchor(registerPane, 0.0);
			AnchorPane.setRightAnchor(registerPane, 0.0);
			AnchorPane.setLeftAnchor(registerPane, 0.0);
			AnchorPane.setBottomAnchor(registerPane, 0.0);
			AnchorEditGuardia.getChildren().setAll(registerPane);
			this.controlerTabEdit = controlerPrincipal;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	/**
	 * 
	 * Muestra la información del administrador que está iniciado sesión.
	 * 
	 * @param event Evento causado cunado el administrador pulsa sobre la imagen de
	 *              su avatar.
	 */
	void verPerfil(MouseEvent event) {
		if (logged == false) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setHeaderText("Oh no! Para acceder a esta función debes estar iniciado sesión.");
			error.showAndWait();

		} else {
			// Se carga el contenido de la ventana
			FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaPerfil.fxml"));
			// Se le asigna el controlador de la ventana para editar informaci�n de los
			// guardias
			ControladorPerfil controlerPrincipal = new ControladorPerfil(usuario);
			loaderPrincipala.setController(controlerPrincipal);
			AnchorPane PaneVentanaPrincipal;

			try {
				// Se carga en un AnchorPane la ventana
				PaneVentanaPrincipal = (AnchorPane) loaderPrincipala.load();

				// Se elimina el contenido de la ventana padre
				anchorPaneEditGuardia.getChildren().clear();

				// Se ajusta el AnchorPane para que sea escalable
				AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);

				// Se añade el contenido de la ventana cargada en el AnchorPane del padre
				anchorPaneEditGuardia.getChildren().setAll(PaneVentanaPrincipal);
				// Cambia el color de la barra de la ventana perfil
				controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	@FXML
	/**
	 * 
	 * Refresca la tabla y muestra los datos actualizados del Json en la tabla.
	 * 
	 * @param event Evento causado cuando el administrador pulsa el botón
	 *              "Actualizar Cambios".
	 */
	void GuardarTodosCambios(MouseEvent event) {
		actualizarTablaGuardias();	
	}
	
	
	
	void actualizarTablaGuardias() {
		Registro registro = new Registro();
		Vector<Staff> staff = registro.recuperarStaff();
		tableView.getItems().clear();
		// Se leen los datos del Json del staff
		for (int i = 0; i < staff.size(); i++) {
			// Obtiene solo el personal con numero de identificacion 2
			if (staff.get(i).getIdentificadorUser() == 2) {

				// Se muestran los guardias obtenidos en la tabla
				tableView.getItems()
						.add(new Guardia(staff.get(i).getUsuario(), staff.get(i).getDni(), staff.get(i).getEmail(),
								staff.get(i).getContrasenia(), staff.get(i).getFechaNacimiento(),
								staff.get(i).getNombre(), staff.get(i).getApellido1(), staff.get(i).getApellido2()));
			}
		}

		// Obtenemos el las diferentes columnas de la tabla y asociamos cada columna al
		// tipo de dato que queremos guardar
		Usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		PrimerApellido.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
		SegundoApellido.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
		Email.setCellValueFactory(new PropertyValueFactory<>("email"));
		DNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
		FechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
		Contrasenia.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));

	}

	@FXML
	/**
	 * 
	 * Dirige al administrador a su ventana inicial.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre el botón de
	 *              "Cancelar".
	 */
	void CancelarEdiccion(ActionEvent event) {
		
		alertaNotiAdmin.getTimer_alert().cancel();

		FXMLLoader loaderAdmin = new FXMLLoader(getClass().getResource("/application/VentanaAdministrador.fxml"));
		ControladorAdministrador controlerAdmin = new ControladorAdministrador(usuario);
		loaderAdmin.setController(controlerAdmin);

		try {
			AnchorPane PaneAdmin = (AnchorPane) loaderAdmin.load();
			anchorPaneEditGuardia.getChildren().clear();
			AnchorPane.setTopAnchor(PaneAdmin, 0.0);
			AnchorPane.setRightAnchor(PaneAdmin, 0.0);
			AnchorPane.setLeftAnchor(PaneAdmin, 0.0);
			AnchorPane.setBottomAnchor(PaneAdmin, 0.0);
			anchorPaneEditGuardia.getChildren().setAll(PaneAdmin);

		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}

	@FXML
	/**
	 * 
	 * Comprueba la selección selecccionada por el administrador.
	 * 
	 * @param event		Evento causado cuando el administrador pulsa sobre algún sitio de la tabla.
	 */
	void clickGuardia(MouseEvent event) {

		if (!tableView.getSelectionModel().isEmpty()) {
			EditarGuardia(null);

		}
	}

	public AnchorPane getAnchorPaneEditGuardia() {
		return anchorPaneEditGuardia;
	}

	public void setAnchorPaneEditGuardia(AnchorPane anchorPaneEditGuardia) {
		this.anchorPaneEditGuardia = anchorPaneEditGuardia;
	}

	public TableView<Guardia> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<Guardia> tableView) {
		this.tableView = tableView;
	}

	public TableColumn<Guardia, String> getUsuario() {
		return Usuario;
	}

	public void setUsuario(TableColumn<Guardia, String> usuario) {
		Usuario = usuario;
	}

	public TableColumn<Guardia, String> getNombre() {
		return Nombre;
	}

	public void setNombre(TableColumn<Guardia, String> nombre) {
		Nombre = nombre;
	}

	public TableColumn<Guardia, String> getPrimerApellido() {
		return PrimerApellido;
	}

	public void setPrimerApellido(TableColumn<Guardia, String> primerApellido) {
		PrimerApellido = primerApellido;
	}

	public TableColumn<Guardia, String> getSegundoApellido() {
		return SegundoApellido;
	}

	public void setSegundoApellido(TableColumn<Guardia, String> segundoApellido) {
		SegundoApellido = segundoApellido;
	}

	public TableColumn<Guardia, String> getEmail() {
		return Email;
	}

	public void setEmail(TableColumn<Guardia, String> email) {
		Email = email;
	}

	public TableColumn<Guardia, String> getDNI() {
		return DNI;
	}

	public void setDNI(TableColumn<Guardia, String> dNI) {
		DNI = dNI;
	}

	public TableColumn<Guardia, String> getFechaNacimiento() {
		return FechaNacimiento;
	}

	public void setFechaNacimiento(TableColumn<Guardia, String> fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}

	public TableColumn<Guardia, String> getContrasenia() {
		return Contrasenia;
	}

	public void setContrasenia(TableColumn<Guardia, String> contrasenia) {
		Contrasenia = contrasenia;
	}

	public Tab getTabAniadirGuardia() {
		return TabAniadirGuardia;
	}

	public void setTabAniadirGuardia(Tab tabAniadirGuardia) {
		TabAniadirGuardia = tabAniadirGuardia;
	}

	public Tab getTabEditGuardia() {
		return TabEditGuardia;
	}

	public void setTabEditGuardia(Tab tabEditGuardia) {
		TabEditGuardia = tabEditGuardia;
	}

	public AnchorPane getAnchorTabAniadir() {
		return AnchorTabAniadir;
	}

	public void setAnchorTabAniadir(AnchorPane anchorTabAniadir) {
		AnchorTabAniadir = anchorTabAniadir;
	}

	public AnchorPane getAnchorEditGuardia() {
		return AnchorEditGuardia;
	}

	public void setAnchorEditGuardia(AnchorPane anchorEditGuardia) {
		AnchorEditGuardia = anchorEditGuardia;
	}

}
