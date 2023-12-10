package Controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;
import com.jfoenix.controls.JFXTextArea;
import Modelo.Cliente;
import Modelo.M_Reservas;
import Modelo.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * En esta clase se maneja la edicción de una reserva, en la vista <b>VentanaEditarTickets</b>.
 *
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class ControladorEditarReserva {

    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private VBox VBoxPrincipal;

    @FXML
    private ImageView imgUsuario;

    @FXML
    private ImageView imgVolverAtras;

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TableColumn<Reserva, Integer> Ident;

    @FXML
    private TableColumn<Reserva, Integer> cantidad;

    @FXML
    private TableColumn<Reserva, LocalDate> fecha;

    @FXML
    private TableColumn<Reserva, LocalTime> hora;

    @FXML
    private JFXTextArea mostrarReservas;

    @FXML
    private Button btnEliminar;

    boolean logged;

    private String usuario;

    private String infoReserva;

    private Vector<Reserva> reservasEspecificas = new Vector<Reserva>();

    /**
     *
     * Inicializa la ventana con la información requerida.
     *
     */
    public void initialize() {
    	getReserUser();
		refrescarTabla();


	}

    /**
     *
     * Constructor de la clase <b>ControladorEditarReserva</b> que guarda la información de un cliente.
     *
     * @param usuario		El cliente que se encuentre iniciado sesión.
     */
    public ControladorEditarReserva(String usuario) {
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
     * Recoge la selección del cliente de su lista de reservas.
     *
     * @param event		Evento causado cuando el cliente pulsa sobre la tabla.
     */
    void clickItem(MouseEvent event) {
    	if (!tablaReservas.getSelectionModel().isEmpty()) {
			int posicion = tablaReservas.getSelectionModel().getSelectedIndex();
			mostrarReservas(-posicion+reservasEspecificas.size()-1);
		}
    }

    @FXML
    /**
     *
     * Elimina la reserva seleccionada por el cliente.
     *
     * @param event		Evento causado cuando el cliente pulsa sobre el botón "Eliminar".
     */
    void eliminarReserva(ActionEvent event) {

    	M_Reservas reservas = new M_Reservas();

    	Alert alerta = new Alert(AlertType.INFORMATION);
    	//Se comrpueba que la seleccion no este vacia
    	if(tablaReservas.getSelectionModel().getSelectedItem()!=null) {
    		int idEliminar = tablaReservas.getSelectionModel().getSelectedItem().getIdentificador();
    		reservas.eliminarReserva(idEliminar);
    		//obtiene las reservas de dicho usuario
    		getReserUser();
    		//muestra las reservas sin la reserva que se "elimino"
        	refrescarTabla();

        	alerta.setHeaderText("La reserva se ha eliminado con éxito!");
    		alerta.showAndWait();
    	}
    	else {
    		alerta.setHeaderText("¡Porfavor seleccione alguna reserva para eliminar!");
    		alerta.showAndWait();
    	}

    }


    @FXML
    /**
     *
     * Muestra la información de un cliente.
     *
     * @param event		Evento causado cuando el cliente pulsa sobre la imagen de su avatar.
     */
    void accederPerfil(MouseEvent event) {
    	if(logged == false) {
        	Alert error = new Alert(Alert.AlertType.ERROR);
			error.setHeaderText("Oh no! Para acceder a esta función debes estar iniciado sesión.");
    		error.showAndWait();

        }
        else {
        	//Se carga el contenido de la ventana
        	FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaPerfil.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias
            ControladorPerfil controlerPrincipal = new ControladorPerfil(usuario);
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
                controlerPrincipal.getBarra().setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");


    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }
    }

    @FXML
    /**
     *
     * Devuelve al cliente a la ventana de su perfil.
     *
     * @param event		Evento causado cuando el cliente pulsa sobre la imagen para volver atrás.
     */
    void volverAtras(MouseEvent event) {

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaPerfil.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorPerfil controlerPerfil = new ControladorPerfil(usuario);
        loaderPrincipal.setController(controlerPerfil);
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
	        controlerPerfil.getBarra().setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");


		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }

    /**
     *
     * Muestra la información de la reserva seleccionada por el usuario.
     *
     * @param posicion		Número que guarda la posición de los elementos de la tabla.
     */
    private void mostrarReservas(int posicion) {

    	this.infoReserva = "\t\t\t\tReserva Seleccionada:"
    					+"\nIdentificador:  "+reservasEspecificas.elementAt(posicion).getIdentificador()
		    			+ "\nNumero de tickets:  "+ reservasEspecificas.elementAt(posicion).getNumTickets()
		    			+ "\nFecha:  "+ reservasEspecificas.elementAt(posicion).getFecha()
		    			+ "\nHora: "+reservasEspecificas.elementAt(posicion).getHora();
    			mostrarReservas.setText(infoReserva);
	}

    /**
     *
     * Obtiene la reserva del cliente.
     *
     */
    private void getReserUser() {
    	//Obtener los datos de la reserva
    	M_Reservas reservas = new M_Reservas ();
    	reservasEspecificas = reservas.buscarReserva(usuario);
    }

    /**
     *
     * Refresca la información de la tabla, tras haber realizado cambios.
     *
     */
    private void refrescarTabla() {

		tablaReservas.getItems().clear();
		//Si la persona tiene reservas, entonces las muestra en la tabla
		if(reservasEspecificas.size() > 0) {
			for (int i=(reservasEspecificas.size()-1); i >= 0; i--) {
				tablaReservas.getItems().add(reservasEspecificas.elementAt(i)); //Y se aniaden a la tabla
			}
			//Obtenemos el las diferentes columnas de la tabla y asociamos cada columna al tipo de dato que queremos guardar
			Ident.setCellValueFactory(new PropertyValueFactory<>("identificador"));
			cantidad.setCellValueFactory(new PropertyValueFactory<>("numTickets"));
			fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
			hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
			mostrarReservas(reservasEspecificas.size()-1);

		}
	}

}
