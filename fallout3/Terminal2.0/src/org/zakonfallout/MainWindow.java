package org.zakonfallout;


import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.Caret;

import org.vexillium.easterneggs.EasternEggConfig;
import org.vexillium.easterneggs.FlashPanel;
import org.vexillium.map.MapPanel;
import org.vexillium.threads.EasternTimer;
import org.vexillium.threads.ExternalDrive;
import org.zakonfallout.admin.AdminPanel;
import org.zakonfallout.admin.AdminUtils;
import org.zakonfallout.objects.ModeList;
import org.zakonfallout.utils.Parsers;

public class MainWindow extends JFrame implements KeyListener {
	/*
	 * Primitives
	 */
	private int middleHeight = (int) Math.ceil(Config.getHeight()*(double) 23/30),
				 chances = 4;
	private boolean hacked = false,
			byPass=false,
			goAdmin = false;
	/*
	 * JComponents
	 */
	/*
	 * User Made panels. Add here your panel.
	 */
	private UpperPanel upperPanel = new UpperPanel();
	private MiddlePanel middlePanel = new MiddlePanel();
	private AdminPanel adminPanel = new AdminPanel(Config.getWidth(),middlePanel.getContsHeight());
	private HackPanel hackPanel = new HackPanel();
	private MapPanel mapPanel = new MapPanel(Config.getWidth(), Config.getFrontgroundColor(),Config.getBackgroundColor(), Config.getSectorW(), Config.getSectorH());
	private FlashPanel flashPanelEgg = new FlashPanel(Config.getWidth(),middlePanel.getContsHeight());
	//java defined
	private static JScrollPane middleScrollPane;
	private JScrollBar scrollBar;
	private JTextField cmdChar = new JTextField(">"),
					   cmdLine = new JTextField("");
	//dont know how to make it other way, so for now its like this, but feel free to remake this
	private static MainWindow mainWindow;
	/*
	 * Other stuff
	 */
	private EasternTimer easternTimer;
	private static BufferedReader tempIn;
	private ArrayList<StringBuilder> passwordList = new ArrayList<StringBuilder>();
	private Caret caret;
	private String fileInMemory;
	private ExternalDrive extDrive = new ExternalDrive();
	/**
	 * Main Construction for window, loads stuff from config, burns heretics etc :P
	 */
	public MainWindow() {
		mainWindow = this;
		if(Config.isExternalOn()){
			extDrive.start();
		}
		reSetPanel();
	}
	
	/**
	 * Scrolls to the top
	 */
	private void scrollToTop() {
		setScrollHeight(-scrollBar.getValue());
	}
	
	/**
	 * Sets the position of scrollbar of the middle panel
	 * @param sh - what position should it be, 0 for top.
	 */
	private	void setScrollHeight(final int sh) {
		scrollBar = middleScrollPane.getVerticalScrollBar();
		java.awt.EventQueue.invokeLater(new Runnable() { public void run() {
			scrollBar.setValue(scrollBar.getValue()+sh);
		}});
		
		repaint();
		validate();
	}
	
	/**
	 * Sets up cmd line and cmd char
	 */
	private void setUPCmdStuff() {
		/*
		 * part for cmd line
		 */
		cmdLine.setBackground(Config.getBackgroundColor());
		cmdLine.setForeground(Config.getFrontgroundColor());
		cmdLine.setFont(Config.getFont());
		caret = cmdLine.getCaret();
		caret.setBlinkRate(1000);
		cmdLine.setCaretColor(Config.getFrontgroundColor());
		cmdLine.setCaret(caret);
		cmdLine.setBorder(BorderFactory.createEmptyBorder());
		
		/*
		 * part for cmd char
		 */
		cmdChar.setBackground(Config.getBackgroundColor());
		cmdChar.setForeground(Config.getFrontgroundColor());
		cmdChar.setFont(Config.getFont());
		cmdChar.setEditable(false);
		cmdChar.setBorder(BorderFactory.createEmptyBorder());
		
		/*
		 * bounds of cmd char and line
		 */
		cmdLine.setBounds(5+(Config.getFontWidth()*2), middleHeight+upperPanel.getHeight()+2, Config.getWidth()-(5+(Config.getFontWidth()*2)), Config.getFontHeight());
		cmdChar.setBounds(5,middleHeight+upperPanel.getHeight()+2,(Config.getFontWidth()*2), Config.getFontHeight());
		
		/*
		 * add listeners and to the main container
		 */
		cmdLine.addKeyListener(this);
		cmdLine.requestFocusInWindow();
		add(cmdLine);
		add(cmdChar);
	}
	
