import com.tpdds.categorias.Categoria;
import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.transformadores.Posicion;
import com.tpdds.usuarios.Cliente;
import com.tpdds.repositorios.*;

import org.junit.Assert;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;

public class TestCaso1 implements WithGlobalEntityManager, TransactionalOps {
	Cliente mockedCliente = mock(Cliente.class);
 	Categoria mockedCategoria = mock(Categoria.class);
 	DispositivoEstandar estandar = mock(DispositivoEstandar.class);
 	DispositivoInteligente inteligente = mock(DispositivoInteligente.class);

 
 	@Test
 	public void persistoCliente() {
 
 		 withTransaction(() -> {
	 
	 // instancio categoria, repo categoria, cliente y dispositivos 
	 mockedCategoria = new Categoria(1, new BigDecimal(20),new BigDecimal(10), new BigDecimal(0), new BigDecimal(10000));
	 RepositorioCategorias.getInstance().agregar(mockedCategoria);	 
	 mockedCliente = new Cliente("Pepe",
			 "Argento",
			 "TipoId",
			 "1",
			 "44346465",
			 "Domicilio",
			 LocalDate.now(),
			 new Posicion(new BigDecimal(15), new BigDecimal(20)),
			 true,
			 "username1");
	 estandar = new DispositivoEstandar("Heladera", new BigDecimal(10), new BigDecimal(15), new BigDecimal(33), new BigDecimal(90));
	 inteligente = new DispositivoInteligente("TV", null);

	 // agrego dispositivos al cliente 
	 mockedCliente.agregarDispositivo(estandar);
	 mockedCliente.agregarDispositivoInteligente(inteligente);
	 entityManager().persist(estandar);
     entityManager().persist(inteligente);
 	 mockedCliente.setPosicion(new BigDecimal(12), new BigDecimal(7)); 

	 entityManager().persist(mockedCliente);
 		 });
	 
 	}
 	
 	public void recuperoCliente() {
 		
 		 withTransaction(() -> {
	Cliente cliente = entityManager().find(Cliente.class, mockedCliente.getID());
	//chequeo mocked = al que busca el entity manager
	Assert.assertEquals(cliente,mockedCliente);
	cliente.setPosicion(new BigDecimal(24), new BigDecimal(15)); 
 	});
 	}
 	public void chequeoClientePersistido() {

 		 withTransaction(() -> {
	//recupero cliente con geolocalizacion cambiada
	Cliente clientebd = entityManager().find(Cliente.class, mockedCliente.getID());
	//le agrego al mocked la posicion del que persisti para al recuperarlo de la db comparar que sea haya persistido bien 
	mockedCliente.setPosicion(new BigDecimal(24), new BigDecimal(15));
	Assert.assertEquals(mockedCliente,clientebd);
 	entityManager().persist(clientebd);
 	});}

	 
	
 	}
 
 