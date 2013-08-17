package sc.fall;


import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;


public class PC {
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
			new PCFrame();	
				
			}
		});
	}
	
}

