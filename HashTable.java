
public class HashTable implements TermIndex {
	
	public int size;
	public Term[] hashTable;
	boolean notFound;
	
	public HashTable(int arraySize){
		hashTable = new Term[arraySize];
		this.size = arraySize;
	}

	@Override
	public void add(String filename, String newWord) {
		if(notFound == false){
			hashTable[search(newWord, null)] = new Term(newWord);
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void delete(String word) {
		if(notFound == false){
			if(hashTable[search(word, word)] != null){
				hashTable[search(word, word)] = new Term("RESERVED");
			}
		}
	}

	@Override
	public Term get(String word, Boolean printP) {
		if(notFound == false){
			return hashTable[search(word, word)];
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
		int position = (key.hashCode())%(size-1);
		int counter = 1;
		int positionIncrement = 0;
		// keep quadratically probing until you find the position
		while(!(hashTable[(position+positionIncrement)%(size-1)].equals(end))){
			// if we have probed as big as our table size, break from loop
			if(counter == size){
				notFound = true;
				break;
			}
			// quadratic probing
			positionIncrement = (counter*counter);
			counter++;
		}
		// return position
		return position+positionIncrement;
		
	}
	
	// will change size to 2*currentSize+1 and will rehash each item
	private void rehash(){

		
	}

}
