package gui;

import javax.swing.JLayeredPane;

public class MapChooser {
	
	private JLayeredPane lpane;
    private BodenPanel boden;
    private UmgebungPanel umgebung;
    private FunctionPanel function;
    static int choose;
    
    public MapChooser(){
    	choose = WeltFrame.map;
    	
    	lpane = new JLayeredPane();
    	if(choose == 1) {
    		boden = new BodenPanelWald();
    		umgebung = new UmgebungPanelWald();
    		function = new FunctionPanelWald();
    	}
    	if(choose == 2) {
    		boden = new BodenPanelFriedhof();
    		umgebung = new UmgebungPanelFriedhof();
    		function = new FunctionPanelFriedhof();
    	}
    	//boden = new BodenPanel(choose);
    	//function = new FunctionPanel(choose);
    	
    	boden.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        umgebung.setBounds(0, 0, Main.SIZE + 10, Main.SIZE + 38);
        function.setBounds(0,0,Main.SIZE + 10, Main.SIZE + 38);
    	
    	lpane.add(boden, new Integer(0), 0);
    	lpane.add(umgebung, new Integer(0), 0);
    	lpane.add(function, new Integer(0), 0);
    }
    
    public BodenPanel boden() {
    	return boden;
    }
    public UmgebungPanel umgebung() {
    	return umgebung;
    }
    public FunctionPanel function() {
    	return function;
    }
    public JLayeredPane lpane() {
    	return lpane;
    }
    
}
