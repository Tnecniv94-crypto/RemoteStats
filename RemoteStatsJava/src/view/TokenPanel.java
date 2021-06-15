package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.TokenGenerator;

public class TokenPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3447772371917727580L;
	JPanel tokenInfo, tokenGenerate;
	JButton generateTokenButton;
	JLabel currentToken, tokenLabel;
	String token;
	
	public TokenPanel() {
		setUpView();
	}
	
	private void setUpView() {
		token = TokenGenerator.generateToken();
		tokenInfo = new JPanel(new FlowLayout());
		tokenGenerate = new JPanel(new FlowLayout());
		tokenLabel = new JLabel("Current token: ");
		currentToken = new JLabel(token);
		generateTokenButton = new JButton("Generate new token!");
		
		setLayout(new BorderLayout());
		tokenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentToken.setAlignmentX(Component.CENTER_ALIGNMENT);
		generateTokenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		tokenInfo.add(tokenLabel);
		tokenInfo.add(currentToken);
		tokenGenerate.add(generateTokenButton);
		add(tokenInfo, BorderLayout.NORTH);
		add(tokenGenerate, BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder("Token info"));
		
		addActionListener();
	}
	
	private void addActionListener() {
		generateTokenButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				currentToken.setText(TokenGenerator.generateToken());
			} 
		});
	}
}
