import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.reglas.sensores.SensorFisico;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TestSensor {
    private DispositivoInteligente mockedDispositivo = mock(DispositivoInteligente.class);

    @Before
    public void fixture() {
        when(mockedDispositivo.comando("solicitar_temperatura")).thenReturn("20");
        when(mockedDispositivo.comando("aumentar_temperatura")).thenReturn("ok");
    }

    @Test
    public void sensorComun() {
        SensorFisico s = new SensorFisico(mockedDispositivo, "solicitar_temperatura");

        Assert.assertEquals(20, s.sensar().doubleValue(), 0.1f);
    }
}
