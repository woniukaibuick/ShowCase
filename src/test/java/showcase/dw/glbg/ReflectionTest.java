package showcase.dw.glbg;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionTest {
	
	@Test
	public void testReflectionInstance() throws InstantiationException, IllegalAccessException{
		ReflectionTest a  = ReflectionTest.class.newInstance();
		ReflectionTest b  = ReflectionTest.class.newInstance();
		System.out.println(a == b);
		Assert.assertTrue(a==b);
	}
	@Test
	public void testReflectionSingletonInstance() throws InstantiationException, IllegalAccessException{
		SingletonClass a  = SingletonClass.class.newInstance();
		SingletonClass b  = SingletonClass.class.newInstance();
		System.out.println(a == b);
		Assert.assertTrue(a==b);
	}

}
class SingletonClass {
	private static SingletonClass instance;
	public static synchronized SingletonClass getInstance(){
		if (instance == null) {
			instance = new SingletonClass();
		}
		return instance;
	}
}
