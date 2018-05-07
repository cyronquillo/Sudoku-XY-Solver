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
			this.board = new SudoBox[size][size];
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

	public Board(Board replica){
		this.size = replica.size;
		this.last_row = replica.last_row;
		this.last_col = replica.last_col;
		
		this.board = new SudoBox[this.size][this.size];
		for(int i = 0; i < replica.size; i++){
			for(int j = 0; j < replica.size; j++){
				this.board[i][j] = new SudoBox(replica.board[i][j].top_of_stack, replica.board[i][j].prev_row, replica.board[i][j].prev_col);
				this.board[i][j].is_preset = replica.board[i][j].is_preset;
			}
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