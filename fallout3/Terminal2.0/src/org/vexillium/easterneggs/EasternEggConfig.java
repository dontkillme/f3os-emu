package org.vexillium.easterneggs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.zakonfallout.Config;

public class EasternEggConfig {

	
	private static boolean rabarbar = false,
						   flashing = false;
	private static String flashText = "trolololo";
	
	/*
	 * The chars for rabarbar mode (char changer) will be
	 * setted to uppercase in the mode control class so give
	 * only the lowercase. Use only letters.
	 */
	private static char toReplaceChar = 'r',
				 		replacementChar = 'ł';
	
	/*
	 * flashPanel has 300ms intervals between repaints
	 * and 1500ms between relocating. So if we set flashPanelTime to 25
	 * its 300ms*25;
	 */
	private static int flashPanelTime = 25,
					   happensIn=600,
					   rabarbarPossibility=25,
					   flashPanelPossibility=25;
	
	/**
	 * Loads eastern egg settings. Works same as config.
	 * happensIn=600 - max random interval time in seconds after which eastern eggs should happen
	 * flashPanelTime=25 - life of the flashPanel, there are 300ms intervals so in fact its 25*300ms
	 * flashText=sometext - text to be displayed in flashpanel
	 * flashPanelPossibility=25 - the possibility of flashpanel eastern egg happening
	 * rabarbarChars=r/ł - char to be replaced in text by the second one old_char/new_char
	 * rabarbarPossibility=25 - the possibility of rabarbar toggle.
	 * 
	 */ 
	public static void loadEasternEggs(){
		try {
			
			URL inFile = new URL(Config.getRoot()+"\\easternegg.f3s");
			BufferedReader in = new BufferedReader(new InputStreamReader(inFile.openStream(),"UTF-8"));
			String inText, temp[];
			
			while((inText = in.readLine())!=null){
				if(inText.contains("happensIn=")){
					setHappensIn(Integer.parseInt(inText.substring(inText.indexOf("=")+1) ));
				}
				if(inText.contains("rabarbarPossibility=")){
					setRabarbarPossibility(Integer.parseInt(inText.substring(inText.indexOf("=")+1) ));
				}
				if(inText.contains("flashPanelPossibility=")){
					setFlashPanelPossibility(Integer.parseInt(inText.substring(inText.indexOf("=")+1) ));
				}
				if(inText.contains("flashPanelTime=")){
					setFlashPanelTime(Integer.parseInt(inText.substring(inText.indexOf("=")+1) ));
				}
				else if(inText.contains("flashText=")){
					setFlashText(inText.substring(inText.indexOf("=")+1));
				}
				else if(inText.contains("rabarbar=")){
					if(inText.substring(inText.indexOf("=")+1).equalsIgnoreCase("ON")){
						setRabarbar(true);
 					}
				}
				else if(inText.contains("rabarbarChars=")){
					setRabarbars(inText.substring(inText.indexOf("=")+1));
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("cannot find easternEggFile, using default");
		} 
	}

	/**
	 * Sets the rabarbar chars. It goes like this CharToBeReplace/ReplacementChar
	 * @param text - CharToBeReplace/ReplacementChar
	 */
	public static void setRabarbars(String text){
		String temp[] =  text.split("/");
		setToReplaceChar(temp[0].charAt(0));
		setReplacementChar(temp[1].charAt(0));
	}
	
	/**
	 * @return the flashText
	 */
	public static String getFlashText() {
		return flashText;
	}

	/**
	 * @param flashText the flashText to set
	 */
	public static void setFlashText(String flashText) {
		EasternEggConfig.flashText = flashText;
	}

	/**
	 * @return the toReplaceChar
	 */
	public static char getToReplaceChar() {
		return toReplaceChar;
	}

	/**
	 * @param toReplaceChar the toReplaceChar to set
	 */
	public static void setToReplaceChar(char toReplaceChar) {
		EasternEggConfig.toReplaceChar = toReplaceChar;
	}

	/**
	 * @return the replacementChar
	 */
	public static char getReplacementChar() {
		return replacementChar;
	}

	/**
	 * @param replacementChar the replacementChar to set
	 */
	public static void setReplacementChar(char replacementChar) {
		EasternEggConfig.replacementChar = replacementChar;
	}

	/**
	 * @return the flashPanelTime
	 */
	public static int getFlashPanelTime() {
		return flashPanelTime;
	}

	/**
	 * @param flashPanelTime the flashPanelTime to set
	 */
	public static void setFlashPanelTime(int flashPanelTime) {
		EasternEggConfig.flashPanelTime = flashPanelTime;
	}
	


	/**
	 * @return the rabarbar
	 */
	public static boolean isRabarbar() {
		return rabarbar;
	}


	/**
	 * @param rabarbar the rabarbar to set
	 */
	public static void setRabarbar(boolean rabarbar) {
		EasternEggConfig.rabarbar = rabarbar;
	}

	/**
	 * @return the flashing
	 */
	public static boolean isFlashing() {
		return flashing;
	}

	/**
	 * @param flashing the flashing to set
	 */
	public static void setFlashing(boolean flashing) {
		EasternEggConfig.flashing = flashing;
	}

	/**
	 * @return the happensIn
	 */
	public static int getHappensIn() {
		return happensIn;
	}

	/**
	 * @param happensIn the happensIn to set
	 */
	public static void setHappensIn(int happensIn) {
		EasternEggConfig.happensIn = happensIn;
	}

	/**
	 * @return the rabarbarPossibility
	 */
	public static int getRabarbarPossibility() {
		return rabarbarPossibility;
	}

	/**
	 * @param rabarbarPossibility the rabarbarPossibility to set
	 */
	public static void setRabarbarPossibility(int rabarbarPossibility) {
		EasternEggConfig.rabarbarPossibility = rabarbarPossibility;
	}

	/**
	 * @return the flashPanelPossibility
	 */
	public static int getFlashPanelPossibility() {
		return flashPanelPossibility;
	}

	/**
	 * @param flashPanelPossibility the flashPanelPossibility to set
	 */
	public static void setFlashPanelPossibility(int flashPanelPossibility) {
		EasternEggConfig.flashPanelPossibility = flashPanelPossibility;
	}
	
}
