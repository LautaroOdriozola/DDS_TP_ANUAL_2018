package com.tpdds.transformadores;

import java.math.BigDecimal;
import java.util.List;

import com.tpdds.usuarios.Cliente;

import javax.persistence.*;

@Entity
public class Transformador {
	@Id
	private int id;
	@Embedded
	private Posicion posicion;
	@ManyToOne @JoinColumn(name = "zona")
	private Zona zona;

	public Transformador(){}

	public Transformador(int id, Posicion posicion, Zona zona) {
		this.id = id;
		this.posicion = posicion;
		this.zona = zona;
	}

	public BigDecimal energiaSuministrada() {
		return zona.obtenerClientesTransf(this)
				.stream()
				.map(Cliente::calcularConsumoDeDispositivos)
				.reduce(new BigDecimal(0), BigDecimal::add);
	}

	public int getId() {
		return id;
	}

	public BigDecimal diferenciaCon(Posicion posCliente) {
		return posicion.diferenciaCon(posCliente);
	}
	
	public double diferenciaConEnDouble(Posicion posCliente) {
		return posicion.diferenciaCon(posCliente).doubleValue();
	}

	public List<Cliente> getClientes() {
	    return zona.obtenerClientesZona();
	}

	// para handlebar
	public long getEnergia() {
		return energiaSuministrada().longValue();
	}
	
	//para handlebar
	public Posicion getPosicion() {
		return this.posicion;
	}
	
	@Override
	public String toString() {
		return "" + this.getId();
	}
}