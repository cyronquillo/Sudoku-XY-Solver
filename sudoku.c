#include <stdio.h>
#include <stdlib.h>
#define TRUE 1
#define FALSE 0
#define INIT 0
#define SUDOKU 0 
#define SUDOKU_X 1 
#define SUDOKU_Y 2 
#define SUDOKU_XY 3 

int type = SUDOKU_XY;

typedef struct sudokuBox{
    int is_preset;          // boolean variable para malaman kung part sya ng table or input sya ng user
    int top_of_stack;       // current value being held by the cell. [0-9] ang content nyan. 0 kapag di pa nagagalaw 
    
    // 2 values below hold the coordinates of the nearest cell to its left that has a is_preset value of FALSE 
    int prev_row;           
    int prev_col;
} sudoBox ;

int N; // size of the mini grid 
int last_row, last_col; // coordinates of the last nonpreset value
sudoBox ** board; // main variable of the whole board

// function for printing the current content of the board
void print_board_status(){
    printf("Board status\n");
    int i, j;
    for(i = 0; i < N*N; i++){
        for(j = 0; j <  N*N; j++){
            printf("%2d ", board[i][j].top_of_stack);
        }
        printf("\n");
    }
    printf("\n\n");
}

// checks for duplicates given the row to check
int row_duplicates(int row){
    int i,j;
    for(i = 0; i < N*N; i++){
        for(j = 0; j < N*N; j++){
            if(i == j) continue;
            if(board[row][i].top_of_stack == 0) break;
            if(board[row][i].top_of_stack == board[row][j].top_of_stack)
                return TRUE;
        }
    }
    return FALSE;
}

// checks for duplicates given the column to check
int col_duplicates(int col){
    int i,j;
    for(i = 0; i < N*N; i++){
        for(j = 0; j < N*N; j++){
            if(i == j) continue;
            if(board[i][col].top_of_stack == 0) break;
            if(board[i][col].top_of_stack == board[j][col].top_of_stack)
                return TRUE;
        }
    }
    return FALSE;
}

// checks for duplicates inside the mini grid
int box_duplicates(int row, int col){
    // row_upper and row_lower
    // column_upper and column_lower
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
    int i, j, k, l;
    for(i = row_lower; i < row_upper; i++){
        for(j = col_lower; j < col_upper; j++){
            if(board[i][j].top_of_stack == 0) break;
            for(k = row_lower; k < row_upper; k++){
                for(l = col_lower; l < col_upper; l++){
                    if(i == k && j == l) continue;
                    if(board[i][j].top_of_stack == board[k][l].top_of_stack){
                        return TRUE;
                    }
                }
            }
        }
    }

    return FALSE;

}

int x_duplicates(){
    int i,j,k,l;
    if(type != SUDOKU_X || type != SUDOKU_XY) return FALSE;

    for(i=0;i<N*N;i++){
        for(j=0; j<N*N; j++){
            if(i != j) continue;
            else if(board[i][j].top_of_stack == 0) continue;
            else{
                for(k=0; k<N*N; k++){
                    for(l=0; l<N*N; l++){
                        if(k != l) continue;
                        else if(i == k && j ==l) continue;
                        else if(board[i][j].top_of_stack == board[k][l].top_of_stack){
                            return TRUE;
                        }
                    }
                }
            }
        }
    }

    for(i=(N*N - 1); i>=0; i--){
        for(j=(N*N - 1); j>=0; j--){
            if((i+j) != (N*N)-1) continue;
            else if(board[i][j].top_of_stack == 0) continue;
            else{
                for(k=(N*N - 1); k>=0; k--){
                    for(l=(N*N - 1); l>=0; l--){
                        if((k+l) != (N*N)-1) continue;
                        else if(i == k && j ==l) continue;
                        else if(board[i][j].top_of_stack == board[k][l].top_of_stack){
                            return TRUE;
                        }
                    }
                }
            }
        }
    }
    return FALSE;
}

