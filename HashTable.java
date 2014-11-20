import java.util.Iterator;

public class HashTable implements TermIndex, Iterable<Term> {

	private Term[] hashTable;
	private int itemCounter = 0;
	
	public HashTable(int arraySize){
		hashTable = new Term[arraySize];
	}

    public Term[] getHashTable() {
        return hashTable;
    }

    public int getItemCounter() {
        return itemCounter;
    }

    @Override
	public void add(String filename, String newWord) {
        //Create the words Term object
		Term word = new Term(newWord);
		word.incFrequency(filename);
        if(search(newWord) != -1)
            hashTable[search(newWord)].incFrequency(filename);
        else
		    add(word);
	}

    private void add(Term term)
    {
        //Do not add new term if one already exists
        if(search(term.word) == -1){
            //Word does not already exist
            int index = getIndex(term.word);
            //Begin quadratically probing for first open spot
            int counter = 1;
            while(true)
            {
                if(hashTable[index] == null){
                    hashTable[index] = term;
                    itemCounter++;
                    //Item successfully added
                    break;
                }
                else {
                    index += counter * counter;
                    index %= (hashTable.length - 1);
                    counter ++;
                }

                if(counter == hashTable.length)
                    try {
                        throw new Exception();
                    } catch (Exception e) {System.out.println("Error: Can not add term to hashtable");}
                }
            }
        else{
            System.out.println("Error: Added duplicate");
        }

        //Rehash if hashtable is 80 percent full
        if(itemCounter >= (hashTable.length*.8)){
            rehash();
        }
    }

	@Override
	public int size() {
		return hashTable.length;
	}

	@Override
	public void delete(String word) {
		int position = search(word);
        if (position != -1)
		    hashTable[position] = new Term("RESERVED");
	}

	@Override
	public Term get(String word) {
		int position = search(word);
        if (position != -1)
            return hashTable[position];
        return null;
	}

    /*Search will search for a word in the hashTable. If the word exists it will return the position in the
    hash table where that word exists. If the word does not exist it will return -1.
     */
    public int search(String key)
    {
        //Get first index to check
        int index = getIndex(key);

        //Begin checking, use quadratic probing, terminate when null
        int counter = 1;
        while(true) {
            //If index is null, terminate:
            if(hashTable[index] == null)
            {
                return -1;
            }
            else {
                if(hashTable[index].word.equals(key)){
                    //Index matches search key
                    return index;
                }
                else
                {
                    //Update index using quadratic probing
                    index += (counter*counter);
                    index %= (hashTable.length - 1);
                    counter ++;
                }
            }

            //Stop probing after checking <size of hash table> positions
            if(counter == hashTable.length)
                return -1;
        }
    }

    private int getIndex(String word){
        int index = word.hashCode();
        index %= (hashTable.length - 1);
        return Math.abs(index);
    }
	
	// will change size to 2*currentSize+1 and will rehash each item
	private void rehash(){
        System.out.println("REHASH: New Size: " + hashTable.length*2);
        itemCounter = 0;
		Term[] tempArray = hashTable;
        int preRehashSize = tempArray.length;
		hashTable = new Term[hashTable.length*2];
		for(int i = 0; i < preRehashSize; i++){
            //If the term is not RESERVED or NULL, add it to the new array
            try {
                Term toAdd = tempArray[i];
                if(!(toAdd.equals("RESERVED") || toAdd == null)){
                    add(toAdd);
                }
            }catch(NullPointerException e){}
		}
	}

    @Override
    public Iterator iterator() {
        return new HashTableIterator(hashTable);
    }
}
