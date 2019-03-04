import com.tpdds.transformadores.Posicion;
import com.tpdds.transformadores.Transformador;
import com.tpdds.transformadores.Zona;
import com.tpdds.usuarios.Cliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class TestTransformador {
    Transformador transformador = mock(Transformador.class);
    BigDecimal consumo = new BigDecimal(500);

    @Before
    public void fixture() {
        when(transformador.energiaSuministrada()).thenReturn(consumo);
    }

    @Test
    public void energiaSuministradaTransformador() {
        Assert.assertEquals(transformador.energiaSuministrada(), consumo);
    }

}
