package com.tpdds.categorias;

import java.math.BigDecimal;
import com.tpdds.repositorios.RepositorioCategorias;

public class DeterminadorDeCategoria {
	private static DeterminadorDeCategoria instance;


	public static DeterminadorDeCategoria getInstance() {
		if(instance == null) instance = new DeterminadorDeCategoria();
		return instance;
	}

	public Categoria calcularCategoria(BigDecimal consumo) {
        return RepositorioCategorias.getInstance().todo()
				.stream()
				.filter(categoria -> categoria.admiteConsumo(consumo))
				.findFirst()
                .orElseThrow(RuntimeException::new); // TODO: hacer excepciÃ³n especial;
	}



}
