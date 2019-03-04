package com.tpdds.repositorios;

import com.tpdds.dispositivos.OptimizacionDispositivo;
import com.tpdds.dispositivos.DispositivoInteligente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCorridaSimplex implements Repositorio<OptimizacionDispositivo> {
    private static List<OptimizacionDispositivo> corridas;
    private static RepositorioCorridaSimplex instance;

    //TODO: No estan persistidas las corridas
    private RepositorioCorridaSimplex() {}
    public static RepositorioCorridaSimplex  getInstance() {
        if (instance == null) instance = new RepositorioCorridaSimplex();
        if (corridas == null) corridas = new ArrayList<>();
        return instance;
    }

    @Override
    public List<OptimizacionDispositivo> todo() {
        return corridas;
    }

    @Override
    public void agregar(OptimizacionDispositivo entry) {
        corridas.add(entry);
    }

    @Override
    public void fijar(List<OptimizacionDispositivo> entries) {
        corridas.clear();
        corridas.addAll(entries);
    }

    @Override
    public void reiniciar() {
        corridas.clear();
    }
    
    public BigDecimal getCorrida(DispositivoInteligente disp) {
        return corridas.stream()
                .filter(corrida -> corrida.getDispositivo() == disp)
                .findFirst()
                .get().getHorasOptimas();
    }
}
