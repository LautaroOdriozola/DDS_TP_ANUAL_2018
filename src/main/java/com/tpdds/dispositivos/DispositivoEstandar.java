package com.tpdds.dispositivos;

import com.tpdds.converters.BigToDouble;
import java.time.temporal.ChronoUnit;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("E")
public class DispositivoEstandar extends Dispositivo {
    @Convert(converter = BigToDouble.class)
	private BigDecimal kwPorHora;
    @Convert(converter = BigToDouble.class)
	private BigDecimal horasUso;
    @Convert(converter = BigToDouble.class)
	private BigDecimal horasMinimas;
    @Convert(converter = BigToDouble.class)
	private BigDecimal horasMaximas;

	public DispositivoEstandar(){}

	public DispositivoEstandar(String nombre, BigDecimal kwPorHora, BigDecimal horasUso, BigDecimal horasMinimas, BigDecimal horasMaximas) {
	    this.nombre = nombre;
		this.kwPorHora = kwPorHora;
		this.horasUso = horasUso;
		this.horasMinimas = horasMinimas;
		this.horasMaximas = horasMaximas;
	}

	public DispositivoInteligente hacerseInteligente(Fabricante f) {
		return new DispositivoInteligente(this.getNombre(), f);
	}

	public BigDecimal getConsumo() {
		return kwPorHora.multiply(horasUso);
	}

	public BigDecimal getConsumoMensual() {
	    return kwPorHora.multiply(new BigDecimal(720));
	}

	// se usa en home.hbs
	public long getConsumoMensualPretty() {
		return getConsumoMensual().longValue();
	}

	public boolean getEstado() {
		return true;
	}

	// -------------------------getters------------------------
	
	public BigDecimal getHorasMinimas() {
		return horasMinimas;
	}

	public BigDecimal getHorasMaximas() {
		return horasMaximas;
	}


	@Override
	public void optimizarCon(BigDecimal horas) {}
	    // DO NOTHING.

	public BigDecimal getKwPorHora() {
		return kwPorHora;
	}
	public BigDecimal getHorasConumo() {
		return horasUso;
	}

	//-------------------------------------------------------
	public BigDecimal pedirConsumo(LocalDate fechaInicio,LocalDate fechaFin) {
		long hoursBetween = ChronoUnit.HOURS.between(fechaInicio,fechaFin);
		return kwPorHora.multiply(new BigDecimal(hoursBetween));
		
		
	}
	
	public void alterDispositivo(DispositivoEstandar dispositivoModificado) {
		this.setNombre(dispositivoModificado.getNombre());
		this.horasMaximas=dispositivoModificado.horasMaximas;
		this.horasMinimas=dispositivoModificado.horasMinimas;
		this.horasUso=dispositivoModificado.horasUso;
		this.kwPorHora=dispositivoModificado.kwPorHora;

	}
}


