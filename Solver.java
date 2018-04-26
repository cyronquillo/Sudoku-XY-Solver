import java.util.ArrayList;
public class Solver{
    public ArrayList<Board> solutions;
    public int num_of_solutions;
    Board solve;
    public Solver(Board solve){
        this.solutions = new ArrayList<Board>();
        this.num_of_solutions = 0;
        this.solve = new Board(solve);
        
    }

    // check for duplicates given the column to check
    public boolean col_duplicates(int col){
        for(int i = 0; i < solve.size; i++){
            for(int j = 0; j < solve.size; j++){
                if(i == j) continue;
                if(solve.board[i][col].top_of_stack == 0) break;
                if(solve.board[i][col].top_of_stack == solve.board[j][col].top_of_stack)
                    return true;
            }
        }
        return false;
    }
    
    public boolean box_duplicates(int row, int col){
        // row_upper and row_lower
        // column_upper and column_lower
        int N = (int) Math.sqrt(solve.size);
        
        int row_lower = 0, row_upper = N;
        int col_lower = 0, col_upper = N;
        
        // row bounds for the mini grid
        while(row_upper <= row){
            row_upper += N;
            row_lower += N;
        }

        // column bounds for the mini grid
        while(col_upper <= col){
            col_upper += N;
            col_lower += N;
        }
        for(int i = row_lower; i < row_upper; i++){
            for(int j = col_lower; j < col_upper; j++){
                if(solve.board[i][j].top_of_stack == 0) break;
                for(int k = row_lower; k < row_upper; k++){
                    for(int l = col_lower; l < col_upper; l++){
                        if(i == k && j == l) continue;
                        if(solve.board[i][j].top_of_stack == solve.board[k][l].top_of_stack){
                            return true;
                        }
                    }
                }
            }
        }

        return false;

    }
    // check for duplicates given the row to check
    public boolean row_duplicates(int row){
        for(int i = 0; i < solve.size; i++){
            for(int j = 0; j < solve.size; j++){
                if(i == j) continue;
                if(solve.board[row][i].top_of_stack == 0) break;
                if(solve.board[row][i].top_of_stack == solve.board[row][j].top_of_stack) return true;
            }
        }
        return false;
    }

    public boolean duplicates_exist(int row, int col){
        if(box_duplicates(row,col)) return true;
        if(row_duplicates(row)) return true;
        if(col_duplicates(col)) return true;
        // add x_duplicates
        // add y_duplicates for odd value of size

        return false;
    }


    public void backtrack(int row, int col){
        while(row != -1 || col != -1){
            if(row == solve.size){
                this.num_of_solutions++;
                this.solutions.add(new Board(solve));
                row = solve.last_row;
                col = solve.last_col;
            } else if(col == solve.size){
                row = row + 1;
                col = 0;
            } else if(solve.board[row][col].is_preset){
                row = row;
                col = col + 1;
            } else if(!solve.board[row][col].is_preset){
                solve.board[row][col].top_of_stack++;

                if(solve.board[row][col].top_of_stack <= solve.size){
                    if(duplicates_exist(row,col)){
                        row = row;
                        col = col;
                    } else{
                        row = row;
                        col = col + 1;
                    }
                } else{
                    // reinstantiate top of stack to 0 again
                    solve.board[row][col].top_of_stack = 0;

                    int new_row = solve.board[row][col].prev_row; 
                    int new_col = solve.board[row][col].prev_col;

                    row = new_row;
                    col = new_col;
                }
            }
        }
        System.out.println("Total Number of Solutions: " + this.num_of_solutions);
    }

    public void viewSolutions(){
        for(int i = 0; i < solutions.size(); i++){
            System.out.println("Solution #" + i + ":");
            solutions.get(i).outputBoard();
        }
    }
}