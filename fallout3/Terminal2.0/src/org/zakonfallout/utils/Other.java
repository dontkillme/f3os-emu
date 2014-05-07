package org.zakonfallout.utils;

import java.util.Random;

/**
 * Here we have other stuff like evil drago...
 * that didnt feat anywhere.
 * @author emerald
 *
 */
public class Other {

	private static Random rand = new Random();
	/**
	 * returns random int from 0 to max. Uses Random();
	 * @param max - max to be returned
	 * @return random int from 0 to max
	 */
	public static int randomInt(int max){
		if(max==0) return 0;
		return rand.nextInt(max);
	}
	/**
	 * Returns random int from setted range-1;
	 * @param min - minimal number
	 * @param max - maximal number
	 * @return random int in range
	 */
	public static int rangedNextInt(int min, int max){
		if(max==0) return 0;
		return rand.nextInt(Math.abs(max-min))+min;
	}
	
	/**
	 * Toggle boolean value.
	 * @param input actual boolean state
	 * @return changed boolean state
	 */
	public static boolean toggleBoolean(boolean input){
		return input ? false : true;
	}
	
}
