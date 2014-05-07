package org.zakonfallout.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Paintings {

	public static BufferedImage noPicture(Color frontColor){
		BufferedImage img = new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		g.setColor(frontColor);
		g.drawRect(0, 0, 400, 400);
		g.drawRect(1, 1, 398, 398);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD,30));
		g.drawString("NO PICTURE", 100, 180);
		g.drawLine(0, 0, 400, 400);
		g.dispose();
		return img;
	}
	
}
