package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;

import Modelo.Alerta;
import Modelo.Sensor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * En estaa clase, se maneja la información recogida de todos los sensores y se muestran en la vista <b>VentanaSala</b>.
 *
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 */

public class ControladorSalas {

    @FXML
    private AnchorPane anchorPaneSala;

    @FXML
    private VBox VBoxSala;

    @FXML
    private JFXToolbar ToolBarSala;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private ImageView imgVolverGuardia;

    @FXML
    private GridPane GridPaneSala;

    @FXML
    private ImageView imgTemperatura;

    @FXML
    private ImageView imgLuz;

    @FXML
    private TextArea textTemperatura;

    @FXML
    private TextArea textLuz;

    @FXML
    private GridPane GridSensorDistancia;

    @FXML
    private ImageView imgDistancia;

    @FXML
    private JFXButton btnA;

    @FXML
    private JFXButton btnB;

    @FXML
    private JFXButton btnC;

    @FXML
    private JFXButton btnD;

    private String usuario;

    boolean logged; //Este nos dira si la parsona esta logueada o no

    private int sala;

    private String tipoStaff;

    static final String USER = "pri_Pictureka";

    static final String PASS = "asas";

    Connection conn = null;

    Statement stmt = null;

    String sql;

    Timer timer_uno = new Timer(true);

    Alerta alert;

    /**
     *
     * Constructor de la clase <b>ControladorSalas</b> que guarda la información del usuario.
     *
     * @param usuario		El usuario que se encuentre iniciado sesión.
     * @param _sala			La sala en la que se encuentre el usuario.
     * @param _tipoStaff	Tipo de Staff que se encuentra iniciado sesión.
     */
	 public ControladorSalas(String usuario, int _sala, String _tipoStaff, Alerta alertaNoti) {
		 if (usuario == "vacio") {
			 logged = false;
			 this.usuario = usuario;
			 this.alert = alertaNoti;
		 }
		 else {
			 this.usuario = usuario;
			 logged = true;
			 this.sala = _sala;
			 tipoStaff =_tipoStaff;
			 this.alert = alertaNoti;
		 }

	}
	@FXML
	/**
	 *
	 * Inicializa la ventana sala, con su respectiva número de sala y con la información del usuario que se encuentra iniciado sesión.
	 *
	 */
	public void initialize() {

		//recibir datos de BD
		Vector<Sensor> sensores = sensoresBD(this.sala);

		//Escribir datos en tablas
		if(sensores!=null) {
			getData();
		}

		//textLuz.setText("Actualmente esta cargada la sala "+sala.getIdentificador());
		//Dependiendo del usuario que se encuentre iniciado sesion se muestra una u otro avatar
		if (tipoStaff.equals("Guardia")) {
			imgAvatar.setImage(new Image("/guardiaAvatar.png"));
		}
		else {
			imgAvatar.setImage(new Image("/administradorAvatar.png"));
		}
	}

	private void actualizarDatos(Vector<Sensor> sensores) {
		for(int i = 0; i<sensores.size();i++) {
			if(sensores.elementAt(i).getTipo().equals("Luz")) {
				textLuz.setText("LUZ: " + (int)(sensores.elementAt(i).getLectura()*100)/1024 + "% |  Analog reading = "+ sensores.elementAt(i).getLectura()+" \n"+textLuz.getText());

				if (sensores.elementAt(i).getLectura()<50) {

				}
			}
			else if(sensores.elementAt(i).getTipo().equals("Temperatura")) {
				textTemperatura.setText("TEMPERATURA: "+ sensores.elementAt(i).getLectura() +" °C \n"+textTemperatura.getText());
			}
			else {
				if(sensores.elementAt(i).getPosicion()==1) {
					actualizarBoton(sensores.elementAt(i).getLectura(), btnA);
				}
				else if(sensores.elementAt(i).getPosicion()==2) {
					actualizarBoton(sensores.elementAt(i).getLectura(), btnB);
				}
				else if(sensores.elementAt(i).getPosicion()==3) {
					actualizarBoton(sensores.elementAt(i).getLectura(), btnC);
				}
				else {
					actualizarBoton(sensores.elementAt(i).getLectura(), btnD);
				}
			}
		}
	}

	private void actualizarBoton(float lectura, JFXButton btn) {

		if(lectura<30) {
			btn.setStyle("-fx-background-color: #ff0000; ");
		}
		else {
			btn.setStyle("-fx-background-color: #ffAA00; ");
		}

	}
	private Vector<Sensor> sensoresBD(int sala) {
		Vector<Sensor> sensores = new Vector<Sensor>();
		try {
            //STEP 1: Register JDBC driver
        	Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);

            //Se realiza la consulta en la tabla de CLIENTE
            sql = "SELECT HISTORIAL.*, SENSORES.Tipo, SENSORES.ID_Sala, SENSORES.Posicion \r\n"
            		+ "    FROM HISTORIAL \r\n"
            		+ "        JOIN (SELECT  HISTORIAL.TipoSensor AS tSensor, max(HISTORIAL.Fecha) as fecha FROM HISTORIAL GROUP BY HISTORIAL.TipoSensor)\r\n"
            		+ "         m ON HISTORIAL.TipoSensor = m.tSensor AND HISTORIAL.Fecha = m.fecha\r\n"
            		+ "        JOIN SENSORES ON HISTORIAL.TipoSensor = SENSORES.identificador\r\n"
            		+ "        WHERE SENSORES.ID_Sala ="+sala+";";

            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String tipo = rs.getString("Tipo");
				int ID_Sala = rs.getInt("ID_Sala");
				int Posicion = rs.getInt("Posicion");
				float lectura = rs.getFloat("Lectura");
				Timestamp Fecha = rs.getTimestamp("Fecha");



				sensores.add(new Sensor(tipo, ID_Sala, Posicion, lectura, Fecha));
			}
			 rs.close();
			stmt.close();

