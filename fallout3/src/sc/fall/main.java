package sc.fall;

import java.awt.Color;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JApplet;
/**
 * 
 * @author Samlis Coldwind
 * Applet starter for fallout 3 terminal emulator.
 * Sorry for all mistakes and lame documentation, still learning to make them.
 * You can find how to configurate and create different filetypes in how_to_work.txt
 * 
 */
public class main extends JApplet {
private Cursor curs = new Cursor(Cursor.TEXT_CURSOR);
private upinfo up;
private itxtread dw;
private String info_term = "", url="", txt="";

public void init(){
	setLayout(null);
	setBackground(Color.BLACK);
	setSize(800,600);
	
	ReadConfig();
}
public void start(){
	
	up = new upinfo(info_term);
	dw = new itxtread(url, txt, "online");
	
	setCursor(curs);
	
	up.setBounds(0,0,800,100);
	dw.setBounds(0,100,800,500);
	add(up);
	add(dw);
	dw.cmd.requestFocus();

}
/**
 * Metoda odpowiada za wczytanie danych z pliku config.f3s
 * Method reads data from config.f3s
 * This starter is for applets (ohhh... what a suprise :P)
 */
private void ReadConfig(){
	try {
		String url_part = getDocumentBase().toString();

		URL urla = new URL(url_part+"config.f3s"); 
		InputStream infile = urla.openStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(infile));
		String txta=in.readLine();
		
		while( !(txta=in.readLine()).equals("<koniec>") ){
		info_term=info_term+txta.toUpperCase()+"\n";
		}
		if((txta=in.readLine()).substring(0,5).equals("<url:")){
			url = txta.substring(5,txta.length()-1);
		}
		in.readLine();
		while( !(txta=in.readLine()).equals("<koniec>") ){ // koniec - end
			txt=txt+txta.toUpperCase()+"\n";
		}
		in.close();
//		System.out.println(info_term+"\n <===>"+url+"\n <+++>"+txt);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void stop(){
		destroy();
}
}
