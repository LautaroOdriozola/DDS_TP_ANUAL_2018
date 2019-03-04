package com.tpdds;

import com.tpdds.dispositivos.Dispositivo;
import com.tpdds.transformadores.Transformador;
import com.tpdds.usuarios.Cliente;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.util.List;

public class ReportGenerator implements WithGlobalEntityManager, TransactionalOps {

    public void consumoPorHogar() { // Asumo que un hogar es un cliente ??
        List<Cliente> clientes = entityManager().createQuery("from Cliente").getResultList();

        for(Cliente c : clientes) {

            BigDecimal consumo = c.calcularConsumoDeDispositivos();

            System.out.println(
                    "Consumo " + c.getNombre() + " " + c.getApellido() + ": " +  consumo);

        }
    }

    //TODO: Arreglar d.cliente.id = cli.id. No reconoce 'cli.id'

    public void consumoPorDispositivo(long id_cliente) {
        Cliente cli = entityManager().find(Cliente.class, id_cliente);

        System.out.println("Cliente " + cli.getNombre() + " " + cli.getApellido() + ":");

        List<Dispositivo> dispositivos = //TODO: Ver como joinear Disp con Cliente. Esto no anda.
                   entityManager().createQuery(
                           "from Dispositivo d where d.cliente.id = " + cli.getID()).getResultList();

        for(Dispositivo d: dispositivos) {
              System.out.println("Dispositivo: " + d.getNombre() + ": " + d.getConsumoMensual());
        }
    }

    public void consumoPorTransformador() {
        List<Transformador> transformadores = entityManager().createQuery("from Transformador").getResultList();

        for(Transformador t: transformadores) {
            System.out.println("Transformador: " + t.getId() + ", consumo: "+ t.energiaSuministrada());
        }

    }
}
