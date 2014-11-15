import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WebPages{

	public HashTable hashTable;
	public static int totalDoc=0;

	//constructor
	public WebPages(){
		// creating hash table with temp value
		hashTable = new HashTable(hashTable.size);
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
				hashTable.add(filename, nextWord);
				
			}


		} catch (FileNotFoundException e) {
			System.out.println("Error: File Not Found");
			e.printStackTrace();
		}
	}
	
	
	public void printTree(){


	}
	
	//returns which pages word is in with TFIDF
	 public String[] whichPages(String word){

		 return null;
	 }
	

}
