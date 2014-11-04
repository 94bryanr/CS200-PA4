import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WebPages{

	public BST bst;
	public static int totalDoc=0;

	//constructor
	public WebPages(){
		bst = new BST();
	}
	
	//add page to fill in ArrayList
	public void addPage(String filename){
		
		
		totalDoc++;

		try{

			File fileIn = new File(filename);

			//Scanning file using delimiter
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fileIn).useDelimiter("[ |*|-|,|!|?|.|:|\\n|\\t|\\r|(|)|\\\"]+");
			
			//looping through file and adding words
			while(scan.hasNext()){

				//grabbing next word and removing in-text HTML
				String nextWord = scan.next().toLowerCase().replaceAll("<[^>]*>", "").replaceAll("/", "");
				if (nextWord.equals("")){
					nextWord = scan.next().toLowerCase();
				}
				//removing other HTML
				if(nextWord.length() > 0){
					if(nextWord.charAt(nextWord.length()-1) == '>'){
						nextWord = scan.next().toLowerCase();
					}
					if(nextWord.charAt(0) == '<'){
						if(nextWord.charAt(nextWord.length() - 1) == '>'){
							nextWord = scan.next().toLowerCase();
						}
						else{
							// Keep scanning until you hit >
							while((!(nextWord.charAt(nextWord.length() - 1) == '>')) && scan.hasNext()){
								nextWord = scan.next().toLowerCase();
							}
							// if more words, skip over word with >
							if(scan.hasNext())  
								nextWord = scan.next().toLowerCase();
							else break;
						}
					}
				}

				//adds the word to the BST
				bst.add(filename, nextWord);
				
			}


		} catch (FileNotFoundException e) {
			System.out.println("Error: File Not Found");
			e.printStackTrace();
		}
	}
	
	
	public void printTree(){
		//set up inorder iteration of tree
		TreeIterator inorder = new TreeIterator(bst);
		inorder.setInorder();
		Term next = inorder.next();
		//loop through tree and print inorder
		while(inorder.hasNext()){
			System.out.println(next.word);
			next = inorder.next();
		}
		//print last word of BST
		System.out.println(next.word);
	}
	
	//returns which pages word is in with TFIDF
	 public String[] whichPages(String word){
		 //set up inorder traversal of BST
		 TreeIterator treeIterator = new TreeIterator(bst);
		 treeIterator.setInorder();
		 Term next = treeIterator.next();
		 while(treeIterator.hasNext()){
			 //if word = element in BST, get that word and return w/ fillArray() (in Term Class)
			 if(next.word.toLowerCase().equals(word)){
				 bst.get(word, true);
				 return next.fillArray();
			 }
			 next = treeIterator.next();
		 }
		 //check last element of BST, if equal, return w/ fillArray() (in Term Class)
		 if(next.word.toLowerCase().equals(word)){
			 bst.get(word, true);
			 return next.fillArray();
		 }
		 bst.get(word, true);
		 return null;
		 
	 }
	

}
