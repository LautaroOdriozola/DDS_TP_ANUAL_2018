package com.tpdds.repositorios;

import java.util.List;

public interface Repositorio<K> {
    List<K> todo();
    void agregar(K entry);
    void fijar(List<K> entries);
    void reiniciar();
}