int y_duplicates(){
    int i, j, counter = 0;
    if((N*N) % 2 == 0) return FALSE;
    if(type != SUDOKU_Y || type != SUDOKU_XY) return FALSE;

    int duplicated[N*N];
    for(i=0; i<(N*N / 2); i++){
        for(j=0; j< (N*N / 2); j++){
            if(i == j){
                duplicated[counter++] = board[i][j].top_of_stack;
            }
        }
    }

    for(i=(N*N / 2); i<N*N; i++){
        duplicated[counter++] = board[i][N*N/2].top_of_stack;
    }

    for(i = 0; i < N*N; i++){
        for(j = 0; j < N*N; j++){
            if(i == j) continue;
            if(duplicated[i] == 0) break;
            if(duplicated[i] == duplicated[j])
                return TRUE;
        }
    }


    counter = 0;
    for(i=0; i<(N*N/2); i++){
        for(j=(N*N - 1); j>= (N*N/2); j--){
            if(i+j == (N*N-1)) duplicated[counter++] = board[i][j].top_of_stack;
        }
    }

    for(i = 0; i < N*N; i++){
        for(j = 0; j < N*N; j++){
            if(i == j) continue;
            if(duplicated[i] == 0) break;
            if(duplicated[i] == duplicated[j])
                return TRUE;
        }
    }

    return FALSE;
}

int duplicates_exist(int row, int col){
    // general function for checking for duplicats
    if(box_duplicates(row, col)) return TRUE; 
    if(row_duplicates(row)) return TRUE; 
    if(col_duplicates(col)) return TRUE; 
    if(x_duplicates()) return TRUE;
    if(y_duplicates()) return TRUE;
    // add x_duplicates checking
    // add y_duplicates checking for odd value of N
    
    return FALSE;
}

void backtrack(int row, int col){
	int occur = 0;

    // kapag naging -1 -1 na ang value, ibig sabihin umabot na sa top left most value nang walang nakikita
    while(row != -1 || col != -1){
        if(row == N*N){
            //umabot na sa dulo(lower right most)
            // solution found
            occur++; // solution counter
            //printf("FINAL BOARD STATE: %d\n", occur);
            //print_board_status(); 
            
            // goes back to the last non preset cell to generate all possible solutions
            row = last_row;
            col = last_col; 
        } else if(col == N*N){
            // umabot na sa rightmost column pero kahit saang row
            row = row + 1;
            col = 0;
        } else if(board[row][col].is_preset == TRUE){
            // check mo kung preset
            row = row;
            col = col + 1;
        } else if(board[row][col].is_preset == FALSE){
            // increase the current value since simula 0 yung TOS ng non-preset
            board[row][col].top_of_stack++;
            
            //check kung yung value ay exceeding na sa pwedeng iinput sa board
            if(board[row][col].top_of_stack <= N*N){
                // check mo kung TOS ay di pa lagpas N*N
                if(duplicates_exist(row, col)){
                    row = row;
                    col = col;
                } else{
                    row = row;
                    col = col + 1;
                }
            } else{
                // reset back to 0 kapag lumagpas na sa N*N yung balue
                // this means no value can fit to the cell, kaya kailangan mag backtrack
                board[row][col].top_of_stack = INIT;

                int new_row = board[row][col].prev_row; 
                int new_col = board[row][col].prev_col;

                row = new_row;
                col = new_col;
            }
        }
    }
    printf("occurrences: %d\n", occur); 
    printf("NO MORE POSSIBLE SOLUTION\n");
}

int main(){
    int i, j, k, test_cases;
    FILE * fp;
    
    fp = fopen("input.in", "r");
    fscanf(fp, "%d", &test_cases);

    for(k=0; k<test_cases; k++){
	    //board instantiate
	    fscanf(fp, "%d", &N);
	    board = (sudoBox**)malloc(sizeof(sudoBox*) * (N*N));
	    for (i = 0; i < N*N; i++){
	        board[i] = (sudoBox*)malloc(sizeof(sudoBox) * (N*N));
	    }
	    
	    // board filling
	    int prev_col = -1, prev_row = -1;
	    for(i = 0; i < N*N; i++){
	        for(j = 0; j < N*N; j++){
	            fscanf(fp, "%d", &(board[i][j].top_of_stack));
	            board[i][j].prev_row = prev_row;
	            board[i][j].prev_col = prev_col;
	            if(board[i][j].top_of_stack != 0){
	                board[i][j].is_preset = TRUE;
	            } else{
	                board[i][j].is_preset = FALSE; 
	                prev_row = i;
	                prev_col = j;
	            }
	        }
	    }
	    last_row = prev_row;
	    last_col = prev_col;
	    //print_board_status(); 
	    
	    printf("\n\n");
	    backtrack(0, 0);

	    for(i=0;i<N*N; i++){
	    	free(board[i]);
	    }
	    free(board);

    }

    return 0;    
}

