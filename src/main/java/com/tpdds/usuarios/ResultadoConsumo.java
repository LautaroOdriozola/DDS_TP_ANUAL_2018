package com.tpdds.usuarios;

import com.tpdds.dispositivos.Dispositivo;
import org.apache.commons.math3.optim.PointValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ResultadoConsumo {
    private Cliente cliente;
    private HashMap<Dispositivo, Double> limites;

    public ResultadoConsumo(PointValuePair solution, Cliente cliente) {
        this.cliente = cliente;
        this.limites = this.obtenerLimites(solution);

    }

    private HashMap<Dispositivo, Double> obtenerLimites(PointValuePair solution) {
        final ArrayList<Dispositivo> dispositivos = this.cliente.getAllDispositivos();
        final HashMap<Dispositivo, Double> limites = new HashMap<>();

        IntStream.range(0, dispositivos.size())
                .forEach(index -> {
                    final double limite = solution.getPoint()[index];
                    final Dispositivo dispositivo = dispositivos.get(index);
                    limites.put(dispositivo, limite);
                });

        return limites;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public HashMap<Dispositivo, Double> getLimites() {
        return limites;
    }

    public double getLimiteParaDispositivo(Dispositivo dispositivo) {
        return limites.get(dispositivo);
    }

    public double getConsumoMaximo() {
        double consumoMaximo = 0;

        // estuve 3 horas para hacerlo con un reduce, queda horrible
        // intente el que se atreva
        for (Map.Entry<Dispositivo, Double> entrada : limites.entrySet()) {
            consumoMaximo +=
                    entrada.getKey().getConsumoMensual().doubleValue()
                            * entrada.getValue();
        }

        return consumoMaximo;
    }
}
