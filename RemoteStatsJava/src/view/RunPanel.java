package view;

import java.awt.Container;
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
	private InputPanel container;
	private JButton run, stop, clearConsole;
	
	public RunPanel(InputPanel container) {
		this.container = container;
		
		setUpView();
	}
	
	private void setUpView() {
		run = new JButton("Run!");
		stop = new JButton("Stop!");
		clearConsole = new JButton("Delete log.");
		
		setLayout(new FlowLayout());
		add(run);
		stop.setEnabled(false);
		add(stop);
		add(clearConsole);
		setBorder(BorderFactory.createTitledBorder("Run/Stop"));
		
		addActionListeners();
	}
	
	private void addActionListeners() {
		run.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				MyFrame myFrame = container.getContainsThis();
				ReportPanel reportPanel = container.getReportPanel();
				TokenPanel tokenPanel = container.getTokenPanel();
				boolean temp = reportPanel.getTemp().isSelected(), power = reportPanel.getPower().isSelected(), fans = reportPanel.getFans().isSelected();
				
				if(temp) {
					myFrame.run(true, false, false); // MyFrame
				}
				else {
					myFrame.delete(true, false, false);
				}
				
				if(power) {
					myFrame.run(false, true, false);
				}
				else {
					myFrame.delete(false, true, false);
				}
				
				if(fans) {
					myFrame.run(false, false, true);
				}
				else {
					myFrame.delete(false, false, true);
				}
				
				if(temp | power | fans) {
					run.setEnabled(false);
					stop.setEnabled(true);
					reportPanel.getTemp().setEnabled(false);
					reportPanel.getPower().setEnabled(false);
					reportPanel.getFans().setEnabled(false);
					tokenPanel.getGenerateToken().setEnabled(false);
				}
			} 
		});
		
		stop.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				ReportPanel reportPanel = container.getReportPanel();
				TokenPanel tokenPanel = container.getTokenPanel();

				container.getContainsThis().stopAllThreads();
				run.setEnabled(true);
				stop.setEnabled(false);
				reportPanel.getTemp().setEnabled(true);
				reportPanel.getPower().setEnabled(true);
				reportPanel.getFans().setEnabled(true);
				tokenPanel.getGenerateToken().setEnabled(true);
			} 
		});
		
		clearConsole.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				container.getContainsThis().getConsolePanel().getConsole().setText("");
			} 
		});
	}
	
	public InputPanel getContainsThis() {
		return container;
	}
}
