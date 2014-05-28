package org.zakonfallout.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.zakonfallout.Config;
import org.zakonfallout.MainWindow;
import org.zakonfallout.PCStart;
import org.zakonfallout.utils.Parsers;
/**
 * GUI configuration panel.
 * @author emerald
 *
 */
public class ConfigPanel extends JPanel {
	
	
	private String[] temp;
	private JLabel lExternalDisc = new JLabel("CdRom/USB path"),
				   lResolution = new JLabel("Program resolution (width, height)"),
				   lBackground = new JLabel("Background color RGB MODEL(r,g,b)"),
				   lButtons = new JLabel("FullScreen    ||    OnTop"),
				   lFrontground = new JLabel("Foreground color RGB MODEL(r,g,b)"),
				   lPixelSkip = new JLabel("Skip how many pixels in images"),
				   lRandomChars = new JLabel("Set of random chars for hacking");
	private JTextField tExternalDisc = new JTextField(Config.getCdRom()),
					   tResolution = new JTextField(Config.getWidth()+","+Config.getHeight()),
					   tBackground = new JTextField(Config.getBackgroundColor().getRed()+","+Config.getBackgroundColor().getGreen()+","+Config.getBackgroundColor().getBlue()),
					   tForeground = new JTextField(Config.getFrontgroundColor().getRed()+","+Config.getFrontgroundColor().getGreen()+","+Config.getFrontgroundColor().getBlue()),
					   tPixelSkip = new JTextField(Parsers.getSkipPixels()+""),
					   tRandomChars = new JTextField(Config.getRandomChars());
	private JButton bSave = new JButton("Save"),
					FullScreen = new JButton("FullScreen"),
					OnTop = new JButton("OnTop");
	
	
	
	public ConfigPanel(int w,int h){
		setSize(w,h);
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		setBackground(Color.gray);
		setupAll();
	}
	
	/**
     * Changes button color and text depending if the option is on or off
     * @param tempButt - reference to button.
     */
    private void setButtonColor(JButton tempButt,boolean prawda){
            if(prawda){
                    tempButt.setText("On");
                    tempButt.setBackground(Color.green);
            }else{
                    tempButt.setText("Off");
                    tempButt.setBackground(Color.red);
            }
    }

	private void setupAll(){
		
		bSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.setCdRom(tExternalDisc.getText());
				temp = tBackground.getText().split(",");
				Config.setResolution(tResolution.getText());
				Config.setBackgroundColor(new Color(Integer.parseInt(temp[0]),
							Integer.parseInt(temp[1]),
							Integer.parseInt(temp[2])
							));
				temp = tForeground.getText().split(",");
				Config.setFrontgroundColor(new Color(Integer.parseInt(temp[0]),
							Integer.parseInt(temp[1]),
							Integer.parseInt(temp[2])
							));
				Parsers.setSkipPixels(Integer.parseInt(tPixelSkip.getText()));
				Config.setRandomChars(tRandomChars.getText());
				PCStart.restartWindow();
			}});
		
		FullScreen.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                    setButtonColor(FullScreen, !Config.isFullScreen());
                    Config.setFullScreen(!Config.isFullScreen());
            }
            
		});
		OnTop.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                    setButtonColor(OnTop, !Config.isOnTop());
                    Config.setOnTop(!Config.isOnTop());
            }
            
		});
		
		add(lExternalDisc);
		add(tExternalDisc);
		add(lResolution);
		add(tResolution);
		 add(lButtons);
         add(FullScreen);
         add(OnTop);
		add(lBackground);
		add(tBackground);
		add(lFrontground);
		add(tForeground);
		add(lPixelSkip);
		add(tPixelSkip);
		add(lRandomChars);
		add(tRandomChars);
		add(bSave);
	}
	
}
