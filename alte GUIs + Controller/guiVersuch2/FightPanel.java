package guiVersuch2;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import skills.Skill;

import welt.Model;

@SuppressWarnings("serial")
public class FightPanel extends JPanel {

	BufferedImage bg;
	JLabel bgLab;
	JPanel hotbar;
	JPanel hpBar;
	Model m;
	
	public FightPanel(Model m) {
		super();
		this.m = m;
		BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(l);
		
		
		try {
		      bg = ImageIO.read(new File("images/background1.gif"));
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		bgLab = new JLabel(new ImageIcon(bg.getScaledInstance(MainFrame.screenWidth, MainFrame.screenHeight - 100, java.awt.Image.SCALE_SMOOTH)));
		
		
		add(bgLab);
		bgLab.setHorizontalAlignment(0);
		
		hotbar = new Hotbar(m);
		/*for(int i = 0; i < 10; i++) {
			hotbar.add(new JButton("" + i));
		}*/
		add(hotbar);
		hotbar.setAlignmentX(0);

		
	}
	
	public class Hotbar extends JPanel {
		public Hotbar(Model m) {
			super();
			JButton temp;
			ButtonAction tempAction;
			for(int i = 0; i < 5; i++) {
				temp = new JButton(m.player().skills().getSkill(0, i).toString());
				System.out.println("buttons erstellen (FightPanel)");
				tempAction = new ButtonAction(m.player().skills().getSkill(0, i));
				
				temp.setAction(tempAction);
				temp.setText(m.player().skills().getSkill(0, i).toString());
				temp.setToolTipText(tempAction.skill().description());
				
			    /*temp.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseEntered(java.awt.event.MouseEvent evt) {
			        	
			        }
			        public void mouseExited(java.awt.event.MouseEvent evt) {
			        temp.setBackground(UIManager.getColor("control"));
			        }
			        });
				*/
				add(temp);
			}
		}
		
		public class ButtonAction extends AbstractAction {
			Skill skill;
			
			public ButtonAction(Skill s) {
				skill = s;
			}
			
			public void actionPerformed(ActionEvent e) {
				if(m.fightJudge().opponent().HP() > 0) {
					skill.use(m.fightJudge().opponent());
					if(m.fightJudge().opponent().HP() > 0) {
						m.fightJudge().opponent().turn(m.player());
					}
					else {
						m.fightJudge().loot();
					
					}
				}
			}
			
			public Skill skill() {
				return skill;
			}
		}
	}
	
	public void setActionListener(ActionListener a) {
		for(Component c : hotbar.getComponents()) {
			JButton b = (JButton) c;
			b.addActionListener(a);
		}
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FightPanel fp = new FightPanel(new Model());
//		fp.setVisible(true);
		f.add(fp);
		f.pack();
		f.setVisible(true);
//		fp.setVisible(true);
		
		
	}
}
