package view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class ReportPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7207657249518835133L;
	private InputPanel container;
	private JCheckBox temp, power, fans;
	
	public ReportPanel(InputPanel container) {
		this.container = container;
		
		setUpView();
	}
	
	private void setUpView() {
		temp = new JCheckBox("report temperature", true);
		power = new JCheckBox("report power", true);
		fans = new JCheckBox("report fans", true);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//temp.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(temp);
		//power.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(power);
		//power.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(fans);
		setBorder(BorderFactory.createTitledBorder("Report"));
	}
	
	public JCheckBox getTemp() {
		return temp;
	}
	
	public JCheckBox getPower() {
		return power;
	}
	
	public JCheckBox getFans() {
		return fans;
	}
	
	public InputPanel getContainer() {
		return container;
	}
}
