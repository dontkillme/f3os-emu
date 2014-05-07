package org.zakonfallout.admin;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AdminPanel extends JPanel {

	private ConfigPanel configPanel;
	private JTabbedPane tabPane = new JTabbedPane();
	
	public AdminPanel(int W, int H){
		setSize(W,H);
		setLayout(new FlowLayout());
		setBackground(Color.gray);
		configPanel = new ConfigPanel(W,H);
		tabPane.addTab("Main settings",null,configPanel,"Main settings from config.f3s");
		add(tabPane);
	}
	
}
