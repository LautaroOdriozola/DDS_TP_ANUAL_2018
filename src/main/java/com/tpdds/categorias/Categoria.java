package com.tpdds.categorias;
import com.tpdds.converters.BigToDouble;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Categoria {
	@Convert(converter = BigToDouble.class)
	private BigDecimal cargoVariable;
	@Convert(converter = BigToDouble.class)
	private BigDecimal cargoFijo;
	@Convert(converter = BigToDouble.class)
	private BigDecimal kwMinimo;
	@Convert(converter = BigToDouble.class)
	private BigDecimal kwMaximo;
	@Id
	private int nombre;

	public Categoria() {}

	public Categoria(int nombreCat, BigDecimal cargoFijo, BigDecimal cargoVariable,
					 BigDecimal kwMinimo, BigDecimal kwMaximo) {
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
		this.kwMinimo = kwMinimo;
		this.kwMaximo = kwMaximo;
		this.nombre = nombreCat;
	}

	public int getNombre() {
		return this.nombre;
	}

	public BigDecimal facturacionPorMes(BigDecimal kWh) {
		return kWh.multiply(this.cargoVariable).add(this.cargoFijo);
	}

	public boolean admiteConsumo(BigDecimal kwConsumidos) {
		// comparteTo me devuelve int y a partir de ahi decido
		int primerComparacion = kwConsumidos.compareTo(this.kwMinimo);		// consumidos >= minimo		
		int segundaComparacion = this.kwMaximo.compareTo(kwConsumidos);		// maximo >= consumidos
		
		return this.devolverResultadoComparacion(primerComparacion, segundaComparacion);
	}	
	
	// La operacion comparteTo, si el primero es mas grande que el segundo, me devuelve un nro > 0. 
	// La operacion comparteTo, si el primero es igual que el segundo, me devuelve un nro == 0.
	public boolean devolverResultadoComparacion(int primerComparacion, int segundaComparacion){
		return primerComparacion >= 0  && segundaComparacion >= 0;
	}

	@Override
	public String toString() {
	    return "" + this.getNombre();
	}
}
