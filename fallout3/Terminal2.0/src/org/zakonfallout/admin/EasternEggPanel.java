package org.zakonfallout.admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.vexillium.easterneggs.EasternEggConfig;
import org.zakonfallout.Config;

public class EasternEggPanel extends JPanel {

	private JTextField tTimeInterval = new JTextField(""+EasternEggConfig.getHappensIn()),
					   tFlashTime = new JTextField(""+EasternEggConfig.getFlashPanelTime()),
					   tFlashText = new JTextField(EasternEggConfig.getFlashText()),
					   tFlashPanelPossibility = new JTextField(""+EasternEggConfig.getFlashPanelPossibility()),
					   tCharsRabarbar = new JTextField(EasternEggConfig.getToReplaceChar()+"/"+EasternEggConfig.getReplacementChar()),
					   tRabarbarPossibility = new JTextField(""+EasternEggConfig.getRabarbarPossibility());
	private JButton bFlashOne = new JButton("Flash Text"),
					bRabarbar = new JButton("Rabarbar"),
					bApply = new JButton("Save"),
					bEEWork = new JButton("EE ON/OFF");
	private JLabel lTimeInterval = new JLabel("Eastern egg time Intervals"),
				   lFlashTime = new JLabel("Flash time"),
				   lEEWork = new JLabel("Turn on/off Eastern Eggs"),
				   lFlashText = new JLabel("Flash text"),
				   lFlashPanelPossibility = new JLabel("Possibility of flash panel"),
				   lRabarbarPossibility = new JLabel("Possibility of rabarbar"),
				   lCharsRabarbar = new JLabel("Rabarbar chars"),
				   lFlashRab = new JLabel("Flash || Rabarbar on/off");
	
	public EasternEggPanel(int width, int height){
		setSize(width,height);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.gray);
		setButtonColor(bFlashOne, EasternEggConfig.isFlashing());
		setButtonColor(bRabarbar, EasternEggConfig.isRabarbar());
		setButtonColor(bEEWork, Config.isEasternEggs());
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
	//button
		bEEWork.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setButtonColor(bEEWork, !Config.isEasternEggs());
				Config.setEasternEggs(!Config.isEasternEggs());
				
			}
		});

		bFlashOne.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setButtonColor(bFlashOne, !EasternEggConfig.isFlashing());
				EasternEggConfig.setFlashing(!EasternEggConfig.isFlashing());
				
			}
		});
		
		bRabarbar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setButtonColor(bRabarbar, !EasternEggConfig.isRabarbar());
				EasternEggConfig.setRabarbar(!EasternEggConfig.isRabarbar());
			}
		});
		
		bApply.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				EasternEggConfig.setFlashPanelTime(Integer.parseInt(tTimeInterval.getText()));
				EasternEggConfig.setFlashPanelPossibility(Integer.parseInt(tFlashPanelPossibility.getText()));
				EasternEggConfig.setFlashText(tFlashText.getText());
				EasternEggConfig.setFlashPanelTime(Integer.parseInt(tFlashTime.getText()));
				EasternEggConfig.setRabarbarPossibility(Integer.parseInt(tRabarbarPossibility.getText()));
				EasternEggConfig.setRabarbars(tCharsRabarbar.getText());
			}
		});
	//adds stuff
		add(lEEWork);
		add(bEEWork);
		add(lTimeInterval);
		add(tTimeInterval);
		add(lFlashTime);
		add(tFlashTime);
		add(lFlashText);
		add(tFlashText);
		add(lFlashPanelPossibility);
		add(tFlashPanelPossibility);
		add(lFlashRab);
		add(bFlashOne);
		add(bRabarbar);
		add(lCharsRabarbar);
		add(tCharsRabarbar);
		add(lRabarbarPossibility);
		add(tRabarbarPossibility);
		add(bApply);
		
	}
	
}
