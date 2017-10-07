package showcase.dw.glbg.udf.connectby.deprecated;

import java.util.ArrayList;
import java.util.List;

public class TreeCreator {

	public static void main(String[] args) {
		List<TreeNode> allNodes = new ArrayList<TreeNode>();
        allNodes.add(new TreeNode("1", "0", "节点1"));
        allNodes.add(new TreeNode("2", "0", "节点2"));
        allNodes.add(new TreeNode("3", "0", "节点3"));
        allNodes.add(new TreeNode("4", "1", "节点4"));
        allNodes.add(new TreeNode("5", "1", "节点5"));
        allNodes.add(new TreeNode("6", "1", "节点6"));
        allNodes.add(new TreeNode("7", "4", "节点7"));
        allNodes.add(new TreeNode("8", "4", "节点8"));
        allNodes.add(new TreeNode("801", "8", "节点801"));
        allNodes.add(new TreeNode("802", "8", "节点802"));
        allNodes.add(new TreeNode("9", "5", "节点9"));
        allNodes.add(new TreeNode("10", "100", "节点10"));
        
        
        List<TreeNode> rootNodes  = findRoots(allNodes);
        for (TreeNode rootNode : rootNodes) {
			
        	//TreeNode  root=new TreeNode("1", "0", "节点1");
        	TreeNode tree= createrTree(rootNode,allNodes);
        	IteratorTree(tree);
		}
        // System.out.println(tree.getChildrenNodes().get(0).getId());

	}

	//找出所有的根节点，一个树只有一个根节点
	   public static List<TreeNode> findRoots(List<TreeNode> allNodes) 
	   {
	        List<TreeNode> rootNodes = new ArrayList<TreeNode>();
	        for (TreeNode node : allNodes) 
	        {
	            String parentId=node.getParentId();
		        if("".equals(parentId)||parentId==null||"0".equals(parentId))
		        {
		        	node.setLevel(0);
		        	rootNodes.add(node);
		        }	
	        }
	        return rootNodes;
	    }
	
	    
	   //创建一个树
	   public static TreeNode createrTree(TreeNode rootNode,List<TreeNode> allNodes) 
	   {
		   for (TreeNode treeNode : allNodes) 
		   {
			   if(treeNode.getParentId()==rootNode.getId())
			   {
				   rootNode.getChildrenNodes().add(treeNode);
				   treeNode.setParent(rootNode);
				   treeNode.getAncestorNodes().add(rootNode);
				   createrTree(treeNode,allNodes);
			   }
		   }
		   return rootNode;
	   }
	
	   //List<TreeNode> allNodes = new ArrayList<TreeNode>();
	   
	   public static void  IteratorTree(TreeNode rootNode)
	   {
		   System.out.println("id=="+rootNode.getId()+",parentId=="+rootNode.getParentId()+",ancestorId=="+rootNode.getId());
		   if(rootNode.getChildrenNodes().size()>0)
		   {
			  List<TreeNode> childrenNodes= rootNode.getChildrenNodes();
			  for (TreeNode treeNode : childrenNodes) 
			  {
				  //System.out.println("id=="+treeNode.getId()+",parentId=="+treeNode.getParentId()+"ancestorId=="+treeNode.parentId);
				  List<TreeNode> ancestorNodes=treeNode.getAncestorNodes();
				  if(ancestorNodes!=null&&ancestorNodes.size()>0){
					  for (TreeNode ancestorNode : ancestorNodes) 
					  {
						  System.out.println("id=="+treeNode.getId()+",parentId=="+treeNode.getParentId()+"ancestorId=="+ancestorNode.parentId);
					  }
				  }
				  IteratorTree(treeNode);
			  }
			   
		   }
		   
		   
		   
		   
		   
		   
	   }
		   
}

//节点信息
class TreeNode
{
	private String id="";
	private String name="";
	public  String parentId;
	private TreeNode parent;
	private List<TreeNode> childrenNodes=new  ArrayList<TreeNode>();
	private boolean isLeaf;
	private int level;
	
	private List<TreeNode> ancestorNodes=new  ArrayList<TreeNode>();
	
	private String ancestorId="";
	
	
	public TreeNode(String id,String parentId,String name)
	{
		this.id=id;
		this.parentId=parentId;
		this.name=name;
	}


	public List<TreeNode> getAncestorNodes() {
		return ancestorNodes;
	}


	public void setAncestorNodes(List<TreeNode> ancestorNodes) {
		this.ancestorNodes = ancestorNodes;
	}


	public String getAncestorId() {
		return ancestorId;
	}


	public void setAncestorId(String ancestorId) {
		this.ancestorId = ancestorId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public TreeNode getParent() {
		return parent;
	}


	public void setParent(TreeNode parent) {
		this.parent = parent;
	}


	public List<TreeNode> getChildrenNodes() {
		return childrenNodes;
	}


	public void setChildrenNodes(List<TreeNode> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}


	public boolean isLeaf() {
		return isLeaf;
	}


	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
}