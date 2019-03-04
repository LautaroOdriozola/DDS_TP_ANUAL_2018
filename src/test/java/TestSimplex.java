import com.tpdds.dispositivos.Dispositivo;
import com.tpdds.usuarios.CalculadoraConsumo;
import com.tpdds.usuarios.Cliente;
import com.tpdds.usuarios.ResultadoConsumo;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSimplex {

    private Dispositivo crearMock(int min, int max, double consumo, String nombre) {
        Dispositivo d = mock(Dispositivo.class);
        when(d.getHorasMinimas()).thenReturn(new BigDecimal(min));
        when(d.getHorasMaximas()).thenReturn(new BigDecimal(max));
        when(d.getConsumoMensual()).thenReturn(new BigDecimal(consumo));
        when(d.getNombre()).thenReturn(nombre);

        return d;
    }

    private Cliente crearCliente() {
        ArrayList<Dispositivo> mockDispositivoList = new ArrayList<>();
        Dispositivo mockAire = crearMock(90, 360, 1.013, "aire");
        Dispositivo mockLampara = crearMock(90, 360, 0.011, "lampara");
        Dispositivo mockTele = crearMock(90, 360, 0.08, "tele");
        Dispositivo mockCompu = crearMock(60, 360, 0.4, "compu");
        Dispositivo mockLavarropas = crearMock(60, 360, 0.1275, "lavarropas");
        Dispositivo mockMicroondas = crearMock(6, 30, 0.64, "microondas");
        Dispositivo mockPlancha = crearMock(3, 15, 0.75, "plancha");
        Dispositivo mockVentilador = crearMock(120, 360, 0.06, "ventilador");

        mockDispositivoList.add(mockAire);
        mockDispositivoList.add(mockLampara);
        mockDispositivoList.add(mockTele);
        mockDispositivoList.add(mockCompu);
        mockDispositivoList.add(mockLavarropas);
        mockDispositivoList.add(mockMicroondas);
        mockDispositivoList.add(mockPlancha);
        mockDispositivoList.add(mockVentilador);

        Cliente mockCliente = mock(Cliente.class);
        when(mockCliente.getAllDispositivos()).thenReturn(mockDispositivoList);

        return mockCliente;
    }

    private ResultadoConsumo generarSolucion(Cliente cliente) {
        return new CalculadoraConsumo().calcular(cliente);
    }

    @Test
    public void consumoMaximoNoSePasaDelLimite() {
        Cliente cliente = crearCliente();
        ResultadoConsumo solucion = generarSolucion(cliente);

        System.out.println(solucion.getConsumoMaximo());

        Assert.assertTrue(solucion.getConsumoMaximo() <= 612.1); // por un tema de precision de bigdecimal->double
    }

    @Test
    public void cadaDispositivoEstaDentroDelRango() {
        Cliente cliente = crearCliente();
        ResultadoConsumo solucion = generarSolucion(cliente);

        cliente.getAllDispositivos().forEach(dispositivo -> {
            final double limiteCalculado = solucion.getLimiteParaDispositivo(dispositivo);

            Assert.assertTrue("Falló límite inferior para dispositivo: " + dispositivo.getNombre(),
                    limiteCalculado <= dispositivo.getHorasMaximas().doubleValue());
            Assert.assertTrue("Falló límite superior para dispositivo: " + dispositivo.getNombre(),
                    limiteCalculado >= dispositivo.getHorasMinimas().doubleValue());
        });
    }

    @Test
    public void creaCantidadCorrectaDeRestricciones() {
        ArrayList<Dispositivo> dispositivos = crearCliente().getAllDispositivos();
        ArrayList<LinearConstraint> constraints =
                new CalculadoraConsumo().crearTodasLasContstraints(dispositivos);

        Assert.assertEquals(constraints.size(), dispositivos.size() * 2 + 1);
    }
}
