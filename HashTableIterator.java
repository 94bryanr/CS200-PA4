import java.util.Iterator;

public class HashTableIterator implements Iterator<Term> {
    private int max;
    private int current;
    private Term[] table;
    private Term nextTerm;

    HashTableIterator(Term[] table){
        max = table.length-1;
        //Current starts at -1 because getNext begins checking at current+1
        current = -1;
        this.table = table;
        nextTerm = getNext();

    }
    @Override
    public boolean hasNext() {
        return (nextTerm != null);
    }

    @Override
    public Term next() {
        Term ret = nextTerm;
        nextTerm = getNext();
        return ret;
    }

    @Override
    public void remove() {

    }

    private Term getNext(){
        //Find next non-null/reserved term.
        for(int i = (current + 1); i<= max; i++){
            try {
                Term t = table[i];
                if(t.word != "RESERVED")
                {
                    current = i;
                    return t;
                }
            }
            catch (NullPointerException e){}
        }
        //No more terms
        return null;
    }
}
