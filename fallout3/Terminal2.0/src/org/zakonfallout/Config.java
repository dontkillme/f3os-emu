package org.zakonfallout;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


import java.nio.file.Paths;

import org.zakonfallout.objects.ModeList;
import org.zakonfallout.utils.Parsers;

public class Config {
	/*
	 * Main stuff of the terminal,
	 */
	private static String root = "",
				   catalog = "",
				   realRoot="",
				   cdRom = "",
				   usb="",
				   floppy="a",
				   wlcFile = "",
				   hlpFile = "",
				   hackText = "",
				   errorText = "",
				   noFile="",
				   infoText = "",
				   noDir="",
				   noAccess="",
				   failedHack="",
				   wrongPassword="",
				   exitCmd="idewchujztejwiochy",
				   randomChars="!@#$%^&*()_++QWERTYUIOPASDFGHJKLZXCVBNM<>,.;'{}[]+_",
				   adminPass="ZakonMoimDomem";
	/*
	 * Booleans
	 */
	private static boolean fullScreen = false,
							onTop = false,
							easternEggs = false,
							adminMode=false,
							externalOn=false;
	/*
	 * int
	 */
	private static int width = 800,
					   height = 600,
					   fontWidth,
					   fontHeight,
					   wordAppear=68,
					   sectorW=30,
					   sectorH=30;
	/*
	 * colors, fonts, objects etc
	 */
	private static Color backgroundColor = Color.black, 
				  frontgroundColor = Color.green;
	private static Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);
	private static Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
	
	/*
	 * enums
	 */
	
	private static ModeList mode = ModeList.NORMAL;
	/**
	 * Loads config for terminal. Case sensive! The file should have:
	 * root=<Address to files>
	 * Error_msg=<text that will display if there is no such command>
	 * Hlp_url=<name of the help file>
	 * Wlc_url=<name of the welcome file, the first file to show up>
	 * background=<color of the background in r,g,b>
	 * frontground=<text, lines, almost everthing color in r,g,b>
	 * fullScreen=ON - or other text if not fullscreen mode
	 * onTop=ON - or other text if not.
	 * resolution=1024,768 - of the screen
	 * font=name,size - use name of the fonts not the files that contains fonts. U need to have it in system.
	 * noFile=msg that there is no such file
	 * noDir=msg that will be show if there is no such directory - not working
	 * skipPixels=value of pixel rescale. 1 for normal image, 2+ for quadated;
	 * exitCmd=CMD that will be the exit from program cmd
	 * randomChars=set of random chars that will be shown in hackpanel etc.
	 * easternEggs=on - if on, then eastern egg will show up in random time after actions...
	 * mapSectorWH=width,height
	 * wordAppear=whats the precent of word appearing in hacking (wordAppear+x=100%, where x is random chars)
	 * wrongPassword= text to appear when u choosed wrong password 
	 * failedHack= msg appearing after 4 wrong password inputed in hack mode
	 * adminPass= password for admin mode. Default password ZakonMoimDomem
	 * <hack_text>
	 * Text for hacking. Mainly tutorial for what to do to hack. Does not support images... afair :P
	 * <end_text>
	 * <info_text>
	 * Text to be showned in upper panel. Supports image. Add it at the beginning!
	 * <end_text>
	 * @throws IOException, MalformedURLException  
	 */
	protected static void loadConfig(String url) throws IOException, MalformedURLException {
		
//		 try {
            
            
             String temp[];
//             File infile = new File(currentRelativePath.toAbsolutePath().toString());
             File infile = new File(url);
             BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(infile+"\\config.f3s"), "UTF-8"));
             String next =  in.readLine(); 
             
 			while(!(next = in.readLine()).equals("<end_file>")){
 				
 				if(next.contains("root=")){
 					setRoot(next.substring(next.indexOf("=")+1));
 					setRealRoot(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("wordAppear=")){
 					setWordAppear( Integer.parseInt( next.substring(next.indexOf("=")+1) ) );
 				}
 				else if(next.contains("wrongPassword=")){
 					setWrongPassword(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("adminPass=")){
 					setAdminPass(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("Error_msg=")){
 					setErrorText(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("exitCmd=")){
 					setExitCmd(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("noAccess=")){
 					setNoAccess(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("failedHack=")){
 					setFailedHack(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("Hlp_url=")){
 					setHlpFile(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("skipPixels=")){
 					Parsers.setSkipPixels(Integer.parseInt(next.substring(next.indexOf("=")+1)));
 				}
 				else if(next.contains("noFile=")){
 					setNoFile(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("noDir=")){
 					setNoDir(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("background=")){
 					temp = next.substring(next.indexOf("=")+1).split(",");
 					setBackgroundColor(new Color(Integer.parseInt(temp[0]),
 												Integer.parseInt(temp[1]),
 												Integer.parseInt(temp[2])
 												));
 				}
 				
 				else if(next.contains("frontground=")){
 					temp = next.substring(next.indexOf("=")+1).split(",");
 					setFrontgroundColor(new Color(Integer.parseInt(temp[0]),
 												Integer.parseInt(temp[1]),
 												Integer.parseInt(temp[2])
 												));
 				}
 				
 				else if(next.contains("resolution=")){
 					setResolution(next.substring(next.indexOf("=")+1));
 				}else if(next.contains("mapSectorWH=")){
 					temp=next.substring(next.indexOf("=")+1).split(",");
 					setWidth(Integer.parseInt(temp[0]));
 					setHeight(Integer.parseInt(temp[1]));
 				}else if(next.contains("font=")){
 					temp=next.substring(next.indexOf("=")+1).split(",");
 					font = new Font(temp[0], Font.PLAIN, Integer.parseInt(temp[1]));
 					setFontWH();
 				}
 				else if(next.contains("cdRom=")){
 					setCdRom(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("usb=")){
 					setUsb(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("floppy=")){
 					setFloppy(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("randomChars=")){
 					setRandomChars(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("Wlc_url=")){
 					setWlcFile(next.substring(next.indexOf("=")+1));
 				}
 				else if(next.contains("fullScreen=")){
 					if(next.substring(next.indexOf("=")+1).equalsIgnoreCase("ON")){
 						setFullScreen(true);
 					}else{
 						setFullScreen(false);
 					}
 				}else if(next.contains("easternEggs=")){
 					if(next.substring(next.indexOf("=")+1).equalsIgnoreCase("ON")){
 						setEasternEggs(true);
 					}else{
 						setEasternEggs(false);
 					}
 				}
 				else if(next.contains("onTop=")){
 					if(next.substring(next.indexOf("=")+1).equalsIgnoreCase("ON")){
 						setOnTop(true);
 					}else{
 						setOnTop(false);
 					}
 				}
 				else if(next.contains("externalOn=")){
 					if(next.substring(next.indexOf("=")+1).equalsIgnoreCase("ON")){
 						setExternalOn(true);
 					}
 				}
 				else if(next.contains("<hack_text>")){
 					while(!(next = in.readLine()).equalsIgnoreCase("<end_text>")){
 						setHackText(getHackText()+ next +"\n");
 					}
 				}
 				
 				else if(next.contains("<info_text>")){
 					while(!(next = in.readLine()).equalsIgnoreCase("<end_text>")){
 					setInfoText(getInfoText() + next +"\n");
 					}
 					
 				}
 				
 			}//end while
 			in.close();
 			/*
 			 * if there is no noDir msg it sets the no file msg as no dir msg.
 			 */
 			if(getNoDir().equals("")){
 				setNoDir(getNoFile());
 			}
// 		} catch (MalformedURLException e) {
// 			
// 			e.printStackTrace();
// 			System.exit(0);
// 		} catch (IOException e) {
// 		
// 			e.printStackTrace();
// 			System.exit(0);
// 		}// end try
	}

	/**
	 * 
	 * @param next - resolution as string width,height.
	 */
	public static void setResolution(String next){
		String temp[];
		temp=next.split(",");
		setWidth(Integer.parseInt(temp[0]));
		setHeight(Integer.parseInt(temp[1]));
	}
	/**
	 * @return the root
	 */
	public static String getRoot() {
		return root;
	}


	/**
	 * @param root the root to set
	 */
	public static void setRoot(String root) {
		if(root.equalsIgnoreCase("this")){
			String a = Paths.get("").toAbsolutePath().toString();
			
			a = a.replace('\\', '/');
			a = a.replace(':', '|');
			
			Config.root = "file://localhost/"+a.toLowerCase()+"/";
		}
		else Config.root = root;
	}


	/**
	 * @return the catalog
	 */
	public static String getCatalog() {
		return catalog;
	}


	/**
	 * @param catalog the catalog to set
	 */
	public static void setCatalog(String catalog) {
		Config.catalog = catalog;
	}


	/**
	 * @return the cdRom
	 */
	public static String getCdRom() {
		return cdRom;
	}


	/**
	 * @param cdRom the cdRom to set
	 */
	public static void setCdRom(String cdRom) {
		Config.cdRom = cdRom;
	}


	/**
	 * @return the wlcFile
	 */
	public static String getWlcFile() {
		return wlcFile;
	}


	/**
	 * @param wlcFile the wlcFile to set
	 */
	public static void setWlcFile(String wlcFile) {
		Config.wlcFile = wlcFile;
	}


	/**
	 * @return the hlpFile
	 */
	public static String getHlpFile() {
		return hlpFile;
	}


	/**
	 * @param hlpFile the hlpFile to set
	 */
	public static void setHlpFile(String hlpFile) {
		Config.hlpFile = hlpFile;
	}


	/**
	 * @return the hackText
	 */
	public static String getHackText() {
		return hackText;
	}


	/**
	 * @param hackText the hackText to set
	 */
	public static void setHackText(String hackText) {
		Config.hackText = hackText;
	}


	/**
	 * @return the errorText
	 */
	public static String getErrorText() {
		return errorText;
	}


	/**
	 * @param errorText the errorText to set
	 */
	public static void setErrorText(String errorText) {
		Config.errorText = errorText;
	}


	/**
	 * @return the infoText
	 */
	public static String getInfoText() {
		return infoText;
	}


	/**
	 * @param infoText the infoText to set
	 */
	public static void setInfoText(String infoText) {
		Config.infoText = infoText;
	}


	/**
	 * @return the fullScreen
	 */
	public static boolean isFullScreen() {
		return fullScreen;
	}


	/**
	 * @param fullScreen the fullScreen to set
	 */
	public static void setFullScreen(boolean fullScreen) {
		Config.fullScreen = fullScreen;
	}


	/**
	 * @return the onTop
	 */
	public static boolean isOnTop() {
		return onTop;
	}


	/**
	 * @param onTop the onTop to set
	 */
	public static void setOnTop(boolean onTop) {
		Config.onTop = onTop;
	}


	/**
	 * @return the width
	 */
	public static int getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public static void setWidth(int width) {
		Config.width = width;
	}


	/**
	 * @return the height
	 */
	public static int getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public static void setHeight(int height) {
		Config.height = height;
	}


	/**
	 * @return the backgroundColor
	 */
	public static Color getBackgroundColor() {
		return backgroundColor;
	}


	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public static void setBackgroundColor(Color backgroundColor) {
		Config.backgroundColor = backgroundColor;
	}


	/**
	 * @return the frontgroundColor
	 */
	public static Color getFrontgroundColor() {
		return frontgroundColor;
	}


	/**
	 * @param frontgroundColor the frontgroundColor to set
	 */
	public static void setFrontgroundColor(Color frontgroundColor) {
		Config.frontgroundColor = frontgroundColor;
	}


	/**
	 * @return the font
	 */
	public static Font getFont() {
		return font;
	}


	/**
	 * @param font the font to set
	 */
	public static void setFont(Font font) {
		Config.font = font;
	}


	/**
	 * @return the cursor
	 */
	public static Cursor getCursor() {
		return cursor;
	}


	/**
	 * @param cursor the cursor to set
	 */
	public static void setCursor(Cursor cursor) {
		Config.cursor = cursor;
	}

	/**
	 * This method gets the font width and height in pixels and sets it to the primitives
	 */
	private static void setFontWH(){
		Graphics2D g = new BufferedImage(150,150,BufferedImage.TYPE_INT_RGB).createGraphics();
		g.setFont(font);
		setFontWidth(g.getFontMetrics().stringWidth("A"));
		setFontHeight((int) ( Math.ceil((g.getFont().getSize()*(1.0/72))*96.0) )+1);
		g.dispose();
	}


	public static int getFontWidth() {
		return fontWidth;
	}


	public static void setFontWidth(int fontWidth) {
		Config.fontWidth = fontWidth;
	}


	public static int getFontHeight() {
		return fontHeight;
	}


	public	 static void setFontHeight(int fontHeight) {
		Config.fontHeight = fontHeight;
	}


	public static String getNoFile() {
		return noFile;
	}


	public static void setNoFile(String noFile) {
		Config.noFile = noFile;
	}


	/**
	 * @return the noDir
	 */
	public static String getNoDir() {
		return noDir;
	}


	/**
	 * @param noDir the noDir to set
	 */
	public static void setNoDir(String noDir) {
		Config.noDir = noDir;
	}


	/**
	 * @return the noAccess
	 */
	public static String getNoAccess() {
		return noAccess;
	}


	/**
	 * @param noAccess the noAccess to set
	 */
	public static void setNoAccess(String noAccess) {
		Config.noAccess = noAccess;
	}


	/**
	 * @return the failedHack
	 */
	public static String getFailedHack() {
		return failedHack;
	}


	/**
	 * @param failedHack the failedHack to set
	 */
	public static void setFailedHack(String failedHack) {
		Config.failedHack = failedHack;
	}


	/**
	 * @return the exitCmd
	 */
	public static String getExitCmd() {
		return exitCmd;
	}


	/**
	 * @param exitCmd the exitCmd to set
	 */
	public static void setExitCmd(String exitCmd) {
		Config.exitCmd = exitCmd;
	}
	
	/**
	 * Checks if exitcmd from cmdline is the same that in config
	 * @param exitCMD - cmd from 
	 * @return
	 */
	public static boolean isExitCmd(String exitCMD){
		return exitCMD.equals(getExitCmd());
	}


	/**
	 * @return the randomChars
	 */
	public static String getRandomChars() {
		return randomChars;
	}


	/**
	 * @param randomChars the randomChars to set
	 */
	public static void setRandomChars(String randomChars) {
		Config.randomChars = randomChars;
	}


	/**
	 * @return the wordAppear
	 */
	public static int getWordAppear() {
		return wordAppear;
	}


	/**
	 * @param wordAppear the wordAppear to set
	 */
	public static void setWordAppear(int wordAppear) {
		Config.wordAppear = wordAppear;
	}


	/**
	 * @return the wrongPassword
	 */
	public static String getWrongPassword() {
		return wrongPassword;
	}


	/**
	 * @param wrongPassword the wrongPassword to set
	 */
	public static void setWrongPassword(String wrongPassword) {
		Config.wrongPassword = wrongPassword;
	}


	/**
	 * @return the sectorH
	 */
	public static int getSectorH() {
		return sectorH;
	}


	/**
	 * @param sectorH the sectorH to set
	 */
	public static void setSectorH(int sectorH) {
		Config.sectorH = sectorH;
	}


	/**
	 * @return the sectorW
	 */
	public static int getSectorW() {
		return sectorW;
	}


	/**
	 * @param sectorW the sectorW to set
	 */
	public static void setSectorW(int sectorW) {
		Config.sectorW = sectorW;
	}


	/**
	 * @return the easternEggs
	 */
	public static boolean isEasternEggs() {
		return easternEggs;
	}


	/**
	 * @param easternEggs the easternEggs to set
	 */
	public static void setEasternEggs(boolean easternEggs) {
		Config.easternEggs = easternEggs;
	}


	/**
	 * @return the adminPass
	 */
	public static String getAdminPass() {
		return adminPass;
	}


	/**
	 * @param adminPass the adminPass to set
	 */
	public static void setAdminPass(String adminPass) {
		Config.adminPass = adminPass;
	}


	/**
	 * @return the adminMode
	 */
	public static boolean isAdminMode() {
		return adminMode;
	}


	/**
	 * @param adminMode the adminMode to set
	 */
	public static void setAdminMode(boolean adminMode) {
		Config.adminMode = adminMode;
	}

	/**
	 * @return the usb
	 */
	public static String getUsb() {
		return usb;
	}

	/**
	 * @param usb the usb to set
	 */
	public static void setUsb(String usb) {
		Config.usb = usb;
	}

	/**
	 * @return the floppy
	 */
	public static String getFloppy() {
		return floppy;
	}

	/**
	 * @param floppy the floppy to set
	 */
	public static void setFloppy(String floppy) {
		Config.floppy = floppy;
	}

	/**
	 * @return the realRoot
	 */
	public static String getRealRoot() {
		return realRoot;
	}

	/**
	 * @param realRoot the realRoot to set
	 */
	public static void setRealRoot(String realRoot) {
		Config.realRoot = realRoot;
	}

	/**
	 * @return the externalOn
	 */
	public static boolean isExternalOn() {
		return externalOn;
	}

	/**
	 * @param externalOn the externalOn to set
	 */
	public static void setExternalOn(boolean externalOn) {
		Config.externalOn = externalOn;
	}

	/**
	 * @return the mode
	 */
	public static ModeList getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public static void setMode(ModeList mode) {
		Config.mode = mode;
	}

	
}
