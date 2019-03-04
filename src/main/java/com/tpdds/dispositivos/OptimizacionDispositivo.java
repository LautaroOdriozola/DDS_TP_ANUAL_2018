package com.tpdds.dispositivos;

import java.math.BigDecimal;

public class OptimizacionDispositivo {
	private Dispositivo dispositivo;
	private BigDecimal horasOptimas;
	
	
	public OptimizacionDispositivo(Dispositivo _disp, BigDecimal _horas) {
		dispositivo= _disp;
		horasOptimas = _horas;
	}
	
	public void setHorasOptimas(BigDecimal _horas) {
		horasOptimas = _horas;
	}
	
	public BigDecimal getHorasOptimas() {
		return horasOptimas;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}
}
