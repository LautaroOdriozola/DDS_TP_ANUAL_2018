package com.tpdds.reglas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tpdds.dispositivos.DispositivoInteligente;


@Entity
public class Actuador {

    @Id @GeneratedValue
	private long idActuador;

    @ManyToOne
    private DispositivoInteligente dispositivo;
	
    private String comando;

    public Actuador(DispositivoInteligente dispositivo, String comando) {
        this.dispositivo = dispositivo;
        this.comando = comando;
    }

    public void actuar() {
        dispositivo.comando(comando);
    }
}