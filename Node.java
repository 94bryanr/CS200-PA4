// Node.java
// Author: NameHere
// Date: Date Here
// Class: cs200
// P0
public class Node {
	
	private String data;
	private Node next;
	Occurrence doc;
	
	public Node(String s)
	{
		doc = new Occurrence(s);
		this.data=s;
        this.next=null;
	}
	public String getDocName(){
		return doc.docName;
	}
	public int getTermFrequency(){
		return doc.termFrequency;
	}
	public String getData() {
		return data;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	public void incFrequency(){
		doc.incFrequency();
	}

}
