package org.vexillium.easterneggs;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.zakonfallout.Config;
import org.zakonfallout.MainWindow;
import org.zakonfallout.utils.Other;
/**
 * This panel renders text on it and repaints it
 * in 300ms intervals
 * @author emerald
 *
 */
public class FlashPanel extends JPanel {

	private String text="";
	private int w,
				textw,
				h,
				r=1,
				green=1,
				b=1,
				x[] = new int[10],
				y[] = new int[10];
	private boolean started=false;
	
	/**
	 * Panel with flashing text randomly showing on screen.
	 * @param w - width of panel
	 * @param h - height of panel
	 */
	public FlashPanel(int w,int h){
		this.w = w;
		this.h = h;
		setSize(this.w,this.h);
		setLayout(null);
		setBackground(Config.getBackgroundColor());
		r=Config.getFrontgroundColor().getRed();
		green=Config.getFrontgroundColor().getGreen();
		b=Config.getFrontgroundColor().getBlue();

	}
	
	public void paintComponent(Graphics g){
		if(started){
			g.setColor(Color.black);
			g.fillRect(0, 0, w, h);
			g.setFont(Config.getFont());
			for(int a=0; a<8; a++){
				g.setColor(new Color( Other.randomInt(r), Other.randomInt(green), Other.randomInt(b) ));
				g.drawString(text, x[a], y[a]);
			}
		}
	}
	
	/**
	 * Sets text to be displayed
	 * @param _text - text to be displayed
	 */
	private void setText(String _text){
		textw=_text.length()*Config.getFontWidth();
		this.text = _text;
	}
	
	/**
	 * Starts eastern egg.
	 * @param _text - text to be displayed
	 * @return out - returns info about end of flashing
	 */
	public boolean startEgg(String _text){
		setText(_text);
		Thread tempThread = new TimerThread();
		
		tempThread.start();
		return true;
		
	}
	
	
	
	private class TimerThread extends Thread{
		private int counter=25;
		public void run(){
			started=true;
			try {
				while(counter>0){
						if( (counter % 5)==0){
							for(int a=0; a<10; a++){
								x[a]=Other.rangedNextInt(0, w-textw);
								y[a]=Other.rangedNextInt(0, h);
							}
						}
						repaint();
						Thread.sleep(300);
						--counter;
						
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			started=false;
			MainWindow.startPausedActions();
		}
	}
	
}
