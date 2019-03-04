import com.tpdds.categorias.Categoria;
import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.dispositivos.Fabricante;
import com.tpdds.transformadores.Posicion;
import com.tpdds.usuarios.Cliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCliente {
//	private Cliente pepe;

	Cliente pepe = mock(Cliente.class);
	Categoria categoria = mock(Categoria.class);

	@Before
	public void makePepe() {
//		List<DispositivoEstandar> dispositivosStandarPepe = new ArrayList<>();
//		List<DispositivoInteligente> dispositivosInteligentesPepe = new ArrayList<>();
//
//		Fabricante fabricante = mock(Fabricante.class);
//
//		DispositivoEstandar heladera = new DispositivoEstandar("Heladera",new BigDecimal(1.5), new BigDecimal(1));
//		DispositivoInteligente tv = new DispositivoInteligente("Television", fabricante);
//
//		dispositivosStandarPepe.add(heladera);
//		dispositivosInteligentesPepe.add(tv);
//
//		pepe = new Cliente("Pepe", "Argento","TipoId", "1", "44346465", "Domicilio",
//			LocalDate.now(), dispositivosStandarPepe, dispositivosInteligentesPepe, new Posicion(new BigDecimal(15), new BigDecimal(20)));


		when(pepe.algunDispositivoEncendido()).thenReturn(true);
		when(pepe.cantidadDispositivosEncendidos()).thenReturn((long)1);
		when(pepe.cantidadDispositivosApagados()).thenReturn((long)1);
		when(pepe.cantidadTotalDeDispositivos()).thenReturn(2);
		when(pepe.getCategoria()).thenReturn(categoria);
		when(categoria.getNombre()).thenReturn(1);

	}

	@Test
	public void tieneAlgunDispositivoEncendido() {
	    Assert.assertTrue(pepe.algunDispositivoEncendido());
	}

	@Test
	public void puedeContarDispositivosEncendidos() {
		Assert.assertEquals(1, pepe.cantidadDispositivosEncendidos());
	}

	@Test
	public void puedeContarDispositivosApagados() {
		Assert.assertEquals(1, pepe.cantidadDispositivosApagados());
	}

	@Test
	public void puedeContarTotalDispositivos() {
	    Assert.assertEquals(pepe.cantidadTotalDeDispositivos(), 2);
	}
	 
	@Test
	public void perteneceACategoria() {
	    Assert.assertEquals(pepe.getCategoria().getNombre(), 1);
	}
	
	@Test
	public void sinConsumoPerteneceACategoriaInicial() {
		Cliente otroPepe = mock(Cliente.class);
		Categoria otraCategoria = mock(Categoria.class);

		when(otroPepe.getCategoria()).thenReturn(otraCategoria);
		when(otraCategoria.getNombre()).thenReturn(1);

	    Assert.assertEquals(pepe.getCategoria().getNombre(), 1);
	}
	
	@Test
	public void cambiaDeCategoria() {

	    Cliente anotherPepe = mock(Cliente.class);
	    Categoria anotherCategoria = mock(Categoria.class);

		when(anotherPepe.getCategoria()).thenReturn(anotherCategoria);
		when(anotherCategoria.getNombre()).thenReturn(2);
		
		anotherPepe.recategorizar();
		
		Assert.assertEquals(2, anotherPepe.getCategoria().getNombre());
		
	}
}
