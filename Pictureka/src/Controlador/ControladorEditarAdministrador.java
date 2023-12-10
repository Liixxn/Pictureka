package Controlador;

import java.io.IOException;
import java.util.Vector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToolbar;
import Modelo.Administrador;
import Modelo.Alerta;
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
 * En esta clase se maneja la edicción de un administrador, en la vista
 * <b>VentanaEditarAdministrador</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class ControladorEditarAdministrador {

	@FXML
	private AnchorPane anchorPaneEditAdmin;

	@FXML
	private VBox VBoxEditAdmin;

	@FXML
	private JFXToolbar ToolBarAdmin;

	@FXML
	private ImageView imgAdministrador;

	@FXML
	private ImageView imgCerrarSesion;

	@FXML
	private GridPane gridPaneMaster;

	@FXML
	private TableView<Administrador> tableViewAdministrador;

	@FXML
	private TableColumn<Administrador, String> Usuario;

	@FXML
	private TableColumn<Administrador, String> Nombre;

	@FXML
	private TableColumn<Administrador, String> PrimerApellido;

	@FXML
	private TableColumn<Administrador, String> SegundoApellido;

	@FXML
	private TableColumn<Administrador, String> Email;

	@FXML
	private TableColumn<Administrador, String> DNI;

	@FXML
	private TableColumn<Administrador, String> FechaNacimiento;

	@FXML
	private TableColumn<Administrador, String> Contrasenia;

	@FXML
	private JFXTabPane TabPaneAdministrador;

	@FXML
	private Tab TabAniadirAdministrador;

	@FXML
	private AnchorPane AnchorTabAniadir;

	@FXML
	private Tab TabEditAdministrador;

	@FXML
	private AnchorPane AnchorEditAdmin;

	@FXML
	private GridPane gridPaneEdit;

	@FXML
	private JFXButton btnCancelar;

	@FXML
    private ImageView btnGuardarCambios;

	private String usuario; // esta el usuario o mail del usuario que tiene la sesion iniciada

	boolean logged; // Este nos dira si la parsona esta logueada o no

	ControladorTabEditarAdmin controlerTabEdit;
	
	Alerta alertaNotiAdmin;

	/**
	 * 
	 * Constructor de la clase <b>ControladorEditarAdministrador</b> que guarda la
	 * informacion de un administrador.
	 * 
	 * @param usuario El administrador que se encuentra iniciado sesión.
	 */
	public ControladorEditarAdministrador(String usuario, Alerta alerta) {
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
	 * Inicializa el contenido de la tabla con la información de los
	 * administradores.
	 * 
	 */
	public void initialize() {

		actualizarTablaAdmins();

	}

	@FXML
	/**
	 * 
	 * Muestra los diferentes campos que se necesitan para registrar un nuevo
	 * administrador.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre el Tab
	 *              "Añadir Admin".
	 */
	void AniadirAdministrador(Event event) {

		// Se carga la segunda ventana del TabPane
		FXMLLoader loaderTabAniadir = new FXMLLoader(
				getClass().getResource("/application/TabAniadirAdministrador.fxml"));
		// Se le asigna el controlador de la ventana para editar informacion de los
		// guardias
		ControladorTabAniadirAdmin controlerTabAniadir = new ControladorTabAniadirAdmin(this);
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
	 * Muestra la información del administrador seleccionado y permite modificarlos.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre el Tab
	 *              "Editar Admin".
	 */
	void EditarAdministrador(Event event) {

		FXMLLoader loaderTabEditar = new FXMLLoader(getClass().getResource("/application/TabEditarAdministrador.fxml"));
		ControladorTabEditarAdmin controlerTabEdit = new ControladorTabEditarAdmin(this);
		loaderTabEditar.setController(controlerTabEdit);
		AnchorPane PaneEditAdmin;

		try {
			PaneEditAdmin = (AnchorPane) loaderTabEditar.load();
			AnchorEditAdmin.getChildren().clear();
			AnchorPane.setTopAnchor(PaneEditAdmin, 0.0);
			AnchorPane.setRightAnchor(PaneEditAdmin, 0.0);
			AnchorPane.setLeftAnchor(PaneEditAdmin, 0.0);
			AnchorPane.setBottomAnchor(PaneEditAdmin, 0.0);
			AnchorEditAdmin.getChildren().setAll(PaneEditAdmin);
			this.controlerTabEdit = controlerTabEdit;

		} catch (IOException e) {
		
			e.printStackTrace();
		}

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
			anchorPaneEditAdmin.getChildren().clear();
			AnchorPane.setTopAnchor(PaneAdmin, 0.0);
			AnchorPane.setRightAnchor(PaneAdmin, 0.0);
			AnchorPane.setLeftAnchor(PaneAdmin, 0.0);
			AnchorPane.setBottomAnchor(PaneAdmin, 0.0);
			anchorPaneEditAdmin.getChildren().setAll(PaneAdmin);

		} catch (IOException e) {
			
			e.printStackTrace();
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

		actualizarTablaAdmins();
		
	}

	void actualizarTablaAdmins() {
		Registro registro = new Registro();
		Vector<Staff> staff = registro.recuperarStaff();
		tableViewAdministrador.getItems().clear();
		// Se leen los datos del Json del staff
		for (int i = 0; i < staff.size(); i++) {
			// Obtiene solo el personal con numero de identificacion 2
			if (staff.get(i).getIdentificadorUser() == 3) {

				// Se muestran los guardias obtenidos en la tabla
				tableViewAdministrador.getItems()
						.add(new Administrador(staff.get(i).getUsuario(), staff.get(i).getDni(),
								staff.get(i).getEmail(), staff.get(i).getContrasenia(),
								staff.get(i).getFechaNacimiento(), staff.get(i).getNombre(),
								staff.get(i).getApellido1(), staff.get(i).getApellido2()));
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
		AnchorPane registerPane;
		try {
			registerPane = (AnchorPane) loaderApp.load();
			anchorPaneEditAdmin.getChildren().clear();
			AnchorPane.setTopAnchor(registerPane, 0.0);
			AnchorPane.setRightAnchor(registerPane, 0.0);
			AnchorPane.setLeftAnchor(registerPane, 0.0);
			AnchorPane.setBottomAnchor(registerPane, 0.0);
			anchorPaneEditAdmin.getChildren().setAll(registerPane);

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * 
	 * Comprueba la selección selecccionada por el administrador.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre algún sitio
	 *              de la tabla.
	 */
	void clickAdministrador(MouseEvent event) {

		if (!tableViewAdministrador.getSelectionModel().isEmpty()) {
			EditarAdministrador(null);

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
			// Se le asigna el controlador de la ventana para editar informacion de los
			// guardias
			ControladorPerfil controlerPrincipal = new ControladorPerfil(usuario);
			loaderPrincipala.setController(controlerPrincipal);
			AnchorPane PaneVentanaPrincipal;

			try {
				// Se carga en un AnchorPane la ventana
				PaneVentanaPrincipal = (AnchorPane) loaderPrincipala.load();

				// Se elimina el contenido de la ventana padre
				anchorPaneEditAdmin.getChildren().clear();

				// Se ajusta el AnchorPane para que sea escalable
				AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);

				// Se añade el contenido de la ventana cargada en el AnchorPane del padre
				anchorPaneEditAdmin.getChildren().setAll(PaneVentanaPrincipal);
				controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FFD700");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
	
	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public AnchorPane getAnchorPaneEditAdmin() {
		return anchorPaneEditAdmin;
	}

	public void setAnchorPaneEditAdmin(AnchorPane anchorPaneEditAdmin) {
		this.anchorPaneEditAdmin = anchorPaneEditAdmin;
	}

	public ImageView getImgAdministrador() {
		return imgAdministrador;
	}

	public void setImgAdministrador(ImageView imgAdministrador) {
		this.imgAdministrador = imgAdministrador;
	}

	public ImageView getImgCerrarSesion() {
		return imgCerrarSesion;
	}

	public void setImgCerrarSesion(ImageView imgCerrarSesion) {
		this.imgCerrarSesion = imgCerrarSesion;
	}

	public GridPane getGridPaneMaster() {
		return gridPaneMaster;
	}

	public void setGridPaneMaster(GridPane gridPaneMaster) {
		this.gridPaneMaster = gridPaneMaster;
	}

	public TableView<Administrador> getTableViewAdministrador() {
		return tableViewAdministrador;
	}

	public void setTableViewAdministrador(TableView<Administrador> tableViewAdministrador) {
		this.tableViewAdministrador = tableViewAdministrador;
	}

	public JFXTabPane getTabPaneAdministrador() {
		return TabPaneAdministrador;
	}

	public void setTabPaneAdministrador(JFXTabPane tabPaneAdministrador) {
		TabPaneAdministrador = tabPaneAdministrador;
	}

	public Tab getTabAniadirAdministrador() {
		return TabAniadirAdministrador;
	}

	public void setTabAniadirAdministrador(Tab tabAniadirAdministrador) {
		TabAniadirAdministrador = tabAniadirAdministrador;
	}

	public AnchorPane getAnchorTabAniadir() {
		return AnchorTabAniadir;
	}

	public void setAnchorTabAniadir(AnchorPane anchorTabAniadir) {
		AnchorTabAniadir = anchorTabAniadir;
	}

	public Tab getTabEditAdministrador() {
		return TabEditAdministrador;
	}

	public void setTabEditAdministrador(Tab tabEditAdministrador) {
		TabEditAdministrador = tabEditAdministrador;
	}

	public AnchorPane getAnchorEditAdmin() {
		return AnchorEditAdmin;
	}

	public void setAnchorEditAdmin(AnchorPane anchorEditAdmin) {
		AnchorEditAdmin = anchorEditAdmin;
	}

	public GridPane getGridPaneEdit() {
		return gridPaneEdit;
	}

	public void setGridPaneEdit(GridPane gridPaneEdit) {
		this.gridPaneEdit = gridPaneEdit;
	}

	public JFXButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JFXButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	

}
