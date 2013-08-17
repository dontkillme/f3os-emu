package sc.fall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Caret;
/**
 * 
 * Bottom side of the console, where the action goes on...
 * Error info list
 * 0 - passwords couldnt load
1 - file coundnt load
2 - error with loading image
3 - error on loading map
4 - error on loading file
5 - error on loading passwords file
6 - error on reading file
 */
public class itxtread extends JPanel implements KeyListener {

	private JTextArea txt = new JTextArea();
	private FileSystemView partitions = FileSystemView.getFileSystemView();
	private File[] f = File.listRoots();
	protected JTextField cmd = new JTextField("");
	private JTextField cmd_char = new JTextField("> ");
	private JPanel panel_txt = new JPanel(null);
	private JScrollPane scroll = new JScrollPane(panel_txt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	//
	private Font font = new Font("Monospaced", Font.BOLD, 14);
	private JScrollBar scrollbar;
	static private JLayeredPane mappoint = new JLayeredPane();
	private JLabel point;
	private Random rand = new Random();
	private BufferedReader in;
//	protected static URL url = null;
	protected static URL url = null;
	protected static URL url_czysty = null;
	private static String file_url = null;

	private String plik ="", nullcmd = "", url_txt="", parent ="";
	private int wys_txt=0, wys_txta=0, width=770;
	private int mode = 0, right, chance = 3, pass_lgh = 0 , lstr=0, img_h=0, img_w=0;
	private boolean password_used = false, debug_mode=false, panel_focus=false, wts_on=false, fddread = false;
	private ArrayList<String> site_list  = new ArrayList<String>();
	private String[] passwords = new String[20];
	private JLabel label;

	
	/**
	 * Main point of the bottom side. Loads all stuff.
	 * @param url - path or url to file folder
	 * @param null_cmd - what text is used if there is no such command
	 * @param parent - how did it started (applet or normal application), use "pc" for normal application if not using PCFrame.java as starter
	 */
	
	private void fddreboot(){
		partitions = FileSystemView.getFileSystemView();
		f = File.listRoots();
	}
	
	public itxtread(String url, String null_cmd, String parent){
		setLayout(null);
		try {
			this.parent = parent;
			if(parent.equalsIgnoreCase("PC")){
				file_url = url+"\\";	
			}else{
			url_czysty = new URL(url);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSize(800,500);
		setBackground(Color.BLACK);
		// wczytanie danych do zmiennych
		nullcmd=null_cmd;
		url_txt=url;
		// wysokosci
		cmd.setBounds(20,wys_txt+16,795,18);
		cmd_char.setBounds(5,wys_txt+16,20,18);
		txt.setBounds(0,0,width,wys_txt);
		txt.setPreferredSize(new Dimension(width,wys_txt));
		panel_txt.setBounds(0,0,790,wys_txt);
		panel_txt.setPreferredSize(new Dimension(790,wys_txt));
		
		scroll.setBounds(12,0,820,wys_txt);
		
		
		
		scroll.setWheelScrollingEnabled(true);
		// kolory itd
	
		txt.setBackground(Color.BLACK);
		txt.setForeground(new Color(0,240,0));
		txt.setEditable(false);
		txt.setWrapStyleWord(true);
		txt.setLineWrap(true);
		txt.setFont(font);
		
		panel_txt.setBackground(Color.BLACK);
		
		scroll.setBorder(null);
		scroll.setBackground(Color.BLACK);
		
		cmd.setBackground(Color.BLACK);
		cmd.setBorder(null);
		cmd.setForeground(Color.GREEN);
		cmd.addKeyListener(this);
		cmd.setFont(font);
		Caret caret = cmd.getCaret();
		caret.setBlinkRate(1000);
		cmd.setCaretColor(new Color(0,255,0));
		panel_txt.addKeyListener(this);	

		
		
		
		
		cmd_char.setFont(font);
		cmd_char.setBackground(Color.BLACK);
		cmd_char.setEditable(false);
		cmd_char.setBorder(null);
		cmd_char.setForeground(Color.GREEN);
		// dodawanie
		add(scroll);
		add(cmd_char);
		add(cmd);
		scrollbar = scroll.getVerticalScrollBar();
		scroll.setVerticalScrollBar(scrollbar);
		
		panel_txt.add(txt);
		point = new JLabel(new ImageIcon(paintPoint()));
		passwordLoad();
		ReadFile("wlc",0);
		
	}
	

	/**
	 * Odpowiedzialne za ustawianie wysokoœci
	 * Sets the height of almost all objects.
	 */

	private void setWys(){
		scroll.setBounds(12,0,820,wys_txt);
		txt.setBounds(0,0,width,wys_txta);
		txt.setPreferredSize(new Dimension(width,wys_txta));
		txt.setCaretPosition(0);
		
			panel_txt.setPreferredSize(new Dimension(790,wys_txta));
			panel_txt.setBounds(0,0,790,wys_txta);
			
			txt.setBounds(0,5,width,wys_txta);
		cmd.setBounds(20,wys_txt+20,790,18);
		cmd_char.setBounds(5,wys_txt+20,15,18);
		if(wts_on==true && label!=null){
			label.setBounds(0,wys_txta+5,img_w,img_h);
			panel_txt.add(label);
			panel_txt.setPreferredSize(new Dimension(790,wys_txta+img_h));
			panel_txt.setBounds(0,0,790,wys_txta+img_h);
			txt.setBounds(0,5,width,wys_txta);
			
			if(wys_txt+img_h<400){
			scroll.setBounds(12,0,820,wys_txt+img_h);	
			cmd.setBounds(20,wys_txt+img_h,790,18);
			cmd_char.setBounds(5,wys_txt+img_h,15,18);
			}else{
				scroll.setBounds(12,0,820,400);
				cmd.setBounds(20,405,790,18);
				cmd_char.setBounds(5,405,15,18);
			}
			
			}
		
	}
	/**
	 * metoda odpowiedzialna za haczenie ala fallout 3
	 * Hacking like in fallout 3/fallout 3: new vegas
	 */
	private void hack(){
		wys_txt = 400;
		passwordLoad();
		wys_txta = wys_txt;
		panel_txt.removeAll();
		panel_txt.add(txt);
		chance = 4;
		right = rand.nextInt(20);
//		System.out.println(passwords[right]);
		txt.setText("");
		txt.setLineWrap(false);
		txt.setWrapStyleWord(false);
		txt.append( "W PONI¯SZEJ TABLICY ZNAJDUJE SIÊ HAS£O USTAWIONE LOSOWO.\nWPISUJ¥C POLECENIE PS WYBRANE_HAS£O MO¯ESZ WPROWADZIÆ HAS£O\nNP. PS JASZCZURKA\n" + "ABY OPUŒCIÆ SZUKANIE HAS£A I PRZEJŒÆ DO WYBORÓW NALE¯Y WPISAÆ: QIT\n\n"+
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" +
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" +
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" +
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" +
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" +
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" +
					RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + "  " +RCoS()+ "  " + RCoS() + " \n" + " \n" );
		setWys();
		mode = 1;
		repaint();
		password_used=false;
	}
	/**
	 * Metoda od wybierania losowych znakow lub hasel
	 * Return random char to fill up the hacking space.
	 * @return char
	 */
	private char randomChar(int random_inta){
		char rnd_char = '.';
		switch(random_inta){
		case 0: rnd_char = '.'; break;
		case 1: rnd_char = ','; break;
		case 2: rnd_char = '_'; break;
		case 3: rnd_char = '-'; break;
		case 4: rnd_char = '&'; break;
		case 5: rnd_char = '$'; break;
		case 6: rnd_char = '#'; break;
		case 7: rnd_char = ','; break;
		case 8: rnd_char = '.'; break;
		}
		return rnd_char;
	}
	/**
	 * metoda odpowiedzialna za wybieranie kolejnych fragmentow haczkowania
	 * This method returns random word from passwords.f3s, and sets it as password or just a word.
	 * @return string - losowe slowo albo haslo/random word or password
	 */
	private String RCoS(){
		int a=rand.nextInt(60);
		String zwrot ="";
		char znak;
		if(a<20){
		zwrot = passwords[a];
		}else if(a>20 && a<25 && password_used==false){
		zwrot = passwords[right];
		password_used=true;
		System.out.println(zwrot);
		}else{
		for(int b=0; b<pass_lgh; b++){
			znak = randomChar(rand.nextInt(9));
			
			zwrot=zwrot+znak;
		}
		}
		return zwrot; 
	}
	/**
	 * metoda odpowiedzialna za sprawdzanie ile liter pasuje
	 * This method returns how many of chars in selected word are in the same position and are the same like in the password
	 * @param typed - selected password
	 * @return numer of correct chars
	 */
	private int CheckLenghtRight(String typed){
		int RightChar = 0, lgh;
		
		if(typed.length()>passwords[right].length()){
			lgh = passwords[right].length();
		}else {
			lgh = typed.length();
		}
		
		for(int a=0; a<lgh; a++)
		{
			if(Character.toLowerCase(passwords[right].charAt(a))==Character.toLowerCase(typed.charAt(a))){
				RightChar++;
			}
		}
		
		return RightChar;
	}
	/**
	 * laduje hasla o pewnej dlugosci
	 * Loads password with specified lenght
	 */
	private void passwordLoad(){
		try {
			
		OpenLocalFiles("passwords");
		String linia;
		
			while( (linia = in.readLine()) !=null ){
				if( linia.equalsIgnoreCase("<"+pass_lgh+">") ){
					int pass_id = 0;
					while( !(linia = in.readLine()).equalsIgnoreCase("<koniec>") )
					{
						passwords[pass_id]=linia.toUpperCase();
						pass_id++;
						if(pass_id>19){
							break;
						}
					}
				}
			}
		in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorShow("0");
		}
	}
	
	
	/**
	 * metoda wypisujaca na ekran
	 * This is the place... method :P that adds text and images to screen
	 * @param FileName - nazwa pliku
	 */

private void WriteToScreen(String filename){
		wys_txt=0;
//		OpenFile(filename);
		panel_txt.removeAll();

		int line_length=0;
		int last_y=0;

		last_y=+5;
		try {
			String alfa = "";
//			String text = in.readLine();
			String text;
//			if(text.equals("<text>")){

			while( (text = in.readLine() ) !=null){

				if(text.contains("<img>")){
					alfa +="\n"+ text.substring(0,text.indexOf("<img>"));
					line_length++;
					alfa.toUpperCase();
					JTextArea asdf = new JTextArea(alfa);
					asdf.setFont(font);
					
					asdf.setForeground(new Color(0,240,0));
					asdf.setWrapStyleWord(true);
					asdf.setLineWrap(true);
					asdf.setEditable(false);
					asdf.setBackground(Color.black);
					asdf.setBounds(0,last_y,770,((line_length+1)*20));
					last_y+=5+line_length*20;
					panel_txt.add(asdf);
//					System.out.println("poszlo");
					JLabel label =	new JLabel(new ImageIcon(resizeImage(text.substring( text.indexOf("<img>")+5, text.indexOf("</img>")),0 )));
					label.setBackground(Color.black);

					label.setBounds(0,last_y,img_w,img_h);
					last_y+=5+img_h;
					panel_txt.add(label);
					line_length = 0;
					alfa="";
				}else {
				alfa = alfa +"\n"+ text;
				line_length++;
				}
			}
			if(alfa != null){
				alfa.toUpperCase();
				JTextArea asdf = new JTextArea(alfa);
//				alfa+="\n"+in.readLine();
				asdf.setFont(font);
				asdf.setForeground(new Color(0,240,0));
				asdf.setLineWrap(true);
				asdf.setEditable(false);
				asdf.setWrapStyleWord(true);
				asdf.setBackground(Color.black);
				asdf.setBounds(0,last_y,770,((line_length+1)*20));
				last_y+=5+(line_length+1)*20;
				panel_txt.add(asdf);
				wys_txta=last_y;
				wys_txt=wys_txta;
				if(wys_txta>400){
					wys_txt=400;
				} 
				
				setWys();
				scrollbar = scroll.getVerticalScrollBar();
				scrollbar.setValue(scrollbar.getMinimum());
				
				
				repaint();
				validate();
				

				
				
			}
			in.close();
			
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorShow("1");
		}
		
	}


private void ErrorShow(String text){
	try {
		in.close();
	} catch (IOException e) {
		
	}
	JTextArea asdf;
	if(text.equals("6")){
		asdf = new JTextArea("NIE MA TAKIEGO PLIKU");
	}else {
		asdf = new JTextArea("B£¥D NR "+text+"\nZG£OŒ ADMINISTRATOROWI WYPE£NIAJ¥C FORMULARZ 432\nZNAJDUJ¥CY SIE W DZIALE REKLAMACJI WEWNÊTRZNYCH.");
	}
	panel_txt.removeAll();
	
	asdf.setFont(font);
	asdf.setForeground(new Color(0,240,0));
	asdf.setLineWrap(true);
	asdf.setEditable(false);
	asdf.setWrapStyleWord(true);
	asdf.setBackground(Color.black);
	asdf.setBounds(0,0,770,70);
	panel_txt.add(asdf);
	wys_txta=80;
	wys_txt=wys_txta;
	setWys();
	repaint();
}
	/**
	 * zmiana wielkosci obrazku; (image resize)
	 * resize and recolor image
	 * @param text - filename
	 */
private BufferedImage resizeImage(String text, int par){
	try {
		BufferedImage img;
		if(parent.equalsIgnoreCase("pc") && fddread!=true){
			File url = new File(file_url+text);
//			System.out.println(url);
			img = ImageIO.read(url);
		}else if(parent.equalsIgnoreCase("pc") && fddread==true)
		{
			File url = new File(f[0]+text);
			img = ImageIO.read(url);
		}
		else{
			URL url = new URL(itxtread.url_czysty+text);
			img = ImageIO.read(url);
		}
//		File imgfile = new File("e://samlis//"+text);
	
	int w, h;
	w=img.getWidth();
	h=img.getHeight();
	switch(par){
	case 0:{
		if(w>700){
			w=700;
			
			}
			if(h>400){
			h=400;
			}
		break;
	}
	case 1:{
		if(w>750){
			w=750;
			
			}
		break;
	}
	}// end switch
	
		img_h=h;
		img_w=w;
	BufferedImage resize = new BufferedImage(w, h, img.getType());
	BufferedImage out = new BufferedImage(w, h, img.getType());
	Graphics2D g = resize.createGraphics();	    
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(), null);
    g.dispose();
    int kolor,red,green,blue;
    g = out.createGraphics();
    g.setColor(Color.black);
    g.drawRect(0,0,w,h);
    for(int y=0; y<resize.getHeight(); y+=2){
    	for(int x=0; x<resize.getWidth(); x+=2){
    kolor = resize.getRGB(x, y);
	 red   = (kolor & 0x00ff0000) >> 16;
	 green = (kolor & 0x0000ff00) >> 8;
	 blue  =  kolor & 0x000000ff;
	 green = Math.max(red, Math.max(green, blue));
		 g.setColor(new Color(0,green,0));
    g.drawRect(x, y, 1, 1);
    	}
    }
    return out;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		ErrorShow("2");
	} 
    return null;
}

	/**
	 * metoda odpowiedzialna za wczytywanie paragrafocostam 
	 * Adds txt and one image to screen for umm interactive story?
	 */
	private void WtsPara(int str){
		OpenFile(plik);
		panel_txt.removeAll();
		panel_txt.add(txt);
		txt.setText("");
		wys_txta=0;
		boolean wyjdz = false;
		site_list.add("1");
		String text;
		label = null;
		try {
			while( (text = in.readLine() )!=null){
				if( text.equals("<"+str+">" ) && site_list.contains((String) ""+str) ){
					while(!(text = in.readLine()).equalsIgnoreCase("<koniec>") )
					{
						if(text.contains("<img>")){
							label = new JLabel(new ImageIcon(resizeImage(text.substring( text.indexOf("<img>")+5, text.indexOf("</img>")),0 )));
						} else if(text.substring(0, 2).equalsIgnoreCase("<=")){
							WtsPara(Integer.parseInt(text.substring(2,3)));
						}else if(text.substring(0,2).equalsIgnoreCase("<-")){
							siteList(text.substring(2));
						}else{
						txt.append(text.toUpperCase()+"\n");
						wys_txta++;
						}
						lstr=str;
						wyjdz=true;
					}
					if(wyjdz==true){
						break;
					}
				} else if (!site_list.contains((String) ""+str)){
					txt.append("NIE MO¯ESZ PRZEJŒÆ NA STRONÊ... TWOJA POPRZEDNIA TO:" +lstr);
					site_list.clear();
					site_list.add((String)""+lstr);
					wys_txta++;
					break;
				}
				
				
			}
			
			wys_txta = wys_txta*30;
			if(wys_txta>400){
			wys_txt=400;
			}
			else {
				wys_txt=wys_txta;
			}
			cmd.setText("");
			setWys();
			repaint();
			in.close();
		}catch(IOException e){
//			System.out.println("nvm");
		}
	}
	
	/**
	 * metoda odpowiedzialna za wczytywanie dozwolonych stron
	 * loads site list for interactive story
	 */
	private void siteList(String sites){
		site_list.clear();
		String[] list;
		list = sites.split(",");
		for(int a=0; a<list.length; a++){
			site_list.add(list[a]);
		}
//		System.out.println("done");
	}
	/**
	 * Method creates point for map
	 * @return
	 */
	private BufferedImage paintPoint(){
		BufferedImage pointa = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
		Graphics g = pointa.createGraphics();
		g.setColor(Color.green);
		g.drawRect(0, 0, 14, 14);
		g.drawRect(1, 1, 12, 12);
		return pointa;
	}
	
	private void paintMap(){
		panel_txt.removeAll();
		wys_txt=400;
		String text;
		try {
			while( (text = in.readLine() ) !=null){
				if(text.contains("<img>")){
					
					JLabel labela =	new JLabel(new ImageIcon(resizeImage(text.substring( text.indexOf("<img>")+5, text.indexOf("</img>")),1 )));
					wys_txta=img_h;
					mappoint.setPreferredSize(new Dimension(img_w,img_h));
					mappoint.setBounds(0,0,img_w,img_h);
					labela.setBounds(0,0,img_w,img_h);

					panel_txt.add(mappoint);
					
					mappoint.add(labela, new Integer(0));
					mappoint.add(point, new Integer(1));
					setWys();
					scrollbar = scroll.getVerticalScrollBar();
					scrollbar.setValue(scrollbar.getMinimum());
					repaint();
					validate();
					in.close();
					break;
				}
			}// end while
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorShow("3");
		}
	}
	private void setPointOnMap(String cord_code){
		String[] code = cord_code.split(",");
		point.setBounds(Integer.parseInt(code[0]),Integer.parseInt(code[1]),15,15);
		
		mappoint.moveToFront(point);
		repaint();
		validate();
	}
	
	private void OpenLocalFiles(String filename){
		try {
			
			if(parent.equalsIgnoreCase("pc")){
				FileInputStream infile;
				
				infile = new FileInputStream(new File(file_url+filename+".f3s"));
				in = new BufferedReader(new InputStreamReader(infile, "UTF-8"));
				
			}else{
				url = new URL(url_txt+"passwords.f3s");
				InputStream infile = url.openStream();	
				in = new BufferedReader(new InputStreamReader(infile, "UTF-8"));
			}
//			new FileInputStream(filename+".f3s"))
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			e.printStackTrace();
			ErrorShow("5");
		}
	}
	/**
	 * metoda odpowiedzialna za otwieranie pliku
	 * Opens file
	 */
	private void OpenFile(String filename){
		try {
			
			if(parent.equalsIgnoreCase("pc")){
				FileInputStream infile;
				if(fddread==true){
					infile = new FileInputStream(new File(f[0]+filename+".f3s"));
					in = new BufferedReader(new InputStreamReader(infile, "UTF-8"));
				}else{
				infile = new FileInputStream(new File(file_url+filename+".f3s"));
				in = new BufferedReader(new InputStreamReader(infile, "UTF-8"));
				}
			}else{
				url = new URL(url_txt+filename+".f3s");
				InputStream infile = url.openStream();	
				in = new BufferedReader(new InputStreamReader(infile, "UTF-8"));
			}
//			new FileInputStream(filename+".f3s"))
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			ErrorShow("4");
		}
	}
	/**
	 * metoda odpowiedzialna za wczytywanie danych typow
	 * Determinates what kind of text is it.
	 * @param filename, str - str stands for page
	 */
	
	protected void fddstart(){
		fddread=true;
		ReadFile("autorun",0);
	}
	protected void fddstop(){
		fddread=false;
		ReadFile("wlc",0);
		if(debug_mode==true){
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		qitMeth();
		debug_mode=false;
		}
	}
	
	private void ReadFile(String filename, int str){
			try {
				if(filename.contains("#") || filename.contains("?") || filename.contains("$")){
					txt.setText("NIE MA SZANS CZLOWIEKU...");
				}else{
				plik=filename;	
			OpenFile(filename);
		
			String text = "";
			text = in.readLine();

			if(text.contains("<")){
				text = text.substring(text.indexOf("<"));
			}

			switch(text.substring(0,6).toLowerCase()){
				case "<text>": {
//					in.close();
					wts_on=false;
					WriteToScreen(filename);
					break;
				}
				case "<hack>": {
					wts_on=false;
					if(debug_mode==true){
					pass_lgh=Integer.parseInt(text.substring(6));
					hack();
					}else{
						panel_txt.removeAll();
						panel_txt.add(txt);
					txt.setText("ACCESS DENIED TO "+filename.toUpperCase());
					repaint();
					}
					break;
				}
				case "<para>":{
					mode=2;
					plik=filename;
					wts_on=true;
					WtsPara(1);
					break;
				}
				case "<mapa>":{
					mode=3;
					paintMap();
					break;
				}
				
			}
			
			setWys();
				}
		} catch (IOException e) {
		
			e.printStackTrace();
			ErrorShow("6");
		}
			
	}
	
	
	
	/**
	 * metoda ustawiaj¹ca terminal na 0
	 * resets terminal text.
	 */
	private void qitMeth(){

		ReadFile("wlc",0);
		cmd.setText("");
		mode=0;
		debug_mode=false;
	}
	/**
	 * text from command line to lowercase
	 * @return lowercase text from command line
	 */
	private String fromCmd(){
		return cmd.getText().toString().toLowerCase();
	}
	
	@Override
	/**
	 * Heart of command list... kinda messy...
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER && panel_focus==false){
		try{
			// offline version only
		if(fromCmd().equals("idewchujztejwiochy")){
			System.exit(0);
		}
		if(fromCmd().equals("fddreboot")){
			fddreboot();
		}
		switch(mode){
		case 0:{
		switch(fromCmd().substring(0,3)){
		
		case "cd ":{ 
			if(fromCmd().substring(4).contains(" ")){
			ReadFile(fromCmd().substring(3,fromCmd().indexOf(' ', 4)),0);
//			System.out.println(fromCmd().substring(3,fromCmd().indexOf(' ', 4)));
			}
			else{
			ReadFile(fromCmd().substring(3),0);	
			}
		cmd.setText("");
		break;
		} // end cd
		case "hlp":{
			ReadFile("help",0);
			cmd.setText("");
			break;
		}
//		case "dir":{
//			fddread=true;
//			ReadFile(fromCmd().substring(3)+"fpp",0);
//			cmd.setText("");
//			break;
//		}
//		case "fdd":{
//			
//			break;
//		}
		case "run":{
			if(fromCmd().substring(4,9).equals("debug")){
				

					txt.setText("TERMINAL PRZECHODZI W STAN DEBUG");
	

				debug_mode=true;
				ReadFile(fromCmd().substring(10),0);
				cmd.setText("");
			}else {
				txt.setText(nullcmd.toUpperCase());
				cmd.setText("");
				break;
			}
			break;
		}
		default:{
			panel_txt.removeAll();
			panel_txt.add(txt);
			txt.setText(nullcmd.toUpperCase());
			cmd.setText("");
			repaint();
		}
		} // switch end
		
		break; 
		}
		
		case 1:{
		switch(fromCmd().substring(0,3)){
		
		case "ps ":{
			int correct;
		if( (correct = CheckLenghtRight(fromCmd().substring(3))) == fromCmd().substring(3).length() ){
			OpenFile(plik);
			try { // totalnie idiotyczne przeszkoczenie linii :DDD
				in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			txt.setLineWrap(true);
			txt.setWrapStyleWord(true);
			WriteToScreen("a");
			cmd.setText("");
			mode = 0;
			debug_mode=false;
		}else{
			if(chance==1){
			txt.setText("ACCESS DENIED");
			cmd.setText("");
			debug_mode=false;
			mode = 0;
			}
			else
			{
				chance--;
			
				if(fromCmd().substring(3).length()>=pass_lgh)
				{
					
					txt.append(chance+": Powtarzaj¹cych siê liter na tej samej pozycji co w haœle:\n"+correct+"/"+passwords[right].length());
					txt.append(" "+cmd.getText().substring(3,(pass_lgh+3)).toUpperCase()+"\n");
				} 
				else
				{
					txt.append(chance+": Powtarzaj¹cych siê liter na tej samej pozycji co w haœle:\n"+correct+"/"+passwords[right].length());
					txt.append(" "+cmd.getText().substring(3).toUpperCase()+"\n");	
				}
				cmd.setText("");
			
			}
		}
		
		break;	
		} // end case ps
		
		case "qit":
		{
			txt.setLineWrap(true);
			txt.setWrapStyleWord(true);
			qitMeth();

			debug_mode=false;
			break;
		}
		default:{
//			txt.setText(nullcmd.toUpperCase());
			cmd.setText("");
		} // end default
		
		}
		break;
		} // switch end for mode 1
		
		case 2:
		{
			switch(fromCmd().substring(0,3))
			{
			case "str":
				{
//					ReadFile("tensam",Integer.parseInt(cmd.getText().substring(4)));
					WtsPara(Integer.parseInt(fromCmd().substring(4)));
					break;
				}
				
			case "qit":
				{	
					qitMeth();
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
					break;
				}	
			default:{
				cmd.setText("");
			} break;
			} // end case mode 2
		}// end case 2
		
		case 3:
		{
			switch(fromCmd().substring(0,3))
			{
			case "cd ":{
				mode=0;
				if(fromCmd().substring(4).contains(" ")){
					ReadFile(fromCmd().substring(3,fromCmd().indexOf(' ', 4)),0);

					}
					else{
					ReadFile(fromCmd().substring(3),0);	
					}
				cmd.setText("");
				break;
			}
			case "kr ":{
			

				setPointOnMap(fromCmd().substring(3));
				cmd.setText("");
				break;
			}
			} // end command switch
			
		} // end case 3
		
		} // end main switch
		}catch(StringIndexOutOfBoundsException f){
			
		}
		} // end if
		else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			scrollbar.setValue(scrollbar.getValue()+15);
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP){
			scrollbar.setValue(scrollbar.getValue()-15);
		}
		else if(e.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
			scrollbar.setValue(scrollbar.getValue()+50);
		}
		else if(e.getKeyCode()==KeyEvent.VK_PAGE_UP){
			scrollbar.setValue(scrollbar.getValue()-50);
		}
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
			
	}
	
}
