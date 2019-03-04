package com.tpdds.reglas.condiciones;

import javax.persistence.*;

@Embeddable
public class Filtro {
    public Filtro() {
    }

    public enum Comparacion {
        GT, LT, EQ, GEQ, LEQ
    }

    @Enumerated(value = EnumType.STRING)
    private Comparacion comparacion;

    private long valorAComparar;

    public Filtro(Comparacion comparacion, long valorAComparar) {
        this.comparacion = comparacion;
        this.valorAComparar = valorAComparar;
    }

    // se podría hacer mas lindo con polimorfismo, pero
    // ya está tan sobrediseñado esto que creo que es demasiado
    public boolean aplicar(long valor) {
        switch(comparacion) {
            case EQ:
                return valor == valorAComparar;
            case GT:
                return valor > valorAComparar;
            case LT:
                return valor < valorAComparar;
            case GEQ:
                return valor >= valorAComparar;
            case LEQ:
                return valor <= valorAComparar;
            default:
                return true;
        }
    }
}

