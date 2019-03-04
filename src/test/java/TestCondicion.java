import com.tpdds.dispositivos.OptimizacionDispositivo;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.reglas.condiciones.CondicionFisica;
import com.tpdds.reglas.condiciones.CondicionSimplex;
import com.tpdds.reglas.condiciones.Filtro;
import com.tpdds.reglas.sensores.SensorBase;
import com.tpdds.reglas.sensores.SensorSimplex;
import com.tpdds.repositorios.RepositorioCorridaSimplex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCondicion {

    @Before
    public void prepararRepoSimplex() {
        RepositorioCorridaSimplex.getInstance().reiniciar();
    }

    @Test
    public void condicionFisica() {
        SensorBase sensor = mock(SensorBase.class);
        when(sensor.sensar()).thenReturn(new BigDecimal(20));

        Filtro filtro = new Filtro(Filtro.Comparacion.EQ, 20);
        CondicionFisica condicionFisica = new CondicionFisica(sensor, filtro);

        Assert.assertTrue(condicionFisica.evaluar());
    }

    @Test
    public void condicionFisicaFalla() {
        SensorBase sensor = mock(SensorBase.class);
        when(sensor.sensar()).thenReturn(new BigDecimal(30));

        Filtro filtro = new Filtro(Filtro.Comparacion.EQ, 20);
        CondicionFisica condicionFisica = new CondicionFisica(sensor, filtro);

        Assert.assertFalse(condicionFisica.evaluar());
    }

    private CondicionSimplex prepararCondicionSimplex(int horasPrendido, int horasFiltro) {
        DispositivoInteligente dispositivoInteligente = mock(DispositivoInteligente.class);
        when(dispositivoInteligente.getHorasPrendido()).thenReturn(new BigDecimal(horasPrendido));

        SensorSimplex sensor = new SensorSimplex(dispositivoInteligente);

        RepositorioCorridaSimplex.getInstance().agregar(new OptimizacionDispositivo(dispositivoInteligente, new BigDecimal(horasFiltro)));

        return new CondicionSimplex(sensor, dispositivoInteligente);
    }

    @Test
    public void condicionSimplex() {
        CondicionSimplex condicionSimplex = prepararCondicionSimplex(5, 5);

        Assert.assertTrue(condicionSimplex.evaluar());
    }

    @Test
    public void condicionSimplexFalla() {
        CondicionSimplex condicionSimplex = prepararCondicionSimplex(7, 10);

        Assert.assertFalse(condicionSimplex.evaluar());
    }
}
