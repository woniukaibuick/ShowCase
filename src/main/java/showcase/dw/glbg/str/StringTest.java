package showcase.dw.glbg.str;

import java.util.StringTokenizer;

import org.junit.Test;

/**
 * @Title: StringTest.java
 * @Package showcase.dw.glbg.str
 * @Description: TODO(description)

 * @date 2017年10月6日 下午3:32:57
 */
public class StringTest {

	@Test
	public void testStaticMethods() {
		String str = "hello";
		System.err.println(str.valueOf(false));
		System.err.println(str.trim());
		System.err.println(str.substring(0, 2).toCharArray()[0] = '1');
		System.err.println(str);
	}

	@Test
	public void testCOnstants() {

		String s0 = "kvill";

		String s1 = new String("kvill");

		String s2 = new String("kvill");

		String s3 = "kvill";
		System.out.println("s0==s1:" + (s0 == s1));
		System.err.println("s0==s2:" + (s0 == s2));
		System.err.println("s1==s2:" + (s1 == s2));
		System.err.println("s0==s3:" + (s0 == s3));
		System.err.println("s0 equals s1:"+s0.equals(s1));

		s1.intern();

		s2 = s2.intern(); // 把常量池中“kvill”的引用赋给s2

		System.out.println(s0 == s1);

		System.out.println(s0 == s1.intern());

		System.out.println(s0 == s2);
		
		
		StringTokenizer s= null;
	}
	
	
	@Test
	public void testStringTokenizer() {
        String str = " hello,java,delphi,asp,php";
        StringTokenizer st=new StringTokenizer(str,",");
        while(st.hasMoreTokens()) { 
            System.out.println(st.nextToken());
        }
	}
	
	
	@Test
	public void testOthers() {
		String str = null;
	}

}
