package com.tpdds.repositorios;

import com.tpdds.categorias.Categoria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioCategorias implements Repositorio<Categoria> , WithGlobalEntityManager, TransactionalOps{
    private static List<Categoria> categorias;
    private static RepositorioCategorias instance;

    private RepositorioCategorias() {}
    public static RepositorioCategorias getInstance() {
        if (instance == null) instance = new RepositorioCategorias();
        if (categorias == null) categorias = new ArrayList<>();
        return instance;
    }

    @Override
    public List<Categoria> todo() {

        Query query = entityManager().createQuery("from Categoria");
        List<Categoria> categorias = query.getResultList();

        return categorias;
    }

    @Override
    public void agregar(Categoria entry) {

    	withTransaction(() -> entityManager().persist(entry));
    }

    @Override
    public void fijar(List<Categoria> entries) { // ??
        categorias.clear();
        categorias.addAll(entries);
    }

    @Override
    public void reiniciar() {
        categorias.clear();
    } // ??
}
