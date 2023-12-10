package Controlador;

import java.io.IOException;
import java.util.Vector;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXToolbar;
import Modelo.Alerta;
import Modelo.Cliente;
import Modelo.Guardia;
import Modelo.Registro;
import Modelo.Reserva;
import Modelo.Staff;
import Modelo.modelo_Museo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * En esta clase se manejan las funcionalidades del administrador, en la vista <b>VentanaAdministrador</b>.
 *
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class ControladorAdministrador {

    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private VBox VBoxPrincipal;

    @FXML
    private JFXToolbar toolBarAdministrador;

    @FXML
    private ImageView imgCerrarSesion;

    @FXML
    private ImageView imgAvatarAdmin;

    @FXML
    private ImageView imgNotiAdmin;

    @FXML
    private ImageView imgInformes;

    @FXML
    private TableView<Cliente> tableClientes;

    @FXML
    private TableColumn<Cliente, String> colUsuario;

    @FXML
    private TableColumn<Cliente, String> colDni;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, String> colContrasenia;

    @FXML
    private TableColumn<Cliente, String> colFecha;

    @FXML
    private ImageView imgEditarGuardias;

    @FXML
    private ImageView imgEditarEventos;

    @FXML
    private ImageView imgEditarAdministrador;

    @FXML
    private ImageView imgHistorico;

    @FXML
    private JFXToolbar ToolBarSensores;

    @FXML
    private Text textSensores;

    @FXML
    private ImageView imgSala1;

    @FXML
    private ImageView imgSala2;

    @FXML
    private ImageView imgSala4;

    @FXML
    private ImageView imgSala3;

    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada

    boolean logged; //Este nos dira si la parsona esta logueada o no

    Alerta alert = new Alerta(usuario, "Administrador", this);

    public void initialize() {




		Registro datos = new Registro();
		Vector<Cliente> clientes = datos.recuperarClientes();

		// Se leen los datos del Json del staff
		for (int i = 0; i < clientes.size(); i++) {
			// Obtiene solo el personal con numero de identificacion 2


				// Se muestran los guardias obtenidos en la tabla
				tableClientes.getItems()
						.add(new Cliente (clientes.get(i).getUsuario(), clientes.get(i).getDni(), clientes.get(i).getEmail(),
								clientes.get(i).getContrasenia(), clientes.get(i).getFechaNacimiento()));

		}

		// Obtenemos el las diferentes columnas de la tabla y asociamos cada columna al
		// tipo de dato que queremos guardar
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colContrasenia.setCellValueFactory(new PropertyValueFactory<>("contrasenia"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));


		alert.getDatos();

	}

	 /**
	  *
	  * Constructor de la clase <b>ControladorAdministrador</b> que guarda la información del administrador.
	  *
	  * @param usuario		El administrador que se encuentre iniciado sesión.
	  */
	 public ControladorAdministrador(String usuario) {
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
     * Muestra todos los informes enviados, y ofrece la posibilidad de crear nuevos informes.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de los informes.
     */
    void accederInformes(MouseEvent event) {
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaInformeAdmin.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias

        ControladorInformeAdmin controlerSala1 = new ControladorInformeAdmin(usuario, alert);
        loaderSala1.setController(controlerSala1);
        AnchorPane PaneSala1;

		try {
			//Se carga en un AnchorPane la ventana
			PaneSala1 = (AnchorPane) loaderSala1.load();

			//Se elimina el contenido de la ventana padre
			anchorPanePrincipal.getChildren().clear();

        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PaneSala1, 0.0);
            AnchorPane.setRightAnchor(PaneSala1, 0.0);
            AnchorPane.setLeftAnchor(PaneSala1, 0.0);
            AnchorPane.setBottomAnchor(PaneSala1, 0.0);


            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PaneSala1);



		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }

    @FXML
    void abrirHistoricoSensores(MouseEvent event) {
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaHistorico.fxml"));
    	//Se le asigna el controlador de la ventana para editar información de los guardias
        ControladorHistorico controlerHistorico = new ControladorHistorico(usuario, "Administrador", alert);
        loaderPrincipala.setController(controlerHistorico);
        AnchorPane PaneVentanaPrincipal;

		try {
			//Se carga en un AnchorPane la ventana
			PaneVentanaPrincipal = (AnchorPane) loaderPrincipala.load();

			//Se elimina el contenido de la ventana padre
			anchorPanePrincipal.getChildren().clear();

        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
            AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
            AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
            AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);

            controlerHistorico.getToolBarAdmin().setStyle("-fx-background-color:  #FF8000");

            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);



		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    /**
     *
     * Muestra la información del administrador que se encuentre iniciado sesión.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de su avatar.
     */
    void accederPerfil(MouseEvent event) {
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaPerfil.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorPerfil controlerPrincipal = new ControladorPerfil(usuario, alert);
        loaderPrincipala.setController(controlerPrincipal);
        AnchorPane PaneVentanaPrincipal;

		try {
			//Se carga en un AnchorPane la ventana
			PaneVentanaPrincipal = (AnchorPane) loaderPrincipala.load();

			//Se elimina el contenido de la ventana padre
			anchorPanePrincipal.getChildren().clear();

        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
            AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
            AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
            AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);


            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);

            //Cambia el color de la barra de la ventana perfil
            controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");


		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }

    @FXML
    /**
     *
     * Devuelve al administrador a la ventana principal habiendo cerrado su sesión.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de cerrar sesión.
     */
    void cerrarSesion(MouseEvent event) {

    	alert.getTimer_alert().cancel();

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
    	this.usuario = "vacio";
    	this.logged = false;
        ControladorVPrincipal controlerPrincipal = new ControladorVPrincipal(usuario);
        loaderPrincipal.setController(controlerPrincipal);

        try {
        	AnchorPane registerPane = (AnchorPane) loaderPrincipal.load();
        	anchorPanePrincipal.getChildren().clear();
            AnchorPane.setTopAnchor(registerPane, 0.0);
            AnchorPane.setRightAnchor(registerPane, 0.0);
            AnchorPane.setLeftAnchor(registerPane, 0.0);
            AnchorPane.setBottomAnchor(registerPane, 0.0);
            anchorPanePrincipal.getChildren().setAll(registerPane);

		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    /**
     *
     * Muestra la ventana para editar eventos, como su t�tulo, contenido e imagen.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen para editar eventos.
     */
    void editarEventos(MouseEvent event) {
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/application/VentanaEditarEventos.fxml"));
        controladorEditarEventos controlerEditEvents = new controladorEditarEventos(usuario, alert);
        loaderEdit.setController(controlerEditEvents);

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

    @FXML
    /**
     *
     * Muestra todos los guardias y ofrece la posibilidad de editar su información o registrar uno nuevo.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen para editar guardias.
     */
    void editarGuardias(MouseEvent event) {
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/application/VentanaEditGuardias.fxml"));
        ControladorEditGuardias controlerEdit = new ControladorEditGuardias(usuario, alert);
        loaderEdit.setController(controlerEdit);

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

    @FXML
    void mostrarInfoSensores(MouseEvent event) {

    }

    @FXML

    /**
     *
     * Muestra la ventana de la Sala 1, mostrando la información de los diferentes sensores.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de la primera sala.
     */
    void sala1(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 1, "Administrador", alert);
            loaderSala1.setController(controlerSala1);
            AnchorPane PaneSala1;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneSala1 = (AnchorPane) loaderSala1.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneSala1, 0.0);
                AnchorPane.setRightAnchor(PaneSala1, 0.0);
                AnchorPane.setLeftAnchor(PaneSala1, 0.0);
                AnchorPane.setBottomAnchor(PaneSala1, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneSala1);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

    }

    @FXML
    /**
     *
     * Muestra la ventana de la Sala 2, mostrando la información de los diferentes sensores.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de la segunda sala.
     */
    void sala2(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 2, "Administrador", alert);
            loaderSala1.setController(controlerSala1);
            AnchorPane PaneSala1;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneSala1 = (AnchorPane) loaderSala1.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneSala1, 0.0);
                AnchorPane.setRightAnchor(PaneSala1, 0.0);
                AnchorPane.setLeftAnchor(PaneSala1, 0.0);
                AnchorPane.setBottomAnchor(PaneSala1, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneSala1);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

    }

    @FXML
    /**
     *
     * Muestra la ventana de la Sala 3, mostrando la información de los diferentes sensores.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de la tercera sala.
     */
    void sala3(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informaci�n de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 3, "Administrador", alert);
            loaderSala1.setController(controlerSala1);
            AnchorPane PaneSala1;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneSala1 = (AnchorPane) loaderSala1.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneSala1, 0.0);
                AnchorPane.setRightAnchor(PaneSala1, 0.0);
                AnchorPane.setLeftAnchor(PaneSala1, 0.0);
                AnchorPane.setBottomAnchor(PaneSala1, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneSala1);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    }

    @FXML
    /**
     *
     * Muestra la ventana de la Sala 4, mostrando la información de los diferentes sensores.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de la cuarta sala.
     */
    void sala4(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 4, "Administrador", alert);
            loaderSala1.setController(controlerSala1);
            AnchorPane PaneSala1;

    		try {
    			//Se carga en un AnchorPane la ventana
    			PaneSala1 = (AnchorPane) loaderSala1.load();

    			//Se elimina el contenido de la ventana padre
    			anchorPanePrincipal.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneSala1, 0.0);
                AnchorPane.setRightAnchor(PaneSala1, 0.0);
                AnchorPane.setLeftAnchor(PaneSala1, 0.0);
                AnchorPane.setBottomAnchor(PaneSala1, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneSala1);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    }

    @FXML
    /**
     *
     * Muestra una lista de los administradores, ofreciendo la posibilidad de editar su información o de registrar uno nuevo.
     *
     * @param event		Evento causado cuando el administrador pulsa sobre la imagen de edicción de un administrador.
     */
    void editarInfoAdministrador(MouseEvent event) {

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/application/VentanaEditarAdministrador.fxml"));
        ControladorEditarAdministrador controlerEdit = new ControladorEditarAdministrador(usuario, alert);
        loaderEdit.setController(controlerEdit);

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

    @FXML
    void mostrarNotificaciones(MouseEvent event) {

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderNotificaciones = new FXMLLoader(getClass().getResource("/application/PopOverNotificaciones.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias

        ControladorPopOverNotificacion controlerNoti = new ControladorPopOverNotificacion(usuario, "Administrador", this, alert);
        loaderNotificaciones.setController(controlerNoti);
        AnchorPane PopOverNoti;

		try {
			//Se carga en un AnchorPane la ventana
			PopOverNoti = (AnchorPane) loaderNotificaciones.load();

        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PopOverNoti, 0.0);
            AnchorPane.setRightAnchor(PopOverNoti, 0.0);
            AnchorPane.setLeftAnchor(PopOverNoti, 0.0);
            AnchorPane.setBottomAnchor(PopOverNoti, 0.0);

            PopOver popOver = new PopOver(PopOverNoti);
            popOver.show(imgNotiAdmin);



		} catch (IOException e1) {
			e1.printStackTrace();
		}


    }


	public ImageView getImgNotiAdmin() {
		return imgNotiAdmin;
	}

	public void setImgNotiAdmin(ImageView imgNotiAdmin) {
		this.imgNotiAdmin = imgNotiAdmin;
	}

	public JFXToolbar getToolBarAdministrador() {
		return toolBarAdministrador;
	}

	public void setToolBarAdministrador(JFXToolbar toolBarAdministrador) {
		this.toolBarAdministrador = toolBarAdministrador;
	}


}
