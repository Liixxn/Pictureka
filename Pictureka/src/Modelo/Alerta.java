package Modelo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.controlsfx.control.Notifications;

import Controlador.ControladorAdministrador;
import Controlador.ControladorGuardia;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;






public class Alerta {

	private String usuario;

	boolean logged; //Este nos dira si la parsona esta logueada o no

	private String tipoStaff;
	 
	static final String USER = "pri_Pictureka";

	static final String PASS = "asas";

	Connection conn = null;
	
	String sql;

	Statement stmt = null;
	
	Timer timer_alert = new Timer(true);
	
	Alert alert = new Alert(Alert.AlertType.WARNING);
	
	Vector<Sensor> sensores = new Vector<Sensor>();
	
	ControladorGuardia controlerG;
	
	ControladorAdministrador controlerA;
	
	Media sound = new Media(new File("src/arpSound.mp3").toURI().toString());
    
	//contador que guarda el numero de sensores inestables
	int i = 0;

	
	public Alerta(String usuario, String tipoStaff, ControladorGuardia controller) {
		
		this.usuario = usuario;
		this.tipoStaff = tipoStaff;
		this.controlerG = controller;
			
	}
	
	public Alerta(String usuario, String tipoStaff, ControladorAdministrador controllerA) {
		
		this.usuario = usuario;
		this.tipoStaff = tipoStaff;
		this.controlerA = controllerA;
			
	}
	
	

	public void showNotificaciones(Sensor sensor, String tipoStaff) {	
		
		i++;
				
		/*
		Stage owner = new Stage(StageStyle.TRANSPARENT);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: TRANSPARENT");
        Scene scene = new Scene(root, 1, 1);
        scene.setFill(Color.TRANSPARENT);
        owner.setScene(scene);
        owner.setWidth(1);
        owner.setHeight(1);
        owner.toBack();
        owner.show();
   */   
		Notifications notificacion = Notifications.create().title("ADVERTENCIA")
				.text("SALA: " + sensor.getID_Sala() + "\nSensor " + sensor.getTipo())
				/* .threshold(4, Notifications.create().title("Notificaciones colapsadas")) */.hideAfter(
						Duration.seconds(3));
		
		if (tipoStaff.equals("Guardia")) {
			this.controlerG.getImgNotificaciones().setImage(new Image("/Notification.png"));
		}
		else if (tipoStaff.equals("Administrador")) {
			this.controlerA.getImgNotiAdmin().setImage(new Image("Notification.png"));
		}
		
		
		
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		notificacion.showWarning();

	}
	
	






	private boolean checkSala(Sensor sensor) {
		
		if (sensor.getTipo().equals("Luz")) {
			if (sensor.getLectura() < 512) {
				sensores.add(new Sensor(sensor.getTipo(), sensor.getID_Sala(), sensor.getPosicion(), sensor.getLectura(), sensor.getFecha()));
				showNotificaciones(sensor, tipoStaff);
				return true;
			}
		}
		else if (sensor.getTipo().equals("Temperatura")) {
			if (sensor.getLectura() < 14 || sensor.getLectura() > 26) {
				sensores.add(new Sensor(sensor.getTipo(), sensor.getID_Sala(), sensor.getPosicion(), sensor.getLectura(), sensor.getFecha()));
				showNotificaciones(sensor, tipoStaff);
				return true;
			}
			
		}
		else if (sensor.getTipo().equals("Distancia")) {
			if (sensor.getLectura() < 30) {
				sensores.add(new Sensor(sensor.getTipo(), sensor.getID_Sala(), sensor.getPosicion(), sensor.getLectura(), sensor.getFecha()));
				showNotificaciones(sensor, tipoStaff);
				return true;
			}
		}
		
		else {
			return false;
		}
		return false;
		
	}
		
		 
	
	
	private void sensoresBD() {

		
		boolean sala1 = false;
		boolean sala2 = false;
		boolean sala3 = false;
		boolean sala4 = false;
		
		
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
            		+ "        JOIN SENSORES ON HISTORIAL.TipoSensor = SENSORES.identificador\r\n;";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String tipo = rs.getString("Tipo");
				int ID_Sala = rs.getInt("ID_Sala");
				int Posicion = rs.getInt("Posicion");
				float lectura = rs.getFloat("Lectura");
				Timestamp Fecha = rs.getTimestamp("Fecha");
				
				
				Sensor sensor = new Sensor(tipo, ID_Sala, Posicion, lectura, Fecha);
				
				
				
				switch (ID_Sala) {
				
				case 1:
						checkSala(sensor);	
					break;
				case 2:
						checkSala(sensor);
					break;
				case 3:
						checkSala(sensor);
					break;
				case 4:
						checkSala(sensor);
					break;
				default:
				
				
				}
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
			   
	}
	
	public void getDatos() {
		timer_alert.scheduleAtFixedRate(new Task(), 0 ,  14000);
	}
	
	
	class Task extends TimerTask {
		
		public void run() {
			Platform.runLater(new Runnable() {
				public void run() {
					i=0;
					sensoresBD();
				}
			});


		}
	}
	
	public Timer getTimer_alert() {
		return timer_alert;
	}


	public void setTimer_alert(Timer timer_alert) {
		this.timer_alert = timer_alert;
	}
	
	public Vector<Sensor> getSensores() {
		return sensores;
	}

	public void setSensores(Vector<Sensor> sensores) {
		this.sensores = sensores;
	}

	
}
