package sc.fall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Caret;

public class txtread extends JPanel implements KeyListener {

	private JTextArea txt = new JTextArea();
	
	protected JTextField cmd = new JTextField("");
	private JTextField cmd_char = new JTextField("> ");
	
	private JPanel panel_txt = new JPanel(null);
	
	private JScrollPane scroll = new JScrollPane(panel_txt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	//
	private Font font = new Font("Monospaced", Font.BOLD, 14);
	
	private BufferedImage userImage;
	private Random rand = new Random();
	private BufferedReader in;
	private URL url = null;
	
	private String plik ="", nullcmd = "", url_txt="";
	private int wys_txt=0, wys_txta=0, width=770;
	private int mode = 0, right, chance = 3, pass_lgh = 0 , lstr=0;
	private boolean password_used = false, debug_mode=false;;
	private ArrayList<String> site_list  = new ArrayList<String>();
	private String[] passwords = new String[20];


	
	/**
	 * konstruktor panelu, posiada w sobie wszystkie metody odpowiedzialne za pozycje, wyglad itd tej czesci panelu
	 */
	public txtread(String url, String null_cmd){
		setLayout(null);
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
		
		scroll.setBounds(12,0,790,wys_txt);
		
		
		
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
		
		
		
		
		cmd_char.setFont(font);
		cmd_char.setBackground(Color.BLACK);
		cmd_char.setEditable(false);
		cmd_char.setBorder(null);
		cmd_char.setForeground(Color.GREEN);
		// dodawanie
		add(scroll);
		add(cmd_char);
		add(cmd);

		panel_txt.add(txt);
		
		passwordLoad();
		ReadFile("wlc",0);
	}
	

	/**
	 * odpowiedzialne za ustawianie wysokoœci
	 */
	private void setWys(){
		scroll.setBounds(12,0,790,wys_txt);
		
		txt.setBounds(0,0,width,wys_txta);
		txt.setPreferredSize(new Dimension(width,wys_txta));
		txt.setCaretPosition(0);
		
		panel_txt.setPreferredSize(new Dimension(790,wys_txta));
		panel_txt.setBounds(0,0,790,wys_txta);
		
		
		cmd.setBounds(20,wys_txt+16,790,18);
		cmd_char.setBounds(5,wys_txt+16,15,18);
		
	}
	/**
	 * metoda odpowiedzialna za haczenie ala fallout 3
	 */
	private void hack(){
		wys_txt = 400;
		passwordLoad();
		wys_txta = wys_txt;
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
		password_used=false;
	}
	/**
	 * Metoda od wybierania losowych znakow lub hasel
	 * @return
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
	 * @return string - losowe slowo albo haslo
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
	 * @param typed
	 * @return
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
	 */
	private void passwordLoad(){
		try {
			
		OpenFile("passwords");
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
		}
	}
	
	
	/**
	 * metoda wypisujaca na ekran
	 * @param FileName
	 */
	private void WriteToScreen(String FileName){
		txt.setText("");
		wys_txta=0;
		String text;
		try {
			while( (text = in.readLine() )!=null){
				txt.append(text.toUpperCase()+"\n");
				wys_txta++;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wys_txta = wys_txta*24;
		if(wys_txta>400){
		wys_txt=400;
		}
		else {
			wys_txt=wys_txta;
		}
		setWys();
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * metoda odpowiedzialna za wczytywanie paragrafocostam 
	 */
	private void WtsPara(int str){
		OpenFile(plik);
		txt.setText("");
		wys_txta=0;
		boolean wyjdz = false;
		site_list.add("1");
		String text;
		try {
			while( (text = in.readLine() )!=null){
				if( text.equals("<"+str+">" ) && site_list.contains((String) ""+str) ){
					while(!(text = in.readLine()).equalsIgnoreCase("<koniec>") )
					{
						if(text.substring(0, 2).equalsIgnoreCase("<=")){
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
			
			wys_txta = wys_txta*60;
			if(wys_txta>400){
			wys_txt=400;
			}
			else {
				wys_txt=wys_txta;
			}
			cmd.setText("");
			setWys();
			in.close();
		}catch(IOException e){
//			System.out.println("nvm");
		}
	}
	
	/**
	 * metoda odpowiedzialna za wczytywanie dozwolonych stron
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
	 * metoda odpowiedzialna za otwieranie pliku
	 */
	private void OpenFile(String filename){
		try {
			
			url = new URL(url_txt+filename+".f3s");
			InputStream infile = url.openStream();	
//			new FileInputStream(filename+".f3s"))
		in = new BufferedReader(new InputStreamReader(infile));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * metoda odpowiedzialna za wczytywanie danych typow
	 * @param filename, str
	 */
	
	private void ReadFile(String filename, int str){
			try {
				if(filename.contains("#") || filename.contains("?") || filename.contains("$")){
					txt.setText("NIE MA SZANS CZLOWIEKU...");
				}else{
				plik=filename;	
			OpenFile(filename);
			String text = in.readLine();
			switch(text.substring(0,6).toLowerCase()){
				case "<text>": {
					WriteToScreen(filename);
					break;
				}
				case "<hack>": {
					if(debug_mode==true){
					pass_lgh=Integer.parseInt(text.substring(6));
					hack();
					}else{
					txt.setText("ACCESS DENIED TO "+filename.toUpperCase());	
					}
					break;
				}
				case "<para>":{
					mode=2;
					plik=filename;
					WtsPara(1);
					break;
				}
				case "<imga>":{
					
				}
			}
			
			setWys();
				}
		} catch (IOException e) {
		
			e.printStackTrace();
		}
			
	}
	
	private void ReadImage(){
		
	}
	
	/**
	 * clearCmd czysci cmd
	 * 
	 */
	private void clearCmd(){
		cmd_char.setText("> ");
		cmd.setText("");
	}
	
	/**
	 * metoda ustawiaj¹ca terminal na 0
	 */
	private void qitMeth(){
//		wys_txta=30;
//		wys_txt=30;
//		setWys();
//		txt.setText("Wycofano...");
		ReadFile("wlc",0);
		cmd.setText("");
		mode=0;
		debug_mode=false;
	}
	
	private String fromCmd(){
		return cmd.getText().toString().toLowerCase();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
		try{
		switch(mode){
		case 0:{
		switch(fromCmd().substring(0,3)){
		case "cd ":{ 
			ReadFile(fromCmd().substring(3),0);
		cmd.setText("");
		break;
		} // end cd
		case "hlp":{
			ReadFile("help",0);
			cmd.setText("");
			break;
		}
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
			txt.setText(nullcmd.toUpperCase());
			cmd.setText("");
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
		
		} // end main switch
		}catch(StringIndexOutOfBoundsException f){
			
		}
		} // end if
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
