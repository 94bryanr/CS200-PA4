
public class Term {
	
		public String word;
		public int docFrequency;
		public int totalFrequency;
		LinkedList list = new LinkedList();
		public Node first;
		boolean inList;
		double tfidf;
		
		//Construction of a term
		public Term(String name){
			this.word = name;
			docFrequency = 0;
		}
		
		//increment frequency for terms and add documents to linked list
		public void incFrequency(String document){
			totalFrequency++;
			
			//loop through list
			while(first != null){
				//if it is in list, inc frequency and set inList to true
				if(first.getData().equals(document)){
					//increment total frequency
					first.incFrequency();
					inList = true;
					break;
				}
				// else it is not in list
				else{
					inList = false;
				}
				first = first.getNext();
			}
			
			first = list.head;
			
			//if empty, insert occurrence
			if(first == null){
				first = new Node(document);
				inList = true;
				list.add(document);
				docFrequency++;
			}
			
			//if not in list, add to end
			if(inList == false){
				list.add(document);
				docFrequency++;
			}
			
			//resets first to front of linked list
			first = list.head;
		}
		
		public double[] fillTFIDFarray(){

			//create array to return
			double[] TFIDFarray = new double[docFrequency];
			int index = 0;

			// loop through linked list of document names
			while(first != null){
				//calculating TFIDF
				tfidf =  (float)first.getTermFrequency() * Math.log((float)(WebPages.totalDoc)/(float)(docFrequency));
				// casting TFIDF into String from double w/ DecimalFormatter
				double tFIDF = tfidf;
				// adding TFIDF to array
				TFIDFarray[index] = tFIDF;
				index++;
				first = first.getNext();
			}
			//set first back to head of linked list
			first = this.list.head;
			// return list of TFIDF and document names;
			return  TFIDFarray;

		}
		
		
		public String[] fillDocArray(){
			
			String[] docs = new String[docFrequency];
			int index = 0;
			first = this.list.head;
			// loop through linked list of document names
			while(first != null){
				//adding document name to array
				String document = first.getData();
				docs[index] = document;
				index++;
				first = first.getNext();
			}
			//set first back to head of linked list
			first = this.list.head;
			// return list of TFIDF and document names;
			return docs;
		}
			
}
