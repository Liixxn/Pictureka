package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Vector;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Modelo.Alerta;
import Modelo.Cliente;
import Modelo.M_Reservas;
import Modelo.Reserva;
import Modelo.Staff;
import Modelo.modelo_Museo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * En esta clase se manejan varias de las funcionalidades que tiene un guardia, en la vista <b>VentanaGuardia</b>.
 *
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 */

public class ControladorGuardia {

	@FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private VBox VBoxPrincipal;

    @FXML
    private ImageView imgCerrarSesion;

    @FXML
    private ImageView imgUsuario;

    @FXML
    private ImageView imgInforne;

    @FXML
    private ImageView btnHistorial;

    @FXML
    private ImageView ImgNotificaciones;

    @FXML
    private JFXTextField numTicket;

    @FXML
    private JFXTextField tituloInforme;

    @FXML
    private Text SalaText;

    @FXML
    private ImageView imgTicket;

    @FXML
    private ImageView imgCerrarSesion1111;

    @FXML
    private ButtonBar imgValidar;

    @FXML
    private ImageView imgEnviarInforme;

    @FXML
    private ImageView imgFormulario;

    @FXML
    private JFXTextArea cuerpoInforme;

    @FXML
    private ImageView imgSala1;

    @FXML
    private ImageView imgSala2;

    @FXML
    private ImageView imgSala4;

    @FXML
    private ImageView imgSala3;

    LocalDate fechaActual = LocalDate.now();

    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada

    boolean logged; //Este nos dira si la parsona esta logueada o no

    static final String USER = "pri_Pictureka";
	static final String PASS = "asas";

	 Alerta alert = new Alerta(usuario, "Guardia", this);

