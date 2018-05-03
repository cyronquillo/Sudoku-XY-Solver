/*Class for the Sudoku Puzzle UI*/
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;

import java.io.File;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
public class GamePanel extends JPanel{
	private static ArrayList<Board> puzzles;
	private static JPanel buttonPanel;
	private static JPanel puzzlePanel;
	private static JButton next;
	private static JButton prev;

	public GamePanel(){
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));

		buttonPanel = new JPanel();
		puzzlePanel = new JPanel();

		next = new JButton();
		prev = new JButton();

		puzzlePanel.setLayout(new CardLayout());
		buttonPanel.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));

		try{
			BufferedImage img = ImageIO.read(new File("Images/next-button.png"));
			next.setIcon(new ImageIcon(img));
			next.setSize(50,50);
			next.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));
			next.setMargin(new Insets(0, 0, 0, 0));
			next.setBorder(new EmptyBorder(0,0,0,0));
			/*next.setEnabled(false);	*/	

			img = ImageIO.read(new File("Images/prev-button.png"));

			prev.setIcon(new ImageIcon(img));
			prev.setSize(50,50);
			prev.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));
			prev.setMargin(new Insets(0, 0, 0, 0));
			prev.setBorder(new EmptyBorder(0,0,0,0));
			/*prev.setEnabled(false);*/

		}catch(Exception e){
			System.out.println(e);
		}


		buttonPanel.add(next);
		buttonPanel.add(prev);

		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(puzzlePanel, BorderLayout.CENTER);

	}

	public void setPuzzles(ArrayList<Board> cases){
		this.puzzles = cases;

		for(int i=0; i<this.puzzles.size(); i++){
			JPanel panel = new JPanel();
			int n = this.puzzles.get(i).size;
			JTable sudoku = new JTable(n, n);

			this.fillTable(sudoku, this.puzzles.get(i), n);
			panel.add(sudoku);
			this.puzzlePanel.add(panel, String.valueOf(i));
		}

		this.revalidate();
	}

	public void fillTable(JTable sudoku, Board puzzle, int size){
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				sudoku.setValueAt(i, j, puzzle.board[i][j].top_of_stack);
			}
		}
	}

}