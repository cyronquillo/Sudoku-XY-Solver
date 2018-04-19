import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;


public class UI{
	private static JPanel cardHolder;
	private static JPanel menu;
	private static JPanel puzzleHolder;
	private static JPanel endWin;
	private static JPanel endLose;
	private static JPanel solns;

	private static JButton start;
	private static JButton win;

	public static void main(String[] args){
		JFrame frame = new JFrame("Sudoku XY");

		cardHolder = new JPanel(new CardLayout());

		menu = new JPanel();
		puzzleHolder = new JPanel();
		endWin = new JPanel();
		endLose = new JPanel();
		solns = new JPanel();
		JPanel temp = new JPanel();

		JButton win = new JButton("Win");
		JButton lose = new JButton("Lose");

		JButton solutions = new JButton("Show Solutions");
		JButton retry = new JButton("Retry");
		JButton next = new JButton("Next");
		JButton prev = new JButton("Prev");

		puzzleHolder.add(win);
		puzzleHolder.add(lose);
		puzzleHolder.setBackground(Color.PINK);

		/*START OF MENU PANEL*/
		start = new JButton();

		try {
			temp.setBackground(Color.WHITE);
			temp.setPreferredSize(new Dimension(250, 500));

			BufferedImage img = ImageIO.read(new File("Images/play-button.png"));

			start.setIcon(new ImageIcon(img));
			start.setSize(50,50);
			start.setBackground(Color.WHITE);
			start.setMargin(new Insets(0, 0, 0, 0));
			start.setBorder(new EmptyBorder(0,0,0,0));

			temp.add(start);

			BufferedImage mainPanelImage;

			mainPanelImage = ImageIO.read(new File("Images/mainpanel.png"));
			JLabel picLabel = new JLabel(new ImageIcon(mainPanelImage));

			menu.add(picLabel);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		menu.setBackground(Color.WHITE);
		menu.add(temp);

		/*END OF MENU PANEL*/

		/*START OF WIN PANEL*/


		/*END OF WIN PANEL*/

		cardHolder.add(menu, "Menu");
		cardHolder.add(puzzleHolder, "Puzzle");
		cardHolder.add(endWin, "Win");
		cardHolder.add(endLose, "Lose");
		cardHolder.add(solns, "Solutions");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(cardHolder);

		frame.pack();
		frame.setSize(500, 500);
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
	}


}