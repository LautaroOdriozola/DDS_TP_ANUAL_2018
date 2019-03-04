package com.tpdds.repositorios;

import com.tpdds.dispositivos.DispositivoEstandar;

import java.util.ArrayList;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;



//Solo necesito el metodo de persistir un dispositivo, no hice que implemente Repositorio
//porque tengo que agregar un montón de métodos que no interesan
public class RepositorioDispositivos implements WithGlobalEntityManager, TransactionalOps{
	
	 private static RepositorioDispositivos instance;

	    private RepositorioDispositivos() {}

	    public static RepositorioDispositivos getInstance() {
	        if (instance == null) instance = new RepositorioDispositivos();
	      
	        return instance;
	    }
	public void persistDispositivoEstandar(DispositivoEstandar d) {
        withTransaction(() -> entityManager().persist(d));
    }
}
