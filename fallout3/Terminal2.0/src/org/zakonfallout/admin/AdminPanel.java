package org.zakonfallout.admin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.zakonfallout.MainWindow;

public class AdminPanel extends JPanel {

	private ConfigPanel configPanel;
	private EasternEggPanel easternPanel;
	private JTabbedPane tabPane = new JTabbedPane();
	private JScrollPane scrollPane = new JScrollPane(tabPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JButton bExit = new JButton("Exit");
	
	public AdminPanel(int W, int H){
		setSize(W,H);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(Color.gray);
		
		//exit button actions
		bExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainWindow.returnFromPanel();
			}
		});
		
		configPanel = new ConfigPanel(W,H);
		easternPanel = new EasternEggPanel(W, H);
		tabPane.addTab("Main settings",null,configPanel,"Main settings from config.f3s");
		tabPane.addTab("EasternEgg settings",null,easternPanel,"Eastern egg settings from easternegg.f3s");
		add(bExit);
		add(scrollPane);
	}
	
}
