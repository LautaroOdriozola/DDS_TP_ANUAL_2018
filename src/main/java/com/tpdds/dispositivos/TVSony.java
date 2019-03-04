package com.tpdds.dispositivos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("TVSony")
public class TVSony extends Fabricante {
	
	public TVSony() {}
	
	@Override
	public boolean estaEncendido() {
		return true;
	}
	@Override
	public boolean estaApagado() {
		return false;
	}
	@Override
	public boolean estaAhorro() {
		return true;
	}
	@Override   
	public BigDecimal getHorasMinimas() {
	    	return new BigDecimal(10);
	    }
	@Override
	public BigDecimal getHorasMaximas() {
	    	return new BigDecimal(100);
	    }
	@Override
	public BigDecimal getHorasPrendido() {
	    	return new BigDecimal(50);
	    }

	@Override
	public void encender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apagar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modoAhorro() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal pedirConsumo(long horas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal pedirConsumo(LocalDate fechaInicio, LocalDate fechaFin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String comando(String mensaje) {
		// TODO Auto-generated method stub
		return null;
	}

}
