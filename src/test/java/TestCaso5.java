import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import com.tpdds.categorias.Categoria;
import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.repositorios.RepositorioCategorias;
import com.tpdds.transformadores.Posicion;
import com.tpdds.usuarios.Cliente;

public class TestCaso5 implements WithGlobalEntityManager, TransactionalOps{
	
    public DispositivoInteligente crearMock(LocalDate fechaIn,LocalDate fechaFin) {
	
 	DispositivoInteligente inteligente = mock(DispositivoInteligente.class);
    when(inteligente.pedirConsumo(fechaIn,fechaFin)).thenReturn(new BigDecimal(20));
    return inteligente;
    }
    
 	@Test
 	public void persistoCliente() {
 	DispositivoInteligente inteligente = crearMock(LocalDate.of(2018, Month.SEPTEMBER, 10),LocalDate.now());
 	Cliente mockedCliente = mock(Cliente.class);
 	Categoria mockedCategoria = mock(Categoria.class);
 	DispositivoEstandar estandar = mock(DispositivoEstandar.class);
	entityManager().getTransaction().begin();
	 mockedCategoria = new Categoria(1, new BigDecimal(20),new BigDecimal(10), new BigDecimal(0), new BigDecimal(10000));
	 RepositorioCategorias.getInstance().agregar(mockedCategoria);	 
	 mockedCliente = new Cliente("Pepe", "Argento","TipoId", "1", "44346465", "Domicilio", LocalDate.now(), new Posicion(new BigDecimal(15), new BigDecimal(20)), true, "username1");
	 estandar = new DispositivoEstandar("Heladera", new BigDecimal(10), new BigDecimal(15), new BigDecimal(33), new BigDecimal(90));
	 inteligente = new DispositivoInteligente("TV", null);
	 mockedCliente.agregarDispositivo(estandar);
	 mockedCliente.agregarDispositivoInteligente(inteligente);
	 entityManager().persist(estandar);
     entityManager().persist(inteligente);
 	 mockedCliente.setPosicion(new BigDecimal(12), new BigDecimal(7));
	 entityManager().persist(mockedCliente);
	 entityManager().getTransaction().commit(); 
 	}
 	

 	/*
 	public void recuperoCliente() {
 		
 		 withTransaction(() -> {
	Cliente cliente = entityManager().find(Cliente.class, mockedCliente.getID());
	//chequeo mocked = al que busca el entity manager
	Assert.assertEquals(cliente,mockedCliente);
	BigDecimal consumo= cliente.calcularConsumoEnIntervalo(LocalDate.of(2018, Month.SEPTEMBER, 10),LocalDate.now());
//consumo del estandar 30 kw y del inteligente 20 total 50
	System.out.println("consumoTotal: "+ consumo);
 	});
 		 }
 		 */
}
