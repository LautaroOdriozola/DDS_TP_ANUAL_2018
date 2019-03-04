import com.tpdds.transformadores.Posicion;
import com.tpdds.transformadores.Transformador;
import com.tpdds.transformadores.Zona;
import com.tpdds.usuarios.Cliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestZona {
    Zona zona = mock(Zona.class);
    Transformador transformador = mock(Transformador.class);
    Posicion posicion = mock(Posicion.class);
    Cliente cliente = mock(Cliente.class);
    BigDecimal consumo = new BigDecimal(500);

    @Before
    public void fixture () {
        when(zona.masCercanoA(cliente, transformador)).thenReturn(true);
        when(zona.estaEnZona(cliente)).thenReturn(true);
        when(zona.obtenerConsumoActual()).thenReturn(consumo);
    }

    // TODO: revisar tests
    @Test
    public void transformadorCercanoACliente() {
        Assert.assertEquals(zona.masCercanoA(cliente, transformador), true);
    }

    @Test
    public void clienteEstaEnLaZona() {
        Assert.assertTrue(zona.estaEnZona(cliente));
    }

    @Test
    public void consumoActualDeLaZona() {
        Assert.assertEquals(zona.obtenerConsumoActual(), consumo);
    }
}
