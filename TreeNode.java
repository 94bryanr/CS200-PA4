//Code taken from "Data Abstraction and Problem Solving" by Janet J. Prichard pg. 581-582

public class TreeNode {
	
	Term item;
	TreeNode leftChild;
	TreeNode rightChild;
	
	public TreeNode(Term newItem){
		item = newItem;
		leftChild = null;
		rightChild = null;
	}
	
	public TreeNode(Term newItem, TreeNode left, TreeNode right){
		item = newItem;
		leftChild = left;
		rightChild = right;
	}

}
