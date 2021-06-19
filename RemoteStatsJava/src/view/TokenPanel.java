package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.TokenGenerator;

public class TokenPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3447772371917727580L;
	private InputPanel container;
	private JPanel tokenInfo, tokenGenerate;
	private JButton generateToken;
	private JLabel currentToken, tokenLabel;
	private String token;
	
	public TokenPanel(InputPanel container) {
		this.container = container;
		
		setUpView();
	}
	
	private void setUpView() {
		token = getTokenFromConfig();
		tokenInfo = new JPanel(new FlowLayout());
		tokenGenerate = new JPanel(new FlowLayout());
		tokenLabel = new JLabel("Current token: ");
		currentToken = new JLabel(token);
		generateToken = new JButton("Generate new token!");
		
		setLayout(new BorderLayout());
		tokenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentToken.setAlignmentX(Component.CENTER_ALIGNMENT);
		generateToken.setAlignmentX(Component.CENTER_ALIGNMENT);
		tokenInfo.add(tokenLabel);
		tokenInfo.add(currentToken);
		tokenGenerate.add(generateToken);
		add(tokenInfo, BorderLayout.NORTH);
		add(tokenGenerate, BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder("Token info"));
		
		addActionListener();
	}
	
	private void addActionListener() {
		generateToken.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(JOptionPane.showConfirmDialog(container,
                        "If you create a new token, you will have to update the current token in your mobile phone!", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					container.getContainsThis().delete(true, true, true);
					currentToken.setText(token = generateTokenToConfig());
					container.getContainsThis().setUpData();
				}
			} 
		});
	}
	
	private String getTokenFromConfig() {
		Properties prop = new Properties();
		FileOutputStream fStream;
		String token;
		
		try {
			prop.load(new FileInputStream("config.properties"));
			token = prop.getProperty("token");
			
			if(token.compareTo("-1") == 0) {
				token = TokenGenerator.generateToken();
				prop.setProperty("token", token);
				prop.store(fStream = new FileOutputStream("config.properties"), null);
				fStream.close();
			}
			
			return token;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String generateTokenToConfig() {
		Properties prop = new Properties();
		FileInputStream fInStream;
		FileOutputStream fOutStream;
		String token;
		
		try {
			prop.load(fInStream = new FileInputStream("config.properties"));
			token = TokenGenerator.generateToken();
			prop.setProperty("token", token);
			prop.store(fOutStream = new FileOutputStream("config.properties"), null);
			fInStream.close();
			fOutStream.close();
			
			return token;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getToken() {
		return token;
	}

	public JButton getGenerateToken() {
		return generateToken;
	}
	
	public InputPanel getContainsThis() {
		return container;
	}
}
