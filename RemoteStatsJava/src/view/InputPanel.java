package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class InputPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 424290221532260697L;
	private MyFrame container;
	private TokenPanel tokenPanel;
	private ReportPanel reportPanel;
	private RunPanel runPanel;
	
	public InputPanel(MyFrame container) {
		this.container = container;
		
		setUpView();
	}
	
	private void setUpView() {
		tokenPanel = new TokenPanel(this);
		reportPanel = new ReportPanel(this);
		runPanel = new RunPanel(this);
		
		setLayout(new BorderLayout());
		add(tokenPanel, BorderLayout.NORTH);
		add(reportPanel, BorderLayout.CENTER);
		add(runPanel, BorderLayout.SOUTH);
	}
	
	public TokenPanel getTokenPanel() {
		return tokenPanel;
	}
	
	public ReportPanel getReportPanel() {
		return reportPanel;
	}
	
	public MyFrame getContainsThis() {
		return container;
	}
}
