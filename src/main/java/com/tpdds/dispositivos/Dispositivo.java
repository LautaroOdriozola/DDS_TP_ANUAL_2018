package com.tpdds.dispositivos;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Dispositivo {
    @Id
    @GeneratedValue
    private long idDispositivo;

    @OneToMany
    @JoinColumn(name = "dispositivo")
    private List<Periodo> periodos = new ArrayList<>();

    protected String nombre;

    public abstract BigDecimal getConsumoMensual();

    public abstract BigDecimal getHorasMinimas();

    public abstract BigDecimal getHorasMaximas();
    public abstract BigDecimal pedirConsumo(LocalDate fi,LocalDate ff);

    public abstract boolean getEstado();

    public abstract void optimizarCon(BigDecimal horas);
    public long getId() {
        return idDispositivo;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nuevoNombre) {
    	this.nombre = nuevoNombre;
    }
    public void alterDispositivo(Dispositivo dispositivo) {}

    public void addPeriodo(Periodo periodo) {
        periodos.add(periodo);
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public Double getConsumoEnPeriodo(YearMonth yearMonth) {
        return periodos.stream()
                .filter(p -> p.getFecha().compareTo(yearMonth) == 0)
                .findFirst()
                .map(Periodo::getConsumo)
                .orElse((double) 0);
    }
}
