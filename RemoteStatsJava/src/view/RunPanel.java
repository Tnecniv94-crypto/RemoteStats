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
	private InputPanel container;
	private JButton run, stop;
	
	public RunPanel(InputPanel container) {
		this.container = container;
		
		setUpView();
	}
	
	private void setUpView() {
		run = new JButton("Run!");
		stop = new JButton("Stop!");
		
		setLayout(new FlowLayout());
		add(run);
		stop.setEnabled(false);
		add(stop);
		setBorder(BorderFactory.createTitledBorder("Run/Stop"));
		
		addActionListeners();
	}
	
	private void addActionListeners() {
		ReportPanel reportPanel = container.getReportPanel();
		TokenPanel tokenPanel = container.getTokenPanel();
		
		boolean temp = reportPanel.getTemp().isSelected(), power = reportPanel.getPower().isSelected(), fans = reportPanel.getFans().isSelected();
		
		run.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(temp) {
					container.getContainsThis().run(true, false, false); // MyFrame
				}
				
				if(power) {
					container.getContainsThis().run(false, true, false);
				}
				
				if(fans) {
					container.getContainsThis().run(false, false, true);
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
				container.getContainsThis().stopAllThreads();
				
				run.setEnabled(true);
				stop.setEnabled(false);
				reportPanel.getTemp().setEnabled(true);
				reportPanel.getPower().setEnabled(true);
				reportPanel.getFans().setEnabled(true);
				tokenPanel.getGenerateToken().setEnabled(true);
			} 
		});
	}
	
	public InputPanel getContainsThis() {
		return container;
	}
}
