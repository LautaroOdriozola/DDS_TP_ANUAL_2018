package com.tpdds.dispositivos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("TVVaio")
public class TVVaio extends Fabricante {
	private boolean encendido = false;
	private boolean ahorrando = false;

	public TVVaio() {}

	@Override
	public boolean estaEncendido() {
		return encendido;
	}

	@Override
	public boolean estaApagado() {
		return !encendido;
	}

	@Override
	public boolean estaAhorro() {
		return ahorrando;
	}

	@Override
	public void encender() {
	    encendido = true;
	}

	@Override
	public void apagar() {
	    encendido = false;
	}

	@Override
	public void modoAhorro() {
		ahorrando = true;
	}

	@Override
	public BigDecimal pedirConsumo(long horas) {
		return new BigDecimal(100);
	}

	@Override
	public BigDecimal pedirConsumo(LocalDate fechaInicio, LocalDate fechaFin) {
		return new BigDecimal(100);
	}

	@Override
	public String comando(String mensaje) {
		return "";
	}

	@Override
	public BigDecimal getHorasMinimas() {
		return new BigDecimal(20);
	}

	@Override
	public BigDecimal getHorasMaximas() {
		return new BigDecimal(200);
	}

	@Override
	public BigDecimal getHorasPrendido() {
		return new BigDecimal(60);
	}
}
