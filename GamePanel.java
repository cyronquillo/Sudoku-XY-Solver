/*Class for the Sudoku Puzzle UI*/
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import java.awt.Font;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.imageio.ImageIO;

import java.io.File;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePanel extends JPanel{
	private static ArrayList<Board> puzzles;
	private static JPanel buttonPanel;
	private static JButton next;
	private static JButton prev;

	public static HashMap<JPanel, Board> panelToPuzzle = new HashMap<JPanel, Board>();
	public static JPanel puzzlePanel;

	private static final Font font = new Font("Verdana", Font.BOLD, 15);

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

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) puzzlePanel.getLayout();

				c.next(puzzlePanel);
			}
		});
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) puzzlePanel.getLayout();

				c.previous(puzzlePanel);
			}
		});
	}

	public void setPuzzles(ArrayList<Board> cases){
		this.puzzles = cases;
		
		for(int i=0; i<this.puzzles.size(); i++){
			Board curr = this.puzzles.get(i);
			JPanel panel = new JPanel();
			panelToPuzzle.put(panel, curr);
			int n = curr.size;
			panel.setLayout(new GridLayout(n,n));
			panel.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.BLACK));
			panel.setBackground(Color.BLACK);
			this.fillTable(panel, curr, n);
			this.puzzlePanel.add(panel, String.valueOf(i));
		}

		this.revalidate();
	}

	public void fillTable(JPanel sudoku, Board puzzle, int size){
		JTextField cell;
		int subgrid = (int) Math.sqrt(size);
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){

				if(puzzle.board[i][j].is_preset){
					cell = new JTextField(String.valueOf(puzzle.board[i][j].top_of_stack));
					cell.setEnabled(false);
				} else{
					cell = new JTextField(" ");
					cell.setBackground(Color.YELLOW);
				}
				cell.addKeyListener(new CellKeyListener(i, j, puzzle));

				if(i%subgrid == 0 && j%subgrid==0){
					cell.setBorder(BorderFactory.createMatteBorder(3,3,1,1,Color.BLACK));
				}else if(i % subgrid == 0){
					cell.setBorder(BorderFactory.createMatteBorder(3,1,1,1,Color.BLACK));
				}else if(j % subgrid == 0){
					cell.setBorder(BorderFactory.createMatteBorder(1,3,1,1,Color.BLACK));
				}else{
					cell.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
				}
				sudoku.add(i+" "+j,cell);
				cell.setFont(font);
				//cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				cell.setHorizontalAlignment(JTextField.CENTER);
			}
		}
	}

}