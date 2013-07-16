package sc.fall;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * 
 * Upper part of the console.
 * Text in the top is from config.f3s file. See how_to_work.txt to get to know how to set it up
 * no magic here.
 */
public class upinfo extends JPanel {
	private Font font = new Font("Monospaced", Font.BOLD, 14);
	private JTextArea txt = new JTextArea();
	
	public upinfo(String info_term){
		setSize(800,100);
		setLayout(null);
		setBackground(Color.BLACK);
		
		txt.setText(info_term);
		
		txt.setBackground(Color.BLACK);
		txt.setForeground(new Color(0,240,0));
		txt.setEditable(false);
		txt.setLineWrap(true);
		txt.setBounds(10,15,750,70);
		txt.setFont(font);
		txt.setWrapStyleWord(true);
		
		add(txt);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.GREEN);
		g.drawLine(0, 90, 800,90);
	}
	
}
