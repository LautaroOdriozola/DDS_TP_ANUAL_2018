import com.tpdds.dispositivos.DispositivoEstandar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class TestDispositivoEstandar {
//	private DispositivoEstandar heladera =
//			new DispositivoEstandar("heladera",new BigDecimal(3.4),new BigDecimal(3));
//

	DispositivoEstandar heladera = mock(DispositivoEstandar.class);
	BigDecimal consumo = new BigDecimal(10.2);

	@Before
	public void fixture() {
		when(heladera.getConsumo()).thenReturn(consumo);
		when(heladera.getNombre()).thenReturn("Heladera");
	}

	@Test
	public void puedoConocerNombre() {
		Assert.assertEquals("Heladera", heladera.getNombre());
	}

	@Test
	public void puedoConocerConsumo() {
	    Assert.assertEquals(consumo, heladera.getConsumo());
	}

}
