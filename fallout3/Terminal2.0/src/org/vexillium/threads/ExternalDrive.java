package org.vexillium.threads;

import java.io.File;
import org.zakonfallout.Config;
import org.zakonfallout.MainWindow;
import org.zakonfallout.objects.ModeList;

public class ExternalDrive extends Thread{
	private boolean runIt = true,
					onDrive = false;
	private String copyOfDrive="",
				   copyOfCatalog="",
				   drivers[],
				   actualRoot="";
	private File driver;
	
	public ExternalDrive() {
		copyOfDrive = Config.getRoot();
		copyOfCatalog = Config.getCatalog();
		actualRoot = copyOfDrive;
		drivers = new String[]{
				"file://localhost/"+Config.getUsb().charAt(0)+"|/",
				"file://localhost/"+Config.getCdRom().charAt(0)+"|/",
				"file://localhost/"+Config.getFloppy().charAt(0)+"|/"
		};
		
		setRunIt(true);
	}
	
	public void run(){
		System.out.println(actualRoot.charAt(17));
		while(runIt){
			try{
				for(String temp : drivers)
				{	
					if(!onDrive){
						changeDisc(temp);	
					}else{
						if(!doesItExist(actualRoot.charAt(17)+":\\")){
							changeDisc(temp);
						}
					}
				}
				Thread.sleep(20000);	
			}catch(InterruptedException e){}
		}
	}
	
	public void changeDisc(String adres){
		if(Config.getMode()==ModeList.NORMAL){
			if(doesItExist(adres.charAt(17)+":\\")){
				actualRoot = adres;
				runChanges(adres, "");
				onDrive = true;
			}else{
				if(copyOfDrive.charAt(17)!=actualRoot.charAt(17)){
					actualRoot = copyOfDrive;
					runChanges(actualRoot, copyOfCatalog);
				}
				
				onDrive = false;
			}
		}
	}
	
	private void runChanges(String adres, String catalog){
		Config.setRoot(adres);
		Config.setCatalog(catalog);
		MainWindow.staticOpenFile("DirFile");
		
	}
	
	private boolean doesItExist(String adres){
		driver = new File(adres);
		if(driver.exists()){
			if(new File(adres+"DirFile.f3s").exists()){
				return true;	
			}
		}
		return false;
	}
	
	/**
	 * @return the runIt
	 */
	public boolean isRunIt() {
		return runIt;
	}

	/**
	 * @param runIt the runIt to set
	 */
	public void setRunIt(boolean runIt) {
		this.runIt = runIt;
	}
	
}
