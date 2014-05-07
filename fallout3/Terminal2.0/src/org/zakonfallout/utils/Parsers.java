package org.zakonfallout.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.zakonfallout.Config;

/*
 * Name of this class is a little
 */
public class Parsers {

	private static int skipPixels=1;
	
	/**
	 * Checks if image should be recolored and resized or only resized.
	 * @param img - image
	 * @param w - width to set
	 * @param h - height to set
	 * @param name - file name
	 * @return
	 */
	public static BufferedImage resetTheImage(BufferedImage img, int w, int h, String name){
		if(name.contains("dnrc") || name.contains("DNRC")){
			if(w!=0 && h!=0){
				return resizeImage(img, w, h);
			}else if(img.getWidth()>Config.getWidth()-20){
				return resizeImage(img, Config.getWidth()-20, Config.getHeight());
			}
		}else{
			return recolorImage(img, w, h);
		
		}
		return null;
	}
	/**
	 * This method recolors and resizes image. Recoloring is done using math:
	 * r*(frontground_Red/255)...
	 */
	public static BufferedImage recolorImage(BufferedImage img, int w, int h){
		int kolor,red,green,blue;
		BufferedImage out;
		BufferedImage resized = img;
		
		if(w!=0 && h!=0){
			out = new BufferedImage(w, h, img.getType());
		}else{
			out = new BufferedImage(Config.getWidth()-20, Config.getHeight(), img.getType());
		}
		
		/*
		 * Check if the image is too width, else we ignore it.
		 */
		if(w!=0 && h!=0){
			resized = resizeImage(img, w, h);
		}else if(img.getWidth()>Config.getWidth()-20){
				resized = resizeImage(img, Config.getWidth()-20, Config.getHeight());
		}
		
		Graphics2D g = out.createGraphics();

		
		for(int x=0; x<resized.getWidth(); x+=getSkipPixels()){
			for(int y=0; y<resized.getHeight(); y+=getSkipPixels()){
				 kolor = resized.getRGB(x, y);
		         red   = (kolor & 0x00ff0000) >> 16;
		         green = (kolor & 0x0000ff00) >> 8;
		         blue  =  kolor & 0x000000ff;
		         g.setColor(transformColor(red, green, blue));
		         g.fillRect(x, y, getSkipPixels(), getSkipPixels());
			}
		}
		g.dispose();
		
		return out;
	}
	
	/**
	 * Resizes image to width equal to console width-20 and image height
	 * @param img - image to resize
	 * @return resized image
	 */
	public static BufferedImage resizeImage(BufferedImage img){
		BufferedImage out = new BufferedImage(Config.getWidth()-20, Config.getHeight(), img.getType());
		Graphics2D g = out.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, Config.getWidth()-20, img.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
		g.dispose();
		
		return out;
	}
	
	/**
	 * Resizes image to the setted by user width and height
	 * @param img - image to resize
	 * @param w - new width
	 * @param h - new height
	 * @return resize image
	 */
	public static BufferedImage resizeImage(BufferedImage img, int w, int h){
		BufferedImage out = new BufferedImage(w, h, img.getType());
		Graphics2D g = out.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(), null);
		g.dispose();
		
