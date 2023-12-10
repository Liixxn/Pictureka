package Controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import com.jfoenix.controls.JFXToolbar;
import Modelo.Alerta;
import Modelo.Evento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;

/**
 *
 * En esta clase se maneja los cambios que se realizan a la hora de editar un evento, en la vista <b>VentanaEditarEventos</b>.
 *
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class controladorEditarEventos {

	@FXML
	private AnchorPane anchorPanePrincipal;

	@FXML
	private BorderPane borderPaneEditEventos;

	@FXML
	private JFXToolbar toolBarAdministrador;

	@FXML
	private ImageView btnVolverAtras;

	@FXML
	private ImageView imgAvatarAdmin;

	@FXML
	private ComboBox<String> comboBoxElegirEvento;

	@FXML
    private TextArea txtAreaInfo;


	@FXML
	private Region imgAniadirImagen;

	@FXML
	private Button btnAniadirCambios;

	@FXML
	private TextField txtFieldTitulo;

	private String usuario; // esta el usuario o mail del usuario que tiene la sesion iniciada

	boolean logged; // Este nos dira si la parsona esta logueada o no

	String directoryName = System.getProperty("user.dir");

	Alerta alertaNotiAdmin;




	//cosas BBDD
		static final String USER = "pri_Pictureka";
	    static final String PASS = "asas";
	    Connection conn = null;
	    Statement stmt = null;
	    String sql;



	/**
	 *
	 *  Constructor de la clase <b>controladorEditarEventos</b>, que guarda la información de un adminsitrador.
	 *
	 * @param usuario	El administrador que se encuentre iniciado sesión.
	 */
	public controladorEditarEventos(String usuario, Alerta alerta) {
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

	public controladorEditarEventos() {


		}



	private Path pathImagen;

	private Path pathSRC; // Creamos un objeto Path para guardar el path del src



	/**
	 *
	 * Inicializa la ventana de ediatar eventos con su respectiva informaci�n.
	 *
	 */
	public void initialize() {

		Vector<String> nombres = asignarNombreAEventos(); 	//recorre los eveentos y devuelve el nombre de cada posicion de los eventos
		ObservableList<String> list = FXCollections.observableArrayList("1 - " + nombres.elementAt(0), "2 - " + nombres.elementAt(1), "3 - " + nombres.elementAt(2), "4 - " + nombres.elementAt(3));
		comboBoxElegirEvento.setItems(list);
	}


	String imagen; // Almacena el nombre (ubicacion de la imagen) que se añadir al evento

	@FXML
	/**
	 *
	 * Dirige al administrador a la ventana perfil para visualizar su informacion.
	 *
	 * @param event		Evento causado cuando el administrador pulsa sobre la imagen de su avatar.
	 */
	void accederPerfilAdmin(MouseEvent event) {
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
				anchorPanePrincipal.getChildren().clear();

				// Se ajusta el AnchorPane para que sea escalable
				AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
				AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);

				// Se añade el contenido de la ventana cargada en el AnchorPane del padre
				anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);
				// Cambia el color de la barra de la ventana perfil
				controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}



	@FXML
    void cambiarInfo(ActionEvent event) {
		if(comboBoxElegirEvento.getValue() != null) {
		String[] datosSeleccion = comboBoxElegirEvento.getValue().split(" - ");	//recupera la seleccion
		int seleccion = Integer.parseInt(datosSeleccion[0]);		//asigna la parte de la seleccion que tiene el numero y omite el nombre

		try {
			Class.forName("org.mariadb.jdbc.Driver");
            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);

            sql = "SELECT * FROM EVENTOS WHERE EVENTOS.identificador = '"+seleccion+"'";
            stmt = conn.createStatement();
   			ResultSet rs = stmt.executeQuery( sql );
   			while(rs.next()) {
   			txtFieldTitulo.setText(rs.getString("nombre"));
			txtAreaInfo.setText(rs.getString("informacion"));
			imagen=rs.getString("imagen");
			Image image = new Image("file:"+directoryName+"\\Imagenes_Multimedia\\"+imagen);
			imgAniadirImagen.setBackground(
					new Background(new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY)));
   			}


   			stmt.close();
   			rs.close();
   		}
		catch(SQLException | ClassNotFoundException e){
			System.err.println(e.getMessage());
		}

		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setHeaderText("Porfavor elige el evento que desees editar.");
		}
    }


	@FXML
	/**
	 *
	 * A�ade los cambios realizados por el administrador a la ventana de eventos.
	 *
	 * @param event		Evento causado cuando el administrador pulsa sobre el botón "Añadir Cambios".
	 */
	void aniadirCambios(MouseEvent event) {
		Alert error = new Alert(Alert.AlertType.ERROR);
		Alert aviso = new Alert(Alert.AlertType.CONFIRMATION);

		//Se comrpueba que la seleccion del comboBox no sea nula
		if (comboBoxElegirEvento.getValue() != null) {
			if (txtAreaInfo.getLength() >= 10 && txtAreaInfo.getText() != null) {
				if (imagen != null) {
					if (txtFieldTitulo.getLength() >= 10 && txtFieldTitulo.getText() != null) {

						// Si todo sale bien la imagen se guarda en el src
						try {
							if(pathImagen != null && pathSRC != null) {
								Files.copy(pathImagen, pathSRC);
							}


							// Se guardan los datos en la BBDD
							guardarDatos();

						} catch (IOException e) {

							e.printStackTrace();
						}



						aviso.setTitle("Exito.");
						aviso.setHeaderText("Evento cambiado exitosamente.");
						aviso.showAndWait();
						abrirAdmin();
					} else {
						error.setTitle("Datos incompletos.");
						error.setHeaderText("Porfavor ingrese un titulo adecuado para el evento.");
						error.show();
					}

				} else {
					error.setTitle("Datos incompletos.");
					error.setHeaderText("Porfavor ingrese una Imagen para añadir al evento.");
					error.show();
				}

			} else {
				error.setTitle("Datos incompletos.");
				error.setHeaderText("Porfavor ingrese la informacion correspondiente al evento.");
				error.show();
			}

		} else {
			error.setTitle("Datos incompletos.");
			error.setHeaderText("Porfavor ingrese el evento que se reemplazará.");
			error.show();
		}

	}

	@FXML
	/**
	 *
	 * Abre el explorador de archivos del administrador, para poder elegir la imagen que desea añadir a la ventana de eventos.
	 *
	 * @param event		Evento causado cuando el administrador pulsa sobre la región.
	 */
	void aniadirImagen(MouseEvent event) {

		//Abre el explorador de archivos
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Buscar Imagen");

		// Agregar filtros para facilitar la busqueda
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
		// Obtener la imagen seleccionada
		File imgFile = fileChooser.showOpenDialog(null);

		// Mostar la imagen
		if (imgFile != null) {
			Image image = new Image("file:" + imgFile.getAbsolutePath());
			imgAniadirImagen.setBackground(
					new Background(new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY)));
			// imagen = imgFile.getName();
			// para guardar el path de la imagen seleccionada
			pathImagen = imgFile.toPath();
			// Se almacena el path al que ira dirigido la imagen con el nombre que ya tenia
			// la imagen seleccionada
			String directoryName = System.getProperty("user.dir");
			String pathConcatenar = "\\Imagenes_Multimedia\\";
			pathSRC = Paths.get(directoryName + pathConcatenar + imgFile.getName());
			imagen = imgFile.getName();

		}

	}

	@FXML
	/**
	 *
	 * Devuelve al administrador a su ventana inciial.
	 *
	 * @param event		Evento causado cuando el administrador pulsa sobre la imagen de volver atrás.
	 */
	void regresarPrincipalAdmin(MouseEvent event) {

		alertaNotiAdmin.getTimer_alert().cancel();
		abrirAdmin();

	}

	/**
	 *
	 * Muestra la ventana inicial del administrador.
	 *
	 */
	void abrirAdmin() {
		FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/application/VentanaAdministrador.fxml"));
		ControladorAdministrador controlerAdmin = new ControladorAdministrador(usuario);
		loaderEdit.setController(controlerAdmin);

		try {
			AnchorPane PaneEdit = (AnchorPane) loaderEdit.load();
			anchorPanePrincipal.getChildren().clear();
			AnchorPane.setTopAnchor(PaneEdit, 0.0);
			AnchorPane.setRightAnchor(PaneEdit, 0.0);
			AnchorPane.setLeftAnchor(PaneEdit, 0.0);
			AnchorPane.setBottomAnchor(PaneEdit, 0.0);
			anchorPanePrincipal.getChildren().setAll(PaneEdit);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private Vector<String> asignarNombreAEventos() {
		Vector<String> nombres = new Vector<String>(4);

		try {
			Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);

            sql = "SELECT * FROM EVENTOS";
            stmt = conn.createStatement();
   			ResultSet rs = stmt.executeQuery( sql );
   			while ( rs.next() ) {
				nombres.add(rs.getString("nombre"));
			}

   			stmt.close();
   			rs.close();
   		}
		catch(SQLException | ClassNotFoundException e){

		}



		return nombres;
	}

	public void guardarDatos() {// Este metodo guardara los cambios en el Json de eventos con la informacion que
								// haya solicitado
		String[] datosSeleccion = comboBoxElegirEvento.getValue().split(" - ");	//recupera la seleccion
		int seleccion = Integer.parseInt(datosSeleccion[0]);		//asigna la parte de la seleccion que tiene el numero y omite el nombre


		try {
			Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            sql = "UPDATE EVENTOS SET "
            		+ "nombre = '"+txtFieldTitulo.getText()+"', "
            		+ "imagen = '"+ imagen +"', "
            		+ "informacion = '"+txtAreaInfo.getText() +"'"
            				+ "WHERE "
            				+ "EVENTOS.identificador = '"+seleccion+"';";

            String sqlDos = "INSERT INTO MODIFICA(Usuario, identificadorEvento) VALUES ('"+ usuario+"' , '"+seleccion+"');";
            stmt = conn.createStatement();
   			ResultSet rs = stmt.executeQuery( sql );

   			rs = stmt.executeQuery(sqlDos);
   			stmt.close();
   			rs.close();
   		}
		catch(SQLException | ClassNotFoundException e){

		}


	}

}