	/**
	 * Sets the cmd line visible and not
	 * @param asdf
	 */
	private void visibleCmdLine(boolean asdf){
		cmdLine.setEditable(asdf);
		cmdLine.setVisible(asdf);
		cmdChar.setVisible(asdf);
	}
	
	/**
	 * Sets up the scrollpane parameters
	 */
	private void setUpScrollPane() {

		setMiddleHeight();
		
		middlePanel.setPreferredSize(new Dimension(middlePanel.getWidth(), middlePanel.getHeight()));
		middleScrollPane.setBorder(BorderFactory.createEmptyBorder());
		middleScrollPane.setBounds(0,upperPanel.getHeight(),Config.getWidth()+20, middleHeight);
		scrollBar = middleScrollPane.getVerticalScrollBar();

		
		java.awt.EventQueue.invokeLater(new Runnable() { public void run() {
		    scrollBar.setValue(scrollBar.getMinimum());
		}});
	}

	/**
	 * returns to basic mode
	 */
	private void returnToMain(){
		middleScrollPane.setViewportView(middlePanel);
		resetPosition();
	}
	
	/**
	 * This method is for other panels (mainly with threads) to return to main view;
	 */
	public static void returnFromPanel(){
		mainWindow.returnToMain();
	}
	
	/*
	 * REWRITE THIS!!!
	 */
	/**
	 * Resets the panel settings or if there were no, he sets them.
	 */
	public static void reSetPanel(){

		if(mainWindow.isVisible()){
			MainWindow.returnFromPanel();
			mainWindow.dispose();
		}
		
		if(Config.isEasternEggs()){
			mainWindow.easternTimer = new EasternTimer();
			mainWindow.easternTimer.start();
		}
		
		middleScrollPane = new JScrollPane( mainWindow.middlePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		
		
		mainWindow.addWindowListener(new WindowAdapter() {
		    public void windowOpened( WindowEvent e ) {
		    	mainWindow.cmdLine.requestFocus();
		    }
		    public void windowClosed( WindowEvent e) {
		    	//nothing
		    }
		}); 
		
		mainWindow.getContentPane().setBackground(Config.getBackgroundColor());

		mainWindow.resize(Config.getWidth(), Config.getHeight());
		mainWindow.setLayout(null);	// somehow i prefer it, if u dont like it just edit... HF :D
		
		/*
		 * The part where fullscreen takes control of the world...
		 */
		if (Config.isFullScreen()) {	
			if (Config.isOnTop()) {
				mainWindow.setAlwaysOnTop(true);
			}else{ // this is needed if we use the admin panel to change stuff
				mainWindow.setAlwaysOnTop(false);
			}
			
			mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			mainWindow.setUndecorated(true);
		}else{
			mainWindow.setUndecorated(false);
			mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		}
		
		mainWindow.setCursor(Config.getCursor());
		mainWindow.setUPCmdStuff();
		
		mainWindow.setUpScrollPane();
		mainWindow.upperPanel.setBounds(0,0,mainWindow.upperPanel.getWidth(),mainWindow.upperPanel.getHeight());
		mainWindow.upperPanel.SetUp();
		mainWindow.add(mainWindow.upperPanel);
		mainWindow.add(middleScrollPane);

		mainWindow.setVisible(true);
//		System.out.println(Config.getRoot()+Config.getWlcFile());
		mainWindow.openFile(Config.getWlcFile());
	}
	
	public static void startPausedActions(){
		try {
			returnFromPanel();
			mainWindow.showToScreen(mainWindow.getTextFromFile(tempIn).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sets hackpanel in middle
	 */
	private void setHackPanel(int passwordLenght){
		hackPanel.setPreferredSize(new Dimension(hackPanel.getWidth(),hackPanel.getHeight()));
		hackPanel.createHack(passwordLenght);
		middleScrollPane.setViewportView(hackPanel);
		middleScrollPane.setBounds(0,upperPanel.getHeight(),Config.getWidth()+20, hackPanel.getHeight());
		setCMDLineHeight();
		middleScrollPane.revalidate();
		middleScrollPane.repaint();
	}
	
	/**
	 * Sets up admin panel
	 */
	private void setAdminPanel(){
		adminPanel.setPreferredSize(new Dimension(adminPanel.getWidth(),adminPanel.getHeight()));
		middleScrollPane.setViewportView(adminPanel);
		middleScrollPane.setBounds(0,upperPanel.getHeight(),Config.getWidth()+20, adminPanel.getHeight());
		
		setCMDLineHeight();
		visibleCmdLine(false);
		middleScrollPane.revalidate();
		middleScrollPane.repaint();
	}
	
	public static void valiRepaintEtc(){
		mainWindow.repaint();
		mainWindow.validate();
	}
	
	/**
	 * Sets up MapPanel in middle
	 * @param filename - file name of image to be the map
	 */
	private void setMapPanel(String filename){
		try {
			mapPanel.setPoint(false);
			BufferedImage temp = ImageIO.read(new URL(Config.getRoot()+Config.getCatalog()+"/"+filename));
			mapPanel.setImage(temp);
			mapPanel.setPreferredSize(new Dimension(mapPanel.getWidth(),mapPanel.getHeight()));
			middleScrollPane.setViewportView(mapPanel);
			middleScrollPane.setBounds(0,upperPanel.getHeight(),Config.getWidth()+20, middlePanel.getContsHeight());
			setCMDLineHeight();
			middleScrollPane.revalidate();
			middleScrollPane.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets up flashPanel in middle. Text for flashPanel is
	 * in eggstuff.f3s. By default (if there is no eggstuff.f3s or there is no position for
	 * flashpanel) it sets to "trololololo". Also there is a way to set text for flashpanel
	 * inside the Panel (check adminPanel).
	 */
	private void setFlashPanel(){
		flashPanelEgg.setPreferredSize(new Dimension(flashPanelEgg.getWidth(),flashPanelEgg.getHeight()));
		middleScrollPane.setViewportView(flashPanelEgg);
		middleScrollPane.setBounds(0,upperPanel.getHeight(),Config.getWidth()+20, middlePanel.getContsHeight());
		setCMDLineHeight();
		middleScrollPane.revalidate();
		middleScrollPane.repaint();
		
		cmdLine.setEditable(false);
		cmdLine.setVisible(false);
		cmdChar.setVisible(false);
		
		flashPanelEgg.startEgg(EasternEggConfig.getFlashText());
		

	}
	
	/**
	 * Sets the middle panel height
	 */
	private void setMiddleHeight(){
		/*
		 * if middlepanel height is smaller then 
		 */
		if(middlePanel.getHeight()>middlePanel.getContsHeight())
			middleHeight = middlePanel.getContsHeight();
		else
			middleHeight = middlePanel.getHeight();
	}
	
	/**
	 * Sets the cmdline position on Y-asix. Also if cmdline is not editable (for example 
	 * because flashpanel is was on) it resets it to editable and visible. At the end it
	 * requests focus, cause it could lose it. 
	 */
	private void setCMDLineHeight(){
		if(!cmdLine.isEditable()){
			cmdLine.setEditable(true);
			cmdLine.setVisible(true);
			cmdChar.setVisible(true);
		}
		
		cmdLine.setBounds(5 + (Config.getFontWidth() * 2), middleScrollPane.getHeight() + upperPanel.getHeight() + 2, Config.getWidth()-(5+(Config.getFontWidth()*2)), Config.getFontHeight());
		cmdChar.setBounds(5,middleScrollPane.getHeight()+upperPanel.getHeight()+2,(Config.getFontWidth()*2), Config.getFontHeight());
		cmdLine.requestFocus();
	}
	
	/**
	 * Resets the position of panels in frame to begining position.
	 */
	private void resetPosition(){
		
		setMiddleHeight();

		middlePanel.setPreferredSize(new Dimension(middlePanel.getWidth(), middlePanel.getHeight()));
		middleScrollPane.setBounds(0, upperPanel.getHeight(), Config.getWidth() + 20, middleHeight);
	
		setCMDLineHeight();
		repaint();
		java.awt.EventQueue.invokeLater(new Runnable() { public void run() {
		    scrollBar.setValue(scrollBar.getMinimum());
		}});
	}
	
	/*
	 * File opening, file type actions etc.
	 */
	
	/**
	 * Opens file that should be in main direct. If there are not in it, modify the config file parameters.
	 * @param name - file name
	 */
	private void openStaticFile(String name){
		try{
			URL tempURL = new URL(Config.getRoot()+name+".f3s");
			readFile(tempURL);
		}catch(IOException e){
			showToScreen("Something went wrong with opening static file...");	
		}
	}
	
	/**
	 * This method is for opening files by other packages
	 * @param name
	 */
	public static void staticOpenFile(String name){
		mainWindow.openFile(name);
	}
	
	/**
	 * Opens file and checks the type of the file, depending on it, takes actions
	 * if the path leads to direction it adds it to catalog. 
	 * @param name
	 */
	private void openFile(String name){
		try {
			URL tempURL = new URL(Config.getRoot()+Config.getCatalog()+"/"+name);
			
			int FOD = isDirectory(tempURL);
//			System.out.println(tempURL.toString());
			switch(FOD){
			/*
			 * there is no such directory/file
			 */
			case 0:
					noSuchFileDir(name);
					break;
			/*
			 * its a directory
			 */
			case 1:
					Config.setCatalog(Config.getCatalog()+"/"+name);
					tempURL = new URL(Config.getRoot()+Config.getCatalog()+"/DirFile.f3s");
					readFile(tempURL);
					break;
			/*
			 * its a file
			 */
			case 2:
					tempURL = new URL(Config.getRoot()+Config.getCatalog()+"/"+name+".f3s");
					readFile(tempURL);
					break;
			}
		} catch (MalformedURLException e) {
				showToScreen("Something went wrong with opening file/directory: " + name);
				Config.setMode(ModeList.NORMAL);// returns console to normal mode. This can be a bug in future...
		}
	}
	
	/**
	 * Shows msg that there is no such file/directory.
	 * Depending on the name of the file it shows a information
	 * that the file is not avaible
	 * @param name - file name
	 */
	private void noSuchFileDir(String name){
		if(name.equals("DirFile")){
			showToScreen(Config.getNoDir());
		}else{
			showToScreen(Config.getNoFile());
		}
	}
	
	/**
	 * Checks if url is a directory or file.
	 * Sends no such file if there url leads to nothing
	 * @param url - path to file/directory
	 * @return 0 - there is no such directory
	 * 1 - its a directory
	 * 2 - its a file
	 */
	private int isDirectory(URL url){
			
			try {
				/*
				 * Checks if url leads to file 
				 */
				
				URL urlTemp = new URL(url.toString()+".f3s");
				InputStream inFile = urlTemp.openStream();
				BufferedReader inReader = new BufferedReader(new InputStreamReader(inFile, "UTF-8"));
				inReader.close();
				return 2;
			} catch (IOException e) {
				
			}// end catch
			
			/*
			 * checks if directory exists, by checking if dirFile exists
			 * else it shows no such file/directory msg.
			 */
			try {
				URL urlTemp = new URL(url.toString()+"/DirFile.f3s");
				InputStream inFile = urlTemp.openStream();
				BufferedReader inReader = new BufferedReader(new InputStreamReader(inFile, "UTF-8"));
				inReader.close();
				return 1;
			} catch (IOException a) {
				
			}// end inside catch
			
			return 0;
	}
	
	private StringBuilder getTextFromFile(BufferedReader in) throws IOException{
		String tempText;
		StringBuilder textToAdd = new StringBuilder("");
		while((tempText = in.readLine()) != null){
			if(EasternEggConfig.isRabarbar()){
				tempText = tempText.replaceAll(EasternEggConfig.getToReplaceChar()+"", EasternEggConfig.getReplacementChar()+"");
				tempText = tempText.replaceAll((EasternEggConfig.getToReplaceChar()+"").toUpperCase(), (EasternEggConfig.getReplacementChar()+"").toUpperCase());
			}
			textToAdd.append(tempText+"\n");
		}
		
		return textToAdd;
	}
	
	/**
	 * This method is responsible for reading text from file.
	 * First it reads the header to check what kind of file is it. Next depending on the type
	 * it does specified actions.
	 * @param url
	 */
	private void readFile(URL url){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String head = in.readLine();
			
			/*
			 * Actions for text file and dirfile. Text file contains images also. Dirfile are plain text in definition
			 * but can contain images, so feel free to experiment.
			 */
			if(head.toLowerCase().contains("text") || head.toLowerCase().contains("dir")){
				/* this part determinets if there should be flashing panel before reading file or not...
				* actually you can see it in the code, but ppl use to put LOTS of useless comments in
				* code, so i wanna be gangsta too... :P
				*/
				if(EasternEggConfig.isFlashing()){
					setFlashPanel();
					tempIn = in;
					EasternEggConfig.setFlashing(false);
				}else{
					showToScreen(getTextFromFile(in).toString());	
				}
				
			}
				
			/*
			 * Actions for hackfiles. First it runs hacking fallout 3 alike and if user passes it
			 * opens textfile
			 */
			else if(head.toLowerCase().contains("hack")){
				if (hacked || byPass){
					String tempText;
					showToScreen(getTextFromFile(in).toString());
					hacked=false;
					Config.setMode(ModeList.NORMAL);
				}
				else if(!hacked && Config.getMode()==ModeList.HACKMODE){
					setHackPanel( Integer.parseInt( head.substring( head.indexOf('>')+1 ) ) );
				}else{
					showToScreen(Config.getNoAccess());
				}
			}
			/*
			 * actions for mapfile. It runs as image only file with coords. Maybe in future it will work like google maps engine :PP
			 * anyway it shows another panel, so feel free to add ur own implementation. Just test it and pray that it will work :P
			 */
			else if(head.toLowerCase().contains("map")){
				Config.setMode(ModeList.MAPMODE);
				setMapPanel(Parsers.filterText(in.readLine())[1]);
			}
		} 
		catch (UnsupportedEncodingException e) {e.printStackTrace();} 
		catch (IOException e) {	e.printStackTrace();}
	}
	
	/**
	 * Sets the middlepanel text to defined in parametr
	 * @param text - text that will be displayed on screen
	 */
	private void showToScreen(String text){
		
		middlePanel.addText(text);
		resetPosition();
		clrCmd();
		scrollToTop();
		repaint();
	}
	
	private void backOffCatalog(){
		try{
			Config.setCatalog(Config.getCatalog().substring(0,Config.getCatalog().lastIndexOf("/")));
		}catch(StringIndexOutOfBoundsException e){
			// actually this is how i shouldnt do... but nvm
		}
		openFile("DirFile");
	}
	
	/*
	 * Key actions, cmds and stuff
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	
	/**
	 * returns text from cmdline in lower case
	 * @return text from cmd line in lower case
	 */
	private String fromCmd(){
		return cmdLine.getText().toLowerCase();
	}
	
	/**
	 * Returns the first word from cmdline that is in fact the command
	 * @return first word from cmdline
	 */
	private String getCommand(){
		try{
			return fromCmd().substring(0,fromCmd().indexOf(" "));
		}catch(StringIndexOutOfBoundsException e){
			return fromCmd();
		}
	}
	/**
	 * returns the rest of cmdline
	 * @return
	 */
	private String getArguments(){
		return fromCmd().substring(fromCmd().indexOf(" ")+1);
	}
	
	/**
	 * returns the second argument of the cmd line.
	 * @return second argument from cmd line
	 */
	private String getSecondArgument(){
		return getSepTextCmd()[2];
	}
	
	/**
	 * returns the first argument from cmd line
	 * @return first argument
	 */
	private String getFirstArgument(){
		try{
			return getSepTextCmd()[1];
		}catch(ArrayIndexOutOfBoundsException e){
			clrCmd();
			return "";
		}
	}
	/**
	 * returns whole cmd line splited at space. Arguments starts at 1;
	 * @return string array
	 */
	private String[] getSepTextCmd(){
		return fromCmd().split(" ");
	}
	
	/**
	 * Clears cmdline from text
	 */
	private void clrCmd(){
		cmdLine.setText("");
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * Scrolling up and down
		 */

		if(e.getKeyCode() == KeyEvent.VK_PAGE_UP){
			setScrollHeight(50);
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			setScrollHeight(-25);
		}else if(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
			setScrollHeight(+50);
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			setScrollHeight(+25);
		
			/*
			 * commands and stuff 
			 */
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			//admin mode activation
			if(fromCmd().equals("#active admin mode")){
				goAdmin=true;
				showToScreen("enter password:");
			}
			else if(fromCmd().equals("#"+Config.getAdminPass()) && goAdmin){
				Config.setAdminMode(true);
				Config.setMode(ModeList.ADMINMODE);
				goAdmin=false;
				openFile("adminhelp");

			}
			//normal mode
			else{
			
				switch(Config.getMode()){
				case NORMAL :
					if(fromCmd().equals("")){
						//nothing to do, but its better then catching exception... 	
						}
						else if(getCommand().equals("cd")){
							openFile(getFirstArgument());
						}
						else if(getCommand().equals("dir")){
							openFile("DirFile");
						}
					//not for ever, just for testing...
						else if(getCommand().equals("jebzakon")){
							if(Config.isEasternEggs()) setFlashPanel();
						}
						else if(getCommand().equals("flashing")){
							EasternEggConfig.setFlashing(true);
						}
						else if(getCommand().equals("rabarbar")){
							if(Config.isEasternEggs()){
								if(EasternEggConfig.isRabarbar()){
									EasternEggConfig.setRabarbar(false);
								}
								else{
									EasternEggConfig.setRabarbar(true);
								}
							}
						}
					// end of stuff for testing... they will be deleted in builds
						else if(getCommand().equals("cd..")){
							backOffCatalog();
						}
						else if(getCommand().equals("run")){
							/*
							 * checks if first argument is debug and acts on it.
							 */
							if(getFirstArgument().equals("debug")){
								Config.setMode(ModeList.HACKMODE);
								chances=4;
								fileInMemory = getSecondArgument();
								openFile(fileInMemory);
								
							}
						}
						else if(getCommand().equals("hlp") || getCommand().equals("help")){
							openStaticFile(Config.getHlpFile());
						}
						
						/*
						*at end check if the cmd isnt the exit cmd
						*/
						else if(Config.isExitCmd(getCommand())){
							System.exit(0);
						}
						else{
							if(goAdmin){ 
								goAdmin=false;
								returnFromPanel();
							}
							showToScreen(Config.getErrorText());
						}
				
					break;
					/*
					 * Hack mode
					 */
				case HACKMODE:
					
					if(getCommand().equals("ps")){
						
						if(hackPanel.isRightPassword(getFirstArgument())){
							hacked=true;
							Config.setMode(ModeList.NORMAL);
							returnToMain();
							openFile(fileInMemory);
						}else{
							chances--;
						}
						
						if(chances<1){
							Config.setMode(ModeList.NORMAL);
							returnToMain();
							showToScreen(Config.getNoAccess());
						}
						
					}else if(getCommand().equals("quit") || getCommand().equals("qit") || getCommand().equals("cd..")){
						Config.setMode(ModeList.NORMAL);
						returnToMain();
						openFile("DirFile");
						
					}
					break;
					
				case MAPMODE:
					
					if(getCommand().equals("point")){
						mapPanel.setPoint(getFirstArgument());
					}else if(getCommand().equals("treasure")){
						mapPanel.randomPoint();
					}else if(getCommand().equals("quit") || getCommand().equals("qit")){
						Config.setMode(ModeList.NORMAL);
						returnToMain();
						openFile("DirFile");
						
					}
					
					break;
					
				case ADMINMODE: // this goes for admin mode
					
					if(getCommand().equals("#adminshow")){
						showToScreen(AdminUtils.getAllProps());
					}
					else if(getCommand().equals("#help")){
						openStaticFile("adminhelp");
					}
					else if(getCommand().equals("#adminpanel")){
						setAdminPanel();
					}
					else if(getCommand().equals("#bypass")){
							byPass ^= true;
							showToScreen("byPass mode is working: "+byPass);
					}
					else if(getCommand().equals("#papa") || fromCmd().equals("#exit admin mode") && Config.isAdminMode()){
						Config.setAdminMode(false);
						Config.setMode(ModeList.NORMAL);
						openFile("DirFile");
					}

					break;
					
				}
			}
			clrCmd();			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	
		
	}
	
	

	
}
