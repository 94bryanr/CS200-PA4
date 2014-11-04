// LinkedList.java
// Author: NameHere
// Date: DateHere
// Class: cs200
// P0 
public class LinkedList {
	
	public Node head;
	private int size; 
	
	public LinkedList()
	{
		head=null;
		size=0;
	}

	public void add(String s)
	{
		//increment size of LinkedList
		size+=1;
		Node n = new Node(s);
		//case: LinkedList is Empty
		if(head == null)
		{
			head = n;
			
		}
		//otherwise add to tail of LinkedList
		else
		{
			Node temp = head;
			while(temp.getNext()!=null)
			{
				//this iterates to the next Node in the LinkedList
				temp=temp.getNext();
			}
			//This sets the pointer of temp to the Node that we want to add.
			temp.setNext(n);
		}
	}

}
