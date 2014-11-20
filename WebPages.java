import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class WebPages{

	public HashTable hashTable;
	public static int totalDoc = 0;
	
	// arrays for Sim calculation
	// holds place holders that represent documents (sorted in alpha order)
	public String[] docs = new String[0];
	// contains numerator of equation
	public double[] common;
	// keeps the first summation of the denominators
	public double[] docSpecific;
	// keeps second summation of denominator
	public double queryWeights;

	//constructor
	public WebPages(int hashSize){
		// creating hash table with temp value
		hashTable = new HashTable(hashSize);
	}
	
	//add page to fill in ArrayList
	public void addPage(String filename){
		
		// increase number of total documents
		totalDoc++;
		
		// copy docs content to temp array
		String[] temp = new String[docs.length];
		for(int j = 0; j < temp.length; j++){
			temp[j] = docs[j];
		}
		// increase docs array size by 1
		docs = new String[totalDoc];
		// copy back to docs array from temp array
		for(int k = 0; k < temp.length; k++){
			docs[k] = temp[k];
		}
		// add read in file to end of docs array
		docs[docs.length-1] = filename;
		
		// sort docs array alphabetically
		Arrays.sort(docs);

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
	
	
	public void printTable(){

		for(int i = 0; i < hashTable.size(); i++){
			if(hashTable.hashTable[i] == null || hashTable.hashTable[i].word.equals("RESERVED")){
				//System.out.println("NULL");
			}
			else{
				System.out.println(hashTable.hashTable[i].word);
			}
		}
	}

	 
	 public void pruneStopWords(String word){
		 hashTable.delete(word);
	 }
	 
	 public String[] fillQueryArray(String query){
		 Scanner scan = new Scanner(query);
		 int querySize = 0;
		 while(scan.hasNext()){
			 querySize++;
			 scan.next();
		 }
		 scan.close();
		 String[] queryArray = new String[querySize];
		 Scanner scan2 = new Scanner(query);
		 for(int i = 0; i < queryArray.length; i++){
			 String word = scan2.next();
			 queryArray[i] = word;
		 }
		 scan2.close();
		 return queryArray;
	 }
	 
	 public void clearArrayData(double[] common, double[] docSpecific){
		 common = this.common;
		 docSpecific = this.docSpecific;
		 for(int i = 0; i < docs.length; i++){
			 common[i] = 0;
			 docSpecific[i] = 0;
		 }
	 }
	 
	 public String[] bestPages(String query){
		 DecimalFormat df = new DecimalFormat("###0.00");
		 // initialize all data fields to 0
		 common = new double[totalDoc];
		 docSpecific = new double[totalDoc];
		 queryWeights = 0.0;
		 clearArrayData(common, docSpecific);
		 
		 // traverse termIndex to fill queryWeights, docSpecific, and common
		 traversal(query);
		 
		 String[] returnArray = new String[2];
		 double max = 0;
		 // traverse all documents
		 for(int i = 0; i < docs.length; i++){
			 //System.out.println("document: " + docs[i] + " sim: " + sim(common[i], docSpecific[i], queryWeights));
			 double simValue = sim(common[i], docSpecific[i], queryWeights);
			 if(simValue >= max){
				 max = simValue;
				 returnArray[0] = docs[i];
				 returnArray[1] = String.valueOf(df.format(max));
			 }
		 }
		 return returnArray;
	 }

	 public double sim(double commonValue, double docSpecificValue, double queryWeightValue){
		 return commonValue/(Math.sqrt(docSpecificValue)*Math.sqrt(queryWeightValue)); 
	 }


	 // fills queryWeights, docSpecific, and common
	 public void traversal(String query){
		 // fill queryArray
		 String[] queryArray = fillQueryArray(query);

		 //Traverse termIndex and compare each Term to each query word
		 for(int i = 0; i < hashTable.size(); i++){
			 Term currentTerm = hashTable.hashTable[i];
			 if(currentTerm != null){
				 boolean inquery = false;
				 // Scanning through all query words to check if Term is in query
				 for(int k = 0; k < queryArray.length; k++ ){
					 // a) if the term is in query, compute wiq, square it, and add to queryWeights
					 if(currentTerm.word.equals(queryArray[k])){
						 inquery = true;
						 double wiqTemp = wiq(totalDoc, currentTerm.docFrequency, currentTerm);
						 wiqTemp = wiqTemp*wiqTemp;
						 queryWeights += wiqTemp;
					 }
				 } 
				 
				 // fill array of term documents
				 String[] termDocuments = currentTerm.fillDocArray();
				 double[] tfidfValues = currentTerm.fillTFIDFarray();
				 // for each document that contains term
				 for(int a = 0; a < termDocuments.length; a++){
					 // i. compute wid value, square it, and add to doc position
					 double wid = tfidfValues[a];
					 wid = wid*wid;
					 int docposition = Arrays.asList(docs).indexOf(termDocuments[a]);
					 docSpecific[docposition] += wid;
					 
					 // if term is in doc & query
					 if(inquery == true){
						 double commonValue = tfidfValues[a]*wiq(totalDoc, currentTerm.docFrequency, currentTerm);
						 common[docposition] += commonValue;
					 }
				 }
			 }
		 }
	 }
	 
	 public double wiq(int numTotalDocuments, int numDocumentsPerTerm, Term term){
		 return .5*(1+Math.log(numTotalDocuments/numDocumentsPerTerm));
	 }
	 
	 public double wid(Term term){
		 return term.tfidf;
	 }
	


}
