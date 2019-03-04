/*

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;

import com.tpdds.dispositivos.DispositivoInteligente;
import com.tpdds.reglas.Actuador;
import com.tpdds.reglas.condiciones.CondicionFisica;
import com.tpdds.reglas.condiciones.Filtro;
import com.tpdds.reglas.condiciones.Filtro.Condicion;
import com.tpdds.reglas.Regla;
import com.tpdds.reglas.sensores.SensorFisico;

public class TestCaso3 implements WithGlobalEntityManager, TransactionalOps {
	Regla reglaMocked= mock(Regla.class);
 	DispositivoInteligente inteligenteMocked = mock(DispositivoInteligente.class);
 	Actuador actuadorMocked=mock(Actuador.class);
 	Filtro filtroMocked= mock(Filtro.class);
 	CondicionFisica condicionMocked= mock(CondicionFisica.class);
 	SensorFisico sensorMocked = mock(SensorFisico.class);
 	 
 	@Before
 	
 	public void crearDispositivo() {
 		entityManager().getTransaction().begin();
 		inteligenteMocked= new DispositivoInteligente("heladera",null);
 		entityManager().update(inteligenteMocked);
 		entityManager().getTransaction().commit();
 	}
 	
 	public void crearSensor() {
 		entityManager().getTransaction().begin();
        sensorMocked= new SensorFisico(inteligenteMocked, "solicitar_temperatura");
        entityManager().update(sensorMocked);
        entityManager().getTransaction().commit();
 	}
 	
 	public void crearFiltro() {
 		entityManager().getTransaction().begin();
 		Condicion condicion = Condicion.EQ;
 		entityManager().getTransaction().begin();
 		filtroMocked=new Filtro(condicion,(long)10);
        entityManager().update(filtroMocked);
 		entityManager().getTransaction().commit();
 	}
 	
 	public void crearCondicion() {
 		entityManager().getTransaction().begin();
 		condicionMocked= new CondicionFisica(sensorMocked,filtroMocked);
 		entityManager().update(condicionMocked);
 		entityManager().getTransaction().commit();
 	}
 	
 	public void crearActuador() {
 		entityManager().getTransaction().begin();
 		actuadorMocked= new Actuador(inteligenteMocked,"solicitar_temperatura");
 		entityManager().update(actuadorMocked);
 		entityManager().getTransaction().commit();
 	}
 	
 	public void crearRegla(){
 		entityManager().getTransaction().begin();
		reglaMocked = new Regla(condicionMocked,actuadorMocked);
		entityManager().update(reglaMocked);
		entityManager().getTransaction().commit();
		
		
		
	}
}

*/
