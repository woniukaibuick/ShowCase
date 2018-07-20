package valar.showcase.hive.udf.connectby;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * UDTF function: ConnectByUDTF<br>
 * sample:select connect_by(id,parent_id) as (ID,PS_ID,PS_LEVEL,START_WITH_ID)  from test
 * @ClassName ConnectByUDTF 
 * @Description TODO(calculate node relationship,similar with oracle connect by function) 
 * @author gongxuesong
 * @date 2017-09-20 7:33:18 PM
 * @since 
 */
public class ConnectByUDTF extends GenericUDTF {

	private List<Node> dataList = new ArrayList<Node>();
	private MultipleTree Mtree = null;

	private static boolean isDebug = false;

	@Override
	public void close() throws HiveException {
		// TODO Auto-generated method stub

		initMutipletree();
		calculateNodesRelationship();
		printDebugInfo();

		dataList.clear();
		dataList = null;
		Mtree = null;
	}

	@Override
	public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
		// TODO Auto-generated method stub
		if (args.length != 2) {
			throw new UDFArgumentException("Argument length exception,\"connect by(id,parent_id)\" take 2 parameters");
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
		fieldNames.add("START_WITH_ID");
		fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
	}

	/**
	 * para1 must be id, para2 must be parentId, para4 must be true/false
	 */
	@Override
	public void process(Object[] args) throws HiveException {
		// TODO Auto-generated method stub
		String id = args[0] == null ? "" : args[0].toString();
		String parentId = args[1] == null ? "" : args[1].toString();

		Node node = new Node();
		node.setId(id);
		node.setParentId(parentId);
		dataList.add(node);

	}

	private void initMutipletree() {
		Mtree = new MultipleTree();
		Mtree.buildTree(dataList);
	}

	/**
	 * 
	 * @param id
	 * @param parentId
	 * @param isParent
	 * @throws HiveException
	 */
	private void calculate(String id, String parentId) throws HiveException {
		List<Node> nodeList = Mtree.getDirectParentOrSonNodeList(id);
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			forward(new String[] { node.getId(), node.getParentId(), node.getLevel() + "", node.getStartWithID() });
		}
	}

	private void calculateNodesRelationship() throws HiveException {
		for (Node node : dataList) {
			calculate(node.getId(), node.getParentId());
		}
	}

	private void printDebugInfo() throws HiveException {
		if (isDebug) {
			List<String> infoList = Mtree.getDebugInfo();
			boolean isEmpty = (infoList == null || infoList.isEmpty());
			forward(new String[] { !isEmpty && infoList.size() >= 1 ? infoList.get(0) : "",
					!isEmpty && infoList.size() >= 2 ? infoList.get(1) : "",
					!isEmpty && infoList.size() >= 3 ? infoList.get(2) : "",
					!isEmpty && infoList.size() >= 4 ? infoList.get(3) : "" });
		}
	}

	/**
	 * for test
	 * @param args
	 */
	private void forward(String[] args) {
		if (args == null || args.length < 1) {
			return;
		}
		System.err.println("ID:" + args[0] + ",PS_ID:" + args[1] + ",PS_LEVEL:" + args[2] + ",StartWithID:" + args[3]);
	}

}
