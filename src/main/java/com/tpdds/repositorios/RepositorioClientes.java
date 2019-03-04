package com.tpdds.repositorios;

import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.usuarios.Administrador;
import com.tpdds.usuarios.Cliente;
import com.tpdds.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioClientes implements Repositorio<Cliente>, WithGlobalEntityManager, TransactionalOps {
    private static List<Cliente> clientes;
    private static RepositorioClientes instance;

    private RepositorioClientes() {
    }

    public static RepositorioClientes getInstance() {
        if (instance == null) instance = new RepositorioClientes();
        if (clientes == null) clientes = new ArrayList<>();
        return instance;
    }

    @Override
    public List<Cliente> todo() {
        Query query = entityManager().createQuery("from Cliente");

        return (List<Cliente>) query.getResultList();
    }

    @Override
    public void agregar(Cliente entry) {
        withTransaction(() -> entityManager().persist(entry));
    }

    @Override
    public void fijar(List<Cliente> entries) {
        clientes.clear();
        clientes.addAll(entries);
    }

    @Override
    public void reiniciar() {
        clientes.clear();
    }

    public boolean accesoConcedido(String user, String password) {
        Usuario usuario = null;

        usuario = entityManager().find(Usuario.class, user);

        if (usuario == null)
            return false;

        return usuario.getPassword().equals(password);
    }

    public boolean esAdmin(String user) {
        Query query = entityManager().createQuery("from Administrador a where a.user = :usr");
        query.setParameter("usr", user);

        List<Administrador> admins = query.getResultList();

        return admins.size() > 0;
    }

    public boolean existe(String username) {
        Usuario usuario = entityManager().find(Usuario.class, username);
        return usuario != null;
    }

    public List<DispositivoEstandar> getDispositivosEstandar(Cliente c) {

        String queryString = "FROM Cliente c " + "WHERE c.id = :idCliente";

        Query query = entityManager()
                .createQuery(queryString)
                .setParameter("idCliente", Long.valueOf(c.getID()));

        List<Cliente> clientes = query.getResultList();

        List<DispositivoEstandar> dispositivos = clientes.get(0).getDispositivosEstandar();

        return dispositivos;
    }

    public List<DispositivoInteligente> getDispositivosInteligentes(Cliente c) {

        String queryString = "FROM DispositivoInteligente d " + "WHERE d.cliente.id = :idCliente";

        Query query = entityManager()
                .createQuery(queryString)
                .setParameter("idCliente", Long.valueOf(c.getID()));

        List<DispositivoInteligente> dispositivos = query.getResultList();

        return dispositivos;
    }

    public Cliente getClientByUser(String usuario) {

        Query query = entityManager().createQuery("from Cliente c where c.user = :usr");

        query.setParameter("usr", usuario);

        List<Cliente> client = query.getResultList();

        Cliente cliente = client.get(0);

        return cliente;

    }

    public void update(Cliente cliente) {
        withTransaction(() -> entityManager().merge(cliente));
    }
}
