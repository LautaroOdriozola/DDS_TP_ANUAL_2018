import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.usuarios.Administrador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestAdministrador implements WithGlobalEntityManager {
	
	Administrador mockedAdministrator = mock(Administrador.class);
	
	@Before
	
	public void cargarFechaDeAlta() {
		
		mockedAdministrator.getMesesAdministrador();
		mockedAdministrator.getNombre();
	}
	
	@Test
	public void puedoConocerLosMesesDeAdministrador() {
		verify(mockedAdministrator).getMesesAdministrador();
		
	}
	
	@Test	
	public void puedoConocerElNombre() { 
		verify(mockedAdministrator).getNombre();
	
	}
	
	@Test
	public void getMesesAdministrador() {
		
		mockedAdministrator = new Administrador("Juan", "Perez", "calle falsa 123", LocalDate.of(2001, Month.JANUARY, 1), 5, "username2");
		
		long cantidadDeMeses = ChronoUnit.MONTHS.between(mockedAdministrator.getFechaDeAlta(), LocalDate.now());
		Assert.assertEquals(cantidadDeMeses, mockedAdministrator.getMesesAdministrador());
	}

	@Test
	public void puedoAgregarDispositivos() {
		DispositivoInteligente inteligente = new DispositivoInteligente("aireAcondicionado", null);
		mockedAdministrator = new Administrador("Juan", "Perez", "calle falsa 123", LocalDate.of(2001, Month.JANUARY, 1), 5, "username2");
		mockedAdministrator.crearDispositivo(inteligente);
		entityManager().getTransaction().begin();
		DispositivoInteligente inteligentedb= entityManager().find(DispositivoInteligente.class,inteligente.getId());
		Assert.assertEquals(inteligente,inteligentedb);
		entityManager().getTransaction().commit();

	}

	public void puedoModificarDispositivos() {
		entityManager().getTransaction().begin();
		DispositivoEstandar estandarViejo = new DispositivoEstandar("Heladera", new BigDecimal(10), new BigDecimal(15),new BigDecimal(33), new BigDecimal(90));
		DispositivoEstandar estandarNuevo = new DispositivoEstandar("Heladera", new BigDecimal(20), new BigDecimal(30),new BigDecimal(66), new BigDecimal(180));
		entityManager().persist(estandarViejo);
		DispositivoEstandar estandarAModificar= entityManager().find(DispositivoEstandar.class, estandarViejo.getId());
		estandarAModificar.alterDispositivo(estandarNuevo);
		entityManager().getTransaction().commit();
		Assert.assertEquals(estandarNuevo,entityManager().find(DispositivoEstandar.class,estandarAModificar.getId()));
	}
	
	public void detachDispositivo() {
		entityManager().getTransaction().begin();
		DispositivoInteligente dispositivoInt= entityManager().find(DispositivoInteligente.class, new Long(5));
		entityManager().detach(dispositivoInt);
		entityManager().getTransaction().commit();
		entityManager().getTransaction().begin();
		DispositivoInteligente disp= entityManager().find(DispositivoInteligente.class, new Long(5));
		Assert.assertNull(disp);
		entityManager().getTransaction().commit();
		}

	
}
