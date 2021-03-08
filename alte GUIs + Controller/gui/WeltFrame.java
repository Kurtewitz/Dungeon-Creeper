package gui;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class WeltFrame extends JFrame {

	public static int map = 1;
	
	private JFrame frame = new JFrame("Dungeon Creeper");
    private JLayeredPane lpane;
    //private BodenPanel boden;
    //private UmgebungPanel umgebung;
    private HeroPanel hero;
    //private FunctionPanel function;
    private MapChooser mapchooser;
    private SteuerungLandkarte steuerung;
    
    public WeltFrame() {
    	//setMap(1);
    	
    	lpane = new JLayeredPane();
    	//boden = new BodenPanel();
    	//umgebung = new UmgebungPanel();
    	//function = new FunctionPanel();
    	mapchooser = new MapChooser();
    	hero = new HeroPanel();
    	steuerung = new SteuerungLandkarte(this);
    	
    	frame.setPreferredSize(new Dimension(Main.SIZE + 10, Main.SIZE + 38));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        
        //boden.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        //umgebung.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        //function.setBounds(0,0,Main.SIZE + 10, Main.SIZE + 38);
        hero.setBounds(UmgebungPanel.objektSize, Main.SIZE - UmgebungPanel.objektSize - HeroPanel.heroSize, HeroPanel.heroSize, HeroPanel.heroSize);
        
        //lpane.add(boden, new Integer(0), 0);
        //lpane.add(umgebung, new Integer(0), 0);
        //lpane.add(function, new Integer(0), 0);
        lpane = mapchooser.lpane();
        lpane.add(hero, new Integer(0), 0);
        frame.pack();
        //frame.setUndecorated(true);
        frame.setVisible(true);
    }
    public JFrame frame() {
    	return frame;
    }
    public JLayeredPane lpane() {
    	return lpane;
    }
    public HeroPanel hero() {
    	return hero;
    }
    public SteuerungLandkarte steuerung() {
    	return steuerung;
    }
    public BodenPanel boden(){
    	return mapchooser.boden();
    	//return boden;
    }
    public UmgebungPanel umgebung() {
    	return mapchooser.umgebung();
    	//return umgebung;
    }
    
    public FunctionPanel function() {
    	return mapchooser.function();
    	//return function;
    }
    
    
    public void setMap(int i) {
    	map = i;
    	//boden.setMap(i);
    	//umgebung.setMap(i);
    	//function.setMap(i);
    }
    
    public void changeMap(int i) {
    	
    	//return new MainMini(i);
    	setMap(i);
    	
    	frame.remove(lpane);
    	lpane = new JLayeredPane();
    	mapchooser = new MapChooser();
    	//boden = new BodenPanel();
    	//umgebung = new UmgebungPanel();
    	//function = new FunctionPanel();
    	lpane = mapchooser.lpane();
    	hero = new HeroPanel();   //Avatar aendern abhaengig von der Map? Wintermap = Pelz etc.
    	lpane.remove(hero);
    	
		frame.setPreferredSize(new Dimension(Main.SIZE + 10, Main.SIZE + 38));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        
    	//boden.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        //umgebung.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        //function.setBounds(0,0,Main.SIZE + 10, Main.SIZE + 38);
    	if(i == 1) {
    		hero.setBounds(Main.SIZE - FunctionPanel.objektSize - HeroPanel.heroSize, UmgebungPanel.objektSize, HeroPanel.heroSize, HeroPanel.heroSize);
    	}
    	if(i == 2) {
    		hero.setBounds(FunctionPanel.objektSize, UmgebungPanel.objektSize, HeroPanel.heroSize, HeroPanel.heroSize);
    	}
    	
    	//lpane.add(boden, new Integer(0), 0);
        //lpane.add(umgebung, new Integer(0), 0);
        //lpane.add(function, new Integer(0), 0);
        lpane = mapchooser.lpane();
    	lpane.add(hero, new Integer(0), 0);
        
        frame.add(lpane, BorderLayout.CENTER);
        frame.pack();
    	frame.setVisible(true);
    	
    }
    
    
    
    
}
