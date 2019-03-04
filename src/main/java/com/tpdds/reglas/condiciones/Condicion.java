package com.tpdds.reglas.condiciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity @Inheritance
public abstract class Condicion {

    @Id @GeneratedValue
    private long idCondicion;

    public boolean evaluar() {
    	return true;
    }

	
}