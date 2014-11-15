import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class PA4 {

	public static void main(String[] args) {
		
		WebPages web = new WebPages();

		try {

			File file = new File(args[0]);
			Scanner scan;
			scan = new Scanner(file);
			String nextArg = scan.nextLine();

			//adding files/creating new webPages
			while(!(nextArg.equals("*EOFs*"))){
				web.addPage(nextArg);
				nextArg = scan.nextLine();
			}
			
			// print out BST
			System.out.println("WORDS");
			web.printTree();

			//skip EOFs
			nextArg = scan.nextLine();

			System.out.println();


			//scanning though all whichPages words
			while(!(nextArg == null)){
				String[] pages = web.whichPages(nextArg.toLowerCase());
				if(pages == null){
					System.out.println(nextArg + " not found");
				}
				else{
					System.out.print(nextArg + " in pages: ");
					for(int i = 0; i < pages.length-1; i++){
						System.out.print(pages[i] + ": ");
						if(i < pages.length-2){
							i++;
							System.out.print(pages[i] + ", ");
						}
					}
					if(pages.length > 0){
						System.out.println(pages[pages.length-1]);
					}
					else{
						System.out.println();
					}

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
