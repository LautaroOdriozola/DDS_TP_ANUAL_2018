import com.tpdds.categorias.Categoria;
import com.tpdds.categorias.DeterminadorDeCategoria;
import com.tpdds.repositorios.RepositorioCategorias;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDeterminadorDeCategorias {
//	private DeterminadorDeCategoria det = DeterminadorDeCategoria.getInstance();
//	private RepositorioCategorias repCat = RepositorioCategorias.getInstance();
//	private Categoria r1, r2;
//
//	@Before
//	public void crearCategorias(){
//		r1 = new Categoria(1,new BigDecimal(18.76),new BigDecimal(0.644),new BigDecimal(0.344), new BigDecimal(150));
//        r2 = new Categoria(2,new BigDecimal(35.32),new BigDecimal(0.644),new BigDecimal(325),new BigDecimal(150));
//        repCat.fijar(Arrays.asList(r1, r2));
//	}

	Categoria categoria = mock(Categoria.class);
	DeterminadorDeCategoria determinador = mock(DeterminadorDeCategoria.class);

	@Before
	public void fixture() {
		when(determinador.calcularCategoria(new BigDecimal(180))).thenReturn(categoria);
	}

	@Test
	public void puedeCalcularCategoria() {
		Assert.assertEquals(categoria, determinador.calcularCategoria(new BigDecimal(180)));
	}
//
//	@After
//	public void resetearCategorias() {
//		repCat.reiniciar();
//	}

}
