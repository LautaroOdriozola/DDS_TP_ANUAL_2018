package com.tpdds.reglas;

import com.tpdds.reglas.Actuador;
import com.tpdds.reglas.condiciones.Condicion;

import javax.persistence.*;


@Entity
public class Regla {

    @Id @GeneratedValue
	private long idRegla;

    @OneToOne
    private Condicion condicion;

    @OneToOne
    private Actuador actuador;

    public Regla(Condicion condicion, Actuador actuador) {
    	this.condicion = condicion;
        this.actuador = actuador;
    }

    public Regla() {
    }

    public void ejecutar() {
        if (condicion.evaluar()) actuador.actuar();
    }
}