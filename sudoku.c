#include <stdio.h>
#include <stdlib.h>
#define TRUE 1
#define FALSE 0
#define INIT 0 

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

int duplicates_exist(int row, int col){
    // general function for checking for duplicats
    if(box_duplicates(row, col)) return TRUE; 
    if(row_duplicates(row)) return TRUE; 
    if(col_duplicates(col)) return TRUE; 
    // add x_duplicates checking
    // add y_duplicates checking for odd value of N
    
    return FALSE;
}

int occur = 0;
void backtrack(int row, int col){
    // kapag naging -1 -1 na ang value, ibig sabihin umabot na sa top left most value nang walang nakikita
    if(row == -1 && col == -1){
        printf("occurrences: %d\n", occur); 
        printf("NO MORE POSSIBLE SOLUTION\n");
    } else if(row == N*N){
        //umabot na sa dulo(lower right most)
        // solution found
        occur++; // solution counter
        printf("FINAL BOARD STATE: %d\n", occur);
        print_board_status();
        
        // goes back to the last non preset cell to generate all possible solutions
        backtrack(last_row, last_col); 
    } else if(col == N*N){
        // umabot na sa rightmost column pero kahit saang row
        backtrack(row + 1, 0);
    } else if(board[row][col].is_preset == TRUE){
        // check mo kung preset
        backtrack(row, col + 1);
    } else if(board[row][col].is_preset == FALSE){
        // increase the current value since simula 0 yung TOS ng non-preset
        board[row][col].top_of_stack++;
        
        //check kung yung value ay exceeding na sa pwedeng iinput sa board
        if(board[row][col].top_of_stack <= N*N){
            // check mo kung TOS ay di pa lagpas N*N
            if(duplicates_exist(row, col)){
                backtrack(row, col);
            } else{
                backtrack(row, col + 1);
            }
        } else{
            // reset back to 0 kapag lumagpas na sa N*N yung balue
            // this means no value can fit to the cell, kaya kailangan mag backtrack
            board[row][col].top_of_stack = INIT;

            backtrack(board[row][col].prev_row, board[row][col].prev_col);
        }
    }
}

int main(){
    int i, j, test_cases;
    FILE * fp;
    
    fp = fopen("file.txt", "r");
    fscanf(fp, "%d", &N);

    //board instantiate
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
    print_board_status();
    
    printf("\n\n");
    backtrack(0, 0);    
}

