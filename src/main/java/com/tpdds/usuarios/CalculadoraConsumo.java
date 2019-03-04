package com.tpdds.usuarios;

import com.tpdds.dispositivos.Dispositivo;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CalculadoraConsumo {
    private final int CONSUMO_OPTIMO = 612;

    public ResultadoConsumo calcular(Cliente cliente) {
        ArrayList<Dispositivo> dispositivos = cliente.getAllDispositivos();

        LinearObjectiveFunction funcionObjetivo = generarFuncionObjetivo(dispositivos);
        ArrayList<LinearConstraint> constraints = crearTodasLasContstraints(dispositivos);

        PointValuePair solution;

        solution = new SimplexSolver().optimize(funcionObjetivo, new LinearConstraintSet(constraints), GoalType.MAXIMIZE);

        return new ResultadoConsumo(solution, cliente);
    }

    private LinearObjectiveFunction generarFuncionObjetivo(ArrayList<Dispositivo> dispositivos) {
        double[] variables = new double[dispositivos.size()];
        Arrays.fill(variables, 1);

        return new LinearObjectiveFunction(variables, 0);
    }

    public ArrayList<LinearConstraint> crearTodasLasContstraints(ArrayList<Dispositivo> dispositivos) {
        ArrayList<LinearConstraint> constraints = new ArrayList<>();

        constraints.add(crearConstraintConsumoTotal(dispositivos));
        constraints.addAll(crearConstraintsDispositivos(dispositivos));

        return constraints;
    }

    private LinearConstraint crearConstraintConsumoTotal(ArrayList<Dispositivo> dispositivos) {
        double[] coeficientes = dispositivos.stream()
                .mapToDouble(dispositivo -> dispositivo.getConsumoMensual().doubleValue())
                .toArray();

        return new LinearConstraint(coeficientes, Relationship.LEQ, CONSUMO_OPTIMO);
    }

    private ArrayList<LinearConstraint> crearConstraintsDispositivos(ArrayList<Dispositivo> dispositivos) {
        ArrayList<LinearConstraint> resultado = new ArrayList<>();

        IntStream
                .range(0, dispositivos.size())
                .forEach(index -> {
                    final Dispositivo dispositivo = dispositivos.get(index);
                    final double[] puntos = new double[dispositivos.size()];

                    Arrays.fill(puntos, 0);
                    puntos[index] = 1;

                    resultado.add(crearConstraintDispositivoMaximo(puntos, dispositivo));
                    resultado.add(crearConstraintDispositivoMinimo(puntos, dispositivo));
                });

        return resultado;
    }

    private LinearConstraint crearConstraintDispositivoMinimo(double[] puntos, Dispositivo dispositivo) {
        return new LinearConstraint(puntos, Relationship.LEQ, dispositivo.getHorasMaximas().doubleValue());
    }

    private LinearConstraint crearConstraintDispositivoMaximo(double[] puntos, Dispositivo dispositivo) {
        return new LinearConstraint(puntos, Relationship.GEQ, dispositivo.getHorasMinimas().doubleValue());
    }

    public void generarCorridaSimplex(Cliente c) {
        ResultadoConsumo solution = calcular(c);

        solution.getLimites().forEach((dispositivo, horas) ->
                dispositivo.optimizarCon(new BigDecimal(horas)));
    }
}
