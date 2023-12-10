package Modelo;

import java.sql.Timestamp;

/**
 * 
 * En esta clase se almacena la informacion del sensor que se gestiona
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */

public class Sensor {
	private String tipo;
	private int ID_Sala;
	private int Posicion;
	private float lectura;
	private Timestamp Fecha;
	
	public Sensor(String tipo, int iD_Sala, int posicion, float lectura, Timestamp fecha) {
		super();
		this.tipo = tipo;
		ID_Sala = iD_Sala;
		Posicion = posicion;
		this.lectura = lectura;
		Fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getID_Sala() {
		return ID_Sala;
	}

	public void setID_Sala(int iD_Sala) {
		ID_Sala = iD_Sala;
	}

	public int getPosicion() {
		return Posicion;
	}

	public void setPosicion(int posicion) {
		Posicion = posicion;
	}

	public float getLectura() {
		return lectura;
	}

	public void setLectura(float lectura) {
		this.lectura = lectura;
	}

	public Timestamp getFecha() {
		return Fecha;
	}

	public void setFecha(Timestamp fecha) {
		Fecha = fecha;
	} 
	

}
