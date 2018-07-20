package valar.showcase.hive.udf.sample;

import java.util.ArrayList;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class ExplodeMapUDTF extends GenericUDTF{

	@Override
	public void close() throws HiveException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
		// TODO Auto-generated method stub
		if (args.length  != 1) {
		   throw new UDFArgumentException("argument length exception!");	
		}
		if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
			throw new UDFArgumentException("ExplodeMap function takes string as parameter!");
		}
		ArrayList<String> fieldNames = new ArrayList<String>();
		ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();
		fieldNames.add("column1");
		fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		fieldNames.add("column2");
		fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
	}

	@Override
	public void process(Object[] args) throws HiveException {
		// TODO Auto-generated method stub
		String input = args[0] == null ? "":args[0].toString();
		String[] test = input.split(";");
		for(String item : test) {
			try {
				String[] result = item.split(":");
				forward(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
		
		
	}

}
