package Modelo;

import java.time.LocalDate;

/**
 * 
 * En esta clase se almacena la informacion de un Cliente
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class Cliente { 

	private int identificadorCliente;
	private String usuario;
	private String dni;
	private String email;
	private String contrasenia;
	private LocalDate fechaNacimiento;
	
	
	//Constructores
	/**
	 * Constructor de Cliente
	 * 
	 * @param usuario Usuario que decide el usuario
	 * @param dni Dni del usuario
	 * @param email Email del usuario
	 * @param contrasenia constrasenia del usuario
	 * @param fechaNacimiento fecha de nacimiento del usuario
	 */
	public Cliente(String usuario, String dni, String email, String contrasenia, LocalDate fechaNacimiento) {
		this.identificadorCliente = 1;
		this.usuario = usuario;
		this.dni = dni;
		this.email = email;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
		

	}
	
	public Cliente(int identificadorCliente, String usuario, String dni, String email, String contrasenia, LocalDate fechaNacimiento) {
		this.identificadorCliente = identificadorCliente;
		this.usuario = usuario;
		this.dni = dni;
		this.email = email;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
		

	}


	
	
	public int getIdentificadorCliente() {
		return identificadorCliente;
	}


	public void setIdentificadorCliente(int identificadorCliente) {
		this.identificadorCliente = identificadorCliente;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContrasenia() {
		return contrasenia;
	}


	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}



	
	
}
