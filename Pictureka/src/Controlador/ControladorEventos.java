package Controlador;

import java.io.IOException;
import java.util.ArrayList;
import org.controlsfx.control.PopOver;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import Modelo.*;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;

/**
 * 
 * En este clase se maneja la información de los eventos, junto con sus respectivos sucesos en la vista <b>VentanaEventos</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class ControladorEventos {

	@FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private BorderPane BordPanePrincipal;

    @FXML
    private Button btnContacto;

    @FXML
    private Button btnCorreo;

    @FXML
    private Button btnMensaje;

    @FXML
    private ImageView imgView_BtnFlecha1;

    @FXML
    private ImageView imgView_BtnFlecha;

    @FXML
    private Region region1;

    @FXML
    private Region region2;

    @FXML
    private Region region3;

    @FXML
    private GridPane GridPaneObras2;

    @FXML
    private Region regionEventoDerecha;

    @FXML
    private Region regionEventoIzq;

    @FXML
    private JFXTextArea txtAreaUno;

    @FXML
    private Label lblEventoUno;

    @FXML
    private JFXTextArea txtAreaDos;

    @FXML
    private Label lblEventoDos;

    @FXML
    private Region regionEventAbajoIzq;

    @FXML
    private Region regionEventoAbajoDer;

    @FXML
    private Label lblEventoTres;

    @FXML
    private JFXTextArea txtAreaTres;

    @FXML
    private Label lblEventoCuatro;

    @FXML
    private JFXTextArea txtAreaCuatro;

    @FXML
    private GridPane gridPaneEventos;

    @FXML
    private ButtonBar btnBarArriba;

    @FXML
    private ImageView imgCalendar;

    @FXML
    private ImageView imgTickets;

    @FXML
    private ImageView imgUsuario;
    
    @FXML
    private ImageView imgVolverAtrasEventos;
    
    
    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada
		
    boolean logged; //Este nos dira si la parsona esta logueada o no
   
   
	 
	 public ControladorEventos(String usuario) {
		 if (usuario == "vacio") {
			 logged = false;
			 this.usuario = usuario;
		 }
		 else {
			 this.usuario = usuario;
			 logged = true;
		 }
		 
	}


    
    //ArrayList que guardara las imagenes que se mostraran en la ventana principal
  	ArrayList<Image> imagenes = new ArrayList<Image>();
  	//Vector<Evento> eventos = new Vector<Evento>(); //creamos el vector de eventos
  	Evento eventoUno = new Evento(1, "", "", "");
  	Evento eventoDos = new Evento(2, "", "", "");
  	Evento eventoTres = new Evento(3, "", "", "");
  	Evento eventoCuatro = new Evento(4, "", "", "");
  	
  	
  	//Contadores que indicaran como un puntero las imagenes que se muestran en los imgviews del image slider
   	int count = 0;
   	int countDos = 1;
   	int countTres = 2;
   	
    String directoryName = System.getProperty("user.dir");
    
   	
  	@FXML
  	/**
  	 * 
  	 * M�todo que inicializa ciertos elementos de la ventana de eventos cada vez que es desplegada.
  	 * 
  	 */
  	public void initialize() {
  		//Se añaden al ArrayList las imagenes que queremos que se muestren
  		imagenes.add(new Image("/sorolla.jpg"));
  	 	imagenes.add(new Image("/Dali.jpg"));
  	 	imagenes.add(new Image("/monet.jpg"));
  	 	imagenes.add(new Image("/seurat.jpg"));
  	 	imagenes.add(new Image("/VanGogh.jpg"));
  	 	imagenes.add(new Image("/people.jpg"));
  	 	eventoUno.obtenerDatosEventoBBDD(eventoUno.getIdentificador());
  	 	eventoDos.obtenerDatosEventoBBDD(eventoDos.getIdentificador());
  	 	eventoTres.obtenerDatosEventoBBDD(eventoTres.getIdentificador());
  	 	eventoCuatro.obtenerDatosEventoBBDD(eventoCuatro.getIdentificador());
  	 	
  	 	//Se colocan las imagenes en las regiones
  	 	region1.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(0)), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	region2.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(1)), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	region3.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(2)), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	
  	 	
  	 	//Cosas de la Informacion
  	 	Image arribaIzq = new Image("file:"+directoryName+"\\Imagenes_Multimedia\\"+eventoUno.getImagen()); //A las imagenes correspondientes le asignamos la direccion en la que se encuentran
  	 	Image arribaDer = new Image("file:"+directoryName+"\\Imagenes_Multimedia\\"+eventoDos.getImagen());
  	 	Image abajoIzq = new Image("file:"+directoryName+"\\Imagenes_Multimedia\\"+eventoTres.getImagen()); //A las imagenes correspondientes le asignamos la direccion en la que se encuentran
  	 	Image abajoDer = new Image("file:"+directoryName+"\\Imagenes_Multimedia\\"+eventoCuatro.getImagen());
  	 	
  	 	
  	 	//asignamos imagenes a la region
  	 	
  	 	regionEventoIzq.setBackground(new Background(new BackgroundFill(new ImagePattern(arribaIzq), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	regionEventoDerecha.setBackground(new Background(new BackgroundFill(new ImagePattern(arribaDer), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	regionEventAbajoIzq.setBackground(new Background(new BackgroundFill(new ImagePattern(abajoIzq), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	regionEventoAbajoDer.setBackground(new Background(new BackgroundFill(new ImagePattern(abajoDer), CornerRadii.EMPTY, Insets.EMPTY)));
  	 	
  	 	//asignamos los titulos
  	 	
  	 	lblEventoUno.setText(eventoUno.getNombre());
  	 	lblEventoDos.setText(eventoDos.getNombre());
  	 	lblEventoTres.setText(eventoTres.getNombre());
  	 	lblEventoCuatro.setText(eventoCuatro.getNombre());
  	 	
  	 	//asignamos las descripciones
  	 	
  	 	txtAreaUno.setText(eventoUno.getInformacion());
  	 	txtAreaDos.setText(eventoDos.getInformacion());
  	 	txtAreaTres.setText(eventoTres.getInformacion());
  	 	txtAreaCuatro.setText(eventoCuatro.getInformacion());
  	 	
  	 	//Mensajes emergentes con la informacion de contacto del museo
	 	Tooltip correo = new Tooltip("Dirección de correo: \npicturekasfw@gmail.com");
	 	btnCorreo.setTooltip(correo);
	 	
	 	Tooltip mensaje = new Tooltip("Número de mensaje: \n2309");
	 	btnMensaje.setTooltip(mensaje);
	 	
	 	Tooltip contacto = new Tooltip("Número de contacto: \n608693411");
	 	btnContacto.setTooltip(contacto);
	 	
  	}
  	

    @FXML
    void accederPerfil(MouseEvent event) {
    	
    	
    	if(logged == false) {
    		abrirLogin();
    		
    		
        	
        }
        else {
        	//Se carga el contenido de la ventana
        	FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaPerfil.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias
            ControladorPerfil controlerPerfil = new ControladorPerfil(usuario);
            loaderPrincipala.setController(controlerPerfil);
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
                controlerPerfil.getBarra().setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");
               
               
                
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

        }

    }


    @FXML
    /**
     * 
     * Mueve las imágenes a la derecha, tantas veces como el usuario pulse el botón.
     * 
     * @param event   Evento causado por el usuario al pulsar el botón de slide derecho.
     */
    void cambioImg(MouseEvent event) {
    	count++;
    	countDos++;
    	countTres++;
    	if(count>=imagenes.size()) {
    		count=0;
    	}
    	if(countDos>=imagenes.size()) {
    		countDos=0;
    	}
    	if(countTres>=imagenes.size()) {
    		countTres=0;
    	}
    	
    	region1.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(count)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	region2.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countDos)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	region3.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countTres)), CornerRadii.EMPTY, Insets.EMPTY)));
    	
	 	new SlideInRight(region1).setCycleCount(1).play();
    	new SlideInRight(region2).setCycleCount(1).play();
    	new SlideInRight(region3).setCycleCount(1).play();
	 	
	 	
    }
    
    
    
    @FXML
    
    /**
     * 
     * Mueve las im�genes a la izquierda, tantas veces como el usuario pulse el botón.
     * 
     * @param event   Evento causado por el usuario al pulsar el botón de slide izquierdo.
     */
    void cambioImgAtras(MouseEvent event) {
    	
    	count--;
    	countDos--;
    	countTres--;
    	if(count<0) {
    		count=(imagenes.size())-1;
    	}
    	if(countDos<0) {
    		countDos=(imagenes.size())-1;
    	}
    	if(countTres<0) {
    		countTres=(imagenes.size())-1;
    	}
    	
    	region1.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(count)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	region2.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countDos)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	region3.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countTres)), CornerRadii.EMPTY, Insets.EMPTY)));
    	
	 	new SlideInLeft(region1).setCycleCount(1).play();
    	new SlideInLeft(region2).setCycleCount(1).play();
    	new SlideInLeft(region3).setCycleCount(1).play();
	 	
    }

    @FXML
    /**
     * 
     * Ofrece la posibilidad de mandar emails al email del museo.
     * 
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre el botón del correo.
     */
    void mandarCorreo(ActionEvent event) {
    	
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPop = new FXMLLoader(getClass().getResource("/application/PopOverCorreo.fxml"));
    	//Se le asigna el controlador de la ventana PopOver
        ControladorPopOverCorreo controlerPop= new ControladorPopOverCorreo(usuario);
        loaderPop.setController(controlerPop);
        AnchorPane PopOverCorreo;
    	
		if (logged == false) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setHeaderText("Oh no! Para acceder a esta función debes estar iniciado sesión.");
    		error.showAndWait();
        	abrirLogin();
			
		} else {

			try {
				// Se carga en un AnchorPane la ventana
				PopOverCorreo = (AnchorPane) loaderPop.load();

				// Se ajusta el AnchorPane para que sea escalable
				AnchorPane.setTopAnchor(PopOverCorreo, 0.0);
				AnchorPane.setRightAnchor(PopOverCorreo, 0.0);
				AnchorPane.setLeftAnchor(PopOverCorreo, 0.0);
				AnchorPane.setBottomAnchor(PopOverCorreo, 0.0);

				// Se crea un PopOver al que se le asigna el anchorPane en el que hemos cargado
				// nuestra ventana
				PopOver popOver = new PopOver(PopOverCorreo);
				// Se le dice que se muestre tal PopOver al pulsar el boton pasado por parametro
				popOver.show(btnCorreo);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

    @FXML
    void mandarMensaje(ActionEvent event) {

    }

    @FXML
    void mostrarContacto(ActionEvent event) {

    }

    @FXML
    /**
     * 
     * Muestra la ventana de reserva de tickets.
     * 
     * @param event		Evento causado cuando el usuario pulsa la imagen de los tickets.
     */
    void reservarTickets(MouseEvent event) {
    	
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderTickets = new FXMLLoader(getClass().getResource("/application/VentanaTickets.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorTickets controlerTickets = new ControladorTickets(usuario);
        loaderTickets.setController(controlerTickets);
        AnchorPane PaneTickets;

        
        if(logged == false) {
        	Alert error = new Alert(Alert.AlertType.ERROR);
			error.setHeaderText("Oh no! Para acceder a esta función debes estar iniciado sesión.");
    		error.showAndWait();
        	abrirLogin();
        	
        }
        else {
        	 try {
     			//Se carga en un AnchorPane la ventana
     			PaneTickets = (AnchorPane) loaderTickets.load();
     			
     			//Se elimina el contenido de la ventana padre
             	anchorPanePrincipal.getChildren().clear();
             	
             	//Se ajusta el AnchorPane para que sea escalable
                 AnchorPane.setTopAnchor(PaneTickets, 0.0);
                 AnchorPane.setRightAnchor(PaneTickets, 0.0);
                 AnchorPane.setLeftAnchor(PaneTickets, 0.0);
                 AnchorPane.setBottomAnchor(PaneTickets, 0.0);
                 
                 //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                 anchorPanePrincipal.getChildren().setAll(PaneTickets);
                 Stage s = (Stage) anchorPanePrincipal.getScene().getWindow();
                 s.setHeight(833);
                 s.setWidth(1156);
                 s.setResizable(false);
                 s.setMaximized(false);
                 s.show();
                
                 
     		} catch (IOException e) {
     			e.printStackTrace();
     		}
        }
       

    	
    }
    
    /**
     * 
     * Muestra la ventana de Login inmediatamente sin que el usuario pulse ningún botón.
     * 
     */
    void abrirLogin() {
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderApp = new FXMLLoader(getClass().getResource("/application/InterfazLogin.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorInicioSesion controlerInicio = new ControladorInicioSesion();
        loaderApp.setController(controlerInicio);
        AnchorPane PaneInicioSesion;

		try {
			//Se carga en un AnchorPane la ventana
			PaneInicioSesion = (AnchorPane) loaderApp.load();
			
			//Se elimina el contenido de la ventana padre
        	anchorPanePrincipal.getChildren().clear();
        	
        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PaneInicioSesion, 0.0);
            AnchorPane.setRightAnchor(PaneInicioSesion, 0.0);
            AnchorPane.setLeftAnchor(PaneInicioSesion, 0.0);
            AnchorPane.setBottomAnchor(PaneInicioSesion, 0.0);
            

            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PaneInicioSesion);
            
           
            
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	
    }

    @FXML
    void verEventos(MouseEvent event) {

    }
    
    @FXML
    /**
     * 
     * Devuelve al usuario a la ventana principal.
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre la imagen para volver atrás.
     */
    void volverAtrasEventos(MouseEvent event) {
		// Se carga el contenido de la ventana
		FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
		// Se le asigna el controlador de la ventana para editar informacion de los
		// guardias

		ControladorVPrincipal controlerPrincipal = new ControladorVPrincipal(usuario);
		loaderPrincipal.setController(controlerPrincipal);
		AnchorPane PaneVentanaPrincipal;

		try {
			// Se carga en un AnchorPane la ventana
			PaneVentanaPrincipal = (AnchorPane) loaderPrincipal.load();

			// Se elimina el contenido de la ventana padre
			anchorPanePrincipal.getChildren().clear();

			// Se ajusta el AnchorPane para que sea escalable
			AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
			AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
			AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
			AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);

			// Se añade el contenido de la ventana cargada en el AnchorPane del padre
			anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);
			
			if(logged!=false) {
				controlerPrincipal.getAvatarUsuario().setImage(new Image("/avatarCliente.png"));
    			controlerPrincipal.getGridPaneButton().setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    
  	
  	
  	public GridPane getGridPaneEventos() {
		return gridPaneEventos;
	}
	
	public void setLogged(boolean log) {
  		
  		logged = log;
  	}
  	public boolean getLogged() {
  		
  		return logged;
  	}
  	public ButtonBar getBtnBarArriba() {
  		return btnBarArriba;
  	}
  	
  	public ImageView getAvatarUsuario() {
  		return imgUsuario;
  	}
    
    
}
