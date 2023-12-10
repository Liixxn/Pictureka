package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 
 * Esta clase maneja el inicio de sesión o registro de usuarios, edicción de datos
 * 
 */

public class Registro {
	private Vector <Cliente> usuarios;
	private Vector<Staff> staff;
	private Vector<Informe> informes;
	
	
	 static final String USER = "pri_Pictureka";
	 static final String PASS = "asas";
	
	public Registro () {
		Vector <Cliente> _usuarios = new Vector<Cliente>();
		Vector <Staff> _staff = new Vector<Staff>();
		Vector<Informe> _informes = new Vector<Informe>();
		this.usuarios = _usuarios;
		this.staff = _staff;
		this.informes = _informes;
	}
	
	/**
	 * aqui se maneja el registro del cliente, mediante sus datos 
	 * @param usuario
	 * @param dni
	 * @param email
	 * @param Contrasenia
	 * @param fechaNacimiento
	 * @return String
	 */
	
	public String registrarCliente(String usuario, String dni, String email, String contrasenia, LocalDate fechaNacimiento) {
		//Conectar con la base de datos 
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		//cuando esta a false significa que no hay datos iguales al que se esta registrando
		boolean correcto = false;
		if (validarEmail(email)) { // devuelve true si el email es valid
			try{
				//credenciales
				conn = DriverManager.getConnection(
						"jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
			    //consulta que comprueba que el usuario no se repita, el email no se repita, dni no se repita entre 
				//el mismo tipo de usuario
			    sql = "SELECT * FROM CLIENTE "
			    		+ "WHERE CLIENTE.Usuario = '"+usuario+"' OR CLIENTE.Email = '"+email+"' OR CLIENTE.Dni = '"+dni+"'";
		        stmt = conn.createStatement();
				ResultSet respuestaCliente = stmt.executeQuery( sql );
				if(respuestaCliente.first()) {
					//significa que ya alguno de los datos introducitos ya esta registrado
					correcto = true;
				}
				respuestaCliente.close();
				stmt.close();
				if (correcto == false) {
					//consulta que comprueba que el usuario no se repita, el email no se repita, dni no se repita entre 
					//el mismo tipo de usuario
					sql = "SELECT * FROM STAFF "
							+ "WHERE STAFF.Usuario = '"+usuario+"' OR STAFF.Email = '"+email+"'";
			        stmt = conn.createStatement();
					ResultSet respuestaStaff = stmt.executeQuery( sql );
					if(respuestaStaff.first()) {
						//significa que ya alguno de los datos introducitos ya esta registrado
						correcto = true;
					}
					respuestaStaff.close();
					stmt.close();
				}
				//inserta el usuario en caso de que los datos no se repitan
				if (correcto == false) {
					sql = "INSERT INTO `CLIENTE` (`Usuario`, `identificadorUser`, `Dni`, "
		            		+ "`FechaNacimiento`, `Email`, `Contraseña`) VALUES ('"+usuario+"', "+ 1 +", '"+ dni +"', '"+ fechaNacimiento +"', "
		            		+ "'"+ email +"', '"+ contrasenia +"')";
		            stmt = conn.createStatement();
		            stmt.executeUpdate(sql);  
		            stmt.close();
		            usuario = "Validacion completada con exito";
				}
				else {
					usuario = "Datos ya registrado";
				}
				
				//Registrar el nuevo cliente
				
			} catch (SQLException se) {
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
			usuario = "El email introducido no es valido";
		}
		return usuario;
	}
	    
	
	/**
	 * aqui se maneja el registro de los administradores, mediante las siguientes comprobaciones
	 * @param usuario
	 * @param dni
	 * @param email
	 * @param contrasenia
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param fechaNacimiento
	 * @return
	 */
	public String registrarStaff(String usuario, String dni, String email, String contrasenia, String nombre, String apellido1,
		String apellido2, LocalDate fechaNacimiento, int identificador) {
		//Conectar con la base de datos 
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		//cuando esta a false significa que no hay datos iguales al que se esta registrando
		boolean correcto = false;
		if(dni.length()==9) {
			if (usuario.length() <= 10) {
				
			if (validarEmail(email)) { // devuelve true si el email es valid
				try{
					//credenciales
					conn = DriverManager.getConnection(
							"jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
				    //consulta que comprueba que el usuario no se repita, el email no se repita, dni no se repita entre 
					//el mismo tipo de usuario
				    sql = "SELECT * FROM CLIENTE "
				    		+ "WHERE CLIENTE.Usuario = '"+usuario+"' OR CLIENTE.Email = '"+email+"'";
			        stmt = conn.createStatement();
					ResultSet respuestaCliente = stmt.executeQuery( sql );
					if(respuestaCliente.first()) {
						//significa que ya alguno de los datos introducitos ya esta registrado
						correcto = true;
					}
					respuestaCliente.close();
					stmt.close();
					if (correcto == false) {
						//consulta que comprueba que el usuario no se repita, el email no se repita, dni no se repita entre 
						//el mismo tipo de usuario
						sql = "SELECT * FROM STAFF "
								+ "WHERE STAFF.Usuario = '"+usuario+"' OR STAFF.Email = '"+email+"' OR STAFF.Dni = '"+dni+"'";
				        stmt = conn.createStatement();
						ResultSet respuestaStaff = stmt.executeQuery( sql );
						if(respuestaStaff.first()) {
							//significa que ya alguno de los datos introducitos ya esta registrado
							correcto = true;
						}
						respuestaStaff.close();
						stmt.close();
					}
					//inserta el usuario en caso de que los datos no se repitan
					if (correcto == false) {
						sql = "INSERT INTO `STAFF` (`Usuario`, `Nombre`, `Apellido1`, `Apellido2`, `identificadorUser`,"
								+ " `Dni`, `FechaNacimiento`, `Email`, `Contraseña`) VALUES ('"+usuario+"','"+nombre+"','"+apellido1+"',"
								+ "'"+apellido2+"', "+ identificador +", '"+ dni +"', '"+ fechaNacimiento +"', "
			            		+ "'"+ email +"', '"+ contrasenia +"')";
			            stmt = conn.createStatement();
			            stmt.executeUpdate(sql);  
			            stmt.close();
			            usuario = "Validacion completada con exito";
					}
					else {
						usuario = "Datos ya registrado";
					}
					
					//Registrar el nuevo cliente
					
				} catch (SQLException se) {
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
				usuario = "El email introducido no es valido";
			}
			}else {
				usuario = "La longitud del Usuario no es válida";
			}
		}
		else{
			usuario = "La longitud del DNI introducido no es valida";
		}
		return usuario;
	}
	
	
	public Cliente loginDeUsuarios(String emailOUsuario, String contrasenia) {
		
		
		Connection conn = null;
        Statement stmt = null;
        String sql;
        Cliente cliente = new Cliente(0, "vacio", "", "", "", null);
        
        try {
            //STEP 1: Register JDBC driver
        	Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            
            //Se realiza la consulta en la tabla de CLIENTE
            sql = "SELECT * FROM CLIENTE "
            		+ " WHERE (CLIENTE.Usuario = '"+emailOUsuario+"'OR CLIENTE.Email = '"+emailOUsuario+"') AND CLIENTE.Contraseña = '"+contrasenia+"'";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String Usuario = rs.getString("Usuario");
				int identificadorCliente = rs.getInt("identificadorUser");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String Contrasenia = rs.getString("Contraseña");
				
				Date fechaNacimeinto = rs.getDate("FechaNacimiento");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaNacimeinto);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));
				
				cliente.setIdentificadorCliente(identificadorCliente);
				cliente.setUsuario(Usuario);
				cliente.setDni(dni);
				cliente.setEmail(email);
				cliente.setContrasenia(Contrasenia);
				cliente.setFechaNacimiento(fecha);
					
			}
			

			rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
			conn.close();     
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
		
		
		
		return cliente;
		
	}
	
	
	public Staff loginStaff(String emailOUsuario, String contrasenia) {
	
		Connection conn = null;
        Statement stmt = null;
        String sql;
        Staff staff = new Staff(0, "vacio", "", "", "", "", "", "", null);
        
        try {
            //STEP 1: Register JDBC driver
        	Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            
			//Si no se ha encontrado en la de CLIENTE se busca en la de STAFF
			sql = "SELECT * FROM STAFF "
            		+ " WHERE (STAFF.Usuario = '"+emailOUsuario+"' OR STAFF.Email = '"+emailOUsuario+"') AND STAFF.Contraseña = '"+contrasenia+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			rs = stmt.executeQuery(sql);
			
			
			while ( rs.next() ) {
				String Usuario = rs.getString("Usuario");
				String nombre = rs.getString("Nombre");
				String apellido1 = rs.getString("Apellido1");
				String apelldio2 = rs.getString("Apellido2");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String Contrasenia = rs.getString("Contraseña");
				int identificadorUser = rs.getInt("identificadorUser");
				
				Date fechaNacimeinto = rs.getDate("FechaNacimiento");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaNacimeinto);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));

				
				
				staff.setIdentificadorUser(identificadorUser);
				staff.setUsuario(Usuario);
				staff.setNombre(nombre);
				staff.setApellido1(apellido1);
				staff.setApellido2(apelldio2);
				staff.setDni(dni);
				staff.setEmail(email);
				staff.setContrasenia(Contrasenia);
				staff.setFechaNacimiento(fecha);
				
			}

			rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
			conn.close();     
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
		
		
		
		return staff;
		
		
	}
	
	
	
  /**
   * compruebamos el email
   * @param email
   * @return boolean
   */
	public boolean validarEmail(String email) {
		// Patr�n para validar el email
		boolean comprobar = false;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            comprobar = true;
        }
        return comprobar;
	}
	/**
	 * en el caso que se repita el email
	 * @param email
	 * @return boolean
	 */
	public boolean emailRepetido(String email) { 
		boolean noRepetido = true;
		int contador = 0;
		while (noRepetido != false && contador < usuarios.size()) {
			if (usuarios.elementAt(contador).getEmail().equals(email)) {
				noRepetido = false;	
			}
			contador++;
		}
		return noRepetido;
	}
	/**
	 * cuando se repite el email del staff
	 * @param email
	 * @return boolean
	 */
	public boolean emailRepetidoStaff(String email) {
		boolean noRepetido = true;
		int contador = 0;
		while (noRepetido != false && contador < staff.size()) {
			if (staff.elementAt(contador).getEmail().equals(email)) {
				noRepetido = false;	
			}
			contador++;
		}
		return noRepetido;
	}
	
	/**
	 * cuando se introduce un usuario repetido
	 * @param usuario
	 * @return boolean
	 */
	public boolean usuarioRepetido(String usuario) {
		boolean noRepetido = true;
		int contador = 0;
		while (noRepetido != false && contador < usuarios.size()) {
			if (usuarios.elementAt(contador).getUsuario().equals(usuario)) {
				noRepetido = false;	
			}
			contador++;
		}
		return noRepetido;
	}
	/**
	 * Funcion para cuando se introduzca un staff repetido
	 * @param staffs
	 * @return
	 */
	public boolean staffRepetido(String staffs) {
		boolean noRepetido = true;
		int contador = 0;
		while (noRepetido != false && contador < staff.size()) {
			if (staff.elementAt(contador).getUsuario().equals(staffs)) {
				noRepetido = false;	
			}
			contador++;
		}
		return noRepetido;
	}
	
	/**
	 * Funcion para evitar que se introduzca un email repetido
	 * @param dni
	 * @return
	 */
	public boolean dniRepetido(String dni) {
		
		boolean noRepetido = true;
		int contador = 0;
		while (noRepetido != false && contador < usuarios.size()) {
			if (usuarios.elementAt(contador).getDni().equals(dni)) {
				noRepetido = false;	
			}
			contador++;
		}

		return noRepetido;
	}
	
	public boolean dniStaffRepetido(String dni) {
		
		boolean noRepetido = true;
		int contador = 0;
		while (noRepetido != false && contador < staff.size()) {
			if (staff.elementAt(contador).getDni().equals(dni)) {
				noRepetido = false;	
			}
			contador++;
		}

		return noRepetido;
	}
	
	
	
	public int rDevolderIdentificador(String usuario) {
		
		int identificador = 0;
		
		
		if (recuperar1Cliente(usuario).getIdentificadorCliente()==1) {
			identificador = 1;
		}
		else {
			if (recuperar1Staff(usuario).getIdentificadorUser()==2) {
				identificador = 2;
			}
			else if (recuperar1Staff(usuario).getIdentificadorUser()==3) {
				identificador = 3;
			}
		}
		
		return identificador;
	}
	
	public Cliente rDevolverCliente(String usuario) {
		recuperarClientes();
		Cliente cli = recuperar1Cliente(usuario);
		return cli;
	}

	public Staff rDevolderStaff(String usuario) {
		recuperarStaff();
		Staff sta = recuperar1Staff(usuario);
		return sta;
	}
	
	public String rDevolverNombreStaff(String usuario) {
		recuperarStaff();
		Staff sta = recuperar1Staff(usuario);
		return sta.getNombre();
	}
	
	
	
	
	public Vector<Staff> recuperarStaff() {

		Vector<Staff> staffs = new Vector<Staff>();
		
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
            sql = "SELECT * FROM STAFF";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String Usuario = rs.getString("Usuario");
				String nombre = rs.getString("Nombre");
				String apellido1 = rs.getString("Apellido1");
				String apellido2 = rs.getString("Apellido2");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String Contrasenia = rs.getString("Contraseña");
				int identificadorUser = rs.getInt("identificadorUser");
				
				Date fechaNacimeinto = rs.getDate("FechaNacimiento");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaNacimeinto);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));

				staffs.add(new Staff(identificadorUser, Usuario, nombre, apellido1, apellido2, dni, email, Contrasenia, fecha));
				
			}
			

			rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
			conn.close();     
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
		this.staff = staffs;
		return staffs;
	}
	
	
	public Cliente recuperar1Cliente(String usuario) {

		Cliente cli = new Cliente(0, usuario, usuario, usuario, usuario, null);
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
            sql = "SELECT * FROM CLIENTE "
            		+ " WHERE CLIENTE.Usuario = '"+usuario+"'";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String Usuario = rs.getString("Usuario");

				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String Contrasenia = rs.getString("Contraseña");
				int identificadorUser = rs.getInt("identificadorUser");
				Date fechaNacimeinto = rs.getDate("FechaNacimiento");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaNacimeinto);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));
				
				
				cli.setUsuario(Usuario);
				cli.setIdentificadorCliente(identificadorUser);
				cli.setDni(dni);
				cli.setEmail(email);
				cli.setContrasenia(Contrasenia);
				cli.setFechaNacimiento(fecha);
					
			}
			

			rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
			conn.close();     
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
		return cli;
	}
	
	public Vector<Cliente> recuperarClientes() {
		Vector<Cliente> Clientes = new Vector<Cliente>();
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
            sql = "SELECT * FROM CLIENTE";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String Usuario = rs.getString("Usuario");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String Contrasenia = rs.getString("Contraseña");
				int identificadorCliente = rs.getInt("identificadorUser");
				Date fechaNacimiento = rs.getDate("FechaNacimiento");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaNacimiento);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));
				
				Clientes.add(new Cliente(Usuario, dni, email, Contrasenia, fecha));
			}
			 rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
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
		 
		        
		   this.usuarios = Clientes;
	   return Clientes;
	}
	
	
	
	
	
	
	public Staff recuperar1Staff(String usuario) {
		
		Staff sta = new Staff(0, usuario, usuario, usuario, usuario, usuario, usuario, usuario, null);
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
            sql = "SELECT * FROM STAFF "
            		+ " WHERE STAFF.Usuario = '"+usuario+"'";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				String Usuario = rs.getString("Usuario");
				String nombre = rs.getString("Nombre");
				String apellido1 = rs.getString("Apellido1");
				String apelldio2 = rs.getString("Apellido2");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String Contrasenia = rs.getString("Contraseña");
				int identificadorUser = rs.getInt("identificadorUser");
				
				Date fechaNacimeinto = rs.getDate("FechaNacimiento");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaNacimeinto);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));

				
				sta.setIdentificadorUser(identificadorUser);
				sta.setUsuario(Usuario);
				sta.setNombre(nombre);
				sta.setApellido1(apellido1);
				sta.setApellido2(apelldio2);
				sta.setDni(dni);
				sta.setEmail(email);
				sta.setContrasenia(Contrasenia);
				sta.setFechaNacimiento(fecha);
			}
			

			rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
			conn.close();     
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
        
		return sta;
	}

	public Vector<Informe> devolverInforme() {
		recuperarInformes();
		return this.informes;
	}
	


	private Vector<Informe> recuperarInformes() {
		
		Vector<Informe> _informes = new Vector<Informe>();
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
            sql = "SELECT * FROM INFORMES";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				int id = rs.getInt("id_informe");
				String titulo = rs.getString("titulo");
				String cuerpo = rs.getString("cuerpo");
				String destino = rs.getString("destino");
				String autor = rs.getString("autor");
				Timestamp fecha = rs.getTimestamp("fecha");
				
				SimpleDateFormat df = new SimpleDateFormat("YYYY.MM.dd HH:mm:ss");
			    String time = df.format(fecha);

				_informes.add(new Informe(id, autor, titulo, destino, cuerpo, time));
				
			}
			

			rs.close();
			stmt.close();
			
			//STEP 6: Cerrando conexion.
			conn.close();     
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
		this.informes = _informes;
		return _informes;
	}

	public Vector<Staff> getStaff() {
		recuperarStaff();
		return staff;
	}



	
}
