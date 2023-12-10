package Modelo;

import java.time.LocalDate;

/**
 * 
 * En esta clase se lleva el manejo de los atributos del personal de staff, como guardias y administradores.
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class Staff {

	//Atributos
	//Identificador para diferenciar el tipo de usuario al leer el Json
	private int identificadorUser;
	private String usuario;
	private String nombre;
	private String apellido1;
	private String apellido2;	
	private String dni;
	private String email;
	private String contrasenia;
	private LocalDate fechaNacimiento;
	
	//Contructores
	/**
	 * 
	 * Constructor del personal del staff, que necesita su respectiva información para ser creado.
	 * 
	 * @param identificadorUser		Número que identifica el tipo de usuario que es.
	 * @param usuario				Identificador del usuario, con el qué podrá iniciar sesión.
	 * @param nombre				Nombre del personal de staff.
	 * @param apellido1				Primer apellido del personal de staff.
	 * @param apellido2				Segundo apellido del personal de staff.
	 * @param dni					Número de identifiación del personal de staff.
	 * @param email					Dirección de correo del personal de staff.
	 * @param contrasenia			Contraseña del personal de staff, para acceder a la aplicación.
	 * @param fechaNacimiento		Fecha de nacimiento del personal de staff.
	 */
	public Staff(int identificadorUser, String usuario, String nombre, String apellido1, String apellido2, String dni, String email, String contrasenia, LocalDate fechaNacimiento) {
		super();
		this.identificadorUser = identificadorUser;
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.email = email;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	//Getters y Setters
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public int getIdentificadorUser() {
		return identificadorUser;
	}
	public void setIdentificadorUser(int identificadorUser) {
		this.identificadorUser = identificadorUser;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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








