import com.tpdds.dispositivos.DispositivoInteligente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import static org.mockito.Mockito.*;

public class TestDispositivoInteligente {
	private DispositivoInteligente mockedDispositivo = mock(DispositivoInteligente.class);

	@Before
	public void fixture() {
		when(mockedDispositivo.comando("Decime la temperatura")).thenReturn("La temperatura es baja");
	}

	/*
	@Test
	public void encenderAunqueEstePrendido() {
		Assert.assertEquals(true, smartTv.getEstado());
	}

	@Test
	public void apagarAunqueEsteApagado() {
		Assert.assertEquals(true,! smartTv.getEstado());
	}

	@Test
	public void puedoObtenerModo() {
		Assert.assertEquals(Modo.APAGADO,smartTv.getModo());
	}

	@Test
	public void energiaConsumidaDurante() {
		Assert.assertEquals(new BigDecimal(15),smartTv.energiaConsumidaDurante(5));
	}

	@Test
	public void enviarComando() {
		Assert.assertEquals("La temperatura es baja",mockedDispositivo.enviarComando("Decime la temperatura"));
	}
	*/
}
