package Modelo;

import java.time.LocalDate;

/**
 * 
 * En esta clase se almacena la informacion de los administradores
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class Administrador extends Staff { 

	/**
	 * Constructor de administrador
	 * 
	 * @param usuario Usuario asignado al administrador
	 * @param dni DNI asignado al administrador
	 * @param email email asignado al administrador
	 * @param contrasenia contrasenia asignado al administrador
	 * @param fechaNacimiento fechaNacimiento asignado al administrador
	 * @param nombre nombre asignado al administrador
	 * @param apellido1 apellido1 asignado al administrador
	 * @param apellido2 apellido2 asignado al administrador
	 */
	public Administrador(String usuario, String dni, String email, String contrasenia, LocalDate fechaNacimiento , String nombre, String apellido1, String apellido2) { 
		super(3,usuario, nombre, apellido1, apellido2, dni, email, contrasenia,fechaNacimiento);
	}



	//M�todos
	

}
