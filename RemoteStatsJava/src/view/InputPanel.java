package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class InputPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 424290221532260697L;
	private TokenPanel tokenPanel;
	private ReportPanel reportPanel;
	private RunPanel runPanel;
	
	public InputPanel() {
		setUpView();
	}
	
	private void setUpView() {
		tokenPanel = new TokenPanel();
		reportPanel = new ReportPanel();
		runPanel = new RunPanel(reportPanel.getTemp(), reportPanel.getPower(), reportPanel.getFans());
		
		setLayout(new BorderLayout());
		add(tokenPanel, BorderLayout.NORTH);
		add(reportPanel, BorderLayout.CENTER);
		add(runPanel, BorderLayout.SOUTH);
	}
	
	public TokenPanel getTokenPanel() {
		return tokenPanel;
	}
}
