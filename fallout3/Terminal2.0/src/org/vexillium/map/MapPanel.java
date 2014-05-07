package org.vexillium.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import org.zakonfallout.utils.Parsers;

public class MapPanel extends JPanel {

	/*
	 * Image parts
	 */
	private BufferedImage mapImage;
	private boolean point = false;
	private int x=0,
				y=0,
				sectorW=30,
				sectorH=30;
	private Color foreColor;
	private Random rand = new Random();
	
	/**
	 * Main constructor of MapPanel.
	 * At first it creates only panel size Width,1px; But after image loading it resets the 
	 * panel size to Width, image.height after rescale;
	 * @param w - width of panel
	 * @param _foreColor - color of foreground
	 * @param _sectorW - width of the sector
	 * 
	 */
	public MapPanel(int w, Color _foreColor, Color _background, int _sectorW, int _sectorH){
		foreColor = _foreColor;
		setSectorH(_sectorH);
		setSectorW(_sectorW);
		setSize(w,500);
		setLayout(null);
//		setBackground(Color.red);
		setBackground(_background);
	}
	
	
	/**
	 * Sets the map image and sets the panel height.
	 * The image is rescaled
	 * @param img
	 */
	public void setImage(BufferedImage img){
		int height = 0,
			width = 0;
		System.out.println(img.getWidth()+" "+img.getHeight());
		if(img.getWidth()>getWidth()){
			height = (int) Math.floor(img.getHeight() * ((double) (getWidth())/img.getWidth()) );
			width  = getWidth();
		}else{
			height=img.getHeight();
			width=img.getWidth();
		}
		
		mapImage = Parsers.resetTheImage(img, width, height, "map");
		System.out.println(mapImage.getWidth()+" "+mapImage.getHeight());
		resize(getWidth(),mapImage.getHeight());
		repaint();
	}
	
	/**
	 * Shows random point on map, for treasure, raiders, girls, wodka, spirytus.... :D
	 */
	public void randomPoint(){
		setPoint(rand.nextInt(mapImage.getWidth())+","+rand.nextInt(mapImage.getHeight()));
	}
	
	/**
	 * Sets the x,y position of sector to be selected
	 * then repaints the whole stuff so the point is painted on map
	 * @param xy - x,y
	 */
	public void setPoint(String xy){
		String temp[] = xy.split(",");
		
		x = Integer.parseInt(temp[0]);
		y = Integer.parseInt(temp[1]);
		
		if(x-(sectorW/2)<=0) x=(sectorW/2);
		else if(x+sectorW>mapImage.getWidth()) x=mapImage.getWidth()-(sectorW/2)-1;
	
		
		if(y-(sectorH/2)<=0) y=(sectorH/2);
		else if(y+sectorH>mapImage.getHeight()) y=mapImage.getHeight()-(sectorH/2)-1;
		
		point=true;
		repaint();
	}
	
	/**
	 * Paints map and stuff
	 */
	public void paintComponent(Graphics g){
//		if(mapImage!=null){
			g.drawImage(mapImage,0,0,null);
//		}
		
		if(isPoint()){
			g.setColor(foreColor);
			g.drawRect(x-(sectorW/2), y-(sectorH/2), sectorW, sectorH);
			g.drawRect(x+1-(sectorW/2), y+1-(sectorH/2), sectorW-2, sectorH-2);
		}
		
	}

	/*
	 * Getters and setters
	 */

	/**
	 * @return the sectorH
	 */
	public int getSectorH() {
		return sectorH;
	}


	/**
	 * @param sectorH the sectorH to set
	 */
	public void setSectorH(int sectorH) {
		this.sectorH = sectorH;
	}


	/**
	 * @return the sectorW
	 */
	public int getSectorW() {
		return sectorW;
	}


	/**
	 * @param sectorW the sectorW to set
	 */
	public void setSectorW(int sectorW) {
		this.sectorW = sectorW;
	}


	/**
	 * @return the point
	 */
	public boolean isPoint() {
		return point;
	}


	/**
	 * @param point the point to set
	 */
	public void setPoint(boolean point) {
		this.point = point;
	}
	
}
