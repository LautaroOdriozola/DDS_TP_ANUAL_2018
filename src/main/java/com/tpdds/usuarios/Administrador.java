package com.tpdds.usuarios;

import com.tpdds.converters.LocalToDate;
import com.tpdds.dispositivos.*;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Administrador implements WithGlobalEntityManager, TransactionalOps {
	@Id
	private long id;
	private String nombre;
	private String apellido;
	private String domicilio;
	@Convert(converter = LocalToDate.class)
	private LocalDate fechaDeAlta;
	String user;

	public Administrador(){}

	public Administrador(String nombre, String apellido, String domicilio, LocalDate fecha, long id, String user) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.fechaDeAlta = fecha;
		this.id = id;
		this.user = user;
	}

	public long getId() {
		return id;
	}
	

	public long getMesesAdministrador() {
		return ChronoUnit.MONTHS.between(getFechaDeAlta(), LocalDate.now());
	}

	public String getNombre() {
		return nombre;
	}

	public String getUser() {
		return this.user;
	}

	public LocalDate getFechaDeAlta() {
		return fechaDeAlta;
	}
	
	public void crearDispositivo(Dispositivo dispositivo) {
		entityManager().getTransaction().begin();
		entityManager().persist(dispositivo);
		entityManager().getTransaction().commit();
	}
	
	public void modificarDispositivo(Dispositivo dispositivoViejo, Dispositivo dispositivoNuevo) {
		entityManager().getTransaction().begin();
		Dispositivo dispositivoAModificar = entityManager().find(Dispositivo.class,dispositivoViejo.getId());
		dispositivoAModificar.alterDispositivo(dispositivoNuevo);
		entityManager().getTransaction().commit();
		
	}
	
	public void detachDispositivo(Dispositivo dispositivo) {
		entityManager().getTransaction().begin();
		Dispositivo dispositivoDb= entityManager().find(Dispositivo.class,dispositivo.getId());
		entityManager().detach(dispositivoDb);
		entityManager().getTransaction().commit();
		
	}
}

