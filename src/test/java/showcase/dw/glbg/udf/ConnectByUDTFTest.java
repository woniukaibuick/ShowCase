package showcase.dw.glbg.udf;

import static org.junit.Assert.*;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Test;

import valar.showcase.hive.udf.connectby.ConnectByUDTF;


public class ConnectByUDTFTest {

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

	@Test
	public void testInitializeObjectInspectorArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessObjectArray() {
		fail("Not yet implemented");
		
	}
	@Test
	public void testMultipleTree() throws HiveException {
		int[] idsample = new int[]{5,6,7,4,3,2,1,8,9,11};
		int[] parentidssample = new int[]{4,4,4,3,2,1,0,0,8,9};
		ConnectByUDTF cb = new ConnectByUDTF();
		for (int i = 0; i < idsample.length; i++) {
			Object[] data = new Object[] {idsample[i],parentidssample[i],"true",100};
			cb.process(data);
		}
		cb.close();
	}
	@Test
	public void testBugs() throws HiveException {		
		String[] idsample = new String[]{"A001"	,"B001"	,"C001"	,"D001"	,"E001"	,"F001"	};
		String[] parentidssample = new String[]{null,"A001","A001","B001","B001","E001"};
		ConnectByUDTF cb = new ConnectByUDTF();
		for (int i = 0; i < idsample.length; i++) {
			Object[] data = new Object[] {idsample[i],parentidssample[i],"true",100};
			cb.process(data);
		}
		cb.close();
	}
	/**
	 * if there is repeat elements , the later will rewrite the before<br>
	 * default consider an element has only one parent
	 * @throws HiveException
	 */
	@Test
	public void testRepeatElements() throws HiveException {
		int[] idsample = new int[]{7,7,6,30,29,28,5,4,3,2,1};
		int[] parentidssample = new int[]{4,3,4,29,28,27,4,3,2,1,0};
		ConnectByUDTF cb = new ConnectByUDTF();
		for (int i = 0; i < idsample.length; i++) {
			Object[] data = new Object[] {idsample[i],parentidssample[i],"true",100};
			cb.process(data);
		}
		cb.close();
	}

}