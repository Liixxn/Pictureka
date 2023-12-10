package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Modelo.Alerta;
import Modelo.Informe;
import Modelo.Staff;
import Modelo.modelo_Museo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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
 * En esta clase se maneja la lectura y escritura de informes de parte del
 * administrador, en la vista <b>VentanaInformeAdmin</b>.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class ControladorInformeAdmin {

	@FXML
	private AnchorPane anchorPanePrincipal;

	@FXML
	private VBox VBoxPrincipal;

	@FXML
	private ImageView imgUsuario;

	@FXML
	private ImageView imgCerrarSesion;

	@FXML
	private JFXTextArea mostrarInforme;

	@FXML
	private TableView<Informe> tablaInformes;

	@FXML
	private TableColumn<Informe, String> Autor;

	@FXML
	private TableColumn<Informe, String> Titulo;

	@FXML
	private TableColumn<Informe, String> Cuerpo;

	@FXML
	private TableColumn<Informe, String> Fecha;
	
	@FXML
    private JFXComboBox<String> ComboBoxPersonal;
	
	@FXML
    private GridPane gridPaneInforme;

	@FXML
	private JFXTextField tituloInforme;

	@FXML
    private JFXTextArea cuerpoInforme;

	@FXML
	private ImageView imgFormulario;

	@FXML
	private ImageView imgEnviarInforme;

	private String usuario; // esta el usuario o mail del usuario que tiene la sesion iniciada

	boolean logged; // Este nos dira si la parsona esta logueada o no

	private String informe;

	private Vector<Informe> informes;
	
	static final String USER = "pri_Pictureka";
	static final String PASS = "asas";
	
	Alerta alert;

	/**
	 * 
	 * Constructor de la clase <b>ControladorInformeAdmin</b> que guarda la
	 * información del administrador.
	 * 
	 * @param usuario El administrador que est� iniciado sesión.
	 */
	public ControladorInformeAdmin(String usuario, Alerta alertaNoti) {
		if (usuario == "vacio") {
			logged = false;
			this.usuario = usuario;
			this.alert = alertaNoti;
		} else {
			this.usuario = usuario;
			this.alert = alertaNoti;
			logged = true;
		}

	}
	
	/**
	 * 
	 * Inicializa la ventana con un método que refresca el contenido de la tabla.
	 * 
	 */
	public void initialize() {
		refrescarTabla();
		MostrarPersonal();
	}

	@FXML
	/**
	 * 
	 * Obtiene la posición del elemento que se ha seleccionado en la tabla de informes.
	 * 
	 * @param event		Evento causado cuando el administrador pulsa sobre la tabla.
	 */
	public void clickItem(MouseEvent event) {
		// Comrpueba que la seleccion del usaurio no sea vacia
		if (!tablaInformes.getSelectionModel().isEmpty()) {
			mostrarInforme();
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

				// Se a�ade el contenido de la ventana cargada en el AnchorPane del padre
				anchorPanePrincipal.getChildren().setAll(PaneVentanaPrincipal);
				controlerPrincipal.getBarra().setStyle("-fx-background-color:  #FF8000");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	@FXML
	/**
	 * 
	 * Dirige al administrador a su ventana inicial.
	 * 
	 * @param event		Evento causado cuando el administrador pulsa sobre la imagen de volver atrás.
	 */
	void volverAtras(MouseEvent event) {
		
		alert.getTimer_alert().cancel();
		
		modelo_Museo museo = new modelo_Museo();
		
		if (museo.getRegistro().recuperar1Staff(usuario).getIdentificadorUser()==2) {
			FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/application/VentanaGuardia.fxml"));
			ControladorGuardia controlerGuardia = new ControladorGuardia(usuario);
			loaderEdit.setController(controlerGuardia);
			
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
		else if (museo.getRegistro().recuperar1Staff(usuario).getIdentificadorUser()==3) {
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
		
		
	}

	@FXML
	/**
	 * 
	 * Envia el informe escrito por el administrador, con su respectivo título y
	 * cuerpo.
	 * 
	 * @param event Evento causado cuando el administrador pulsa sobre la imagen
	 *              para enviar el informe.
	 */
	void enviarInforme(MouseEvent event) {
		
		Connection conn = null;
		Statement stmt = null;
		String sql;
		String sql2;

		Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
		Alert error = new Alert(Alert.AlertType.ERROR);
		// Se comprueba que ninguno de los dos campos se encuentren vacios
		if (!(tituloInforme.getText().isEmpty() | cuerpoInforme.getText().isEmpty())) {

			modelo_Museo museo = new modelo_Museo();
			Staff admin = museo.getRegistro().recuperar1Staff(usuario);

			// String que guarda el dni del guardia al quee se le quiere enviar el informe
			String destino = devolverDestino();
			// Se escribe el nuevo informe y se refresca la tabla
			
			try {
	            //STEP 1: Register JDBC driver
	        	Class.forName("org.mariadb.jdbc.Driver");

	            //STEP 2: Open a connection

	            conn = DriverManager.getConnection(
	                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
	            
	            //Se realiza la consulta en la tabla de CLIENTE
	            sql = "INSERT INTO INFORMES(titulo, cuerpo, destino, autor, fecha)"
	            		+ "VALUES ('"+tituloInforme.getText()+"', '"+cuerpoInforme.getText()+"', '"+destino+"', '"+admin.getNombre()+"', CURRENT_TIMESTAMP())";
	            stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery( sql );

				
				sql2 = "INSERT INTO MANEJA(Usuario, Fecha)"
						+ "VALUES('"+admin.getUsuario()+"', CURRENT_TIMESTAMP());";
				
				rs = stmt.executeQuery(sql2);
				
				rs.close();
				stmt.close();
				
				//STEP 6: Cerrando conexion.
				conn.close();
				
				confirmacion.setHeaderText("Informe guardado con éxito");
				confirmacion.showAndWait();
				refrescarTabla();
				
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

	/**
	 * 
	 * Refresca la información de la tabla, mostrando el contenido del Json.
	 * 
	 */
	private void refrescarTabla() {
		modelo_Museo museo = new modelo_Museo();
		Vector<Informe> _informes = museo.getRegistro().devolverInforme();
		
		if (museo.devolverIdentificador(usuario)==3) {
			//Elimina el contenido de la tabla
			tablaInformes.getItems().clear();
			if (_informes.size() > 0) {
				this.informes = _informes;
				//Rellena la tabla con la informacion del Json de Informes
				for (int i = (informes.size() - 1); i >= 0; i--) {
					// Se muestran los informes obtenidos en la tabla
					tablaInformes.getItems().add(informes.elementAt(i));
				}
				// Obtenemos el las diferentes columnas de la tabla y asociamos cada columna al
				// tipo de dato que queremos guardar
				Autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
				Titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
				Cuerpo.setCellValueFactory(new PropertyValueFactory<>("cuerpo"));
				Fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

			}
		}
		else if (museo.devolverIdentificador(usuario) == 2) {
			// Elimina el contenido de la tabla
			tablaInformes.getItems().clear();
			if (_informes.size() > 0) {
				this.informes = _informes;
				String usuarioDestino = museo.devolverStaff(usuario).getUsuario();
				// Rellena la tabla con la informacion del Json de Informes
				for (int i = (informes.size() - 1); i >= 0; i--) {
					if (informes.get(i).getDestino().equals(usuarioDestino) | informes.get(i).getDestino().equals("Todos")) {
						// Se muestran los informes obtenidos en la tabla
						tablaInformes.getItems().add(informes.elementAt(i));

						// Obtenemos el las diferentes columnas de la tabla y asociamos cada columna al
						// tipo de dato que queremos guardar
						Autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
						Titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
						Cuerpo.setCellValueFactory(new PropertyValueFactory<>("cuerpo"));
						Fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));	
					}

				}
			}
		}
	}
	
	/**
	 * 
	 * Muestra la información del informe seleccionado en la tabla.
	 * 
	 */
	public void mostrarInforme() {
		this.informe = "Autor:  " + tablaInformes.getSelectionModel().getSelectedItem().getAutor() + "\n" + "Fecha:  "
				+ tablaInformes.getSelectionModel().getSelectedItem().getFecha() + "\n\n" + "Titulo:  "
				+ tablaInformes.getSelectionModel().getSelectedItem().getTitulo() + "\n\n" + tablaInformes.getSelectionModel().getSelectedItem().getCuerpo();
		mostrarInforme.setText(informe);
		
	}

	private void MostrarPersonal() {
		modelo_Museo museo = new modelo_Museo();	
		Vector<Staff> personal= museo.getRegistro().getStaff();		//se recuperan el personal que se mostrara
		
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add("Todos");
		items.add("Solo administradores");
		for (Staff a : personal) {
			if(this.usuario != a.getUsuario()) {
				items.add(a.getNombre()+" "+a.getApellido1()+" - "+ a.getUsuario());
			}
		}
		ComboBoxPersonal.setItems(items);
		ComboBoxPersonal.setValue("Todos");
	}
	
	public String devolverDestino() {
		String destino = "Error";
		if (ComboBoxPersonal.getValue() == "Todos" | ComboBoxPersonal.getValue() == "Solo administradores") {
			destino = ComboBoxPersonal.getValue();
		}
		else {
			String[] datosEnvio = ComboBoxPersonal.getValue().split(" - ");
			destino = datosEnvio[1];
		}
		return destino;
	}
	
	public AnchorPane getAnchorPanePrincipal() {
		return anchorPanePrincipal;
	}

	public void setAnchorPanePrincipal(AnchorPane anchorPanePrincipal) {
		this.anchorPanePrincipal = anchorPanePrincipal;
	}

	public VBox getVBoxPrincipal() {
		return VBoxPrincipal;
	}

	public void setVBoxPrincipal(VBox vBoxPrincipal) {
		VBoxPrincipal = vBoxPrincipal;
	}

	public ImageView getImgUsuario() {
		return imgUsuario;
	}

	public void setImgUsuario(ImageView imgUsuario) {
		this.imgUsuario = imgUsuario;
	}

	public ImageView getImgCerrarSesion() {
		return imgCerrarSesion;
	}

	public void setImgCerrarSesion(ImageView imgCerrarSesion) {
		this.imgCerrarSesion = imgCerrarSesion;
	}

	public TableColumn<Informe, String> getAutor() {
		return Autor;
	}

	public void setAutor(TableColumn<Informe, String> autor) {
		Autor = autor;
	}

	public TableColumn<Informe, String> getTitulo() {
		return Titulo;
	}

	public void setTitulo(TableColumn<Informe, String> titulo) {
		Titulo = titulo;
	}

	public TableColumn<Informe, String> getCuerpo() {
		return Cuerpo;
	}

	public void setCuerpo(TableColumn<Informe, String> cuerpo) {
		Cuerpo = cuerpo;
	}

	public TableColumn<Informe, String> getFecha() {
		return Fecha;
	}

	public void setFecha(TableColumn<Informe, String> fecha) {
		Fecha = fecha;
	}

	public JFXTextField getTituloInforme() {
		return tituloInforme;
	}

	public void setTituloInforme(JFXTextField tituloInforme) {
		this.tituloInforme = tituloInforme;
	}

	public JFXTextArea getCuerpoInforme() {
		return cuerpoInforme;
	}

	public void setCuerpoInforme(JFXTextArea cuerpoInforme) {
		this.cuerpoInforme = cuerpoInforme;
	}

	public ImageView getImgFormulario() {
		return imgFormulario;
	}

	public void setImgFormulario(ImageView imgFormulario) {
		this.imgFormulario = imgFormulario;
	}

	public ImageView getImgEnviarInforme() {
		return imgEnviarInforme;
	}

	public void setImgEnviarInforme(ImageView imgEnviarInforme) {
		this.imgEnviarInforme = imgEnviarInforme;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

	public Vector<Informe> getInformes() {
		return informes;
	}

	public void setInformes(Vector<Informe> informes) {
		this.informes = informes;
	}
	
	public GridPane getGridPaneInforme() {
		return gridPaneInforme;
	}

	public void setGridPaneInforme(GridPane gridPaneInforme) {
		this.gridPaneInforme = gridPaneInforme;
	}
	
	
	

}