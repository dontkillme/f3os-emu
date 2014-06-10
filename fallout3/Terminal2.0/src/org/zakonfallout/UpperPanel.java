package org.zakonfallout;

import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.zakonfallout.utils.Parsers;

public class UpperPanel extends JPanel {

	private int width = Config.getWidth(),
			    height = (int) Math.ceil(Config.getHeight()/(double) 1/6), // we want to have 1/6 of the screen for title etc
			    imgH,
			    imgW;
	private BufferedImage img;
	
	public UpperPanel(){
		SetUp();
	}
	
	/**
	 * sets up the whole panel. Also is used after changing stuff in admin panel
	 */
	public void SetUp(){
		setBackground(Config.getBackgroundColor());
		setSize(width, height);
		setLayout(null);
		setFont(Config.getFont());
		removeAll();
		setUpPanel();
	}
	
	/**
	 * Sets up the panel stuff
	 */
	public void setUpPanel(){
		String temp[] = Parsers.filterText(Config.getInfoText());
		
		if(temp[1]!=null){
			
			try {
				img = ImageIO.read(new File(Parsers.rootToFileConv(Config.getRoot()+"/"+temp[1])));
				
				imgW = (int) Math.floor(img.getWidth() * ((double) height/img.getHeight()) );
				imgH = (int) Math.floor(img.getHeight() * ((double) height/img.getHeight()) );
				
//				img = Parsers.recolorImage( img, imgW, imgH	);
				img = Parsers.resetTheImage(img, imgW, imgH, temp[1]);
			} catch (IOException e) { 
				e.printStackTrace();
				}
			
		}
		repaint();
		add(createText(temp[0]));
	
	}
	
	public void paintComponent(Graphics g){
		if(img!=null){
			g.drawImage(img, 0, 0, null);
		}
		g.setColor(Config.getFrontgroundColor());
		g.drawLine(0, height-1, width, height-1);
		
	}
	
	/**
	 * creates upper text.
	 * @return jtextfield with text
	 */
	private JTextArea createText(String text){
		JTextArea textOut = new JTextArea();
		
		if(img!=null){
			textOut.setBounds(imgW+5, 5, width-imgW-5, height-10);
		}else{
			textOut.setBounds(5, 5, width, height-10);
		}
		textOut.setFont(Config.getFont());
		textOut.setForeground(Config.getFrontgroundColor());
		textOut.setBackground(Config.getBackgroundColor());
		textOut.setEditable(false);
		textOut.setWrapStyleWord(true);
		textOut.setLineWrap(true);
		textOut.setText(text);
		
		return textOut;
	}
	
	
}
