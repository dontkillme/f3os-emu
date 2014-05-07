package org.zakonfallout.admin;

import org.vexillium.easterneggs.EasternEggConfig;
import org.zakonfallout.Config;
import org.zakonfallout.utils.Parsers;

public class AdminUtils {

	/**
	 * Returns all config and eastern egg config properties.
	 * @return properties of config and eeconfig.
	 */
	public static String getAllProps(){
		StringBuilder out = new StringBuilder("");
		out.append("Admin propertie returner.\n"+
				   "------------------------\n");
		out.append("Root path> "+Config.getRoot()+"\n"
				+  "Current path> "+Config.getRoot()+"\\"+Config.getCatalog()+"\n" 
				+  "CdRom/USB> "+Config.getCdRom() +"\n" //
				+  "HELP FILE> "+Config.getHlpFile() +"\n"
				+  "Welcome File> "+Config.getWlcFile() +"\n"
				+  "Resolution> "+Config.getWidth()+","+Config.getHeight() +"\n" //
				+  "Background> "+Config.getBackgroundColor() +"\n" //
				+  "Foreground> "+Config.getFrontgroundColor() +"\n" //
				+  "Font> "+Config.getFont().getFamily()+" "+Config.getFont().getSize() +"\n" 
				+  "Pixel skip> "+Parsers.getSkipPixels() +"\n" //
				+  "FullScreen> "+Config.isFullScreen() +"\n" //
				+  "OnTop> "+Config.isOnTop() +"\n" //
				+  "Exit command> "+Config.getExitCmd() +"\n"
				+  "Word/Chars %> "+Config.getWordAppear() +"\n" 
				+  "Wrong Password msg> "+Config.getWrongPassword() +"\n" 
				+  "failed Hack msg> "+Config.getFailedHack() +"\n"
				+  "RandomCharset> "+Config.getRandomChars() +"\n" //
				+  "NoFile msg> "+Config.getNoFile() +"\n" 
				+  "NoDir msg(n/w)> "+Config.getNoDir() +"\n" 
				+  "no cmd msg> "+Config.getErrorText() +"\n" 
				+  "no Access> "+Config.getNoAccess() +"\n"  
				+  "Hack text> "+Config.getHackText() +"\n" 
				+  "info text> "+Config.getInfoText() +"\n" 
				+  "eastern eggs on> "+Config.isEasternEggs() +"\n");
		if(Config.isEasternEggs()){
			out.append("---Eastern Egg Config---\n"
					+  "Time interval(s)> "+EasternEggConfig.getHappensIn() +"\n"
					+  "Flash Panel Time(1/3s)> "+EasternEggConfig.getFlashPanelTime() +"\n"
					+  "Flash panel text> "+EasternEggConfig.getFlashText() +"\n"
					+  "Flash Panel Possibility> "+EasternEggConfig.getFlashPanelPossibility() +"\n"
					+  "Flash Panel current on> "+EasternEggConfig.isFlashing() +"\n"
					+  "Rabarbar chars> "+EasternEggConfig.getToReplaceChar()+"/"+EasternEggConfig.getReplacementChar() +"\n"
					+  "Rabarbar possibility> "+EasternEggConfig.getRabarbarPossibility() +"\n"
					+  "Rabarbar current on> "+EasternEggConfig.isRabarbar() +"\n"
					);
		}
		return out.toString();
	}
	
}
