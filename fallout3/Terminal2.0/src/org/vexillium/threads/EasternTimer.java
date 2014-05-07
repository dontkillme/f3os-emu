package org.vexillium.threads;

import org.vexillium.easterneggs.EasternEggConfig;
import org.zakonfallout.utils.Other;

/**
 * Timer for eastern egg.
 * First it cound down
 * @author emerald
 *
 */
public class EasternTimer extends Thread {

	private int time = 0;
	
	public void run(){
		time = Other.randomInt(EasternEggConfig.getHappensIn());
		System.out.println(time);
		while(true){
			if(time<=0){
				time = Other.randomInt(EasternEggConfig.getHappensIn());
				randomEasternEgg();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			time--;
			
		}
	}
	
	
	/**
	 * Toggles between on and off of a eastern egg.
	 */
	private void randomEasternEgg(){
		
		int rMode = EasternEggConfig.getRabarbarPossibility(),
			fMode = EasternEggConfig.getFlashPanelPossibility(),
			randomNO = Other.randomInt(100);
		//first is rMode
		if(randomNO<rMode){
			EasternEggConfig.setRabarbar( Other.toggleBoolean(EasternEggConfig.isRabarbar()) );
		}
		//flashing mode is second
		else if(randomNO>rMode && randomNO<(rMode+fMode)){ 
			EasternEggConfig.setFlashing( Other.toggleBoolean(EasternEggConfig.isFlashing()) );
		}
		
	}
	
}
