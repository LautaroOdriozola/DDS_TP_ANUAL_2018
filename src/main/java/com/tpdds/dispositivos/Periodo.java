package com.tpdds.dispositivos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.YearMonth;

@Entity
public class Periodo {

    @Id
    @GeneratedValue
    private long idPeriodo;

    private YearMonth fecha;
    private double consumo;

    public Periodo(YearMonth fecha, long consumo) {
        this.fecha = fecha;
        this.consumo = consumo;
    }

    public Periodo() {}

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public YearMonth getFecha() {
        return fecha;
    }

    public void setFecha(YearMonth fecha) {
        this.fecha = fecha;
    }
}
