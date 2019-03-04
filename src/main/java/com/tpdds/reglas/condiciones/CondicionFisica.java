package com.tpdds.reglas.condiciones;

import com.tpdds.reglas.sensores.SensorBase;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class CondicionFisica extends Condicion {

    @OneToOne
    private SensorBase sensor;

    @Embedded
    private Filtro filtro;

    public CondicionFisica(SensorBase sensor, Filtro filtro) {
        this.sensor = sensor;
        this.filtro = filtro;
    }

    public CondicionFisica() {}

    public boolean evaluar() {
        return this.filtro.aplicar(sensor.sensar().longValue());
    }
}
