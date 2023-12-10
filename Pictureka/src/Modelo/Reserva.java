package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;



/**
 * 
 * En esta clase se almacena la informacion de las reservas realizadas por los clientes
 * 
 * @author Jolie Alain Vásquez
 * @author Oscar González Guerra
 * @author Ruoyan Zhang
 * @author Lian Salmerón López
 *
 */
public class Reserva {
	private int identificador = 0;
	private int numTickets = 0;
	private String id_Duenio;
	private LocalDate fecha;
	private LocalTime hora;
	private String revisor;
	
	/**
	 * Constructor Reserva
	 * @param identificador: indica el numero asignado a la reserva
	 * @param numTickets: es el numero de tickets que se han reservado
	 * @param id_Duenio: indica el dni de la persona que hizo esa reserva
	 * @param fecha: almacena la fecha de la reserva
	 * @param hora: almacena la hora de la reserva
	 */
	public Reserva(int identificador, int numTickets, String duenio, LocalDate fecha, LocalTime hora, String revisor) {
		super();
		this.identificador = identificador;
		this.numTickets = numTickets;
		this.id_Duenio = duenio;
		this.fecha = fecha;
		this.hora = hora;
		this.revisor = revisor;
	}

	public String getRevisor() {
		return revisor;
	}

	public void setRevisor(String revisor) {
		this.revisor = revisor;
	}

	public int getIdentificador() {
		return identificador;
	}

	public int getNumTickets() {
		return numTickets;
	}

	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	

	public String getId_Duenio() {
		return id_Duenio;
	}

	public void setId_Duenio(String id_Duenio) {
		this.id_Duenio = id_Duenio;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	
	
	
}
