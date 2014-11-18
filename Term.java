import java.text.DecimalFormat;


public class Term {
	
		public String word;
		public int docFrequency;
		public int totalFrequency;
		LinkedList list = new LinkedList();
		public Node first;
		boolean inList;
		
		//Construction of a term
		public Term(String name){
			this.word = name;
			docFrequency = 0;
		}
		
		//increment frequency for terms and add documents to linked list
		public void incFrequency(String document){
			totalFrequency++;
			
			//if empty, insert occurrence
			if(first == null){
				first = new Node(document);
				inList = true;
				list.add(document);
				docFrequency++;
			}
			
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
			
			//if not in list, add to end
			if(inList == false){
				list.add(document);
				docFrequency++;
			}
			
			//resets first to front of linked list
			first = list.head;
			
		}
		

		// fills array of Document names and TFIDF for a given Term
		public String[] fillArray(){
			
			//create array to return
			String[] list = new String[0];
			int index = 0;
			
			// loop through linked list of document names
			while(first != null){
				//reallocating size of array
				String[] temp = new String[list.length];
				temp = list;
				// increase array size by 2 (1 space for Document and 1 space for TFIDF)
				list = new String[index+2];
				// copy data from temp array back to array w/ realocated size
				for(int i = 0; i < temp.length; i++){
					list[i] = temp[i];
				}
				
				//adding document name to array
				String document = first.getData();
				DecimalFormat df = new DecimalFormat("###0.00");
				
				//calculating TFIDF
				double tfidf =  (float)first.getTermFrequency() * Math.log((float)(WebPages.getTotalDoc())/(float)(docFrequency));
				
				// adding document name to array
				list[index] = document;
				// casting TFIDF into String from double w/ DecimalFormatter
				String tFIDF = String.valueOf(df.format(tfidf));
				index++;
				// adding TFIDF to array
				list[index] = tFIDF;
				index++;
				first = first.getNext();
				
			}
			//return array of document names

			return list;
		}
}
