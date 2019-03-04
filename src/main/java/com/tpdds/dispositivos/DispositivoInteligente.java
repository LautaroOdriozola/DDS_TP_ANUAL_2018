package com.tpdds.dispositivos;

import com.tpdds.converters.BigToDouble;
import com.tpdds.repositorios.RepositorioCorridaSimplex;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("I")
public class DispositivoInteligente extends Dispositivo {
	@ManyToOne
	private Fabricante fabricante;
	@Convert(converter = BigToDouble.class)
	private BigDecimal contadorConsumo;

	public DispositivoInteligente(){}

	public DispositivoInteligente(String nombre, Fabricante fabricante) {
		this.nombre = nombre;
	    this.fabricante = fabricante;
	    this.contadorConsumo = new BigDecimal(0);
	}

	public void acumularConsumoMes() {
		contadorConsumo = contadorConsumo.add(this.pedirConsumo(720));
	}

	public boolean getEstado() {
	    return fabricante.estaEncendido();
	}

	public boolean estaAhorro() {
		return fabricante.estaAhorro();
	}

	public void encender() {
		if (! fabricante.estaEncendido()) {
			fabricante.encender();
		}
	}
	public void apagar() {
		if (! fabricante.estaApagado()) {
			fabricante.apagar();
		}
	}
	public void modoAhorro() {
		if (!fabricante.estaAhorro()) {
			fabricante.modoAhorro();
		}
	}

	public BigDecimal pedirConsumo(long horas) {
		return fabricante.pedirConsumo(horas);
	}

	public BigDecimal pedirConsumo(LocalDate fechaInicio, LocalDate fechaFin) {
		return fabricante.pedirConsumo(fechaInicio, fechaFin);
	}

	public String comando(String mensaje) {
		return fabricante.comando(mensaje);
	}

	public BigDecimal getConsumoMensual() {
	    return pedirConsumo(720);
	}

	// se usa en home.hbs
	public long getConsumoMensualPretty() {
		return getConsumoMensual().longValue();
	}

	public BigDecimal getHorasMinimas() {
		return fabricante.getHorasMinimas();
	}

	public BigDecimal getHorasMaximas() {
		return fabricante.getHorasMaximas();
	}

	@Override
	public void optimizarCon(BigDecimal horas) {
		OptimizacionDispositivo corrida = new OptimizacionDispositivo(this, horas);
		RepositorioCorridaSimplex.getInstance().agregar(corrida);
	}

	public BigDecimal getHorasPrendido() {
		return fabricante.getHorasPrendido();
	}
	
	public void alterDispositivo(DispositivoInteligente dispositivoModificado) {
		this.setNombre(dispositivoModificado.getNombre());
		this.contadorConsumo=dispositivoModificado.contadorConsumo;
		this.fabricante= dispositivoModificado.fabricante;
	}



}

