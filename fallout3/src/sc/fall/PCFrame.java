package sc.fall;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

public class PCFrame extends JFrame{ 
	private Cursor curs = new Cursor(Cursor.TEXT_CURSOR);
	protected upinfo up;
	protected itxtread dw;
	private String info_term = "", url="", txt="";
	private Runnable hello = new Fddchecker();
    private Thread thread1 = new Thread(hello);
	
		public PCFrame(){
			setLayout(null);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setAlwaysOnTop(true);
			setUndecorated(true);
			setVisible(true);
			setSize(800,600);
			setBackground(Color.BLACK);
			setCursor(curs);
			ReadConfig();
			up = new upinfo(info_term);
			dw = new itxtread(url, txt, "pc");
			up.setBounds(0,0,800,100);
			dw.setBounds(0,100,800,500);
		//
			
			
		    thread1.setDaemon(true);
		    thread1.setName("fddchecker");
		    thread1.start();
//			dw.requestFocus();
			add(up);
			add(dw);
			dw.cmd.requestFocus();
			
		}
		public void threadreboot(){
			thread1.start();
			System.out.println("reboot fdd");
		}
		
		/**
		 * This method is almost the same as for applet, but instead of url it uses paths, and fileinputstream
		 */
		private void ReadConfig(){
			try {
				Path currentRelativePath = Paths.get("");
				String url_part = currentRelativePath.toAbsolutePath().toString();
				
				File infile = new File(currentRelativePath.toAbsolutePath().toString());
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(infile+"\\config.f3s")));
				String txta=in.readLine();
				
				while( !(txta=in.readLine()).equals("<koniec>") ){
				info_term=info_term+txta.toUpperCase()+"\n";
				}
				if((txta=in.readLine()).substring(0,5).equals("<url:")){
					url = ""+url_part+"\\";
				}
				in.readLine();
				while( !(txta=in.readLine()).equals("<koniec>") ){
					txt=txt+txta.toUpperCase()+"\n";
				}
				in.close();
//				System.out.println(info_term+"\n <===>"+url+"\n <+++>"+txt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private class Fddchecker implements Runnable {
			private FileSystemView  partitions;
			private File[] f;
			private int flopp=123;
			private boolean fddread=false;
			public Fddchecker(){
				partitions = FileSystemView.getFileSystemView();
				f = File.listRoots();
				for(int a=0; a<f.length; a++){
					if(partitions.isFloppyDrive(f[a])==true){
						flopp=a;
						break;
					}
				}
			
			}
			
			public void run() {
				while(true){
					if(flopp==123){
						break;
					}
//					System.out.println("fdd run");
					if(partitions.isFloppyDrive(f[flopp])==true && !partitions.getSystemDisplayName(f[flopp]).equals("") && fddread==false){
						fddread=true;
						dw.fddstart();
//						System.out.println("fdd read on");
					}
					if(partitions.isFloppyDrive(f[flopp])==true && partitions.getSystemDisplayName(f[flopp]).equals("") && fddread==true){
						fddread=false;
						dw.fddstop();
//						System.out.println("fdd read off");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}


		}

	}

