package showcase.dw.glbg;
/**
 * @ClassName:     Slf4jTest.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年8月30日 上午11:25:29 
 */
public class Slf4jTest {

	public static void main(String[] args){
		System.out.println(getClassResource(org.slf4j.spi.LocationAwareLogger.class));
	}
	public static String getClassResource(Class<?> klass) {
		           return klass.getClassLoader().getResource(
		              klass.getName().replace('.', '/') + ".class").toString();
		    }
}