		return out;
	}
	
	/**
	 * Recalculates the color of pixel to our convetion. For example we want
	 * green console, so image needs to be in green scale, so this does the trick
	 * 
	 * @param r - red channel
	 * @param g - green channel
	 * @param b - blue channel
	 * @return new color
	 */
	private static Color transformColor(int r, int g, int b){
		int r1,g1,b1;
		double[] a = calcDiff(Config.getFrontgroundColor().getRed(),
								Config.getFrontgroundColor().getGreen(),
								Config.getFrontgroundColor().getBlue());
		
		r1=(int) Math.ceil(r*a[0]);
		g1=(int) Math.ceil(g*a[1]);
		b1=(int) Math.ceil(b*a[2]);
		
		return new Color(r1,g1,b1);
	}
	
	/**
	 * Calculates the transformation % for each channel.
	 * r/255 where r is red channel (0 - 1 range in double)
	 * @param r - red channel
	 * @param g - green channel
	 * @param b - blue channel
	 * @return array of % for each color channel transformation 
	 */
	private static double[] calcDiff(int r, int g,int b){
		double[] calc = new double[3];
		calc[0]=(double) r/255;
		calc[1]=(double) g/255;
		calc[2]=(double) b/255;
		return calc;
	}
	
	/**
	 * Checks text line for <img></img>
	 * @param text - line of text
	 * @return array where index 0 contains plain text, index 1 image name, 2 text after </img> if img was in middle of the text;
	 */
	public static String[] filterText(String text){
		String textOut[] = new String[3], temp;
		
		if(text.contains("<img>")){
			/*
			 * if img isnt first word
			 */
			if(text.indexOf("<img>")>1){
				textOut[0]=removeSpace(""+text.substring(0,text.indexOf("<img>")));
				/*
				 * removes space before <img> if there is one
				 */
				
				/*
				 * creates temp string and checks if there is a space, if not it adds it, else ignore
				 */
				textOut[2]=text.substring(text.indexOf("</img>")+6);
				if(textOut[2].length()>0) textOut[2]=removeSpace(textOut[2]);
					
				
				
				textOut[1]=text.substring(text.indexOf("<img>")+5, text.indexOf("</img>"));
			}
			/*
			 * If the line contains only image
			 */
			else if(text.indexOf("</img>")+6==text.length()){
				textOut[0]="";
				textOut[1]=text.substring(text.indexOf("<img>")+5, text.indexOf("</img>"));
			}
			/*
			 * Runs if img is first :P
			 */
			else{
				textOut[1]=text.substring(text.indexOf("<img>")+5, text.indexOf("</img>"));
				textOut[0]=text.substring(text.indexOf("</img>")+6);
				textOut[0]=removeSpace(textOut[0]);
			
				
			}
			

		}else{
			textOut[0]=text;
		}
		
		return textOut;
	}
	
	/**
	 * Removes space at end and beginning of the text.
	 * @param text - text that will have removed space
	 */
	public static String removeSpace(String text){
		String temp=text;
		if(text.charAt(text.length()-1) == ' '){
			temp = text.substring(0,text.length()-1);
		}
		
		if(text.charAt(0) == ' '){
			temp = temp.substring(1,temp.length());
		}
		return temp;
		
	}
	
	/**
	 * this method transforms url from / to \\
	 * @param text - root url + catalogs
	 * @return transformed url
	 */
	
	public static String rootToFileConv(String text){
		   StringBuilder temp = new StringBuilder(text);
		   temp.setCharAt(18, ':');
		   String tempA = temp.toString().substring(17);
		   return tempA.replace("/", "\\");
	   }
	
	
	/**
	 * Counts how many lines (one line = Width/fontWidth chars) is in panel and adds new lines
	 * @param text
	 * @return
	 */
	public static int countLines(String text, int lines){	

		return (int) ( Math.ceil((double) text.length()/ ( (Config.getWidth()-20)/Config.getFontWidth() ))) + lines;
		
	}
	
	public static int countLines2(String text){
		BufferedReader tempReader = new BufferedReader( new StringReader(text));
		String temp;
		int lines = 0,
			tempDouble = 0;
		try {
			while( (temp = tempReader.readLine()) != null){
				tempDouble = countLines(temp,0);
				if((tempDouble>1 && temp.contains("\n")) || temp.equals("")){
					lines+=tempDouble+1;
				}else{
					lines+=tempDouble;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	/**
	 * @return the skipPixels
	 */
	public static int getSkipPixels() {
		return skipPixels;
	}
	/**
	 * @param skipPixels the skipPixels to set
	 */
	public static void setSkipPixels(int skipPixels) {
		Parsers.skipPixels = skipPixels;
	}
	
}
