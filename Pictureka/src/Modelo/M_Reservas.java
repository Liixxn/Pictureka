package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class M_Reservas {
	
	static final String USER = "pri_Pictureka";
	
    static final String PASS = "asas";
    
    Connection conn = null;
    
    Statement stmt = null;
    
    String sql;

	public M_Reservas() {
		
	}

	public void eliminarReserva(int idEliminar) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            sql = "UPDATE RESERVA"
            	+ " SET visible = 0 "
            	+ " WHERE identificador = "+idEliminar+";";

           
            stmt = conn.createStatement();
   			ResultSet rs = stmt.executeQuery( sql );
   			
   			stmt.close();
   			rs.close();
		}catch (SQLException se) {
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
		
	}
	
	public Vector<Reserva> recuperarReserva () {
		Vector<Reserva> Reservas = new Vector<Reserva>();
		
		try {
            //STEP 1: Register JDBC driver
        	Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            
            //Se realiza la consulta en la tabla de CLIENTE
            sql = "SELECT * FROM RESERVA Order by RESERVA.identificador asc";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				int identificador = rs.getInt("identificador");
				int num_ticket = rs.getInt("num_ticket");
				String id_duenio = rs.getString("id_duenio");
				Date day =rs.getDate("fecha");
				String revisor = rs.getString("revisor");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(day);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));
				
				
				Time hora = rs.getTime("hora");
				LocalTime time = hora.toLocalTime();

				
				Reservas.add(new Reserva(identificador, num_ticket, id_duenio, fecha, time, revisor));

				
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
		
		return Reservas;
			   
	}
	
	
	public int visualizarVisibilidad (int identificador) {
		
		int visible = -1;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            sql = "SELECT visible  FROM RESERVA"
            	+ " WHERE identificador = "+identificador+";";

            stmt = conn.createStatement();
   			ResultSet rs = stmt.executeQuery( sql );
   			while ( rs.next() ) {
   				visible = rs.getInt("visible");
   			}
   						
   			stmt.close();
   			rs.close();
		}catch (SQLException se) {
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
		
		return visible;
	}

	public void registrarReserva (int identificador, int num_ticket, LocalDate fecha, LocalTime hora, String id_duenio, String revisor) {
        
        try {
            //STEP 1: Register JDBC driver
        	Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            
        //el 1 de visible significa true
		 sql = "INSERT INTO `RESERVA` (`identificador`, `num_ticket`, `fecha`, `hora`, `id_duenio`, `revisor`, `visible`) "
				+ "VALUES ("+identificador+","+num_ticket+",'"+fecha+"', '"+hora+"', '"+id_duenio+"', "+revisor+", "+1+");"; 
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
			
        }catch (SQLException se) {
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
        
	}
	
	public Vector<Reserva> buscarReserva (String usuario) {
		Vector<Reserva> Reservas = new Vector<Reserva>();
		
		try {
            //STEP 1: Register JDBC driver
        	Class.forName("org.mariadb.jdbc.Driver");

            //STEP 2: Open a connection

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
            
            //Se realiza la consulta en la tabla de CLIENTE
            sql = "SELECT * FROM RESERVA "
                  + "WHERE (RESERVA.id_duenio ='"+usuario+ "' AND RESERVA.visible = 1);";
            stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				int identificador = rs.getInt("identificador");
				int num_ticket = rs.getInt("num_ticket");
				String id_duenio = rs.getString("id_duenio");
				Date day =rs.getDate("fecha");
				String revisor = rs.getString("revisor");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(day);
				LocalDate fecha = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.DATE));
				
				
				Time hora = rs.getTime("hora");
				LocalTime time = hora.toLocalTime();

				
				Reservas.add(new Reserva(identificador, num_ticket, id_duenio, fecha, time, revisor));

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
		
		return Reservas;
		
	}

	public void establecerRevisor(String usuario, int id_reserva) {
	
		try {
			// STEP 1: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");
	
			// STEP 2: Open a connection
	
			conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priPictureka", USER, PASS);
	
			// Se realiza la consulta en la tabla de CLIENTE
			sql = "UPDATE RESERVA SET `revisor`='"+usuario+"' "
					+ "WHERE  `identificador`="+id_reserva+"";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
	
			rs.close();
			stmt.close();
	
			// STEP 6: Cerrando conexion.
			conn.close();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
}
