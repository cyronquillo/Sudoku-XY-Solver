import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Solver{
    public static int NORMAL_SOLVER = 0;
    public static int X_SOLVER = 1;
    public static int Y_SOLVER = 2;
    public static int XY_SOLVER = 3;
    
    public ArrayList<Board> solutions;
    public int num_of_solutions;
    public int board_type;
    Board solve;
    public Solver(Board solve, String board_type){
        this.solutions = new ArrayList<Board>();

        if(board_type.equals("xy"))
            this.board_type = XY_SOLVER;
        if(board_type.equals("x"))
            this.board_type = X_SOLVER;
        if(board_type.equals("y"))
            this.board_type = Y_SOLVER;
        if(board_type.equals("n"))
            this.board_type = NORMAL_SOLVER;
            
        this.num_of_solutions = 0;
        try{
            this.solve = new Board(solve);
        } catch(Exception e){
            System.out.println(e);
        }        
    }


    public boolean check_duplicates(int[] values){
        for(int i = 0; i < this.solve.size; i++){
            for(int j = 0; j < this.solve.size; j++){
                if(values[i] == 0) break;
                if(i == j) continue;
                if(values[i] == values[j]){
                    return true;
                }
            }
        }
        return false;
    }
    // check for duplicates given the column to check
    public boolean col_duplicates(int col, boolean verbose){
        int[] values = new int[this.solve.size];
        for(int i = 0; i < this.solve.size; i++){
            values[i] = this.solve.board[i][col].top_of_stack;
        }
        if(check_duplicates(values)){
            if(verbose){
                JOptionPane.showMessageDialog(null, "Repeated value exists in column: " + (col+1) );
            }
            return true;
        }
        return false;
    }
    
    public boolean box_duplicates(int row, int col, boolean verbose){
        int[] values = new int[this.solve.size];
        int N = (int) Math.sqrt(this.solve.size);
        
        int row_lower = N * (int)(row/N);
        int row_upper = row_lower + N;
        int col_lower = N * (int)(col/N);
        int col_upper = col_lower + N;
        
        int k = 0;
        for(int i = row_lower; i < row_upper; i++){
            for(int j = col_lower; j < col_upper; j++){
                values[k++] = this.solve.board[i][j].top_of_stack;
            }
        }
        if(check_duplicates(values)){
            if(verbose){
                int x = (row/N) + 1;
                int y = (col/N) + 1;
                JOptionPane.showMessageDialog(null, "Repeated value exists in Subgrid (" + x + "," + y + ")");
                
            }
            return true;
        } 
        return false;
    }

    // check for duplicates given the row to check
    public boolean row_duplicates(int row, boolean verbose){
        int[] values = new int[this.solve.size];
        for(int i = 0; i < this.solve.size; i++){
            values[i] = this.solve.board[row][i].top_of_stack;
        }

        if(check_duplicates(values)){
            if(verbose){
                JOptionPane.showMessageDialog(null, "Repeated value exists in row: " + (row+1) );
            }
            return true;
        } 
        return false;
    }

    // check for duplicates in the 2 main diagonals
    public boolean x_duplicates(boolean verbose){
        if(this.board_type != X_SOLVER && this.board_type != XY_SOLVER) return false;
        
        int d1 = 0, d2 = 0;
        int[] diag1 = new int[this.solve.size];
        int[] diag2 = new int[this.solve.size];
        for(int i = 0; i < this.solve.size; i++){
            for(int j = 0; j < this.solve.size; j++){
                if(i == j) diag1[d1++] = this.solve.board[i][j].top_of_stack;
                if(i+j == this.solve.size-1)  diag2[d2++] = this.solve.board[i][j].top_of_stack;
            }

        }  

        if(check_duplicates(diag1) || check_duplicates(diag2)){
            if(verbose){
                JOptionPane.showMessageDialog(null, "Repeated value exists in X");                
                //popout saying x has repeating value
            }
            return true;
        } 
        return false;             
    }

    public boolean y_duplicates(boolean verbose){
        if(this.board_type != Y_SOLVER && this.board_type != XY_SOLVER) return false;
        if(this.solve.size % 2 == 0) return false;

        int lep = 0, rayt = 0, mid = this.solve.size/2;
        int[] left = new int[this.solve.size];
        int[] right = new int[this.solve.size];
        for(int i = 0; i < mid; i++){
            for(int j = 0; j < this.solve.size; j++){
                if(i == j) left[lep++] = this.solve.board[i][j].top_of_stack;
                if(i+j == this.solve.size-1)  right[rayt++] = this.solve.board[i][j].top_of_stack;
            }
        }

        for(int i = mid; i < this.solve.size; i++){
            left[lep++] = this.solve.board[i][mid].top_of_stack;
            right[rayt++] = this.solve.board[i][mid].top_of_stack;
        }
        if(check_duplicates(left) || check_duplicates(right)){
            if(verbose){
                JOptionPane.showMessageDialog(null, "Repeated value exists in Y");                
            }
            return true;
        } 
        return false;  
    }

    public boolean duplicates_exist(int row, int col, boolean verbose){
        if(row_duplicates(row,verbose)) return true;
        if(col_duplicates(col,verbose)) return true;
        if(box_duplicates(row,col,verbose)) return true;
        if(x_duplicates(verbose)) return true;
        if(y_duplicates(verbose)) return true;

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
                    if(duplicates_exist(row,col,false)){
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
    }

    public void viewSolutions(){
        System.out.println("Total Number of Solutions: " + this.num_of_solutions);
        /*for(int i = 0; i < solutions.size(); i++){
            System.out.println("\nSolution #" + (i + 1) + ":");
            solutions.get(i).outputBoard();
        }*/
    }

    public ArrayList<Board> getSolutions(){
        return solutions;
    }

    public void clearNonPreset(){
        for(int i = 0; i < solve.size; i++){
            for(int j = 0; j < solve.size; j++){
                if(!solve.board[i][j].is_preset){
                    solve.board[i][j].top_of_stack = 0;
                }
            }
        }
    }

    public boolean zero_exists(){
        int size = this.solve.size;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(solve.board[i][j].top_of_stack == 0) return true;
            }
        }

        return false;
    }
}