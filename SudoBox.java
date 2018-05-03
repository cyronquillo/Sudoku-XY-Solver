public class SudoBox{
	boolean is_preset;
	int top_of_stack;
	int prev_row;
	int prev_col;

	public SudoBox(int top_of_stack, int prev_row, int prev_col){
		this.top_of_stack = top_of_stack;
		this.prev_row = prev_row;
		this.prev_col = prev_col;

		this.is_preset = top_of_stack == 0 ? false : true; 
	}	
}