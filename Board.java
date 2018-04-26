import java.io.BufferedReader;

public class Board{
	SudoBox[][] board;
	int last_row;
	int last_col;
	int size;

	public Board (BufferedReader in){
		try{
			this.size = (int) Math.pow(Integer.parseInt(in.readLine().trim()), 2);
			int row = 0, prev_row = -1, prev_col = -1;
			board = new SudoBox[size][size];
			while(row < this.size){
				String line = in.readLine();
				String[] nums = line.split(" ");
				for(int i=0; i<nums.length; i++){
					board[row][i] = new SudoBox(Integer.parseInt(nums[i]), prev_row, prev_col);
					if(!board[row][i].is_preset){
						prev_row = row;
						prev_col = i;
					}
				}
				row += 1;
			}

			this.last_row = prev_row;
			this.last_col = prev_col;
		} catch(Exception e){
			System.out.println(e);
		}
	}

	public void outputBoard(){
		for(int i=0; i<this.size; i++){
			for(int j=0; j<this.size; j++){
				System.out.print(this.board[i][j].top_of_stack);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}