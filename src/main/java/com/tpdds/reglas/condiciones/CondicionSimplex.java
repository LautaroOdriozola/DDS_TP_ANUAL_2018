package com.tpdds.reglas.condiciones;

import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.reglas.sensores.SensorSimplex;
import com.tpdds.repositorios.RepositorioCorridaSimplex;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class CondicionSimplex extends Condicion {

    @OneToOne
    private SensorSimplex sensor;

    @ManyToOne
    private DispositivoInteligente dispositivo;

    public CondicionSimplex(SensorSimplex _sens, DispositivoInteligente _disp) {
        this.sensor = _sens;
        this.dispositivo = _disp;
    }

    public CondicionSimplex() {}

    public boolean evaluar(){
        final BigDecimal corrida = RepositorioCorridaSimplex.getInstance().getCorrida(this.dispositivo);

        return sensor.sensar().compareTo(corrida) >= 0;
	}
	
}