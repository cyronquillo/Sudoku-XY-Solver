import java.io.BufferedReader;
import java.io.FileReader;

public class Main{
	public static void main(String[] args){
		try{
			BufferedReader in = new BufferedReader(new FileReader("input.in"));

			int test_cases = Integer.parseInt(in.readLine());
			System.out.println(test_cases);
			for(int i=0; i<test_cases; i++){
				Board b = new Board(in);
				b.outputBoard();
				Solver solver = new Solver(b, args[0]);
				solver.backtrack(0,0);
				solver.viewSolutions();
			}
			in.close();
		}catch(Exception e){
			System.out.println(e);
		}

	}
}