import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class PA4 {
	public static void main(String[] args) {
		try {
			File file = new File(args[0]);
			Scanner scan;
			scan = new Scanner(file);
			String nextArg = scan.nextLine();
			
			Integer hashSize = new Integer(nextArg);
			nextArg = scan.nextLine();
			
			WebPages web = new WebPages(hashSize);

			//adding files/creating new webPages
			while(!(nextArg.equals("*EOFs*"))){
				web.addPage(nextArg);
				nextArg = scan.nextLine();
			}
			
			// print out BST        this.table = table;
			System.out.println("WORDS");
			web.printTable();

			//skip EOFs
			//nextArg = scan.nextLine();

			System.out.println();
			
			while(!(nextArg.equals("*STOPs*"))){
				nextArg = scan.nextLine();
				web.pruneStopWords(nextArg);
			}
			
			nextArg = scan.nextLine();
			
			System.out.println("WORDS");
			web.printTable();
			System.out.println();

			//scanning though all whichPages words
			while(!(nextArg == null)){
				String[] pages = web.bestPages(nextArg.toLowerCase());
				String[] query = web.fillQueryArray(nextArg.toLowerCase());
				Arrays.sort(query);
				if(pages == null){
					System.out.println("[" + nextArg + " ]" + " not found");
				}
				else{
					System.out.print("[");
					for(int i = 0; i < query.length; i++){
						System.out.print(query[i] + " ");
					}
					System.out.print("] in ");
					System.out.print(pages[0] + ": ");
					System.out.println(pages[1]);
				}
				if(scan.hasNext()){
					nextArg = scan.nextLine();
				}
				else{
					break;
				}
			}

			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
