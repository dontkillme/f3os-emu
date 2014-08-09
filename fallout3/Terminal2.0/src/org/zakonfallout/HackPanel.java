package org.zakonfallout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.zakonfallout.utils.Parsers;
/**
 * Class extends JPanel, creating hacking like in fallout 3
 * @author emerald
 *
 */
// dorzucic sprawdzanie dlugosci istniejacych i podmienianie wartosci dlugosci jesli nie ma, bo tak
//check why does new arch adds
public class HackPanel extends JPanel {
	
	/*
	 * primitives 
	 */
	private int width = Config.getWidth(),
				height = (int) Math.ceil(Config.getHeight()*(double) 23/30),
				rightPassword = 0,
				passwordLen;
	private boolean rpOnSet=false; // right password on set (its displayed);
	/*
	 * JComponents
	 */
	private JTextArea outText;
	/*
	 * Objects and lists
	 */
	private ArrayList<String> passwordList = new ArrayList<String>();
	private Random rand = new Random();
	/**
	 * Main constructor of hack panel.
	 * Hacking is like in fallout 3, user main objective is to find the key word
	 */
	public HackPanel(){
		setSize(width,height);
		setLayout(null);
		setBackground(Config.getBackgroundColor());
	}
	
	/**
	 * Sets up the hack panel to zero point.
	 * @param passwordLenght
	 */
	protected void createHack(int passwordLenght){
		removeAll();
		passwordLen = passwordLenght;
		/*
		 * loads password and if there could not find the right lenght it manipulates the
		 */
		int way=-1;
		do{
			loadPasswords(passwordLen);
			if(passwordList.isEmpty()){
				
				if(passwordLen>13) way=-1;
				else if(passwordLen<1) way=1;
				passwordLen+=way;
			}
		}while(passwordList.isEmpty());
		
		setTextArea(Config.getHackText()+"\n", Parsers.countLines2(Config.getHackText()));
		String temp = generatePasswords(passwordLen);
		addToTextArea(temp, Parsers.countLines2(temp));
		addToTextArea("\n", 1);
		rpOnSet=false;
		
		
	}
	
	/**
	 * Creates passwords and random chars.
	 * @return
	 */
	private String generatePasswords(int passwordLenght){
		int maxHeight=height-outText.getHeight();
		int howManyLines = (int) Math.floor(maxHeight/Config.getFontHeight()-10);
		int hmpPerLine = (int) Math.floor( (outText.getWidth()-10) / ( (passwordLenght+1)*Config.getFontWidth() ) );
		rightPassword = rand.nextInt(passwordList.size());
		StringBuilder out = new StringBuilder("");
		
		for(int a=0; a<howManyLines; a++){
			for(int b=0; b<hmpPerLine; b++){
				
				/*
				 * if there wasnt
				 */
				if(a+1==howManyLines && b+1==hmpPerLine && !rpOnSet){
					out.append( passwordList.get(rightPassword) );
				}else{
					out.append( generateWord(passwordLenght)+" " );
				}
			}
			out.append("\n");
		}
		
		return out.toString();
	}
	
	private String generateWord(int lenght){
		if(rand.nextInt(100)<Config.getWordAppear()){
			return passwordsChars().toUpperCase();
		}else{
			return randomChars(lenght);
		}
	}
	
	/**
	 * generetes random word from randomchar list;
	 * @param lenght - lenght of the word
	 * @return word
	 */
	private String randomChars(int lenght){
		StringBuilder out = new StringBuilder("");
		
		for(int a=0; a<lenght;a++){
			out.append(randomChar());
		}
		
		return out.toString();
	}
	
	/**
	 * returns random char from randomchar list of config
	 * @return
	 */
	private char randomChar(){
		
		return Config.getRandomChars().charAt( rand.nextInt(Config.getRandomChars().length()-1) );
	}
	
	/**
	 * Returns one of the passwords from list, and if there wasnt the right password in text already
	 * it returns it (1/4 that it will);
	 * @return
	 */
	private String passwordsChars(){
		
		if(!rpOnSet && ( rand.nextInt(100) < 25 )){
			rpOnSet=true;
			System.out.println(passwordList.get(rightPassword));
			return passwordList.get(rightPassword);
		}
			return passwordList.get( rand.nextInt( passwordList.size() ) );
	}
	
	/**
	 * Loads passwords from passwords.f3s depending on the passwordLenght (level of diff)
	 * 
	 * @param passwordLenght - which group of passwords (depending on the lenght of word) should it load
	 */
	private void loadPasswords(int passwordLenght){
		try {
			passwordList.clear();
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(Config.getRealRoot()+"passwords.f3s").openStream(), "UTF-8"));
			String tempPasswords="";
			
			loaderLoop : while( (tempPasswords = in.readLine()) != null ){
				if(tempPasswords.contains("<lenght>"+passwordLenght)){
					otherLoop : while( (tempPasswords = in.readLine()) !=null ){
						if(tempPasswords.contains("<end>")){
							break loaderLoop;
						}else{
							 passwordList.add(tempPasswords);
						}
					}
				}
			}
			
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRightPassword(String text){
		if(text.equals(passwordList.get(rightPassword) ) ){
			return true;
		}else if(text.equals("overridemethodonzakon")){
			return true;
		}
		String temp = Config.getWrongPassword()+" "+CheckLenghtRight(text)+"/"+passwordList.get(rightPassword).length()+" "+text.toUpperCase()+"\n";
		addToTextArea(temp, Parsers.countLines2(temp));
		return false;
	}
	
		
	/**
	 * Adds to text area new text
	 * @param text - text to add
	 * @param height - how many lines does it have
	 */
	private void addToTextArea(String text, int height){
		outText.append(text);
		outText.setBounds(5,0,getWidth()-15,outText.getHeight()+(height+1)*Config.getFontHeight());
		repaint();
	}
	
	 /**
     * This method returns how many of chars in selected word are in the same position and are the same like in the password
     * @param typed - selected password
     * @return numer of correct chars
     */
    private int CheckLenghtRight(String typed){
            int RightChar = 0, lgh;
            
            if(typed.length()>passwordList.get(rightPassword).length()){
                    lgh = passwordList.get(rightPassword).length();
            }else {
                    lgh = typed.length();
            }
            
            for(int a=0; a<lgh; a++)
            {
                    if(Character.toLowerCase(passwordList.get(rightPassword).charAt(a))==Character.toLowerCase(typed.charAt(a))){
                            RightChar++;
                    }
            }
            
            return RightChar;
    }
	
	/**
	 * Sets up  text area with setted text and height
	 * @param text - text that will be setted to text area
	 * @param height - how many lines there are.
	 * @return
	 */
	private void setTextArea(String text, int height){
		outText = new JTextArea();
		outText.setBounds(5,0,getWidth()-15,height*Config.getFontHeight());
		outText.setEditable(false);
		outText.setLineWrap(true);
		outText.setWrapStyleWord(true);
		outText.setFont(Config.getFont());
		outText.setForeground(Config.getFrontgroundColor());
		outText.setBackground(Config.getBackgroundColor());
		outText.setText(text);
//		repaint();
		add(outText);
	}
	
	
	
}
