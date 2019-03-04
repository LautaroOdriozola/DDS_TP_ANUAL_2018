package com.tpdds.reglas.sensores;

import com.tpdds.dispositivos.DispositivoInteligente;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class SensorSimplex extends SensorBase {

    @OneToOne
    private DispositivoInteligente dispositivo;

    public SensorSimplex(DispositivoInteligente dispositivo) {
        this.dispositivo = dispositivo;
    }

    public SensorSimplex() {}

    public BigDecimal sensar() {
        return dispositivo.getHorasPrendido();
    }
}