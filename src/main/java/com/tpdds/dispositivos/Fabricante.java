package com.tpdds.dispositivos;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "nombre", discriminatorType = DiscriminatorType.STRING)
public abstract class Fabricante {
    @Id
    @GeneratedValue
    private long idFabricante;

    public abstract boolean estaEncendido();
    public abstract boolean estaApagado();
    public abstract boolean estaAhorro();

    public abstract void encender();
    public abstract void apagar();
    public abstract void modoAhorro();

    public abstract BigDecimal pedirConsumo(long horas);
    public abstract BigDecimal pedirConsumo(LocalDate fechaInicio, LocalDate fechaFin);

    public abstract String comando(String mensaje);

    public abstract BigDecimal getHorasMinimas();
    public abstract BigDecimal getHorasMaximas();
    public abstract BigDecimal getHorasPrendido();
}