	 /**
	  *
	  * Constructor de la calse <b>ControladorGuardia</b> que guarda la información del usuario.
	  *
	  * @param usuario		Información del usuario que se encuentra iniciado sesión.
	  */
	 public ControladorGuardia(String usuario) {
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


	 public void initialize() {


		 alert.getDatos();
	 }



    @FXML
    /**
     *
     * Muestra la ventana Perfil, donde el guarda puede visualizar su información personal.
     *
     *@param event		Evento causado cuando el guardia pulsa sobre su avatar.
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
        	//Se le asigna el controlador de la ventana para editar información de los guardias
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

                controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");

                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);



    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

        }

    }



    @FXML
    void abrirHistorialSensores(MouseEvent event) {


    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipala = new FXMLLoader(getClass().getResource("/application/VentanaHistorico.fxml"));
    	//Se le asigna el controlador de la ventana para editar información de los guardias
        ControladorHistorico controlerHistorico = new ControladorHistorico(usuario, "Guardia", alert);
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
	 * Envía el correspondiente informe a todos los administradores, con su
	 * respectivo título y cuerpo.
	 *
	 * @param event Evento causado cuando el guardia pulsa sobre la imagen de envío
	 *              del informe.
	 */
	void enviar(MouseEvent event) {

		Connection conn = null;
		Statement stmt = null;
		String sql;
		String sql2;

		Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
		Alert error = new Alert(Alert.AlertType.ERROR);
		// Se comprueba que el titulo y cuerpo del informe no se encuentren vacios
		if (!(tituloInforme.getText().isEmpty() | cuerpoInforme.getText().isEmpty())) {
			// Se comprueba que el cuerpo del informe tenga al menos 20 caracteres
			modelo_Museo museo = new modelo_Museo();
			Staff guardia = museo.getRegistro().recuperar1Staff(usuario);
			// El informe que el guardia escribe lo pueden visualizar todos
			String destino = "Solo administradores";


			 try {
		            //STEP 1: Register JDBC driver
		        	Class.forName("org.mariadb.jdbc.Driver");

		            //STEP 2: Open a connection

		            conn = DriverManager.getConnection(
		                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);

		            //Se realiza la consulta en la tabla de CLIENTE
		            sql = "INSERT INTO INFORMES(titulo, cuerpo, destino, autor, fecha)"
		            		+ "VALUES ('"+tituloInforme.getText()+"', '"+cuerpoInforme.getText()+"', '"+destino+"', '"+guardia.getNombre()+"', CURRENT_TIMESTAMP())";
		            stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery( sql );


					sql2 = "INSERT INTO MANEJA(Usuario, Fecha)"
							+ "VALUES('"+guardia.getUsuario()+"', CURRENT_TIMESTAMP());";

					rs = stmt.executeQuery(sql2);


					rs.close();
					stmt.close();

					//STEP 6: Cerrando conexion.
					conn.close();

					confirmacion.setHeaderText("Informe guardado con éxito");
					confirmacion.showAndWait();

		        }

		        catch (SQLException se) {
		            //Handle errors for JDBC
		            se.printStackTrace();
		        } catch (Exception e) {
		            //Handle errors for Class.forName
		            e.printStackTrace();
		        } finally {
		            //finally block used to close resources
		            try {
		                if (stmt != null) {
		                    conn.close();
		                }
		            } catch (SQLException se) {
		            }// do nothing
		            try {
		                if (conn != null) {
		                    conn.close();
		                }
		            } catch (SQLException se) {
		                se.printStackTrace();
		            }//end finally try
		        }//end try

		} else {
			error.setHeaderText("Porfavor rellene los campos del informe!");
			error.show();
		}
	}

    @FXML
    /**
     *
     * Devuelve al guardia a la ventana principal, cerrando su sesión actual.
     *
     * @param event		Evento causado cuando el guardia pulsa sobre la imagen de cerrar sesión.
     */
    void cerrarSesion(MouseEvent event) {

    	//Se cancela el timer ya que se cierra sesion
    	alert.getTimer_alert().cancel();

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaPrincipal.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
    	this.usuario = "vacio";
    	this.logged = false;
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


            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
            anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);



		} catch (IOException e1) {
			e1.printStackTrace();
		}


    }

    @FXML
    void accederInformes(MouseEvent event) {

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderPrincipal = new FXMLLoader(getClass().getResource("/application/VentanaInformeAdmin.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias
        ControladorInformeAdmin controlerInforme = new ControladorInformeAdmin(usuario, alert);
        loaderPrincipal.setController(controlerInforme);
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
            controlerInforme.getImgUsuario().setImage(new Image("/guardiaAvatar.png"));
            controlerInforme.getGridPaneInforme().setVisible(false);



		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }


    @FXML
    /**
     *
     * Muestra la ventana de la Sala 1, mostrando la información de los diferentes sensores.
     *
     * @param event		Evento causado cuando el guardia pulsa sobre la imagen de la primera sala.
     */
    void sala1(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 1, "Guardia", alert);
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
	 * @param event		Evento causado cuando el guardia pulsa sobre la imagen de la segunda sala.
	 */
    void sala2(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 2, "Guardia", alert);
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
     *@param event		Evento causado cuando el guardia pulsa sobre la imagen de la tercera sala.
     */
    void sala3(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 3, "Guardia", alert);
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
     * @param event		Evento causado cuando el guardia pulsa sobre la imagen de la cuarta sala.
     */
    void sala4(MouseEvent event) {

        	//Se carga el contenido de la ventana
        	FXMLLoader loaderSala1 = new FXMLLoader(getClass().getResource("/application/VentanaSala.fxml"));
        	//Se le asigna el controlador de la ventana para editar informacion de los guardias

            ControladorSalas controlerSala1 = new ControladorSalas(usuario, 4, "Guardia", alert);
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
    void desplegarNotificaciones(MouseEvent event) {

    	//Se carga el contenido de la ventana
    	FXMLLoader loaderNotificaciones = new FXMLLoader(getClass().getResource("/application/PopOverNotificaciones.fxml"));
    	//Se le asigna el controlador de la ventana para editar informacion de los guardias

        ControladorPopOverNotificacion controlerNoti = new ControladorPopOverNotificacion(usuario, "Guardia"
        		+ "", this, alert);
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
            popOver.show(ImgNotificaciones);



		} catch (IOException e1) {
			e1.printStackTrace();
		}


    }


    @FXML
    /**
     *
     * Método que valida una reserva de un cliente. Se introduce el identificador de la reserva y se comprueba que sea válida.
     *
     * @param event		Evento causado cuando el guardia pulsa sobre la imagen para validar la reserva.
     */
    void validarTicket(MouseEvent event) {


		Alert error = new Alert(Alert.AlertType.ERROR);
		Alert informative = new Alert(Alert.AlertType.CONFIRMATION);

		String reservaEncontrada = "no encontrada";

		modelo_Museo museo = new modelo_Museo();
		Staff staff = museo.devolverStaff(usuario);
		String revisor = staff.getUsuario();


		// Se obtiene el texto del JtextField
		String ticketAcomprobar = numTicket.getText();
		int identificadorReserva = 0;
		try {
			// Se comprueba que lo introducida por el guardia sea un numero
			identificadorReserva = Integer.parseInt(ticketAcomprobar);
			// Se comrpueba que el numero introducido se encuentre dentro del rango
			if (identificadorReserva >= 100000 && identificadorReserva <= 999999) {

				M_Reservas reservas = new M_Reservas();
				Vector<Reserva> tickets = reservas.recuperarReserva();
				reservaEncontrada = busBinReservas(tickets, identificadorReserva, 0, tickets.size()-1);


				if (reservaEncontrada.equals("no encontrada")) {
					error.setHeaderText("La reserva que busca no existe.");
					error.showAndWait();
				}
				else if(reservaEncontrada.equals("valida")){
					reservas.establecerRevisor(revisor, identificadorReserva);
					informative.setHeaderText("La reserva es valida.");
					informative.showAndWait();

				}

			} else {
				error.setHeaderText("Rango del identificador del ticket no aceptable.");
				error.showAndWait();
			}

		} catch (NumberFormatException ex) {
			error.setHeaderText("Formato del identificador no válido.");
			error.showAndWait();
		}

	}

	private String busBinReservas(Vector<Reserva> reservas, int idReserva, int inicio, int fin) {

		Alert error = new Alert(Alert.AlertType.ERROR);

		if (inicio > fin) {
			return "no encontrada";
		} else {
			M_Reservas visibilidad = new M_Reservas();
			int medio = (inicio + fin) / 2;

			int res = visibilidad.visualizarVisibilidad(idReserva);

			if (reservas.get(medio).getIdentificador() == idReserva) {

				if (res == 1) {

					if (!reservas.get(medio).getFecha().isBefore(fechaActual)) {
						return "valida";
					} else {

						error.setHeaderText("La reserva ya ha caducado.");
						error.setContentText("Caducó en: " + reservas.elementAt(medio).getFecha());
						error.show();
						return "invalida";
					}
				} else {

					error.setHeaderText("La reserva no existe.");
					return "no encontrada";

				}
			} else {
				if (idReserva < reservas.get(medio).getIdentificador()) {
					return busBinReservas(reservas, idReserva, inicio, medio);
				} else {
					return busBinReservas(reservas, idReserva, medio + 1, fin);
				}
			}
		}
	}

	public ImageView getImgNotificaciones() {
		return ImgNotificaciones;
	}

	public void setImgNotificaciones(ImageView imgNotificaciones) {
		ImgNotificaciones = imgNotificaciones;
	}

}
