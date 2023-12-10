package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * Almacena la informacion de los eventos y la direccion de la imagen para poner en el evento
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class Evento {
	//Atributos
	
	private int identificador = 0;
	private String nombre;
	private String imagen;
	private String informacion;
	
	//cosas BBDD
	static final String USER = "pri_Pictureka";
    static final String PASS = "asas";
    Connection conn = null;
    Statement stmt = null;
    String sql;
	
	//Constructor
	/**
	 * Constructor del evento a mostrar 
	 * 
	 * @param identificador para saber la posicion en la que se debe mostrar
	 * @param nombre nombre del evento
	 * @param imagen imagen del evento a mostrar
	 * @param informacion informacion del evento
	 */
	public Evento(int identificador, String nombre, String imagen, String informacion) {
		super();
		this.identificador = identificador;
		this.nombre = nombre;
		this.imagen = imagen;
		this.informacion = informacion;
	}
	
	public void obtenerDatosEventoBBDD(int id) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            sql = "SELECT * FROM EVENTOS WHERE EVENTOS.identificador ='"+id+"';";
            //System.out.println("sql Select: "+sql);
            stmt = conn.createStatement();
   			ResultSet rs = stmt.executeQuery( sql );
   			while ( rs.next() ) {
   				this.nombre = rs.getString("nombre");
   				this.informacion = rs.getString("informacion");
   				this.imagen = rs.getString("imagen");
   			}
   			stmt.close();
   			rs.close();
   		}
		catch(SQLException | ClassNotFoundException e){
			
		}
            
		}
		 
	
	
	//getters y setters
	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	
	
	
	
}
