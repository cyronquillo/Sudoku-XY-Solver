import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

class CellKeyListener implements KeyListener {
    private int row;
    private int col;
    private Board puzzle;

    public CellKeyListener(int row, int col, Board puzzle){
        this.row = row;
        this.col = col;
        this.puzzle = puzzle;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }   

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*Improve po pls*/
        char c = e.getKeyChar();
        JTextField textField = (JTextField) e.getSource();
        String regex = textField.getText().replaceAll("[^\\d]", "");
        if(!regex.matches("\\d+")){
            this.puzzle.board[this.row][this.col].top_of_stack = 0;
            textField.setText("");   
        } else{
            int num = Integer.parseInt(regex);
            if(num < 1) num = 0;
            if(num > this.puzzle.size) num = this.puzzle.size;
            if((c < '0' || c > '9') && c != KeyEvent.VK_BACK_SPACE){
                e.consume();
            } else{
                this.puzzle.board[this.row][this.col].top_of_stack = num;
                this.puzzle.outputBoard();
                if(num != 0){
                    textField.setText("" + num);   
                } else{
                    textField.setText("");
                }
            }         

        }

    }
}