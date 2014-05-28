package org.zakonfallout;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.zakonfallout.objects.Paintings;
import org.zakonfallout.utils.Parsers;

/**
 * This is the main text and graphics panel, it shows magic and stuff like harry potter...
 * but srly
 * @author Samlis Coldwind
 *
 */
public class MiddlePanel extends JPanel {
	
	/*
	 * primitivs
	 */

	private int width = Config.getWidth(),
				height = (int) Math.ceil(Config.getHeight()*(double) 23/30),
				contsHeight = (int) Math.ceil(Config.getHeight()*(double) 23/30),
				lastPosition=0;
	/*
	 * Jcompots
	 */
	
	
	/**
	 * Main constructor
	 */
	
	public MiddlePanel(){
		setPanel();
	}

	/**
	 * Sets up the whole panel.
	 */
	public void setPanel(){
		calculation();
		resize(width-10,height);
		setLayout(null);
		setBackground(Config.getBackgroundColor());
	}
	
	/**
	 * calculates the stuff for the panel;
	 */
	private void calculation(){
		width = Config.getWidth();
		height = (int) Math.ceil(Config.getHeight()*(double) 23/30);
		contsHeight = (int) Math.ceil(Config.getHeight()*(double) 23/30);
		lastPosition=0;
	}
	
	/**
	 * Sets the width of panel
	 * @param width - guess what
	 */
	protected void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets new height for middlePanel .
	 * @param height
	 */
	protected void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * reloads the whole panel settings and repaints it... 
	 * more use in future.
	 */
	protected void reload(){

		resize(width,height);
		validate();
		repaint();
	}
	
	/*
	 * need to rewrite this to have isthereimage method.
	 */
	protected void addText(String text){
		lastPosition=0;
		removeAll();

		reload();
		BufferedReader inReader = new BufferedReader(new StringReader(text));
		
		String line, textToAdd="", temp[];
		int lines = 0;
		int tempHeight = 0;
		
		try {
				while( (line = inReader.readLine()) != null){
				
					temp = Parsers.filterText(line);
					// if there is no img, then text is add to the rest. Else it will be add as new
					// jtextarea, under it img, and under that a text again...
					if(temp[1]==null){
						textToAdd+=temp[0]+"\n";
						++lines;
					
					}else{
						/*
						 * adds text before <img>
						 */
						textToAdd+=temp[0]+"\n";
						++lines;
						
						/*
						 * calculates, sets height, creates and adds text, then zeros lines
						 */
//						tempHeight=countLines(textToAdd, lines);
						tempHeight=Parsers.countLines2(textToAdd);
						setHeight(this.height+tempHeight*Config.getFontHeight());
						add(createTextArea(textToAdd,tempHeight));
						lines=0;
						
						/*
						 * if there is something after <img> it sets textToAdd to have it else creates empty textToAdd
						 */
						if(temp[2]!=null) textToAdd=""+temp[2];
						else textToAdd="";
						
						BufferedImage img;
						
						try{
							img = ImageIO.read(new URL(Config.getRoot()+Config.getCatalog()+"/"+temp[1]));
						}catch(IOException e){
							img = Paintings.noPicture(Config.getFrontgroundColor());
						}
						/*
						 * calculates the ration to resize the image height to ration when width is equal screen width-20 
						 */
						int height,width;
						
						/*
						 * Checks if image is bigger than width = 780 pixels if its is, then resize.
						 */
						if(img.getWidth()>getWidth()){
							height = (int) Math.floor(img.getHeight() * ((double) (Config.getWidth()-20)/img.getWidth()) );
							width  = Config.getWidth()-20;
						}else{
							height=img.getHeight();
							width=img.getWidth();
						}
						
						JLabel tempLabel = new JLabel(new ImageIcon(
//								Parsers.recolorImage(img, width, height )
								Parsers.resetTheImage(img, width, height, temp[1])
								));
						
						tempLabel.setBounds(5,lastPosition+10,width,height);
						
						lastPosition+=height+10;
						
						add(tempLabel);
						setHeight(lastPosition);
						
					}
					
				}
				
				if(textToAdd.length()>0){
//					tempHeight=countLines(textToAdd, lines);
					tempHeight=Parsers.countLines2(textToAdd);
//					setHeight(this.height+tempHeight*Config.getFontHeight());
					add(createTextArea(textToAdd,tempHeight));
					setHeight(lastPosition);
				}
				
				reload();
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	
	private JTextArea createTextArea(String text, int height){
		JTextArea outText = new JTextArea();
		int tempHeight = lastPosition;
		outText.setBounds(5,tempHeight,getWidth()-15,height*Config.getFontHeight());
		outText.setEditable(false);
		outText.setLineWrap(true);
		outText.setWrapStyleWord(true);
		outText.setFont(Config.getFont());
		outText.setForeground(Config.getFrontgroundColor());
		outText.setBackground(Config.getBackgroundColor());
		outText.setText(text);
		lastPosition+=outText.getHeight();
		
		return outText;
		
	}
	

	
	/**
	 * removes everything from panel
	 */
	private void resetAll(){
		removeAll();
		repaint();
	}


	public int getContsHeight() {
		return contsHeight;
	}


	public void setContsHeight(int contsHeight) {
		this.contsHeight = contsHeight;
	}
	
}
