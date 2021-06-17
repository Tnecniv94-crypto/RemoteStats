package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class RunPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -785098582113181256L;
	private JButton run, stop;
	private JCheckBox temp, power, fans;
	
	public RunPanel(JCheckBox temp, JCheckBox power, JCheckBox fans) {
		this.temp = temp;
		this.power = power;
		this.fans = fans;
		
		setUpView();
	}
	
	private void setUpView() {
		run = new JButton("Run!");
		stop = new JButton("Stop!");
		
		setLayout(new FlowLayout());
		add(run);
		add(stop);
		setBorder(BorderFactory.createTitledBorder("Run/Stop"));
		
		addActionListeners();
	}
	
	private void addActionListeners() {
		run.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(temp.isSelected()) {
					MyFrame.myFrame.run(true, false, false);
				}
				
				if(power.isSelected()) {
					MyFrame.myFrame.run(false, true, false);
				}
				
				if(fans.isSelected()) {
					MyFrame.myFrame.run(false, false, true);
				}
			} 
		});
		
		stop.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				MyFrame.myFrame.stopAllThreads();
			} 
		});
	}
}
