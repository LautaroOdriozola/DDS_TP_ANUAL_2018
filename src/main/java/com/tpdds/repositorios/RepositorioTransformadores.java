package com.tpdds.repositorios;

import com.tpdds.transformadores.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioTransformadores implements Repositorio<Transformador>, WithGlobalEntityManager, TransactionalOps {

    private static RepositorioTransformadores instance;
    private static List<Transformador> transformadores;
    
    public static RepositorioTransformadores getInstance() {
        if (instance == null) instance = new RepositorioTransformadores();
        if (transformadores == null) transformadores = new ArrayList<>();
        return instance;
    }
    
    @Override
    public List<Transformador> todo() {
    	 Query query = entityManager().createQuery("from Transformador");

         return (List<Transformador>) query.getResultList();
    }
    
    @Override
    public void agregar(Transformador entry) {

    	withTransaction(() -> entityManager().persist(entry));
    }
    
    @Override
    public void fijar(List<Transformador> entries) {
        transformadores.clear();
        transformadores.addAll(entries);
    }
    
    @Override
    public void reiniciar() {
        transformadores.clear();
    }

    public Transformador buscar(int id) {
        return entityManager().find(Transformador.class, id);
    }
}