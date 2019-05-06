package logic;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.toedter.calendar.JDateChooser;
import javax.swing.SwingConstants;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class Ticket {

	Database database = new Database();
	private String selectedDate, journeyDateInfo, route = "", time = "";
	private JFrame frame;
	JPanel parentPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JLabel[] seat = new JLabel[60];
	JDateChooser dateChooser;
	Boolean[] isBooked = new Boolean[50];
	Color available = new Color(0, 255, 0);
	Color booked = new Color(255, 0, 0);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ticket window = new Ticket();
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
	public Ticket() {
		database.databaseConnector();
		initializeRightPanel();
		rightPanel.setVisible(false);
		initialize();
	}

	//rightPanel initializing
	private void initializeRightPanel() {

		rightPanel.setBounds(413, 23, 238, 468);
		rightPanel.setLayout(null);
		rightPanel.setOpaque(false);
		
		
		


		MouseListener listener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				((Component) e.getSource()).setForeground(Color.WHITE);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				((Component) e.getSource()).setForeground(Color.BLUE);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				String seatNo = ((JLabel) e.getSource()).getText();

				//checking if seat is already booked
				if (database.checkIfBooked(seatNo)) {
					JOptionPane.showMessageDialog(null, "This Seat Already Booked!", "Ticket Already Booked!!!", 0);
				} else {
					JDialog.setDefaultLookAndFeelDecorated(true);//JOption pane decoration

					//asking ticket confirmation
					int response = JOptionPane.showConfirmDialog(null, "Do you want to book this seat?", "Confirm",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {

						//taking customer name, contact
						JTextField name = new JTextField(), contactNo = new JTextField();
						Object[] message = { "Name:", name, "Contact No:", contactNo };

						int option = JOptionPane.showConfirmDialog(null, message, "Passenger Information",
								JOptionPane.OK_CANCEL_OPTION);
						if (option == JOptionPane.OK_OPTION) {

							try {
								String buyerContact = contactNo.getText(), buyerName = name.getText();

								//if (printWork(parentPanel)) {
									PrintTicket print=new PrintTicket(buyerName, route, journeyDateInfo, time, seatNo);
								//	PrintTicket.main(null);
									if(print.printWork(print.panel))
									{					
									database.insertData(seatNo, buyerName, buyerContact);
									((Component) e.getSource()).setBackground(booked);
									String bookingDetails = "Name: " + buyerName + "\nContact No:" + contactNo.getText()
											+ "\nJourney Date:" + journeyDateInfo;

									JOptionPane.showMessageDialog(null, bookingDetails, "Ticket Booked!!!", 1);
									}
								 else {
									JOptionPane.showMessageDialog(null, "Ticket Not Booked", "Error!!!", 0);
								}

							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else {
							System.out.println("Ticket Booking Canceled");
						}

					} else if (response == JOptionPane.CLOSED_OPTION) {
						System.out.println("JOptionPane closed");
					}

				}
			}

			
		};

		// setting seat properties
		for (int index = 1, row = 1, rowPosition = 84; row <= 11; row++, rowPosition += 35) {

			for (int column = 0; column < 4; column++) {
				char seatRow = (char) (64 + row);
				seat[index] = new JLabel();
				seat[index].setOpaque(true);
				seat[index].setHorizontalAlignment(SwingConstants.CENTER);
				seat[index].setFont(new Font("Times New Roman", Font.BOLD, 18));
				seat[index].setForeground(Color.WHITE);
				seat[index].addMouseListener(listener);

				switch (column) {
				case 0:
					seat[index].setText(((char) seatRow) + "1");
					seat[index].setBounds(0, rowPosition, 46, 33);
					break;
				case 1:
					seat[index].setText(((char) seatRow) + "2");
					seat[index].setBounds(50, rowPosition, 46, 33);
					break;
				case 2:
					seat[index].setText(((char) seatRow) + "3");
					seat[index].setBounds(142, rowPosition, 46, 33);
					break;
				case 3:
					seat[index].setText(((char) seatRow) + "4");
					seat[index].setBounds(192, rowPosition, 46, 33);
					break;
				default:
					break;
				}
				rightPanel.add(seat[index]);
				index++;
			}
		}
		parentPanel.add(rightPanel);
	}

	// initialize component
	private void initialize() {
		frame = new JFrame("Ticket Booking");
		frame.setBounds(100, 100, 700, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		parentPanel.setBounds(5, 5, 684, 562);
		frame.getContentPane().add(parentPanel);
		parentPanel.setLayout(null);
		
		ItemListener itemListener=new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					rightPanel.setVisible(false);
					}
				
			}
		};
		
		

		
		JComboBox<String> routeComboBox = new JComboBox<String>();
		routeComboBox.addItemListener(itemListener);
		routeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		routeComboBox.setBounds(110, 195, 206, 36);
		routeComboBox.addItem("Dhaka-Barishal");
		routeComboBox.addItem("Dhaka-Chattogram");
		routeComboBox.addItem("Dhaka-Khulna");
		routeComboBox.addItem("Dhaka-Mymensingh");
		routeComboBox.addItem("Dhaka-Rajshahi");
		routeComboBox.addItem("Dhaka-Rangpur");
		routeComboBox.addItem("Dhaka-Sylet");
		parentPanel.add(routeComboBox);

		JComboBox<String> shiftComboBox = new JComboBox<String>();
		shiftComboBox.addItemListener(itemListener);
		shiftComboBox.setBounds(110, 263, 206, 36);
		shiftComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		shiftComboBox.addItem("Day");
		shiftComboBox.addItem("Night");
		parentPanel.add(shiftComboBox);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(110, 127, 206, 36);
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		parentPanel.add(dateChooser);
		JButton showBtn = new JButton("Show Ticket");
		showBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				java.util.Date date = dateChooser.getDate();
				if (date != null) {
					try {

						// Checking if date is valid
						if (isValidDate()) {
							route = routeComboBox.getSelectedItem().toString();

							// getting time according to shift
							if ((shiftComboBox.getSelectedItem().toString()).equals("Day")) {
								time = "9.15 am";
							} else {
								time = "10.00 pm";
							}

							// Initialize Database
							database.initializeDatabase(
									(String) shiftComboBox.getSelectedItem() + selectedDate
											+ routeComboBox.getSelectedIndex(),
									journeyDateInfo, shiftComboBox.getSelectedIndex(),
									routeComboBox.getSelectedIndex());

							// getting and setting seat status
							for (int i = 1; i <= 44; i++) {
								if (database.seatIsBooked[i])
									seat[i].setBackground(booked);
								else
									seat[i].setBackground(available);
							}
							rightPanel.setVisible(true);

						}
					} catch (ParseException e) {
						System.out.println("Ticket Line:295" + e.toString());
					}
				} else {
					JOptionPane.showMessageDialog(parentPanel, "Date is required.", "Choose a Date", 0);
				}

			}
		});
		showBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		showBtn.setBounds(110, 424, 139, 31);
		parentPanel.add(showBtn);
		JLabel windowBackground = new JLabel("");
		windowBackground.setIcon(new ImageIcon(Ticket.class.getResource("/images/background.png")));
		windowBackground.setBounds(0, 0, 684, 512);
		parentPanel.add(windowBackground);

	}

	// valid date checker
	Boolean isValidDate() throws ParseException {
		boolean validDateState = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat dateInfoFormat = new SimpleDateFormat("dd.MM.yyyy");

		selectedDate = dateFormat.format(dateChooser.getDate());// date for table
		journeyDateInfo = dateInfoFormat.format(dateChooser.getDate()).toString();// date for display

		Date journeyDate = dateFormat.parse(selectedDate);
		Date currentDate = dateFormat.parse(dateFormat.format(new Date()));

		if (journeyDate.compareTo(currentDate) > 0 || journeyDate.compareTo(currentDate) == 0) {
			validDateState = true;
		} else {
			JOptionPane.showMessageDialog(parentPanel, "Date is not Valid.", "Choose a Valid Date", 0);
			validDateState = false;
		}
		return validDateState;

	}
}
