package valar.showcase.hive.udf.connectby.deprecated;

import org.apache.hadoop.hive.ql.metadata.HiveException;

public class UnitTest {
	public static void main(String[] args) throws HiveException {
		int[] idsample = new int[]{7,6,5,4,3,2,1};
		int[] parentidssample = new int[]{4,4,4,3,2,1,0};
		ConnectByUDTF cb = new ConnectByUDTF();
		for (int i = 0; i < idsample.length; i++) {
			Object[] data = new Object[] {idsample[i],parentidssample[i],"true",6};
			cb.process(data);
			
		}
	}

	

}
