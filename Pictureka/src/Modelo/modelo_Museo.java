package Modelo;


/**
 * 
 * Esta clase tiene acceso a todos los datos y funciones del museo
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class modelo_Museo {
	private Registro registro;
	
	
	/**
	 * Crea un Museo. No recibe parametros ya que crea todo desde cero
	 */
	public modelo_Museo() {
		Registro _registro = new Registro();
		registro = _registro;
	}
	
	
	/**
	 * Comprueba que la informacion proporcionada es correcta y permitirle entrar en su cuenta
	 * 
	 * @param emailOUsuario correo electronico o usuario para identificar al usuario
	 * @param contrasenia contrasenia introducida por el usuario
	 * @return la informacion o estado del resultado del login del usuario
	 */
	public Cliente loginUsuario(String emailOUsuario, String contrasenia) {
		return registro.loginDeUsuarios(emailOUsuario, contrasenia);
	}
	public Staff loginStaffs(String emailOUsuario, String contrasenia) {
		return registro.loginStaff(emailOUsuario, contrasenia);
	}
	/**
	 * Funcion para saber que tipo de usuario es
	 * 
	 * @param usuario usuario que se desea saber el tipo de usuario que es
	 * @return Devuelve 1 si es cliente, 2 si es guardia, 3 si es un administrador
	 */
	public int devolverIdentificador(String usuario) {
		return registro.rDevolderIdentificador(usuario);
	}
	/**
	 * Devuelve un cliente 
	 * 
	 * @param usuario usuario del cliente que necesita su informacion
	 * @return Devuelve el cliente
	 */
	public Cliente devolverCliente(String usuario) {
		return registro.rDevolverCliente(usuario);
	}
	/**
	 * Devuelve un cliente 
	 * 
	 * @param usuario usuario del staff que necesita su informacion
	 * @return Devuelve un staff
	 */
	public Staff devolverStaff(String usuario) {
		return registro.rDevolderStaff(usuario);
	}
	
	public Registro getRegistro() {
		return registro;
	}
	public void setRegistro(Registro registro) {
		this.registro = registro;
	}
	
	
	
	
	
	
	
	
}
