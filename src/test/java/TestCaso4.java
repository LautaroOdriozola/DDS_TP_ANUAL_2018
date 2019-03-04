import org.junit.Assert;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


public class TestCaso4 implements WithGlobalEntityManager, TransactionalOps{
	
	public void cargoTransformadores(String archivo) {
		
	}
	
	public void cuentoTransformadores() {
		 withTransaction(() -> {
		Long count = (Long) entityManager().createNativeQuery("select count(1) from Tansformador")
                   .getSingleResult();
		Assert.assertEquals(count, new Long(0));
	});
	}
}