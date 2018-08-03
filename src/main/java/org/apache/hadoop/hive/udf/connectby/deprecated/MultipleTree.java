package org.apache.hadoop.hive.udf.connectby.deprecated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 多叉树类 树类（MultipleTree.java）、节点类（Node.java）、孩子列表类（Children.java）
 */
public class MultipleTree {
	private List<Node> result = new ArrayList<Node>();
	private int presentLevel = -1;

    public static void main(String[] args) {

		// 输出无序的树形菜单的JSON字符串
//		System.out.println(root.toString());
		// 对多叉树进行横向排序
		// root.sortChildren();
		// 输出有序的树形菜单的JSON字符串
		// System.out.println(root.toString());
		MultipleTree te = new MultipleTree();
		List<Node> mylist = te.initNodeList(VirtualDataGenerator.getVirtualResult());
//		for(Node node : mylist){
//			System.err.println("Level:"+node.getUuid()+",ID:"+node.id+",Name:"+node.text);
//		}

		

		System.out.println("------------------output---------------------------");
		
		List<Node> targetList = te.getParentOrSonNodeList("", true);
		for(Node node : targetList){
			System.err.println("Level:"+node.getUuid()+"_"+node.getLevel()+",ID:"+node.id+",Name:"+node.text);
		}
	}
	
	private Node buildTree(List dataList){
//		 读取层次数据结果集列表
//		List dataList = VirtualDataGenerator.getVirtualResult();
		// 节点列表（散列表，用于临时存储节点对象）
		HashMap nodeList = new HashMap();
		// 根节点
		Node root = null;
		// 根据结果集构造节点列表（存入散列表）
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node node = new Node();
			node.id = (String) dataRecord.get("id");
			node.text = (String) dataRecord.get("text");
			node.parentId = (String) dataRecord.get("parentId");
			nodeList.put(node.id, node);
		}
		// 构造无序的多叉树
		Set entrySet = nodeList.entrySet();
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node node = (Node) ((Map.Entry) it.next()).getValue();
			if (node.parentId == null || node.parentId.equals("")) {
				root = node;
			} else {
				Node elseNode = ((Node) nodeList.get(node.parentId));
				if(elseNode != null){
					elseNode.addChild(node);
				}else{
					root = node;
				}
				
			}
		}
		return root;
	}

	public  List<Node> initNodeList(List dataList) {
		result.clear();
		Node root = buildTree(dataList);
		List<Node> initNodeList = new ArrayList<Node>();
		initNodeList.add(root);
		getLevelList(initNodeList);
		return result;
	}
	/**
	 * 
	* @Title: getParentOrSonNodeList 
	* @Description: TODO(description) 
	* @param   
	* @return    
	* @throws
	 */
    public List<Node> getParentOrSonNodeList(String ID,boolean isParent){
//    	Collections.sort(result, new NodeLevelComparator());
    	List<Node> nodeList = new ArrayList<Node>();
    	if (ID == null || ID.isEmpty()) {
			return result;
		}
    	int posi = -1;
    	String posiUUID = "";
    	if (isParent) {
    		for (int i = 0; i < result.size(); i++) {
				if (ID.equals(result.get(i).id)) {
					posiUUID = result.get(i).getUuid();
					break;
				}
			}
    		for (int i = 0; i < result.size(); i++) {
				if (posiUUID.equals(result.get(i).getUuid())) {
					posi = (i == 0? -1:i-1);
					break;
				}
			}
    		nodeList = (posi!=-1?result.subList(0, posi+1):nodeList);
		} else {
    		for (int i = result.size()-1; i >=0; i--) {
				if (ID.equals(result.get(i).id)) {
					posiUUID = result.get(i).getUuid();
					break;
				}
			}
    		for (int i = result.size()-1; i >=0; i--) {
				if (posiUUID.equals(result.get(i).getUuid())) {//ID.equals(result.get(i).id) && 
					posi = (i == result.size()-1? -1: i+1);
					break;
				}
			}
    		nodeList = (posi!=-1?result.subList(posi, result.size()):nodeList);
		}
//    	Collections.reverse(nodeList);//the smaller index ,the  higher level,there is a bug!
    	return nodeList;
    }
	
	private  boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	private  List<Node> getLevelList(List<Node> tmpLevelNodeList) {
		ArrayList<Node> nextLevelNodeList = new ArrayList<Node>();
		// List<Node> childrenList = root.getChildren().getChildrenList();
		if (isEmpty(tmpLevelNodeList)) {
			return nextLevelNodeList;
		}
		String tmpLevelUUID = UUID.randomUUID().toString();
		presentLevel ++;
		for (Node nodeItem : tmpLevelNodeList) {
			if(nodeItem == null){
				continue;
			}
			nodeItem.setUuid(tmpLevelUUID);
			nodeItem.setLevel(presentLevel);
			result.add(nodeItem);
			List nodeSubItemList = nodeItem.getChildren().getChildrenList();
			if (!isEmpty(nodeSubItemList)) {
				nextLevelNodeList.addAll(nodeSubItemList);
			}

		}
		if (!isEmpty(nextLevelNodeList)) {
			getLevelList(nextLevelNodeList);
		}
		return nextLevelNodeList;
	}

	public ArrayList<String> getItemLevelList() {
		return null;
	}

}

/**
 * 节点类
 */
class Node {
	/**
	 * 节点编号
	 */
	public String id;
	/**
	 * 节点内容
	 */
	public String text;
	/**
	 * 父节点编号
	 */
	public String parentId;
	/**
	 * 孩子节点列表
	 */
	private Children children = new Children();

	/**
	 * 用于同级节点标识
	 */
	private String uuid;
	private int level;

	// 先序遍历，拼接JSON字符串
	public String toString() {
		String result = "{" + "id : '" + id + "'" + ", text : '" + text + "'";

		if (children != null && children.getSize() != 0) {
			result += ", children : " + children.toString();
		} else {
			result += ", leaf : true";
		}

		return result + "}";
	}

	// 兄弟节点横向排序
	public void sortChildren() {
		if (children != null && children.getSize() != 0) {
			children.sortChildren();
		}
	}

	// 添加孩子节点
	public void addChild(Node node) {
		this.children.addChild(node);
	}

	public Children getChildren() {
		return children;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

/**
 * 孩子列表类
 */
class Children {
	private List list = new ArrayList();

	public int getSize() {
		return list.size();
	}

	public void addChild(Node node) {
		list.add(node);
	}

	// 拼接孩子节点的JSON字符串
	public String toString() {
		String result = "[";
		for (Iterator it = list.iterator(); it.hasNext();) {
			result += ((Node) it.next()).toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}

	// 孩子节点排序
	public void sortChildren() {
		// 对本层节点进行排序
		// 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
		Collections.sort(list, new NodeIDComparator());
		// 对每个节点的下一层节点进行排序
		for (Iterator it = list.iterator(); it.hasNext();) {
			((Node) it.next()).sortChildren();
		}
	}

	public List getChildrenList() {
		return list;
	}
}

/**
 * 节点比较器
 */
class NodeIDComparator implements Comparator {
	// 按照节点编号比较
	public int compare(Object o1, Object o2) {
		int j1 = Integer.parseInt(((Node) o1).id);
		int j2 = Integer.parseInt(((Node) o2).id);
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}
/**
 * 节点比较器
 */
class NodeLevelComparator implements Comparator {
	// 按照节点编号比较
	public int compare(Object o1, Object o2) {
		int j1 = ((Node) o1).getLevel();
		int j2 = ((Node) o2).getLevel();
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}