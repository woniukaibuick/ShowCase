package org.apache.hadoop.hive.udf.connectby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MultipleTree {
	private List<Node> result = null;
	/**
	 * evolutional forest map, has plenty of trees, exclude can be merged trees.
	 * <br>
	 * the key is the root node id, and the value is the tree root node.
	 */
	private HashMap<String, Node> evolutionalForestMap = new HashMap<String, Node>();
	/**
	 * raw data map, the node has no parent and children. <br>
	 * will use this map to locate Node position.
	 */
	private HashMap<String, Node> rawDataMap = new HashMap<String, Node>();
	/**
	 * find all root node , and these node do not have children or parent node information.
	 */
	private List<Node> rootNodeList = new ArrayList<Node>();
	private int presentLevel = -1;
	/**
	 * step 1: find out all root nodes<br>
	 * step 2: clear evolutionalForestMap and then build tree
	 * @param dataList
	 */
	public void buildTree(List<Node> dataList) {
		for (Node node: dataList) {
			rootCheck(node);
			rawDataMap.put(node.getId(), node);
		}
		evolutionalForestMap.clear();
		for(Node rootNode : rootNodeList) {
			Node tree = createrTree(rootNode, rawDataMap.values());
			evolutionalForestMap.put(rootNode.getId(), tree);
		}
	}
	/**
	 * check if the node is root node
	 * @param allNodes
	 * @return
	 */
	private  void rootCheck(Node node) {
		String parentId = node.getParentId();
		if ("".equals(parentId) || parentId == null || "0".equals(parentId)) {
			rootNodeList.add(node);
		}
	}

	/**
	 * create tree by recursive algorithm
	 * @param rootNode
	 * @param allNodes
	 * @return
	 */
	private  Node createrTree(Node rootNode, Collection<Node> allNodes) {
		for (Node treeNode : allNodes) {
			if (treeNode.getParentId().equals(rootNode.getId())) {
				rootNode.getChildren().addChild(treeNode);
				treeNode.setParentNode(rootNode);
				createrTree(treeNode, allNodes);
			}
		}
		return rootNode;
	}

    /**
     * @param id
     * @return
     */
	private Node getNodeByID(String id) {
		return rawDataMap.get(id);
	}

	/**
	 * get direct level list by recursive algorithm
	 * @param ID
	 * @return list
	 */
	private List<Node> getDirectLevelResultListByID(Node node, boolean isParent) {
		List<Node> directLevelResultList = new ArrayList<Node>();
		if (node == null) {
			return directLevelResultList;
		}
		result = new ArrayList<Node>();
		presentLevel = -1;
		getLevelListForParentNode(node);
		return result;
	}


	/**
	 * 1. find node by id<br>
	 * 2. find node relationship list by node<br>
	 * 3. set start with id to each node<br>
	 * @param id
	 * @return nodeList
	 */
	public List<Node> getDirectParentOrSonNodeList(String id) {
		List<Node> nodeList = new ArrayList<Node>();
		Node tmpNode = getNodeByID(id);
		if (tmpNode == null) {
			return nodeList;
		}
		List<Node> resultList = getDirectLevelResultListByID(tmpNode, true);
		if (!isEmpty(resultList)) {
			nodeList.addAll(resultList);
		}
		for (Node item : nodeList) {
			item.setStartWithID(tmpNode.getId());
		}

		return nodeList;
	}

	private boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	private void getLevelListForParentNode(Node nodeItem) {
		if (nodeItem == null) {
			return ;
		}
		presentLevel++;
		nodeItem.setLevel(presentLevel);
		result.add(nodeItem);
		Node parentNode = nodeItem.getParentNode();
		if (parentNode != null) {
			getLevelListForParentNode(parentNode);
		}
	}

	/**
	 * for debug use, will collect all tree info
	 * @return List<String>
	 */
	public List<String> getDebugInfo() {
		List<String> debugInfoList = new ArrayList<String>();
		for (Node node : evolutionalForestMap.values()) {
			debugInfoList.add(node.toString());
		}
		return debugInfoList;
	}

}

class Node {
	private String id;
	private String text;
	private String parentId;
	private Children children = new Children();

	private int level;
	private Node parentNode;
	private String startWithID;

	public String toString() {
		String result = "{" + "id : '" + id + "'" + ", text : '" + text + "'";

		if (children != null && children.getSize() != 0) {
			result += ", children : " + children.toString();
		} else {
			result += ", leaf : true";
		}

		return result + "}";
	}

	public void sortChildren() {
		if (children != null && children.getSize() != 0) {
			children.sortChildren();
		}
	}

	public void addChild(Node node) {
		this.children.addChild(node);
	}

	public Children getChildren() {
		return children;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public String getStartWithID() {
		return startWithID;
	}

	public void setStartWithID(String startWithID) {
		this.startWithID = startWithID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	

}

class Children {
	private List<Node> list = new ArrayList<Node>();

	public int getSize() {
		return list.size();
	}

	public void addChild(Node node) {
		list.add(node);
	}

	public String toString() {
		String result = "[";
		for (Node node : list) {
			result += node.toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}

	@SuppressWarnings("unchecked")
	public void sortChildren() {
		Collections.sort(list, new NodeIDComparator());
		for (Iterator it = list.iterator(); it.hasNext();) {
			((Node) it.next()).sortChildren();
		}
	}

	public List<Node> getChildrenList() {
		return list;
	}
}

class NodeIDComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		int j1 = Integer.parseInt(((Node) o1).getId());
		int j2 = Integer.parseInt(((Node) o2).getId());
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}

class NodeLevelComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		int j1 = ((Node) o1).getLevel();
		int j2 = ((Node) o2).getLevel();
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}