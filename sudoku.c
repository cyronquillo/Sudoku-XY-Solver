#include <stdio.h>
#include <stdlib.h>
#define TRUE 1
#define FALSE 0
#define INIT 0 

int last_row, last_col;
typedef struct sudokuBox{
	int is_preset;
    int top_of_stack;
    int prev_row;
    int prev_col;
} sudoBox ;

int N;
sudoBox ** board;


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

int box_duplicates(int row, int col){
    // row_upper and row_lower
    // column_upper and column_lower
    int row_lower = 0, row_upper = N;
    int col_lower = 0, col_upper = N;
    while(row_upper <= row){
        row_upper += N;
        row_lower += N;
    }

    while(col_upper <= col){
        col_upper += N;
        col_lower += N;
    }
    int i, j, k, l;
    for(i = row_lower; i < row_upper; i++){
        for(j = col_lower; j < col_upper; j++){
            for(k = row_lower; k < row_upper; k++){
                for(l = col_lower; l < col_upper; l++){
                    if(i == k && j == l) continue;
                    if(board[i][j].top_of_stack == 0) break;
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
    if(box_duplicates(row, col)) return TRUE; 
    if(row_duplicates(row)) return TRUE; 
    if(col_duplicates(col)) return TRUE; 
    return FALSE;
}
int occur = 0;
void backtrack(int row, int col){
    // printf("%d\n", occur);
    if(row == -1 && col == -1){
        printf("NO MORE POSSIBLE SOLUTION\n");
    } else if(row == N*N){
        // solution found
        occur++;
        printf("FINAL BOARD STATE: %d\n", occur);
        print_board_status();
        backtrack(last_row, last_col);
    } else if(col == N*N){
        backtrack(row + 1, 0);
    } else if(board[row][col].is_preset == TRUE){
        // check mo kung preset
        backtrack(row, col + 1);
    } else if(board[row][col].is_preset == FALSE){
        // increase the current value since simula 0 yung TOS ng non-preset
        board[row][col].top_of_stack++;
        if(board[row][col].top_of_stack <= N*N){
            // check mo kung TOS ay di pa lagpas N*N
            if(duplicates_exist(row, col)){
                backtrack(row, col);
            } else{
                backtrack(row, col + 1);
            }
        } else{
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

    // printf("%d", box_duplicates(0, 6));
    
}

