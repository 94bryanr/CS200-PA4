public interface TermIndex {

    public void add(String word, String filename);

    public int size();

    public void delete(String word);

    public Term get(String word, Boolean printP);

}
