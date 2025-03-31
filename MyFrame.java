package assignPackage5;
import java.awt.Color;

import javax.swing.*;

public class MyFrame extends JFrame {
	
	MyFrame(){
		
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(new Color(42,44,65));
	
	}
}
