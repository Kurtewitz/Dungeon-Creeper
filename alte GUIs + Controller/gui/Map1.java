package gui;

import java.awt.*;
import javax.swing.*;;

@SuppressWarnings("serial")
public class Map1 extends JFrame{

//	private String welt;
	public static int size = 270;
	public static int objektSize = 30;
	public static int heroSize = 30;
	public static int grabSize = 60;
	
	private JFrame frame;
	
	Icon icon;
	JButton button;
	JButton button1;
	
	public Map1() {
		
		icon = new ImageIcon("baum.gif");
		button = new JButton(icon);
		button1 = new JButton(icon);
		button.setMaximumSize(new Dimension(30,30));
		button1.setMaximumSize(new Dimension(30,30));
		//button.setSize(new Dimension(30,30));
		button.setBorderPainted(false);
		button1.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button1.setContentAreaFilled(false);
		button.setMargin(new Insets(0,0,0,0));
		button1.setMargin(new Insets(0,0,0,0));
		button.setBounds(0,0,30,30);
		button1.setBounds(30,0,30,30);
		frame = new JFrame();
		//frame.setUndecorated(false);
		frame.add(button);
		frame.add(button1);
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Map1();
	}

	
/**	public void init() {
		setLayout(new BoxLayout(BoxLayout.LINE_AXIS));
		add(new JButton(new ImageIcon("baum.gif")));
	}
*/
}
