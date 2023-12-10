package Modelo;

import java.time.LocalDate;

/**
 * 
 * En esta clase se almacena la informacion de un Guardia
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class Guardia extends Staff{
	

	//private int salaActual;
	//Atributo del horario de trabajo
	
	//Constructores

	/**
	 * Constructor de guardia
	 * 
	 * @param usuario usario designado al guardia
	 * @param dni Dni del guardia
	 * @param email Correo electronico del guardia
	 * @param contrasenia contrasenia del guardia
	 * @param fechaNacimiento fecha de nacimiento del guardia
	 * @param nombre nombre del guardia
	 * @param apellido1 Apellido paterno 
	 * @param apellido2 Apellido materno
	 */
	public Guardia(String usuario, String dni, String email, String contrasenia, LocalDate fechaNacimiento, String nombre, String apellido1,
			String apellido2) {
		super(2,usuario, nombre, apellido1, apellido2, dni, email, contrasenia, fechaNacimiento);
	}
	


	
}
