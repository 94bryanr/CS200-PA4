public class HashTable implements TermIndex {
	
	public int size;
	public Term[] hashTable;
	boolean notFound;
	public int itemCounter = 0;
	
	public HashTable(int arraySize){
		hashTable = new Term[arraySize];
		this.size = arraySize;
	}

	@Override
	public void add(String filename, String newWord) {
		Term word = new Term(newWord);
		word.incFrequency(filename);
		//Do not add new term if one already exists
		if(get(newWord, false) == null){
			int position = search(newWord, null);
			if(notFound == false){
				itemCounter++;
				hashTable[position] = word;
			}
		}
		else
			get(newWord, false).incFrequency(filename);
		
		if(itemCounter >= (hashTable.length*.8)){
			rehash();
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void delete(String word) {
		int position = search(word, word);
		if(notFound == false){
			if(hashTable[position] != null){
				itemCounter--;
				hashTable[position] = new Term("RESERVED");
			}
		}
	}

	@Override
	public Term get(String word, Boolean printP) {
		int position = search(word, word);
		if(notFound == false){
			return hashTable[position];
		}
		else{
			return null;
		}
	}
	
	public int search(String key, String endCondition){
		notFound = false;
		// construct new Term = null
		Term end = null;
		// if newWord is term, construct
		if(endCondition != null){
			end = new Term(endCondition);
		}
		// find hash position
		int position = Math.abs((key.hashCode())%(size-1));
		int counter = 1;
		int positionIncrement = 0;
		// keep quadratically probing until you find the position
		if(endCondition != null && hashTable[(position+positionIncrement)%(size-1)] != null){
			while(true){
				Term compareTerm = hashTable[(position+positionIncrement)%(size-1)];
				if (compareTerm == null){
					notFound = true;
					return -1;
				}
				else if (compareTerm.word.equals(end.word))
					return position + positionIncrement;
				// if we have probed as big as our table size, break from loop
				if(counter == size){
					notFound = true;
					break;
				}
				// quadratic probing
				positionIncrement = (counter*counter);
				counter++;
			}
		}
		else if(end == null){
			while(!(hashTable[(position+positionIncrement)%(size-1)] == end)){
				// if we have probed as big as our table size, break from loop
				if(counter == size){
					notFound = true;
					break;
				}
				// quadratic probing
				positionIncrement = (counter*counter);
				counter++;
			}

		}
		// return position
		return (position+positionIncrement)%(size-1);

	}
	
	// will change size to 2*currentSize+1 and will rehash each item
	private void rehash(){
		int originalsize = size;
		size = (2*size)+1;
		Term[] tempArray = hashTable;
		hashTable = new Term[size];
		
		for(int i = 0; i < originalsize; i++){
			if(!(tempArray[i].equals("RESERVED") || tempArray[i] == null)){
				hashTable[(tempArray[i].word.hashCode())%size] = tempArray[i];
			}
		}

		
	}

}
