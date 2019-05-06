package logic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private JTextField userName;
	private JPasswordField passwordField;
	private JLabel buttonLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Ticket Booking");
		frame.setBounds(100, 100, 500, 330);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		buttonLbl = new JLabel("");
		buttonLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String pwd = new String(passwordField.getPassword());
				if(userName.getText().toString().equals("admin")&&pwd.equals("pass"))
				{
					JOptionPane.showMessageDialog(frame, "Login Success", "Congratulation!!!", 1);
					Ticket.main(null);
					frame.dispose();
				}else {

					JOptionPane.showMessageDialog(frame, "User Name & Password not matched.", "Login Failed!", 0);
				}
			}
		});
		buttonLbl.setBounds(27, 236, 436, 55);
		buttonLbl.setOpaque(false);
		frame.getContentPane().add(buttonLbl);
		

		JLabel label = new JLabel("");
		label.setBackground(new Color(255,255,255));
		label.setIcon(new ImageIcon(Login.class.getResource("/images/login.png")));
		label.setBounds(0, 0, 494, 302);
		frame.getContentPane().add(label);
		
		userName = new JTextField();
		userName.setBackground(new Color(203,211,203));
		userName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		userName.setBounds(78, 114, 385, 50);
		userName.setEditable(true);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		userName.setBackground(new Color(203,211,203));
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(203,211,203));
		passwordField.setFont(new Font("Times New Roman", Font.BOLD, 18));
		passwordField.setBounds(78, 175, 385, 50);
		frame.getContentPane().add(passwordField);		
	}
}
