package com.tpdds.reglas.sensores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import java.math.BigDecimal;


@Entity @Inheritance
public abstract class SensorBase {

	@Id @GeneratedValue
	private long idSensor;

	public BigDecimal sensar() {
		return BigDecimal.valueOf(0);
	}
}
