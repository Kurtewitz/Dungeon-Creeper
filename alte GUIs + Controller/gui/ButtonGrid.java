package gui;
import java.awt.*;
import java.applet.Applet;
import javax.swing.*;
 
 
 @SuppressWarnings("serial")
public class ButtonGrid extends Applet {
     public static final int  size = 2;
     Icon icon;
     JButton[] button;
	 
	 public ButtonGrid() {
		 button = new JButton[size * size];
		 icon = new ImageIcon("baum.gif"); //Datei kann nicht gelesen werden
		 System.out.println(icon.getIconHeight());
         setLayout(new GridLayout(size + 1, size));
         for(int i = 0; i < size * size; i++) button[i] = new JButton(icon);
         for(int i = 0; i < size * size; i++) add(button[i]);
         //add(button);
         //add(button);
         //add(button);
         add(new JButton("5"));
         add(new JButton("6"));
     }
	 
/**	 public static void main(String[] args) {
		 new ButtonGrid();
	 }
	 */
 }