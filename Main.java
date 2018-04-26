import java.io.BufferedReader;
import java.io.FileReader;

public class Main{
	public static void main(String[] args){
		try{
			BufferedReader in = new BufferedReader(new FileReader("file.txt"));

			int test_cases = Integer.parseInt(in.readLine());
			System.out.println(test_cases);
			for(int i=0; i<test_cases; i++){
				Board b = new Board(in);
				b.outputBoard();
			}
			in.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}