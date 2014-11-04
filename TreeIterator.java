//Code taken from "Data Abstraction and Problem Solving" by Janet J. Prichard pg. 587-588

import java.util.LinkedList;

public class TreeIterator implements java.util.Iterator {
	
	private BST binTree;
	private TreeNode currentNode;
	private LinkedList <TreeNode> queue;
	
	public TreeIterator(BST bTree){
		binTree = bTree;
		currentNode = null;
		queue = new LinkedList <TreeNode>();
	}
	
	public boolean hasNext(){
		return !queue.isEmpty();
	}
	
	public Term next() throws java.util.NoSuchElementException{
		currentNode = queue.remove();
		return currentNode.item;
	}
	
	public void setInorder(){
		queue.clear();
		inorder(binTree.root);
	}
	
	public void remove() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	private void inorder(TreeNode treeNode){
		if(treeNode != null){
			inorder(treeNode.leftChild);
			queue.add(treeNode);
			inorder(treeNode.rightChild);
		}
	}

}