			conn.close();

			}
			 catch (Exception e) {
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

		return sensores;

	}
	@FXML
	/**
	 *
	 * Devuelve al usuario a su ventana inicial, dependiendo del usuario que está iniciado sesión.
	 *
	 * @param event		Evento causado cuando el guardia pulsa sobre la imagen de vuelta atrás.
	 */
	void volverAtrasSalas(MouseEvent event) {

		timer_uno.cancel();


		if(tipoStaff.equals("Guardia")) {

			alert.getTimer_alert().cancel();

			//Se carga el contenido de la ventana
	    	FXMLLoader loaderGuardia = new FXMLLoader(getClass().getResource("/application/VentanaGuardia.fxml"));
	    	//Se le asigna el controlador de la ventana para editar información de los guardias
	        ControladorGuardia controlerGuardia = new ControladorGuardia(usuario);
	        loaderGuardia.setController(controlerGuardia);
	        AnchorPane PaneGuardia;
			try {
				//Se carga en un AnchorPane la ventana
				PaneGuardia = (AnchorPane) loaderGuardia.load();

				//Se elimina el contenido de la ventana padre
				anchorPaneSala.getChildren().clear();

	        	//Se ajusta el AnchorPane para que sea escalable
	            AnchorPane.setTopAnchor(PaneGuardia, 0.0);
	            AnchorPane.setRightAnchor(PaneGuardia, 0.0);
	            AnchorPane.setLeftAnchor(PaneGuardia, 0.0);
	            AnchorPane.setBottomAnchor(PaneGuardia, 0.0);


	            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
	            anchorPaneSala.getChildren().setAll(PaneGuardia);



			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		else {

			alert.getTimer_alert().cancel();

			//Se carga el contenido de la ventana
	    	FXMLLoader loaderAdministrador = new FXMLLoader(getClass().getResource("/application/VentanaAdministrador.fxml"));
	    	//Se le asigna el controlador de la ventana para editar información de los guardias
	        ControladorAdministrador controlerAdministrador = new ControladorAdministrador(usuario);
	        loaderAdministrador.setController(controlerAdministrador);
	        AnchorPane PaneAdministrador;

			try {
				//Se carga en un AnchorPane la ventana
				PaneAdministrador = (AnchorPane) loaderAdministrador.load();

				//Se elimina el contenido de la ventana padre
				anchorPaneSala.getChildren().clear();

	        	//Se ajusta el AnchorPane para que sea escalable
	            AnchorPane.setTopAnchor(PaneAdministrador, 0.0);
	            AnchorPane.setRightAnchor(PaneAdministrador, 0.0);
	            AnchorPane.setLeftAnchor(PaneAdministrador, 0.0);
	            AnchorPane.setBottomAnchor(PaneAdministrador, 0.0);


	            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
	            anchorPaneSala.getChildren().setAll(PaneAdministrador);



			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

    @FXML
    /**
     *
     * Muestra la información del usuario que se encuentra iniciado sesión.
     *
     * @param event		Evento causado cuando el usuario pulsa sobre la imagen de su avatar.
     */
    void verPerfil(MouseEvent event) {

    	if(logged == false) {
        	Alert error = new Alert(Alert.AlertType.ERROR);
			error.setHeaderText("Oh no! Para acceder a esta función debes estar iniciado sesión.");
    		error.showAndWait();

        }
        else {
        	timer_uno.cancel();
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
    			anchorPaneSala.getChildren().clear();

            	//Se ajusta el AnchorPane para que sea escalable
                AnchorPane.setTopAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setRightAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setLeftAnchor(PaneVentanaPrincipal, 0.0);
                AnchorPane.setBottomAnchor(PaneVentanaPrincipal, 0.0);


                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPaneSala.getChildren().setAll(PaneVentanaPrincipal);
                controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");


    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }

    }
    public void getData() {
    	timer_uno.scheduleAtFixedRate(new Task(this.sala), 0 ,  10500);

    }
    class Task extends TimerTask {
    	int sala = 1;

    	public Task(int sala) {
    		this.sala = sala;
    	}

        public void run() {
        	Vector<Sensor> sensores = sensoresBD(sala);
    		//Escribir datos en tablas
    		if(sensores!=null) {
    			actualizarDatos(sensores);
    		}
        }
      }


	public JFXToolbar getToolBarSala() {
		return ToolBarSala;
	}
	public void setToolBarSala(JFXToolbar toolBarSala) {
		ToolBarSala = toolBarSala;
	}




}
