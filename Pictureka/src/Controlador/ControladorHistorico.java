package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import Modelo.Alerta;
import Modelo.Sensor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
//import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ControladorHistorico {
	
	static final String USER = "pri_Pictureka";
	
    static final String PASS = "asas";


	    @FXML
	    private AnchorPane anchorPanePrincipal;

	    @FXML
	    private VBox VBoxEditAdmin;

	    @FXML
	    private JFXToolbar ToolBarAdmin;


		@FXML
	    private ImageView imgAvatar;

	    @FXML
	    private ImageView imgCerrarSesion;

	    @FXML
	    private GridPane gridPaneMaster;

	    @FXML
	    private TableView<Sensor> tableViewHistorico;

	    @FXML
	    private TableColumn<Sensor, String> tipoTabla;

	    @FXML
	    private TableColumn<Sensor, Integer> salaSensor;

	    @FXML
	    private TableColumn<Sensor, Integer> posicionTabla;

	    @FXML
	    private TableColumn<Sensor, Float> lecturaTabla;

	    @FXML
	    private TableColumn<Sensor, Timestamp> timestampTabla;
	    
	    final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
	    
	    @FXML
	    private LineChart<String, Float> graficaLineal;
	    
	    @FXML
	    private LineChart<String, Float> graficaLinealLuz;

	    @FXML
	    private Button btnRegresar;

	    @FXML
	    private GridPane gridPaneEdit;

	    @FXML
	    private JFXButton btnActualizar;
	    
	    private String usuario;		//esta el usuario o mail del usuario que tiene la sesion iniciada
	    
	    private String tipoStaff;
	    
	    Alerta alertNotificaciones;
		
	    boolean logged; //Este nos dira si la parsona esta logueada o no
	    
	    public ControladorHistorico(String usuario, String _tipoStaff, Alerta alert) {
			 if (usuario == "vacio") {
				 logged = false;
				 this.usuario = usuario;
				 this.alertNotificaciones = alert;
			 }
			 else {
				 this.usuario = usuario;
				 logged = true;
				 tipoStaff =_tipoStaff;
				 this.alertNotificaciones = alert;
			 }
			
		}
	    
	    
	    @FXML
	    public void initialize(){
	    	//Dependiendo del usuario que se encuentre iniciado sesion se muestra una u otro avatar
			if (tipoStaff.equals("Guardia")) {
				imgAvatar.setImage(new Image("/guardiaAvatar.png"));
			}
			else {
				imgAvatar.setImage(new Image("/administradorAvatar.png"));
			}
		 
	    	cargarTabla();
	    	sensoresAGraficaTemperatura();
	    	sensoresAGraficaLuz();
	    	
	    	
	    }

	    @FXML
	    void actualizarTabla(ActionEvent event) {
	    	cargarTabla();
	    	graficaLineal.getData().remove(0);
	    	graficaLinealLuz.getData().remove(0);
	    	sensoresAGraficaTemperatura();
	    	sensoresAGraficaLuz();
	    }

	    @FXML
	    void cerrarSesion(MouseEvent event) {
	    	
	    	alertNotificaciones.getTimer_alert().cancel();
	    	
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
	    void clickAdministrador(MouseEvent event) {
	    	
	    }

	    @FXML
	    void regresar(ActionEvent event) {
	    	
	    	alertNotificaciones.getTimer_alert().cancel();
	    	
	    	
	    	if(tipoStaff.equals("Guardia")) {
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
					anchorPanePrincipal.getChildren().clear();
		        	
		        	//Se ajusta el AnchorPane para que sea escalable
		            AnchorPane.setTopAnchor(PaneGuardia, 0.0);
		            AnchorPane.setRightAnchor(PaneGuardia, 0.0);
		            AnchorPane.setLeftAnchor(PaneGuardia, 0.0);
		            AnchorPane.setBottomAnchor(PaneGuardia, 0.0);
		            

		            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
		            anchorPanePrincipal.getChildren().setAll(PaneGuardia);
		            
		           
		            
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			else {
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
					anchorPanePrincipal.getChildren().clear();
		        	
		        	//Se ajusta el AnchorPane para que sea escalable
		            AnchorPane.setTopAnchor(PaneAdministrador, 0.0);
		            AnchorPane.setRightAnchor(PaneAdministrador, 0.0);
		            AnchorPane.setLeftAnchor(PaneAdministrador, 0.0);
		            AnchorPane.setBottomAnchor(PaneAdministrador, 0.0);
		            

		            //Se añade el contenido de la ventana cargada en el AnchorPane del padre
		            anchorPanePrincipal.getChildren().setAll(PaneAdministrador);
		            
		           
		            
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}

	    }

	    @FXML
	    void verPerfil(MouseEvent event) {
	    	
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
                
                controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");

                //Se añade el contenido de la ventana cargada en el AnchorPane del padre
                anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);
                
               
                
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
	    }
	    
	    
	    public void cargarTabla() {
	    	Vector<Sensor> sensores = getSensores();
			tableViewHistorico.getItems().clear();
			// Se leen los datos del Json del staff
			for (int i = 0; i < sensores.size(); i++) {
				// Obtiene solo el personal con numero de identificacion 2

					// Se muestran los guardias obtenidos en la tabla
				tableViewHistorico.getItems()
							.add(new Sensor(sensores.get(i).getTipo(), sensores.get(i).getID_Sala(), sensores.get(i).getPosicion(), sensores.get(i).getLectura(), sensores.get(i).getFecha()));
				
			}

			// Obtenemos el las diferentes columnas de la tabla y asociamos cada columna al
			// tipo de dato que queremos guardar
			tipoTabla.setCellValueFactory(new PropertyValueFactory<>("tipo"));
			salaSensor.setCellValueFactory(new PropertyValueFactory<>("ID_Sala"));
			posicionTabla.setCellValueFactory(new PropertyValueFactory<>("Posicion"));
			lecturaTabla.setCellValueFactory(new PropertyValueFactory<>("lectura"));
			timestampTabla.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
			
	    }
	    
	    
	    public Vector<Sensor> getSensores(){
	    	Vector<Sensor> sensores = new Vector<Sensor>();
	    	
	    	Connection conn = null;
	        Statement stmt = null;
	        String sql;
	    	
	    	try {
	            //STEP 1: Register JDBC driver
	        	Class.forName("org.mariadb.jdbc.Driver");

	            //STEP 2: Open a connection
	            conn = DriverManager.getConnection(
	                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
	            
	            //Se realiza la consulta en la tabla de CLIENTE
	            sql = "SELECT HISTORIAL.*, SENSORES.Tipo, SENSORES.ID_Sala, SENSORES.Posicion"
	            		+ "	FROM HISTORIAL "
	            		+ "		JOIN SENSORES ON HISTORIAL.TipoSensor = SENSORES.identificador";
	            stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery( sql );
				while ( rs.next() ) {
					String tipo = rs.getString("tipo");
					int ID_Sala = rs.getInt("ID_Sala");
					int Posicion = rs.getInt("Posicion");
					float lectura = rs.getFloat("lectura");
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
	    
	    
	    public void sensoresAGraficaTemperatura() {
	    	
	    	graficaLineal.setTitle("Media de temperatura diaria");
	    	XYChart.Series series = new XYChart.Series();
	    	float dom = 0, lun=0, mar=0, mie=0, jue=0, vie=0, sab=0;
	    	int dom_ = 0, lun_=0, mar_=0, mie_=0, jue_=0, vie_=0, sab_=0;
	    	int j=0;
	    	
	    	Vector<Sensor> sensores = getSensores();
	    	
	    	for(int i=0; i<sensores.size();i++) {
	    		if(sensores.get(i).getTipo().equals("Temperatura")) {
	    			j=sensores.get(i).getFecha().getDay();
		    		switch(j) {
		    		case 0:
		    			dom+=sensores.get(i).getLectura();
		    			dom_++;
		    			break;
		    		case 1:
		    			lun+=sensores.get(i).getLectura();
		    			lun_++;
		    			break;
		    		case 2:
		    			mar+=sensores.get(i).getLectura();
		    			mar_++;
		    			break;
		    		case 3:
		    			mie+=sensores.get(i).getLectura();
		    			mie_++;
		    			break;
		    		case 4:
		    			jue+=sensores.get(i).getLectura();
		    			jue_++;
		    			break;
		    		case 5:
		    			vie+=sensores.get(i).getLectura();
		    			vie_++;
		    			break;
		    		case 6:
		    			sab+=sensores.get(i).getLectura();
		    			sab_++;
		    			break;
		    		
		    		}
	    		}
	    	}
	    	series.setName("Temperatura");
	    	series.getData().add(new XYChart.Data("Dom", (dom/dom_)));
	        series.getData().add(new XYChart.Data("Lun", (lun/lun_)));
	        series.getData().add(new XYChart.Data("Mar", (mar/mar_)));
	        series.getData().add(new XYChart.Data("Mie", (mie/mie_)));
	        series.getData().add(new XYChart.Data("Jue", (jue/jue_)));
	        series.getData().add(new XYChart.Data("Vie", (vie/vie_)));
	        series.getData().add(new XYChart.Data("Sab", (sab/sab_)));
	        
	    	
	    	
	        graficaLineal.getData().add(series);
	        
	        
	    }
	    
	    
	    public void sensoresAGraficaLuz() {
	    	
	    	graficaLinealLuz.setTitle("Media de luminidad diaria");
	    	XYChart.Series series = new XYChart.Series();
	    	float dom = 0, lun=0, mar=0, mie=0, jue=0, vie=0, sab=0;
	    	int dom_ = 0, lun_=0, mar_=0, mie_=0, jue_=0, vie_=0, sab_=0;
	    	int j=0;
	    	
	    	Vector<Sensor> sensores = getSensores();
	    	for(int i=0; i<sensores.size();i++) {
	    		if(sensores.get(i).getTipo().equals("Luz")) {
	    			j=sensores.get(i).getFecha().getDay();
		    		switch(j) {
		    		case 0:
		    			dom+=sensores.get(i).getLectura();
		    			dom_++;
		    			break;
		    		case 1:
		    			lun+=sensores.get(i).getLectura();
		    			lun_++;
		    			break;
		    		case 2:
		    			mar+=sensores.get(i).getLectura();
		    			mar_++;
		    			break;
		    		case 3:
		    			mie+=sensores.get(i).getLectura();
		    			mie_++;
		    			break;
		    		case 4:
		    			jue+=sensores.get(i).getLectura();
		    			jue_++;
		    			break;
		    		case 5:
		    			vie+=sensores.get(i).getLectura();
		    			vie_++;
		    			break;
		    		case 6:
		    			sab+=sensores.get(i).getLectura();
		    			sab_++;
		    			break;
		    		
		    		}
	    		}
	    	}
	    	series.setName("Luminidad");
	    	
	    	series.getData().add(new XYChart.Data("Dom", (dom/dom_)));
	        series.getData().add(new XYChart.Data("Lun", (lun/lun_)));
	        series.getData().add(new XYChart.Data("Mar", (mar/mar_)));
	        series.getData().add(new XYChart.Data("Mie", (mie/mie_)));
	        series.getData().add(new XYChart.Data("Jue", (jue/jue_)));
	        series.getData().add(new XYChart.Data("Vie", (vie/vie_)));
	        series.getData().add(new XYChart.Data("Sab", (sab/sab_)));
	        
	    	
	    	
	        graficaLinealLuz.getData().add(series);
	        
	    }
	    
	    
	    
	    public JFXToolbar getToolBarAdmin() {
				return ToolBarAdmin;
			}

			public void setToolBarAdmin(JFXToolbar toolBarAdmin) {
				ToolBarAdmin = toolBarAdmin;
			}

	}


