package com.tpdds;

import com.tpdds.categorias.Categoria;
import com.tpdds.dispositivos.*;
import com.tpdds.repositorios.RepositorioCategorias;
import com.tpdds.transformadores.Posicion;
import com.tpdds.transformadores.Transformador;
import com.tpdds.transformadores.Zona;
import com.tpdds.usuarios.Administrador;
import com.tpdds.usuarios.Cliente;
import com.tpdds.usuarios.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class Fixture implements WithGlobalEntityManager, TransactionalOps {
    public Fixture() {
        withTransaction(() -> {
            Usuario usuario1 = new Usuario("username1", "password");
            entityManager().persist(usuario1);

            Usuario usuario2 = new Usuario("username2", "password");
            entityManager().persist(usuario2);
            
            Usuario usuario3 = new Usuario("username3", "password");
            entityManager().persist(usuario3);
        });

        withTransaction(() -> {
            Categoria categoria1 = new Categoria(
                    1, new BigDecimal(20),
                    new BigDecimal(50), new BigDecimal(0),
                    new BigDecimal(1000)); // TODO: Para asegurarme que entre en esta cat. Borrar estos datos despues

            RepositorioCategorias.getInstance().agregar(categoria1);

            entityManager().persist(categoria1);
        });
        
        withTransaction(() -> {
            Categoria categoria2 = new Categoria(
                    2, new BigDecimal(30),
                    new BigDecimal(1000), new BigDecimal(0),
                    new BigDecimal(2000)); // TODO: Para asegurarme que entre en esta cat. Borrar estos datos despues

            RepositorioCategorias.getInstance().agregar(categoria2);

            entityManager().persist(categoria2);
        });

        withTransaction(() -> {
            Cliente cliente = new Cliente(
                    "Juan", "Rodriguez",
                    "DNI", "40359986",
                    "0111556689458", "Santa fe 2100",
                    LocalDate.of(2010, 1, 15),
                    new Posicion(new BigDecimal(15), new BigDecimal(20)), false, "username1");

            entityManager().persist(cliente);
        });

        withTransaction(() -> {
            Cliente cliente = entityManager().find(Cliente.class, new Long(1));

            DispositivoEstandar estandar = new DispositivoEstandar(
                    "Heladera", new BigDecimal(0.063), new BigDecimal(24),
                    new BigDecimal(33), new BigDecimal(24 * 30));

            Fabricante vaio = new TVVaio();
            DispositivoInteligente inteligente = new DispositivoInteligente("TV", vaio);

            Periodo p1 = new Periodo(YearMonth.of(2018, 10), 150);
            Periodo p2 = new Periodo(YearMonth.of(2018, 11), 120);
            Periodo p3 = new Periodo(YearMonth.of(2018, 12), 130);

            estandar.addPeriodo(p1);
            estandar.addPeriodo(p2);
            estandar.addPeriodo(p3);

            Periodo p4 = new Periodo(YearMonth.of(2018, 11), 400);
            inteligente.addPeriodo(p4);

            cliente.agregarDispositivo(estandar);
            cliente.agregarDispositivoInteligente(inteligente);

            entityManager().persist(estandar);
            entityManager().persist(inteligente);
            entityManager().persist(p1);
            entityManager().persist(p2);
            entityManager().persist(p3);
            entityManager().persist(p4);
            entityManager().persist(vaio);
        });

        withTransaction(() -> {
            Administrador administrador1 = new Administrador(
                    "Homero",
                    "Simpson",
                    "Calle Falsa 123",
                    LocalDate.of(2018, 1, 15),
                    new Long(1), "username2");

            entityManager().persist(administrador1);
        });
        
        withTransaction(() -> {
            Administrador administrador2 = new Administrador(
                    "Marge",
                    "Simpson",
                    "Calle Falsa 123",
                    LocalDate.of(2018, 1, 15),
                    new Long(2), "username3");

            entityManager().persist(administrador2);
        });

        withTransaction(() -> {
            Posicion posicion = new Posicion(new BigDecimal(0), new BigDecimal(0));
            Posicion segundaPosicion =
                    new Posicion(new BigDecimal(10), new BigDecimal(10));

            Zona zona = new Zona(new ArrayList<>(), posicion, 100);

            Transformador transformador = new Transformador(
                    1,
                    posicion,
                    zona
            );

            Transformador segundoTransformador = new Transformador(
                    2,
                    segundaPosicion,
                    zona
            );

            zona.agregarTransformador(transformador);
            zona.agregarTransformador(segundoTransformador);

            entityManager().persist(transformador);
            entityManager().persist(segundoTransformador);
            entityManager().persist(zona);
        });
    }
 public static void main(String[] args) {
    	new Fixture();
   }  
  
}
