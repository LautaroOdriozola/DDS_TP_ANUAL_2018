import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.usuarios.Administrador;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;


	public class TestCaso2 implements WithGlobalEntityManager, TransactionalOps {
		DispositivoEstandar estandar = mock(DispositivoEstandar.class);
		DispositivoInteligente inteligente = mock(DispositivoInteligente.class);
		DispositivoEstandar dispositivo = mock(DispositivoEstandar.class);

		Administrador mockedAdministrator = mock(Administrador.class);

 	@Before
 	public void persistoElementos() {
 		 withTransaction(() -> {
 		estandar = new DispositivoEstandar("Heladera", new BigDecimal(10), new BigDecimal(15), new BigDecimal(33), new BigDecimal(90));
 		inteligente = new DispositivoInteligente("TV", null);
   
 		entityManager().persist(estandar);
 		entityManager().persist(inteligente);
 		 });
 	}
 
 	@Test
 	public void muestroIntervalo() {
 		mockedAdministrator = new Administrador("Juan", "Perez", "Calle falsa 123", LocalDate.now(), (long) 3, "username2");
 		dispositivo = entityManager().find(DispositivoEstandar.class, estandar.getId());
  
 		BigDecimal consumoMensual = dispositivo.getConsumoMensual();
  // BigDecimal consumoMensual = dispositivo.pedirConsumo(LocalDate.now().minus(amountToSubtract),LocalDate.now());

 		System.out.println("Dispositivo: "+ dispositivo.getNombre() + "  "+ "Consumo: " + consumoMensual);  
 	} 
 
 	public void cambioNombre() {
 		 withTransaction(() -> {
  
 		dispositivo.setNombre("Heladera Con freezer");
  
 		mockedAdministrator.modificarDispositivo(estandar, dispositivo);
 		
 		entityManager().persist(dispositivo);
 	});
 	}
 
 	public void recuperoYPruebo() {
 		 withTransaction(() -> {
  
 		DispositivoEstandar nuevo = entityManager().find(DispositivoEstandar.class, dispositivo.getId());
  
 		Assert.assertEquals(nuevo.getNombre(), "Heladera Con freezer");  
  
 	});
 	}
 
}