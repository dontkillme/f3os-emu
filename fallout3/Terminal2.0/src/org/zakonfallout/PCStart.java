package org.zakonfallout;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import org.vexillium.easterneggs.EasternEggConfig;


public class PCStart {
 
	private static MainWindow wm;
 
	public static void main(String[] args) {
		
		Path currentRelativePath = Paths.get("");
		String file = currentRelativePath.toAbsolutePath().toString();
		try {
			Config.loadConfig(file);
			if(Config.isEasternEggs()){
				EasternEggConfig.loadEasternEggs();
			}
			mainWindow();
		} catch (IOException e) {
			try {
				Config.loadConfig(pathToConfig());
				mainWindow();
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}
	

	private static String pathToConfig(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setDialogTitle("Select Config file");
		int result = fc.showSaveDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION){
			String a = fc.getSelectedFile().toString();
			return a.substring(0,a.length()-10);
		}else{
			System.exit(0);
		}
		return null;
	}
	
	/**
	 * restarts window
	 */
	public static void restartWindow(){
		wm.dispose();
		wm.removeAll();
		mainWindow();
	}
	
	
	
	private static void mainWindow(){
		EventQueue.invokeLater(new Runnable(){
            public void run(){
              wm = new MainWindow();     
            }
		});
	}

}
