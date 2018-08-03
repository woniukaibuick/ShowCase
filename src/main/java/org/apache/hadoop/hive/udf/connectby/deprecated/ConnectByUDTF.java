package org.apache.hadoop.hive.udf.connectby.deprecated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class ConnectByUDTF  extends GenericUDTF {

	private static List dataList = new ArrayList();
	private static final boolean IS_LEVEL_NEEDED = true;
	private static long  START_BUILD_TREE_NUM = -1;
	private static boolean PARSE_FLAG = true;
	private static long  CURRENT_NUM = 0;
	private static List<String> ids = new ArrayList<String>(); 
	private static List<String> parentids = new ArrayList<String>(); 
	private MultipleTree Mtree = null;
	@Override
	public void close() throws HiveException {
		// TODO Auto-generated method stub
		dataList.clear();
		ids.clear();
		parentids.clear();
		START_BUILD_TREE_NUM = -1;
		PARSE_FLAG = true;
		Mtree = null;
		CURRENT_NUM = 0;
	}

	@Override
	public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
		// TODO Auto-generated method stub
		if (args.length  != 4) {
			   throw new UDFArgumentException("Argument length exception,\"connect by(id,parent_id,'true',number)\" take 3 parameters,"
			   		+ "if number greater than actual data counts, will go exception and return no data!");	
			}
			if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
				throw new UDFArgumentException("ConnectByUDTF function takes string as parameter!");
			}
			if (args[1].getCategory() != ObjectInspector.Category.PRIMITIVE) {
				throw new UDFArgumentException("ConnectByUDTF function takes string as parameter!");
			}
			
			
			ArrayList<String> fieldNames = new ArrayList<String>();
			ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();
			fieldNames.add("ID");
			fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
			fieldNames.add("PS_ID");
			fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
			fieldNames.add("PS_LEVEL");
			fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
			return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
	}
	/**
	 * para1 must be id,
	 * para2 must be parentId,
	 * para4 must be true/false
	 */
	@Override
	public void process(Object[] args) throws HiveException {
		// TODO Auto-generated method stub
		String id = args[0] == null ? "":args[0].toString();
		String parentId = args[1] == null ? "":args[1].toString();
		String falg = args[2] == null ? "":args[2].toString();
		if (PARSE_FLAG) {
			ids.clear();
			parentids.clear();
			try {
				START_BUILD_TREE_NUM = Long.parseLong(args[3].toString());
			} catch (Exception e) {
				// TODO: handle exception
				START_BUILD_TREE_NUM = -1;
			}
		}
		PARSE_FLAG = false;
		CURRENT_NUM++;
		
		if (START_BUILD_TREE_NUM != -1) {
			ids.add(id);
			parentids.add(parentId);
		}
		
		HashMap dataRecord = new HashMap();
		dataRecord.put("id", id);
		dataRecord.put("text", "");
		dataRecord.put("parentId", parentId);
		dataList.add(dataRecord);
		
		
		if(START_BUILD_TREE_NUM == -1  || CURRENT_NUM >= START_BUILD_TREE_NUM) {
			autoInitMtree();
		}
		boolean isParent = falg.equalsIgnoreCase("true")?true:false;
		
		//if wait until START_BUILD_TREE_NUM, then all before ids should be recorded, and calculate again.
		if(CURRENT_NUM == START_BUILD_TREE_NUM && !ids.isEmpty()  ) {
			for(int i = 0; i< ids.size();i++) {
				calculate(ids.get(i), parentids.get(i), isParent);
			}
		}
		if(START_BUILD_TREE_NUM == -1  || CURRENT_NUM > START_BUILD_TREE_NUM) {
			calculate(id, parentId, isParent);	
		}
	}
	/**
	 * 
	 */
	private void autoInitMtree() {
			Mtree = new MultipleTree();
			Mtree.initNodeList(dataList);	
	}
	/**
	 * 
	 * @param id
	 * @param parentId
	 * @param isParent
	 * @throws HiveException
	 */
	private void calculate(String id, String parentId,boolean isParent) throws HiveException {
		if(Mtree == null) {
			autoInitMtree();
		}
		List<Node> nodeList = Mtree.getParentOrSonNodeList(id, isParent);
		if (IS_LEVEL_NEEDED) {
			for(int i = 0; i <nodeList.size();i++) {
				forward(new String[] {id,nodeList.get(i).id,nodeList.get(i).getLevel()+""});
			}
		}else {
			if (isParent && (nodeList == null || nodeList.isEmpty()) ) {
				forward(new String[] {id,parentId,""});
			}else if(!isParent && (nodeList == null || nodeList.isEmpty()) ) {
				forward(new String[] {parentId,id,""});
			}else {
				for(int i = 0; i <nodeList.size();i++) {
					forward(new String[] {id,nodeList.get(i).id,nodeList.get(i).getLevel()+""});
				}
			}
		}
	}
	/**
	 * for test
	 * @param args
	 */
//	private void forward(String[] args) {
//		if(args == null || args.length <1) {
//			return ;
//		}
//	    System.err.println("ID:"+args[0]+",PS_ID:"+args[1]+",PS_LEVEL:"+args[2]);
//	}
	
	

}
