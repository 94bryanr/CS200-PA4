
public class Occurrence {
	
	public int termFrequency;
	public String docName;
	
	public Occurrence(String name){
		this.docName = name;
		termFrequency = 1;
	}
	
	public void incFrequency(){
		termFrequency++;
	}
	

}
