import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
public class Solutions{
	public ArrayList<Board> solutions;
	public static JButton next;
	public static JButton prev;
	public static JPanel  btnPanel;
	public static JPanel centerPanel;
	public static JFrame solFrame;
	public static JPanel holder;
	public static JLabel label_counter;
	public int counter;
	private static final Font font = new Font("Verdana", Font.BOLD, 15);

	public Solutions(){
		this.counter = 1;
		this.solFrame = new JFrame("Solutions");
		this.holder = new JPanel();

		this.holder.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));
		this.holder.setLayout(new BorderLayout());

		btnPanel = new JPanel();
		centerPanel = new JPanel();
		centerPanel.setLayout(new CardLayout());

		next = new JButton();
		prev = new JButton();

		btnPanel.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,70,10));
		try {
			BufferedImage img = ImageIO.read(new File("Images/next-button.png"));
			next.setIcon(new ImageIcon(img));
			next.setSize(50,50);
			next.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));
			next.setMargin(new Insets(0, 0, 0, 0));
			next.setBorder(new EmptyBorder(0,0,0,0));

			img = ImageIO.read(new File("Images/prev-button.png"));

			prev.setIcon(new ImageIcon(img));
			prev.setSize(50,50);
			prev.setBackground(new Color(UI.RED, UI.GREEN, UI.BLUE));
			prev.setMargin(new Insets(0, 0, 0, 0));
			prev.setBorder(new EmptyBorder(0,0,0,0));

			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		label_counter = new JLabel("");
		label_counter.setFont(new Font(label_counter.getName(), Font.BOLD, 15));
		btnPanel.add(prev);
		btnPanel.add(next);
		btnPanel.add(label_counter);
		

		this.holder.add(btnPanel, BorderLayout.SOUTH);
		this.holder.add(centerPanel, BorderLayout.CENTER);

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) centerPanel.getLayout();

				c.next(centerPanel);
				counter++;
				if(counter > solutions.size()) counter = 1;
				label_counter.setText(counter + " of " + solutions.size());
			}
		});

		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) centerPanel.getLayout();

				c.previous(centerPanel);
				
				counter--;
				if(counter == 0) counter = solutions.size();
				label_counter.setText(counter + " of " + solutions.size());	
			}
		});

		solFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		solFrame.setContentPane(holder);

		solFrame.pack();
		solFrame.setSize(500, 500);
		solFrame.setLocation(250, 50);
		solFrame.setVisible(true);

	}

	public void displaySolutions(Solver solver){
		this.solutions = solver.getSolutions();
		System.out.println(this.solutions.size());
		label_counter.setText(counter + " of " + solutions.size());	

		for(int i=0; i<this.solutions.size(); i++){
			int n = this.solutions.get(i).size;
			JPanel temp = new JPanel();
			temp.setLayout(new GridLayout(n,n));
			temp.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.BLACK));
			temp.setBackground(Color.BLACK);
			this.fillTable(temp, this.solutions.get(i));
			centerPanel.add(temp, String.valueOf(i));
		}
	}

	public void fillTable(JPanel panel, Board puzzle){
		JTextField cell;
		int size = puzzle.size;
		int subgrid = (int) Math.sqrt(size);

		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				cell = new JTextField(String.valueOf(puzzle.board[i][j].top_of_stack));
				cell.setEditable(false);

				if(i%subgrid == 0 && j%subgrid==0){
					cell.setBorder(BorderFactory.createMatteBorder(3,3,1,1,Color.BLACK));
				}else if(i % subgrid == 0){
					cell.setBorder(BorderFactory.createMatteBorder(3,1,1,1,Color.BLACK));
				}else if(j % subgrid == 0){
					cell.setBorder(BorderFactory.createMatteBorder(1,3,1,1,Color.BLACK));
				}else{
					cell.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
				}

				panel.add(i+" "+j,cell);
				cell.setFont(font);
				cell.setHorizontalAlignment(JTextField.CENTER);
			}
		}
	}

}