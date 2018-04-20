import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

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

public class UI{
	private static JPanel cardHolder;
	private static JPanel menu;
	private static JPanel puzzleHolder;
	private static JPanel endWin;
	private static JPanel endLose;
	private static JPanel solns;

	private static GamePanel game;

	private static JButton solutions;
	private static JButton start;
	private static JButton win;
	private static JButton retry;
	private static JButton solutions2;
	private static JButton lose;
	private static JButton reset;
	private static JButton check;
	private static JButton checkx;
	private static JButton checky;
	private static JButton checkxy;
	private static JButton filechooser;

	private static final float RED = (float) 153/255;
	private static final float GREEN = (float) 143/255;
	private static final float BLUE = (float) 127/255;

	public static void main(String[] args){
		JFrame frame = new JFrame("Sudoku XY");

		cardHolder = new JPanel(new CardLayout());

		menu = new JPanel();
		puzzleHolder = new JPanel();
		endWin = new JPanel();
		endLose = new JPanel();
		solns = new JPanel();

		/*START OF MENU PANEL*/
		start = new JButton();

		menu.setBackground(new Color(RED, GREEN, BLUE));
		try {

			BufferedImage img = ImageIO.read(new File("Images/play-button.png"));

			start.setIcon(new ImageIcon(img));
			start.setSize(50,50);
			start.setBackground(new Color(RED, GREEN, BLUE));
			start.setMargin(new Insets(0, 0, 0, 0));
			start.setBorder(new EmptyBorder(0,0,0,0));

			BufferedImage mainPanelImage;

			mainPanelImage = ImageIO.read(new File("Images/title.png"));
			JLabel picLabel = new JLabel(new ImageIcon(mainPanelImage));
			picLabel.setPreferredSize(new Dimension(570, 275));

			menu.add(picLabel);
			menu.add(start);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		/*END OF MENU PANEL*/

		/*START OF WIN PANEL*/
		solutions = new JButton();
		endWin.setBackground(new Color(RED, GREEN, BLUE));
		endWin.setLayout(new FlowLayout());
		try {

			BufferedImage img = ImageIO.read(new File("Images/sol-button.png"));

			solutions.setIcon(new ImageIcon(img));
			solutions.setSize(50,50);
			solutions.setBackground(new Color(RED, GREEN, BLUE));
			solutions.setMargin(new Insets(0, 0, 0, 0));
			solutions.setBorder(new EmptyBorder(0,0,0,0));


			BufferedImage mainPanelImage;

			mainPanelImage = ImageIO.read(new File("Images/congrats.png"));
			JLabel picLabel = new JLabel(new ImageIcon(mainPanelImage));
			picLabel.setPreferredSize(new Dimension(600, 250));
			endWin.add(picLabel);
			endWin.add(solutions);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		/*END OF WIN PANEL*/

		/*START OF LOSE PANEL*/
		retry = new JButton();
		solutions2 = new JButton(); 
		endLose.setBackground(new Color(RED, GREEN, BLUE));
		endLose.setLayout(new BoxLayout(endLose,BoxLayout.PAGE_AXIS));
		try {
			JPanel temp = new JPanel();
			temp.setBackground(new Color(RED, GREEN, BLUE));

			BufferedImage img = ImageIO.read(new File("Images/retry-button.png"));

			retry.setIcon(new ImageIcon(img));
			retry.setSize(50,50);
			retry.setBackground(new Color(RED, GREEN, BLUE));
			retry.setMargin(new Insets(0, 0, 0, 0));
			retry.setBorder(new EmptyBorder(0,0,0,0));

			img = ImageIO.read(new File("Images/sol-button.png"));

			solutions2.setIcon(new ImageIcon(img));
			solutions2.setSize(50,50);
			solutions2.setBackground(new Color(RED, GREEN, BLUE));
			solutions2.setMargin(new Insets(0, 0, 0, 0));
			solutions2.setBorder(new EmptyBorder(0,0,0,0));

			temp.add(retry);
			temp.add(solutions2);

			BufferedImage mainPanelImage;

			mainPanelImage = ImageIO.read(new File("Images/tryagain.png"));
			JLabel picLabel = new JLabel(new ImageIcon(mainPanelImage));
			picLabel.setPreferredSize(new Dimension(600, 300));

			picLabel.setAlignmentX(endLose.CENTER_ALIGNMENT);
			temp.setAlignmentX(endLose.CENTER_ALIGNMENT);

			endLose.add(picLabel);
			endLose.add(temp);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		/*END OF LOSE PANEL*/

		/*START OF SOLUTIONS PANEL*/
		JButton next = new JButton();
		JButton prev = new JButton();
		JPanel buttons = new JPanel();
		JPanel answer = new JPanel();
		try {
			buttons.setBackground(new Color(RED, GREEN, BLUE));
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER,70,10));
			BufferedImage img = ImageIO.read(new File("Images/next-button.png"));
			answer.setBackground(Color.PINK);
			next.setIcon(new ImageIcon(img));
			next.setSize(50,50);
			next.setBackground(new Color(RED, GREEN, BLUE));
			next.setMargin(new Insets(0, 0, 0, 0));
			next.setBorder(new EmptyBorder(0,0,0,0));

			img = ImageIO.read(new File("Images/prev-button.png"));

			prev.setIcon(new ImageIcon(img));
			prev.setSize(50,50);
			prev.setBackground(new Color(RED, GREEN, BLUE));
			prev.setMargin(new Insets(0, 0, 0, 0));
			prev.setBorder(new EmptyBorder(0,0,0,0));

			
			buttons.add(prev);
			buttons.add(next);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		solns.setLayout(new BorderLayout());
		solns.add(buttons,BorderLayout.SOUTH);
		solns.add(answer,BorderLayout.CENTER);
		solns.setBackground(new Color(RED, GREEN, BLUE));
		/*END OF SOLUTIONS PANEL*/

		/*START OF PUZZLE PANEL*/
		JPanel sideOptions = new JPanel();
		JPanel topOptions = new JPanel();

		game = new GamePanel();
		win = new JButton("Win");
		lose = new JButton("Lose");
		reset = new JButton();
		check = new JButton();
		checkx = new JButton();
		checky = new JButton();
		checkxy = new JButton();
		filechooser = new JButton();

		sideOptions.setPreferredSize(new Dimension(150 ,500));
		puzzleHolder.setLayout(new BorderLayout());
		game.setBackground(Color.BLUE);
		sideOptions.setBackground(new Color(RED, GREEN, BLUE));
		topOptions.setBackground(new Color(RED, GREEN, BLUE));

		sideOptions.setLayout(new BoxLayout(sideOptions,BoxLayout.PAGE_AXIS));
		topOptions.setLayout(new BoxLayout(topOptions, BoxLayout.X_AXIS));

		try{
			BufferedImage img = ImageIO.read(new File("Images/reset-button.png"));
			reset.setPreferredSize(new Dimension(60, 60));
			reset.setIcon(new ImageIcon(img));
			reset.setBackground(new Color(RED, GREEN, BLUE));
			reset.setMargin(new Insets(0, 0, 0, 0));
			reset.setBorder(new EmptyBorder(0,0,0,0));

			img = ImageIO.read(new File("Images/file-button.png"));
			filechooser.setIcon(new ImageIcon(img));
			filechooser.setPreferredSize(new Dimension(60, 60));
			filechooser.setBackground(new Color(RED, GREEN, BLUE));
			filechooser.setMargin(new Insets(0, 0, 0, 0));
			filechooser.setBorder(new EmptyBorder(0,0,0,0));

			topOptions.add(Box.createHorizontalStrut(50));
			topOptions.add(filechooser);
			topOptions.add(Box.createHorizontalStrut(430));
			topOptions.add(reset);

			img = ImageIO.read(new File("Images/check-button.png"));
			check.setIcon(new ImageIcon(img));
			check.setSize(50,50);
			check.setBackground(new Color(RED, GREEN, BLUE));
			check.setMargin(new Insets(0, 0, 0, 0));
			check.setBorder(new EmptyBorder(0,0,0,0));
			check.setAlignmentX(check.CENTER_ALIGNMENT);

			img = ImageIO.read(new File("Images/checkx-button.png"));
			checkx.setIcon(new ImageIcon(img));
			checkx.setSize(50,50);
			checkx.setBackground(new Color(RED, GREEN, BLUE));
			checkx.setMargin(new Insets(0, 0, 0, 0));
			checkx.setBorder(new EmptyBorder(0,0,0,0));
			checkx.setAlignmentX(checkx.CENTER_ALIGNMENT);

			img = ImageIO.read(new File("Images/checky-button.png"));
			checky.setIcon(new ImageIcon(img));
			checky.setSize(50,50);
			checky.setBackground(new Color(RED, GREEN, BLUE));
			checky.setMargin(new Insets(0, 0, 0, 0));
			checky.setBorder(new EmptyBorder(0,0,0,0));
			checky.setAlignmentX(checky.CENTER_ALIGNMENT);

			img = ImageIO.read(new File("Images/checkxy-button.png"));
			checkxy.setIcon(new ImageIcon(img));
			checkxy.setSize(50,50);
			checkxy.setBackground(new Color(RED, GREEN, BLUE));
			checkxy.setMargin(new Insets(0, 0, 0, 0));
			checkxy.setBorder(new EmptyBorder(0,0,0,0));
			checkxy.setAlignmentX(checkxy.CENTER_ALIGNMENT);

			
			sideOptions.add(win);//temp button only
			sideOptions.add(lose);//temp button only
			
			/*sideOptions.add(Box.createHorizontalStrut(10));*/
			sideOptions.add(check);
			sideOptions.add(Box.createVerticalStrut(10));

			/*sideOptions.add(Box.createHorizontalStrut(10));*/
			sideOptions.add(checkx);
			sideOptions.add(Box.createVerticalStrut(10));

			/*sideOptions.add(Box.createHorizontalStrut(10));*/
			sideOptions.add(checky);
			sideOptions.add(Box.createVerticalStrut(10));

			/*sideOptions.add(Box.createHorizontalStrut(10));*/
			sideOptions.add(checkxy);
		
		}catch(Exception ex){
			System.out.println(ex);
		}
		puzzleHolder.add(topOptions, BorderLayout.NORTH);
		puzzleHolder.add(sideOptions,BorderLayout.EAST);
		puzzleHolder.add(game,BorderLayout.CENTER);

		/*END OF PUZZLE PANEL*/

		cardHolder.add(menu, "Menu");
		cardHolder.add(puzzleHolder, "Puzzle");
		cardHolder.add(endWin, "Win");
		cardHolder.add(endLose, "Lose");
		cardHolder.add(solns, "Solutions");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(cardHolder);

		frame.pack();
		frame.setSize(700, 500);
	    frame.setLocation(500, 100);
	    frame.setVisible(true);
		
	    /*ACTION LISTENERS*/
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) cardHolder.getLayout();

				c.show(cardHolder, "Puzzle");
			}
		});

		win.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) cardHolder.getLayout();

				c.show(cardHolder, "Win");
			}
		});

		lose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) cardHolder.getLayout();

				c.show(cardHolder, "Lose");
			}
		});

		retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) cardHolder.getLayout();

				c.show(cardHolder, "Puzzle");
			}
		});

		solutions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) cardHolder.getLayout();

				c.show(cardHolder, "Solutions");
			}
		});

		solutions2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				CardLayout c = (CardLayout) cardHolder.getLayout();

				c.show(cardHolder, "Solutions");
			}
		});
		
		filechooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){
					File selected = jfc.getSelectedFile();
				}	
			}
		});
	}


}