package Controlador;

import java.io.IOException;
import java.util.ArrayList;

import org.controlsfx.control.PopOver;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
/**
 * 
 * En la clase ControladorVPrincipal, manejamos todos los eventos que ocurren en la vista <b>VentanaPrincipal</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */


public class ControladorVPrincipal {

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
    private GridPane gridPaneRegion;

    @FXML
    private ImageView imgView_BtnFlecha1;

    @FXML
    private ImageView imgView_BtnFlecha;

    @FXML
    private Region regionUno;

    @FXML
    private Region regionDos;

    @FXML
    private Region regionTres;

    @FXML
    private GridPane gridPaneInfo;

    @FXML
    private Region regionImg;

    @FXML
    private Region regionMuseo;
    
    @FXML
    private Region regionPensamiento;

    @FXML
    private GridPane gridPaneButton;

    @FXML
    private ImageView imgCerrarSesionCliente;

    @FXML
    private Label lblBienvenido;

    @FXML
    private ImageView imgUsuario;

    @FXML
    private ImageView imgTickets;

    @FXML
    private ImageView imgCalendar;
    
    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada
	
    boolean logged; //Este nos dira si la parsona esta logueada o no
    
	 
    /**
     * 
     * Constructor de la clase <b>ControladorVPrincipal</b> que guarda la información del usuario.
     * 
     * @param usuario	El usuario que está inciado sesión en ese momento.
     */
	 public ControladorVPrincipal(String usuario) {
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
 	
	@FXML
	/**
	 * 
	 * Método que inicializa ciertos elementos de la ventana cada vez que es desplegada.
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
	 	
	 	
	 	regionUno.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(0)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionDos.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(1)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionTres.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(2)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	
	 	
	 	Image horario = new Image("/Horarios.jpg");
	 	Image museo = new Image("/museoLouvre.jpg");
	 	Image pensamiento = new Image("/Pensamiento.jpg");
	 	
	 	regionImg.setBackground(new Background(new BackgroundFill(new ImagePattern(horario), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionMuseo.setBackground(new Background(new BackgroundFill(new ImagePattern(museo), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionPensamiento.setBackground(new Background(new BackgroundFill(new ImagePattern(pensamiento), CornerRadii.EMPTY, Insets.EMPTY)));
	 	
	 	//Label que muestra un mensaje de bienvenida al usuario
	 	lblBienvenido.setVisible(true);
	 	
	 	//Mensajes emergentes con la informacion de contacto del museo
	 	Tooltip correo = new Tooltip("Dirección de correo: \npicturekasfw@gmail.com");
	 	btnCorreo.setTooltip(correo);
	 	
	 	Tooltip mensaje = new Tooltip("Número de mensaje: \n2309");
	 	btnMensaje.setTooltip(mensaje);
	 	
	 	Tooltip contacto = new Tooltip("Número de contacto: \n608693411");
	 	btnContacto.setTooltip(contacto);
	 	
	 	//Comprobacion para mostrar el boton de cierre de sesion, si un usuario se encuentra registrado
	 	if (logged==false) {
	 		imgCerrarSesionCliente.setVisible(false);
	 	}
	}
	


	//Contadores que indicaran como un puntero las imagenes que se muestran en los imgviews del image slider
	int count = 0;
	int countDos = 1;
	int countTres = 2;

    @FXML
    /**
     * 
     * Mueve las imagenes a la derecha, tantas veces como el usuario pulse el boton.
     * 
     * @param event   Evento causado por el usuario al pulsar el boton de slide derecho.
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
    	
    	
    	
    	regionUno.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(count)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionDos.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countDos)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionTres.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countTres)), CornerRadii.EMPTY, Insets.EMPTY)));
    	
    	new SlideInRight(regionUno).setCycleCount(1).play();
    	new SlideInRight(regionDos).setCycleCount(1).play();
    	new SlideInRight(regionTres).setCycleCount(1).play();
    	
    }
    
    
    
    @FXML
    
    /**
     * 
     * Mueve las imagenes a la izquierda, tantas veces como el usuario pulse el boton.
     * 
     * @param event   Evento causado por el usuario al pulsar el boton de slide izquierdo.
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
    	
    	
    	regionUno.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(count)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionDos.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countDos)), CornerRadii.EMPTY, Insets.EMPTY)));
	 	regionTres.setBackground(new Background(new BackgroundFill(new ImagePattern(imagenes.get(countTres)), CornerRadii.EMPTY, Insets.EMPTY)));
    	
	 	new SlideInLeft(regionUno).setCycleCount(1).play();
    	new SlideInLeft(regionDos).setCycleCount(1).play();
    	new SlideInLeft(regionTres).setCycleCount(1).play();
    }

    @FXML
    /**
     * 
     * Dirige al usuario a la ventana para visualizar su información.
     * 
     * @param event   Evento causado cuando el usuario pulsa sobre la imagen del avatar.
     */
    void accederPerfil(MouseEvent event) {
		
    	if(logged == false) {
        	abrirLogin();
        }
        else {
        	//Se carga el contenido de la ventana
        	FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaPerfil.fxml"));
        	//Se le asigna el controlador de la ventana para editar información de los guardias
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
     * Muestra la ventana de reserva de tickets.
     * 
     * @param event   Evento causado cuando el usuario pulsa la imagen de los tickets.
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
               //Cambia el color de la barra de la ventana perfil
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
    
    @FXML
    /**
     * 
     * Ofrece la posibilidad de mandar emails al email del museo.
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre el botón del correo.
     */
    
    void mandarCorreo(ActionEvent event) {
    	/*
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
*/
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
     * Muestra la ventana de eventos, en la que se puede visualizar la información respectiva a cada evento.
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre la imagen del calendario.
     */
    void verEventos(MouseEvent event) {
		// carga el contenido de la ventana
    	FXMLLoader loaderEventos = new FXMLLoader(getClass().getResource("/application/VentanaEventos.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorEventos controlerEventos = new ControladorEventos(usuario);
        loaderEventos.setController(controlerEventos);
        AnchorPane PaneCalendar;

        
        try {
			//Se carga en un AnchorPane la ventana
            PaneCalendar = (AnchorPane) loaderEventos.load();
			
			//Se elimina el contenido de la ventana padre
        	anchorPanePrincipal.getChildren().clear();
        	
        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PaneCalendar, 0.0);
            AnchorPane.setRightAnchor(PaneCalendar, 0.0);
            AnchorPane.setLeftAnchor(PaneCalendar, 0.0);
            AnchorPane.setBottomAnchor(PaneCalendar, 0.0);
            
            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PaneCalendar);
    	
            if (logged==false) {
            	this.usuario = "vacio";
	    		this.logged = false;
	    		
            }
	    	else {
	    		controlerEventos.getAvatarUsuario().setImage(new Image("/avatarCliente.png"));
	            controlerEventos.getGridPaneEventos().setStyle("-fx-background-color:  linear-gradient(to bottom, #80FFDB, #5390D9)");
            } 
        }catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    
    @FXML
    /**
     * 
     * Devuelve al usuario a la ventana principal habiendo cerrado su sesión.
     * 
     * @param event		Evento causado cuando el usuario pulsa sobre la imagen para cerrar sesión.
     */
    void cerrarSesionCliente(MouseEvent event) {
    	
    	if (logged == false) {
    		
    	}
    	else {
    	
    	
    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
    	
    	this.usuario = "vacio";
    	this.logged = false;
    	
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorVPrincipal controlerPrincipal = new ControladorVPrincipal(usuario);
        loaderPrincipal.setController(controlerPrincipal);
        AnchorPane PanePrincipal;

		try {
			//Se carga en un AnchorPane la ventana
			PanePrincipal = (AnchorPane) loaderPrincipal.load();
			
			//Se elimina el contenido de la ventana padre
        	anchorPanePrincipal.getChildren().clear();
        	
        	//Se ajusta el AnchorPane para que sea escalable
            AnchorPane.setTopAnchor(PanePrincipal, 0.0);
            AnchorPane.setRightAnchor(PanePrincipal, 0.0);
            AnchorPane.setLeftAnchor(PanePrincipal, 0.0);
            AnchorPane.setBottomAnchor(PanePrincipal, 0.0);
            

            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PanePrincipal);
            
           
            
		} catch (IOException e1) {
			e1.printStackTrace();
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
    
    
    
    
	public void setLogged(boolean log) {
		
		this.logged = log;
	}
	public boolean getLogged() {
		
		return logged;
	}
	
	public GridPane getGridPaneButton() {
		return gridPaneButton;
	}

	public ImageView getAvatarUsuario() {
		return imgUsuario;
	}
	public Label getLblBienvenido() {
		return lblBienvenido;
	}
	public GridPane getGridPaneRegion() {
		return gridPaneRegion;
	}
	public void setGridPaneRegion(GridPane gridPaneRegion) {
		this.gridPaneRegion = gridPaneRegion;
	}
    
    

}
