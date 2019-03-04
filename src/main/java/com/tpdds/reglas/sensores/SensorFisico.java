package com.tpdds.reglas.sensores;

import com.tpdds.dispositivos.DispositivoInteligente;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class SensorFisico extends SensorBase {

    private String comando;

    @OneToOne
    private DispositivoInteligente dispositivo;

    public SensorFisico(DispositivoInteligente dispositivo, String comando) {
        this.comando = comando;
        this.dispositivo = dispositivo;
    }

    public SensorFisico() {}

    public BigDecimal sensar() {
        return new BigDecimal(dispositivo.comando(comando));
    }
}