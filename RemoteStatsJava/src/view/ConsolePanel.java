package view;

import java.awt.Component;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3550141002587630858L;
	private JTextArea console;
	private JLabel consoleTitle;
	
	public ConsolePanel() {
		setUpView();
	}
	
	private void setUpView() {
		console = new JTextArea(15, 40);
		consoleTitle = new JLabel("Console log:");
		
		console.setEditable(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	    consoleTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	    add(consoleTitle);
	    add(new JScrollPane(console));
	    
	    PrintStream printStream = new PrintStream(new CustomOutputStream(console));
	    System.setOut(printStream);
	    System.setErr(printStream);
	}
	
	public JTextArea getConsole() {
		return console;
	}
}
