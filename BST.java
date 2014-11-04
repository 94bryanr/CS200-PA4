//Code taken (and then modified) from "Data Abstraction and Problem Solving" by Janet J. Prichard

public class BST{

	TreeNode root;
	int count;
	Term newTerm;
	String documentName;
	boolean newword = false;
	int c = 0;


	public BST(){
		root = null;
		count = 0;
	}
	
	public Term add(String documentName, String word){
		this.documentName = documentName;
		newTerm = new Term(word);
		root = insertItem(root, newTerm);
		if(newword == true){
			newTerm.incFrequency(documentName);
		}
		return newTerm;
		
	}
	
	public Term get(String word, Boolean printDepth){
		if(printDepth == true){
			count = 0;
			Term term = retrieveItem(root, word);
			System.out.println("  At depth " + count);
			return term;
		}
		return retrieveItem(root, word);
	}

	protected TreeNode insertItem(TreeNode tNode, Term newItem){
		TreeNode newSubtree;
		
		if(tNode == null){
			tNode = new TreeNode(newItem, null, null);
			if(c == 0){
			newItem.incFrequency(documentName);
			}
			return tNode;
		}
		c++;
		Term nodeItem = tNode.item;
		if (newItem.word.compareTo(nodeItem.word) == 0){
			nodeItem.incFrequency(documentName);
			return tNode;
		}
		else if(newItem.word.compareTo(nodeItem.word) < 0){
			newSubtree = insertItem(tNode.leftChild, newItem);
			tNode.leftChild = newSubtree;
			newword = true;
			return tNode;
		}
		else{
			newSubtree = insertItem(tNode.rightChild, newItem);
			tNode.rightChild = newSubtree;
			newword = true;
			return tNode;
		}

	}

	protected Term retrieveItem(TreeNode tNode, String word){

		Term treeItem;
		if (tNode == null){
			treeItem = null;
		}
		else{
			Term nodeItem = tNode.item;
			if(word.compareTo(nodeItem.word) == 0){
				treeItem = tNode.item;
			}
			else if(word.compareTo(nodeItem.word) < 0){
				treeItem = retrieveItem(tNode.leftChild, word);
			}
			else{
				treeItem = retrieveItem(tNode.rightChild, word);
			}
		}
		count++;
		return treeItem;
	}




}
