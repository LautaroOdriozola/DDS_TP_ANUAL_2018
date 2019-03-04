package com.tpdds.transformadores;

import com.tpdds.converters.BigToDouble;

import javax.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class Posicion {
	@Convert(converter = BigToDouble.class)
	private BigDecimal x;
	@Convert(converter = BigToDouble.class)
	private BigDecimal y;

	public Posicion(){}

	public Posicion(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}

	public BigDecimal getx() {
		return x;
	}

	public BigDecimal gety() {
		return y;
	}

	public boolean estaIncluidaEn(Posicion pb, int radio) {

		BigDecimal maxX = pb.getx().add(new BigDecimal(radio));

		BigDecimal minX = pb.getx().subtract(new BigDecimal(radio));

		BigDecimal maxY = pb.gety().add(new BigDecimal(radio));

		BigDecimal minY = pb.gety().subtract(new BigDecimal(radio));

		return ((x.compareTo(minX) > 0 && x.compareTo(maxX) < 0)
				&& (y.compareTo(minY) > 0 && y.compareTo(maxY) < 0));

	}

	// TODO: buscar geodds
	public BigDecimal diferenciaCon(Posicion otraPos) {
		BigDecimal distX = otraPos.getx().subtract(x);
		BigDecimal distY = otraPos.gety().subtract(y);
		
		return BigDecimal.valueOf(Math.sqrt((distX.pow(2).add(distY.pow(2)).doubleValue())));
	}

	@Override
	public String toString() {
		return String.format("%.5f, %.5f", this.getx().doubleValue(), this.gety().doubleValue());
	}

}