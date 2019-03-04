package com.tpdds.transformadores;

import com.tpdds.repositorios.RepositorioClientes;
import com.tpdds.usuarios.Cliente;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Zona {
	@Id
	@GeneratedValue
	private int id;
	@OneToMany(mappedBy = "zona")
	private List<Transformador> transformadores;
	@Embedded
	private Posicion posicionBase;
	private int radio;

	public Zona(){}

	public Zona(List<Transformador> transformadores, Posicion posicionBase, int radio) {
		this.transformadores = transformadores;
		this.posicionBase = posicionBase;
		this.radio = radio;
	}

	public List<Cliente> obtenerClientesTransf(Transformador t) {
		return this.obtenerClientesZona()
				.stream()
				.filter(c -> this.masCercanoA(c, t))
				.collect(Collectors.toList());
	}

	public List<Cliente> obtenerClientesZona() {
		return RepositorioClientes.getInstance().todo()
				.stream()
				.filter(c -> this.estaEnZona(c))
				.collect(Collectors.toList());
	}

	public boolean estaEnZona(Cliente c) {
		return c.getPosicion().estaIncluidaEn(posicionBase, radio);
	}
	
	public boolean masCercanoA(Cliente c, Transformador t) {
		return transformadores
				.stream()
				.sorted((t1, t2)->t1.diferenciaCon(c.getPosicion()).
                        compareTo(t2.diferenciaCon(c.getPosicion())))
				.collect(Collectors.toList())
				.get(0).equals(t);
	}
	


	public BigDecimal obtenerConsumoActual() {
		return this.obtenerClientesZona().stream().map(Cliente::calcularConsumoDeDispositivos).reduce(new BigDecimal(0),
				BigDecimal::add);
	}

	public void agregarTransformador(Transformador transformador) {
		transformadores.add(transformador);
	}
}
