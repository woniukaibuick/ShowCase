package basis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class StringTest {
	
	@Test
	public void testIntegerCache(){
		Integer a = new Integer(100);
		Integer b = new Integer(100);
		
		Integer c = 127;
		Integer d = 127;
		
		System.out.println(a==b);
		System.out.println("c==d:"+(c==d));
		
		Float m = 1f;
		Float n = 1f;
		System.out.println("float :"+(m==n));
	}
	
	@Test
	public void testString(){
		String s0 = "kvill";

		String s1 = new String("kvill");

		String s2 = new String("kvill");

		String s3 = "kvill";
		
		String s4 = "kvi"+"ll";
		System.out.println("s0==s1:" + (s0 == s1));
		System.err.println("s0==s2:" + (s0 == s2));
		System.err.println("s1==s2:" + (s1 == s2));
		System.err.println("s0==s3:" + (s0 == s3));
		System.err.println("s0 equals s1:"+s0.equals(s1));

		s1.intern();

		s2 = s2.intern(); // 把常量池中“kvill”的引用赋给s2
        System.out.println("after s2.intern....");
		System.out.println("s0 == s1:"+(s0 == s1));

		System.out.println("s0 == s1.intern():"+(s0 == s1.intern()));

		System.out.println("s0 == s2:"+(s0 == s2));
		
		System.out.println("s0 == s4:"+(s0 == s4));
		
	}
	
	@Test
	public void testTryFinallyReturn(){
		String s = "Aello world";
	    System.out.println(s.codePointAt(0));;
	    
	    try {
			System.out.println("try");
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}  finally {
			System.out.println("finally");
		}
	}
	
	@Test
	public void testMapKey(){
		Map<String,Integer> a = new HashMap<String,Integer>();
		a.put(new String("a"),1 );
		a.put(new String("a"),11 );
		a.put(new String("a"),1111 );
		System.out.println(a.size());
		
		
		Integer m = 100;
		System.out.println(m.hashCode());
		
		String[] arr = new String[]{"c","d","b"};
		
		Arrays.sort(arr);
		
		System.out.println(arr);
		
		short s1 = 1; s1 +=  1;
	}
	
	@Test
	public void testClassLoader() throws ClassNotFoundException{
		Class.forName("algorithm.TopK");
		ClassLoader t = null; t.loadClass("");
	}
	
	
	@Test
	public void testTryErrorFinally(){
		/**
		 * ouput:
		 try
         finally
		 */
		try {
			System.out.println("try");
			throw new Error("runtime error");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception:"+e.toString());
		} finally {
			System.out.println("finally");
		}
	}
	
	@Test
	public void testOOM(){
		System.out.println(retI(Integer.MAX_VALUE, Integer.MIN_VALUE));
	}
	public int retI(int a,int b ){
		return a-b;
	}
	
	@Test
	public void testStringBytesLength(){
		String s = "hi";
		System.out.println(s.getBytes().length);
	}

}
